package edu.wayne.cs.severe.redress2.entity.refactoring.formulas.mf;

import java.util.HashMap;
import java.util.LinkedHashMap;

import edu.wayne.cs.severe.redress2.controller.metric.CodeMetric;
import edu.wayne.cs.severe.redress2.controller.metric.MPCMetric;
import edu.wayne.cs.severe.redress2.entity.TypeDeclaration;
import edu.wayne.cs.severe.redress2.entity.refactoring.RefactoringOperation;

public class MPCMoveFieldPF extends MoveFieldPredFormula {

	@Override
	public HashMap<String, Double> predictMetrVal(RefactoringOperation ref,
			LinkedHashMap<String, LinkedHashMap<String, Double>> prevMetrics)
			throws Exception {

		TypeDeclaration srcCls = getSourceClass(ref);
		// AttributeDeclaration attr = getAttribute(ref);
		TypeDeclaration tgtCls = getTargetClass(ref);

		HashMap<String, Double> predMetrs = new HashMap<String, Double>();

		Double prevMetr = prevMetrics.get(srcCls.getQualifiedName()).get(
				getMetric().getMetricAcronym());
		predMetrs.put(srcCls.getQualifiedName(), prevMetr + 2);

		LinkedHashMap<String, Double> metrs = prevMetrics.get(tgtCls
				.getQualifiedName());
		predMetrs
				.put(tgtCls.getQualifiedName(),
						(metrs != null ? metrs.get(getMetric()
								.getMetricAcronym()) : 0));

		return predMetrs;
	}

	// private Double getDelta(TypeDeclaration srcCls, AttributeDeclaration
	// attr)
	// throws Exception {
	//
	// LinkedHashSet<String> calls = MetricUtils.getMethodCalls(srcCls);
	//
	// String objName = attr.getObjName();
	// String suffix = objName.substring(0, 1).toUpperCase()
	// + objName.substring(1, objName.length());
	// String setter = "set" + suffix;
	// String getter = "get" + suffix;
	//
	// boolean remove = calls.remove(setter);
	// boolean remove2 = calls.remove(getter);
	// if (remove && remove2) {
	// return 2.0;
	// } else if (remove || remove2) {
	// return 1.0;
	// }
	//
	// return 0.0;
	// }

	@Override
	public CodeMetric getMetric() {
		return new MPCMetric();
	}

}
