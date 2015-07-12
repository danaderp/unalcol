package edu.wayne.cs.severe.redress2.entity.refactoring.formulas.mf;

import org.junit.Test;

import edu.wayne.cs.severe.redress2.controller.metric.LCOM2Metric;
import edu.wayne.cs.severe.redress2.entity.refactoring.formulas.TestFormulasUtils;
import edu.wayne.cs.severe.redress2.utils.TestUtils;

public class LCOM2MoveFieldPFTest {

	@Test
	public void test() throws Exception {
		LCOM2Metric form = new LCOM2Metric();

		String[] expClasses = {
				"edu.wayne.cs.severe.redress2.tests.mf.SrcClassMF",
				"edu.wayne.cs.severe.redress2.tests.mf.TgtClassMF" };

		double numMethods = 4;
		double numFields = 1;
		double numFieldUsage = 2;

		Double expec = 1 - (numFieldUsage / (numFields * numMethods));

		numMethods = 4;
		numFields = 2;
		numFieldUsage = 4;

		Double expec2 = 1 - (numFieldUsage / (numFields * numMethods));

		double[] expVals = { expec, expec2 };

		TestFormulasUtils.testPredictMetrVal(form, expClasses, expVals,
				TestUtils.REFS_FILE_MF, 0);
	}

}
