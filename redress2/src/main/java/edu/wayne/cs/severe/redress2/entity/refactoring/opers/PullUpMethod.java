package edu.wayne.cs.severe.redress2.entity.refactoring.opers;

import java.util.HashMap;
import java.util.List;

import edu.wayne.cs.severe.redress2.controller.HierarchyBuilder;
import edu.wayne.cs.severe.redress2.controller.metric.CBOMetric;
import edu.wayne.cs.severe.redress2.controller.metric.CYCLOMetric;
import edu.wayne.cs.severe.redress2.controller.metric.LCOM2Metric;
import edu.wayne.cs.severe.redress2.controller.metric.LCOM5Metric;
import edu.wayne.cs.severe.redress2.controller.metric.LOCMetric;
import edu.wayne.cs.severe.redress2.controller.metric.MPCMetric;
import edu.wayne.cs.severe.redress2.controller.metric.NOMMetric;
import edu.wayne.cs.severe.redress2.controller.metric.RFCMetric;
import edu.wayne.cs.severe.redress2.entity.MethodDeclaration;
import edu.wayne.cs.severe.redress2.entity.ProgLang;
import edu.wayne.cs.severe.redress2.entity.TypeDeclaration;
import edu.wayne.cs.severe.redress2.entity.refactoring.RefactoringParameter;
import edu.wayne.cs.severe.redress2.entity.refactoring.formulas.pum.CBOPullUpMethodPF;
import edu.wayne.cs.severe.redress2.entity.refactoring.formulas.pum.CYCLOPullUpMethodPF;
import edu.wayne.cs.severe.redress2.entity.refactoring.formulas.pum.LCOM2PullUpMethodPF;
import edu.wayne.cs.severe.redress2.entity.refactoring.formulas.pum.LCOM5PullUpMethodPF;
import edu.wayne.cs.severe.redress2.entity.refactoring.formulas.pum.LOCPullUpMethodPF;
import edu.wayne.cs.severe.redress2.entity.refactoring.formulas.pum.MPCPullUpMethodPF;
import edu.wayne.cs.severe.redress2.entity.refactoring.formulas.pum.NOMPullUpMethodPF;
import edu.wayne.cs.severe.redress2.entity.refactoring.formulas.pum.RFCPullUpMethodPF;
import edu.wayne.cs.severe.redress2.entity.refactoring.json.JSONRefParam;
import edu.wayne.cs.severe.redress2.exception.RefactoringException;
import edu.wayne.cs.severe.redress2.utils.RefactoringUtils;

public class PullUpMethod extends RefactoringType {

	public PullUpMethod(List<TypeDeclaration> sysTypeDcls, ProgLang lang,
			HierarchyBuilder builder) {
		super(sysTypeDcls);
		formulas.put(new LOCMetric().getMetricAcronym(), new LOCPullUpMethodPF(
				lang));
		formulas.put(new NOMMetric().getMetricAcronym(),
				new NOMPullUpMethodPF());
		formulas.put(new RFCMetric().getMetricAcronym(),
				new RFCPullUpMethodPF());
		formulas.put(new CBOMetric().getMetricAcronym(), new CBOPullUpMethodPF(
				builder));
		formulas.put(new MPCMetric().getMetricAcronym(),
				new MPCPullUpMethodPF());
		formulas.put(new LCOM5Metric().getMetricAcronym(),
				new LCOM5PullUpMethodPF());
		formulas.put(new LCOM2Metric().getMetricAcronym(),
				new LCOM2PullUpMethodPF());
		formulas.put(new CYCLOMetric().getMetricAcronym(),
				new CYCLOPullUpMethodPF());
	}

	@Override
	public String getAcronym() {
		return "PUM";
	}

	@Override
	public HashMap<String, List<RefactoringParameter>> getRefactoringParams(
			List<JSONRefParam> jsonParams) throws RefactoringException {

		String key = "src";
		String key2 = "tgt";
		String key3 = "mtd";

		HashMap<String, JSONRefParam> idxParams = RefactoringUtils
				.validateJsonParams(jsonParams, 3, new String[] { key, key2,
						key3 }, new int[] { 0, 1, 1 });

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
				MethodDeclaration.class);
		params.put(key3, refParams);

		return params;
	}

}
