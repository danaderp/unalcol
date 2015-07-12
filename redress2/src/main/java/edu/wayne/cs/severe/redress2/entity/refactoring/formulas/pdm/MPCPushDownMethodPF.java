package edu.wayne.cs.severe.redress2.entity.refactoring.formulas.pdm;

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

public class MPCPushDownMethodPF extends PushDownPredFormula {

	@Override
	public HashMap<String, Double> predictMetrVal(RefactoringOperation ref,
			LinkedHashMap<String, LinkedHashMap<String, Double>> prevMetrics)
			throws Exception {

		TypeDeclaration srcCls = getSourceClass(ref);
		MethodDeclaration method = getMethod(ref);
		List<TypeDeclaration> tgtClses = getTargetClasses(ref);

		HashMap<String, Double> predMetrs = new HashMap<String, Double>();

		LinkedHashSet<String> callsMethod = MetricUtils.getMethodCallsMethod(
				srcCls, method.getObjName());

		Double prevMetr = prevMetrics.get(srcCls.getQualifiedName()).get(
				getMetric().getMetricAcronym());
		predMetrs.put(srcCls.getQualifiedName(),
				prevMetr - getDelta(srcCls, method, callsMethod));

		for (TypeDeclaration tgtCls : tgtClses) {
			predMetrs.put(
					tgtCls.getQualifiedName(),
					prevMetrics.get(tgtCls.getQualifiedName()).get(
							getMetric().getMetricAcronym())
							+ getDelta(tgtCls, method, callsMethod));
		}

		return predMetrs;
	}

	private Double getDelta(TypeDeclaration typeDcl, MethodDeclaration method,
			LinkedHashSet<String> callsMethodP) throws Exception {

		LinkedHashSet<String> callsMethod = new LinkedHashSet<String>(
				callsMethodP);

		if (typeDcl.getCompUnit() != null) {
			HashSet<String> methods = MetricUtils.getMethods(typeDcl);
			callsMethod.removeAll(methods);

			LinkedHashSet<String> callsNoMethod = MetricUtils
					.getMethodCallsNoMethod(typeDcl, method.getObjName());
			callsNoMethod.removeAll(methods);

			callsMethod.removeAll(callsNoMethod);
		}

		return (double) callsMethod.size();
	}

	@Override
	public CodeMetric getMetric() {
		return new MPCMetric();
	}

}
