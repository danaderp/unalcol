package edu.wayne.cs.severe.redress2.controller.processor;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.wayne.cs.severe.redress2.controller.HierarchyBuilder;
import edu.wayne.cs.severe.redress2.controller.MetricCalculator;
import edu.wayne.cs.severe.redress2.controller.metric.CBOMetric;
import edu.wayne.cs.severe.redress2.controller.metric.CYCLOMetric;
import edu.wayne.cs.severe.redress2.controller.metric.CodeMetric;
import edu.wayne.cs.severe.redress2.controller.metric.DACMetric;
import edu.wayne.cs.severe.redress2.controller.metric.DITMetric;
import edu.wayne.cs.severe.redress2.controller.metric.LCOM2Metric;
import edu.wayne.cs.severe.redress2.controller.metric.LCOM5Metric;
import edu.wayne.cs.severe.redress2.controller.metric.LOCMetric;
import edu.wayne.cs.severe.redress2.controller.metric.MPCMetric;
import edu.wayne.cs.severe.redress2.controller.metric.NOCMetric;
import edu.wayne.cs.severe.redress2.controller.metric.NOMMetric;
import edu.wayne.cs.severe.redress2.controller.metric.RFCMetric;
import edu.wayne.cs.severe.redress2.entity.CompilationUnit;
import edu.wayne.cs.severe.redress2.entity.ProgLang;
import edu.wayne.cs.severe.redress2.entity.TypeDeclaration;
import edu.wayne.cs.severe.redress2.entity.refactoring.RefactoringOperation;
import edu.wayne.cs.severe.redress2.exception.CompilUnitException;
import edu.wayne.cs.severe.redress2.exception.ReadException;
import edu.wayne.cs.severe.redress2.exception.WritingException;
import edu.wayne.cs.severe.redress2.io.MetricsReader;
import edu.wayne.cs.severe.redress2.io.MetricsWriter;
import edu.wayne.cs.severe.redress2.io.RefactoringReader;
import edu.wayne.cs.severe.redress2.parser.CodeParser;
import edu.wayne.cs.severe.redress2.parser.CompilUnitParser;
import edu.wayne.cs.severe.redress2.parser.CompilUnitParserFactory;

/**
 * @author ojcchar
 * @version 1.0
 * @created 23-Mar-2014 12:28:47
 */
public class PredFormulasProcessor {

	// parameters of the system
	private File systemPath;
	private String sysName;
	private ProgLang lang;
	private File refsFile;

	// logger
	private static Logger LOGGER = LoggerFactory
			.getLogger(MetricProcessor.class);

	public PredFormulasProcessor(File systemPath, String sysName,
			ProgLang lang, File refsPath) {
		this.systemPath = systemPath;
		this.sysName = sysName;
		this.lang = lang;
		this.refsFile = refsPath;
	}

	public File processSytem() throws ReadException, IOException,
			CompilUnitException, WritingException {

		LOGGER.debug("Processing the system \"" + sysName + "\"");

		// parse the code and get the compilation units
		CodeParser parser = new CodeParser(sysName, lang, false);
		List<CompilationUnit> compUnits = parser.parseCodeInFolder(systemPath);

		// parse the XML files of the code
		CompilUnitParser cUParser = CompilUnitParserFactory
				.getCompilUnitParser(lang);
		List<TypeDeclaration> sysTypeDcls = cUParser.getClasses(compUnits);

		LOGGER.debug("Building class hierarchy");

		HierarchyBuilder builder = new HierarchyBuilder();
		builder.buildHierarchy(sysTypeDcls);

		LOGGER.debug("Reading refactorings");

		RefactoringReader refReader = new RefactoringReader(sysTypeDcls, lang,
				builder);
		List<RefactoringOperation> operations = refReader
				.getRefactOperations(refsFile);

		LOGGER.debug("Reading previous metrics");

		MetricsReader metReader = new MetricsReader(systemPath, sysName);
		LinkedHashMap<String, LinkedHashMap<String, Double>> prevMetrics = metReader
				.readMetrics();

		// list of metrics
		ArrayList<CodeMetric> metrics = new ArrayList<CodeMetric>();
		metrics.add(new LOCMetric());
		metrics.add(new NOMMetric());
		metrics.add(new RFCMetric());
		metrics.add(new CBOMetric());
		metrics.add(new DITMetric());
		metrics.add(new MPCMetric());
		metrics.add(new LCOM5Metric());
		metrics.add(new LCOM2Metric());
		metrics.add(new NOCMetric());
		metrics.add(new CYCLOMetric());
		metrics.add(new DACMetric());

		LOGGER.debug("Predicting metrics");

		MetricCalculator calc = new MetricCalculator();
		LinkedHashMap<String, LinkedHashMap<String, LinkedHashMap<String, Double>>> predictMetrics = calc
				.predictMetrics(operations, metrics, prevMetrics);

		LOGGER.debug("Writing predicted metrics");

		// save a file with the metric values per class
		MetricsWriter writer = new MetricsWriter(sysName, systemPath);
		File metrFile = writer.savePredictedMetrics(predictMetrics, metrics);

		LOGGER.debug("Done! Predicted metrics file: "
				+ metrFile.getAbsolutePath());

		return metrFile;

	}
}// end PredFormulasProcessor