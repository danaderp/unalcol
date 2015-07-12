package edu.wayne.cs.severe.redress2.entity.refactoring.formulas.pdm;

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

public class PushDownMethodPFTest {

	@Test
	public void tests() throws Exception {

		String[] expClasses = {
				"edu.wayne.cs.severe.redress2.tests.pdm.SubClassPDM1",
				"edu.wayne.cs.severe.redress2.tests.pdm.SubClassPDM2",
				"edu.wayne.cs.severe.redress2.tests.pdm.SuperClassPDM" };

		CodeMetric[] metrics = { new RFCMetric(), new CBOMetric(),
				new LOCMetric(), new NOMMetric(), new CYCLOMetric(),
				new MPCMetric(), new LCOM5Metric(), new LCOM2Metric() };

		double numMethods = 3;
		double numFields = 1;
		double numFieldUsage = 0;

		Double expec = ((numFieldUsage / numFields) - numMethods)
				/ (1 - numMethods);
		Double expec2 = 1 - (numFieldUsage / (numFields * numMethods));

		// -------

		numMethods = 2;
		numFields = 1;
		numFieldUsage = 1;

		Double expec3 = ((numFieldUsage / numFields) - numMethods)
				/ (1 - numMethods);
		Double expec4 = 1 - (numFieldUsage / (numFields * numMethods));

		// -------

		numMethods = 3;
		numFields = 4;
		numFieldUsage = 0;

		Double expec5 = ((numFieldUsage / numFields) - numMethods)
				/ (1 - numMethods);
		Double expec6 = 1 - (numFieldUsage / (numFields * numMethods));

		// { 2, 3, 1 }
		// { 4, 3, 1 }
		// { 5, 4, 0 }
		//
		double[][] expValues = { { 10.0, 8.0, 2.0 }, { 5, 5, 0 },
				{ 19.0, 15.0, 14 }, { 3, 2, 3 }, { 3, 2, 3 }, { 6, 6, 1 },
				{ expec, expec3, expec5 }, { expec2, expec4, expec6 } };

		for (int i = 0; i < metrics.length; i++) {

			CodeMetric metric = metrics[i];
			double[] expVals = expValues[i];

			TestFormulasUtils.testPredictMetrVal(metric, expClasses, expVals,
					TestUtils.REFS_FILE_PDM, 0);

		}

	}

}
