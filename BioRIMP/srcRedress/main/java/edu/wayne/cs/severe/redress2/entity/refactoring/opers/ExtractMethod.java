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
import edu.wayne.cs.severe.redress2.entity.refactoring.formulas.em.CYCLOExtractMethodPF;
import edu.wayne.cs.severe.redress2.entity.refactoring.formulas.em.LCOM2ExtractMethodPF;
import edu.wayne.cs.severe.redress2.entity.refactoring.formulas.em.LCOM5ExtractMethodPF;
import edu.wayne.cs.severe.redress2.entity.refactoring.formulas.em.LOCExtractMethodPF;
import edu.wayne.cs.severe.redress2.entity.refactoring.formulas.em.NOMExtractMethodPF;
import edu.wayne.cs.severe.redress2.entity.refactoring.formulas.em.RFCExtractMethodPF;
import edu.wayne.cs.severe.redress2.entity.refactoring.json.JSONRefParam;
import edu.wayne.cs.severe.redress2.entity.refactoring.json.OBSERVRefParam;
import edu.wayne.cs.severe.redress2.exception.RefactoringException;
import edu.wayne.cs.severe.redress2.utils.RefactoringUtils;

public class ExtractMethod extends RefactoringType {

	public ExtractMethod(List<TypeDeclaration> sysTypeDcls, ProgLang lang) {
		super(sysTypeDcls);
		formulas.put(new LOCMetric().getMetricAcronym(),
				new LOCExtractMethodPF(lang));
		formulas.put(new NOMMetric().getMetricAcronym(),
				new NOMExtractMethodPF());
		formulas.put(new RFCMetric().getMetricAcronym(),
				new RFCExtractMethodPF());
		formulas.put(new LCOM5Metric().getMetricAcronym(),
				new LCOM5ExtractMethodPF());
		formulas.put(new LCOM2Metric().getMetricAcronym(),
				new LCOM2ExtractMethodPF());
		formulas.put(new CYCLOMetric().getMetricAcronym(),
				new CYCLOExtractMethodPF());
	}

	@Override
	public String getAcronym() {
		return "EM";
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
