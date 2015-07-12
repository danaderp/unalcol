package edu.wayne.cs.severe.redress2.entity.refactoring.formulas.mm;

import org.junit.Test;

import edu.wayne.cs.severe.redress2.controller.metric.CYCLOMetric;
import edu.wayne.cs.severe.redress2.entity.refactoring.formulas.TestFormulasUtils;
import edu.wayne.cs.severe.redress2.utils.TestUtils;

public class CYCLOMoveMethodPFTest {

	@Test
	public void testPredictMetrVal() throws Exception {
		CYCLOMetric form = new CYCLOMetric();

		String[] expClasses = {
				"edu.wayne.cs.severe.redress2.tests.mm.SrcClass",
				"edu.wayne.cs.severe.redress2.tests.mm.TgtClass" };
		double[] expVals = { 5.0, 6.0 };

		TestFormulasUtils.testPredictMetrVal(form, expClasses, expVals,
				TestUtils.REFS_FILE_MM, 0);
	}

}
