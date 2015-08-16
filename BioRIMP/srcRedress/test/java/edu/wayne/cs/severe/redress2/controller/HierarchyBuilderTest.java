package edu.wayne.cs.severe.redress2.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import edu.wayne.cs.severe.redress2.entity.CompilationUnit;
import edu.wayne.cs.severe.redress2.entity.ProgLang;
import edu.wayne.cs.severe.redress2.entity.TypeDeclaration;
import edu.wayne.cs.severe.redress2.parser.CodeParser;
import edu.wayne.cs.severe.redress2.parser.CompilUnitParser;
import edu.wayne.cs.severe.redress2.parser.CompilUnitParserFactory;
import edu.wayne.cs.severe.redress2.utils.TestUtils;

public class HierarchyBuilderTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Test
	public void testComputeHierarchy() {

		List<TypeDeclaration> sysTypeDcls = new ArrayList<TypeDeclaration>();
		String folder = "test_data/code/test_parsed/";

		// ----------------------
		File compUnitFile = new File(folder + "Test4.java.xml");
		TypeDeclaration typeDcl = new TypeDeclaration("test.", "Class", true,
				new CompilationUnit(compUnitFile));

		sysTypeDcls.add(typeDcl);
		sysTypeDcls.add(new TypeDeclaration("", "Class2", true,
				new CompilationUnit(new File(folder + "Class2.java.xml"))));
		sysTypeDcls.add(new TypeDeclaration("", "Class3", false,
				new CompilationUnit(new File(folder + "Class3.java.xml"))));
		sysTypeDcls.add(new TypeDeclaration("", "Class4", true,
				new CompilationUnit(new File(folder + "Class4.java.xml"))));
		sysTypeDcls.add(new TypeDeclaration("", "Class20", false,
				new CompilationUnit(new File(folder + "Class20.java.xml"))));
		sysTypeDcls.add(new TypeDeclaration("", "Class21", false,
				new CompilationUnit(new File(folder + "Class21.java.xml"))));

		HierarchyBuilder builder = new HierarchyBuilder();
		builder.buildHierarchy(sysTypeDcls);

		// ------------------------------------------------------------

		HashMap<String, String[]> expParents = new HashMap<String, String[]>();
		expParents.put("test.Class", new String[] { "Class2", "Class3",
				"Class4" });
		expParents.put("Class2", new String[] {});
		expParents.put("Class3", new String[] { "Class20" });
		expParents.put("Class4", new String[] {});
		expParents.put("Class20", new String[] { "Class21" });
		expParents.put("Class21", new String[] {});

		// ------------------------------------------------------------

		Set<Entry<String, String[]>> entrySet = expParents.entrySet();

		HashMap<String, List<TypeDeclaration>> parentClasses = builder
				.getParentClasses();

		printHierarchy(parentClasses);

		assertEquals(entrySet.size(), parentClasses.size());
		for (Entry<String, String[]> entry : entrySet) {

			String qNameClass = entry.getKey();
			String[] expPars = entry.getValue();

			List<TypeDeclaration> parents = parentClasses.get(qNameClass);

			assertNotNull(parents);
			assertEquals(expPars.length, parents.size());

			for (String expPar : expPars) {
				assertTrue(parents.contains(new TypeDeclaration("", expPar)));
			}

		}

		// ------------------------------------------------------------

		HashMap<String, String[]> expChildren = new HashMap<String, String[]>();
		expChildren.put("test.Class", new String[] {});
		expChildren.put("Class2", new String[] { "test.Class" });
		expChildren.put("Class3", new String[] { "test.Class" });
		expChildren.put("Class4", new String[] { "test.Class" });
		expChildren.put("Class20", new String[] { "Class3" });
		expChildren.put("Class21", new String[] { "Class20" });

		// ------------------------------------------------------------

		Set<Entry<String, String[]>> entryChildSet = expChildren.entrySet();

		HashMap<String, List<TypeDeclaration>> childClasses = builder
				.getChildClasses();

		System.out.println("children");
		printHierarchy(childClasses);

		assertEquals(entryChildSet.size(), childClasses.size());
		for (Entry<String, String[]> entry : entryChildSet) {

			String qNameClass = entry.getKey();
			String[] expChild = entry.getValue();

			List<TypeDeclaration> children = childClasses.get(qNameClass);

			assertNotNull(children);
			assertEquals(expChild.length, children.size());

			for (String expPar : expChild) {
				assertTrue(children.contains(new TypeDeclaration("", expPar)));
			}

		}

	}

	private void printHierarchy(
			HashMap<String, List<TypeDeclaration>> parentClasses) {
		Set<Entry<String, List<TypeDeclaration>>> entrySet2 = parentClasses
				.entrySet();
		for (Entry<String, List<TypeDeclaration>> entry : entrySet2) {
			System.out.println(entry.getKey() + "=" + entry.getValue());
		}
	}

	private void deleteFiles(File xmlFolder) throws IOException {
		if (xmlFolder != null) {
			FileUtils.deleteDirectory(xmlFolder);
		}
	}

	@Test
	public void testComputeHierarchy2() throws Exception {
		File xmlFolder = null;
		try {
			ProgLang lang = TestUtils.SYS_LANG_JAVA;
			String sysName = TestUtils.SYS_NAME_TEST2;
			File systemPath = TestUtils.SYS_PATH_TEST2;

			// parse the code and get the compilation units
			CodeParser parser = new CodeParser(sysName, lang, true);
			List<CompilationUnit> compUnits = parser
					.parseCodeInFolder(systemPath);
			xmlFolder = parser.getXmlFolder();

			// parse the XML files of the code
			CompilUnitParser cUParser = CompilUnitParserFactory
					.getCompilUnitParser(lang);
			List<TypeDeclaration> classes = cUParser.getClasses(compUnits);

			HierarchyBuilder builder = new HierarchyBuilder();
			builder.buildHierarchy(classes);

			HashMap<String, List<TypeDeclaration>> parentClasses = builder
					.getParentClasses();
			printHierarchy(parentClasses);
		} finally {
			deleteFiles(xmlFolder);
		}
	}

	@Test
	public void testComputeHierarchy3() throws Exception {
		File xmlFolder = null;
		try {
			ProgLang lang = TestUtils.SYS_LANG_CS;
			String sysName = TestUtils.SYS_NAME_TEST3;
			File systemPath = TestUtils.SYS_PATH_TEST3;

			// parse the code and get the compilation units
			CodeParser parser = new CodeParser(sysName, lang, true);
			List<CompilationUnit> compUnits = parser
					.parseCodeInFolder(systemPath);
			xmlFolder = parser.getXmlFolder();

			// parse the XML files of the code
			CompilUnitParser cUParser = CompilUnitParserFactory
					.getCompilUnitParser(lang);
			List<TypeDeclaration> classes = cUParser.getClasses(compUnits);

			HierarchyBuilder builder = new HierarchyBuilder();
			builder.buildHierarchy(classes);

			HashMap<String, List<TypeDeclaration>> parentClasses = builder
					.getParentClasses();
			printHierarchy(parentClasses);
		} finally {
			deleteFiles(xmlFolder);
		}
	}
}
