package edu.wayne.cs.severe.redress2.io;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import edu.wayne.cs.severe.redress2.controller.HierarchyBuilder;
import edu.wayne.cs.severe.redress2.entity.CompilationUnit;
import edu.wayne.cs.severe.redress2.entity.ProgLang;
import edu.wayne.cs.severe.redress2.entity.TypeDeclaration;
import edu.wayne.cs.severe.redress2.entity.refactoring.RefactoringOperation;
import edu.wayne.cs.severe.redress2.parser.CodeParser;
import edu.wayne.cs.severe.redress2.parser.CompilUnitParser;
import edu.wayne.cs.severe.redress2.parser.CompilUnitParserFactory;
import edu.wayne.cs.severe.redress2.utils.TestUtils;

public class RefactoringReaderTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Test
	public void testGetRefactOperations2() throws Exception {

		ProgLang lang = TestUtils.SYS_LANG_JAVA;
		String sysName = TestUtils.SYS_NAME_TEST4;
		File systemPath = TestUtils.SYS_PATH_TEST4;
		boolean parseSrcML = false;

		// parse the code and get the compilation units
		CodeParser parser = new CodeParser(sysName, lang, parseSrcML);
		List<CompilationUnit> compUnits = parser.parseCodeInFolder(systemPath);

		// parse the XML files of the code
		CompilUnitParser cUParser = CompilUnitParserFactory
				.getCompilUnitParser(lang);
		List<TypeDeclaration> sysTypeDcls = cUParser.getClasses(compUnits);

		HierarchyBuilder builder = new HierarchyBuilder();
		builder.buildHierarchy(sysTypeDcls);

		RefactoringReader reader = new RefactoringReader(sysTypeDcls, lang, builder);
		File refFile = TestUtils.REFS_FILE_PUF1;
		List<RefactoringOperation> operations = reader
				.getRefactOperations(refFile);

		assertNotNull(operations);
		String[] expec = { "pullUpField" };
		assertEquals(expec.length, operations.size());

		for (int i = 0; i < expec.length; i++) {
			// String exp = expec[i];
			RefactoringOperation operation = operations.get(i);
			assertNotNull(operation);
			assertNotNull(operation.getRefType());
			assertNotNull(operation.getParams());

			assertEquals(3, operation.getParams().size());
		}

	}

}
