package edu.wayne.cs.severe.redress2.controller.processor;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.wayne.cs.severe.redress2.controller.HierarchyBuilder;
import edu.wayne.cs.severe.redress2.controller.MetaphorBuilder;
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
 * @author danaderp
 * @version 1.0
 * @created 03-Agosto-2015 12:28:47
 */
public class PredFormulasProcessorBIoRIPM {

	// parameters of the system
	private File systemPath;
	private String sysName;
	private ProgLang lang;
	private HierarchyBuilder builder;
	List<TypeDeclaration> sysTypeDcls;
	


	// logger
	private static Logger LOGGER = LoggerFactory
			.getLogger(MetricProcessor.class);

	public PredFormulasProcessorBIoRIPM(File systemPath, String sysName,
			ProgLang lang) throws ReadException, IOException, CompilUnitException, WritingException {
		
		this.systemPath = systemPath;
		this.sysName = sysName;
		this.lang = lang;
		processSytem();

	}
	
	//Method for reading refactors from a BITARRAY 
	//FIXME Refactors should be parameters not GSON!!
	/*
	public List<RefactoringOperation> ReadRefactors() throws ReadException, IOException,
	CompilUnitException, WritingException {
		
		LOGGER.debug("Reading refactorings");

		RefactoringReader refReader = new RefactoringReader(sysTypeDcls, lang,
				builder);
		List<RefactoringOperation> operations = refReader
				.getRefactOperations(refsFile);
		return operations;
	}*/
	
	//Method for Init the Metaphor Construction
	
	public MetaphorBuilder InitMetaphorBuilder(){
		LOGGER.debug("Representing SUA in a Metaphor");
		MetaphorBuilder metaphor= new MetaphorBuilder();
		metaphor.buildHierarchy(sysTypeDcls);
		return metaphor;
	}
	
	
	//Method for predicting the metrics according to the list of refactor
	//operations
	public LinkedHashMap<String, LinkedHashMap<String, LinkedHashMap<String, Double>>> 
					PredictingMetrics(List<RefactoringOperation> operations)
							throws ReadException, IOException,
							CompilUnitException, WritingException{
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
		
		return predictMetrics;
		
	}
	
	//Method for processing the System -> Generating the parse and initial
	//metrics of the system
	//constructs the Hierarchy Builder
	private void processSytem() throws ReadException, IOException,
			CompilUnitException, WritingException {

		LOGGER.debug("Processing the system \"" + sysName + "\"");

		// parse the code and get the compilation units
		CodeParser parser = new CodeParser(sysName, lang, false);
		List<CompilationUnit> compUnits = parser.parseCodeInFolder(systemPath);

		// parse the XML files of the code
		CompilUnitParser cUParser = CompilUnitParserFactory
				.getCompilUnitParser(lang);
		sysTypeDcls = cUParser.getClasses(compUnits);

		LOGGER.debug("Building class hierarchy");
		
		builder = new HierarchyBuilder();
		builder.buildHierarchy(sysTypeDcls);		
		/*
		//
		LOGGER.debug("Writing predicted metrics");
		// save a file with the metric values per class
		MetricsWriter writer = new MetricsWriter(sysName, systemPath);
		File metrFile = writer.savePredictedMetrics(predictMetrics, metrics);

		LOGGER.debug("Done! Predicted metrics file: "
				+ metrFile.getAbsolutePath());

		return metrFile;*/

	}

	public HierarchyBuilder getBuilder() {
		return builder;
	}

	public List<TypeDeclaration> getSysTypeDcls() {
		return sysTypeDcls;
	}


	
	
}// end PredFormulasProcessor