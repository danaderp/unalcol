package edu.wayne.cs.severe.redress2.controller.processor;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import edu.wayne.cs.severe.redress2.exception.CompilUnitException;
import edu.wayne.cs.severe.redress2.exception.WritingException;
import edu.wayne.cs.severe.redress2.utils.TestUtils;

public class MetricProcessorTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Test
	public void testProcessSytemJava() throws WritingException, IOException,
			CompilUnitException {
		MetricProcessor processor = null;
		File fileMetrics = null;
		try {
			processor = new MetricProcessor(TestUtils.SYS_PATH_TEST,
					TestUtils.SYS_NAME_TEST, TestUtils.SYS_LANG_JAVA, true);

			fileMetrics = processor.processSytem();

			assertNotNull(fileMetrics);
			assertTrue(fileMetrics.exists());
		} finally {
			deleteFiles(processor, fileMetrics);
		}

	}

	private void deleteFiles(MetricProcessor processor, File fileMetrics)
			throws IOException {
		if (fileMetrics != null) {
			FileUtils.deleteDirectory(fileMetrics.getParentFile());
		}
		if (processor != null) {
			FileUtils.deleteDirectory(processor.getXmlFolder());
		}
	}

	@Test
	public void testProcessSytemJava2() throws WritingException, IOException,
			CompilUnitException {
		MetricProcessor processor = null;
		File fileMetrics = null;
		try {
			processor = new MetricProcessor(TestUtils.SYS_PATH_TEST2,
					TestUtils.SYS_NAME_TEST2, TestUtils.SYS_LANG_JAVA, true);

			fileMetrics = processor.processSytem();

			assertNotNull(fileMetrics);
			assertTrue(fileMetrics.exists());
		} finally {
			deleteFiles(processor, fileMetrics);
		}

	}

	@Test
	public void testProcessSytemCS() throws WritingException, IOException,
			CompilUnitException {
		MetricProcessor processor = null;
		File fileMetrics = null;
		try {
			processor = new MetricProcessor(TestUtils.SYS_PATH_TEST3,
					TestUtils.SYS_NAME_TEST3, TestUtils.SYS_LANG_CS, true);

			fileMetrics = processor.processSytem();

			assertNotNull(fileMetrics);
			assertTrue(fileMetrics.exists());
		} finally {
			deleteFiles(processor, fileMetrics);
		}

	}

}
