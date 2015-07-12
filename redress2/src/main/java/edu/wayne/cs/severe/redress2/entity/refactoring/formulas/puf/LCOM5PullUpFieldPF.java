package edu.wayne.cs.severe.redress2.entity.refactoring.formulas.puf;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;

import edu.wayne.cs.severe.redress2.controller.MetricUtils;
import edu.wayne.cs.severe.redress2.controller.metric.CodeMetric;
import edu.wayne.cs.severe.redress2.controller.metric.LCOM5Metric;
import edu.wayne.cs.severe.redress2.entity.AttributeDeclaration;
import edu.wayne.cs.severe.redress2.entity.TypeDeclaration;
import edu.wayne.cs.severe.redress2.entity.refactoring.RefactoringOperation;

public class LCOM5PullUpFieldPF extends PullUpFieldPredFormula {

	@Override
	public HashMap<String, Double> predictMetrVal(RefactoringOperation ref,
			LinkedHashMap<String, LinkedHashMap<String, Double>> prevMetrics)
			throws Exception {

		List<TypeDeclaration> srcClses = getSourceClasses(ref);
		AttributeDeclaration attr = getAttribute(ref);
		TypeDeclaration tgtCls = getTargetClass(ref);

		HashMap<String, Double> predMetrs = new HashMap<String, Double>();
		for (TypeDeclaration srcCls : srcClses) {
			predMetrs.put(srcCls.getQualifiedName(),
					getValLCOM5(srcCls, attr, -1));
		}

		predMetrs.put(tgtCls.getQualifiedName(), getValLCOM5(tgtCls, attr, 1));

		return predMetrs;
	}

	private Double getValLCOM5(TypeDeclaration typeDcl,
			AttributeDeclaration attr, int mult) throws Exception {

		File compUnitFile = typeDcl.getCompUnit().getSrcFile();
		HashSet<String> fields = MetricUtils.getFields(typeDcl);

		double numFieldUsage = 0.0;
		for (String field : fields) {
			int numField = MetricUtils.getNumberOfMethodsUsingString(typeDcl,
					field);
			numFieldUsage += numField;
		}

		double numFields = fields.size() + mult * 1;
		Double numMethods = MetricUtils.getNumberOfMethods(typeDcl,
				compUnitFile);

		int numMethAttr = MetricUtils.getNumberOfMethodsUsingString(typeDcl,
				attr.getObjName());

		double metric = (numMethods == 1 || numFields == 0) ? 0
				: (((numFieldUsage + mult * numMethAttr) / numFields) - numMethods)
						/ (1 - numMethods);

		return metric;
	}

	@Override
	public CodeMetric getMetric() {
		return new LCOM5Metric();
	}

}
