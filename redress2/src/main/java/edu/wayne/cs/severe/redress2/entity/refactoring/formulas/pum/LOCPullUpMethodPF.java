package edu.wayne.cs.severe.redress2.entity.refactoring.formulas.pum;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;

import edu.wayne.cs.severe.redress2.controller.MetricUtils;
import edu.wayne.cs.severe.redress2.controller.metric.CodeMetric;
import edu.wayne.cs.severe.redress2.controller.metric.LOCMetric;
import edu.wayne.cs.severe.redress2.entity.ClassField;
import edu.wayne.cs.severe.redress2.entity.MethodDeclaration;
import edu.wayne.cs.severe.redress2.entity.ProgLang;
import edu.wayne.cs.severe.redress2.entity.TypeDeclaration;
import edu.wayne.cs.severe.redress2.entity.refactoring.RefactoringOperation;
import edu.wayne.cs.severe.redress2.utils.PullUpMethodUtils;

/**
 * Prediction formula for Pull Up Method and the LOC metric
 * 
 * @author ojcchar
 * 
 */
public class LOCPullUpMethodPF extends PullUpMethodPredFormula {

	private ProgLang lang;

	public LOCPullUpMethodPF(ProgLang lang) {
		this.lang = lang;
	}

	@Override
	public HashMap<String, Double> predictMetrVal(RefactoringOperation ref,
			LinkedHashMap<String, LinkedHashMap<String, Double>> prevMetrics)
			throws Exception {

		// get the parameters of the refactoring
		List<TypeDeclaration> srcClses = getSourceClasses(ref);
		MethodDeclaration method = getMethod(ref);
		TypeDeclaration tgtCls = getTargetClass(ref);
		CodeMetric metric = getMetric();

		// count the lines of code of the method
		String code = MetricUtils.getCodeMethod(srcClses.get(0),
				method.getObjName());
		int countLOC = MetricUtils.countLOC(code);

		// get the fields being read
		List<ClassField> usedFieldsSrc = MetricUtils.getFieldsUsedByMethod(
				srcClses.get(0), method);
		// delta of the fields used by the method
		double deltaFieldsUsed = PullUpMethodUtils.getDeltaFieldsUsed(
				usedFieldsSrc, srcClses.get(0));
		int linesLang = ProgLang.JAVA == lang ? 3 : 4;

		// predicted metrics
		HashMap<String, Double> predMetrs = new HashMap<String, Double>();

		// for every subclass
		for (TypeDeclaration srcCls : srcClses) {

			// previous metric
			Double prevMetr = prevMetrics.get(srcCls.getQualifiedName()).get(
					metric.getMetricAcronym());
			predMetrs.put(srcCls.getQualifiedName(), prevMetr - countLOC
					+ linesLang * deltaFieldsUsed);
		}

		// get the method calls in the method
		LinkedHashSet<String> callsMethod = MetricUtils.getMethodCallsMethod(
				srcClses.get(0), method.getObjName());

		// change the metric of the target class
		Double prevMetrTgt = prevMetrics.get(tgtCls.getQualifiedName()).get(
				metric.getMetricAcronym());
		// delta of the fields used by the method
		double deltaFieldsUsedTgt = PullUpMethodUtils.getDeltaFieldsUsed(
				usedFieldsSrc, tgtCls);
		double deltaSubclassMethodsUsed = PullUpMethodUtils
				.getDeltaSubclassMethodsUsed(srcClses.get(0), callsMethod,
						tgtCls);
		predMetrs.put(tgtCls.getQualifiedName(), prevMetrTgt + countLOC
				+ deltaFieldsUsedTgt + deltaSubclassMethodsUsed);

		return predMetrs;
	}

	@Override
	public CodeMetric getMetric() {
		return new LOCMetric();
	}

}
