package edu.wayne.cs.severe.redress2.entity.refactoring.formulas.rmmo;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;

import edu.wayne.cs.severe.redress2.controller.MetricUtils;
import edu.wayne.cs.severe.redress2.controller.metric.CodeMetric;
import edu.wayne.cs.severe.redress2.controller.metric.LCOM5Metric;
import edu.wayne.cs.severe.redress2.entity.MethodDeclaration;
import edu.wayne.cs.severe.redress2.entity.TypeDeclaration;
import edu.wayne.cs.severe.redress2.entity.refactoring.RefactoringOperation;

public class LCOM5ReplaceMethodObjectPF extends ReplaceMethodObjectPredFormula {

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

		double numParsVars = MetricUtils.getNumParsAndVars(srcCls,
				method.getObjName());
		double numVars = MetricUtils.getNumVars(srcCls, method.getObjName());
		double numParams = numParsVars - numVars;
		double numRefsSrc = getNumRefsSrc(srcCls, method);

		double valueTgt = (numVars - numRefsSrc + 2)
				/ (numVars + numParams + 1);

		// double numFieldUsage = numVars + 2 * numParams + numRefsSrc;
		// double numFields = numParsVars + 1;
		// double numMethods = 2;
		// Double expec2 = ((numFieldUsage / numFields) - numMethods)
		// / (1 - numMethods);

		predMetrs.put(tgtCls.getQualifiedName(), valueTgt);

		return predMetrs;
	}

	private double getNumRefsSrc(TypeDeclaration srcCls,
			MethodDeclaration methodp) throws Exception {

		boolean usingTgtClass = false;

		// using a method or field of tgtCls?
		HashSet<String> methodsSrc = MetricUtils.getMethods(srcCls);
		HashSet<String> fieldsSrc = MetricUtils.getFields(srcCls);

		for (String srcMethod : methodsSrc) {

			if (srcMethod.equals(methodp.getObjName())) {
				continue;
			}

			int numUsages = MetricUtils.getStringNumUsagesInMethod(srcCls,
					methodp, srcMethod);
			if (numUsages > 0) {
				usingTgtClass = true;
				break;
			}
		}

		if (!usingTgtClass) {
			for (String srcField : fieldsSrc) {
				int numUsages = MetricUtils.getStringNumUsagesInMethod(srcCls,
						methodp, srcField);
				if (numUsages > 0) {
					usingTgtClass = true;
					break;
				}
			}
		}

		return 1 + (usingTgtClass ? 1 : 0);
	}

	private Double getValLCOM5(TypeDeclaration typeDcl,
			MethodDeclaration method, int mult, TypeDeclaration typeDclSrc)
			throws Exception {

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
				compUnitFile);

		int numAttrMethod = getNumAttrsUsedByMethod(typeDcl, method, typeDclSrc);

		double metric = (numMethods == 1 || numFields == 0) ? 0
				: (((numFieldUsage + mult * numAttrMethod) / numFields) - numMethods)
						/ (1 - numMethods);

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
		return new LCOM5Metric();
	}

}
