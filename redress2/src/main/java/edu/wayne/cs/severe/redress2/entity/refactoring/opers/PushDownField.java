package edu.wayne.cs.severe.redress2.entity.refactoring.opers;

import java.util.HashMap;
import java.util.List;

import edu.wayne.cs.severe.redress2.controller.metric.CBOMetric;
import edu.wayne.cs.severe.redress2.controller.metric.CYCLOMetric;
import edu.wayne.cs.severe.redress2.controller.metric.DACMetric;
import edu.wayne.cs.severe.redress2.controller.metric.LCOM2Metric;
import edu.wayne.cs.severe.redress2.controller.metric.LCOM5Metric;
import edu.wayne.cs.severe.redress2.controller.metric.LOCMetric;
import edu.wayne.cs.severe.redress2.controller.metric.NOMMetric;
import edu.wayne.cs.severe.redress2.controller.metric.RFCMetric;
import edu.wayne.cs.severe.redress2.entity.AttributeDeclaration;
import edu.wayne.cs.severe.redress2.entity.ProgLang;
import edu.wayne.cs.severe.redress2.entity.TypeDeclaration;
import edu.wayne.cs.severe.redress2.entity.refactoring.RefactoringParameter;
import edu.wayne.cs.severe.redress2.entity.refactoring.formulas.pdf.CBOPushDownFieldPF;
import edu.wayne.cs.severe.redress2.entity.refactoring.formulas.pdf.CYCLOPushDownFieldPF;
import edu.wayne.cs.severe.redress2.entity.refactoring.formulas.pdf.DACPushDownFieldPF;
import edu.wayne.cs.severe.redress2.entity.refactoring.formulas.pdf.LCOM2PushDownFieldPF;
import edu.wayne.cs.severe.redress2.entity.refactoring.formulas.pdf.LCOM5PushDownFieldPF;
import edu.wayne.cs.severe.redress2.entity.refactoring.formulas.pdf.LOCPushDownFieldPF;
import edu.wayne.cs.severe.redress2.entity.refactoring.formulas.pdf.NOMPushDownFieldPF;
import edu.wayne.cs.severe.redress2.entity.refactoring.formulas.pdf.RFCPushDownFieldPF;
import edu.wayne.cs.severe.redress2.entity.refactoring.json.JSONRefParam;
import edu.wayne.cs.severe.redress2.entity.refactoring.json.OBSERVRefParam;
import edu.wayne.cs.severe.redress2.exception.RefactoringException;
import edu.wayne.cs.severe.redress2.utils.RefactoringUtils;

public class PushDownField extends RefactoringType {

	public PushDownField(List<TypeDeclaration> sysTypeDcls, ProgLang lang) {
		super(sysTypeDcls);
		formulas.put(new LOCMetric().getMetricAcronym(),
				new LOCPushDownFieldPF(lang));
		formulas.put(new CBOMetric().getMetricAcronym(),
				new CBOPushDownFieldPF());
		formulas.put(new LCOM5Metric().getMetricAcronym(),
				new LCOM5PushDownFieldPF());
		formulas.put(new LCOM2Metric().getMetricAcronym(),
				new LCOM2PushDownFieldPF());
		formulas.put(new DACMetric().getMetricAcronym(),
				new DACPushDownFieldPF());
		// --------
		formulas.put(new RFCMetric().getMetricAcronym(),
				new RFCPushDownFieldPF());
		formulas.put(new NOMMetric().getMetricAcronym(),
				new NOMPushDownFieldPF());
		formulas.put(new CYCLOMetric().getMetricAcronym(),
				new CYCLOPushDownFieldPF());
	}

	@Override
	public String getAcronym() {
		return "PDF";
	}

	@Override
	public HashMap<String, List<RefactoringParameter>> getRefactoringParams(
			List<JSONRefParam> jsonParams) throws RefactoringException {

		String key = "src";
		String key2 = "tgt";
		String key3 = "fld";

		HashMap<String, JSONRefParam> idxParams = RefactoringUtils
				.validateJsonParams(jsonParams, 3, new String[] { key, key2,
						key3 }, new int[] { 1, 0, 1 });

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

}
