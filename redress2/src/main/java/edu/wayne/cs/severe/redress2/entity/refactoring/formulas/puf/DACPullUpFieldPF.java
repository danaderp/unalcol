package edu.wayne.cs.severe.redress2.entity.refactoring.formulas.puf;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import edu.wayne.cs.severe.redress2.controller.MetricUtils;
import edu.wayne.cs.severe.redress2.controller.metric.CodeMetric;
import edu.wayne.cs.severe.redress2.controller.metric.DACMetric;
import edu.wayne.cs.severe.redress2.entity.AttributeDeclaration;
import edu.wayne.cs.severe.redress2.entity.TypeDeclaration;
import edu.wayne.cs.severe.redress2.entity.refactoring.RefactoringOperation;

public class DACPullUpFieldPF extends PullUpFieldPredFormula {

	@Override
	public HashMap<String, Double> predictMetrVal(RefactoringOperation ref,
			LinkedHashMap<String, LinkedHashMap<String, Double>> prevMetrics)
			throws Exception {

		List<TypeDeclaration> srcClses = getSourceClasses(ref);
		AttributeDeclaration attr = getAttribute(ref);
		TypeDeclaration tgtCls = getTargetClass(ref);

		String fieldType = MetricUtils.getFieldType(srcClses.get(0),
				attr.getObjName());

		HashMap<String, Double> predMetrs = new HashMap<String, Double>();
		for (TypeDeclaration srcCls : srcClses) {
			Double prevMetr = prevMetrics.get(srcCls.getQualifiedName()).get(
					getMetric().getMetricAcronym());
			predMetrs.put(srcCls.getQualifiedName(),
					prevMetr - getDeltaSrc(srcCls, fieldType));
		}

		predMetrs.put(
				tgtCls.getQualifiedName(),
				prevMetrics.get(tgtCls.getQualifiedName()).get(
						getMetric().getMetricAcronym())
						+ getDeltaSrc(tgtCls, fieldType));

		return predMetrs;
	}

	// If Type(a_{k})\neq c_{s} then d_{k}=1 , otherwise, d_{k}=0 .
	private Double getDeltaSrc(TypeDeclaration srcCls, String fieldType)
			throws Exception {

		if (fieldType != null) {
			String qName = srcCls.getQualifiedName();
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
