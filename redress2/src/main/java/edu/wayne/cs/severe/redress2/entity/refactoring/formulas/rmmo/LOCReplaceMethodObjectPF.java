package edu.wayne.cs.severe.redress2.entity.refactoring.formulas.rmmo;

import java.util.HashMap;
import java.util.LinkedHashMap;

import edu.wayne.cs.severe.redress2.controller.MetricUtils;
import edu.wayne.cs.severe.redress2.controller.metric.CodeMetric;
import edu.wayne.cs.severe.redress2.controller.metric.LOCMetric;
import edu.wayne.cs.severe.redress2.entity.MethodDeclaration;
import edu.wayne.cs.severe.redress2.entity.ProgLang;
import edu.wayne.cs.severe.redress2.entity.TypeDeclaration;
import edu.wayne.cs.severe.redress2.entity.refactoring.RefactoringOperation;

public class LOCReplaceMethodObjectPF extends ReplaceMethodObjectPredFormula {

	private ProgLang lang;

	public LOCReplaceMethodObjectPF(ProgLang lang) {
		this.lang = lang;
	}

	@Override
	public HashMap<String, Double> predictMetrVal(RefactoringOperation ref,
			LinkedHashMap<String, LinkedHashMap<String, Double>> prevMetrics)
			throws Exception {

		TypeDeclaration srcCls = getSourceClass(ref);
		MethodDeclaration method = getMethod(ref);
		TypeDeclaration tgtCls = getTargetClass(ref);

		String codeMethod = MetricUtils.getCodeMethod(srcCls,
				method.getObjName());
		int methodLoc = MetricUtils.countLOC(codeMethod);
		int linesLang = ProgLang.JAVA == lang ? 2 : 3;

		HashMap<String, Double> predMetrs = new HashMap<String, Double>();

		Double prevMetr = prevMetrics.get(srcCls.getQualifiedName()).get(
				getMetric().getMetricAcronym());
		predMetrs.put(srcCls.getQualifiedName(), prevMetr - methodLoc
				+ (linesLang + 1));

		double numParsVars = MetricUtils.getNumParsAndVars(srcCls,
				method.getObjName());
		double numVars = MetricUtils.getNumVars(srcCls, method.getObjName());

		predMetrs.put(tgtCls.getQualifiedName(), methodLoc + 2
				* (numParsVars + linesLang + 1) - numVars);

		return predMetrs;
	}

	@Override
	public CodeMetric getMetric() {
		return new LOCMetric();
	}

}
