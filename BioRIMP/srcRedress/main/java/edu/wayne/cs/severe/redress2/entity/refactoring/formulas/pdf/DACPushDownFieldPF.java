package edu.wayne.cs.severe.redress2.entity.refactoring.formulas.pdf;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import edu.wayne.cs.severe.redress2.controller.MetricUtils;
import edu.wayne.cs.severe.redress2.controller.metric.CodeMetric;
import edu.wayne.cs.severe.redress2.controller.metric.DACMetric;
import edu.wayne.cs.severe.redress2.entity.AttributeDeclaration;
import edu.wayne.cs.severe.redress2.entity.TypeDeclaration;
import edu.wayne.cs.severe.redress2.entity.refactoring.RefactoringOperation;

public class DACPushDownFieldPF extends PushDownFieldPredFormua {

	@Override
	public HashMap<String, Double> predictMetrVal(RefactoringOperation ref,
			LinkedHashMap<String, LinkedHashMap<String, Double>> prevMetrics)
			throws Exception {

		TypeDeclaration srcCls = getSourceClass(ref);
		AttributeDeclaration attr = getAttribute(ref);
		List<TypeDeclaration> tgtClses = getTargetClasses(ref);
		CodeMetric metric = getMetric();

		String fieldType = MetricUtils.getFieldType(srcCls, attr.getObjName());

		HashMap<String, Double> predMetrs = new HashMap<String, Double>();
		Double prevMetr = prevMetrics.get(srcCls.getQualifiedName()).get(
				metric.getMetricAcronym());
		predMetrs.put(srcCls.getQualifiedName(),
				prevMetr - getDeltaSrc(srcCls, fieldType));

		for (TypeDeclaration tgtCls : tgtClses) {
			Double prevMetrTgt = prevMetrics.get(tgtCls.getQualifiedName())
					.get(metric.getMetricAcronym());
			predMetrs.put(tgtCls.getQualifiedName(),
					prevMetrTgt + getDeltaSrc(tgtCls, fieldType));
		}

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

	public CodeMetric getMetric() {
		return new DACMetric();
	}

}
