package edu.wayne.cs.severe.redress2.entity.refactoring.formulas.mf;

import org.junit.Test;

import edu.wayne.cs.severe.redress2.controller.metric.NOMMetric;
import edu.wayne.cs.severe.redress2.entity.refactoring.formulas.TestFormulasUtils;
import edu.wayne.cs.severe.redress2.utils.TestUtils;

public class NOMMoveFieldPFTest {

	@Test
	public void test() throws Exception {
		NOMMetric form = new NOMMetric();

		String[] expClasses = {
				"edu.wayne.cs.severe.redress2.tests.mf.SrcClassMF",
				"edu.wayne.cs.severe.redress2.tests.mf.TgtClassMF" };
		double[] expVals = { 4.0, 4.0 };

		TestFormulasUtils.testPredictMetrVal(form, expClasses, expVals,
				TestUtils.REFS_FILE_MF, 0);
	}

}
