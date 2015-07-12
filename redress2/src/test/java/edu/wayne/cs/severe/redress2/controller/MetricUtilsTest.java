package edu.wayne.cs.severe.redress2.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import edu.wayne.cs.severe.redress2.entity.ClassField;
import edu.wayne.cs.severe.redress2.entity.CompilationUnit;
import edu.wayne.cs.severe.redress2.entity.MethodDeclaration;
import edu.wayne.cs.severe.redress2.entity.TypeDeclaration;

public class MetricUtilsTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Test
	public void testGetFields() throws Exception {
		File compUnitFile = new File(
				"test_data/code/test_parsed/Test5.java.xml");
		TypeDeclaration typeDcl = new TypeDeclaration("test.test2.", "Test5",
				false, new CompilationUnit(compUnitFile));
		HashSet<String> fields = MetricUtils.getFields(typeDcl);

		String[] expcFields = { "field1", "field2", "field3", "field4",
				"field5", "field6" };

		assertEquals(expcFields.length, fields.size());
		for (String expcField : expcFields) {
			assertTrue(fields.contains(expcField));
		}

	}

	@Test
	public void testGetFields2() throws Exception {

		File compUnitFile = new File("test_data/code/test_parsed/Test.java.xml");
		TypeDeclaration typeDcl = new TypeDeclaration("test", "C7", false,
				new CompilationUnit(compUnitFile));
		HashSet<String> fields = MetricUtils.getFields(typeDcl);

		assertEquals(0, fields.size());

	}

	@Test
	public void testGetFields3() throws Exception {
		File compUnitFile = new File(
				"test_data/code/test_parsed_cs/Test5.cs.xml");
		TypeDeclaration typeDcl = new TypeDeclaration("test.test2.", "Test5",
				false, new CompilationUnit(compUnitFile));
		HashSet<String> fields = MetricUtils.getFields(typeDcl);

		String[] expcFields = { "field1", "field2", "field3", "field4",
				"field5", "field6", "date", "day", "Date" };

		assertEquals(expcFields.length, fields.size());
		for (String expcField : expcFields) {
			assertTrue(fields.contains(expcField));
		}

	}

	@Test
	public void testGetNumberOfMethodsUsingField() throws Exception {
		File compUnitFile = new File(
				"test_data/code/test_parsed_cs/Test5.cs.xml");
		TypeDeclaration typeDcl = new TypeDeclaration("test.test2.", "Test5",
				false, new CompilationUnit(compUnitFile));

		String[] fields = { "field1", "field2", "field3", "field4", "field5",
				"field6" };
		int[] expcMetr = { 5, 5, 4, 3, 2, 1 };

		for (int i = 0; i < fields.length; i++) {
			String field = fields[i];
			int expcM = expcMetr[i];
			int numMethods = MetricUtils.getNumberOfMethodsUsingString(typeDcl,
					field);

			assertEquals(expcM, numMethods);

		}

	}

	@Test
	public void testGetNumberOfMethodsUsingField2() throws Exception {

		TypeDeclaration typeDcl = new TypeDeclaration("test.test2.", "Test5",
				false, new CompilationUnit(new File(
						"test_data/code/test_parsed/Test5.java.xml")));

		String[] fields = { "field1", "field2", "field3", "field4", "field5",
				"field6" };
		int[] expcMetr = { 5, 5, 4, 3, 2, 1 };

		for (int i = 0; i < fields.length; i++) {
			String field = fields[i];
			int expcM = expcMetr[i];
			int numMethods = MetricUtils.getNumberOfMethodsUsingString(typeDcl,
					field);
			assertEquals(expcM, numMethods);

		}

	}

	@Test
	public void testGetClassesUsed() throws Exception {
		TypeDeclaration typeDcl = new TypeDeclaration("cbo.", "ClassCBO",
				false, new CompilationUnit(new File(
						"test_data/code/test_parsed/ClassCBO.java.xml")));

		List<TypeDeclaration> sysTypeDcls = new ArrayList<TypeDeclaration>();
		sysTypeDcls.add(typeDcl);
		sysTypeDcls.add(new TypeDeclaration("cbo.", "SuperClassCBO", false,
				new CompilationUnit(new File(
						"test_data/code/test_parsed/SuperClassCBO.java.xml"))));

		HierarchyBuilder hBuilder = new HierarchyBuilder();
		hBuilder.buildHierarchy(sysTypeDcls);

		List<TypeDeclaration> superClasses = hBuilder.getParentClasses().get(
				typeDcl.getQualifiedName());

		LinkedHashSet<String> classesUsed = MetricUtils.getClassesUsed(typeDcl,
				superClasses);

		System.out.println(classesUsed);

		assertEquals(13, classesUsed.size());
	}

	@Test
	public void testGetFieldsReadByMethod() throws Exception {
		TypeDeclaration typeDcl = new TypeDeclaration("rfc.pum.",
				"FieldsUsedClass", false, new CompilationUnit(new File(
						"test_data/code/test_parsed/FieldsUsedClass.java.xml")));
		MethodDeclaration method = new MethodDeclaration("fieldsReadMethod");
		List<ClassField> fields = MetricUtils.getFieldsUsedByMethod(typeDcl,
				method);

		Assert.assertNotNull(fields);

		assertEquals(7, fields.size());
	}

}
