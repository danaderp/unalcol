package edu.wayne.cs.severe.redress2.entity.refactoring.formulas.rdi;

import org.junit.Test;

import edu.wayne.cs.severe.redress2.controller.metric.NOCMetric;
import edu.wayne.cs.severe.redress2.entity.refactoring.formulas.TestFormulasUtils;
import edu.wayne.cs.severe.redress2.utils.TestUtils;

public class NOCReplaceDelegInhPFTest {

	@Test
	public void test() throws Exception {
		NOCMetric form = new NOCMetric();

		String[] expClasses = {
				"edu.wayne.cs.severe.redress2.tests.rdi.SrcClassRDI",
				"edu.wayne.cs.severe.redress2.tests.rdi.TgtClassRDI" };
		double[] expVals = { 0.0, 1.0 };

		TestFormulasUtils.testPredictMetrVal(form, expClasses, expVals,
				TestUtils.REFS_FILE_RDI, 0);
	}

}
