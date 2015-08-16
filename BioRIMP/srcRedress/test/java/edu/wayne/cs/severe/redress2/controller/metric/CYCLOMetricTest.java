package edu.wayne.cs.severe.redress2.controller.metric;

import static org.junit.Assert.assertEquals;

import java.io.File;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import edu.wayne.cs.severe.redress2.entity.CompilationUnit;
import edu.wayne.cs.severe.redress2.entity.TypeDeclaration;
import edu.wayne.cs.severe.redress2.exception.MetricException;

public class CYCLOMetricTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Test
	public void testComputeMetric() throws MetricException {

		File compUnitFile = new File(
				"test_data/code/test_parsed_cs/Test6.cs.xml");
		TypeDeclaration typeDcl = new TypeDeclaration("", "Test6", false,
				new CompilationUnit(compUnitFile));
		CYCLOMetric metric = new CYCLOMetric(null, null);

		double val = metric.computeMetric(typeDcl);

		assertEquals(new Double(42), new Double(val));
	}

	@Test
	public void testComputeMetric2() throws MetricException {

		File compUnitFile = new File(
				"test_data/code/test_parsed/Test6.java.xml");
		TypeDeclaration typeDcl = new TypeDeclaration("", "Test6", false,
				new CompilationUnit(compUnitFile));
		CYCLOMetric metric = new CYCLOMetric(null, null);

		double val = metric.computeMetric(typeDcl);

		assertEquals(new Double(42), new Double(val));
	}

}
