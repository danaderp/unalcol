package edu.wayne.cs.severe.redress2.entity.refactoring.formulas.ec;

import java.util.HashMap;
import java.util.LinkedHashMap;

import edu.wayne.cs.severe.redress2.controller.metric.CodeMetric;
import edu.wayne.cs.severe.redress2.controller.metric.DACMetric;
import edu.wayne.cs.severe.redress2.entity.TypeDeclaration;
import edu.wayne.cs.severe.redress2.entity.refactoring.RefactoringOperation;
import edu.wayne.cs.severe.redress2.entity.refactoring.formulas.PredictionFormula;
import edu.wayne.cs.severe.redress2.entity.refactoring.formulas.mf.DACMoveFieldPF;
import edu.wayne.cs.severe.redress2.entity.refactoring.formulas.mf.MoveFieldPredFormula;
import edu.wayne.cs.severe.redress2.utils.ExtractClassUtils;
import edu.wayne.cs.severe.redress2.utils.RefactoringUtils;

public class DACExctractClassPF extends PredictionFormula {

	@Override
	public HashMap<String, Double> predictMetrVal(RefactoringOperation ref,
			LinkedHashMap<String, LinkedHashMap<String, Double>> prevMetrics)
			throws Exception {

		MoveFieldPredFormula preFormMF = new DACMoveFieldPF();

		HashMap<String, Double> predMetrs = RefactoringUtils
				.computePredMetrExtractClass(prevMetrics, ref, getMetric(),
						preFormMF, null);

		TypeDeclaration srcCls = preFormMF.getSourceClass(ref.getSubRefs().get(
				0));
		Double valSrc = predMetrs.get(srcCls.getQualifiedName());
		predMetrs.put(srcCls.getQualifiedName(), (valSrc == null ? 0 : valSrc)
				+ 1.0 - ExtractClassUtils.numFieldsToMove(ref));

		return predMetrs;
	}

	@Override
	public CodeMetric getMetric() {
		return new DACMetric();
	}

}
