package edu.wayne.cs.severe.redress2.utils;

import java.util.LinkedHashSet;
import java.util.List;

import edu.wayne.cs.severe.redress2.controller.MetricUtils;
import edu.wayne.cs.severe.redress2.entity.ClassField;
import edu.wayne.cs.severe.redress2.entity.MethodDeclaration;
import edu.wayne.cs.severe.redress2.entity.TypeDeclaration;

public class PullUpMethodUtils {

	/**
	 * Calculates the delta of field usage. If a field is used in the method to
	 * pull up, then a getter and setter should be created in the class involved
	 * 
	 * @param usedFieldsSrc
	 * @param typeDecl
	 * @return
	 * @throws Exception
	 */
	public static double getDeltaFieldsUsed(List<ClassField> usedFieldsSrc,
			TypeDeclaration typeDecl) throws Exception {

		int numGetters = 0;
		int numSetters = 0;

		// for every field
		for (ClassField classField : usedFieldsSrc) {

			// does the field getter exist?
			boolean exist = MetricUtils.existsGetterOfField(typeDecl,
					classField);
			if (!exist) {
				numGetters++;
			}
			// does the field setter exist?
			exist = MetricUtils.existsSetterOfField(typeDecl, classField);
			if (!exist) {
				numSetters++;
			}
		}

		return numGetters + numSetters;
	}

	public static double getDeltaSubclassMethodsUsed(TypeDeclaration srcCls,
			LinkedHashSet<String> callsMethodP, TypeDeclaration tgtCls)
			throws Exception {

		LinkedHashSet<String> methodsSrc = MetricUtils.getMethods(srcCls);
		LinkedHashSet<String> methodCallsSrc = new LinkedHashSet<String>(
				callsMethodP);
		LinkedHashSet<String> methodsTgt = MetricUtils.getMethods(tgtCls);

		// get the external methods
		LinkedHashSet<String> externalMethods = new LinkedHashSet<String>(
				methodCallsSrc);
		externalMethods.removeAll(methodsSrc);

		// internal methods of the source class
		methodCallsSrc.removeAll(externalMethods);

		// which of these are need to be created in the target class?
		methodCallsSrc.removeAll(methodsTgt);

		return methodCallsSrc.size();
	}

	/**
	 * 
	 * 
	 * @param usedFieldsSrc
	 * @param typeDecl
	 * @param method
	 * @return
	 * @throws Exception
	 */
	public static double getDeltaFieldsUsed2(List<ClassField> usedFieldsSrc,
			TypeDeclaration typeDecl, MethodDeclaration method)
			throws Exception {

		int numGetters = 0;
		

		// for every field
		for (ClassField classField : usedFieldsSrc) {

			Integer num = MetricUtils.getStringNumUsagesInMethod(typeDecl,
					method, classField.getName());
			if (num > 0) {
				continue;
			}

			// does the field getter exist?
			boolean exist = MetricUtils.existsGetterOfField(typeDecl,
					classField);
			boolean exist2 = MetricUtils.existsSetterOfField(typeDecl,
					classField);
			if (!exist || !exist2) {
				numGetters++;
			}
		}

		return numGetters;
	}

}
