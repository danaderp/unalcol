package edu.wayne.cs.severe.redress2.entity.refactoring.formulas.mm;

import org.junit.Test;

import edu.wayne.cs.severe.redress2.controller.metric.LCOM2Metric;
import edu.wayne.cs.severe.redress2.entity.refactoring.formulas.TestFormulasUtils;
import edu.wayne.cs.severe.redress2.utils.TestUtils;

public class LCOM2MoveMethodPFTest {

	@Test
	public void testPredictMetrVal() throws Exception {
		LCOM2Metric form = new LCOM2Metric();

		String[] expClasses = {
				"edu.wayne.cs.severe.redress2.tests.mm.SrcClass",
				"edu.wayne.cs.severe.redress2.tests.mm.TgtClass" };

		double numMethods = 5;
		double numFields = 3;
		double numFieldUsage = 0;

		Double expec = 1 - (numFieldUsage / (numFields * numMethods));

		numMethods = 4;
		numFields = 1;
		numFieldUsage = 1;

		Double expec2 = 1 - (numFieldUsage / (numFields * numMethods));

		double[] expVals = { expec, expec2 };
		// LCOM5
		// 1.066666667
		// 1.5

		TestFormulasUtils.testPredictMetrVal(form, expClasses, expVals,
				TestUtils.REFS_FILE_MM, 0);
	}

}
