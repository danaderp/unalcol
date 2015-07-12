package edu.wayne.cs.severe.redress2.entity.refactoring.formulas.mm;

import org.junit.Test;

import edu.wayne.cs.severe.redress2.controller.metric.LCOM5Metric;
import edu.wayne.cs.severe.redress2.entity.refactoring.formulas.TestFormulasUtils;
import edu.wayne.cs.severe.redress2.utils.TestUtils;

public class LCOM5MoveMethodPFTest {

	@Test
	public void testPredictMetrVal() throws Exception {
		LCOM5Metric form = new LCOM5Metric();

		String[] expClasses = {
				"edu.wayne.cs.severe.redress2.tests.mm.SrcClass",
				"edu.wayne.cs.severe.redress2.tests.mm.TgtClass" };

		double numMethods = 5;
		double numFields = 3;
		double numFieldUsage = 0;

		Double expec = ((numFieldUsage / numFields) - numMethods)
				/ (1 - numMethods);

		numMethods = 4;
		numFields = 1;
		numFieldUsage = 1;

		Double expec2 = ((numFieldUsage / numFields) - numMethods)
				/ (1 - numMethods);

		double[] expVals = { expec, expec2 };
		// LCOM5
		// 1.066666667
		// 1.5

		TestFormulasUtils.testPredictMetrVal(form, expClasses, expVals,
				TestUtils.REFS_FILE_MM, 0);
	}

}
