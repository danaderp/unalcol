/**
 * 
 */
package edu.wayne.cs.severe.redress2.utils;

import static org.junit.Assert.assertEquals;

import org.apache.commons.cli.ParseException;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import edu.wayne.cs.severe.redress2.entity.ProgLang;

/**
 * @author ojcchar
 * 
 */
public class ArgsParserTest {

	private static ArgsParser parser;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		parser = new ArgsParser();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	/**
	 * Test method for
	 * {@link edu.wayne.cs.severe.redress2.utils.ArgsParser#processArgs(java.lang.String[])}
	 * .
	 * 
	 * @throws ParseException
	 */
	@Test(expected = ParseException.class)
	public void testProcessArgs() throws ParseException {
		String[] args = null;
		parser.processArgs(args);
	}

	/**
	 * Test method for
	 * {@link edu.wayne.cs.severe.redress2.utils.ArgsParser#processArgs(java.lang.String[])}
	 * .
	 * 
	 * @throws ParseException
	 */
	@Test(expected = ParseException.class)
	public void testProcessArgs2() throws ParseException {
		String[] args = {};
		parser.processArgs(args);
	}

	/**
	 * Test method for
	 * {@link edu.wayne.cs.severe.redress2.utils.ArgsParser#processArgs(java.lang.String[])}
	 * .
	 * 
	 * @throws ParseException
	 */
	@Test(expected = ParseException.class)
	public void testProcessArgs3() throws ParseException {
		String[] args = { "-l", "Java", "-p", "", "-s", "systest" };
		parser.processArgs(args);
	}

	/**
	 * Test method for
	 * {@link edu.wayne.cs.severe.redress2.utils.ArgsParser#processArgs(java.lang.String[])}
	 * .
	 * 
	 * @throws ParseException
	 */
	@Test(expected = ParseException.class)
	public void testProcessArgs4() throws ParseException {

		String[] args = { "-l", "Java", "-p", "nosense path", "-s", "systest" };
		parser.processArgs(args);
	}

	/**
	 * Test method for
	 * {@link edu.wayne.cs.severe.redress2.utils.ArgsParser#processArgs(java.lang.String[])}
	 * .
	 * 
	 * @throws ParseException
	 */
	@Test(expected = ParseException.class)
	public void testProcessArgs5() throws ParseException {
		String userPath = System.getProperty("user.dir");
		String[] args = { "-l", "", "-p", userPath, "-s", "systest" };
		parser.processArgs(args);

		String[] args2 = { "-l", "Java2", "-p", userPath, "-s", "systest" };
		parser.processArgs(args2);
	}

	/**
	 * Test method for
	 * {@link edu.wayne.cs.severe.redress2.utils.ArgsParser#processArgs(java.lang.String[])}
	 * .
	 * 
	 * @throws ParseException
	 */
	@Test(expected = ParseException.class)
	public void testProcessArgs6() throws ParseException {
		String userPath = System.getProperty("user.dir");
		String[] args = { "-l", "Java2", "-p", userPath, "-s", "systest" };
		parser.processArgs(args);
	}

	/**
	 * Test method for
	 * {@link edu.wayne.cs.severe.redress2.utils.ArgsParser#processArgs(java.lang.String[])}
	 * .
	 * 
	 * @throws ParseException
	 */
	@Test(expected = ParseException.class)
	public void testProcessArgs7() throws ParseException {
		String userPath = System.getProperty("user.dir");
		String[] args = { "-l", "Java", "-p", userPath, "-s", "" };
		parser.processArgs(args);
	}

	/**
	 * Test method for
	 * {@link edu.wayne.cs.severe.redress2.utils.ArgsParser#processArgs(java.lang.String[])}
	 * .
	 * 
	 * @throws ParseException
	 */
	@Test(expected = ParseException.class)
	public void testProcessArgs8() throws ParseException {
		String userPath = System.getProperty("user.dir");
		String[] args = { "-l", "Java", "-p", userPath, "-s", "           " };
		parser.processArgs(args);
	}

	/**
	 * Test method for
	 * {@link edu.wayne.cs.severe.redress2.utils.ArgsParser#processArgs(java.lang.String[])}
	 * .
	 * 
	 * @throws ParseException
	 */
	@Test
	public void testProcessArgs9() throws ParseException {
		String userPath = System.getProperty("user.dir");
		String[] args = { "-l", "C#", "-p", userPath, "-s", "     TEST      " };
		parser.processArgs(args);

		assertEquals(ProgLang.CS, parser.getLang());
		assertEquals(userPath, parser.getSysPath().getAbsolutePath());
		assertEquals("TEST", parser.getSysName());

	}

	/**
	 * Test method for
	 * {@link edu.wayne.cs.severe.redress2.utils.ArgsParser#printHelp()}.
	 */
	@Test
	public void testPrintHelp() {
		parser.printHelp();
		parser.printHelp();
	}

}
