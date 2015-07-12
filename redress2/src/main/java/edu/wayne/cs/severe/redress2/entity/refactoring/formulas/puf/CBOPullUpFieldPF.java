package edu.wayne.cs.severe.redress2.entity.refactoring.formulas.puf;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import edu.wayne.cs.severe.redress2.controller.metric.CBOMetric;
import edu.wayne.cs.severe.redress2.controller.metric.CodeMetric;
import edu.wayne.cs.severe.redress2.entity.AttributeDeclaration;
import edu.wayne.cs.severe.redress2.entity.TypeDeclaration;
import edu.wayne.cs.severe.redress2.entity.refactoring.RefactoringOperation;
import edu.wayne.cs.severe.redress2.utils.RefactoringUtils;

public class CBOPullUpFieldPF extends PullUpFieldPredFormula {

	@Override
	public HashMap<String, Double> predictMetrVal(RefactoringOperation ref,
			LinkedHashMap<String, LinkedHashMap<String, Double>> prevMetrics)
			throws Exception {

		List<TypeDeclaration> srcClses = getSourceClasses(ref);
		AttributeDeclaration attr = getAttribute(ref);
		TypeDeclaration tgtCls = getTargetClass(ref);
		CodeMetric metric = getMetric();

		// process the subclasses
		HashMap<String, Double> predMetrs = new HashMap<String, Double>();
		for (TypeDeclaration srcCls : srcClses) {
			Double prevMetr = prevMetrics.get(srcCls.getQualifiedName()).get(
					metric.getMetricAcronym());

			Double deltaSrc = RefactoringUtils.getDelta4(srcCls, attr, tgtCls);
			predMetrs.put(srcCls.getQualifiedName(), prevMetr + deltaSrc);
		}

		// process the superclass
		Double prevMetrTgt = prevMetrics.get(tgtCls.getQualifiedName()).get(
				metric.getMetricAcronym());
		predMetrs.put(tgtCls.getQualifiedName(), prevMetrTgt);

		return predMetrs;
	}

	public CodeMetric getMetric() {
		return new CBOMetric();
	}

}
