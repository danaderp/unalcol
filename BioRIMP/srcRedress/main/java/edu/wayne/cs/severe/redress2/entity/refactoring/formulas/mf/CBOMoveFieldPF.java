package edu.wayne.cs.severe.redress2.entity.refactoring.formulas.mf;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;

import edu.wayne.cs.severe.redress2.controller.MetricUtils;
import edu.wayne.cs.severe.redress2.controller.metric.CBOMetric;
import edu.wayne.cs.severe.redress2.controller.metric.CodeMetric;
import edu.wayne.cs.severe.redress2.entity.AttributeDeclaration;
import edu.wayne.cs.severe.redress2.entity.TypeDeclaration;
import edu.wayne.cs.severe.redress2.entity.refactoring.RefactoringOperation;

public class CBOMoveFieldPF extends MoveFieldPredFormula {

	@Override
	public HashMap<String, Double> predictMetrVal(RefactoringOperation ref,
			LinkedHashMap<String, LinkedHashMap<String, Double>> prevMetrics)
			throws Exception {

		TypeDeclaration srcCls = getSourceClass(ref);
		AttributeDeclaration attr = getAttribute(ref);
		TypeDeclaration tgtCls = getTargetClass(ref);
		CodeMetric metric = getMetric();

		HashMap<String, Double> predMetrs = new HashMap<String, Double>();

		Double prevMetr = prevMetrics.get(srcCls.getQualifiedName()).get(
				metric.getMetricAcronym());
		Double deltaSrc = getDelta(srcCls, attr, tgtCls);
		// System.out.println("deltaSrc: " + deltaSrc);
		predMetrs.put(srcCls.getQualifiedName(), prevMetr + deltaSrc);

		LinkedHashMap<String, Double> metrs = prevMetrics.get(tgtCls
				.getQualifiedName());
		Double prevMetrTgt = (metrs != null ? metrs.get(metric
				.getMetricAcronym()) : 0);
		// double deltaTgt = getDeltaTgt(srcCls, attr, tgtCls);
		predMetrs.put(tgtCls.getQualifiedName(), prevMetrTgt);

		return predMetrs;
	}

	// private double getDeltaTgt(TypeDeclaration srcCls,
	// AttributeDeclaration attr, TypeDeclaration tgtCls) throws Exception {
	//
	// LinkedHashSet<String> classesUsed = tgtCls.getCompUnit() != null ?
	// MetricUtils
	// .getClassesUsed(tgtCls, null) : new LinkedHashSet<String>();
	//
	// String fieldType = MetricUtils.getFieldType(srcCls, attr.getObjName());
	//
	// double delta = classesUsed.contains(fieldType + ".") ? 0.0 : 1.0;
	//
	// return delta;
	// }

	public CodeMetric getMetric() {
		return new CBOMetric();
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

}
