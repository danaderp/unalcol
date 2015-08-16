package edu.wayne.cs.severe.redress2.parser;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import edu.wayne.cs.severe.redress2.entity.CompilationUnit;
import edu.wayne.cs.severe.redress2.entity.TypeDeclaration;
import edu.wayne.cs.severe.redress2.exception.CompilUnitException;
import edu.wayne.cs.severe.redress2.utils.XpathSrcMLUtils;

/**
 * Compilation unit parser for C#
 * 
 * @author ojcchar
 * 
 */
public class CsCompilUnitParser extends CompilUnitParser {

	// constants
	private static final String DOT = ".";
	// the logger
	private static Logger LOGGER = LoggerFactory
			.getLogger(CsCompilUnitParser.class);

	@Override
	public List<TypeDeclaration> getClasses(List<CompilationUnit> compUnits)
			throws CompilUnitException {

		// all the types
		List<TypeDeclaration> classes = new ArrayList<TypeDeclaration>();

		// the initial xpath expression
		String xpExpr = "/a:unit/a:namespace/a:name";
		for (CompilationUnit compUnit : compUnits) {
			try {

				// get the namespaces of the compilation unit
				List<CSNamespace> namespaces = getNamespaces(xpExpr, compUnit,
						"");

				// recursively add the classes declared in the namespaces
				classes.addAll(getClassesFromNamespaces(namespaces));

			} catch (Exception e) {
				LOGGER.error(
						"Error for compilation unit "
								+ compUnit.getSrcFile().getName() + ": "
								+ e.getMessage(), e);
			}

		}

		return getAllTypes(classes);
	}

	/**
	 * Recursively extracts all the classes declared in the namespaces
	 * 
	 * @param namespaces
	 *            the namespaces
	 * @return the classes declared
	 */
	private List<TypeDeclaration> getClassesFromNamespaces(
			List<CSNamespace> namespaces) {
		List<TypeDeclaration> types = new ArrayList<TypeDeclaration>();
		for (CSNamespace csNamespace : namespaces) {
			types.addAll(csNamespace.types);
			types.addAll(getClassesFromNamespaces(csNamespace.namespaces));
		}
		return types;
	}

