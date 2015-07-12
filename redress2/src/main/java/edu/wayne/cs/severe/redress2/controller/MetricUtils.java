package edu.wayne.cs.severe.redress2.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.regex.Pattern;

import javax.xml.xpath.XPathExpressionException;

import org.apache.xml.dtm.ref.DTMNodeList;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import edu.wayne.cs.severe.redress2.entity.ClassField;
import edu.wayne.cs.severe.redress2.entity.CompilationUnit;
import edu.wayne.cs.severe.redress2.entity.MethodDeclaration;
import edu.wayne.cs.severe.redress2.entity.TypeDeclaration;
import edu.wayne.cs.severe.redress2.utils.XpathSrcMLUtils;

public class MetricUtils {
	// logger
	// private static Logger LOGGER =
	// LoggerFactory.getLogger(MetricUtils.class);

	public static LinkedHashSet<String> getMethodCalls(TypeDeclaration typeDcl)
			throws Exception {

		File compUnitFile = typeDcl.getCompUnit().getSrcFile();
		InputSource inputSource = XpathSrcMLUtils.getInputSource(compUnitFile);

		// xpath expression to get the nodes from the file
		String parXpExpr = "";
		String prefXpExpr = "";
		if (typeDcl.isHasParams()) {
			parXpExpr = "/..";
			prefXpExpr = "/a:name";
		}

		String prefExpr = "//a:class/a:name" + prefXpExpr + "[text()=\""
				+ typeDcl.getName() + "\"]/.." + parXpExpr;
		String xpExpr = prefExpr + "//a:function//a:call";
		xpExpr += " | " + prefExpr + "//a:constructor//a:call";
		// LOGGER.debug(xpExpr);

		NodeList callNodes = XpathSrcMLUtils
				.getResultXpath(xpExpr, inputSource);

		return getMethodCalls(callNodes);
	}

	private static LinkedHashSet<String> getMethodCalls(NodeList callNodes) {
		LinkedHashSet<String> methodsCalls = new LinkedHashSet<String>();

		for (int i = 0; i < callNodes.getLength(); i++) {
			Node callNode = callNodes.item(i);
			NodeList childNodes = callNode.getChildNodes();

			Node nameNode = childNodes.item(0);
			String methodName = getMethodName(nameNode);
			if (methodName != null) {
				methodsCalls.add(methodName);
				// Node argsNode = childNodes.item(1);
			}

		}
		return methodsCalls;
	}

	private static String getMethodName(Node nameNode) {

		Node firstChild = nameNode.getFirstChild();
		if (firstChild == null) {
			return null;
		}

		String nodeValue = firstChild.getNodeValue();
		String nameMethod = "";
		if (nodeValue != null && !nodeValue.trim().isEmpty()) {
			nameMethod = nodeValue;
		} else if (nameNode.getChildNodes().getLength() > 1) {
			NodeList childNodes2 = nameNode.getChildNodes();
			for (int j = 0; j < childNodes2.getLength(); j++) {
				Node child = childNodes2.item(j);
				if (!"name".equalsIgnoreCase(child.getNodeName())) {
					continue;
				}

				nameMethod += child.getFirstChild().getNodeValue() + ".";
			}
		}
		return nameMethod;
	}

	public static LinkedHashSet<String> getMethods(TypeDeclaration typeDcl)
			throws Exception {

		File compUnitFile = typeDcl.getCompUnit().getSrcFile();
		InputSource inputSource = XpathSrcMLUtils.getInputSource(compUnitFile);

		// xpath expression to get the nodes from the file
		String parXpExpr = "";
		String prefXpExpr = "";
		if (typeDcl.isHasParams()) {
			parXpExpr = "/..";
			prefXpExpr = "/a:name";
		}

		String prefExpr = "//a:class/a:name" + prefXpExpr + "[text()=\""
				+ typeDcl.getName() + "\"]/.." + parXpExpr;
		String fXpExpr = prefExpr + "/a:block/";
		String xpExpr = "" + fXpExpr + "a:function/a:name | " + fXpExpr
				+ "a:function_decl/a:name | " + fXpExpr
				+ "a:constructor/a:name ";

		NodeList callNodes = XpathSrcMLUtils
				.getResultXpath(xpExpr, inputSource);

		LinkedHashSet<String> methods = getMethodNames(callNodes);
		return methods;
	}

	private static LinkedHashSet<String> getMethodNames(NodeList methodNodes) {
		LinkedHashSet<String> methodsNames = new LinkedHashSet<String>();

		for (int i = 0; i < methodNodes.getLength(); i++) {
			Node methodNode = methodNodes.item(i);
			String methodName = getMethodName(methodNode);
			if (methodName != null) {
				methodsNames.add(methodName);
			}
		}
		return methodsNames;
	}

