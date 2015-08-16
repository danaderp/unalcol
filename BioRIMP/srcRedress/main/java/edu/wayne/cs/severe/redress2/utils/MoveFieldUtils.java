package edu.wayne.cs.severe.redress2.utils;

import edu.wayne.cs.severe.redress2.controller.MetricUtils;
import edu.wayne.cs.severe.redress2.entity.ClassField;
import edu.wayne.cs.severe.redress2.entity.TypeDeclaration;

public class MoveFieldUtils {
	/**
	 * Calculates the delta of field usage. If a field is used in the method to
	 * move, then a getter and setter should be created in the class involved
	 * 
	 * @param usedFieldsSrc
	 * @param typeDecl
	 * @return
	 * @throws Exception
	 */
	public static double getDeltaFieldsUsed(ClassField classField,
			TypeDeclaration typeDecl) throws Exception {

		int numGetters = 0;
		int numSetters = 0;

		// does the field getter exist?
		boolean exist = MetricUtils.existsGetterOfField(typeDecl, classField);
		if (!exist) {
			numGetters++;
		}
		// does the field setter exist?
		exist = MetricUtils.existsSetterOfField(typeDecl, classField);
		if (!exist) {
			numSetters++;
		}

		return numGetters + numSetters;
	}
}
