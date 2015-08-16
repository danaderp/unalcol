package edu.wayne.cs.severe.redress2.entity.refactoring.formulas.puf;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import edu.wayne.cs.severe.redress2.controller.metric.LOCMetric;
import edu.wayne.cs.severe.redress2.entity.TypeDeclaration;
import edu.wayne.cs.severe.redress2.entity.refactoring.RefactoringOperation;

public class LOCPullUpFieldPF extends PullUpFieldPredFormula {

	@Override
	public HashMap<String, Double> predictMetrVal(RefactoringOperation ref,
			LinkedHashMap<String, LinkedHashMap<String, Double>> prevMetrics) {

		List<TypeDeclaration> srcClses = getSourceClasses(ref);

		HashMap<String, Double> predMetrs = new HashMap<String, Double>();
		for (TypeDeclaration cls : srcClses) {
			Double prevMetr = prevMetrics.get(cls.getQualifiedName()).get(
					getMetric().getMetricAcronym());
			predMetrs.put(cls.getQualifiedName(), prevMetr - 1);
		}

		TypeDeclaration tgtCls = getTargetClass(ref);
		predMetrs.put(
				tgtCls.getQualifiedName(),
				prevMetrics.get(tgtCls.getQualifiedName()).get(
						getMetric().getMetricAcronym()) + 1);

		return predMetrs;
	}

	public LOCMetric getMetric() {
		return new LOCMetric();
	}

}
