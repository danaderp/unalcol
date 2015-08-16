package edu.wayne.cs.severe.redress2.entity.refactoring.formulas.pum;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;

import edu.wayne.cs.severe.redress2.controller.MetricUtils;
import edu.wayne.cs.severe.redress2.controller.metric.CodeMetric;
import edu.wayne.cs.severe.redress2.controller.metric.RFCMetric;
import edu.wayne.cs.severe.redress2.entity.ClassField;
import edu.wayne.cs.severe.redress2.entity.MethodDeclaration;
import edu.wayne.cs.severe.redress2.entity.TypeDeclaration;
import edu.wayne.cs.severe.redress2.entity.refactoring.RefactoringOperation;
import edu.wayne.cs.severe.redress2.utils.PullUpMethodUtils;

/**
 * Prediction formula for Pull Up Method and the RFC metric
 * 
 * @author ojcchar
 * 
 */
public class RFCPullUpMethodPF extends PullUpMethodPredFormula {

	@Override
	public HashMap<String, Double> predictMetrVal(RefactoringOperation ref,
			LinkedHashMap<String, LinkedHashMap<String, Double>> prevMetrics)
			throws Exception {

		// get the parameters of the refactoring
		List<TypeDeclaration> srcClses = getSourceClasses(ref);
		MethodDeclaration method = getMethod(ref);
		TypeDeclaration tgtCls = getTargetClass(ref);
		CodeMetric metric = getMetric();

		// predicted metrics
		HashMap<String, Double> predMetrs = new HashMap<String, Double>();

		// get the method calls in the method
		LinkedHashSet<String> callsMethod = MetricUtils.getMethodCallsMethod(
				srcClses.get(0), method.getObjName());

		// get the fields being read
		List<ClassField> usedFieldsSrc = MetricUtils.getFieldsUsedByMethod(
				srcClses.get(0), method);
		// delta of the fields used by the method
		double deltaFieldsUsed = PullUpMethodUtils.getDeltaFieldsUsed(
				usedFieldsSrc, srcClses.get(0));

		// for every subclass
		for (TypeDeclaration srcCls : srcClses) {

			// get the previous metric
			Double prevMetr = prevMetrics.get(srcCls.getQualifiedName()).get(
					metric.getMetricAcronym());

			// delta for the calls
			Double deltaCalls = getDeltaCalls(srcCls, method, callsMethod);
			predMetrs.put(srcCls.getQualifiedName(), prevMetr - deltaCalls
					+ deltaFieldsUsed);
		}

		// prediction for the superclass
		// get the previous metric
		Double prevMetrTgt = prevMetrics.get(tgtCls.getQualifiedName()).get(
				metric.getMetricAcronym());
		double deltaSubclassMethodsUsed = PullUpMethodUtils
				.getDeltaSubclassMethodsUsed(srcClses.get(0), callsMethod,
						tgtCls);
		Double deltaCalls = getDeltaCalls(tgtCls, method, callsMethod);

		// delta of the fields used by the method
		double deltaFieldsUsedTgt = PullUpMethodUtils.getDeltaFieldsUsed(
				usedFieldsSrc, tgtCls);
		predMetrs.put(tgtCls.getQualifiedName(), prevMetrTgt + deltaCalls
				+ deltaSubclassMethodsUsed + 2 * deltaFieldsUsedTgt);

		return predMetrs;
	}

	private Double getDeltaCalls(TypeDeclaration typeDcl,
			MethodDeclaration method, LinkedHashSet<String> callsMethodP)
			throws Exception {

		LinkedHashSet<String> callsMethod = new LinkedHashSet<String>(
				callsMethodP);

		LinkedHashSet<String> callsNoMethod = new LinkedHashSet<String>();
		if (typeDcl.getCompUnit() != null) {
			callsNoMethod = MetricUtils.getMethodCallsNoMethod(typeDcl,
					method.getObjName());
		}
		callsMethod.removeAll(callsNoMethod);

		return (1.0 + callsMethod.size());
	}

	@Override
	public CodeMetric getMetric() {
		return new RFCMetric();
	}

}
