package edu.wayne.cs.severe.redress2.entity.refactoring.formulas.ec;

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
import edu.wayne.cs.severe.redress2.entity.MethodDeclaration;
import edu.wayne.cs.severe.redress2.entity.TypeDeclaration;
import edu.wayne.cs.severe.redress2.entity.refactoring.RefactoringOperation;
import edu.wayne.cs.severe.redress2.entity.refactoring.formulas.PredictionFormula;
import edu.wayne.cs.severe.redress2.entity.refactoring.formulas.mf.LCOM5MoveFieldPF;
import edu.wayne.cs.severe.redress2.entity.refactoring.formulas.mf.MoveFieldPredFormula;
import edu.wayne.cs.severe.redress2.entity.refactoring.formulas.mm.LCOM5MoveMethodPF;
import edu.wayne.cs.severe.redress2.entity.refactoring.formulas.mm.MoveMethodPredFormula;
import edu.wayne.cs.severe.redress2.entity.refactoring.opers.MoveField;
import edu.wayne.cs.severe.redress2.entity.refactoring.opers.MoveMethod;
import edu.wayne.cs.severe.redress2.entity.refactoring.opers.RefactoringType;
import edu.wayne.cs.severe.redress2.utils.ExtractClassUtils;
import edu.wayne.cs.severe.redress2.utils.MoveFieldUtils;
import edu.wayne.cs.severe.redress2.utils.MoveMethodUtils;

public class LCOM2ExctractClassPF extends PredictionFormula {

	MoveFieldPredFormula formMF = new LCOM5MoveFieldPF();
	MoveMethodPredFormula formMM = new LCOM5MoveMethodPF();

	@Override
	public HashMap<String, Double> predictMetrVal(RefactoringOperation ref,
			LinkedHashMap<String, LinkedHashMap<String, Double>> prevMetrics)
			throws Exception {

		TypeDeclaration srcCls = formMF.getSourceClass(ref.getSubRefs().get(0));
		TypeDeclaration tgtCls = formMM.getTargetClass(ref.getSubRefs().get(0));

		HashMap<String, Double> predMetrs = new HashMap<String, Double>();
		Double valSrc = getValLCOM5Src(srcCls, ref);
		predMetrs.put(srcCls.getQualifiedName(), valSrc);

		Double valTgt = getValLCOM5Tgt(srcCls, ref);
		predMetrs.put(tgtCls.getQualifiedName(), valTgt);

		return predMetrs;
	}

	private Double getValLCOM5Tgt(TypeDeclaration typeDcl,
			RefactoringOperation ref) throws Exception {

		double n1 = ExtractClassUtils.numFieldsToMove(ref);
		double numFieldUsage = 2 * n1 + getDeltaUsageFields2(typeDcl, ref);

		double numFields = ExtractClassUtils.numFieldsToMove(ref);

		Double numMethods = 2 * n1 + numFields;

		double metric = (numMethods == 0 || numFields == 0) ? 0
				: 1 - ((numFieldUsage) / (numMethods * numFields));

		return metric;
	}

	private double getDeltaUsageFields2(TypeDeclaration typeDcl,
			RefactoringOperation ref) throws Exception {
		double delta = 0.0;

		List<RefactoringOperation> subRefs = ref.getSubRefs();

		List<AttributeDeclaration> attrs = new ArrayList<AttributeDeclaration>();
		List<MethodDeclaration> methods = new ArrayList<MethodDeclaration>();

		for (RefactoringOperation subRef : subRefs) {

			RefactoringType refType = subRef.getRefType();
			if (refType instanceof MoveField) {
				AttributeDeclaration attr = formMF.getAttribute(subRef);
				attrs.add(attr);
			} else if (refType instanceof MoveMethod) {
				MethodDeclaration method = formMM.getMethod(subRef);
				methods.add(method);
			}

		}

		for (AttributeDeclaration attr : attrs) {
			for (MethodDeclaration method : methods) {

				Integer num = MetricUtils.getFieldNumUsagesInMethod(typeDcl,
						method, new ClassField(attr.getObjName(), null));
				if (num > 0) {

					delta++;
				}
			}
		}

		return delta;
	}

	private Double getValLCOM5Src(TypeDeclaration typeDcl,
			RefactoringOperation ref) throws Exception {

		File compUnitFile = typeDcl.getCompUnit().getSrcFile();
		HashSet<String> fields = MetricUtils.getFields(typeDcl);

		double numFieldUsage = 0.0;
		for (String field : fields) {
			int numField = MetricUtils.getNumberOfMethodsUsingString(typeDcl,
					field);
			numFieldUsage += numField;
		}

		double deltaMethods = getDeltaNumMethods(typeDcl, ref);
		Double numMethods = MetricUtils.getNumberOfMethods(typeDcl,
				compUnitFile) - deltaMethods;

		double deltaFields = getDeltaFields(typeDcl, ref);
		double numFields = fields.size() - deltaFields;

		double deltaFieldUsage = getDeltaFieldUsage(typeDcl, ref);

		double metric = (numMethods == 0 || numFields == 0) ? 0
				: 1 - ((numFieldUsage - deltaFieldUsage) / (numMethods * numFields));

		return metric;
	}

