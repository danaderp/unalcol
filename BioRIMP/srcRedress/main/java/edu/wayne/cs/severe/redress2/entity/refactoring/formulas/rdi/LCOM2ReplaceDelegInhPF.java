package edu.wayne.cs.severe.redress2.entity.refactoring.formulas.rdi;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;

import edu.wayne.cs.severe.redress2.controller.MetricUtils;
import edu.wayne.cs.severe.redress2.controller.metric.CodeMetric;
import edu.wayne.cs.severe.redress2.controller.metric.LCOM2Metric;
import edu.wayne.cs.severe.redress2.entity.TypeDeclaration;
import edu.wayne.cs.severe.redress2.entity.refactoring.RefactoringOperation;

public class LCOM2ReplaceDelegInhPF extends ReplaceDelegInherPredFormula {

	@Override
	public HashMap<String, Double> predictMetrVal(RefactoringOperation ref,
			LinkedHashMap<String, LinkedHashMap<String, Double>> prevMetrics)
			throws Exception {

		TypeDeclaration srcCls = getSourceClass(ref);
		TypeDeclaration tgtCls = getTargetClass(ref);

		HashMap<String, Double> predMetrs = new HashMap<String, Double>();

		LinkedHashSet<String> fields = MetricUtils.getFields(srcCls);

		String attribute = null;
		for (String field : fields) {
			String fieldType = MetricUtils.getFieldType(srcCls, field);
			String qName = srcCls.getQualifiedName();
			if (("." + qName).endsWith("." + fieldType)) {
				attribute = field;
				break;
			}
		}

		predMetrs.put(srcCls.getQualifiedName(),
				getValLCOM2(srcCls, attribute, -1));


		LinkedHashMap<String, Double> prevMetrsTgt = prevMetrics.get(tgtCls
				.getQualifiedName());
		Double prevMetrTgt = prevMetrsTgt == null ? 0 : prevMetrsTgt
				.get(getMetric().getMetricAcronym());
		predMetrs.put(tgtCls.getQualifiedName(), prevMetrTgt);

		return predMetrs;
	}

	private Double getValLCOM2(TypeDeclaration typeDcl, String attribute,
			int mult) throws Exception {

		File compUnitFile = typeDcl.getCompUnit().getSrcFile();
		HashSet<String> fields = MetricUtils.getFields(typeDcl);

		double numFieldUsage = 0.0;
		for (String field : fields) {
			int numField = MetricUtils.getNumberOfMethodsUsingString(typeDcl,
					field);
			numFieldUsage += numField;
		}

		double numFields = fields.size() - 1;
		Double numMethods = MetricUtils.getNumberOfMethods(typeDcl,
				compUnitFile);

		int numAttrMethod = MetricUtils.getNumberOfMethodsUsingString(typeDcl,
				attribute);

		double metric = (numMethods == 0 || numFields == 0) ? 0
				: 1 - ((numFieldUsage + mult * numAttrMethod) / (numMethods * numFields));

		return metric;
	}

	@Override
	public CodeMetric getMetric() {
		return new LCOM2Metric();
	}

}
