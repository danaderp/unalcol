package edu.wayne.cs.severe.redress2.entity.refactoring.formulas.em;

import java.util.HashMap;
import java.util.LinkedHashMap;

import edu.wayne.cs.severe.redress2.controller.metric.CodeMetric;
import edu.wayne.cs.severe.redress2.controller.metric.LOCMetric;
import edu.wayne.cs.severe.redress2.entity.ProgLang;
import edu.wayne.cs.severe.redress2.entity.TypeDeclaration;
import edu.wayne.cs.severe.redress2.entity.refactoring.RefactoringOperation;

public class LOCExtractMethodPF extends ExtractMethodPredFormula {

	private ProgLang lang;

	public LOCExtractMethodPF(ProgLang lang) {
		this.lang = lang;
	}

	@Override
	public HashMap<String, Double> predictMetrVal(RefactoringOperation ref,
			LinkedHashMap<String, LinkedHashMap<String, Double>> prevMetrics)
			throws Exception {

		TypeDeclaration srcCls = getSourceClass(ref);

		HashMap<String, Double> predMetrs = new HashMap<String, Double>();

		Double prevMetr = prevMetrics.get(srcCls.getQualifiedName()).get(
				getMetric().getMetricAcronym());
		Double progLangFactor = ProgLang.JAVA == this.lang ? 2.0 : 3.0;
		predMetrs.put(srcCls.getQualifiedName(), prevMetr + progLangFactor + 1);

		return predMetrs;
	}

	@Override
	public CodeMetric getMetric() {
		return new LOCMetric();
	}

}
