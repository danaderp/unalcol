package edu.wayne.cs.severe.redress2.entity.refactoring.formulas.pum;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;

import edu.wayne.cs.severe.redress2.controller.MetricUtils;
import edu.wayne.cs.severe.redress2.controller.metric.CodeMetric;
import edu.wayne.cs.severe.redress2.controller.metric.LCOM5Metric;
import edu.wayne.cs.severe.redress2.entity.ClassField;
import edu.wayne.cs.severe.redress2.entity.MethodDeclaration;
import edu.wayne.cs.severe.redress2.entity.TypeDeclaration;
import edu.wayne.cs.severe.redress2.entity.refactoring.RefactoringOperation;
import edu.wayne.cs.severe.redress2.utils.PullUpMethodUtils;

/**
 * Prediction formula for Pull Up Method and the LCOM5 metric
 * 
 * @author ojcchar
 * 
 */
public class LCOM5PullUpMethodPF extends PullUpMethodPredFormula {

	@Override
	public HashMap<String, Double> predictMetrVal(RefactoringOperation ref,
			LinkedHashMap<String, LinkedHashMap<String, Double>> prevMetrics)
			throws Exception {

		// get the parameters of the refactoring
		List<TypeDeclaration> srcClses = getSourceClasses(ref);
		MethodDeclaration method = getMethod(ref);
		TypeDeclaration tgtCls = getTargetClass(ref);

		// predicted metrics
		HashMap<String, Double> predMetrs = new HashMap<String, Double>();

		// get the fields being read
		List<ClassField> usedFieldsSrc = MetricUtils.getFieldsUsedByMethod(
				srcClses.get(0), method);
		// delta of the fields used by the method
		double deltaFieldsUsed = PullUpMethodUtils.getDeltaFieldsUsed(
				usedFieldsSrc, srcClses.get(0));

		for (TypeDeclaration srcCls : srcClses) {
			predMetrs.put(srcCls.getQualifiedName(),
					getValLCOM5(srcCls, method, -1, deltaFieldsUsed, 0, false));
		}

		// get the method calls in the method
		LinkedHashSet<String> callsMethod = MetricUtils.getMethodCallsMethod(
				srcClses.get(0), method.getObjName());

		// change the metric of the target class
		// delta of the fields used by the method
		double deltaFieldsUsedTgt = PullUpMethodUtils.getDeltaFieldsUsed(
				usedFieldsSrc, tgtCls);
		double deltaSubclassMethodsUsed = PullUpMethodUtils
				.getDeltaSubclassMethodsUsed(srcClses.get(0), callsMethod,
						tgtCls);
		predMetrs.put(
				tgtCls.getQualifiedName(),
				getValLCOM5(tgtCls, method, 1, deltaFieldsUsedTgt,
						deltaSubclassMethodsUsed, true));

		return predMetrs;
	}

	private Double getValLCOM5(TypeDeclaration typeDcl,
			MethodDeclaration method, int mult, double deltaFieldsUsed,
			double deltaSubclassMethodsUsed, boolean isTgt) throws Exception {

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
				compUnitFile)
				+ mult
				* 1
				+ deltaFieldsUsed
				+ deltaSubclassMethodsUsed;

		int numAttrMethod = getNumAttrsUsedByMethod(typeDcl, method);

		double metric = (numMethods == 1 || numFields == 0) ? 0
				: ((numFieldUsage + mult * numAttrMethod + (isTgt ? 0.0
						: deltaFieldsUsed)) / numFields - numMethods)
						/ (1 - numMethods);

		return metric;
	}

	private int getNumAttrsUsedByMethod(TypeDeclaration typeDcl,
			MethodDeclaration method) throws Exception {

		LinkedHashSet<String> fields = MetricUtils.getFields(typeDcl);

		int numSttrs = 0;
		for (String field : fields) {
			LinkedHashSet<String> methods = MetricUtils.getMethodsUsingString(
					typeDcl, field);
			// typeDclSrc == null ? typeDcl : typeDclSrc, field);
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
