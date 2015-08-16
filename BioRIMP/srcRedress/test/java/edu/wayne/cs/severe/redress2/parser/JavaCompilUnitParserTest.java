package edu.wayne.cs.severe.redress2.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import edu.wayne.cs.severe.redress2.entity.CompilationUnit;
import edu.wayne.cs.severe.redress2.entity.TypeDeclaration;
import edu.wayne.cs.severe.redress2.exception.CompilUnitException;

public class JavaCompilUnitParserTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Test
	public void testGetClasses() throws CompilUnitException {

		JavaCompilUnitParser parser = new JavaCompilUnitParser();
		List<CompilationUnit> compUnits = new ArrayList<CompilationUnit>();
		compUnits.add(new CompilationUnit(new File(
				"test_data/code/test_parsed/Test.java.xml")));
		List<TypeDeclaration> classes = parser.getClasses(compUnits);

		List<String> qNames = extractClassQNames(classes);
		String[] expecQNames = { "C1.C2", "C1.C2.C3", "C7", "C1",
				"C1.C4.C5.C6", "C1.C4.C5", "C1.C4" };

		System.out.println(qNames);

		assertEquals(expecQNames.length, qNames.size());
		for (String expQName : expecQNames) {
			int i = qNames.indexOf(expQName);
			assertFalse(i == -1);
			assertEquals(expQName, qNames.get(i));
		}
	}

	@Test
	public void testGetClasses2() throws CompilUnitException {

		JavaCompilUnitParser parser = new JavaCompilUnitParser();
		List<CompilationUnit> compUnits = new ArrayList<CompilationUnit>();
		compUnits.add(new CompilationUnit(new File(
				"test_data/code/test_parsed/PersonMapper.java8.xml")));
		List<TypeDeclaration> classes = parser.getClasses(compUnits);

		List<String> qNames = extractClassQNames(classes);
		String[] expecQNames = {
				"org.apache.ibatis.submitted.ognl_enum.PersonMapper",
				"org.apache.ibatis.submitted.ognl_enum.PersonMapper.PersonType" };

		assertEquals(expecQNames.length, qNames.size());
		for (String expQName : expecQNames) {
			int i = qNames.indexOf(expQName);
			assertFalse(i == -1);
			assertEquals(expQName, qNames.get(i));
		}
	}

	private List<String> extractClassQNames(List<TypeDeclaration> classes) {
		List<String> qNames = new ArrayList<String>();

		for (TypeDeclaration cl : classes) {
			qNames.add(cl.getQualifiedName());
		}

		return qNames;
	}
}
