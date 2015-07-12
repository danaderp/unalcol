package edu.wayne.cs.severe.redress2.entity.refactoring.formulas.mf;

import java.util.HashMap;
import java.util.LinkedHashMap;

import edu.wayne.cs.severe.redress2.controller.MetricUtils;
import edu.wayne.cs.severe.redress2.controller.metric.CodeMetric;
import edu.wayne.cs.severe.redress2.controller.metric.DACMetric;
import edu.wayne.cs.severe.redress2.entity.AttributeDeclaration;
import edu.wayne.cs.severe.redress2.entity.TypeDeclaration;
import edu.wayne.cs.severe.redress2.entity.refactoring.RefactoringOperation;

public class DACMoveFieldPF extends MoveFieldPredFormula {

	@Override
	public HashMap<String, Double> predictMetrVal(RefactoringOperation ref,
			LinkedHashMap<String, LinkedHashMap<String, Double>> prevMetrics)
			throws Exception {

		TypeDeclaration srcCls = getSourceClass(ref);
		AttributeDeclaration attr = getAttribute(ref);
		TypeDeclaration tgtCls = getTargetClass(ref);

		String fieldType = MetricUtils.getFieldType(srcCls, attr.getObjName());

		HashMap<String, Double> predMetrs = new HashMap<String, Double>();
		Double prevMetr = prevMetrics.get(srcCls.getQualifiedName()).get(
				getMetric().getMetricAcronym());
		predMetrs.put(srcCls.getQualifiedName(), prevMetr);

		LinkedHashMap<String, Double> metrs = prevMetrics.get(tgtCls
				.getQualifiedName());
		Double prevMetrTgt = (metrs != null ? metrs.get(getMetric()
				.getMetricAcronym()) : 0);
		Double deltaSrc = getDeltaSrc(tgtCls, fieldType);
		predMetrs.put(tgtCls.getQualifiedName(), prevMetrTgt + deltaSrc);

		return predMetrs;
	}

	// If Type(a_{k})\neq c_{s} then d_{k}=1 , otherwise, d_{k}=0 .
	private Double getDeltaSrc(TypeDeclaration typeDcl, String fieldType)
			throws Exception {

		if (fieldType != null) {
			String qName = typeDcl.getQualifiedName();
			if (("." + qName).endsWith("." + fieldType)) {
				return 0.0;
			}
		}

		return 1.0;
	}

	@Override
	public CodeMetric getMetric() {
		return new DACMetric();
	}

}
