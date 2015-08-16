package edu.wayne.cs.severe.redress2.entity.refactoring.formulas.rmmo;

import org.junit.Test;

import edu.wayne.cs.severe.redress2.controller.metric.DACMetric;
import edu.wayne.cs.severe.redress2.entity.refactoring.formulas.TestFormulasUtils;
import edu.wayne.cs.severe.redress2.utils.TestUtils;

public class DACReplaceMethodObjectPFTest {

	@Test
	public void test() throws Exception {
		DACMetric form = new DACMetric();

		String[] expClasses = {
				"edu.wayne.cs.severe.redress2.tests.rmmo.SrcClassRMMO",
				"edu.wayne.cs.severe.redress2.tests.rmmo.MethodClass" };
		double[] expVals = { 2.0, 8.0 };

		TestFormulasUtils.testPredictMetrVal(form, expClasses, expVals,
				TestUtils.REFS_FILE_RMMO, 0);

	}

}
