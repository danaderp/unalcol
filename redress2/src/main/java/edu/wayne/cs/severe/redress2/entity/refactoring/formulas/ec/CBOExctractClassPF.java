package edu.wayne.cs.severe.redress2.entity.refactoring.formulas.ec;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;

import edu.wayne.cs.severe.redress2.controller.HierarchyBuilder;
import edu.wayne.cs.severe.redress2.controller.MetricUtils;
import edu.wayne.cs.severe.redress2.controller.metric.CBOMetric;
import edu.wayne.cs.severe.redress2.controller.metric.CodeMetric;
import edu.wayne.cs.severe.redress2.entity.AttributeDeclaration;
import edu.wayne.cs.severe.redress2.entity.MethodDeclaration;
import edu.wayne.cs.severe.redress2.entity.TypeDeclaration;
import edu.wayne.cs.severe.redress2.entity.refactoring.RefactoringOperation;
import edu.wayne.cs.severe.redress2.entity.refactoring.formulas.PredictionFormula;
import edu.wayne.cs.severe.redress2.entity.refactoring.formulas.mm.CBOMoveMethodPF;
import edu.wayne.cs.severe.redress2.entity.refactoring.formulas.mm.MoveMethodPredFormula;
import edu.wayne.cs.severe.redress2.utils.ExtractClassUtils;
import edu.wayne.cs.severe.redress2.utils.RefactoringUtils;

public class CBOExctractClassPF extends PredictionFormula {

	private HierarchyBuilder builder;

	public CBOExctractClassPF(HierarchyBuilder builder) {
		this.builder = builder;
	}

	@Override
	public HashMap<String, Double> predictMetrVal(RefactoringOperation ref,
			LinkedHashMap<String, LinkedHashMap<String, Double>> prevMetrics)
			throws Exception {

		// ----------------------
		// parameters

		MoveMethodPredFormula preFormMM = new CBOMoveMethodPF(builder);
		TypeDeclaration srcCls = preFormMM.getSourceClass(ref.getSubRefs().get(
				0));
		List<MethodDeclaration> methods = ExtractClassUtils.getMethodsToMove(ref, preFormMM);
		TypeDeclaration tgtCls = preFormMM.getTargetClass(ref.getSubRefs().get(
				0));
		CodeMetric metric = getMetric();

		// --------------------
		// source class

		List<TypeDeclaration> superClasses = builder.getParentClasses().get(
				srcCls.getQualifiedName());
		LinkedHashSet<String> classesUsed = MetricUtils
				.getUsedClassesByMethods(srcCls, superClasses, methods);

		HashMap<String, Double> predMetrs = new HashMap<String, Double>();

		Double prevMetr = prevMetrics.get(srcCls.getQualifiedName()).get(
				metric.getMetricAcronym());
		List<MethodDeclaration> otherMethods = MetricUtils.getOtherMethods(
				methods, srcCls);
		Double deltaSrc = RefactoringUtils.getCBODeltaEC(srcCls, methods,
				classesUsed, superClasses, otherMethods);
		predMetrs.put(srcCls.getQualifiedName(), prevMetr - deltaSrc + 1);

		// --------------------
		// target class

		LinkedHashMap<String, Double> tgtMetrs = prevMetrics.get(tgtCls
				.getQualifiedName());
		Double prevMetrTgt = tgtMetrs == null ? 0.0 : tgtMetrs.get(metric
				.getMetricAcronym());

		// List<AttributeDeclaration> attrs = getAttributes(ref,
		// new CBOMoveFieldPF());
		Double deltaSrcClassUsage = 0.0;
		predMetrs.put(tgtCls.getQualifiedName(),
				prevMetrTgt + classesUsed.size() + deltaSrcClassUsage);

		return predMetrs;
	}

	private Double getDeltaSrcClassUsage(TypeDeclaration srcCls,
			List<MethodDeclaration> otherMethods,
			List<MethodDeclaration> methods, List<AttributeDeclaration> attrs)
			throws Exception {

		for (MethodDeclaration otherMethod : otherMethods) {
			for (AttributeDeclaration attr : attrs) {
				Integer num = MetricUtils.getStringNumUsagesInMethod(srcCls,
						otherMethod, attr.getObjName());
				if (num > 0) {
					return 1.0;
				}
			}

			for (MethodDeclaration method : methods) {
				Integer num = MetricUtils.getStringNumUsagesInMethod(srcCls,
						otherMethod, method.getObjName());
				if (num > 0) {
					return 1.0;
				}
			}
		}
		return 0.0;
	}

	@Override
	public CodeMetric getMetric() {
		return new CBOMetric();
	}

}
