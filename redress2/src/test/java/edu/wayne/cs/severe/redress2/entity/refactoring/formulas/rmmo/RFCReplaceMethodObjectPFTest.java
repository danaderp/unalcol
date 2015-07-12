package edu.wayne.cs.severe.redress2.entity.refactoring.formulas.rmmo;

import org.junit.Test;

import edu.wayne.cs.severe.redress2.controller.metric.RFCMetric;
import edu.wayne.cs.severe.redress2.entity.refactoring.formulas.TestFormulasUtils;
import edu.wayne.cs.severe.redress2.utils.TestUtils;

public class RFCReplaceMethodObjectPFTest {

	@Test
	public void test() throws Exception {
		RFCMetric form = new RFCMetric();

		String[] expClasses = {
				"edu.wayne.cs.severe.redress2.tests.rmmo.SrcClassRMMO",
				"edu.wayne.cs.severe.redress2.tests.rmmo.MethodClass" };
		double[] expVals = { 7.0, 5.0 };

		TestFormulasUtils.testPredictMetrVal(form, expClasses, expVals,
				TestUtils.REFS_FILE_RMMO, 0);

	}

}
