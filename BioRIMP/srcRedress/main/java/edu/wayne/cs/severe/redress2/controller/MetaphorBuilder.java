package edu.wayne.cs.severe.redress2.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import edu.wayne.cs.severe.redress2.controller.metric.CBOMetric;
import edu.wayne.cs.severe.redress2.entity.CompilationUnit;
import edu.wayne.cs.severe.redress2.entity.TypeDeclaration;
import edu.wayne.cs.severe.redress2.utils.XpathSrcMLUtils;

/**
 * @author danaderp
 * @version 1.0
 * @created 03-Agosto-2015 12:28:47
 */
public class MetaphorBuilder {

	// logger
	private static Logger LOGGER = LoggerFactory.getLogger(CBOMetric.class);

	enum NODE_ST {
		VISITING, DONE;
	}

	private LinkedHashMap<String, NODE_ST> visitedClasses = new LinkedHashMap<String, NODE_ST>();
	private HashMap<String, List<TypeDeclaration>> childClasses = new HashMap<String, List<TypeDeclaration>>();
	private HashMap<String, List<TypeDeclaration>> parentClasses = new HashMap<String, List<TypeDeclaration>>();

	private List<TypeDeclaration> sysTypeDcls;
	
	public void buildHierarchy(List<TypeDeclaration> sysTypeDcls) {
		this.sysTypeDcls = sysTypeDcls;
		
		for (TypeDeclaration typeDcl : sysTypeDcls) {
			
			try {
				//Test Erase
				String test1 = getRandomMethod(getMethodsClass(typeDcl));
				String test2 = getRandomField(getFieldsClass(typeDcl));
				computeHierarchyForType(sysTypeDcls, typeDcl);

			} catch (Exception e) {
				LOGGER.error("Error for class: " + typeDcl.getQualifiedName()
						+ " - " + e.getMessage(), e);
			}

		}
	}

	private void computeHierarchyForType(List<TypeDeclaration> allTypes,
			TypeDeclaration typeDcl) throws Exception {

		String qName = typeDcl.getQualifiedName();

		// should I visit this node?
		NODE_ST nodeState = visitedClasses.get(qName);
		if (nodeState != null) {
			return;
		}

		if (childClasses.get(typeDcl.getQualifiedName()) == null) {
			childClasses.put(typeDcl.getQualifiedName(),
					new ArrayList<TypeDeclaration>());
		}

		// mark as visiting
		visitedClasses.put(qName, NODE_ST.VISITING);

		// ---------------------------------------------------------------------
		// The the super classes string

		// xpath expression to get the nodes from the file
		String parXpExpr = "";
		String prefXpExpr = "";
		if (typeDcl.isHasParams()) {
			parXpExpr = "/..";
			prefXpExpr = "/a:name";
		}

		String prefExpr = "//a:class/a:name" + prefXpExpr + "[text()=\""
				+ typeDcl.getName() + "\"]/.." + parXpExpr;

		// find the implementations, extensions
		CompilationUnit compUnit = typeDcl.getCompUnit();
		List<String> superClasses = null;
		if (compUnit != null && compUnit.getSrcFile() != null) {

			InputSource inputSource = XpathSrcMLUtils.getInputSource(compUnit
					.getSrcFile());
			String xpExpr = prefExpr + "/a:super/a:extends";
			xpExpr += " | " + prefExpr + "/a:super/a:implements";
			xpExpr += " | " + prefExpr + "/a:super/a:name";

			// LOGGER.debug(xpExpr);
			superClasses = getSuperClasses(xpExpr, inputSource);

		}

		// ---------------------------------------------------------------------

		// parent classes
		List<TypeDeclaration> superTypes = getSuperTypes(superClasses, allTypes);

		// assign parents and children
		assignParents(typeDcl, superTypes, allTypes);
		assignChildren(superTypes, typeDcl, allTypes);

		// compute the hierarchy for parents
		for (TypeDeclaration superType : superTypes) {
			computeHierarchyForType(allTypes, superType);
		}

		visitedClasses.put(typeDcl.getQualifiedName(), NODE_ST.DONE);

	}

	private List<TypeDeclaration> getSuperTypes(List<String> superClasses,
			List<TypeDeclaration> allTypes) {
		List<TypeDeclaration> superTypes = new ArrayList<TypeDeclaration>();
		if (superClasses == null) {
			return superTypes;
		}

		for (String supClass : superClasses) {

			TypeDeclaration qNameClass = getQNameClass(supClass, allTypes);

			if (qNameClass == null) {
				qNameClass = new TypeDeclaration("",
						(supClass.endsWith(".") ? supClass.substring(0,
								supClass.length() - 1) : supClass));
			}

			superTypes.add(qNameClass);
		}

		return superTypes;
	}

	private void assignParents(TypeDeclaration typeDcl,
			List<TypeDeclaration> superTypes, List<TypeDeclaration> allTypes) {

		if (superTypes == null) {
			return;
		}

		parentClasses.put(typeDcl.getQualifiedName(), superTypes);
	}