	public static LinkedHashSet<String> getFields(TypeDeclaration typeDcl)
			throws Exception {

		File compUnitFile = typeDcl.getCompUnit().getSrcFile();
		// //class/name[text()="Test5"]/../block/decl_stmt/decl/name
		LinkedHashSet<String> fields = new LinkedHashSet<String>();

		// xpath expression to get the nodes from the file
		String parXpExpr = "";
		String prefXpExpr = "";
		if (typeDcl.isHasParams()) {
			parXpExpr = "/..";
			prefXpExpr = "/a:name";
		}

		String prefExpr = "//a:class/a:name" + prefXpExpr + "[text()=\""
				+ typeDcl.getName() + "\"]/.." + parXpExpr;
		String xpExpr = prefExpr + "/a:block/a:decl_stmt/a:decl/a:name";

		InputSource inputSource = XpathSrcMLUtils.getInputSource(compUnitFile);
		NodeList callNodes = XpathSrcMLUtils
				.getResultXpath(xpExpr, inputSource);

		fields.addAll(getMethodNames(callNodes));
		return fields;
	}

	public static Double getNumFieldsDiff(TypeDeclaration typeDcl)
			throws Exception {

		// //class/name[text()="Test5"]/../block/decl_stmt/decl/name
		InputSource inputSource = XpathSrcMLUtils.getInputSource(typeDcl
				.getCompUnit().getSrcFile());

		// xpath expression to get the nodes from the file
		String parXpExpr = "";
		String prefXpExpr = "";
		if (typeDcl.isHasParams()) {
			parXpExpr = "/..";
			prefXpExpr = "/a:name";
		}

		String prefExp1 = "//a:class/a:name" + prefXpExpr + "[text()=\""
				+ typeDcl.getName() + "\"]/.." + parXpExpr;
		String prefExpr2 = "/a:block/a:decl_stmt/a:decl/a:type/a:name";
		String prefExpr3 = "[text()!=\"" + typeDcl.getName()
				+ "\" or (not(text()) and ./a:name[1]/text()!=\""
				+ typeDcl.getName() + "\")]";

		StringBuffer totalExpr = new StringBuffer("count(");
		totalExpr.append(prefExp1);
		totalExpr.append(prefExpr2);
		totalExpr.append(prefExpr3);
		totalExpr.append(") ");

		String count = XpathSrcMLUtils.getResultXpathstring(
				totalExpr.toString(), inputSource);

		Double numMethods = Double.valueOf(count);
		return numMethods;
	}

	public static Double getNumberOfMethods(TypeDeclaration typeDcl,
			File compUnitFile) throws FileNotFoundException,
			XPathExpressionException {
		InputSource inputSource = XpathSrcMLUtils.getInputSource(compUnitFile);

		// xpath expression to get the nodes from the file
		String parXpExpr = "";
		String prefXpExpr = "";
		if (typeDcl.isHasParams()) {
			parXpExpr = "/..";
			prefXpExpr = "/a:name";
		}

		String fXpExpr = "//a:class/a:name" + prefXpExpr + "[text()=\""
				+ typeDcl.getName() + "\"]/.." + parXpExpr + "/a:block/";
		String xpExpr = "count(" + fXpExpr + "a:function | " + fXpExpr
				+ "a:function_decl | " + fXpExpr + "a:constructor)";

		String result = XpathSrcMLUtils.getResultXpathstring(xpExpr,
				inputSource);
		Double numMethods = Double.valueOf(result);
		return numMethods;
	}

	public static Double getCountClassExpression(String[] xpathExprs,
			TypeDeclaration typeDcl) throws Exception {

		if (xpathExprs == null || xpathExprs.length == 0) {
			throw new RuntimeException(
					"The xpath expressions should be provided");
		}

		InputSource inputSource = XpathSrcMLUtils.getInputSource(typeDcl
				.getCompUnit().getSrcFile());

		// xpath expression to get the nodes from the file
		String parXpExpr = "";
		String prefXpExpr = "";
		if (typeDcl.isHasParams()) {
			parXpExpr = "/..";
			prefXpExpr = "/a:name";
		}

		String fXpExpr = "//a:class/a:name" + prefXpExpr + "[text()=\""
				+ typeDcl.getName() + "\"]/.." + parXpExpr + "/a:block";

		// ------------------------------------------

		StringBuilder xpExpr = new StringBuilder("count(");
		for (String xExpr : xpathExprs) {
			xpExpr.append(fXpExpr);
			xpExpr.append(xExpr);
			xpExpr.append(" | ");
		}
		xpExpr.delete(xpExpr.length() - 3, xpExpr.length());
		xpExpr.append(")");

		// System.out.println(xpExpr.toString());

		// ------------------------------------------

		String result = XpathSrcMLUtils.getResultXpathstring(xpExpr.toString(),
				inputSource);
		Double numMethods = Double.valueOf(result);
		return numMethods;
	}

