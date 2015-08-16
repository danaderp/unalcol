package edu.wayne.cs.severe.redress2.entity.refactoring.formulas.mf;

import org.junit.Test;

import edu.wayne.cs.severe.redress2.controller.metric.LCOM5Metric;
import edu.wayne.cs.severe.redress2.entity.refactoring.formulas.TestFormulasUtils;
import edu.wayne.cs.severe.redress2.utils.TestUtils;

public class LCOM5MoveFieldPFTest {

	@Test
	public void test() throws Exception {
		LCOM5Metric form = new LCOM5Metric();

		String[] expClasses = {
				"edu.wayne.cs.severe.redress2.tests.mf.SrcClassMF",
				"edu.wayne.cs.severe.redress2.tests.mf.TgtClassMF" };

		double numMethods = 4;
		double numFields = 1;
		double numFieldUsage = 2;

		Double expec = ((numFieldUsage / numFields) - numMethods)
				/ (1 - numMethods);

		numMethods = 4;
		numFields = 2;
		numFieldUsage = 4;

		Double expec2 = ((numFieldUsage / numFields) - numMethods)
				/ (1 - numMethods);

		double[] expVals = { expec, expec2 };

		TestFormulasUtils.testPredictMetrVal(form, expClasses, expVals,
				TestUtils.REFS_FILE_MF, 0);
	}

}
