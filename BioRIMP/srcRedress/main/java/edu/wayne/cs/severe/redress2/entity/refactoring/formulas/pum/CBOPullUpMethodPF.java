package edu.wayne.cs.severe.redress2.entity.refactoring.formulas.pum;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;

import edu.wayne.cs.severe.redress2.controller.HierarchyBuilder;
import edu.wayne.cs.severe.redress2.controller.MetricUtils;
import edu.wayne.cs.severe.redress2.controller.metric.CBOMetric;
import edu.wayne.cs.severe.redress2.controller.metric.CodeMetric;
import edu.wayne.cs.severe.redress2.entity.ClassField;
import edu.wayne.cs.severe.redress2.entity.MethodDeclaration;
import edu.wayne.cs.severe.redress2.entity.TypeDeclaration;
import edu.wayne.cs.severe.redress2.entity.refactoring.RefactoringOperation;
import edu.wayne.cs.severe.redress2.utils.PullUpMethodUtils;
import edu.wayne.cs.severe.redress2.utils.RefactoringUtils;

/**
 * Prediction formula for Pull Up Method and the CBO metric
 * 
 * @author ojcchar
 * 
 */
public class CBOPullUpMethodPF extends PullUpMethodPredFormula {

	private HierarchyBuilder builder;

	public CBOPullUpMethodPF(HierarchyBuilder builder) {
		this.builder = builder;
	}

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

		// get the fields and methods of the target class
//		LinkedHashSet<String> fields = MetricUtils.getFields(tgtCls);
//		LinkedHashSet<String> methods = MetricUtils.getMethods(tgtCls);

		List<TypeDeclaration> superClasses = builder.getParentClasses().get(
				srcClses.get(0).getQualifiedName());
		LinkedHashSet<String> classesUsedVars = MetricUtils
				.getClassesUsedByVarsInMethod(srcClses.get(0), method, true);
//		LinkedHashSet<String> classesUsed = MetricUtils.getUsedClassesByMethod(
//				srcClses.get(0), superClasses, method);
		List<ClassField> usedFieldsSrc = MetricUtils.getFieldsUsedByMethod(
				srcClses.get(0), method);

		// for every subclass
		for (TypeDeclaration srcCls : srcClses) {

			// get the previous metric
			Double prevMetr = prevMetrics.get(srcCls.getQualifiedName()).get(
					metric.getMetricAcronym());

			// calculate the delta and set the new metric value
			// Double deltaSrc = getDeltaSrc(method, srcCls, fields, methods);

			Double deltaSrc = RefactoringUtils.getCBODelta(srcCls, method,
					classesUsedVars, superClasses);
			Double deltaSrc2 = RefactoringUtils.getCBODelta3(tgtCls, method,
					srcCls);
			// delta of the fields used by the method
			double deltaFieldsUsed = PullUpMethodUtils.getDeltaFieldsUsed2(
					usedFieldsSrc, srcClses.get(0), method);
			predMetrs.put(srcCls.getQualifiedName(), prevMetr - deltaSrc
					+ deltaSrc2 + deltaFieldsUsed);
		}

		List<TypeDeclaration> superClassesTgt = builder.getParentClasses().get(
				tgtCls.getQualifiedName());
		Double deltaTgt = RefactoringUtils.getCBODelta(tgtCls, method,
				classesUsedVars, superClassesTgt);

		// the metric of the target superclass does not change
		Double prevMetricTgt = prevMetrics.get(tgtCls.getQualifiedName()).get(
				metric.getMetricAcronym());
		predMetrs.put(tgtCls.getQualifiedName(), prevMetricTgt + deltaTgt);

		return predMetrs;
	}

	// /**
	// * Get the delta for the source classes
	// *
	// * @param method
	// * method to be pulled up
	// * @param srcCls
	// * source subclass
	// * @param fieldsTgt
	// * fields of the superclass
	// * @param methodsTgt
	// * methods of the superclass
	// * @return the delta
	// * @throws Exception
	// * if an error occurs
	// */
	// private Double getDeltaSrc(MethodDeclaration method,
	// TypeDeclaration srcCls, LinkedHashSet<String> fieldsTgt,
	// LinkedHashSet<String> methodsTgt) throws Exception {
	//
	// // is the source subclass using a field of the target super class?
	// boolean usingField = false;
	// for (String field : fieldsTgt) {
	// int numMeth = MetricUtils.getNumberOfMethodsUsingString(srcCls,
	// field);
	// if (numMeth > 0) {
	// usingField = true;
	// break;
	// }
	// }
	//
	// // is the source subclass using a method of the target super class?
	// boolean usingMethod = false;
	// for (String methodTgt : methodsTgt) {
	// int numMeth = MetricUtils.getNumberOfMethodsUsingString(srcCls,
	// methodTgt);
	// if (numMeth > 0) {
	// usingMethod = true;
	// break;
	// }
	// }
	//
	// // if the source class is not using any method or field of the target
	// // class
	// if (!usingMethod && !usingField) {
	//
	// // get the method calls of the other methods apart from the method
	// // to be pulled up, in the source class
	// LinkedHashSet<String> methodCallsSrc = MetricUtils
	// .getMethodCallsNoMethod(srcCls, method.getObjName());
	//
	// // and check the method to be pulled up is called
	// if (methodCallsSrc.contains(method.getObjName())) {
	// return 1.0;
	// }
	// }
	//
	// return 0.0;
	//
	// }

	@Override
	public CodeMetric getMetric() {
		return new CBOMetric();
	}

}