	public static int getNumberOfMethodsUsingString(TypeDeclaration typeDcl,
			String text) throws Exception {

		File compUnitFile = typeDcl.getCompUnit().getSrcFile();
		InputSource inputSource = XpathSrcMLUtils.getInputSource(compUnitFile);

		// xpath expression to get the nodes from the file
		String parXpExpr = "";
		String prefXpExpr = "";
		if (typeDcl.isHasParams()) {
			parXpExpr = "/..";
			prefXpExpr = "/a:name";
		}

		String prefExpr = "//a:class/a:name" + prefXpExpr + "[text()=\""
				+ typeDcl.getName() + "\"]/.." + parXpExpr;
		String fXpExpr = prefExpr + "/a:block/";
		String xpExpr = "count(" + fXpExpr + "a:function[.//text()=\"" + text
				+ "\"] | " + fXpExpr + "a:constructor[.//text()=\"" + text
				+ "\"] )";

		String num = XpathSrcMLUtils.getResultXpathstring(xpExpr, inputSource);

		return Integer.valueOf(num);
	}

	public static String getFieldType(TypeDeclaration typeDcl, String fieldName)
			throws Exception {

		InputSource inputSource = XpathSrcMLUtils.getInputSource(typeDcl
				.getCompUnit().getSrcFile());

		// xpath expression to get the nodes from the file
		String parXpExpr = "";
		String prefXpExpr = "";
		if (typeDcl.isHasParams()) {
			parXpExpr = "/..";
			prefXpExpr = "/a:name";
		}

		String prefExpr = "//a:class/a:name" + prefXpExpr + "[text()=\""
				+ typeDcl.getName() + "\"]/.." + parXpExpr;
		String xpExpr = prefExpr + "/a:block/a:decl_stmt/a:decl[./a:name=\""
				+ fieldName + "\"]/a:type/a:name";

		// System.out.println(xpExpr);

		NodeList callNodes = XpathSrcMLUtils
				.getResultXpath(xpExpr, inputSource);

		LinkedHashSet<String> methodNames = getMethodNames(callNodes);

		if (methodNames == null || methodNames.isEmpty()) {
			return null;
		}

		String fieldType = new ArrayList<String>(methodNames).get(0);
		if (fieldType.endsWith(".")) {
			fieldType = fieldType.substring(0, fieldType.length() - 1);
		}

		return fieldType;
	}

	public static LinkedHashSet<String> getMethodCallsMethod(
			TypeDeclaration typeDcl, String objName) throws Exception {
		return getMethodCallsMethod(typeDcl, objName, true);
	}

	public static LinkedHashSet<String> getMethodCallsMethod(
			TypeDeclaration typeDcl, String objName, boolean equals)
			throws Exception {

		InputSource inputSource = XpathSrcMLUtils.getInputSource(typeDcl
				.getCompUnit().getSrcFile());

		// xpath expression to get the nodes from the file
		String parXpExpr = "";
		String prefXpExpr = "";
		if (typeDcl.isHasParams()) {
			parXpExpr = "/..";
			prefXpExpr = "/a:name";
		}

		String prefExpr = "//a:class/a:name" + prefXpExpr + "[text()=\""
				+ typeDcl.getName() + "\"]/.." + parXpExpr;
		String operator = "=";
		if (!equals) {
			operator = "!=";
		}
		String xpExpr = prefExpr + "//a:function[./a:name/text()" + operator
				+ "\"" + objName + "\"]//a:call";
		xpExpr += " | " + prefExpr + "//a:constructor[./a:name/text()"
				+ operator + "\"" + objName + "\"]//a:call";

		NodeList callNodes = XpathSrcMLUtils
				.getResultXpath(xpExpr, inputSource);

		LinkedHashSet<String> methodCalls = getMethodCalls(callNodes);

		return methodCalls;
	}

	public static LinkedHashSet<String> getMethodCallsNoMethod(
			TypeDeclaration typeDcl, String objName) throws Exception {
		return getMethodCallsMethod(typeDcl, objName, false);
	}

