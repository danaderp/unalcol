package edu.wayne.cs.severe.redress2.entity.refactoring.formulas.em;

import org.junit.Test;

import edu.wayne.cs.severe.redress2.controller.metric.CYCLOMetric;
import edu.wayne.cs.severe.redress2.controller.metric.CodeMetric;
import edu.wayne.cs.severe.redress2.controller.metric.LCOM2Metric;
import edu.wayne.cs.severe.redress2.controller.metric.LCOM5Metric;
import edu.wayne.cs.severe.redress2.controller.metric.LOCMetric;
import edu.wayne.cs.severe.redress2.controller.metric.NOMMetric;
import edu.wayne.cs.severe.redress2.controller.metric.RFCMetric;
import edu.wayne.cs.severe.redress2.entity.refactoring.formulas.TestFormulasUtils;
import edu.wayne.cs.severe.redress2.utils.TestUtils;

public class ExtractMethodPFTest {

	@Test
	public void tests() throws Exception {

		String[] expClasses = { "edu.wayne.cs.severe.redress2.tests.em.SrcClassEM" };

		CodeMetric[] forms = { new RFCMetric(),
				new LOCMetric(),
				new NOMMetric(), new CYCLOMetric(),
				new LCOM5Metric(), new LCOM2Metric() };

		double numMethods = 3;
		double numFields = 2;
		double numFieldUsage = 0;

		Double expec = ((numFieldUsage / numFields) - numMethods)
				/ (1 - numMethods);

		Double expec2 = 1 - (numFieldUsage / (numFields * numMethods));

		double[][] expValues = { { 4.0 }, { 17.0 }, { 3.0 }, { 5.0 },
				{ expec }, { expec2 } };

		for (int i = 0; i < forms.length; i++) {

			CodeMetric form = forms[i];
			double[] expVals = expValues[i];

			TestFormulasUtils.testPredictMetrVal(form, expClasses, expVals,
					TestUtils.REFS_FILE_EM, 0);

		}

	}

}
