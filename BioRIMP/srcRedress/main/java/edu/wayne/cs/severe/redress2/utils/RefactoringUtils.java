package edu.wayne.cs.severe.redress2.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import edu.wayne.cs.severe.redress2.controller.MetricUtils;
import edu.wayne.cs.severe.redress2.controller.metric.CodeMetric;
import edu.wayne.cs.severe.redress2.entity.AttributeDeclaration;
import edu.wayne.cs.severe.redress2.entity.ClassField;
import edu.wayne.cs.severe.redress2.entity.CodeObject;
import edu.wayne.cs.severe.redress2.entity.MethodDeclaration;
import edu.wayne.cs.severe.redress2.entity.TypeDeclaration;
import edu.wayne.cs.severe.redress2.entity.refactoring.CodeObjState;
import edu.wayne.cs.severe.redress2.entity.refactoring.RefactoringOperation;
import edu.wayne.cs.severe.redress2.entity.refactoring.RefactoringParameter;
import edu.wayne.cs.severe.redress2.entity.refactoring.formulas.PredictionFormula;
import edu.wayne.cs.severe.redress2.entity.refactoring.json.JSONRefParam;
import edu.wayne.cs.severe.redress2.entity.refactoring.opers.MoveField;
import edu.wayne.cs.severe.redress2.entity.refactoring.opers.MoveMethod;
import edu.wayne.cs.severe.redress2.entity.refactoring.opers.RefactoringType;
import edu.wayne.cs.severe.redress2.exception.RefactoringException;

/**
 * @author ojcchar
 * 
 */
public class RefactoringUtils {

	static HashMap<String, CodeObjState> objStates = new HashMap<String, CodeObjState>();

	static {
		objStates.put("N", CodeObjState.NEW);
		objStates.put("NP", CodeObjState.NOT_PROVIDED);
		objStates.put("E", CodeObjState.EXISTS);
	}

	public static HashMap<String, JSONRefParam> validateJsonParams(
			List<JSONRefParam> jsonParams, int numParams,
			String[] expectedParams, int[] expectedParamSize)
			throws RefactoringException {

		if (jsonParams == null) {
			throw new RefactoringException("No JSON params");
		}

		if (jsonParams.size() != numParams) {
			throw new RefactoringException("The size of the JSON params ("
					+ jsonParams.size() + ") is not the required one ("
					+ numParams + ")");
		}

		HashMap<String, JSONRefParam> map = new HashMap<String, JSONRefParam>();
		for (JSONRefParam jsonRefParam : jsonParams) {
			if (jsonRefParam != null) {
				map.put(jsonRefParam.getName(), jsonRefParam);
			}
		}

		for (int i = 0; i < expectedParams.length; i++) {
			String expParam = expectedParams[i];
			int expPrSize = expectedParamSize[i];

			JSONRefParam param = map.get(expParam);
			if (param == null
					|| param.getValue() == null
					|| ((expPrSize != 0) ? param.getValue().size() != expPrSize
							: param.getValue().isEmpty())) {
				throw new RefactoringException("The " + expParam
						+ " parameter is incorrect");
			}
		}

		return map;

	}

	public static List<RefactoringParameter> getOpersCodeObject(
			JSONRefParam srcParam, List<TypeDeclaration> sysTypeDcls,
			Class<? extends CodeObject> objClass) throws RefactoringException {

		List<String> objsValues = srcParam.getValue();

		List<RefactoringParameter> values = new ArrayList<RefactoringParameter>();
		for (String obj : objsValues) {

			String[] objSplit = obj.split("\\|");

			String objName = objSplit[0];
			String objSt = "E";
			if (objSplit.length > 1) {
				objSt = objSplit[1];
			}

			CodeObjState objState = objStates.get(objSt);

			if (objState == null) {
				throw new RefactoringException("The object state " + objSt
						+ " is invalid");
			}

			CodeObject codeObj = null;

			if (TypeDeclaration.class.equals(objClass)) {
				codeObj = getObjectClass(objName, objState, sysTypeDcls);
				if (codeObj == null) {
					throw new RefactoringException("Class doesn't exist: "
							+ objName);
				}
			} else if (AttributeDeclaration.class.equals(objClass)) {
				codeObj = new AttributeDeclaration(objName);
			} else if (MethodDeclaration.class.equals(objClass)) {
				codeObj = new MethodDeclaration(objName);
			}

			RefactoringParameter param = new RefactoringParameter(codeObj,
					objState);
			values.add(param);
		}
		return values;
	}

	private static CodeObject getObjectClass(String objName,
			CodeObjState objState, List<TypeDeclaration> sysTypeDcls) {

		CodeObject obj = new TypeDeclaration("", objName);
		if (CodeObjState.NEW == objState
				|| CodeObjState.NOT_PROVIDED == objState) {
			return obj;
		}

		for (TypeDeclaration typeDecl : sysTypeDcls) {
			if (typeDecl.getQualifiedName().equals(objName)) {
				return typeDecl;
			}
		}

		return null;
	}

