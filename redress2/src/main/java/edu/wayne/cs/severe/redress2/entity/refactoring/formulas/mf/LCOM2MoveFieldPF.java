package edu.wayne.cs.severe.redress2.entity.refactoring.formulas.mf;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;

import edu.wayne.cs.severe.redress2.controller.MetricUtils;
import edu.wayne.cs.severe.redress2.controller.metric.CodeMetric;
import edu.wayne.cs.severe.redress2.controller.metric.LCOM2Metric;
import edu.wayne.cs.severe.redress2.entity.AttributeDeclaration;
import edu.wayne.cs.severe.redress2.entity.ClassField;
import edu.wayne.cs.severe.redress2.entity.TypeDeclaration;
import edu.wayne.cs.severe.redress2.entity.refactoring.RefactoringOperation;
import edu.wayne.cs.severe.redress2.utils.MoveFieldUtils;

public class LCOM2MoveFieldPF extends MoveFieldPredFormula {

	@Override
	public HashMap<String, Double> predictMetrVal(RefactoringOperation ref,
			LinkedHashMap<String, LinkedHashMap<String, Double>> prevMetrics)
			throws Exception {

		TypeDeclaration srcCls = getSourceClass(ref);
		AttributeDeclaration attr = getAttribute(ref);
		TypeDeclaration tgtCls = getTargetClass(ref);

		HashMap<String, Double> predMetrs = new HashMap<String, Double>();
		predMetrs.put(srcCls.getQualifiedName(),
				getValLCOM5(srcCls, attr, tgtCls));

		predMetrs.put(tgtCls.getQualifiedName(), getValLCOM5Tgt(tgtCls));

		return predMetrs;
	}

	private Double getValLCOM5Tgt(TypeDeclaration typeDcl) throws Exception {

		if (typeDcl.getCompUnit() == null) {
			return 0.0;
		}

		File compUnitFile = typeDcl.getCompUnit().getSrcFile();
		HashSet<String> fields = MetricUtils.getFields(typeDcl);

		double numFieldUsage = 0.0;
		for (String field : fields) {
			int numField = MetricUtils.getNumberOfMethodsUsingString(typeDcl,
					field);
			numFieldUsage += numField;
		}

		Double numMethods = MetricUtils.getNumberOfMethods(typeDcl,
				compUnitFile) + 2;

		double numFields = fields.size() + 1;

		double metric = (numMethods == 0 || numFields == 0) ? 0
				: 1 - ((numFieldUsage + 2) / (numMethods * numFields));

		return metric;
	}

	private Double getValLCOM5(TypeDeclaration typeDcl,
			AttributeDeclaration attr, TypeDeclaration tgtCls) throws Exception {

		File compUnitFile = typeDcl.getCompUnit().getSrcFile();
		HashSet<String> fields = MetricUtils.getFields(typeDcl);

		double numFieldUsage = 0.0;
		for (String field : fields) {
			int numField = MetricUtils.getNumberOfMethodsUsingString(typeDcl,
					field);
			numFieldUsage += numField;
		}

		double delta = MoveFieldUtils.getDeltaFieldsUsed(
				new ClassField(attr.getObjName(), null), typeDcl);
		Double numMethods = MetricUtils.getNumberOfMethods(typeDcl,
				compUnitFile) + delta;

		int numMethAttr = MetricUtils.getNumberOfMethodsUsingString(typeDcl,
				attr.getObjName());
		double numFields = fields.size();

		double metric = (numMethods == 0 || numFields == 0) ? 0
				: 1 - ((numFieldUsage - numMethAttr + 2) / (numMethods * numFields));

		return metric;
	}

	@Override
	public CodeMetric getMetric() {
		return new LCOM2Metric();
	}

}
