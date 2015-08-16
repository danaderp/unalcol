package edu.wayne.cs.severe.redress2.controller.metric;

import static org.junit.Assert.assertEquals;

import java.io.File;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import edu.wayne.cs.severe.redress2.entity.CompilationUnit;
import edu.wayne.cs.severe.redress2.entity.TypeDeclaration;
import edu.wayne.cs.severe.redress2.exception.MetricException;

public class DACMetricTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Test
	public void testComputeMetric() throws MetricException {

		File compUnitFile = new File(
				"test_data/code/test_parsed/Test7.java.xml");
		TypeDeclaration typeDcl = new TypeDeclaration("test.test2.", "Test7",
				false, new CompilationUnit(compUnitFile));
		DACMetric metric = new DACMetric(null, null);

		double val = metric.computeMetric(typeDcl);

		Double expec = 6.0;
		assertEquals(expec, Double.valueOf(val));
	}

	@Test
	public void testComputeMetric2() throws MetricException {

		File compUnitFile = new File(
				"test_data/code/test_parsed/SubClass2.java.xml");
		TypeDeclaration typeDcl = new TypeDeclaration(
				"edu.wayne.cs.severe.redress2.tests.puf.", "SubClass2", false,
				new CompilationUnit(compUnitFile));
		DACMetric metric = new DACMetric(null, null);

		double val = metric.computeMetric(typeDcl);

		Double expec = 1.0;
		assertEquals(expec, Double.valueOf(val));
	}
}
