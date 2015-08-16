package edu.wayne.cs.severe.redress2.entity.refactoring.formulas.pdf;

import org.junit.Test;

import edu.wayne.cs.severe.redress2.controller.metric.CBOMetric;
import edu.wayne.cs.severe.redress2.controller.metric.CYCLOMetric;
import edu.wayne.cs.severe.redress2.controller.metric.CodeMetric;
import edu.wayne.cs.severe.redress2.controller.metric.DACMetric;
import edu.wayne.cs.severe.redress2.controller.metric.LCOM2Metric;
import edu.wayne.cs.severe.redress2.controller.metric.LCOM5Metric;
import edu.wayne.cs.severe.redress2.controller.metric.LOCMetric;
import edu.wayne.cs.severe.redress2.controller.metric.NOMMetric;
import edu.wayne.cs.severe.redress2.controller.metric.RFCMetric;
import edu.wayne.cs.severe.redress2.entity.refactoring.formulas.TestFormulasUtils;
import edu.wayne.cs.severe.redress2.utils.TestUtils;

public class PushDownFieldPFTest {

	@Test
	public void tests() throws Exception {

		String[] expClasses = {
				"edu.wayne.cs.severe.redress2.tests.pdf.SubClassPDF1",
				"edu.wayne.cs.severe.redress2.tests.pdf.SubClassPDF2",
				"edu.wayne.cs.severe.redress2.tests.pdf.SuperClassPDF" };

		CodeMetric[] forms = { new RFCMetric(),
				new CBOMetric(),
				new LOCMetric(),
				new NOMMetric(), new CYCLOMetric(),
				new DACMetric(), new LCOM5Metric(),
				new LCOM2Metric() };

		double numMethods = 4;
		double numFields = 3;
		double numFieldUsage = 3;

		Double expec = ((numFieldUsage / numFields) - numMethods)
				/ (1 - numMethods);
		Double expec2 = 1 - (numFieldUsage / (numFields * numMethods));

		numMethods = 4;
		numFields = 2;
		numFieldUsage = 4;

		Double expec3 = ((numFieldUsage / numFields) - numMethods)
				/ (1 - numMethods);
		Double expec4 = 1 - (numFieldUsage / (numFields * numMethods));

		numMethods = 4;
		numFields = 1;
		numFieldUsage = 1;

		Double expec5 = ((numFieldUsage / numFields) - numMethods)
				/ (1 - numMethods);
		Double expec6 = 1 - (numFieldUsage / (numFields * numMethods));

		//0		1 		1
		//{ 1, 1, 1 }
		//CBO: { 1, 1, 1 }
		double[][] expValues = { { 4, 5, 6 }, { 1, 2, 2 }, { 16, 16, 11 },
				{ 4, 4, 4 }, { 4, 4, 4 }, { 2, 2, 1 },
				{ expec, expec3, expec5 }, { expec2, expec4, expec6 } };

		for (int i = 0; i < forms.length; i++) {

			CodeMetric form = forms[i];
			double[] expVals = expValues[i];

			TestFormulasUtils.testPredictMetrVal(form, expClasses, expVals,
					TestUtils.REFS_FILE_PDF, 0);

		}

	}

}
