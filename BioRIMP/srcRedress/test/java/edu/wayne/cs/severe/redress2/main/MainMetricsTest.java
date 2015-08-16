package edu.wayne.cs.severe.redress2.main;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class MainMetricsTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Test
	public void testMain() {
		String userPath = System.getProperty("user.dir");
		String[] args = { "-l", "C#", "-p", userPath, "-s", "     TEST      " };
		MainMetrics.main(args);
	}

	@Test
	public void testMain2() {
		String userPath = System.getProperty("user.dir");
		String[] args = { "-l", "", "-p", userPath, "-s", "     TEST      " };
		MainMetrics.main(args);
	}

	@Test
	public void testMain3() {
		String[] args = { "-l", "C#", "-p", "", "-s", "     TEST      " };
		MainMetrics.main(args);
	}

	@Test
	public void testMain4() {
		String userPath = System.getProperty("user.dir");
		String[] args = { "-l", "C#", "-p", userPath, "-s", "           " };
		MainMetrics.main(args);
	}

	@Test
	public void testMain5() {
		String[] args = {};
		MainMetrics.main(args);
	}

}
