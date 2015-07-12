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
import edu.wayne.cs.severe.redress2.entity.refactoring.formulas.rid.DACReplaceInhDelegPF;
import edu.wayne.cs.severe.redress2.entity.refactoring.formulas.rid.DITReplaceInhDelegPF;
import edu.wayne.cs.severe.redress2.entity.refactoring.formulas.rid.LCOM2ReplaceInhDelegPF;
import edu.wayne.cs.severe.redress2.entity.refactoring.formulas.rid.LCOM5ReplaceInhDelegPF;
import edu.wayne.cs.severe.redress2.entity.refactoring.formulas.rid.LOCReplaceInhDelegPF;
import edu.wayne.cs.severe.redress2.entity.refactoring.formulas.rid.NOCReplaceInhDelegPF;
import edu.wayne.cs.severe.redress2.entity.refactoring.json.JSONRefParam;
import edu.wayne.cs.severe.redress2.exception.RefactoringException;
import edu.wayne.cs.severe.redress2.utils.RefactoringUtils;

public class ReplaceInheritanceDelegation extends RefactoringType {

	public ReplaceInheritanceDelegation(List<TypeDeclaration> sysTypeDcls, HierarchyBuilder builder) {
		super(sysTypeDcls);

		formulas.put(new LOCMetric().getMetricAcronym(),
				new LOCReplaceInhDelegPF());
		formulas.put(new DITMetric().getMetricAcronym(),
				new DITReplaceInhDelegPF(builder));
		formulas.put(new LCOM5Metric().getMetricAcronym(),
				new LCOM5ReplaceInhDelegPF());
		formulas.put(new LCOM2Metric().getMetricAcronym(),
				new LCOM2ReplaceInhDelegPF());
		formulas.put(new NOCMetric().getMetricAcronym(),
				new NOCReplaceInhDelegPF());
		formulas.put(new DACMetric().getMetricAcronym(),
				new DACReplaceInhDelegPF());
	}

	@Override
	public String getAcronym() {
		return "RID";
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

}
