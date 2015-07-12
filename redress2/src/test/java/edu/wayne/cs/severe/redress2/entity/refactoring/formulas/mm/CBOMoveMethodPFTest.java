package edu.wayne.cs.severe.redress2.entity.refactoring.formulas.mm;

import org.junit.Test;

import edu.wayne.cs.severe.redress2.controller.metric.CBOMetric;
import edu.wayne.cs.severe.redress2.entity.refactoring.formulas.TestFormulasUtils;
import edu.wayne.cs.severe.redress2.utils.TestUtils;

public class CBOMoveMethodPFTest {

	@Test
	public void testPredictMetrVal() throws Exception {
		CBOMetric form = new CBOMetric();

		String[] expClasses = {
				"edu.wayne.cs.severe.redress2.tests.mm.SrcClass",
				"edu.wayne.cs.severe.redress2.tests.mm.TgtClass" };
//		double[] expVals = { 1.0, 6.0 };
		double[] expVals = { 1.0, 7.0 };

		TestFormulasUtils.testPredictMetrVal(form, expClasses, expVals,
				TestUtils.REFS_FILE_MM, 0);
	}

}
