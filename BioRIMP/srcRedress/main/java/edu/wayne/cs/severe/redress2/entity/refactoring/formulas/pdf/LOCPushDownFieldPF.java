package edu.wayne.cs.severe.redress2.entity.refactoring.formulas.pdf;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import edu.wayne.cs.severe.redress2.controller.metric.CodeMetric;
import edu.wayne.cs.severe.redress2.controller.metric.LOCMetric;
import edu.wayne.cs.severe.redress2.entity.AttributeDeclaration;
import edu.wayne.cs.severe.redress2.entity.ClassField;
import edu.wayne.cs.severe.redress2.entity.ProgLang;
import edu.wayne.cs.severe.redress2.entity.TypeDeclaration;
import edu.wayne.cs.severe.redress2.entity.refactoring.RefactoringOperation;
import edu.wayne.cs.severe.redress2.utils.PullUpMethodUtils;

/**
 * Prediction formula for Push Down Field and the LOC metric
 * 
 * @author ojcchar
 * 
 */
public class LOCPushDownFieldPF extends PushDownFieldPredFormua {

	private ProgLang lang;

	public LOCPushDownFieldPF(ProgLang lang) {
		this.lang = lang;
	}

	@Override
	public HashMap<String, Double> predictMetrVal(RefactoringOperation ref,
			LinkedHashMap<String, LinkedHashMap<String, Double>> prevMetrics)
			throws Exception {

		// get the parameters of the refactoring
		TypeDeclaration srcCls = getSourceClass(ref);
		AttributeDeclaration attr = getAttribute(ref);
		List<TypeDeclaration> tgtClses = getTargetClasses(ref);
		CodeMetric metric = getMetric();

		// predicted metrics
		HashMap<String, Double> predMetrs = new HashMap<String, Double>();

		// delta of the fields used by the method
		ClassField classField = new ClassField(attr.getObjName(), null);
		List<ClassField> usedFieldsSrc = new ArrayList<ClassField>();
		usedFieldsSrc.add(classField);
		double deltaFieldsUsed = PullUpMethodUtils.getDeltaFieldsUsed(
				usedFieldsSrc, srcCls);

		Double prevMetr = prevMetrics.get(srcCls.getQualifiedName()).get(
				metric.getMetricAcronym());
		predMetrs
				.put(srcCls.getQualifiedName(), prevMetr - 1 + deltaFieldsUsed);

		int linesLang = ProgLang.JAVA == lang ? 3 : 4;

		for (TypeDeclaration tgtCls : tgtClses) {
			Double prevMetrTgt = prevMetrics.get(tgtCls.getQualifiedName())
					.get(metric.getMetricAcronym());

			// compute the delta and set the metric value
			double deltaFieldsTgt = PullUpMethodUtils.getDeltaFieldsUsed(
					usedFieldsSrc, tgtCls);

			predMetrs.put(tgtCls.getQualifiedName(), prevMetrTgt + 1
					+ linesLang * deltaFieldsTgt);
		}

		return predMetrs;
	}

	public CodeMetric getMetric() {
		return new LOCMetric();
	}

}
