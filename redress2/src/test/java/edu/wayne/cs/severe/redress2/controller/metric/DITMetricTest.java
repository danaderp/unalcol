package edu.wayne.cs.severe.redress2.controller.metric;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import edu.wayne.cs.severe.redress2.controller.HierarchyBuilder;
import edu.wayne.cs.severe.redress2.entity.CompilationUnit;
import edu.wayne.cs.severe.redress2.entity.TypeDeclaration;
import edu.wayne.cs.severe.redress2.exception.MetricException;

public class DITMetricTest {

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

		HierarchyBuilder hBuilder = new HierarchyBuilder();
		hBuilder.buildHierarchy(sysTypeDcls);

		DITMetric metric = new DITMetric(sysTypeDcls, hBuilder);
		double val = metric.computeMetric(typeDcl);

		System.out.println(DITMetric.DITs);
		assertEquals(Double.valueOf(3.0), Double.valueOf(val));
	}

	@Test
	public void testComputeMetric2() throws MetricException {

		String folder = "test_data/code/test_parsed/";
		List<TypeDeclaration> sysTypeDcls = new ArrayList<TypeDeclaration>();
		// ----------------------
		File compUnitFile = new File(folder + "ProviderSqlSource.java.xml");
		TypeDeclaration typeDcl = new TypeDeclaration(
				"org.apache.ibatis.builder.annotation.", "ProviderSqlSource",
				false, new CompilationUnit(compUnitFile));

		sysTypeDcls.add(typeDcl);
		sysTypeDcls.add(new TypeDeclaration("org.apache.ibatis.mapping.",
				"SqlSource", false, new CompilationUnit(new File(folder
						+ "SqlSource.java.xml"))));

		HierarchyBuilder hBuilder = new HierarchyBuilder();
		hBuilder.buildHierarchy(sysTypeDcls);

		DITMetric metric = new DITMetric(sysTypeDcls, hBuilder);
		double val = metric.computeMetric(typeDcl);

		System.out.println(DITMetric.DITs);
		assertEquals(Double.valueOf(1.0), Double.valueOf(val));
	}

	@Test
	public void testComputeMetric3() throws MetricException {

		String folder = "test_data/code/test_parsed/";
		List<TypeDeclaration> sysTypeDcls = new ArrayList<TypeDeclaration>();
		// ----------------------
		File compUnitFile = new File(folder + "HashMap.java.xml");
		TypeDeclaration typeDcl = new TypeDeclaration("java.util.HashMap.",
				"Entry", true, new CompilationUnit(compUnitFile));
		sysTypeDcls.add(typeDcl);

		HierarchyBuilder hBuilder = new HierarchyBuilder();
		hBuilder.buildHierarchy(sysTypeDcls);

		// printHierarchy(hBuilder.getParentClasses());
		// printHierarchy(hBuilder.getChildClasses());

		DITMetric metric = new DITMetric(sysTypeDcls, hBuilder);
		double val = metric.computeMetric(typeDcl);

		System.out.println("printing dits");
		System.out.println(DITMetric.DITs);
		assertEquals(Double.valueOf(1.0), Double.valueOf(val));
	}

	public void printHierarchy(
			HashMap<String, List<TypeDeclaration>> parentClasses) {
		System.out.println("printing hierarchy");
		Set<Entry<String, List<TypeDeclaration>>> entrySet2 = parentClasses
				.entrySet();
		for (Entry<String, List<TypeDeclaration>> entry : entrySet2) {
			System.out.println(entry.getKey() + "=" + entry.getValue());
		}
	}
}