	/**
	 * Recursively extracts all the namespaces in the provided compilation unit
	 * given the xpath expression to find them and the previous namespace
	 * 
	 * @param xpExpr
	 *            the xpath expression
	 * @param compUnit
	 *            the compilation unit
	 * @param prevNmSpace
	 *            the previous namespace
	 * @return the list of all namespaces
	 * @throws Exception
	 *             if an error occurs
	 */
	private List<CSNamespace> getNamespaces(String xpExpr,
			CompilationUnit compUnit, String prevNmSpace) throws Exception {

		// query the expression
		InputSource inputSource = XpathSrcMLUtils.getInputSource(compUnit
				.getSrcFile());
		NodeList nodeList = XpathSrcMLUtils.getResultXpath(xpExpr, inputSource);

		List<CSNamespace> namespaces = new ArrayList<CsCompilUnitParser.CSNamespace>();
		for (int i = 0; i < nodeList.getLength(); i++) {

			// try to get the name of the namespace
			Node item = nodeList.item(i);
			String nodeValue = item.getFirstChild().getNodeValue();
			StringBuffer namespc = null;

			String parentXpExpr = "/..";
			String nameXpExpr = null;

			// if no generics
			if (nodeValue != null && !nodeValue.trim().isEmpty()) {

				namespc = new StringBuffer(nodeValue);
				namespc.append(DOT);

				// xpath expression for the name of the namespace
				nameXpExpr = "[text()=\"" + nodeValue + "\"]/..";
				parentXpExpr = "";

				// if generics
			} else if (item.getChildNodes().getLength() > 1) {

				namespc = new StringBuffer();
				StringBuffer nameXpExprBold = new StringBuffer("/a:name[");

				NodeList childNodes = item.getChildNodes();
				for (int j = 0; j < childNodes.getLength(); j++) {
					Node child = childNodes.item(j);
					if (!"name".equalsIgnoreCase(child.getNodeName())) {
						continue;
					}

					// build the name of the namespace
					namespc.append(child.getFirstChild().getNodeValue());
					namespc.append(DOT);

					// build the name xpath expression
					nameXpExprBold.append("text()=\"");
					nameXpExprBold.append(child.getFirstChild().getNodeValue());
					nameXpExprBold.append("\" or ");
				}

				nameXpExprBold.delete(nameXpExprBold.length() - 3,
						nameXpExprBold.length());
				nameXpExprBold.append("]/..");

				nameXpExpr = nameXpExprBold.toString();
			}

			// if the node really is about a namespace declaration
			if (nameXpExpr != null) {

				// get the inner namespaces
				String innerXpExpr = xpExpr + nameXpExpr + parentXpExpr
						+ "/a:block/a:namespace/a:name";
				String completeNmSpace = prevNmSpace + namespc.toString();
				List<CSNamespace> subNmSpcs = getNamespaces(innerXpExpr,
						compUnit, completeNmSpace);

				// get the classes implemented in the namespace
				String innerClsXpExpr = xpExpr + nameXpExpr + parentXpExpr
						+ "/a:block/a:class/a:name";
				List<TypeDeclaration> classes = getClassesGenerics(
						innerClsXpExpr, compUnit, true, completeNmSpace);
				innerClsXpExpr = xpExpr + nameXpExpr + parentXpExpr
						+ "/a:block/a:class/a:name/a:name";
				classes.addAll(getClassesGenerics(innerClsXpExpr, compUnit,
						false, completeNmSpace));

				// build the namespace
				CSNamespace csNmSpc = new CSNamespace(namespc.toString());
				csNmSpc.namespaces = subNmSpcs;
				csNmSpc.types = classes;

				namespaces.add(csNmSpc);
			}
		}
		return namespaces;
	}

	private List<TypeDeclaration> getClassesGenerics(String xpExpr,
			CompilationUnit compUnit, boolean classNoGenerics, String namespace)
			throws Exception {

		// search for classes
		InputSource inputSource = XpathSrcMLUtils.getInputSource(compUnit
				.getSrcFile());
		NodeList nodeList = XpathSrcMLUtils.getResultXpath(xpExpr, inputSource);

		List<TypeDeclaration> classes = new ArrayList<TypeDeclaration>();

		for (int i = 0; i < nodeList.getLength(); i++) {

			Node item = nodeList.item(i);
			String qNameClass = item.getFirstChild().getNodeValue();

			// if we found a class
			if (qNameClass != null && !qNameClass.trim().isEmpty()) {

				// create the type
				TypeDeclaration typeDcl = new TypeDeclaration(namespace,
						qNameClass, !classNoGenerics, compUnit);

				// look recursively inner types
				String parExpr = "/..";
				if (classNoGenerics) {
					parExpr = "";
				}
				String siblExpr = xpExpr + "[text()=\"" + qNameClass + "\"]/.."
						+ parExpr + "/a:block/a:class/a:name";
				String completeNmSpace = namespace + qNameClass + DOT;
				List<TypeDeclaration> subTypes = getClassesGenerics(siblExpr,
						compUnit, true, completeNmSpace);
				siblExpr = xpExpr + "[text()=\"" + qNameClass + "\"]/.."
						+ parExpr + "/a:block/a:class/a:name/a:name";
				subTypes.addAll(getClassesGenerics(siblExpr, compUnit, false,
						completeNmSpace));

				// add the sub types and add the type to the list
				typeDcl.setSubTypes(subTypes);
				classes.add(typeDcl);
			}
		}

		return classes;

	}

	/**
	 * Inner class that represents a C# namespace
	 * 
	 * @author ojcchar
	 * 
	 */
	public class CSNamespace {

		// the name
		public String name;
		// the inner namespaces
		public List<CSNamespace> namespaces;
		// the types declared in the namespace
		public List<TypeDeclaration> types;

		public CSNamespace(String name) {
			this.name = name;
		}
	}

}
