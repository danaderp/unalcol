package edu.wayne.cs.severe.redress2.entity.refactoring.formulas.puf;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import edu.wayne.cs.severe.redress2.controller.HierarchyBuilder;
import edu.wayne.cs.severe.redress2.entity.CompilationUnit;
import edu.wayne.cs.severe.redress2.entity.ProgLang;
import edu.wayne.cs.severe.redress2.entity.TypeDeclaration;
import edu.wayne.cs.severe.redress2.entity.refactoring.RefactoringOperation;
import edu.wayne.cs.severe.redress2.io.MetricsReader;
import edu.wayne.cs.severe.redress2.io.RefactoringReader;
import edu.wayne.cs.severe.redress2.parser.CodeParser;
import edu.wayne.cs.severe.redress2.parser.CompilUnitParser;
import edu.wayne.cs.severe.redress2.parser.CompilUnitParserFactory;
import edu.wayne.cs.severe.redress2.utils.TestUtils;

public class LOCPullUpFieldPFTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Test
	public void testPredictMetrVal() throws Exception {
		LOCPullUpFieldPF form = new LOCPullUpFieldPF();

		String sysName = TestUtils.SYS_NAME_TEST5;
		File systemPath = TestUtils.SYS_PATH_TEST5;
		ProgLang lang = TestUtils.SYS_LANG_JAVA;
		File refsFile = TestUtils.REFS_FILE_PUF;

		MetricsReader reader = new MetricsReader(systemPath, sysName);
		LinkedHashMap<String, LinkedHashMap<String, Double>> prevMetrics = reader
				.readMetrics();

		// parse the code and get the compilation units
		CodeParser parser = new CodeParser(sysName, lang, false);
		List<CompilationUnit> compUnits = parser.parseCodeInFolder(systemPath);

		// parse the XML files of the code
		CompilUnitParser cUParser = CompilUnitParserFactory
				.getCompilUnitParser(lang);
		List<TypeDeclaration> sysTypeDcls = cUParser.getClasses(compUnits);

		HierarchyBuilder builder = new HierarchyBuilder();
		builder.buildHierarchy(sysTypeDcls);

		RefactoringReader refReader = new RefactoringReader(sysTypeDcls, lang, builder);
		List<RefactoringOperation> operations = refReader
				.getRefactOperations(refsFile);

		HashMap<String, Double> predictMetrVal = form.predictMetrVal(
				operations.get(0), prevMetrics);

		String[] expClasses = {
				"edu.wayne.cs.severe.redress2.tests.puf.SubClass1",
				"edu.wayne.cs.severe.redress2.tests.puf.SuperClass" };
		double[] expVals = { 11.0, 5.0 };

		assertEquals(expClasses.length, predictMetrVal.size());

		for (int i = 0; i < expClasses.length; i++) {
			String expCl = expClasses[i];

			Double val = predictMetrVal.get(expCl);
			assertEquals(new Double(expVals[i]), val);

		}

		// /-----------------------------------------

		predictMetrVal = form.predictMetrVal(operations.get(1), prevMetrics);

		expClasses = new String[] {
				"edu.wayne.cs.severe.redress2.tests.puf.SubClass1",
				"edu.wayne.cs.severe.redress2.tests.puf.SubClass2",
				"edu.wayne.cs.severe.redress2.tests.puf.SubClass3",
				"edu.wayne.cs.severe.redress2.tests.puf.SuperClass" };
		expVals = new double[] { 11.0, 10.0, 10.0, 5.0 };

		assertEquals(expClasses.length, predictMetrVal.size());

		for (int i = 0; i < expClasses.length; i++) {
			String expCl = expClasses[i];

			Double val = predictMetrVal.get(expCl);
			assertEquals(new Double(expVals[i]), val);

		}

	}

}
