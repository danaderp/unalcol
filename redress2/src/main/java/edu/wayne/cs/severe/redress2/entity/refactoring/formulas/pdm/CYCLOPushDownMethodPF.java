package edu.wayne.cs.severe.redress2.entity.refactoring.formulas.pdm;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import edu.wayne.cs.severe.redress2.controller.MetricUtils;
import edu.wayne.cs.severe.redress2.controller.metric.CYCLOMetric;
import edu.wayne.cs.severe.redress2.controller.metric.CodeMetric;
import edu.wayne.cs.severe.redress2.entity.MethodDeclaration;
import edu.wayne.cs.severe.redress2.entity.TypeDeclaration;
import edu.wayne.cs.severe.redress2.entity.refactoring.RefactoringOperation;

public class CYCLOPushDownMethodPF extends PushDownPredFormula {

	@Override
	public HashMap<String, Double> predictMetrVal(RefactoringOperation ref,
			LinkedHashMap<String, LinkedHashMap<String, Double>> prevMetrics)
			throws Exception {

		TypeDeclaration srcCls = getSourceClass(ref);
		MethodDeclaration method = getMethod(ref);
		List<TypeDeclaration> tgtClses = getTargetClasses(ref);

		HashMap<String, Double> predMetrs = new HashMap<String, Double>();

		double cyclo = MetricUtils.getCycloMethod(srcCls, method.getObjName());

		Double prevMetr = prevMetrics.get(srcCls.getQualifiedName()).get(
				getMetric().getMetricAcronym());
		predMetrs.put(srcCls.getQualifiedName(), prevMetr - cyclo);

		for (TypeDeclaration tgtCls : tgtClses) {
			predMetrs.put(
					tgtCls.getQualifiedName(),
					prevMetrics.get(tgtCls.getQualifiedName()).get(
							getMetric().getMetricAcronym())
							+ cyclo);
		}

		return predMetrs;
	}

	@Override
	public CodeMetric getMetric() {
		return new CYCLOMetric();
	}

}
