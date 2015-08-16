package edu.wayne.cs.severe.redress2.entity.refactoring.opers;

import java.util.HashMap;
import java.util.List;

import edu.wayne.cs.severe.redress2.controller.metric.CYCLOMetric;
import edu.wayne.cs.severe.redress2.controller.metric.LCOM2Metric;
import edu.wayne.cs.severe.redress2.controller.metric.LCOM5Metric;
import edu.wayne.cs.severe.redress2.controller.metric.LOCMetric;
import edu.wayne.cs.severe.redress2.controller.metric.NOMMetric;
import edu.wayne.cs.severe.redress2.controller.metric.RFCMetric;
import edu.wayne.cs.severe.redress2.entity.MethodDeclaration;
import edu.wayne.cs.severe.redress2.entity.ProgLang;
import edu.wayne.cs.severe.redress2.entity.TypeDeclaration;
import edu.wayne.cs.severe.redress2.entity.refactoring.RefactoringParameter;
import edu.wayne.cs.severe.redress2.entity.refactoring.formulas.im.CYCLOInlineMethodPF;
import edu.wayne.cs.severe.redress2.entity.refactoring.formulas.im.LCOM2InlineMethodPF;
import edu.wayne.cs.severe.redress2.entity.refactoring.formulas.im.LCOM5InlineMethodPF;
import edu.wayne.cs.severe.redress2.entity.refactoring.formulas.im.LOCInlineMethodPF;
import edu.wayne.cs.severe.redress2.entity.refactoring.formulas.im.NOMInlineMethodPF;
import edu.wayne.cs.severe.redress2.entity.refactoring.formulas.im.RFCInlineMethodPF;
import edu.wayne.cs.severe.redress2.entity.refactoring.json.JSONRefParam;
import edu.wayne.cs.severe.redress2.entity.refactoring.json.OBSERVRefParam;
import edu.wayne.cs.severe.redress2.exception.RefactoringException;
import edu.wayne.cs.severe.redress2.utils.RefactoringUtils;

public class InlineMethod extends RefactoringType {

	public InlineMethod(List<TypeDeclaration> sysTypeDcls, ProgLang lang) {
		super(sysTypeDcls);
		formulas.put(new LOCMetric().getMetricAcronym(), new LOCInlineMethodPF(
				lang));
		formulas.put(new NOMMetric().getMetricAcronym(),
				new NOMInlineMethodPF());
		formulas.put(new RFCMetric().getMetricAcronym(),
				new RFCInlineMethodPF());
		formulas.put(new LCOM5Metric().getMetricAcronym(),
				new LCOM5InlineMethodPF());
		formulas.put(new LCOM2Metric().getMetricAcronym(),
				new LCOM2InlineMethodPF());
		formulas.put(new CYCLOMetric().getMetricAcronym(),
				new CYCLOInlineMethodPF());
	}

	@Override
	public String getAcronym() {
		return "IM";
	}

	@Override
	public HashMap<String, List<RefactoringParameter>> getRefactoringParams(
			List<JSONRefParam> jsonParams) throws RefactoringException {
		String srcKey = "src";
		String mtdKey = "mtd";

		HashMap<String, JSONRefParam> idxParams = RefactoringUtils
				.validateJsonParams(jsonParams, 2, new String[] { srcKey,
						mtdKey }, new int[] { 1, 1 });

		HashMap<String, List<RefactoringParameter>> params = new HashMap<String, List<RefactoringParameter>>();
		JSONRefParam jsonParam = idxParams.get(srcKey);
		List<RefactoringParameter> refParams = RefactoringUtils
				.getOpersCodeObject(jsonParam, sysTypeDcls,
						TypeDeclaration.class);
		params.put(srcKey, refParams);

		jsonParam = idxParams.get(mtdKey);
		refParams = RefactoringUtils.getOpersCodeObject(jsonParam, sysTypeDcls,
				MethodDeclaration.class);
		params.put(mtdKey, refParams);

		return params;
	}

	@Override
	public HashMap<String, List<RefactoringParameter>> getOBSERVRefactoringParams(List<OBSERVRefParam> jsonParams)
			throws RefactoringException {
		// TODO Auto-generated method stub
		return null;
	}

}
