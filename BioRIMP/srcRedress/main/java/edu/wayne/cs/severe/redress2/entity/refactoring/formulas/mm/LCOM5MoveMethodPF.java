package edu.wayne.cs.severe.redress2.entity.refactoring.formulas.mm;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;

import edu.wayne.cs.severe.redress2.controller.MetricUtils;
import edu.wayne.cs.severe.redress2.controller.metric.CodeMetric;
import edu.wayne.cs.severe.redress2.controller.metric.LCOM5Metric;
import edu.wayne.cs.severe.redress2.entity.MethodDeclaration;
import edu.wayne.cs.severe.redress2.entity.TypeDeclaration;
import edu.wayne.cs.severe.redress2.entity.refactoring.RefactoringOperation;
import edu.wayne.cs.severe.redress2.utils.MoveMethodUtils;

public class LCOM5MoveMethodPF extends MoveMethodPredFormula {

	@Override
	public HashMap<String, Double> predictMetrVal(RefactoringOperation ref,
			LinkedHashMap<String, LinkedHashMap<String, Double>> prevMetrics)
			throws Exception {

		TypeDeclaration srcCls = getSourceClass(ref);
		MethodDeclaration method = getMethod(ref);
		TypeDeclaration tgtCls = getTargetClass(ref);

		HashMap<String, Double> predMetrs = new HashMap<String, Double>();

		predMetrs.put(srcCls.getQualifiedName(),
				getValLCOM5(srcCls, method, -1, null));

		predMetrs.put(tgtCls.getQualifiedName(),
				getValLCOM5(tgtCls, method, 1, srcCls));

		return predMetrs;
	}

	private Double getValLCOM5(TypeDeclaration typeDcl,
			MethodDeclaration method, int mult, TypeDeclaration typeDclSrc)
			throws Exception {

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

		double numFields = fields.size();
		Double numMethods = MetricUtils.getNumberOfMethods(typeDcl,
				compUnitFile) + mult * 1;

		int numAttrMethod = MoveMethodUtils.getNumAttrsUsedByMethod(typeDcl, method, typeDclSrc);

		double metric = (numMethods == 1 || numFields == 0) ? 0
				: ((numFieldUsage + mult * numAttrMethod) / numFields - numMethods)
						/ (1 - numMethods);

		return metric;
	}

	@Override
	public CodeMetric getMetric() {
		return new LCOM5Metric();
	}

}
