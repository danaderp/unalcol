package edu.wayne.cs.severe.redress2.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import edu.wayne.cs.severe.redress2.controller.metric.CBOMetric;
import edu.wayne.cs.severe.redress2.controller.metric.CYCLOMetric;
import edu.wayne.cs.severe.redress2.controller.metric.CodeMetric;
import edu.wayne.cs.severe.redress2.controller.metric.DITMetric;
import edu.wayne.cs.severe.redress2.controller.metric.LCOM2Metric;
import edu.wayne.cs.severe.redress2.controller.metric.LCOM5Metric;
import edu.wayne.cs.severe.redress2.controller.metric.LOCMetric;
import edu.wayne.cs.severe.redress2.controller.metric.MPCMetric;
import edu.wayne.cs.severe.redress2.controller.metric.NOCMetric;
import edu.wayne.cs.severe.redress2.controller.metric.NOMMetric;
import edu.wayne.cs.severe.redress2.controller.metric.RFCMetric;
import edu.wayne.cs.severe.redress2.entity.CompilationUnit;
import edu.wayne.cs.severe.redress2.entity.ProgLang;
import edu.wayne.cs.severe.redress2.entity.TypeDeclaration;
import edu.wayne.cs.severe.redress2.entity.refactoring.RefactoringOperation;
import edu.wayne.cs.severe.redress2.exception.CompilUnitException;
import edu.wayne.cs.severe.redress2.io.MetricsReader;
import edu.wayne.cs.severe.redress2.io.RefactoringReader;
import edu.wayne.cs.severe.redress2.parser.CodeParser;
import edu.wayne.cs.severe.redress2.parser.CompilUnitParser;
import edu.wayne.cs.severe.redress2.parser.CompilUnitParserFactory;
import edu.wayne.cs.severe.redress2.utils.TestUtils;

public class MetricCalculatorTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {

	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Test
	public void testComputeMetricsJava() throws CompilUnitException {

		MetricCalculator metrCalc = new MetricCalculator();

		List<CompilationUnit> compUnits = new ArrayList<CompilationUnit>();
		compUnits.add(new CompilationUnit(new File(
				"test_data/code/test_parsed/Test.java.xml")));

		List<CodeMetric> metrics = new ArrayList<CodeMetric>();
		metrics.add(new LOCMetric(null, null));

		// ------------------------------------------------

		CompilUnitParser cuParser = CompilUnitParserFactory
				.getCompilUnitParser(ProgLang.JAVA);
		List<TypeDeclaration> cls = cuParser.getClasses(compUnits);

		LinkedHashMap<String, LinkedHashMap<String, Double>> metrs = metrCalc
				.computeMetrics(cls, metrics);

		String[] classes = { "C1.C2", "C1.C2.C3", "C7", "C1", "C1.C4.C5.C6",
				"C1.C4.C5", "C1.C4" };
		String[] metricsStr = { "LOC" };
		Double[][] values = { { 4.0, 2.0, 11.0, 12.0, 2.0, 4.0, 6.0 } };

		for (int i = 0; i < classes.length; i++) {
			String cl = classes[i];
			System.out.println(cl);
			LinkedHashMap<String, Double> value = new LinkedHashMap<String, Double>();

			for (int j = 0; j < metricsStr.length; j++) {
				String met = metricsStr[j];
				value.put(met, values[j][i]);
			}

			assertEquals(value, metrs.get(cl));
		}

		System.out.println(metrs);

	}

	@Test
	public void testComputeMetricsCS() throws CompilUnitException {

		MetricCalculator metrCalc = new MetricCalculator();

		List<CompilationUnit> compUnits = new ArrayList<CompilationUnit>();
		compUnits.add(new CompilationUnit(new File(
				"test_data/code/test_parsed_cs/GenericList.cs.xml")));

		List<CodeMetric> metrics = new ArrayList<CodeMetric>();
		metrics.add(new LOCMetric(null, null));

		// ------------------------------------------------

		CompilUnitParser cuParser = CompilUnitParserFactory
				.getCompilUnitParser(ProgLang.CS);
		List<TypeDeclaration> cls = cuParser.getClasses(compUnits);

		LinkedHashMap<String, LinkedHashMap<String, Double>> metrs = metrCalc
				.computeMetrics(cls, metrics);

		String[] classes = { "test.test2.test3.test4.GenericList",
				"test.test2.test3.test4.GenericList.Nested3",
				"test.test2.test3.test4.GenericList.Nested3.Nested4",
				"test.test2.test3.test4.GenericList.Nested3.Nested4.Nested5",
				"test.test2.test5.test6.TestGenericList",
				"test.test2.test5.test6.TestGenericList.ExampleClass",
				"test.test2.test5.test6.TestGenericList.Nested",
				"test.test2.test5.test6.TestGenericList.Nested.Nested2" };

		String[] metricsStr = { "LOC" };
		Double[][] values = { { 13.0, 9.0, 6.0, 3.0, 24.0, 1.0, 14.0, 3.0 } };

		assertEquals(classes.length, cls.size());

		for (int i = 0; i < classes.length; i++) {
			String cl = classes[i];
			System.out.println(cl);
			LinkedHashMap<String, Double> value = new LinkedHashMap<String, Double>();

			for (int j = 0; j < metricsStr.length; j++) {
				String met = metricsStr[j];
				value.put(met, values[j][i]);
			}

			assertEquals(value, metrs.get(cl));
		}

		System.out.println(metrs);

	}

