package edu.wayne.cs.severe.redress2.entity.refactoring.formulas.pdm;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import edu.wayne.cs.severe.redress2.controller.metric.CodeMetric;
import edu.wayne.cs.severe.redress2.controller.metric.NOMMetric;
import edu.wayne.cs.severe.redress2.entity.TypeDeclaration;
import edu.wayne.cs.severe.redress2.entity.refactoring.RefactoringOperation;

public class NOMPushDownMethodPF extends PushDownPredFormula {

	@Override
	public HashMap<String, Double> predictMetrVal(RefactoringOperation ref,
			LinkedHashMap<String, LinkedHashMap<String, Double>> prevMetrics)
			throws Exception {

		TypeDeclaration srcCls = getSourceClass(ref);
		List<TypeDeclaration> tgtClses = getTargetClasses(ref);

		HashMap<String, Double> predMetrs = new HashMap<String, Double>();

		Double prevMetr = prevMetrics.get(srcCls.getQualifiedName()).get(
				getMetric().getMetricAcronym());
		predMetrs.put(srcCls.getQualifiedName(), prevMetr - 1);

		for (TypeDeclaration tgtCls : tgtClses) {
			predMetrs.put(
					tgtCls.getQualifiedName(),
					prevMetrics.get(tgtCls.getQualifiedName()).get(
							getMetric().getMetricAcronym()) + 1);
		}

		return predMetrs;
	}

	@Override
	public CodeMetric getMetric() {
		return new NOMMetric();
	}

}