	/**
	 * Primitive types are considered
	 * 
	 * @param typeDcl
	 * @param superClasses
	 * @return
	 * @throws Exception
	 */
	public static LinkedHashSet<String> getClassesUsed(TypeDeclaration typeDcl,
			List<TypeDeclaration> superClasses) throws Exception {

		// xpath expression to get the nodes from the file
		String parXpExpr = "";
		String prefXpExpr = "";
		if (typeDcl.isHasParams()) {
			parXpExpr = "/..";
			prefXpExpr = "/a:name";
		}

		String prefExpr = "//a:class/a:name" + prefXpExpr + "[text()=\""
				+ typeDcl.getName() + "\"]/.." + parXpExpr;
		LinkedHashSet<String> clsUsed = new LinkedHashSet<String>();

		String[] methodTypes = { "a:function", "a:constructor" };

		for (int i = 0; i < methodTypes.length; i++) {

			String methodType = methodTypes[i];

			// process types of parameters
			File srcFile = typeDcl.getCompUnit().getSrcFile();
			InputSource inputSource = XpathSrcMLUtils.getInputSource(srcFile);
			String xpExpr = prefExpr + "//" + methodType
					+ "//a:parameter_list/a:param//a:type/a:name";
			String argsExpr = "//a:argument/a:name";
			xpExpr += " | " + xpExpr + argsExpr;
			clsUsed.addAll(getClassesUsedFromNames(xpExpr, inputSource));

			// process types of variables
			inputSource = XpathSrcMLUtils.getInputSource(srcFile);
			xpExpr = prefExpr + "//" + methodType
					+ "//a:decl_stmt//a:type/a:name";
			xpExpr += " | " + xpExpr + argsExpr;
			clsUsed.addAll(getClassesUsedFromNames(xpExpr, inputSource));

			// process new expressions
			inputSource = XpathSrcMLUtils.getInputSource(srcFile);
			xpExpr = prefExpr + "//" + methodType
					+ "//a:expr[text()[contains(.,'new')]]/a:call/a:name";
			xpExpr += " | " + xpExpr + argsExpr;
			clsUsed.addAll(getClassesUsedFromNames(xpExpr, inputSource));

			// return of functions
			inputSource = XpathSrcMLUtils.getInputSource(srcFile);
			xpExpr = prefExpr + "//" + methodType
					+ "/a:type/a:name[text()!=\"void\"]";
			xpExpr += " | " + prefExpr + "//" + methodType
					+ "/a:type/a:name/a:name[text()!=\"void\"]";
			xpExpr += " | " + xpExpr + argsExpr;
			clsUsed.addAll(getClassesUsedFromNames(xpExpr, inputSource));
		}

		// Check the type of the fields
		LinkedHashSet<ClassField> fields = getFieldsWithType(typeDcl, true);
		for (ClassField field : fields) {
			int num = getNumberOfMethodsUsingString(typeDcl, field.getName());
			if (num > 0) {
				clsUsed.addAll(field.getTypes());
			}
		}

		LinkedHashSet<String> superClassesUsage = getSuperClassesUsage(typeDcl,
				superClasses, null, false);

		clsUsed.addAll(superClassesUsage);

		return clsUsed;
	}

	public static LinkedHashSet<String> getSuperClassesUsage(
			TypeDeclaration typeDcl, List<TypeDeclaration> superClasses,
			MethodDeclaration methodDcl, boolean equals) throws Exception {
		LinkedHashSet<String> clsUsed = new LinkedHashSet<String>();
		LinkedHashSet<String> methodCalls = null;

		if (methodDcl == null) {
			methodCalls = getMethodCalls(typeDcl);
		} else {
			methodCalls = getMethodCallsMethod(typeDcl, methodDcl.getObjName(),
					equals);
		}

		if (superClasses == null) {
			return clsUsed;
		}

		// superclasses
		LinkedHashSet<String> methods = getMethods(typeDcl);
		for (TypeDeclaration superCls : superClasses) {

			CompilationUnit compUnit = superCls.getCompUnit();
			if (compUnit == null) {
				continue;
			}

			File srcFile = compUnit.getSrcFile();
			if (srcFile == null) {
				continue;
			}

			LinkedHashSet<String> methodsSuper = getMethods(superCls);

			// methods
			boolean clsAdded = false;
			String superName = superCls.getName() + ".";
			for (String methodCall : methodCalls) {
				for (String methodSuper : methodsSuper) {
					if (methodCall.startsWith(methodSuper)
							&& !methods.contains(methodSuper)) {
						clsUsed.add(superName);
						clsAdded = true;
						break;
					}
				}
				if (clsAdded) {
					break;
				}
			}

			if (clsAdded) {
				continue;
			}

			// fields
			LinkedHashSet<String> fieldsSuper = getFields(superCls);
			for (String fieldSuper : fieldsSuper) {
				int num = getNumberOfMethodsUsingString(typeDcl, fieldSuper);
				if (num > 0) {
					clsUsed.add(superName);
					clsAdded = true;
					break;
				}
			}
		}

		return clsUsed;
	}

	public static LinkedHashSet<String> getClassesUsedByMethodGen(
			TypeDeclaration typeDcl, List<TypeDeclaration> superClasses,
			MethodDeclaration methodDcl, boolean equals) throws Exception {

		LinkedHashSet<String> clsUsed = getClassesUsedByVarsInMethod(typeDcl,
				methodDcl, equals);

		LinkedHashSet<String> methodsSub = new LinkedHashSet<String>();
		methodsSub.add(methodDcl.getObjName());
		if (!equals) {
			methodsSub = getMethods(typeDcl);
			methodsSub.remove(methodDcl.getObjName());
		}

		// Check the type of the fields
		LinkedHashSet<ClassField> fields = getFieldsWithType(typeDcl, true);
		for (ClassField field : fields) {
			for (String methodSub : methodsSub) {
				int num = getFieldNumUsagesInMethod(typeDcl,
						new MethodDeclaration(methodSub), field);
				if (num > 0) {
					clsUsed.addAll(field.getTypes());
				}
			}
		}

		LinkedHashSet<String> superClassesUsage = getSuperClassesUsage(typeDcl,
				superClasses, methodDcl, equals);

		clsUsed.addAll(superClassesUsage);

		return clsUsed;
	}

