package edu.wayne.cs.severe.redress2.entity.refactoring.formulas.rmmo;

import org.junit.Test;

import edu.wayne.cs.severe.redress2.controller.metric.CYCLOMetric;
import edu.wayne.cs.severe.redress2.entity.refactoring.formulas.TestFormulasUtils;
import edu.wayne.cs.severe.redress2.utils.TestUtils;

public class CYCLOReplaceMethodObjectPFTest {

	@Test
	public void test() throws Exception {
		CYCLOMetric form = new CYCLOMetric();

		String[] expClasses = {
				"edu.wayne.cs.severe.redress2.tests.rmmo.SrcClassRMMO",
				"edu.wayne.cs.severe.redress2.tests.rmmo.MethodClass" };
		double[] expVals = { 6.0, 4.0 };

		TestFormulasUtils.testPredictMetrVal(form, expClasses, expVals,
				TestUtils.REFS_FILE_RMMO, 0);

	}

}
