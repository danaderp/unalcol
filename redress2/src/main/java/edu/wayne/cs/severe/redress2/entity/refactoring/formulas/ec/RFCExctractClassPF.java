package edu.wayne.cs.severe.redress2.entity.refactoring.formulas.ec;

import java.util.HashMap;
import java.util.LinkedHashMap;

import edu.wayne.cs.severe.redress2.controller.metric.CodeMetric;
import edu.wayne.cs.severe.redress2.controller.metric.RFCMetric;
import edu.wayne.cs.severe.redress2.entity.refactoring.RefactoringOperation;
import edu.wayne.cs.severe.redress2.entity.refactoring.formulas.PredictionFormula;
import edu.wayne.cs.severe.redress2.entity.refactoring.formulas.mf.RFCMoveFieldPF;
import edu.wayne.cs.severe.redress2.entity.refactoring.formulas.mm.RFCMoveMethodPF;
import edu.wayne.cs.severe.redress2.utils.RefactoringUtils;

public class RFCExctractClassPF extends PredictionFormula {

	@Override
	public HashMap<String, Double> predictMetrVal(RefactoringOperation ref,
			LinkedHashMap<String, LinkedHashMap<String, Double>> prevMetrics)
			throws Exception {

		PredictionFormula preFormMF = new RFCMoveFieldPF();
		PredictionFormula preFormMM = new RFCMoveMethodPF();

		HashMap<String, Double> predMetrs = RefactoringUtils
				.computePredMetrExtractClass(prevMetrics, ref, getMetric(),
						preFormMF, preFormMM);
		return predMetrs;
	}

	@Override
	public CodeMetric getMetric() {
		return new RFCMetric();
	}

}
