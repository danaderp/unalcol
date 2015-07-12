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
 * The Java compilation unit parser
 * 
 * @author ojcchar
 * 
 */
public class JavaCompilUnitParser extends CompilUnitParser {

	// constants
	private static final String DOT = ".";
	// logger
	private static Logger LOGGER = LoggerFactory
			.getLogger(JavaCompilUnitParser.class);

	@Override
	public List<TypeDeclaration> getClasses(List<CompilationUnit> compUnits)
			throws CompilUnitException {

		// list of classes
		List<TypeDeclaration> allClasses = new ArrayList<TypeDeclaration>();

		// process each comp. unit
		for (CompilationUnit compUnit : compUnits) {

			try {
				// get the classes
				List<TypeDeclaration> classes = getClasses(compUnit);
				allClasses.addAll(classes);

			} catch (Exception e) {
				LOGGER.error(
						"Error for comp. unit: "
								+ compUnit.getSrcFile().getName() + ". "
								+ e.getMessage(), e);
			}

		}
		return getAllTypes(allClasses);
	}

	/**
	 * Process the compilation unit and return the classes implemented in it
	 * 
	 * @param compUnit
	 *            the comp. unit
	 * @return the list of classes
	 * @throws Exception
	 *             if some error occurs
	 */
	private List<TypeDeclaration> getClasses(CompilationUnit compUnit)
			throws Exception {

		// get the package
		String packagStr = getPackageName(compUnit);

		// get the classes without generics
		String prevXExpr = "/a:unit/a:class/a:name";
		List<TypeDeclaration> classes = getClassesGenerics(prevXExpr, compUnit,
				true);

		// get the classes with generics
		String parmXExpr = "/a:unit/a:class/a:name/a:name";
		classes.addAll(getClassesGenerics(parmXExpr, compUnit, false));

		// set the package for every class
		for (TypeDeclaration cl : classes) {
			cl.setPack(packagStr);
			setPackageSubClasses(cl);
		}

		return classes;
	}

	/**
	 * Set the package for the subclasses of the type declaration
	 * 
	 * @param typeDcl
	 *            the type declaration
	 */
	private void setPackageSubClasses(TypeDeclaration typeDcl) {
		List<TypeDeclaration> subTypes = typeDcl.getSubTypes();
		for (TypeDeclaration subType : subTypes) {
			subType.setPack(typeDcl.getQualifiedName() + DOT);
			setPackageSubClasses(subType);
		}
	}

	/**
	 * Recursive Method to find type declarations in the provided compilation
	 * unit. The method takes into account if the xpath expression was build to
	 * find types with no generics or with generics.
	 * 
	 * @param xpExpr
	 *            the xpath expression to find the types
	 * @param compUnit
	 *            the compilation unit
	 * @param classNoGenerics
	 *            true if no generics, false otherwise
	 * @return the list of type declarations
	 * @throws Exception
	 *             if some error occurs
	 */
	private List<TypeDeclaration> getClassesGenerics(String xpExpr,
			CompilationUnit compUnit, boolean classNoGenerics) throws Exception {

		// search for classes
		InputSource inputSource = XpathSrcMLUtils.getInputSource(compUnit
				.getSrcFile());
		NodeList nodeList = XpathSrcMLUtils.getResultXpath(xpExpr, inputSource);

		// the classes
		List<TypeDeclaration> classes = new ArrayList<TypeDeclaration>();

		for (int i = 0; i < nodeList.getLength(); i++) {

			Node item = nodeList.item(i);
			String qNameClass = item.getFirstChild().getNodeValue();

			// if we found a class
			if (qNameClass != null && !qNameClass.trim().isEmpty()) {

				// create the type
				TypeDeclaration typeDcl = new TypeDeclaration(null, qNameClass,
						!classNoGenerics, compUnit);

				// look recursively inner types
				String parExpr = "/..";
				if (classNoGenerics) {
					parExpr = "";
				}
				String siblExpr = xpExpr + "[text()=\"" + qNameClass + "\"]/.."
						+ parExpr + "/a:block/a:class/a:name";
				List<TypeDeclaration> subTypes = getClassesGenerics(siblExpr,
						compUnit, true);
				siblExpr = xpExpr + "[text()=\"" + qNameClass + "\"]/.."
						+ parExpr + "/a:block/a:class/a:name/a:name";
				subTypes.addAll(getClassesGenerics(siblExpr, compUnit, false));

				// add the sub types and add the type to the list
				typeDcl.setSubTypes(subTypes);
				classes.add(typeDcl);
			}
		}

		return classes;

	}

	/**
	 * Get the package name of this compilation unit
	 * 
	 * @param compUnit
	 *            the compilation unit
	 * @return the package name
	 * @throws Exception
	 *             if an error occurs
	 */
	private String getPackageName(CompilationUnit compUnit) throws Exception {

		// search for the substrings of the package
		InputSource inputSource = XpathSrcMLUtils.getInputSource(compUnit
				.getSrcFile());
		String xpExpr = "/a:unit/a:package/a:name/a:name";
		NodeList nodeList = XpathSrcMLUtils.getResultXpath(xpExpr, inputSource);

		// build the package string
		StringBuffer packagStr = new StringBuffer();
		for (int i = 0; i < nodeList.getLength(); i++) {
			Node item = nodeList.item(i);
			packagStr.append(item.getFirstChild().getNodeValue());
			packagStr.append(DOT);
		}

		return packagStr.toString();
	}

}
