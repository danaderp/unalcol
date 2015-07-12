package edu.wayne.cs.severe.redress2.entity.refactoring.formulas;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.wayne.cs.severe.redress2.controller.HierarchyBuilder;
import edu.wayne.cs.severe.redress2.controller.metric.CodeMetric;
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

public class TestFormulasUtils {

	// logger
	private static Logger LOGGER = LoggerFactory
			.getLogger(TestFormulasUtils.class);

	public static void testPredictMetrVal(CodeMetric metric,
			String[] expClasses, double[] expVals, File refsFile, int operNum)
			throws Exception {
		
		String sysName = TestUtils.SYS_NAME_TEST5;
		File systemPath = TestUtils.SYS_PATH_TEST5;
		ProgLang lang = TestUtils.SYS_LANG_JAVA;

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

		RefactoringReader refReader = new RefactoringReader(sysTypeDcls, lang,
				builder);
		List<RefactoringOperation> operations = refReader
				.getRefactOperations(refsFile);
		
		PredictionFormula form = operations.get(operNum).getRefType().getPredFormula(metric.getMetricAcronym());

		LOGGER.debug("Testing " + form.getClass().getSimpleName());

		HashMap<String, Double> predictMetrVal = form.predictMetrVal(
				operations.get(operNum), prevMetrics);

		LOGGER.debug("Checking if the metric values are not null");
		assertNotNull(predictMetrVal);

		LOGGER.debug("Checking the size of expected classes");
		assertEquals(expClasses.length, predictMetrVal.size());

		LOGGER.debug("Checking the actual metric values");
		for (int i = 0; i < expClasses.length; i++) {
			String expCl = expClasses[i];
			LOGGER.debug(expCl + " -> ");
			Double val = predictMetrVal.get(expCl);
			assertEquals(new Double(expVals[i]), val);
			LOGGER.debug("PASSED!");

		}

		LOGGER.debug("-----------------------------------------");
	}
}
