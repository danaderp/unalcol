package edu.wayne.cs.severe.redress2.controller.metric;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ CBOMetricTest.class, CYCLOMetricTest.class,
		DACMetricTest.class, DITMetricTest.class, LCOM2MetricTest.class,
		LCOM5MetricTest.class, MPCMetricTest.class, NOCMetricTest.class,
		NOMMetricTest.class, RFCMetricTest.class })
public class AllTestsMetrics {

}
