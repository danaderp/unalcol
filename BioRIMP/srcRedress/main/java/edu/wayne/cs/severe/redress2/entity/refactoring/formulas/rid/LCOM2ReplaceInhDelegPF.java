package edu.wayne.cs.severe.redress2.entity.refactoring.formulas.rid;

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

public class LCOM2ReplaceInhDelegPF extends ReplaceInherDelegPredFormula {

	@Override
	public HashMap<String, Double> predictMetrVal(RefactoringOperation ref,
			LinkedHashMap<String, LinkedHashMap<String, Double>> prevMetrics)
			throws Exception {

		TypeDeclaration srcCls = getSourceClass(ref);
		TypeDeclaration tgtCls = getTargetClass(ref);

		HashMap<String, Double> predMetrs = new HashMap<String, Double>();

		predMetrs
				.put(srcCls.getQualifiedName(), getValLCOM5Src(srcCls, tgtCls));

		LinkedHashMap<String, Double> prevMetrsTgt = prevMetrics.get(tgtCls
				.getQualifiedName());
		Double prevMetrTgt = prevMetrsTgt == null ? 0 : prevMetrsTgt
				.get(getMetric().getMetricAcronym());
		predMetrs.put(tgtCls.getQualifiedName(), prevMetrTgt);

		return predMetrs;
	}

	private Double getValLCOM5Src(TypeDeclaration typeDclSrc,
			TypeDeclaration typeDclTgt) throws Exception {

		File compUnitFile = typeDclSrc.getCompUnit().getSrcFile();
		HashSet<String> fields = MetricUtils.getFields(typeDclSrc);

		double numFieldUsage = 0.0;
		for (String field : fields) {
			int numField = MetricUtils.getNumberOfMethodsUsingString(
					typeDclSrc, field);
			numFieldUsage += numField;
		}

		double numFields = fields.size() + 1;
		Double numMethods = MetricUtils.getNumberOfMethods(typeDclSrc,
				compUnitFile);

		int numMethodsUsingTgt = getNumMethodsUsingTgt(typeDclSrc, typeDclTgt);

		double metric = (numMethods == 0 || numFields == 0) ? 0
				: 1 - ((numFieldUsage + numMethodsUsingTgt) / (numMethods * numFields));

		return metric;
	}

	private int getNumMethodsUsingTgt(TypeDeclaration typeDclSrc,
			TypeDeclaration typeDclTgt) throws Exception {

		if (typeDclTgt.getCompUnit() == null) {
			return 0;
		}

		LinkedHashSet<String> fields = MetricUtils.getFields(typeDclTgt);

		LinkedHashSet<String> methodsSrc = new LinkedHashSet<String>();
		for (String field : fields) {
			methodsSrc.addAll(MetricUtils.getMethodsUsingString(typeDclSrc,
					field));
		}

		LinkedHashSet<String> methodsTgt = MetricUtils.getMethods(typeDclTgt);
		for (String methodTgt : methodsTgt) {
			methodsSrc.addAll(MetricUtils.getMethodsUsingString(typeDclSrc,
					methodTgt));
		}

		return methodsSrc.size();
	}

	@Override
	public CodeMetric getMetric() {
		return new LCOM2Metric();
	}

}
