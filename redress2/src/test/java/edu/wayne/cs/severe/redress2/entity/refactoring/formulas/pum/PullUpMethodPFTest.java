package edu.wayne.cs.severe.redress2.entity.refactoring.formulas.pum;

import org.junit.Test;

import edu.wayne.cs.severe.redress2.controller.metric.CBOMetric;
import edu.wayne.cs.severe.redress2.controller.metric.CYCLOMetric;
import edu.wayne.cs.severe.redress2.controller.metric.CodeMetric;
import edu.wayne.cs.severe.redress2.controller.metric.LCOM2Metric;
import edu.wayne.cs.severe.redress2.controller.metric.LCOM5Metric;
import edu.wayne.cs.severe.redress2.controller.metric.LOCMetric;
import edu.wayne.cs.severe.redress2.controller.metric.MPCMetric;
import edu.wayne.cs.severe.redress2.controller.metric.NOMMetric;
import edu.wayne.cs.severe.redress2.controller.metric.RFCMetric;
import edu.wayne.cs.severe.redress2.entity.refactoring.formulas.TestFormulasUtils;
import edu.wayne.cs.severe.redress2.utils.TestUtils;

public class PullUpMethodPFTest {

	@Test
	public void tests() throws Exception {

		String[] expClasses = {
				"edu.wayne.cs.severe.redress2.tests.pum.SubClassPUM1",
				"edu.wayne.cs.severe.redress2.tests.pum.SubClassPUM2",
				"edu.wayne.cs.severe.redress2.tests.pum.SuperClassPUM" };

		CodeMetric[] forms = { new RFCMetric(), new CBOMetric(),
				new LOCMetric(), new NOMMetric(), new CYCLOMetric(),
				new MPCMetric(), new LCOM5Metric(), new LCOM2Metric() };

		double numMethods = 4;
		double numFields = 2;
		double numFieldUsage = 3;

		Double expec = ((numFieldUsage / numFields) - numMethods)
				/ (1 - numMethods);
		Double expec2 = 1 - (numFieldUsage / (numFields * numMethods));

		numMethods = 4;
		numFields = 2;
		numFieldUsage = 2;

		Double expec3 = ((numFieldUsage / numFields) - numMethods)
				/ (1 - numMethods);
		Double expec4 = 1 - (numFieldUsage / (numFields * numMethods));

		numMethods = 5;
		numFields = 1;
		numFieldUsage = 1;

		Double expec5 = ((numFieldUsage / numFields) - numMethods)
				/ (1 - numMethods);
		Double expec6 = 1 - (numFieldUsage / (numFields * numMethods));

		// 3 1 3

		double[][] expValues = { { 4, 3, 9 }, { 3, 1, 3 }, { 17, 14, 15 },
				{ 4, 4, 5 }, { 4, 4, 5 }, { 1, 0, 2 },
				{ expec, expec3, expec5 }, { expec2, expec4, expec6 } };

		for (int i = 0; i < forms.length; i++) {

			CodeMetric form = forms[i];
			double[] expVals = expValues[i];

			TestFormulasUtils.testPredictMetrVal(form, expClasses, expVals,
					TestUtils.REFS_FILE_PUM, 0);

		}

	}

}
