package edu.wayne.cs.severe.redress2.controller.metric;

import static org.junit.Assert.assertEquals;

import java.io.File;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import edu.wayne.cs.severe.redress2.entity.CompilationUnit;
import edu.wayne.cs.severe.redress2.entity.TypeDeclaration;
import edu.wayne.cs.severe.redress2.exception.MetricException;

public class LCOM2MetricTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Test
	public void testComputeMetric() throws MetricException {

		File compUnitFile = new File(
				"test_data/code/test_parsed/Test5.java.xml");
		TypeDeclaration typeDcl = new TypeDeclaration("test.test2.", "Test5",
				false, new CompilationUnit(compUnitFile));
		LCOM2Metric metric = new LCOM2Metric(null, null);

		double val = metric.computeMetric(typeDcl);
		double numMethods = 6;
		double numFields = 6;
		double numFieldUsage = 20;

		Double expec = 1 - (numFieldUsage / (numFields * numMethods));
		assertEquals(expec, Double.valueOf(val));
	}

	@Test
	public void testComputeMetric2() throws MetricException {

		File compUnitFile = new File(
				"test_data/code/test_parsed_cs/Test5.cs.xml");
		TypeDeclaration typeDcl = new TypeDeclaration("test.test2.", "Test5",
				false, new CompilationUnit(compUnitFile));
		LCOM2Metric metric = new LCOM2Metric(null, null);

		double val = metric.computeMetric(typeDcl);
		double numMethods = 6;
		double numFields = 9;
		double numFieldUsage = 20;

		Double expec = 1 - (numFieldUsage / (numFields * numMethods));
		assertEquals(expec, Double.valueOf(val));
	}
}
