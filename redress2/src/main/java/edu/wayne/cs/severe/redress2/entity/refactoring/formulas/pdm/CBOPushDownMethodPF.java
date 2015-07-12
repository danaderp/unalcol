package edu.wayne.cs.severe.redress2.entity.refactoring.formulas.pdm;

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

/**
 * @author ojcchar
 * 
 */
public class CBOPushDownMethodPF extends PushDownPredFormula {

	private HierarchyBuilder builder;

	public CBOPushDownMethodPF(HierarchyBuilder builder) {
		this.builder = builder;
	}

	@Override
	public HashMap<String, Double> predictMetrVal(RefactoringOperation ref,
			LinkedHashMap<String, LinkedHashMap<String, Double>> prevMetrics)
			throws Exception {

		// parameters of the refactoring
		TypeDeclaration srcCls = getSourceClass(ref);
		MethodDeclaration method = getMethod(ref);
		List<TypeDeclaration> tgtClses = getTargetClasses(ref);
		CodeMetric metric = getMetric();

		// predicted metrics
		HashMap<String, Double> predMetrs = new HashMap<String, Double>();

		List<TypeDeclaration> superClasses = builder.getParentClasses().get(
				srcCls.getQualifiedName());
		LinkedHashSet<String> classesUsed = MetricUtils.getUsedClassesByMethod(
				srcCls, superClasses, method);

		// metric of the superclass
		Double prevMetr = prevMetrics.get(srcCls.getQualifiedName()).get(
				metric.getMetricAcronym());
		Double deltaSrc = RefactoringUtils.getCBODelta(srcCls, method,
				classesUsed, superClasses);
		predMetrs.put(srcCls.getQualifiedName(), prevMetr - deltaSrc);

		LinkedHashSet<String> classesUsedVars = MetricUtils
				.getClassesUsedByVarsInMethod(srcCls, method, true);

		// for each subclass
		for (TypeDeclaration tgtCls : tgtClses) {

			Double prevMetrTgt = prevMetrics.get(tgtCls.getQualifiedName())
					.get(metric.getMetricAcronym());

			List<TypeDeclaration> superClassesTgt = builder.getParentClasses()
					.get(tgtCls.getQualifiedName());

			Double deltaTgt = RefactoringUtils.getCBODelta(tgtCls, method,
					classesUsedVars, superClassesTgt);
			Double deltaTgt2 = RefactoringUtils.getCBODelta2(srcCls, method,
					tgtCls);

			predMetrs.put(tgtCls.getQualifiedName(), prevMetrTgt + deltaTgt
					+ deltaTgt2);
		}

		return predMetrs;
	}

	/*
	 * private Double getDelta(TypeDeclaration tgtCls, MethodDeclaration method,
	 * TypeDeclaration srcCls) throws Exception {
	 * 
	 * LinkedHashSet<String> fields = MetricUtils.getFields(srcCls);
	 * 
	 * LinkedHashSet<String> methodCallsTgt = MetricUtils.getMethodCalls(
	 * tgtCls, tgtCls.getCompUnit().getSrcFile());
	 * 
	 * boolean usingField = false; for (String field : fields) { int numMeth =
	 * MetricUtils.getNumberOfMethodsUsingString(tgtCls, field); if (numMeth >
	 * 0) { usingField = true; break; } }
	 * 
	 * if (!usingField && methodCallsTgt.size() == 1) { for (String methodCall :
	 * methodCallsTgt) { if (method.getObjName().equals(methodCall)) { return
	 * 1.0; } } }
	 * 
	 * return 0.0;
	 * 
	 * }
	 */

	@Override
	public CodeMetric getMetric() {
		return new CBOMetric();
	}

}
