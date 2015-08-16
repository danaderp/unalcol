package edu.wayne.cs.severe.redress2.entity.refactoring.formulas.ec;

import java.util.HashMap;
import java.util.LinkedHashMap;

import edu.wayne.cs.severe.redress2.controller.metric.CYCLOMetric;
import edu.wayne.cs.severe.redress2.controller.metric.CodeMetric;
import edu.wayne.cs.severe.redress2.entity.refactoring.RefactoringOperation;
import edu.wayne.cs.severe.redress2.entity.refactoring.formulas.PredictionFormula;
import edu.wayne.cs.severe.redress2.entity.refactoring.formulas.mf.CYCLOMoveFieldPF;
import edu.wayne.cs.severe.redress2.entity.refactoring.formulas.mm.CYCLOMoveMethodPF;
import edu.wayne.cs.severe.redress2.utils.RefactoringUtils;

public class CYCLOExctractClassPF extends PredictionFormula {

	@Override
	public HashMap<String, Double> predictMetrVal(RefactoringOperation ref,
			LinkedHashMap<String, LinkedHashMap<String, Double>> prevMetrics)
			throws Exception {

		PredictionFormula preFormMF = new CYCLOMoveFieldPF();
		PredictionFormula preFormMM = new CYCLOMoveMethodPF();

		HashMap<String, Double> predMetrs = RefactoringUtils
				.computePredMetrExtractClass(prevMetrics, ref, getMetric(),
						preFormMF, preFormMM);
		return predMetrs;
	}

	@Override
	public CodeMetric getMetric() {
		return new CYCLOMetric();
	}

}