	private void assignChildren(List<TypeDeclaration> superTypes,
			TypeDeclaration typeDcl, List<TypeDeclaration> allTypes) {

		if (superTypes == null) {
			return;
		}

		for (TypeDeclaration supClass : superTypes) {

			String qName = supClass.getQualifiedName();
			List<TypeDeclaration> list = childClasses.get(qName);
			if (list == null) {
				list = new ArrayList<TypeDeclaration>();
			}

			list.add(typeDcl);
			childClasses.put(qName, list);
		}

	}

	private TypeDeclaration getQNameClass(String supClass,
			List<TypeDeclaration> allTypes) {

		if (allTypes == null) {
			return null;
		}

		// FIXME: CHECK IMPORTS, CASE IN ARGOUML
		// org.argouml.uml.diagram.ui.FigNodeModelElement
		// FigNode
		for (TypeDeclaration tDcl : allTypes) {
			String qName = tDcl.getQualifiedName();
			if (("." + qName + ".").endsWith("." + supClass)) {
				return tDcl;
			}
		}
		return null;
	}

	private List<String> getSuperClasses(String xpExpr, InputSource inputSource)
			throws Exception {
		NodeList nameTypes = XpathSrcMLUtils
				.getResultXpath(xpExpr, inputSource);
		List<String> clsUsed = new ArrayList<String>();

		for (int i = 0; i < nameTypes.getLength(); i++) {
			Node nameType = nameTypes.item(i);
			List<String> typeNames = getTypeName(nameType);
			if (typeNames != null) {
				clsUsed.addAll(typeNames);
			}
		}
		return clsUsed;
	}

	private List<String> getTypeName(Node nameNode) {

		Node firstChild = nameNode.getFirstChild();
		if (firstChild == null) {
			return null;
		}

		String nodeValue = firstChild.getNodeValue();
		String nameType = "";

		List<String> nameTypes = new ArrayList<String>();
		if (nodeValue != null && !nodeValue.trim().isEmpty()
				&& !"extends".equalsIgnoreCase(nameNode.getNodeName())
				&& !"implements".equalsIgnoreCase(nameNode.getNodeName())) {
			nameType = nodeValue + ".";
		} else if (nameNode.getChildNodes().getLength() > 1) {

			if ("implements".equalsIgnoreCase(nameNode.getNodeName())) {
				NodeList childNodes2 = nameNode.getChildNodes();
				String nameType2 = "";

				for (int j = 0; j < childNodes2.getLength(); j++) {
					Node child = childNodes2.item(j);
					if (!"name".equalsIgnoreCase(child.getNodeName())) {
						if (child.getNodeValue().trim().equals(",")) {
							nameTypes.add(nameType2);
							nameType2 = "";
						}
						continue;
					}

					List<String> nodeValue2 = getTypeName(child);
					if (nodeValue2 != null) {
						nameType2 += nodeValue2.get(0);
					} else {
						LOGGER.error("<impl> Could not get the type name for child "
								+ child);
					}
				}

				if (!nameType2.isEmpty()) {
					nameTypes.add(nameType2);
				}

				return nameTypes;
			} else {
				NodeList childNodes2 = nameNode.getChildNodes();
				for (int j = 0; j < childNodes2.getLength(); j++) {
					Node child = childNodes2.item(j);
					if (!"name".equalsIgnoreCase(child.getNodeName())) {
						continue;
					}

					List<String> nodeValue2 = getTypeName(child);
					if (nodeValue2 != null) {
						nameType += nodeValue2.get(0);
					} else {
						LOGGER.error("Could not get the type name for child "
								+ child);
					}
				}
			}
		}
		nameTypes.add(nameType);

		return nameTypes;
	}

	public HashMap<String, List<TypeDeclaration>> getChildClasses() {
		return childClasses;
	}

	public HashMap<String, List<TypeDeclaration>> getParentClasses() {
		return parentClasses;
	}
	
	//danaderp
	//Get the complete list of Methods of a specific class
	public LinkedHashSet<String> getMethodsClass(TypeDeclaration typeDcl) {
		LinkedHashSet<String> methods = new LinkedHashSet<String>();
		try {
			methods = MetricUtils.getMethods(typeDcl);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			LOGGER.error("Error for class: " + typeDcl.getQualifiedName()
			+ " - " + e.getMessage(), e);
		}
		
		return methods;
	
	}
	
	//Get the complete list of Fields of a specific class
	public HashSet<String> getFieldsClass(TypeDeclaration typeDcl) {
		HashSet<String> fields = new HashSet<String>();
		try {
			fields = MetricUtils.getFields(typeDcl);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			LOGGER.error("Error for class: " + typeDcl.getQualifiedName()
			+ " - " + e.getMessage(), e);
		}
		
		return fields;
	
	}
	
	//Get randomly a Method of a specific class
	public String getRandomMethod(LinkedHashSet<String> methods){
		Random rand = new Random();
		return (String) methods.toArray()[rand.nextInt(methods.size())];
	}
	
	//Get randomly a Field of a specific class
	public String getRandomField(HashSet<String> fields){
		Random rand = new Random();
		return (String) fields.toArray()[rand.nextInt(fields.size())];
	}
	
}// end DITMetric