	public static HashMap<String, Double> computePredMetrExtractClass(
			LinkedHashMap<String, LinkedHashMap<String, Double>> prevMetrics,
			RefactoringOperation ref, CodeMetric metric,
			PredictionFormula preFormMF, PredictionFormula preFormMM)
			throws Exception {

		List<RefactoringOperation> subRefs = ref.getSubRefs();
		HashMap<String, Double> predMetrs = new HashMap<String, Double>();

		if (subRefs == null) {
			return predMetrs;
		}

		Set<Entry<String, LinkedHashMap<String, Double>>> entrySet = prevMetrics
				.entrySet();

		// clone the previous metrics
		LinkedHashMap<String, LinkedHashMap<String, Double>> cumulativePredMetrics = new LinkedHashMap<String, LinkedHashMap<String, Double>>();
		for (Entry<String, LinkedHashMap<String, Double>> entry : entrySet) {
			cumulativePredMetrics.put(entry.getKey(),
					new LinkedHashMap<String, Double>(entry.getValue()));
		}

		LinkedHashSet<String> allClasses = new LinkedHashSet<String>();

		// for each sub-refactoring
		for (RefactoringOperation subRef : subRefs) {

			// no move method or field
			RefactoringType refType = subRef.getRefType();
			if (!(refType instanceof MoveField)
					&& !(refType instanceof MoveMethod)) {
				continue;
			}

			// it is a move field
			if (refType instanceof MoveField && preFormMF != null) {
				// predict the metrics
				HashMap<String, Double> predictMetrVal = preFormMF
						.predictMetrVal(subRef, cumulativePredMetrics);
				// update the metrics
				updateCumulativeMetrics(cumulativePredMetrics, predictMetrVal,
						allClasses, metric);
			}

			// it is a move method
			if (refType instanceof MoveMethod && preFormMM != null) {
				// predict the metrics
				HashMap<String, Double> predictMetrVal = preFormMM
						.predictMetrVal(subRef, cumulativePredMetrics);
				// update the metrics
				updateCumulativeMetrics(cumulativePredMetrics, predictMetrVal,
						allClasses, metric);
			}

		}

		for (String cls : allClasses) {
			predMetrs.put(
					cls,
					cumulativePredMetrics.get(cls).get(
							metric.getMetricAcronym()));
		}

		return predMetrs;
	}

	/**
	 * @param cumulativePredMetrics
	 *            the cumulative metric values
	 * @param predictMetrVal
	 *            the predicted metrics
	 * @param allClasses
	 *            the map that contains all the classes involved in the
	 *            refactoring
	 * @param metric
	 *            the current metric
	 */
	private static void updateCumulativeMetrics(
			LinkedHashMap<String, LinkedHashMap<String, Double>> cumulativePredMetrics,
			HashMap<String, Double> predictMetrVal,
			LinkedHashSet<String> allClasses, CodeMetric metric) {

		String metricAcronym = metric.getMetricAcronym();

		Set<Entry<String, Double>> entrySet = predictMetrVal.entrySet();
		// traverse each entry of the predicted metrics
		for (Entry<String, Double> entry : entrySet) {

			String cls = entry.getKey();
			Double metrVal = entry.getValue();

			allClasses.add(cls);

			LinkedHashMap<String, Double> metrs = cumulativePredMetrics
					.get(cls);
			if (metrs == null) {
				metrs = new LinkedHashMap<String, Double>();
			}
			metrs.put(metricAcronym, metrVal);

			cumulativePredMetrics.put(cls, metrs);
		}
	}

	/**
	 * Basically computes | {classes used by the method } - {classes used by the
	 * other methods} |
	 * 
	 * @param typeDcl
	 * @param method
	 * @param classesUsedP
	 * @param superClasses
	 * @return
	 * @throws Exception
	 */
	public static Double getCBODelta(TypeDeclaration typeDcl,
			MethodDeclaration method, LinkedHashSet<String> classesUsedP,
			List<TypeDeclaration> superClasses) throws Exception {

		LinkedHashSet<String> classesUsed = new LinkedHashSet<String>(
				classesUsedP);

		if (typeDcl.getCompUnit() != null) {
			LinkedHashSet<String> classesUsedNoMethod = MetricUtils
					.getUsedClassesByOtherMethods(typeDcl, superClasses, method);
			classesUsed.removeAll(classesUsedNoMethod);
		}

		return (double) classesUsed.size();
	}

