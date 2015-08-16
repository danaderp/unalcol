package edu.wayne.cs.severe.redress2.utils;

import java.util.LinkedHashSet;

import edu.wayne.cs.severe.redress2.controller.MetricUtils;
import edu.wayne.cs.severe.redress2.entity.MethodDeclaration;
import edu.wayne.cs.severe.redress2.entity.TypeDeclaration;

public class MoveMethodUtils {

	public static int getNumAttrsUsedByMethod(TypeDeclaration typeDcl,
			MethodDeclaration method, TypeDeclaration typeDclSrc)
			throws Exception {

		LinkedHashSet<String> fields = MetricUtils.getFields(typeDcl);

		int numSttrs = 0;
		for (String field : fields) {
			LinkedHashSet<String> methods = MetricUtils.getMethodsUsingString(
					typeDclSrc == null ? typeDcl : typeDclSrc, field);
			if (methods.contains(method.getObjName() + ".")) {
				++numSttrs;
			}
		}

		return numSttrs;
	}
}
