package edu.wayne.cs.severe.redress2.entity.refactoring.opers;

import java.util.HashMap;
import java.util.List;

import edu.wayne.cs.severe.redress2.controller.metric.CBOMetric;
import edu.wayne.cs.severe.redress2.controller.metric.CYCLOMetric;
import edu.wayne.cs.severe.redress2.controller.metric.DACMetric;
import edu.wayne.cs.severe.redress2.controller.metric.LCOM2Metric;
import edu.wayne.cs.severe.redress2.controller.metric.LCOM5Metric;
import edu.wayne.cs.severe.redress2.controller.metric.LOCMetric;
import edu.wayne.cs.severe.redress2.controller.metric.MPCMetric;
import edu.wayne.cs.severe.redress2.controller.metric.NOMMetric;
import edu.wayne.cs.severe.redress2.controller.metric.RFCMetric;
import edu.wayne.cs.severe.redress2.entity.AttributeDeclaration;
import edu.wayne.cs.severe.redress2.entity.ProgLang;
import edu.wayne.cs.severe.redress2.entity.TypeDeclaration;
import edu.wayne.cs.severe.redress2.entity.refactoring.RefactoringParameter;
import edu.wayne.cs.severe.redress2.entity.refactoring.formulas.mf.CBOMoveFieldPF;
import edu.wayne.cs.severe.redress2.entity.refactoring.formulas.mf.CYCLOMoveFieldPF;
import edu.wayne.cs.severe.redress2.entity.refactoring.formulas.mf.DACMoveFieldPF;
import edu.wayne.cs.severe.redress2.entity.refactoring.formulas.mf.LCOM2MoveFieldPF;
import edu.wayne.cs.severe.redress2.entity.refactoring.formulas.mf.LCOM5MoveFieldPF;
import edu.wayne.cs.severe.redress2.entity.refactoring.formulas.mf.LOCMoveFieldPF;
import edu.wayne.cs.severe.redress2.entity.refactoring.formulas.mf.MPCMoveFieldPF;
import edu.wayne.cs.severe.redress2.entity.refactoring.formulas.mf.NOMMoveFieldPF;
import edu.wayne.cs.severe.redress2.entity.refactoring.formulas.mf.RFCMoveFieldPF;
import edu.wayne.cs.severe.redress2.entity.refactoring.json.JSONRefParam;
import edu.wayne.cs.severe.redress2.exception.RefactoringException;
import edu.wayne.cs.severe.redress2.utils.RefactoringUtils;

/**
 * @author ojcchar
 * @version 1.0
 * @created 28-Mar-2014 17:27:28
 */
public class MoveField extends RefactoringType {

	public MoveField(List<TypeDeclaration> sysTypeDcls, ProgLang lang) {
		super(sysTypeDcls);
		formulas.put(new LOCMetric().getMetricAcronym(), new LOCMoveFieldPF(
				lang));
		formulas.put(new NOMMetric().getMetricAcronym(), new NOMMoveFieldPF());
		formulas.put(new RFCMetric().getMetricAcronym(), new RFCMoveFieldPF());
		formulas.put(new CBOMetric().getMetricAcronym(), new CBOMoveFieldPF());
		formulas.put(new MPCMetric().getMetricAcronym(), new MPCMoveFieldPF());
		formulas.put(new LCOM5Metric().getMetricAcronym(),
				new LCOM5MoveFieldPF());
		formulas.put(new LCOM2Metric().getMetricAcronym(),
				new LCOM2MoveFieldPF());
		formulas.put(new CYCLOMetric().getMetricAcronym(),
				new CYCLOMoveFieldPF());
		formulas.put(new DACMetric().getMetricAcronym(), new DACMoveFieldPF());
	}

	@Override
	public String getAcronym() {
		return "MF";
	}

	@Override
	public HashMap<String, List<RefactoringParameter>> getRefactoringParams(
			List<JSONRefParam> jsonParams) throws RefactoringException {

		String key = "src";
		String key2 = "tgt";
		String key3 = "fld";

		HashMap<String, JSONRefParam> idxParams = RefactoringUtils
				.validateJsonParams(jsonParams, 3, new String[] { key, key2,
						key3 }, new int[] { 1, 1, 1 });

		HashMap<String, List<RefactoringParameter>> params = new HashMap<String, List<RefactoringParameter>>();
		JSONRefParam jsonParam = idxParams.get(key);
		List<RefactoringParameter> refParams = RefactoringUtils
				.getOpersCodeObject(jsonParam, sysTypeDcls,
						TypeDeclaration.class);
		params.put(key, refParams);

		jsonParam = idxParams.get(key2);
		refParams = RefactoringUtils.getOpersCodeObject(jsonParam, sysTypeDcls,
				TypeDeclaration.class);
		params.put(key2, refParams);

		jsonParam = idxParams.get(key3);
		refParams = RefactoringUtils.getOpersCodeObject(jsonParam, sysTypeDcls,
				AttributeDeclaration.class);
		params.put(key3, refParams);

		return params;
	}

}// end MoveField