	@Test
	public void testPredictMetricsJava() throws Exception {

		ProgLang lang = TestUtils.SYS_LANG_JAVA;
		String sysName = TestUtils.SYS_NAME_TEST4;
		File systemPath = TestUtils.SYS_PATH_TEST4;
		File refsFile = TestUtils.REFS_FILE_PUF1;

		// parse the code and get the compilation units
		CodeParser parser = new CodeParser(sysName, lang, false);
		List<CompilationUnit> compUnits = parser.parseCodeInFolder(systemPath);
		System.out.println(parser.getXmlFolder());

		// parse the XML files of the code
		CompilUnitParser cUParser = CompilUnitParserFactory
				.getCompilUnitParser(lang);
		List<TypeDeclaration> sysTypeDcls = cUParser.getClasses(compUnits);

		HierarchyBuilder builder = new HierarchyBuilder();
		builder.buildHierarchy(sysTypeDcls);

		RefactoringReader refReader = new RefactoringReader(sysTypeDcls, lang, builder);
		List<RefactoringOperation> operations = refReader
				.getRefactOperations(refsFile);

		MetricsReader metReader = new MetricsReader(systemPath, sysName);
		LinkedHashMap<String, LinkedHashMap<String, Double>> prevMetrics = metReader
				.readMetrics();

		// list of metrics
		ArrayList<CodeMetric> metrics = new ArrayList<CodeMetric>();
		metrics.add(new LOCMetric());
		// metrics.add(new NOMMetric());
		// metrics.add(new RFCMetric());
		metrics.add(new CBOMetric());
		// metrics.add(new DITMetric());
		// metrics.add(new MPCMetric());
		// metrics.add(new LCOM5Metric());
		// metrics.add(new LCOM2Metric());
		// metrics.add(new NOCMetric());
		// metrics.add(new CYCLOMetric());

		MetricCalculator metrCalc = new MetricCalculator();
		LinkedHashMap<String, LinkedHashMap<String, LinkedHashMap<String, Double>>> metrs = metrCalc
				.predictMetrics(operations, metrics, prevMetrics);

		System.out.println(metrs);

		assertNotNull(metrs);
	}

	@Test
	public void testPredictMetricsJava2() throws Exception {

		ProgLang lang = TestUtils.SYS_LANG_JAVA;
		String sysName = TestUtils.SYS_NAME_TEST5;
		File systemPath = TestUtils.SYS_PATH_TEST5;
		File refsFile = TestUtils.REFS_FILE_PUF;

		// parse the code and get the compilation units
		CodeParser parser = new CodeParser(sysName, lang, false);
		List<CompilationUnit> compUnits = parser.parseCodeInFolder(systemPath);
		System.out.println(parser.getXmlFolder());

		// parse the XML files of the code
		CompilUnitParser cUParser = CompilUnitParserFactory
				.getCompilUnitParser(lang);
		List<TypeDeclaration> sysTypeDcls = cUParser.getClasses(compUnits);
		
		HierarchyBuilder builder = new HierarchyBuilder();
		builder.buildHierarchy(sysTypeDcls);

		RefactoringReader refReader = new RefactoringReader(sysTypeDcls, lang, builder);
		List<RefactoringOperation> operations = refReader
				.getRefactOperations(refsFile);

		MetricsReader metReader = new MetricsReader(systemPath, sysName);
		LinkedHashMap<String, LinkedHashMap<String, Double>> prevMetrics = metReader
				.readMetrics();

		// list of metrics
		ArrayList<CodeMetric> metrics = new ArrayList<CodeMetric>();
		metrics.add(new LOCMetric());
		metrics.add(new NOMMetric());
		metrics.add(new RFCMetric());
		metrics.add(new CBOMetric());
		metrics.add(new DITMetric());
		metrics.add(new MPCMetric());
		metrics.add(new LCOM5Metric());
		metrics.add(new LCOM2Metric());
		metrics.add(new NOCMetric());
		metrics.add(new CYCLOMetric());

		MetricCalculator metrCalc = new MetricCalculator();
		LinkedHashMap<String, LinkedHashMap<String, LinkedHashMap<String, Double>>> metrs = metrCalc
				.predictMetrics(operations, metrics, prevMetrics);

		Set<Entry<String, LinkedHashMap<String, LinkedHashMap<String, Double>>>> entrySet = metrs
				.entrySet();
		for (Entry<String, LinkedHashMap<String, LinkedHashMap<String, Double>>> entry : entrySet) {
			System.out.println(entry.getKey());
			LinkedHashMap<String, LinkedHashMap<String, Double>> value = entry
					.getValue();
			Set<Entry<String, LinkedHashMap<String, Double>>> entrySet2 = value
					.entrySet();
			for (Entry<String, LinkedHashMap<String, Double>> entry2 : entrySet2) {

				System.out.println("bef.: " + entry2.getKey() + "="
						+ prevMetrics.get(entry2.getKey()));
				System.out.println("aft.: " + entry2.getKey() + "="
						+ entry2.getValue());

			}
		}

		assertNotNull(metrs);
	}

}
