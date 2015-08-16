package edu.wayne.cs.severe.redress2.entity.refactoring.formulas.ec;

import org.junit.Test;

import edu.wayne.cs.severe.redress2.controller.metric.CBOMetric;
import edu.wayne.cs.severe.redress2.controller.metric.CYCLOMetric;
import edu.wayne.cs.severe.redress2.controller.metric.CodeMetric;
import edu.wayne.cs.severe.redress2.controller.metric.DACMetric;
import edu.wayne.cs.severe.redress2.controller.metric.LCOM2Metric;
import edu.wayne.cs.severe.redress2.controller.metric.LCOM5Metric;
import edu.wayne.cs.severe.redress2.controller.metric.LOCMetric;
import edu.wayne.cs.severe.redress2.controller.metric.MPCMetric;
import edu.wayne.cs.severe.redress2.controller.metric.NOMMetric;
import edu.wayne.cs.severe.redress2.controller.metric.RFCMetric;
import edu.wayne.cs.severe.redress2.entity.refactoring.formulas.TestFormulasUtils;
import edu.wayne.cs.severe.redress2.utils.TestUtils;

public class ExctractClassPFTest {

	@Test
	public void testRFC() throws Exception {

		CodeMetric form = new RFCMetric();
		String[] expClasses = {
				"edu.wayne.cs.severe.redress2.tests.ec.SrcClassEC",
				"edu.wayne.cs.severe.redress2.tests.ec.TgtClassEC" };

		// -(1+3) , (1+4) --MM
		// -2 , 2 --MF
		// double[] expVals = { 12.0, 0.0 };
		double[] expVals = { 8.0, 7.0 };

		TestFormulasUtils.testPredictMetrVal(form, expClasses, expVals,
				TestUtils.REFS_FILE_EC, 0);

	}

	@Test
	public void testCBO() throws Exception {

		CodeMetric form = new CBOMetric();
		String[] expClasses = {
				"edu.wayne.cs.severe.redress2.tests.ec.SrcClassEC",
				"edu.wayne.cs.severe.redress2.tests.ec.TgtClassEC" };

		// -(4) , (5) --MM
		// 1 , 0 --MF
		// double[] expVals = { 8.0, 0.0 };
		double[] expVals = { 3.0, 7.0 };

		TestFormulasUtils.testPredictMetrVal(form, expClasses, expVals,
				TestUtils.REFS_FILE_EC, 0);

	}

	@Test
	public void testLOC() throws Exception {

		CodeMetric form = new LOCMetric();
		String[] expClasses = {
				"edu.wayne.cs.severe.redress2.tests.ec.SrcClassEC",
				"edu.wayne.cs.severe.redress2.tests.ec.TgtClassEC" };

		// -16 , 16 --MM
		// -7 , 7 --MF
		// double[] expVals = { 46.0, 0.0 };
		double[] expVals = { 30.0, 25.0 };

		TestFormulasUtils.testPredictMetrVal(form, expClasses, expVals,
				TestUtils.REFS_FILE_EC, 0);

	}

	@Test
	public void testNOM() throws Exception {

		CodeMetric form = new NOMMetric();
		String[] expClasses = {
				"edu.wayne.cs.severe.redress2.tests.ec.SrcClassEC",
				"edu.wayne.cs.severe.redress2.tests.ec.TgtClassEC" };

		// -(1) , (1) --MM
		// -2 , 2 --MF
		// double[] expVals = { 9.0, 0.0 };
		double[] expVals = { 8.0, 3.0 };

		TestFormulasUtils.testPredictMetrVal(form, expClasses, expVals,
				TestUtils.REFS_FILE_EC, 0);

	}

	@Test
	public void testCYCLO() throws Exception {

		CodeMetric form = new CYCLOMetric();
		String[] expClasses = {
				"edu.wayne.cs.severe.redress2.tests.ec.SrcClassEC",
				"edu.wayne.cs.severe.redress2.tests.ec.TgtClassEC" };

		// -(4) , (4) --MM
		// -2 , 2 --MF
		// double[] expVals = { 11.0, 0.0 };
		double[] expVals = { 8.0, 5.0 };

		TestFormulasUtils.testPredictMetrVal(form, expClasses, expVals,
				TestUtils.REFS_FILE_EC, 0);

	}

