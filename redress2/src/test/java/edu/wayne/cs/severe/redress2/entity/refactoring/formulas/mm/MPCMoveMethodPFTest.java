package edu.wayne.cs.severe.redress2.entity.refactoring.formulas.mm;

import org.junit.Test;

import edu.wayne.cs.severe.redress2.controller.metric.MPCMetric;
import edu.wayne.cs.severe.redress2.entity.refactoring.formulas.TestFormulasUtils;
import edu.wayne.cs.severe.redress2.utils.TestUtils;

public class MPCMoveMethodPFTest {

	@Test
	public void testPredictMetrVal() throws Exception {
		MPCMetric form = new MPCMetric();

		String[] expClasses = {
				"edu.wayne.cs.severe.redress2.tests.mm.SrcClass",
				"edu.wayne.cs.severe.redress2.tests.mm.TgtClass" };
		double[] expVals = { 0.0, 3.0 };

		TestFormulasUtils.testPredictMetrVal(form, expClasses, expVals,
				TestUtils.REFS_FILE_MM, 0);

	}

}
