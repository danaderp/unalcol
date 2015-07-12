package edu.wayne.cs.severe.redress2.io;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map.Entry;
import java.util.Set;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import edu.wayne.cs.severe.redress2.utils.TestUtils;

public class MetricsReaderTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Test
	public void testReadMetrics() throws IOException {
		MetricsReader reader = new MetricsReader(TestUtils.SYS_PATH_TEST4,
				TestUtils.SYS_NAME_TEST4);
		LinkedHashMap<String, LinkedHashMap<String, Double>> metrics = reader
				.readMetrics();

		assertEquals(1635, metrics.size());

		Set<Entry<String, LinkedHashMap<String, Double>>> entrySet = metrics
				.entrySet();
		for (Entry<String, LinkedHashMap<String, Double>> entry : entrySet) {
			assertNotNull(entry.getValue());
			assertEquals(11, entry.getValue().size());
		}
	}

}
