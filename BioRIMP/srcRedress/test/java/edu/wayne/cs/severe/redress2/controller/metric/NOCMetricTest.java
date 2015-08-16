package edu.wayne.cs.severe.redress2.controller.metric;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import edu.wayne.cs.severe.redress2.controller.HierarchyBuilder;
import edu.wayne.cs.severe.redress2.entity.CompilationUnit;
import edu.wayne.cs.severe.redress2.entity.TypeDeclaration;
import edu.wayne.cs.severe.redress2.exception.MetricException;

public class NOCMetricTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Test
	public void testComputeMetric() throws MetricException {

		String folder = "test_data/code/test_parsed/";
		List<TypeDeclaration> sysTypeDcls = new ArrayList<TypeDeclaration>();
		// ----------------------
		File compUnitFile = new File(folder + "Test4.java.xml");
		TypeDeclaration typeDcl = new TypeDeclaration("test.", "Class", true,
				new CompilationUnit(compUnitFile));

		sysTypeDcls.add(typeDcl);
		sysTypeDcls.add(new TypeDeclaration("", "Class2", true,
				new CompilationUnit(new File(folder + "Class2.java.xml"))));
		sysTypeDcls.add(new TypeDeclaration("", "Class3", false,
				new CompilationUnit(new File(folder + "Class3.java.xml"))));
		sysTypeDcls.add(new TypeDeclaration("", "Class4", true,
				new CompilationUnit(new File(folder + "Class4.java.xml"))));
		sysTypeDcls.add(new TypeDeclaration("", "Class20", false,
				new CompilationUnit(new File(folder + "Class20.java.xml"))));
		sysTypeDcls.add(new TypeDeclaration("", "Class21", false,
				new CompilationUnit(new File(folder + "Class21.java.xml"))));

		HierarchyBuilder builder = new HierarchyBuilder();
		builder.buildHierarchy(sysTypeDcls);

		// ----------------

		NOCMetric metric2 = new NOCMetric(sysTypeDcls, builder);
		double val = metric2.computeMetric(typeDcl);

		assertEquals(Double.valueOf(0.0), Double.valueOf(val));

		// ----------------

		typeDcl = new TypeDeclaration("", "Class21", false,
				new CompilationUnit(new File(folder + "Class21.java.xml")));
		compUnitFile = typeDcl.getCompUnit().getSrcFile();

		val = metric2.computeMetric(typeDcl);

		assertEquals(Double.valueOf(1.0), Double.valueOf(val));

		// ----------------

		typeDcl = new TypeDeclaration("", "Class20", false,
				new CompilationUnit(new File(folder + "Class20.java.xml")));
		compUnitFile = typeDcl.getCompUnit().getSrcFile();

		val = metric2.computeMetric(typeDcl);

		assertEquals(Double.valueOf(1.0), Double.valueOf(val));
	}

}
