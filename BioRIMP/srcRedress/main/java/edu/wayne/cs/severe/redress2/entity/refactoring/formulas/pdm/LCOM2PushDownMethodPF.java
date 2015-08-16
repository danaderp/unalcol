package edu.wayne.cs.severe.redress2.entity.refactoring.formulas.pdm;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;

import edu.wayne.cs.severe.redress2.controller.MetricUtils;
import edu.wayne.cs.severe.redress2.controller.metric.CodeMetric;
import edu.wayne.cs.severe.redress2.controller.metric.LCOM2Metric;
import edu.wayne.cs.severe.redress2.entity.MethodDeclaration;
import edu.wayne.cs.severe.redress2.entity.TypeDeclaration;
import edu.wayne.cs.severe.redress2.entity.refactoring.RefactoringOperation;

public class LCOM2PushDownMethodPF extends PushDownPredFormula {

	@Override
	public HashMap<String, Double> predictMetrVal(RefactoringOperation ref,
			LinkedHashMap<String, LinkedHashMap<String, Double>> prevMetrics)
			throws Exception {

		TypeDeclaration srcCls = getSourceClass(ref);
		MethodDeclaration method = getMethod(ref);
		List<TypeDeclaration> tgtClses = getTargetClasses(ref);

		HashMap<String, Double> predMetrs = new HashMap<String, Double>();

		predMetrs.put(srcCls.getQualifiedName(),
				getValLCOM2(srcCls, method, -1, null));

		for (TypeDeclaration tgtCls : tgtClses) {
			predMetrs.put(tgtCls.getQualifiedName(),
					getValLCOM2(tgtCls, method, 1, srcCls));
		}

		return predMetrs;
	}

	private Double getValLCOM2(TypeDeclaration typeDcl,
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

		int numAttrMethod = getNumAttrsUsedByMethod(typeDcl, method, typeDclSrc);

		double metric = (numMethods == 0 || numFields == 0) ? 0
				: 1 - ((numFieldUsage + mult * numAttrMethod) / (numMethods * numFields));

		return metric;
	}

	private int getNumAttrsUsedByMethod(TypeDeclaration typeDcl,
			MethodDeclaration method, TypeDeclaration typeDclSrc)
			throws Exception {

		LinkedHashSet<String> fields = MetricUtils.getFields(typeDcl);

		int numSttrs = 0;
		for (String field : fields) {
			LinkedHashSet<String> methods = MetricUtils.getMethodsUsingString(
					typeDclSrc == null ? typeDcl : typeDclSrc, field);
			if (methods.contains(method.getObjName() + ".")) {
				++numSttrs;
			}
		}

		return numSttrs;
	}

	@Override
	public CodeMetric getMetric() {
		return new LCOM2Metric();
	}

}
