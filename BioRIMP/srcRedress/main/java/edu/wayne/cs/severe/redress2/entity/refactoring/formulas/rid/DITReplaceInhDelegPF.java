package edu.wayne.cs.severe.redress2.entity.refactoring.formulas.rid;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import edu.wayne.cs.severe.redress2.controller.HierarchyBuilder;
import edu.wayne.cs.severe.redress2.controller.metric.CodeMetric;
import edu.wayne.cs.severe.redress2.controller.metric.DITMetric;
import edu.wayne.cs.severe.redress2.entity.TypeDeclaration;
import edu.wayne.cs.severe.redress2.entity.refactoring.RefactoringOperation;

public class DITReplaceInhDelegPF extends ReplaceInherDelegPredFormula {

	private HierarchyBuilder builder;

	public DITReplaceInhDelegPF(HierarchyBuilder builder) {
		this.builder = builder;
	}

	@Override
	public HashMap<String, Double> predictMetrVal(RefactoringOperation ref,
			LinkedHashMap<String, LinkedHashMap<String, Double>> prevMetrics)
			throws Exception {

		TypeDeclaration srcCls = getSourceClass(ref);
		TypeDeclaration tgtCls = getTargetClass(ref);
		DITMetric metric = (DITMetric) getMetric();

		HashMap<String, Double> predMetrs = new HashMap<String, Double>();

		Double delta = getDelta(srcCls, tgtCls, metric);
		predMetrs.put(srcCls.getQualifiedName(), delta);

		LinkedHashMap<String, Double> prevMetrsTgt = prevMetrics.get(tgtCls
				.getQualifiedName());
		Double prevMetrTgt = prevMetrsTgt == null ? 0 : prevMetrsTgt.get(metric
				.getMetricAcronym());
		predMetrs.put(tgtCls.getQualifiedName(), prevMetrTgt);

		return predMetrs;
	}

	private Double getDelta(TypeDeclaration srcCls, TypeDeclaration tgtCls,
			DITMetric metric) throws Exception {
		HashMap<String, List<TypeDeclaration>> parentClasses = builder
				.getParentClasses();

		List<TypeDeclaration> superClasses = parentClasses.get(srcCls
				.getQualifiedName());

		if (superClasses == null || superClasses.isEmpty()) {
			return 0.0;
		}
		if (superClasses.size() == 1) {
			return 0.0;
		}

		superClasses.remove(tgtCls);

		if (superClasses != null && superClasses.size() != 0) {
			// get the dit for every class
			List<Double> ditsClasses = metric.getDitsClasses(superClasses);

			// choose the greatest
			return Collections.max(ditsClasses);
		}

		return 0.0;
	}

	@Override
	public CodeMetric getMetric() {
		return new DITMetric(null, builder);
	}

}
