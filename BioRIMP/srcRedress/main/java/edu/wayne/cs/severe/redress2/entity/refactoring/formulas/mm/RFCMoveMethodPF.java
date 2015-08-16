package edu.wayne.cs.severe.redress2.entity.refactoring.formulas.mm;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;

import edu.wayne.cs.severe.redress2.controller.MetricUtils;
import edu.wayne.cs.severe.redress2.controller.metric.CodeMetric;
import edu.wayne.cs.severe.redress2.controller.metric.RFCMetric;
import edu.wayne.cs.severe.redress2.entity.MethodDeclaration;
import edu.wayne.cs.severe.redress2.entity.TypeDeclaration;
import edu.wayne.cs.severe.redress2.entity.refactoring.RefactoringOperation;

public class RFCMoveMethodPF extends MoveMethodPredFormula {

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

		LinkedHashMap<String, Double> prevMetrsTgt = prevMetrics.get(tgtCls
				.getQualifiedName());
		Double prevMetrTgt = prevMetrsTgt == null ? 0 : prevMetrsTgt
				.get(getMetric().getMetricAcronym());
		predMetrs.put(tgtCls.getQualifiedName(),
				prevMetrTgt + getDelta(tgtCls, method, callsMethod));
		
		return predMetrs;
	}

	private Double getDelta(TypeDeclaration typeDcl, MethodDeclaration method,
			LinkedHashSet<String> callsMethodP) throws Exception {
		LinkedHashSet<String> callsMethod = new LinkedHashSet<String>(
				callsMethodP);

		LinkedHashSet<String> callsNoMethod = new LinkedHashSet<String>();
		if (typeDcl.getCompUnit() != null) {
			callsNoMethod = MetricUtils.getMethodCallsNoMethod(typeDcl,
					method.getObjName());
		}
		callsMethod.removeAll(callsNoMethod);
		return (1.0 + callsMethod.size());
	}

	@Override
	public CodeMetric getMetric() {
		return new RFCMetric();
	}

}