	public static LinkedHashSet<String> getClassesUsedByVarsInMethod(
			TypeDeclaration typeDcl, MethodDeclaration methodDcl, boolean equals)
			throws FileNotFoundException, Exception {

		// xpath expression to get the nodes from the file
		String parXpExpr = "";
		String prefXpExpr = "";
		if (typeDcl.isHasParams()) {
			parXpExpr = "/..";
			prefXpExpr = "/a:name";
		}

		String operator = "=";
		if (!equals) {
			operator = "!=";
		}

		String prefExpr = "//a:class/a:name" + prefXpExpr + "[text()=\""
				+ typeDcl.getName() + "\"]/.." + parXpExpr;
		LinkedHashSet<String> clsUsed = new LinkedHashSet<String>();

		String[] methodTypes = { "a:function", "a:constructor" };

		for (int i = 0; i < methodTypes.length; i++) {

			String methodType = methodTypes[i] + "[./a:name/text()" + operator
					+ "\"" + methodDcl.getObjName() + "\"]";

			// process types of parameters
			File srcFile = typeDcl.getCompUnit().getSrcFile();
			InputSource inputSource = XpathSrcMLUtils.getInputSource(srcFile);
			String xpExpr = prefExpr + "//" + methodType
					+ "//a:parameter_list/a:param//a:type/a:name";
			String argsExpr = "//a:argument/a:name";
			xpExpr += " | " + xpExpr + argsExpr;
			clsUsed.addAll(getClassesUsedFromNames(xpExpr, inputSource));

			// process types of variables
			inputSource = XpathSrcMLUtils.getInputSource(srcFile);
			xpExpr = prefExpr + "//" + methodType
					+ "//a:decl_stmt//a:type/a:name";
			xpExpr += " | " + xpExpr + argsExpr;
			clsUsed.addAll(getClassesUsedFromNames(xpExpr, inputSource));

			// process new expressions
			inputSource = XpathSrcMLUtils.getInputSource(srcFile);
			xpExpr = prefExpr + "//" + methodType
					+ "//a:expr[text()[contains(.,'new')]]/a:call/a:name";
			xpExpr += " | " + xpExpr + argsExpr;
			clsUsed.addAll(getClassesUsedFromNames(xpExpr, inputSource));

			// return of functions
			inputSource = XpathSrcMLUtils.getInputSource(srcFile);
			xpExpr = prefExpr + "//" + methodType
					+ "/a:type/a:name[text()!=\"void\"]";
			xpExpr += " | " + prefExpr + "//" + methodType
					+ "/a:type/a:name/a:name[text()!=\"void\"]";
			xpExpr += " | " + xpExpr + argsExpr;
			clsUsed.addAll(getClassesUsedFromNames(xpExpr, inputSource));
		}
		return clsUsed;
	}

	public static LinkedHashSet<String> getUsedClassesByMethod(
			TypeDeclaration typeDcl, List<TypeDeclaration> superClasses,
			MethodDeclaration methodDcl) throws Exception {
		return getClassesUsedByMethodGen(typeDcl, superClasses, methodDcl, true);
		// return getUsedClassesByMethodGen(typeDcl, objName, true);
	}

	public static LinkedHashSet<String> getUsedClassesByOtherMethods(
			TypeDeclaration typeDcl, List<TypeDeclaration> superClasses,
			MethodDeclaration methodDcl) throws Exception {
		return getClassesUsedByMethodGen(typeDcl, superClasses, methodDcl,
				false);
	}

	public static LinkedHashSet<ClassField> getFieldsWithType(
			TypeDeclaration typeDcl, boolean includeDot) throws Exception {

		LinkedHashSet<String> fields = getFields(typeDcl);
		LinkedHashSet<ClassField> cFields = new LinkedHashSet<ClassField>();

		for (String field : fields) {
			String fieldType = getFieldType(typeDcl, field);

			if (includeDot) {
				fieldType += ".";
			}

			List<String> types = new ArrayList<String>();
			types.add(fieldType);

			cFields.add(new ClassField(field, types));
		}

		return cFields;
	}

	private static LinkedHashSet<String> getClassesUsedFromNames(String xpExpr,
			InputSource inputSource) throws Exception {

		NodeList nameTypes = XpathSrcMLUtils
				.getResultXpath(xpExpr, inputSource);
		LinkedHashSet<String> clsUsed = new LinkedHashSet<String>();

		for (int i = 0; i < nameTypes.getLength(); i++) {
			Node nameType = nameTypes.item(i);
			String typeName = getTypeName(nameType);
			if (typeName != null) {
				clsUsed.add(typeName);
			}
		}
		return clsUsed;
	}

