package edu.wayne.cs.severe.redress2.entity.refactoring.formulas.rmmo;

import org.junit.Test;

import edu.wayne.cs.severe.redress2.controller.metric.LCOM5Metric;
import edu.wayne.cs.severe.redress2.entity.refactoring.formulas.TestFormulasUtils;
import edu.wayne.cs.severe.redress2.utils.TestUtils;

public class LCOM5ReplaceMethodObjectPFTest {

	@Test
	public void testPredictMetrVal() throws Exception {
		LCOM5Metric form = new LCOM5Metric();

		String[] expClasses = {
				"edu.wayne.cs.severe.redress2.tests.rmmo.SrcClassRMMO",
				"edu.wayne.cs.severe.redress2.tests.rmmo.MethodClass" };

		double numMethods = 6;
		double numFields = 3;
		double numFieldUsage = 0;

		Double expec = ((numFieldUsage / numFields) - numMethods)
				/ (1 - numMethods);

		numMethods = 2;
		numFields = 8;
		numFieldUsage = 3 + 8;

		Double expec2 = ((numFieldUsage / numFields) - numMethods)
				/ (1 - numMethods);

		double[] expVals = { expec, expec2 };

		TestFormulasUtils.testPredictMetrVal(form, expClasses, expVals,
				TestUtils.REFS_FILE_RMMO, 0);
	}

}
