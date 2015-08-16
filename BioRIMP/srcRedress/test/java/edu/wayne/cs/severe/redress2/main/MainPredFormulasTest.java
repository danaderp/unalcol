package edu.wayne.cs.severe.redress2.main;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class MainPredFormulasTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Test
	public void testMain() {
		String userPath = System.getProperty("user.dir");
		String[] args = { "-l", "C#", "-p", userPath, "-s", "     TEST      ",
				"-r", userPath };
		MainPredFormulas.main(args);
	}

	@Test
	public void testMain2() {
		String userPath = System.getProperty("user.dir");
		String[] args = { "-l", "", "-p", userPath, "-s", "     TEST      ",
				"-r", userPath };
		MainPredFormulas.main(args);
	}

	@Test
	public void testMain3() {
		String userPath = System.getProperty("user.dir");
		String[] args = { "-l", "C#", "-p", "", "-s", "     TEST      ", "-r",
				userPath };
		MainPredFormulas.main(args);
	}

	@Test
	public void testMain4() {
		String userPath = System.getProperty("user.dir");
		String[] args = { "-l", "C#", "-p", userPath, "-s", "           ",
				"-r", userPath };
		MainPredFormulas.main(args);
	}

	@Test
	public void testMain5() {
		String[] args = {};
		MainPredFormulas.main(args);
	}

	@Test
	public void testMain6() {
		String userPath = System.getProperty("user.dir");
		String[] args = { "-l", "C#", "-p", userPath, "-s", "     TEST      ",
				"-r", "" };
		MainPredFormulas.main(args);
	}

}
