package edu.wayne.cs.severe.redress2.entity.refactoring.formulas.mf;

import java.util.HashMap;
import java.util.LinkedHashMap;

import edu.wayne.cs.severe.redress2.controller.metric.CodeMetric;
import edu.wayne.cs.severe.redress2.controller.metric.LOCMetric;
import edu.wayne.cs.severe.redress2.entity.AttributeDeclaration;
import edu.wayne.cs.severe.redress2.entity.ClassField;
import edu.wayne.cs.severe.redress2.entity.ProgLang;
import edu.wayne.cs.severe.redress2.entity.TypeDeclaration;
import edu.wayne.cs.severe.redress2.entity.refactoring.RefactoringOperation;
import edu.wayne.cs.severe.redress2.utils.MoveFieldUtils;

public class LOCMoveFieldPF extends MoveFieldPredFormula {

	private ProgLang lang;

	public LOCMoveFieldPF(ProgLang lang) {
		this.lang = lang;
	}

	@Override
	public HashMap<String, Double> predictMetrVal(RefactoringOperation ref,
			LinkedHashMap<String, LinkedHashMap<String, Double>> prevMetrics)
			throws Exception {

		TypeDeclaration srcCls = getSourceClass(ref);
		AttributeDeclaration attr = getAttribute(ref);
		TypeDeclaration tgtCls = getTargetClass(ref);

		HashMap<String, Double> predMetrs = new HashMap<String, Double>();

		double delta = MoveFieldUtils.getDeltaFieldsUsed(
				new ClassField(attr.getObjName(), null), srcCls);

		// Double lk = ProgLang.JAVA == lang ? 7.0 : 1.0;
		Double lk = ProgLang.JAVA == lang ? delta * 3 : 1.0;

		Double prevMetr = prevMetrics.get(srcCls.getQualifiedName()).get(
				getMetric().getMetricAcronym());
		predMetrs.put(srcCls.getQualifiedName(), prevMetr + lk);

		LinkedHashMap<String, Double> metrs = prevMetrics.get(tgtCls
				.getQualifiedName());
		double pk = 7;
		predMetrs.put(tgtCls.getQualifiedName(),
				(metrs != null ? metrs.get(getMetric().getMetricAcronym()) : 0)
						+ pk);

		return predMetrs;
	}

	@Override
	public CodeMetric getMetric() {
		return new LOCMetric();
	}

}
