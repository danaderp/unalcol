package edu.wayne.cs.severe.redress2.entity.refactoring.formulas.mm;

import java.util.HashMap;
import java.util.LinkedHashMap;

import edu.wayne.cs.severe.redress2.controller.MetricUtils;
import edu.wayne.cs.severe.redress2.controller.metric.CYCLOMetric;
import edu.wayne.cs.severe.redress2.controller.metric.CodeMetric;
import edu.wayne.cs.severe.redress2.entity.MethodDeclaration;
import edu.wayne.cs.severe.redress2.entity.TypeDeclaration;
import edu.wayne.cs.severe.redress2.entity.refactoring.RefactoringOperation;

public class CYCLOMoveMethodPF extends MoveMethodPredFormula {

	@Override
	public HashMap<String, Double> predictMetrVal(RefactoringOperation ref,
			LinkedHashMap<String, LinkedHashMap<String, Double>> prevMetrics)
			throws Exception {

		TypeDeclaration srcCls = getSourceClass(ref);
		MethodDeclaration method = getMethod(ref);
		TypeDeclaration tgtCls = getTargetClass(ref);

		double cyclo = MetricUtils.getCycloMethod(srcCls, method.getObjName());

		HashMap<String, Double> predMetrs = new HashMap<String, Double>();

		Double prevMetr = prevMetrics.get(srcCls.getQualifiedName()).get(
				getMetric().getMetricAcronym());
		predMetrs.put(srcCls.getQualifiedName(), prevMetr - cyclo);

		LinkedHashMap<String, Double> prevMetrsTgt = prevMetrics.get(tgtCls
				.getQualifiedName());
		Double prevMetrTgt = prevMetrsTgt == null ? 0 : prevMetrsTgt
				.get(getMetric().getMetricAcronym());
		predMetrs.put(tgtCls.getQualifiedName(), prevMetrTgt + cyclo);

		return predMetrs;
	}

	@Override
	public CodeMetric getMetric() {
		return new CYCLOMetric();
	}

}
