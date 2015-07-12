package edu.wayne.cs.severe.redress2.entity.refactoring.formulas.rid;

import org.junit.Test;

import edu.wayne.cs.severe.redress2.controller.metric.CodeMetric;
import edu.wayne.cs.severe.redress2.controller.metric.DACMetric;
import edu.wayne.cs.severe.redress2.controller.metric.DITMetric;
import edu.wayne.cs.severe.redress2.controller.metric.LCOM2Metric;
import edu.wayne.cs.severe.redress2.controller.metric.LCOM5Metric;
import edu.wayne.cs.severe.redress2.controller.metric.LOCMetric;
import edu.wayne.cs.severe.redress2.controller.metric.NOCMetric;
import edu.wayne.cs.severe.redress2.entity.refactoring.formulas.TestFormulasUtils;
import edu.wayne.cs.severe.redress2.utils.TestUtils;

public class ReplaceInherDelegPFTest {

	@Test
	public void tests() throws Exception {

		String[] expClasses = {
				"edu.wayne.cs.severe.redress2.tests.rid.SrcClassRID",
				"edu.wayne.cs.severe.redress2.tests.rid.TgtClassRID" };

		CodeMetric[] forms = { new NOCMetric(),
				new DITMetric(), new LOCMetric(),
				new DACMetric(), new LCOM5Metric(),
				new LCOM2Metric() };

		double numMethods = 2;
		double numFields = 2;
		double numFieldUsage = 3;

		Double expec = ((numFieldUsage / numFields) - numMethods)
				/ (1 - numMethods);

		Double expec2 = 1 - (numFieldUsage / (numFields * numMethods));

		double[][] expValues = { { 0, 0 }, { 0, 0 }, { 11, 4 }, { 2, 0 },
				{ expec, 0 }, { expec2, 0 } };

		for (int i = 0; i < forms.length; i++) {

			CodeMetric form = forms[i];
			double[] expVals = expValues[i];

			TestFormulasUtils.testPredictMetrVal(form, expClasses, expVals,
					TestUtils.REFS_FILE_RID, 0);

		}

	}

}