	private static String getTypeName(Node nameNode) {

		Node firstChild = nameNode.getFirstChild();
		if (firstChild == null) {
			return null;
		}

		String nodeValue = firstChild.getNodeValue();
		String nameType = "";
		if (nodeValue != null && !nodeValue.trim().isEmpty()) {
			nameType = nodeValue + ".";
		} else if (nameNode.getChildNodes().getLength() > 1) {
			NodeList childNodes2 = nameNode.getChildNodes();
			for (int j = 0; j < childNodes2.getLength(); j++) {
				Node child = childNodes2.item(j);
				if (!"name".equalsIgnoreCase(child.getNodeName())) {
					continue;
				}

				String nodeValue2 = getTypeName(child);
				nameType += nodeValue2;
			}
		}
		return nameType;
	}

	public static String getCodeMethod(TypeDeclaration typeDcl, String objName)
			throws Exception {

		File compUnitFile = typeDcl.getCompUnit().getSrcFile();
		InputSource inputSource = XpathSrcMLUtils.getInputSource(compUnitFile);

		// xpath expression to get the nodes from the file
		String xpExpr = "//a:class/a:name[text()=\"" + typeDcl.getName()
				+ "\"]/..//a:function[./a:name/text()=\"" + objName + "\"]";
		if (typeDcl.isHasParams()) {
			xpExpr = "//a:class/a:name/a:name[text()=\"" + typeDcl.getName()
					+ "\"]/../..//a:function[./a:name/text()=\"" + objName
					+ "\"]";
		}

		// get the code
		NodeList nodeList = XpathSrcMLUtils.getResultXpath(xpExpr, inputSource);
		String code = ((DTMNodeList) nodeList).getDTMIterator().toString();

		return code;
	}

	/**
	 * Counts the LOC for the source code. For counting the lines the source
	 * code replaces all the comments by the empty string.
	 * 
	 * @param code
	 *            the source code
	 * @return the lines of code
	 */
	public static int countLOC(String code) {

		// strip comments
		code = Pattern
				.compile("/\\*.*?\\*/|//.*?$",
						Pattern.MULTILINE | Pattern.DOTALL)
				.matcher(code.toString()).replaceAll("");

		// split into array using non empty lines as delimiters
		String[] s = Pattern.compile("\\S.*?$", Pattern.MULTILINE).split(code);

		// in case the original code only had one line
		int numLines = s.length;
		if (numLines == 0 && code.trim().length() > 0) {
			numLines = 1;
		}

		return numLines;
	}

	public static double getCycloConstructor(TypeDeclaration typeDcl,
			String objName) throws Exception {

		// prefix
		String prefFn = "/a:constructor[./a:name/text()=\"" + objName + "\"]//";
		return getCycloFunction(typeDcl, objName, prefFn);
	}

	public static double getCycloMethod(TypeDeclaration typeDcl, String objName)
			throws Exception {

		// prefix
		String prefFn = "/a:function[./a:name/text()=\"" + objName + "\"]//";
		return getCycloFunction(typeDcl, objName, prefFn);
	}

	private static double getCycloFunction(TypeDeclaration typeDcl,
			String objName, String prefFn) throws Exception {

		double cyclo = 0.0;

		// return
		String prefRt = "a:return";
		String[] xpathExprs = { prefFn + prefRt };
		Double count = MetricUtils.getCountClassExpression(xpathExprs, typeDcl);
		if (count == 0.0) {
			cyclo += 1.0;
		}
		// LOGGER.debug("cyclo [" + prefRt + "]: " + count);
		cyclo += count;

		// if
		String prefIf = "a:if";
		xpathExprs = new String[] { prefFn + prefIf };
		count = MetricUtils.getCountClassExpression(xpathExprs, typeDcl);
		// LOGGER.debug("cyclo [" + prefIf + "]: " + count);
		cyclo += count;

		// else
		String prefElse = "a:else";
		xpathExprs = new String[] { prefFn + prefElse };
		count = MetricUtils.getCountClassExpression(xpathExprs, typeDcl);
		// LOGGER.debug("cyclo [" + prefElse + "]: " + count);
		cyclo += count;

		// case
		String prefCase = "a:case";
		xpathExprs = new String[] { prefFn + prefCase };
		count = MetricUtils.getCountClassExpression(xpathExprs, typeDcl);
		// LOGGER.debug("cyclo [" + prefCase + "]: " + count);
		cyclo += count;

		// default
		String prefDef = "a:default";
		xpathExprs = new String[] { prefFn + prefDef };
		count = MetricUtils.getCountClassExpression(xpathExprs, typeDcl);
		// LOGGER.debug("cyclo [" + prefDef + "]: " + count);
		cyclo += count;

		// for
		String prefFor = "a:for";
		xpathExprs = new String[] { prefFn + prefFor };
		count = MetricUtils.getCountClassExpression(xpathExprs, typeDcl);
		// LOGGER.debug("cyclo [" + prefFor + "]: " + count);
		cyclo += count;

		// foreach
		String prefForEch = "a:foreach";
		xpathExprs = new String[] { prefFn + prefForEch };
		count = MetricUtils.getCountClassExpression(xpathExprs, typeDcl);
		// LOGGER.debug("cyclo [" + prefForEch + "]: " + count);
		cyclo += count;

		// while
		String prefDo = "a:do";
		xpathExprs = new String[] { prefFn + prefDo };
		count = MetricUtils.getCountClassExpression(xpathExprs, typeDcl);
		// LOGGER.debug("cyclo [" + prefDo + "]: " + count);
		cyclo += count;

		// while
		String prefWhl = "a:while";
		xpathExprs = new String[] { prefFn + prefWhl };
		count = MetricUtils.getCountClassExpression(xpathExprs, typeDcl);
		// LOGGER.debug("cyclo [" + prefWhl + "]: " + count);
		cyclo += count;

		// break
		String prefBrk = "a:break";
		xpathExprs = new String[] { prefFn + prefBrk };
		count = MetricUtils.getCountClassExpression(xpathExprs, typeDcl);
		// LOGGER.debug("cyclo [" + prefBrk + "]: " + count);
		cyclo += count;

		// catch
		String prefCth = "a:catch";
		xpathExprs = new String[] { prefFn + prefCth };
		count = MetricUtils.getCountClassExpression(xpathExprs, typeDcl);
		// LOGGER.debug("cyclo [" + prefCth + "]: " + count);
		cyclo += count;

		// throw
		String prefTrw = "a:throw";
		xpathExprs = new String[] { prefFn + prefTrw };
		count = MetricUtils.getCountClassExpression(xpathExprs, typeDcl);
		// LOGGER.debug("cyclo [" + prefTrw + "]: " + count);
		cyclo += count;

		return cyclo;
	}

