package edu.wayne.cs.severe.redress2.entity.refactoring.opers;

import java.util.HashMap;
import java.util.List;

import edu.wayne.cs.severe.redress2.controller.HierarchyBuilder;
import edu.wayne.cs.severe.redress2.controller.metric.DACMetric;
import edu.wayne.cs.severe.redress2.controller.metric.DITMetric;
import edu.wayne.cs.severe.redress2.controller.metric.LCOM2Metric;
import edu.wayne.cs.severe.redress2.controller.metric.LCOM5Metric;
import edu.wayne.cs.severe.redress2.controller.metric.LOCMetric;
import edu.wayne.cs.severe.redress2.controller.metric.NOCMetric;
import edu.wayne.cs.severe.redress2.entity.TypeDeclaration;
import edu.wayne.cs.severe.redress2.entity.refactoring.RefactoringParameter;
import edu.wayne.cs.severe.redress2.entity.refactoring.formulas.rdi.DACReplaceDelegInhPF;
import edu.wayne.cs.severe.redress2.entity.refactoring.formulas.rdi.DITReplaceDelegInhPF;
import edu.wayne.cs.severe.redress2.entity.refactoring.formulas.rdi.LCOM2ReplaceDelegInhPF;
import edu.wayne.cs.severe.redress2.entity.refactoring.formulas.rdi.LCOM5ReplaceDelegInhPF;
import edu.wayne.cs.severe.redress2.entity.refactoring.formulas.rdi.LOCReplaceDelegInhPF;
import edu.wayne.cs.severe.redress2.entity.refactoring.formulas.rdi.NOCReplaceDelegInhPF;
import edu.wayne.cs.severe.redress2.entity.refactoring.json.JSONRefParam;
import edu.wayne.cs.severe.redress2.entity.refactoring.json.OBSERVRefParam;
import edu.wayne.cs.severe.redress2.exception.RefactoringException;
import edu.wayne.cs.severe.redress2.utils.RefactoringUtils;

public class ReplaceDelegationInheritance extends RefactoringType {

	public ReplaceDelegationInheritance(List<TypeDeclaration> sysTypeDcls, HierarchyBuilder builder) {
		super(sysTypeDcls);
		formulas.put(new LOCMetric().getMetricAcronym(),
				new LOCReplaceDelegInhPF());
		formulas.put(new DITMetric().getMetricAcronym(),
				new DITReplaceDelegInhPF(builder));
		formulas.put(new LCOM5Metric().getMetricAcronym(),
				new LCOM5ReplaceDelegInhPF());
		formulas.put(new LCOM2Metric().getMetricAcronym(),
				new LCOM2ReplaceDelegInhPF());
		formulas.put(new NOCMetric().getMetricAcronym(),
				new NOCReplaceDelegInhPF());
		formulas.put(new DACMetric().getMetricAcronym(),
				new DACReplaceDelegInhPF());
	}

	@Override
	public String getAcronym() {
		return "RDI";
	}

	@Override
	public HashMap<String, List<RefactoringParameter>> getRefactoringParams(
			List<JSONRefParam> jsonParams) throws RefactoringException {

		String srcKey = "src";
		String tgtKey = "tgt";

		HashMap<String, JSONRefParam> idxParams = RefactoringUtils
				.validateJsonParams(jsonParams, 2, new String[] { srcKey,
						tgtKey }, new int[] { 1, 1 });

		HashMap<String, List<RefactoringParameter>> params = new HashMap<String, List<RefactoringParameter>>();
		JSONRefParam jsonParam = idxParams.get(srcKey);
		List<RefactoringParameter> refParams = RefactoringUtils
				.getOpersCodeObject(jsonParam, sysTypeDcls,
						TypeDeclaration.class);
		params.put(srcKey, refParams);

		jsonParam = idxParams.get(tgtKey);
		refParams = RefactoringUtils.getOpersCodeObject(jsonParam, sysTypeDcls,
				TypeDeclaration.class);
		params.put(tgtKey, refParams);

		return params;
	}

	@Override
	public HashMap<String, List<RefactoringParameter>> getOBSERVRefactoringParams(List<OBSERVRefParam> jsonParams)
			throws RefactoringException {
		// TODO Auto-generated method stub
		return null;
	}

}
