package edu.wayne.cs.severe.redress2.entity.refactoring.formulas.ec;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import edu.wayne.cs.severe.redress2.controller.metric.CodeMetric;
import edu.wayne.cs.severe.redress2.controller.metric.LOCMetric;
import edu.wayne.cs.severe.redress2.entity.ProgLang;
import edu.wayne.cs.severe.redress2.entity.TypeDeclaration;
import edu.wayne.cs.severe.redress2.entity.refactoring.RefactoringOperation;
import edu.wayne.cs.severe.redress2.entity.refactoring.formulas.PredictionFormula;
import edu.wayne.cs.severe.redress2.entity.refactoring.formulas.mf.LOCMoveFieldPF;
import edu.wayne.cs.severe.redress2.entity.refactoring.formulas.mm.LOCMoveMethodPF;
import edu.wayne.cs.severe.redress2.entity.refactoring.formulas.mm.MoveMethodPredFormula;
import edu.wayne.cs.severe.redress2.entity.refactoring.opers.MoveField;
import edu.wayne.cs.severe.redress2.entity.refactoring.opers.RefactoringType;
import edu.wayne.cs.severe.redress2.utils.RefactoringUtils;

public class LOCExctractClassPF extends PredictionFormula {

	private ProgLang lang;

	public LOCExctractClassPF(ProgLang lang) {
		this.lang = lang;
	}

	@Override
	public HashMap<String, Double> predictMetrVal(RefactoringOperation ref,
			LinkedHashMap<String, LinkedHashMap<String, Double>> prevMetrics)
			throws Exception {

		PredictionFormula preFormMF = new LOCMoveFieldPF(lang);
		MoveMethodPredFormula preFormMM = new LOCMoveMethodPF();

		HashMap<String, Double> predMetrs = RefactoringUtils
				.computePredMetrExtractClass(prevMetrics, ref, getMetric(),
						preFormMF, preFormMM);

		TypeDeclaration srcCls = preFormMM.getSourceClass(ref.getSubRefs().get(
				0));
		Double valSrc = predMetrs.get(srcCls.getQualifiedName());
		predMetrs.put(srcCls.getQualifiedName(), valSrc + 1.0 - numFields(ref));

		// target class
		TypeDeclaration tgtCls = preFormMM.getTargetClass(ref.getSubRefs().get(
				0));
		Double val = predMetrs.get(tgtCls.getQualifiedName());
		predMetrs.put(tgtCls.getQualifiedName(), (val == null ? 0 : val) + 2.0);

		return predMetrs;
	}

	private double numFields(RefactoringOperation ref) {
		List<RefactoringOperation> subRefs = ref.getSubRefs();

		if (subRefs == null) {
			return 0;
		}

		int num = 0;

		// for each sub-refactoring
		for (RefactoringOperation subRef : subRefs) {

			RefactoringType refType = subRef.getRefType();
			// it is a move field
			if (refType instanceof MoveField) {
				++num;
			}
		}

		return num;
	}

	@Override
	public CodeMetric getMetric() {
		return new LOCMetric();
	}

}
