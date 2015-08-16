package edu.wayne.cs.severe.redress2.entity.refactoring.formulas.pum;

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

/**
 * Prediction formula for Pull Up Method and the MPC metric
 * 
 * @author ojcchar
 * 
 */
public class MPCPullUpMethodPF extends PullUpMethodPredFormula {

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

		LinkedHashSet<String> callsMethod = MetricUtils.getMethodCallsMethod(
				srcClses.get(0), method.getObjName());

		for (TypeDeclaration srcCls : srcClses) {
			Double prevMetr = prevMetrics.get(srcCls.getQualifiedName()).get(
					metric.getMetricAcronym());
			predMetrs.put(srcCls.getQualifiedName(),
					prevMetr - getDelta(srcCls, method, callsMethod));
		}

		Double prevMetrTgt = prevMetrics.get(tgtCls.getQualifiedName()).get(
				metric.getMetricAcronym());
		predMetrs.put(tgtCls.getQualifiedName(),
				prevMetrTgt + getDelta(tgtCls, method, callsMethod));

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
