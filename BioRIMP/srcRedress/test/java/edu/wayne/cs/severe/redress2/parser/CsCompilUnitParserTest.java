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

public class CsCompilUnitParserTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Test
	public void testGetClasses() throws CompilUnitException {

		CsCompilUnitParser parser = new CsCompilUnitParser();
		List<CompilationUnit> compUnits = new ArrayList<CompilationUnit>();
		compUnits.add(new CompilationUnit(new File(
				"test_data/code/test_parsed_cs/GenericList.cs.xml")));
		List<TypeDeclaration> classes = parser.getClasses(compUnits);

		List<String> qNames = extractClassQNames(classes);
		String[] expecQNames = { "test.test2.test3.test4.GenericList",
				"test.test2.test3.test4.GenericList.Nested3",
				"test.test2.test3.test4.GenericList.Nested3.Nested4",
				"test.test2.test3.test4.GenericList.Nested3.Nested4.Nested5",
				"test.test2.test5.test6.TestGenericList",
				"test.test2.test5.test6.TestGenericList.ExampleClass",
				"test.test2.test5.test6.TestGenericList.Nested",
				"test.test2.test5.test6.TestGenericList.Nested.Nested2" };

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