	public static LinkedHashSet<String> getMethodsUsingString(
			TypeDeclaration typeDcl, String field) throws Exception {

		InputSource inputSource = XpathSrcMLUtils.getInputSource(typeDcl
				.getCompUnit().getSrcFile());

		// xpath expression to get the nodes from the file
		String parXpExpr = "";
		String prefXpExpr = "";
		if (typeDcl.isHasParams()) {
			parXpExpr = "/..";
			prefXpExpr = "/a:name";
		}

		String prefExpr = "//a:class/a:name" + prefXpExpr + "[text()=\""
				+ typeDcl.getName() + "\"]/.." + parXpExpr;
		String fXpExpr = prefExpr + "/a:block/";
		String xpExpr = fXpExpr + "a:function[.//text()=\"" + field + "\"] | "
				+ fXpExpr + "a:constructor[.//text()=\"" + field + "\"]";

		NodeList callNodes = XpathSrcMLUtils
				.getResultXpath(xpExpr, inputSource);

		LinkedHashSet<String> methods = getMethodNames(callNodes);
		return methods;
	}

	public static double getNumParsAndVars(TypeDeclaration typeDcl,
			String objName) throws Exception {

		InputSource inputSource = XpathSrcMLUtils.getInputSource(typeDcl
				.getCompUnit().getSrcFile());

		// xpath expression to get the nodes from the file
		String parXpExpr = "";
		String prefXpExpr = "";
		if (typeDcl.isHasParams()) {
			parXpExpr = "/..";
			prefXpExpr = "/a:name";
		}

		String prefExpr = "count(//a:class/a:name" + prefXpExpr + "[text()=\""
				+ typeDcl.getName() + "\"]/.." + parXpExpr;
		String fXpExpr = prefExpr + "/a:block/";
		String xpExpr = fXpExpr + "a:function[.//text()=\"" + objName
				+ "\"]//a:decl_stmt) + ";
		xpExpr += fXpExpr + "a:function[.//text()=\"" + objName
				+ "\"]/a:parameter_list/a:param )";

		String count = XpathSrcMLUtils
				.getResultXpathstring(xpExpr, inputSource);

		Double num = Double.valueOf(count);
		return num;
	}

	public static double getNumVars(TypeDeclaration typeDcl, String objName)
			throws Exception {

		InputSource inputSource = XpathSrcMLUtils.getInputSource(typeDcl
				.getCompUnit().getSrcFile());

		// xpath expression to get the nodes from the file
		String parXpExpr = "";
		String prefXpExpr = "";
		if (typeDcl.isHasParams()) {
			parXpExpr = "/..";
			prefXpExpr = "/a:name";
		}

		String prefExpr = "count(//a:class/a:name" + prefXpExpr + "[text()=\""
				+ typeDcl.getName() + "\"]/.." + parXpExpr;
		String fXpExpr = prefExpr + "/a:block/";
		String xpExpr = fXpExpr + "a:function[.//text()=\"" + objName
				+ "\"]//a:decl_stmt) ";

		String count = XpathSrcMLUtils
				.getResultXpathstring(xpExpr, inputSource);

		Double num = Double.valueOf(count);
		return num;
	}

	public static boolean existsGetterOfField(TypeDeclaration typeDcl,
			ClassField classField) throws Exception {

		String objName = classField.getName();
		String suffix = objName.substring(0, 1).toUpperCase()
				+ objName.substring(1, objName.length());
		String setter = "set" + suffix;

		LinkedHashSet<String> methods = MetricUtils.getMethods(typeDcl);
		return methods.contains(setter);
	}

