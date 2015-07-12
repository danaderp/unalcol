package edu.wayne.cs.severe.redress2.entity.refactoring.formulas.mf;

import java.util.HashMap;
import java.util.LinkedHashMap;

import edu.wayne.cs.severe.redress2.controller.metric.CodeMetric;
import edu.wayne.cs.severe.redress2.controller.metric.NOMMetric;
import edu.wayne.cs.severe.redress2.entity.AttributeDeclaration;
import edu.wayne.cs.severe.redress2.entity.ClassField;
import edu.wayne.cs.severe.redress2.entity.TypeDeclaration;
import edu.wayne.cs.severe.redress2.entity.refactoring.RefactoringOperation;
import edu.wayne.cs.severe.redress2.utils.MoveFieldUtils;

public class NOMMoveFieldPF extends MoveFieldPredFormula {

	@Override
	public HashMap<String, Double> predictMetrVal(RefactoringOperation ref,
			LinkedHashMap<String, LinkedHashMap<String, Double>> prevMetrics)
			throws Exception {

		TypeDeclaration srcCls = getSourceClass(ref);
		AttributeDeclaration attr = getAttribute(ref);
		TypeDeclaration tgtCls = getTargetClass(ref);

		HashMap<String, Double> predMetrs = new HashMap<String, Double>();

		Double prevMetr = prevMetrics.get(srcCls.getQualifiedName()).get(
				getMetric().getMetricAcronym());
		double delta = MoveFieldUtils.getDeltaFieldsUsed(
				new ClassField(attr.getObjName(), null), srcCls);
		predMetrs.put(srcCls.getQualifiedName(), prevMetr + delta);

		LinkedHashMap<String, Double> metrs = prevMetrics.get(tgtCls
				.getQualifiedName());
		Double prevMetrTgt = (metrs != null ? metrs.get(getMetric()
				.getMetricAcronym()) : 0);
		predMetrs.put(tgtCls.getQualifiedName(), prevMetrTgt + 2);

		return predMetrs;
	}

	@Override
	public CodeMetric getMetric() {
		return new NOMMetric();
	}

}
