package edu.wayne.cs.severe.redress2.entity.refactoring.formulas.pdf;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;

import edu.wayne.cs.severe.redress2.controller.MetricUtils;
import edu.wayne.cs.severe.redress2.controller.metric.CodeMetric;
import edu.wayne.cs.severe.redress2.controller.metric.LCOM2Metric;
import edu.wayne.cs.severe.redress2.entity.AttributeDeclaration;
import edu.wayne.cs.severe.redress2.entity.ClassField;
import edu.wayne.cs.severe.redress2.entity.TypeDeclaration;
import edu.wayne.cs.severe.redress2.entity.refactoring.RefactoringOperation;
import edu.wayne.cs.severe.redress2.utils.PullUpMethodUtils;

public class LCOM2PushDownFieldPF extends PushDownFieldPredFormua {

	@Override
	public HashMap<String, Double> predictMetrVal(RefactoringOperation ref,
			LinkedHashMap<String, LinkedHashMap<String, Double>> prevMetrics)
			throws Exception {

		TypeDeclaration srcCls = getSourceClass(ref);
		AttributeDeclaration attr = getAttribute(ref);
		List<TypeDeclaration> tgtClses = getTargetClasses(ref);

		HashMap<String, Double> predMetrs = new HashMap<String, Double>();

		// delta of the fields used by the method
		ClassField classField = new ClassField(attr.getObjName(), null);
		List<ClassField> usedFieldsSrc = new ArrayList<ClassField>();
		usedFieldsSrc.add(classField);
		double deltaFieldsUsed = PullUpMethodUtils.getDeltaFieldsUsed(
				usedFieldsSrc, srcCls);

		predMetrs.put(srcCls.getQualifiedName(),
				getValLCOM(srcCls, attr, -1, deltaFieldsUsed, false));

		for (TypeDeclaration tgtCls : tgtClses) {

			// compute the delta and set the metric value
			double deltaFieldsTgt = PullUpMethodUtils.getDeltaFieldsUsed(
					usedFieldsSrc, tgtCls);

			predMetrs.put(tgtCls.getQualifiedName(),
					getValLCOM(tgtCls, attr, 1, deltaFieldsTgt, true));
		}

		return predMetrs;
	}

	private Double getValLCOM(TypeDeclaration typeDcl,
			AttributeDeclaration attr, int mult, double deltaFieldsUsed,
			boolean isTgt) throws Exception {

		File compUnitFile = typeDcl.getCompUnit().getSrcFile();
		HashSet<String> fields = MetricUtils.getFields(typeDcl);

		double numFieldUsage = 0.0;
		for (String field : fields) {
			int numField = MetricUtils.getNumberOfMethodsUsingString(typeDcl,
					field);
			numFieldUsage += numField;
		}

		double numFields = fields.size() + mult * 1;
		Double numMethods = MetricUtils.getNumberOfMethods(typeDcl,
				compUnitFile) + deltaFieldsUsed;

		int numMethAttr = MetricUtils.getNumberOfMethodsUsingString(typeDcl,
				attr.getObjName());

		double metric = (numMethods == 0 || numFields == 0) ? 0
				: 1 - ((numFieldUsage + mult * numMethAttr + (isTgt ? deltaFieldsUsed
						: 0)) / (numMethods * numFields));

		return metric;
	}

	public CodeMetric getMetric() {
		return new LCOM2Metric();
	}

}
