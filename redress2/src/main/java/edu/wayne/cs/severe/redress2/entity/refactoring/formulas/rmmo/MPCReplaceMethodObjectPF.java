package edu.wayne.cs.severe.redress2.entity.refactoring.formulas.rmmo;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;

import edu.wayne.cs.severe.redress2.controller.MetricUtils;
import edu.wayne.cs.severe.redress2.controller.metric.CodeMetric;
import edu.wayne.cs.severe.redress2.controller.metric.MPCMetric;
import edu.wayne.cs.severe.redress2.entity.MethodDeclaration;
import edu.wayne.cs.severe.redress2.entity.TypeDeclaration;
import edu.wayne.cs.severe.redress2.entity.refactoring.RefactoringOperation;

public class MPCReplaceMethodObjectPF extends ReplaceMethodObjectPredFormula {

	@Override
	public HashMap<String, Double> predictMetrVal(RefactoringOperation ref,
			LinkedHashMap<String, LinkedHashMap<String, Double>> prevMetrics)
			throws Exception {

		TypeDeclaration srcCls = getSourceClass(ref);
		MethodDeclaration method = getMethod(ref);
		TypeDeclaration tgtCls = getTargetClass(ref);

		LinkedHashSet<String> callsMethod = MetricUtils.getMethodCallsMethod(
				srcCls, method.getObjName());

		HashMap<String, Double> predMetrs = new HashMap<String, Double>();

		Double prevMetr = prevMetrics.get(srcCls.getQualifiedName()).get(
				getMetric().getMetricAcronym());
		predMetrs.put(srcCls.getQualifiedName(),
				prevMetr - getDelta(srcCls, method, callsMethod) + 2);

		predMetrs.put(tgtCls.getQualifiedName(), (double) callsMethod.size());

		return predMetrs;
	}

	private Double getDelta(TypeDeclaration typeDcl, MethodDeclaration method,
			LinkedHashSet<String> callsMethodP) throws Exception {

		LinkedHashSet<String> callsMethod = new LinkedHashSet<String>(
				callsMethodP);
		HashSet<String> methods = MetricUtils.getMethods(typeDcl);
		callsMethod.removeAll(methods);

		LinkedHashSet<String> callsNoMethod = MetricUtils
				.getMethodCallsNoMethod(typeDcl, method.getObjName());
		callsNoMethod.removeAll(methods);

		callsMethod.removeAll(callsNoMethod);
		return (double) callsMethod.size();
	}

	@Override
	public CodeMetric getMetric() {
		return new MPCMetric();
	}

}
