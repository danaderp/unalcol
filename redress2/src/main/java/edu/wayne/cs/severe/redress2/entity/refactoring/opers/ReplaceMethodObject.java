package edu.wayne.cs.severe.redress2.entity.refactoring.opers;

import java.util.HashMap;
import java.util.List;

import edu.wayne.cs.severe.redress2.controller.HierarchyBuilder;
import edu.wayne.cs.severe.redress2.controller.metric.CBOMetric;
import edu.wayne.cs.severe.redress2.controller.metric.CYCLOMetric;
import edu.wayne.cs.severe.redress2.controller.metric.DACMetric;
import edu.wayne.cs.severe.redress2.controller.metric.LCOM2Metric;
import edu.wayne.cs.severe.redress2.controller.metric.LCOM5Metric;
import edu.wayne.cs.severe.redress2.controller.metric.LOCMetric;
import edu.wayne.cs.severe.redress2.controller.metric.MPCMetric;
import edu.wayne.cs.severe.redress2.controller.metric.NOCMetric;
import edu.wayne.cs.severe.redress2.controller.metric.NOMMetric;
import edu.wayne.cs.severe.redress2.controller.metric.RFCMetric;
import edu.wayne.cs.severe.redress2.entity.MethodDeclaration;
import edu.wayne.cs.severe.redress2.entity.ProgLang;
import edu.wayne.cs.severe.redress2.entity.TypeDeclaration;
import edu.wayne.cs.severe.redress2.entity.refactoring.RefactoringParameter;
import edu.wayne.cs.severe.redress2.entity.refactoring.formulas.rmmo.CBOReplaceMethodObjectPF;
import edu.wayne.cs.severe.redress2.entity.refactoring.formulas.rmmo.CYCLOReplaceMethodObjectPF;
import edu.wayne.cs.severe.redress2.entity.refactoring.formulas.rmmo.DACReplaceMethodObjectPF;
import edu.wayne.cs.severe.redress2.entity.refactoring.formulas.rmmo.LCOM2ReplaceMethodObjectPF;
import edu.wayne.cs.severe.redress2.entity.refactoring.formulas.rmmo.LCOM5ReplaceMethodObjectPF;
import edu.wayne.cs.severe.redress2.entity.refactoring.formulas.rmmo.LOCReplaceMethodObjectPF;
import edu.wayne.cs.severe.redress2.entity.refactoring.formulas.rmmo.MPCReplaceMethodObjectPF;
import edu.wayne.cs.severe.redress2.entity.refactoring.formulas.rmmo.NOCReplaceMethodObjectPF;
import edu.wayne.cs.severe.redress2.entity.refactoring.formulas.rmmo.NOMReplaceMethodObjectPF;
import edu.wayne.cs.severe.redress2.entity.refactoring.formulas.rmmo.RFCReplaceMethodObjectPF;
import edu.wayne.cs.severe.redress2.entity.refactoring.json.JSONRefParam;
import edu.wayne.cs.severe.redress2.entity.refactoring.json.OBSERVRefParam;
import edu.wayne.cs.severe.redress2.exception.RefactoringException;
import edu.wayne.cs.severe.redress2.utils.RefactoringUtils;

/**
 * @author ojcchar
 * @version 1.0
 * @created 28-Mar-2014 17:27:28
 */
public class ReplaceMethodObject extends RefactoringType {

	public ReplaceMethodObject(List<TypeDeclaration> sysTypeDcls,
			ProgLang lang, HierarchyBuilder builder) {
		super(sysTypeDcls);
		formulas.put(new LOCMetric().getMetricAcronym(),
				new LOCReplaceMethodObjectPF(lang));
		formulas.put(new NOMMetric().getMetricAcronym(),
				new NOMReplaceMethodObjectPF());
		formulas.put(new RFCMetric().getMetricAcronym(),
				new RFCReplaceMethodObjectPF());
		formulas.put(new CBOMetric().getMetricAcronym(),
				new CBOReplaceMethodObjectPF(builder));
		formulas.put(new MPCMetric().getMetricAcronym(),
				new MPCReplaceMethodObjectPF());
		formulas.put(new LCOM5Metric().getMetricAcronym(),
				new LCOM5ReplaceMethodObjectPF());
		formulas.put(new LCOM2Metric().getMetricAcronym(),
				new LCOM2ReplaceMethodObjectPF());
		formulas.put(new NOCMetric().getMetricAcronym(),
				new NOCReplaceMethodObjectPF());
		formulas.put(new CYCLOMetric().getMetricAcronym(),
				new CYCLOReplaceMethodObjectPF());
		formulas.put(new DACMetric().getMetricAcronym(),
				new DACReplaceMethodObjectPF());
	}

	@Override
	public String getAcronym() {
		return "RMMO";

	}

	@Override
	public HashMap<String, List<RefactoringParameter>> getRefactoringParams(
			List<JSONRefParam> jsonParams) throws RefactoringException {

		String srcKey = "src";
		String tgtKey = "tgt";
		String mtdKey = "mtd";

		HashMap<String, JSONRefParam> idxParams = RefactoringUtils
				.validateJsonParams(jsonParams, 3, new String[] { srcKey,
						tgtKey, mtdKey }, new int[] { 1, 1, 1 });

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
}// end ReplaceMethodObject