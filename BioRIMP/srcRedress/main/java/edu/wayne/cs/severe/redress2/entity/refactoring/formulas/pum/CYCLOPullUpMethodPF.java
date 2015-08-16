package edu.wayne.cs.severe.redress2.entity.refactoring.formulas.pum;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;

import edu.wayne.cs.severe.redress2.controller.MetricUtils;
import edu.wayne.cs.severe.redress2.controller.metric.CYCLOMetric;
import edu.wayne.cs.severe.redress2.controller.metric.CodeMetric;
import edu.wayne.cs.severe.redress2.entity.ClassField;
import edu.wayne.cs.severe.redress2.entity.MethodDeclaration;
import edu.wayne.cs.severe.redress2.entity.TypeDeclaration;
import edu.wayne.cs.severe.redress2.entity.refactoring.RefactoringOperation;
import edu.wayne.cs.severe.redress2.utils.PullUpMethodUtils;

/**
 * Prediction formula for Pull Up Method and the CYCLO metric
 * 
 * @author ojcchar
 * 
 */
public class CYCLOPullUpMethodPF extends PullUpMethodPredFormula {

	@Override
	public HashMap<String, Double> predictMetrVal(RefactoringOperation ref,
			LinkedHashMap<String, LinkedHashMap<String, Double>> prevMetrics)
			throws Exception {

		// get the parameters of the refactoring
		List<TypeDeclaration> srcClses = getSourceClasses(ref);
		MethodDeclaration method = getMethod(ref);
		TypeDeclaration tgtCls = getTargetClass(ref);
		CodeMetric metric = getMetric();

		HashMap<String, Double> predMetrs = new HashMap<String, Double>();

		double cyclo = MetricUtils.getCycloMethod(srcClses.get(0),
				method.getObjName());
		// get the fields being read
		List<ClassField> usedFieldsSrc = MetricUtils.getFieldsUsedByMethod(
				srcClses.get(0), method);
		// delta of the fields used by the method
		double deltaFieldsUsed = PullUpMethodUtils.getDeltaFieldsUsed(
				usedFieldsSrc, srcClses.get(0));

		for (TypeDeclaration srcCls : srcClses) {
			Double prevMetr = prevMetrics.get(srcCls.getQualifiedName()).get(
					metric.getMetricAcronym());
			predMetrs.put(srcCls.getQualifiedName(), prevMetr - cyclo
					+ deltaFieldsUsed);
		}

		// get the method calls in the method
		LinkedHashSet<String> callsMethod = MetricUtils.getMethodCallsMethod(
				srcClses.get(0), method.getObjName());

		// change the metric of the target class
		Double prevMetrTgt = prevMetrics.get(tgtCls.getQualifiedName()).get(
				metric.getMetricAcronym());
		// delta of the fields used by the method
		double deltaFieldsUsedTgt = PullUpMethodUtils.getDeltaFieldsUsed(
				usedFieldsSrc, tgtCls);
		double deltaSubclassMethodsUsed = PullUpMethodUtils
				.getDeltaSubclassMethodsUsed(srcClses.get(0), callsMethod,
						tgtCls);
		predMetrs.put(tgtCls.getQualifiedName(), prevMetrTgt + cyclo
				+ deltaFieldsUsedTgt + deltaSubclassMethodsUsed);

		return predMetrs;
	}

	@Override
	public CodeMetric getMetric() {
		return new CYCLOMetric();
	}

}
