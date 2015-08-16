package edu.wayne.cs.severe.redress2.entity.refactoring.formulas.rdi;

import org.junit.Test;

import edu.wayne.cs.severe.redress2.controller.metric.LCOM2Metric;
import edu.wayne.cs.severe.redress2.entity.refactoring.formulas.TestFormulasUtils;
import edu.wayne.cs.severe.redress2.utils.TestUtils;

public class LCOM2ReplaceDelegInhPFTest {

	@Test
	public void test() throws Exception {
		LCOM2Metric form = new LCOM2Metric();

		String[] expClasses = {
				"edu.wayne.cs.severe.redress2.tests.rdi.SrcClassRDI",
				"edu.wayne.cs.severe.redress2.tests.rdi.TgtClassRDI" };

		double[] expVals = { 0.0, 0.0 };

		TestFormulasUtils.testPredictMetrVal(form, expClasses, expVals,
				TestUtils.REFS_FILE_RDI, 0);
	}

}
