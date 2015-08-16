package edu.wayne.cs.severe.redress2.controller.metric;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import edu.wayne.cs.severe.redress2.controller.HierarchyBuilder;
import edu.wayne.cs.severe.redress2.entity.CompilationUnit;
import edu.wayne.cs.severe.redress2.entity.TypeDeclaration;
import edu.wayne.cs.severe.redress2.exception.MetricException;

public class CBOMetricTest {

	@Test
	public void testComputeMetric() throws MetricException {

		File compUnitFile = new File(
				"test_data/code/test_parsed/Test3.java.xml");
		TypeDeclaration typeDcl = new TypeDeclaration("test", "Class", true,
				new CompilationUnit(compUnitFile));

		List<TypeDeclaration> sysTypeDcls = new ArrayList<TypeDeclaration>();
		sysTypeDcls.add(typeDcl);

		HierarchyBuilder hBuilder = new HierarchyBuilder();
		hBuilder.buildHierarchy(sysTypeDcls);

		CBOMetric metric = new CBOMetric(sysTypeDcls, hBuilder);

		double val = metric.computeMetric(typeDcl);
		assertEquals(Double.valueOf(15.0), Double.valueOf(val));
	}

	@Test
	public void testComputeMetric2() throws MetricException {

		TypeDeclaration typeDcl = new TypeDeclaration("cbo.", "ClassCBO",
				false, new CompilationUnit(new File(
						"test_data/code/test_parsed/ClassCBO.java.xml")));

		List<TypeDeclaration> sysTypeDcls = new ArrayList<TypeDeclaration>();
		sysTypeDcls.add(typeDcl);
		sysTypeDcls.add(new TypeDeclaration("cbo.", "SuperClassCBO", false,
				new CompilationUnit(new File(
						"test_data/code/test_parsed/SuperClassCBO.java.xml"))));

		HierarchyBuilder hBuilder = new HierarchyBuilder();
		hBuilder.buildHierarchy(sysTypeDcls);

		CBOMetric metric = new CBOMetric(sysTypeDcls, hBuilder);

		double val = metric.computeMetric(typeDcl);
		assertEquals(Double.valueOf(13.0), Double.valueOf(val));
	}

}
