package edu.wayne.cs.severe.redress2.entity.refactoring.formulas.ec;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;

import edu.wayne.cs.severe.redress2.controller.MetricUtils;
import edu.wayne.cs.severe.redress2.controller.metric.CodeMetric;
import edu.wayne.cs.severe.redress2.controller.metric.MPCMetric;
import edu.wayne.cs.severe.redress2.entity.MethodDeclaration;
import edu.wayne.cs.severe.redress2.entity.TypeDeclaration;
import edu.wayne.cs.severe.redress2.entity.refactoring.RefactoringOperation;
import edu.wayne.cs.severe.redress2.entity.refactoring.formulas.PredictionFormula;
import edu.wayne.cs.severe.redress2.entity.refactoring.formulas.mm.MPCMoveMethodPF;
import edu.wayne.cs.severe.redress2.entity.refactoring.formulas.mm.MoveMethodPredFormula;
import edu.wayne.cs.severe.redress2.utils.ExtractClassUtils;
import edu.wayne.cs.severe.redress2.utils.RefactoringUtils;

public class MPCExctractClassPF extends PredictionFormula {

	@Override
	public HashMap<String, Double> predictMetrVal(RefactoringOperation ref,
			LinkedHashMap<String, LinkedHashMap<String, Double>> prevMetrics)
			throws Exception {

		// ----------------------
		// parameters

		MoveMethodPredFormula preFormMM = new MPCMoveMethodPF();
		TypeDeclaration srcCls = preFormMM.getSourceClass(ref.getSubRefs().get(
				0));
		List<MethodDeclaration> methods = ExtractClassUtils.getMethodsToMove(
				ref, preFormMM);
		TypeDeclaration tgtCls = preFormMM.getTargetClass(ref.getSubRefs().get(
				0));
		CodeMetric metric = getMetric();

		// --------------------
		// source class

		LinkedHashSet<String> callsMethods = MetricUtils.getMethodCallsMethods(
				srcCls, methods);
		LinkedHashSet<String> callsMethodsP = new LinkedHashSet<String>(
				callsMethods);
		HashSet<String> methodsStr = MetricUtils.getMethods(srcCls);
		callsMethods.removeAll(methodsStr);

		HashMap<String, Double> predMetrs = new HashMap<String, Double>();

		Double prevMetr = prevMetrics.get(srcCls.getQualifiedName()).get(
				metric.getMetricAcronym());
		Double deltaSrc = RefactoringUtils.getMPCDeltaECSrc(srcCls, methods,
				callsMethods, ref, methodsStr);
		predMetrs.put(srcCls.getQualifiedName(), prevMetr + deltaSrc);

		// --------------------
		// target class

		predMetrs.put(tgtCls.getQualifiedName(), (double) callsMethodsP.size());

		return predMetrs;
	}

	@Override
	public CodeMetric getMetric() {
		return new MPCMetric();
	}

}
