package edu.wayne.cs.severe.redress2.entity.refactoring.formulas.rmmo;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;

import edu.wayne.cs.severe.redress2.controller.MetricUtils;
import edu.wayne.cs.severe.redress2.controller.metric.CodeMetric;
import edu.wayne.cs.severe.redress2.controller.metric.RFCMetric;
import edu.wayne.cs.severe.redress2.entity.MethodDeclaration;
import edu.wayne.cs.severe.redress2.entity.TypeDeclaration;
import edu.wayne.cs.severe.redress2.entity.refactoring.RefactoringOperation;

public class RFCReplaceMethodObjectPF extends ReplaceMethodObjectPredFormula {

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
				prevMetr - getDelta(srcCls, method, callsMethod));

		predMetrs.put(tgtCls.getQualifiedName(), 1.0 + callsMethod.size());

		return predMetrs;
	}

	private Double getDelta(TypeDeclaration typeDcl, MethodDeclaration method,
			LinkedHashSet<String> callsMethodP) throws Exception {

		LinkedHashSet<String> methods = MetricUtils.getMethodsUsingString(
				typeDcl, method.getObjName());
		methods.remove(method.getObjName());

		LinkedHashSet<String> callsMethod = new LinkedHashSet<String>(
				callsMethodP);
		LinkedHashSet<String> callsNoMethod = MetricUtils
				.getMethodCallsNoMethod(typeDcl, method.getObjName());
		callsMethod.removeAll(callsNoMethod);
		return (-2.0 + callsMethod.size());
	}

	@Override
	public CodeMetric getMetric() {
		return new RFCMetric();
	}

}
