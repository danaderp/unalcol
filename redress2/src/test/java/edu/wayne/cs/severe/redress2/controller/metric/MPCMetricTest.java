package edu.wayne.cs.severe.redress2.controller.metric;

import static org.junit.Assert.assertEquals;

import java.io.File;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import edu.wayne.cs.severe.redress2.entity.CompilationUnit;
import edu.wayne.cs.severe.redress2.entity.TypeDeclaration;
import edu.wayne.cs.severe.redress2.exception.MetricException;

public class MPCMetricTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Test
	public void testComputeMetric() throws MetricException {
		File compUnitFile = new File(
				"test_data/code/test_parsed_cs/WindowMain.xaml.cs.xml");
		TypeDeclaration typeDcl = new TypeDeclaration("VirtualRouterClient",
				"WindowMain", false, new CompilationUnit(compUnitFile));
		MPCMetric metric = new MPCMetric(null, null);

		double val = metric.computeMetric(typeDcl);
		assertEquals(Double.valueOf(64.0), Double.valueOf(val));

	}

	@Test
	public void testComputeMetric2() throws MetricException {
		File compUnitFile = new File("test_data/code/test_parsed/Test.java.xml");
		TypeDeclaration typeDcl = new TypeDeclaration("test", "C7", false,
				new CompilationUnit(compUnitFile));
		MPCMetric metric = new MPCMetric(null, null);

		double val = metric.computeMetric(typeDcl);
		assertEquals(Double.valueOf(1.0), Double.valueOf(val));

	}

	@Test
	public void testComputeMetric3() throws MetricException {

		File compUnitFile = new File(
				"test_data/code/test_parsed/DataSourceFactory.java.xml");
		TypeDeclaration typeDcl = new TypeDeclaration(
				"org.apache.ibatis.datasource", "DataSourceFactory", false,
				new CompilationUnit(compUnitFile));
		MPCMetric metric = new MPCMetric(null, null);

		double val = metric.computeMetric(typeDcl);
		assertEquals(Double.valueOf(0.0), Double.valueOf(val));

	}

	@Test
	public void testComputeMetric4() throws MetricException {

		File compUnitFile = new File(
				"test_data/code/test_parsed/FigClassifierRole.java.xml");
		TypeDeclaration typeDcl = new TypeDeclaration(
				"org.argouml.uml.diagram.collaboration.ui", "FigClassifierRole", false,
				new CompilationUnit(compUnitFile));
		MPCMetric metric = new MPCMetric(null, null);

		double val = metric.computeMetric(typeDcl);
//		assertEquals(Double.valueOf(0.0), Double.valueOf(val));

	}
	

}