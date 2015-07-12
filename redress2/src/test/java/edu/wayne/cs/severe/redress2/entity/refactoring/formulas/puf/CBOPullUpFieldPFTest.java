package edu.wayne.cs.severe.redress2.entity.refactoring.formulas.puf;

import org.junit.Test;

import edu.wayne.cs.severe.redress2.controller.metric.CBOMetric;
import edu.wayne.cs.severe.redress2.entity.refactoring.formulas.TestFormulasUtils;
import edu.wayne.cs.severe.redress2.utils.TestUtils;

public class CBOPullUpFieldPFTest {
	@Test
	public void testPredictMetrVal2() throws Exception {

		CBOMetric form = new CBOMetric();

		String[] expClasses = {
				"edu.wayne.cs.severe.redress2.tests.puf.SubClass1",
				"edu.wayne.cs.severe.redress2.tests.puf.SuperClass" };
//		double[] expVals = { 1.0, 0.0 };
		double[] expVals = { 4.0, 0.0 };

		TestFormulasUtils.testPredictMetrVal(form, expClasses, expVals,
				TestUtils.REFS_FILE_PUF, 0);

	}

	@Test
	public void testPredictMetrVal3() throws Exception {

		CBOMetric form = new CBOMetric();

		String[] expClasses = {
				"edu.wayne.cs.severe.redress2.tests.puf.SubClass1",
				"edu.wayne.cs.severe.redress2.tests.puf.SubClass2",
				"edu.wayne.cs.severe.redress2.tests.puf.SubClass3",
				"edu.wayne.cs.severe.redress2.tests.puf.SuperClass" };
		// 3
		// 1
		// 2
		// 0

//		double[] expVals = { 3, 1, 2, 0 };
		double[] expVals = { 4, 1, 4, 0 };

		TestFormulasUtils.testPredictMetrVal(form, expClasses, expVals,
				TestUtils.REFS_FILE_PUF, 1);

	}

}
