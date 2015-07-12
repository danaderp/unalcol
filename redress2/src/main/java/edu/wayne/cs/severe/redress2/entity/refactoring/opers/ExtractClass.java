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
import edu.wayne.cs.severe.redress2.controller.metric.NOMMetric;
import edu.wayne.cs.severe.redress2.controller.metric.RFCMetric;
import edu.wayne.cs.severe.redress2.entity.ProgLang;
import edu.wayne.cs.severe.redress2.entity.TypeDeclaration;
import edu.wayne.cs.severe.redress2.entity.refactoring.RefactoringParameter;
import edu.wayne.cs.severe.redress2.entity.refactoring.formulas.ec.CBOExctractClassPF;
import edu.wayne.cs.severe.redress2.entity.refactoring.formulas.ec.CYCLOExctractClassPF;
import edu.wayne.cs.severe.redress2.entity.refactoring.formulas.ec.DACExctractClassPF;
import edu.wayne.cs.severe.redress2.entity.refactoring.formulas.ec.LCOM2ExctractClassPF;
import edu.wayne.cs.severe.redress2.entity.refactoring.formulas.ec.LCOM5ExctractClassPF;
import edu.wayne.cs.severe.redress2.entity.refactoring.formulas.ec.LOCExctractClassPF;
import edu.wayne.cs.severe.redress2.entity.refactoring.formulas.ec.MPCExctractClassPF;
import edu.wayne.cs.severe.redress2.entity.refactoring.formulas.ec.NOMExctractClassPF;
import edu.wayne.cs.severe.redress2.entity.refactoring.formulas.ec.RFCExctractClassPF;
import edu.wayne.cs.severe.redress2.entity.refactoring.json.JSONRefParam;
import edu.wayne.cs.severe.redress2.exception.RefactoringException;

/**
 * @author ojcchar
 * @version 1.0
 * @created 28-Mar-2014 17:27:27
 */
public class ExtractClass extends RefactoringType {

	public ExtractClass(List<TypeDeclaration> sysTypeDcls, ProgLang lang,
			HierarchyBuilder builder) {
		super(sysTypeDcls);
		formulas.put(new LOCMetric().getMetricAcronym(),
				new LOCExctractClassPF(lang));
		formulas.put(new NOMMetric().getMetricAcronym(),
				new NOMExctractClassPF());
		formulas.put(new RFCMetric().getMetricAcronym(),
				new RFCExctractClassPF());
		formulas.put(new CBOMetric().getMetricAcronym(),
				new CBOExctractClassPF(builder));
		formulas.put(new MPCMetric().getMetricAcronym(),
				new MPCExctractClassPF());
		formulas.put(new LCOM5Metric().getMetricAcronym(),
				new LCOM5ExctractClassPF());
		formulas.put(new LCOM2Metric().getMetricAcronym(),
				new LCOM2ExctractClassPF());
		formulas.put(new CYCLOMetric().getMetricAcronym(),
				new CYCLOExctractClassPF());
		formulas.put(new DACMetric().getMetricAcronym(),
				new DACExctractClassPF());
	}

	public String getAcronym() {
		return "EC";
	}

	@Override
	public HashMap<String, List<RefactoringParameter>> getRefactoringParams(
			List<JSONRefParam> params) throws RefactoringException {
		return null;
	}

}// end ExtractClass