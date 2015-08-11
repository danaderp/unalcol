package edu.wayne.cs.severe.redress2.entity.refactoring.opers;

import java.util.HashMap;
import java.util.List;

import edu.wayne.cs.severe.redress2.controller.metric.CBOMetric;
import edu.wayne.cs.severe.redress2.controller.metric.DACMetric;
import edu.wayne.cs.severe.redress2.controller.metric.LCOM2Metric;
import edu.wayne.cs.severe.redress2.controller.metric.LCOM5Metric;
import edu.wayne.cs.severe.redress2.controller.metric.LOCMetric;
import edu.wayne.cs.severe.redress2.entity.AttributeDeclaration;
import edu.wayne.cs.severe.redress2.entity.TypeDeclaration;
import edu.wayne.cs.severe.redress2.entity.refactoring.RefactoringParameter;
import edu.wayne.cs.severe.redress2.entity.refactoring.formulas.puf.CBOPullUpFieldPF;
import edu.wayne.cs.severe.redress2.entity.refactoring.formulas.puf.DACPullUpFieldPF;
import edu.wayne.cs.severe.redress2.entity.refactoring.formulas.puf.LCOM2PullUpFieldPF;
import edu.wayne.cs.severe.redress2.entity.refactoring.formulas.puf.LCOM5PullUpFieldPF;
import edu.wayne.cs.severe.redress2.entity.refactoring.formulas.puf.LOCPullUpFieldPF;
import edu.wayne.cs.severe.redress2.entity.refactoring.json.JSONRefParam;
import edu.wayne.cs.severe.redress2.entity.refactoring.json.OBSERVRefParam;
import edu.wayne.cs.severe.redress2.exception.RefactoringException;
import edu.wayne.cs.severe.redress2.utils.RefactoringUtils;

/**
 * @author ojcchar
 * @version 1.0
 * @created 28-Mar-2014 17:27:28
 */
public class PullUpField extends RefactoringType {

	public PullUpField(List<TypeDeclaration> sysTypeDcls) {
		super(sysTypeDcls);
		formulas.put(new LOCMetric().getMetricAcronym(), new LOCPullUpFieldPF());
		formulas.put(new CBOMetric().getMetricAcronym(), new CBOPullUpFieldPF());
		formulas.put(new LCOM5Metric().getMetricAcronym(),
				new LCOM5PullUpFieldPF());
		formulas.put(new LCOM2Metric().getMetricAcronym(),
				new LCOM2PullUpFieldPF());
		formulas.put(new DACMetric().getMetricAcronym(), new DACPullUpFieldPF());
	}

	public String getAcronym() {
		return "PUF";
	}

	@Override
	public HashMap<String, List<RefactoringParameter>> getRefactoringParams(
			List<JSONRefParam> jsonParams) throws RefactoringException {

		String key = "src";
		String key2 = "tgt";
		String key3 = "fld";

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
				AttributeDeclaration.class);
		params.put(key3, refParams);

		return params;
	}

	@Override
	public HashMap<String, List<RefactoringParameter>> getOBSERVRefactoringParams(List<OBSERVRefParam> jsonParams)
			throws RefactoringException {
		// TODO Auto-generated method stub
		return null;
	}

}// end PullUpField