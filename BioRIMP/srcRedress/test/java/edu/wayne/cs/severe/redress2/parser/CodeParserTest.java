package edu.wayne.cs.severe.redress2.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import edu.wayne.cs.severe.redress2.entity.CompilationUnit;
import edu.wayne.cs.severe.redress2.utils.TestUtils;

public class CodeParserTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Test
	public void testParseCodeInFolder() throws IOException {
		CodeParser parser = new CodeParser(TestUtils.SYS_NAME_TEST3,
				TestUtils.SYS_LANG_CS, true);
		try {
			List<CompilationUnit> compUnits = parser
					.parseCodeInFolder(TestUtils.SYS_PATH_TEST3);

			assertNotNull(compUnits);
			assertEquals(85, compUnits.size());

			for (CompilationUnit compilationUnit : compUnits) {
				assertTrue(compilationUnit.getSrcFile().exists());
			}
		} finally {
			FileUtils.deleteDirectory(parser.getXmlFolder());
		}
	}

}