	/**
	 * Basically computes | {classes used by the methods } - {classes used by
	 * the other methods} |
	 * 
	 * @param typeDcl
	 * @param methods
	 * @param classesUsedP
	 * @param superClasses
	 * @param otherMethods
	 * @return
	 * @throws Exception
	 */
	public static Double getCBODeltaEC(TypeDeclaration typeDcl,
			List<MethodDeclaration> methods,
			LinkedHashSet<String> classesUsedP,
			List<TypeDeclaration> superClasses,
			List<MethodDeclaration> otherMethods) throws Exception {

		LinkedHashSet<String> classesUsed = new LinkedHashSet<String>(
				classesUsedP);

		if (typeDcl.getCompUnit() != null) {
			LinkedHashSet<String> classesUsedNoMethod = MetricUtils
					.getUsedClassesByOtherMethodsMulti(typeDcl, superClasses,
							methods, otherMethods);
			classesUsed.removeAll(classesUsedNoMethod);
		}

		return (double) classesUsed.size();
	}

	/**
	 * Returns 1 if the the subclass is not using the superclass, 0 otherwise
	 * 
	 * @param superCls
	 * @param method
	 * @param subCls
	 * @return
	 * @throws Exception
	 */
	public static Double getCBODelta2(TypeDeclaration superCls,
			MethodDeclaration method, TypeDeclaration subCls) throws Exception {

		List<TypeDeclaration> superClasses = new ArrayList<TypeDeclaration>();
		superClasses.add(superCls);
		LinkedHashSet<String> superClassesUsage = MetricUtils
				.getSuperClassesUsage(subCls, superClasses, null, false);

		return superClassesUsage.isEmpty() ? 1.0 : 0.0;
	}

	/**
	 * Returns 1 if the the subclass is not using the superclass and the method
	 * is used in other methods of the subclass, 0 otherwise
	 * 
	 * @param superCls
	 * @param method
	 * @param subCls
	 * @return
	 * @throws Exception
	 */
	public static Double getCBODelta3(TypeDeclaration superCls,
			MethodDeclaration method, TypeDeclaration subCls) throws Exception {

		List<TypeDeclaration> superClasses = new ArrayList<TypeDeclaration>();
		superClasses.add(superCls);
		LinkedHashSet<String> superClassesUsage = MetricUtils
				.getSuperClassesUsage(subCls, superClasses, null, false);

		LinkedHashSet<String> methods = MetricUtils.getMethods(subCls);
		String methodName = method.getObjName();

		boolean usingMethod = false;
		for (String methodSt : methods) {
			if (methodSt.equals(methodName)) {
				continue;
			}

			Integer num = MetricUtils.getStringNumUsagesInMethod(subCls,
					new MethodDeclaration(methodSt), methodName);
			if (num > 0) {
				usingMethod = true;
				break;
			}
		}

		return superClassesUsage.isEmpty() && usingMethod ? 1.0 : 0.0;
	}

	/**
	 * Returns 1 if the subclass is not using the superclass and it is using the
	 * attribute, 0 otherwise
	 * 
	 * @param subCls
	 * @param attr
	 * @param superCls
	 * @return
	 * @throws Exception
	 */
	public static Double getDelta4(TypeDeclaration subCls,
			AttributeDeclaration attr, TypeDeclaration superCls)
			throws Exception {

		List<TypeDeclaration> superClasses = new ArrayList<TypeDeclaration>();
		superClasses.add(superCls);
		LinkedHashSet<String> superClassesUsage = MetricUtils
				.getSuperClassesUsage(subCls, superClasses, null, false);

		boolean usingAttr = false;
		LinkedHashSet<ClassField> fields = MetricUtils.getFieldsWithType(
				subCls, true);
		for (ClassField field : fields) {
			int num = MetricUtils.getNumberOfMethodsUsingString(subCls,
					field.getName());
			if (num > 0) {
				usingAttr = true;
				break;
			}
		}

		return superClassesUsage.isEmpty() && usingAttr ? 1.0 : 0.0;

	}

	public static Double getMPCDeltaECSrc(TypeDeclaration srcCls,
			List<MethodDeclaration> methods, LinkedHashSet<String> callsMethod,
			RefactoringOperation ref, HashSet<String> methodsStr)
			throws Exception {
		double d1 = 2 * ExtractClassUtils.numFieldsToMove(ref);
		List<MethodDeclaration> otherMethods = MetricUtils.getOtherMethods(
				methods, srcCls);

		LinkedHashSet<String> callsMethodP = new LinkedHashSet<String>(
				callsMethod);

		LinkedHashSet<String> callsOthers = MetricUtils.getMethodCallsMethods(
				srcCls, otherMethods);
		callsOthers.removeAll(methodsStr);

		callsMethodP.removeAll(callsOthers);

		double d2 = callsMethodP.size();

		return d1 - d2;
	}

}
