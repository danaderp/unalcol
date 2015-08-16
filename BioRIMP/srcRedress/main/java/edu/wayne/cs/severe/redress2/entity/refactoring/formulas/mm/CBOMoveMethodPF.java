package edu.wayne.cs.severe.redress2.entity.refactoring.formulas.mm;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;

import edu.wayne.cs.severe.redress2.controller.HierarchyBuilder;
import edu.wayne.cs.severe.redress2.controller.MetricUtils;
import edu.wayne.cs.severe.redress2.controller.metric.CBOMetric;
import edu.wayne.cs.severe.redress2.controller.metric.CodeMetric;
import edu.wayne.cs.severe.redress2.entity.MethodDeclaration;
import edu.wayne.cs.severe.redress2.entity.TypeDeclaration;
import edu.wayne.cs.severe.redress2.entity.refactoring.RefactoringOperation;
import edu.wayne.cs.severe.redress2.utils.RefactoringUtils;

public class CBOMoveMethodPF extends MoveMethodPredFormula {

	private HierarchyBuilder builder;

	public CBOMoveMethodPF(HierarchyBuilder builder) {
		this.builder = builder;
	}

	@Override
	public HashMap<String, Double> predictMetrVal(RefactoringOperation ref,
			LinkedHashMap<String, LinkedHashMap<String, Double>> prevMetrics)
			throws Exception {

		TypeDeclaration srcCls = getSourceClass(ref);
		MethodDeclaration method = getMethod(ref);
		TypeDeclaration tgtCls = getTargetClass(ref);
		CodeMetric metric = getMetric();

		List<TypeDeclaration> superClasses = builder.getParentClasses().get(
				srcCls.getQualifiedName());
		LinkedHashSet<String> classesUsed = MetricUtils.getUsedClassesByMethod(
				srcCls, superClasses, method);

		HashMap<String, Double> predMetrs = new HashMap<String, Double>();

		Double prevMetr = prevMetrics.get(srcCls.getQualifiedName()).get(
				metric.getMetricAcronym());
		Double deltaSrc = RefactoringUtils.getCBODelta(srcCls, method,
				classesUsed, superClasses);
		predMetrs.put(srcCls.getQualifiedName(), prevMetr - deltaSrc);

		LinkedHashMap<String, Double> tgtMetrs = prevMetrics.get(tgtCls
				.getQualifiedName());
		Double prevMetrTgt = tgtMetrs == null ? 0.0 : tgtMetrs.get(metric
				.getMetricAcronym());

		List<TypeDeclaration> superClassesTgt = builder.getParentClasses().get(
				tgtCls.getQualifiedName());
		Double deltaTgt = RefactoringUtils.getCBODelta(tgtCls, method,
				classesUsed, superClassesTgt);
		predMetrs.put(tgtCls.getQualifiedName(), prevMetrTgt + deltaTgt);

		return predMetrs;
	}

	@Override
	public CodeMetric getMetric() {
		return new CBOMetric();
	}

}
