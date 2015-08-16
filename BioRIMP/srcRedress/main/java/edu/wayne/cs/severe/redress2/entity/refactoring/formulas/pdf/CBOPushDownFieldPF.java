package edu.wayne.cs.severe.redress2.entity.refactoring.formulas.pdf;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;

import edu.wayne.cs.severe.redress2.controller.MetricUtils;
import edu.wayne.cs.severe.redress2.controller.metric.CBOMetric;
import edu.wayne.cs.severe.redress2.controller.metric.CodeMetric;
import edu.wayne.cs.severe.redress2.entity.AttributeDeclaration;
import edu.wayne.cs.severe.redress2.entity.TypeDeclaration;
import edu.wayne.cs.severe.redress2.entity.refactoring.RefactoringOperation;

/**
 * Prediction formula for Push Down Field and the CBO metric
 * 
 * @author ojcchar
 * 
 */
public class CBOPushDownFieldPF extends PushDownFieldPredFormua {

	@Override
	public HashMap<String, Double> predictMetrVal(RefactoringOperation ref,
			LinkedHashMap<String, LinkedHashMap<String, Double>> prevMetrics)
			throws Exception {

		// get the parameters of the refactoring
		TypeDeclaration srcCls = getSourceClass(ref);
		AttributeDeclaration attr = getAttribute(ref);
		List<TypeDeclaration> tgtClses = getTargetClasses(ref);
		CodeMetric metric = getMetric();

		HashMap<String, Double> predMetrs = new HashMap<String, Double>();
		Double prevMetr = prevMetrics.get(srcCls.getQualifiedName()).get(
				metric.getMetricAcronym());
		predMetrs.put(srcCls.getQualifiedName(),
				prevMetr - getDelta(srcCls, attr, tgtClses.get(0)));

		// compute the metric for the subclasses
		for (TypeDeclaration tgtCls : tgtClses) {
			Double prevMetrTgt = prevMetrics.get(tgtCls.getQualifiedName())
					.get(metric.getMetricAcronym());
			predMetrs.put(tgtCls.getQualifiedName(), prevMetrTgt);
		}

		return predMetrs;
	}

	// If c_{s} uses the new field a_{k} of class c_{t} and before it was
	// not using any method or field of class c_{t} , then d_{k}=1,
	// otherwise d_{k}=0
	private Double getDelta(TypeDeclaration srcCls, AttributeDeclaration attr,
			TypeDeclaration tgtCls) throws Exception {

		boolean usingTgtClass = false;

		if (tgtCls.getCompUnit() != null) {

			// using a method or field of tgtCls?
			HashSet<String> methods = MetricUtils.getMethods(tgtCls);
			HashSet<String> fields = MetricUtils.getFields(tgtCls);

			for (String method : methods) {
				int numMethods = MetricUtils.getNumberOfMethodsUsingString(
						srcCls, method);
				if (numMethods > 0) {
					usingTgtClass = true;
					break;
				}
			}

			if (!usingTgtClass) {
				for (String field : fields) {
					int numMethods = MetricUtils.getNumberOfMethodsUsingString(
							srcCls, field);
					if (numMethods > 0) {
						usingTgtClass = true;
						break;
					}
				}
			}

		}

		// using the field in any method?
		int numMethods = MetricUtils.getNumberOfMethodsUsingString(srcCls,
				attr.getObjName());

		return (!usingTgtClass && numMethods > 0) ? 1.0 : 0.0;
	}

	public CodeMetric getMetric() {
		return new CBOMetric();
	}

}