	@Test
	public void testDAC() throws Exception {

		CodeMetric form = new DACMetric();
		String[] expClasses = {
				"edu.wayne.cs.severe.redress2.tests.ec.SrcClassEC",
				"edu.wayne.cs.severe.redress2.tests.ec.TgtClassEC" };

		// 0 , 1 --MF
		// double[] expVals = { 2.0, 0.0 };
		double[] expVals = { 2.0, 1.0 };

		TestFormulasUtils.testPredictMetrVal(form, expClasses, expVals,
				TestUtils.REFS_FILE_EC, 0);

	}

	@Test
	public void testMPC() throws Exception {

		CodeMetric form = new MPCMetric();
		String[] expClasses = {
				"edu.wayne.cs.severe.redress2.tests.ec.SrcClassEC",
				"edu.wayne.cs.severe.redress2.tests.ec.TgtClassEC" };

		// -(2) , (4) --MM
		// 2 , 0 --MF
		// double[] expVals = { 3.0, 0.0 };
		double[] expVals = { 3.0, 4.0 };

		TestFormulasUtils.testPredictMetrVal(form, expClasses, expVals,
				TestUtils.REFS_FILE_EC, 0);

	}

	@Test
	public void testLCOM5() throws Exception {

		CodeMetric form = new LCOM5Metric();
		String[] expClasses = {
				"edu.wayne.cs.severe.redress2.tests.ec.SrcClassEC",
				"edu.wayne.cs.severe.redress2.tests.ec.TgtClassEC" };

		// MM src
		// double numMethods = 8;
		// double numFields = 4;
		// double numFieldUsage = 2;

		// MM tgt
		// double numMethods = 1;
		// double numFields = 0;
		// double numFieldUsage = 0;

		// MF src
		// double numMethods = 6;
		// double numFields = 3;
		// double numFieldUsage = 0;

		// MF tgt
		// double numMethods = 1;
		// double numFields = 1;
		// double numFieldUsage = 2;

		double numMethods = 8;
		double numFields = 4;
		double numFieldUsage = 2 + 2;

		Double expec = ((numFieldUsage / numFields) - numMethods)
				/ (1 - numMethods);

		numMethods = 3;
		numFields = 1;
		numFieldUsage = 2;

		Double expec2 = ((numFieldUsage / numFields) - numMethods)
				/ (1 - numMethods);

		double[] expVals = { expec, expec2 };

		TestFormulasUtils.testPredictMetrVal(form, expClasses, expVals,
				TestUtils.REFS_FILE_EC, 0);

	}

	@Test
	public void testLCOM2() throws Exception {

		LCOM2Metric form = new LCOM2Metric();
		String[] expClasses = {
				"edu.wayne.cs.severe.redress2.tests.ec.SrcClassEC",
				"edu.wayne.cs.severe.redress2.tests.ec.TgtClassEC" };

		// MM src
		// double numMethods = 8;
		// double numFields = 4;
		// double numFieldUsage = 2;

		// MM tgt
		// double numMethods = 1;
		// double numFields = 0;
		// double numFieldUsage = 0;

		// MF src
		// double numMethods = 6;
		// double numFields = 3;
		// double numFieldUsage = 0;

		// MF tgt
		// double numMethods = 1;
		// double numFields = 1;
		// double numFieldUsage = 2;

		// double numMethods = 6;
		// double numFields = 3;
		// double numFieldUsage = 0;
		double numMethods = 8;
		double numFields = 4;
		double numFieldUsage = 2 + 2;

		Double expec = 1 - (numFieldUsage / (numFields * numMethods));

		numMethods = 3;
		numFields = 1;
		numFieldUsage = 2;

		Double expec2 = 1 - (numFieldUsage / (numFields * numMethods));

		double[] expVals = { expec, expec2 };

		TestFormulasUtils.testPredictMetrVal(form, expClasses, expVals,
				TestUtils.REFS_FILE_EC, 0);

	}

}