	public static boolean existsSetterOfField(TypeDeclaration typeDcl,
			ClassField classField) throws Exception {

		String objName = classField.getName();
		String suffix = objName.substring(0, 1).toUpperCase()
				+ objName.substring(1, objName.length());
		String getter = "get" + suffix;

		LinkedHashSet<String> methods = MetricUtils.getMethods(typeDcl);
		return methods.contains(getter);
	}

	public static List<ClassField> getFieldsUsedByMethod(
			TypeDeclaration typeDcl, MethodDeclaration method) throws Exception {
		LinkedHashSet<ClassField> fields = getFieldsWithType(typeDcl, false);
		List<ClassField> usedFields = new ArrayList<ClassField>();
		for (ClassField field : fields) {
			boolean used = isStringUsedInMethod(typeDcl, method, field);
			if (used) {
				usedFields.add(field);
			}
		}
		return usedFields;
	}

	private static boolean isStringUsedInMethod(TypeDeclaration typeDcl,
			MethodDeclaration method, ClassField field) throws Exception {

		Integer val = getFieldNumUsagesInMethod(typeDcl, method, field);
		return val > 0 ? true : false;
	}

	public static Integer getStringNumUsagesInMethod(TypeDeclaration typeDcl,
			MethodDeclaration method, String str) throws FileNotFoundException,
			XPathExpressionException {

		// xpath expression to get the nodes from the file
		String parXpExpr = "";
		String prefXpExpr = "";
		if (typeDcl.isHasParams()) {
			parXpExpr = "/..";
			prefXpExpr = "/a:name";
		}

		String prefExpr = "//a:class/a:name" + prefXpExpr + "[text()=\""
				+ typeDcl.getName() + "\"]/.." + parXpExpr;
		String fExpr = "/a:block//a:function[./a:name/text()=\""
				+ method.getObjName() + "\"]";
		String fXpExpr = prefExpr + fExpr;
		String xpExpr = "count(" + fXpExpr + "//a:name[./text()=\"" + str
				+ "\"]  )";

		InputSource inputSource = XpathSrcMLUtils.getInputSource(typeDcl
				.getCompUnit().getSrcFile());
		String num = XpathSrcMLUtils.getResultXpathstring(xpExpr, inputSource);

		Integer val = Integer.valueOf(num);
		return val;
	}

	public static Integer getFieldNumUsagesInMethod(TypeDeclaration typeDcl,
			MethodDeclaration method, ClassField field) throws Exception {
		return getStringNumUsagesInMethod(typeDcl, method, field.getName());
	}

	public static int getNumberOfFieldUsageInMethods(TypeDeclaration typeDcl,
			ClassField attr) throws Exception {
		LinkedHashSet<String> methods = getMethods(typeDcl);
		int numUsages = 0;

		for (String method : methods) {
			Integer numMethod = getFieldNumUsagesInMethod(typeDcl,
					new MethodDeclaration(method), attr);
			numUsages += numMethod;
		}
		return numUsages;
	}

	public static LinkedHashSet<String> getUsedClassesByMethods(
			TypeDeclaration typeDcl, List<TypeDeclaration> superClasses,
			List<MethodDeclaration> methods) throws Exception {

		LinkedHashSet<String> allClses = new LinkedHashSet<String>();

		for (MethodDeclaration methodDcl : methods) {
			LinkedHashSet<String> clses = getClassesUsedByMethodGen(typeDcl,
					superClasses, methodDcl, true);
			allClses.addAll(clses);
		}
		return allClses;
	}

	public static LinkedHashSet<String> getUsedClassesByOtherMethodsMulti(
			TypeDeclaration typeDcl, List<TypeDeclaration> superClasses,
			List<MethodDeclaration> methods,
			List<MethodDeclaration> otherMethods) throws Exception {
		return getUsedClassesByMethods(typeDcl, superClasses, otherMethods);
	}

	public static List<MethodDeclaration> getOtherMethods(
			List<MethodDeclaration> methods, TypeDeclaration typeDcl)
			throws Exception {
		LinkedHashSet<String> methodsInTypeDcl = getMethods(typeDcl);
		List<MethodDeclaration> otherMethods = new ArrayList<MethodDeclaration>();
		for (String methodName : methodsInTypeDcl) {
			MethodDeclaration otherMethod = new MethodDeclaration(methodName);
			boolean contains = methods.contains(otherMethod);
			if (!contains) {
				otherMethods.add(otherMethod);
			}
		}
		return otherMethods;
	}

	public static LinkedHashSet<String> getMethodCallsMethods(
			TypeDeclaration typeDcl, List<MethodDeclaration> methods)
			throws Exception {
		LinkedHashSet<String> allCalls = new LinkedHashSet<String>();

		for (MethodDeclaration method : methods) {
			LinkedHashSet<String> calls = getMethodCallsMethod(typeDcl,
					method.getObjName());
			allCalls.addAll(calls);
		}

		return allCalls;
	}

}
