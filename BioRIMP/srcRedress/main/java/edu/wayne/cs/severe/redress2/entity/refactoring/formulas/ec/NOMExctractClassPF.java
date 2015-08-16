package edu.wayne.cs.severe.redress2.entity.refactoring.formulas.ec;

import java.util.HashMap;
import java.util.LinkedHashMap;

import edu.wayne.cs.severe.redress2.controller.metric.CodeMetric;
import edu.wayne.cs.severe.redress2.controller.metric.NOMMetric;
import edu.wayne.cs.severe.redress2.entity.refactoring.RefactoringOperation;
import edu.wayne.cs.severe.redress2.entity.refactoring.formulas.PredictionFormula;
import edu.wayne.cs.severe.redress2.entity.refactoring.formulas.mf.NOMMoveFieldPF;
import edu.wayne.cs.severe.redress2.entity.refactoring.formulas.mm.NOMMoveMethodPF;
import edu.wayne.cs.severe.redress2.utils.RefactoringUtils;

public class NOMExctractClassPF extends PredictionFormula {

	@Override
	public HashMap<String, Double> predictMetrVal(RefactoringOperation ref,
			LinkedHashMap<String, LinkedHashMap<String, Double>> prevMetrics)
			throws Exception {

		PredictionFormula preFormMF = new NOMMoveFieldPF();
		PredictionFormula preFormMM = new NOMMoveMethodPF();

		HashMap<String, Double> predMetrs = RefactoringUtils
				.computePredMetrExtractClass(prevMetrics, ref, getMetric(),
						preFormMF, preFormMM);
		return predMetrs;
	}

	@Override
	public CodeMetric getMetric() {
		return new NOMMetric();
	}

}
