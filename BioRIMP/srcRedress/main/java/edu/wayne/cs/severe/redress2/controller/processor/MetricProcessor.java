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
import edu.wayne.cs.severe.redress2.exception.CompilUnitException;
import edu.wayne.cs.severe.redress2.exception.WritingException;
import edu.wayne.cs.severe.redress2.io.MetricsWriter;
import edu.wayne.cs.severe.redress2.parser.CodeParser;
import edu.wayne.cs.severe.redress2.parser.CompilUnitParser;
import edu.wayne.cs.severe.redress2.parser.CompilUnitParserFactory;

/**
 * Class that controls all the process of metrics calculation
 * 
 * @author ojcchar
 * @version 1.0
 * @created 23-Mar-2014 12:28:47
 */
public class MetricProcessor {

	// parameters of the system
	private File systemPath;
	private String sysName;
	private ProgLang lang;

	// xml folder where the srcML files are stored
	private File xmlFolder;
	private boolean parseSrcML;

	// logger
	private static Logger LOGGER = LoggerFactory
			.getLogger(MetricProcessor.class);

	/**
	 * Default constructor
	 * 
	 * @param sysName
	 *            the name of the system
	 * @param lang
	 *            the programming language in which the system is implemented
	 * @param parseSrcML
	 */
	public MetricProcessor(File systemPath, String sysName, ProgLang lang,
			boolean parseSrcML) {
		this.systemPath = systemPath;
		this.sysName = sysName;
		this.lang = lang;
		this.parseSrcML = parseSrcML;

	}

	/**
	 * Method that process the system
	 * 
	 * @return the output file with the metric values per class
	 * @throws WritingException
	 *             if the file could not be saved
	 * @throws CompilUnitException
	 *             if there is an error parsing the code
	 * @throws IOException
	 */
	public File processSytem() throws WritingException, CompilUnitException,
			IOException {

		LOGGER.debug("Processing the system \"" + sysName + "\"");

		LOGGER.debug("Parsing the code");

		// parse the code and get the compilation units
		CodeParser parser = new CodeParser(sysName, lang, parseSrcML);
		List<CompilationUnit> compUnits = parser.parseCodeInFolder(systemPath);
		xmlFolder = parser.getXmlFolder();

		// parse the XML files of the code
		CompilUnitParser cUParser = CompilUnitParserFactory
				.getCompilUnitParser(lang);
		List<TypeDeclaration> sysTypeDcls = cUParser.getClasses(compUnits);

		LOGGER.debug("Building class hierarchy");

		HierarchyBuilder builder = new HierarchyBuilder();
		builder.buildHierarchy(sysTypeDcls);

		LOGGER.debug("Calculating metrics");

		// list of metrics
		ArrayList<CodeMetric> metrics = new ArrayList<CodeMetric>();
		metrics.add(new LOCMetric(sysTypeDcls, builder));
		metrics.add(new NOMMetric(sysTypeDcls, builder));
		metrics.add(new RFCMetric(sysTypeDcls, builder));
		metrics.add(new CBOMetric(sysTypeDcls, builder));
		metrics.add(new DITMetric(sysTypeDcls, builder));
		metrics.add(new MPCMetric(sysTypeDcls, builder));
		metrics.add(new LCOM5Metric(sysTypeDcls, builder));
		metrics.add(new LCOM2Metric(sysTypeDcls, builder));
		metrics.add(new NOCMetric(sysTypeDcls, builder));
		metrics.add(new CYCLOMetric(sysTypeDcls, builder));
		metrics.add(new DACMetric(sysTypeDcls, builder));

		// calculate the metrics
		MetricCalculator calc = new MetricCalculator();
		LinkedHashMap<String, LinkedHashMap<String, Double>> metrs = calc
				.computeMetrics(sysTypeDcls, metrics);

		LOGGER.debug("Saving results");

		// save a file with the metric values per class
		MetricsWriter writer = new MetricsWriter(sysName, systemPath);
		File metrFile = writer.saveMetrics(metrs, metrics);

		LOGGER.debug("Done! Metrics file: " + metrFile.getAbsolutePath());

		return metrFile;

	}

	/**
	 * @return the xmlFolder
	 */
	public File getXmlFolder() {
		return xmlFolder;
	}

}// end MetricProcessor