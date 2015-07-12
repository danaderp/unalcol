package edu.wayne.cs.severe.redress2.entity.refactoring.formulas.mf;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;

import edu.wayne.cs.severe.redress2.controller.MetricUtils;
import edu.wayne.cs.severe.redress2.controller.metric.CodeMetric;
import edu.wayne.cs.severe.redress2.controller.metric.LCOM5Metric;
import edu.wayne.cs.severe.redress2.entity.AttributeDeclaration;
import edu.wayne.cs.severe.redress2.entity.ClassField;
import edu.wayne.cs.severe.redress2.entity.TypeDeclaration;
import edu.wayne.cs.severe.redress2.entity.refactoring.RefactoringOperation;
import edu.wayne.cs.severe.redress2.utils.MoveFieldUtils;

public class LCOM5MoveFieldPF extends MoveFieldPredFormula {

	@Override
	public HashMap<String, Double> predictMetrVal(RefactoringOperation ref,
			LinkedHashMap<String, LinkedHashMap<String, Double>> prevMetrics)
			throws Exception {

		TypeDeclaration srcCls = getSourceClass(ref);
		AttributeDeclaration attr = getAttribute(ref);
		TypeDeclaration tgtCls = getTargetClass(ref);

		HashMap<String, Double> predMetrs = new HashMap<String, Double>();
		Double valSrc = getValLCOM5(srcCls, attr);
		predMetrs.put(srcCls.getQualifiedName(), valSrc);

		Double valTgt = getValLCOM5Tgt(tgtCls);
		predMetrs.put(tgtCls.getQualifiedName(), valTgt);

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

		double metric = (numMethods == 1 || numFields == 0) ? 0
				: (((numFieldUsage + 2) / numFields) - numMethods)
						/ (1 - numMethods);

		return metric;
	}

	private Double getValLCOM5(TypeDeclaration typeDcl,
			AttributeDeclaration attr) throws Exception {

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

		double metric = (numMethods == 1 || numFields == 0) ? 0
				: (((numFieldUsage - numMethAttr + 2) / numFields) - numMethods)
						/ (1 - numMethods);

		return metric;
	}

	@Override
	public CodeMetric getMetric() {
		return new LCOM5Metric();
	}

}