	private double getDeltaFieldUsage(TypeDeclaration typeDcl,
			RefactoringOperation ref) throws Exception {
		double deltaMF = getDeltaUsageFields(typeDcl, ref);
		double deltaMM = getDeltaUsageMethods(typeDcl, ref, null);
		double deltaOther = getDeltaUsageOthers(typeDcl, ref);
		return deltaMF + deltaMM - deltaOther;
	}

	private double getDeltaUsageOthers(TypeDeclaration typeDcl,
			RefactoringOperation ref) throws Exception {
		List<MethodDeclaration> methods = ExtractClassUtils.getMethodsToMove(
				ref, new LCOM5MoveMethodPF());
		List<AttributeDeclaration> attrs = ExtractClassUtils
				.getAttributesToMove(ref, new LCOM5MoveFieldPF());
		List<MethodDeclaration> otherMethods = MetricUtils.getOtherMethods(
				methods, typeDcl);
		double numMeth = 0.0;
		for (MethodDeclaration otherMethod : otherMethods) {

			boolean counted = false;
			for (AttributeDeclaration attr : attrs) {
				Integer num = MetricUtils.getStringNumUsagesInMethod(typeDcl,
						otherMethod, attr.getObjName());
				if (num > 0) {
					numMeth++;
					counted = true;
					break;
				}
			}

			if (counted) {
				continue;
			}

			for (MethodDeclaration method : methods) {
				Integer num = MetricUtils.getStringNumUsagesInMethod(typeDcl,
						otherMethod, method.getObjName());
				if (num > 0) {
					numMeth++;
				}
			}
		}
		return numMeth;
	}

	private double getDeltaUsageFields(TypeDeclaration typeDcl,
			RefactoringOperation ref) throws Exception {
		double delta = 0.0;

		List<RefactoringOperation> subRefs = ref.getSubRefs();

		for (RefactoringOperation subRef : subRefs) {

			RefactoringType refType = subRef.getRefType();
			if (!(refType instanceof MoveField)) {
				continue;
			}

			AttributeDeclaration attr = formMF.getAttribute(subRef);

			int numMethAttr = MetricUtils.getNumberOfMethodsUsingString(
					typeDcl, attr.getObjName());

			// double deltaSetterGetter = MoveFieldUtils.getDeltaFieldsUsed(
			// new ClassField(attr.getObjName(), null), typeDcl);
			delta += (numMethAttr - 2);

		}
		return delta;
	}

	private double getDeltaUsageMethods(TypeDeclaration typeDcl,
			RefactoringOperation ref, TypeDeclaration typeSrc) throws Exception {
		double delta = 0.0;

		List<RefactoringOperation> subRefs = ref.getSubRefs();

		for (RefactoringOperation subRef : subRefs) {

			RefactoringType refType = subRef.getRefType();
			if (!(refType instanceof MoveMethod)) {
				continue;
			}

			MethodDeclaration method = formMM.getMethod(subRef);

			int numAttrMethod = MoveMethodUtils.getNumAttrsUsedByMethod(
					typeDcl, method, typeSrc);

			delta += (numAttrMethod);

		}
		return delta;
	}

	private double getDeltaFields(TypeDeclaration typeDcl,
			RefactoringOperation ref) {
		double n1 = ExtractClassUtils.numFieldsToMove(ref);
		return n1 - 1;
	}

	private double getDeltaNumMethods(TypeDeclaration typeDcl,
			RefactoringOperation ref) throws Exception {
		double n1 = ExtractClassUtils.numMethodsToMove(ref);
		double deltaMethods = getDeltaMoveField(typeDcl, ref);
		return n1 - deltaMethods;
	}

	private double getDeltaMoveField(TypeDeclaration typeDcl,
			RefactoringOperation ref) throws Exception {

		double delta = 0.0;

		List<RefactoringOperation> subRefs = ref.getSubRefs();

		for (RefactoringOperation subRef : subRefs) {

			RefactoringType refType = subRef.getRefType();
			if (!(refType instanceof MoveField)) {
				continue;
			}

			AttributeDeclaration attr = formMF.getAttribute(subRef);

			double numMethAttr = MoveFieldUtils.getDeltaFieldsUsed(
					new ClassField(attr.getObjName(), null), typeDcl);

			delta += (numMethAttr);

		}
		return delta;
	}

	@Override
	public CodeMetric getMetric() {
		return new LCOM2Metric();
	}

}
