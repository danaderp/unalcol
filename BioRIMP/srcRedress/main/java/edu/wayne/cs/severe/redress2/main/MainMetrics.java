package edu.wayne.cs.severe.redress2.main;

import org.apache.commons.cli.ParseException;

import edu.wayne.cs.severe.redress2.controller.processor.MetricProcessor;
import edu.wayne.cs.severe.redress2.utils.ArgsParser;

/**
 * Main class for the metric calculation program
 * 
 * @author ojcchar
 * @version 1.0
 * @created 23-Mar-2014 12:28:47
 */
public class MainMetrics {

	/**
	 * Main method
	 * 
	 * @param args
	 */
	public static void main(String[] args) {

		try {

			// parse the arguments
			ArgsParser parser = new ArgsParser();
			try {
				parser.processArgs(args);
			} catch (ParseException e) {
				System.out.println(e.getMessage());
				parser.printHelp();
				return;
			}

			// process the system
			MetricProcessor metricProcessor = new MetricProcessor(
					parser.getSysPath(), parser.getSysName(), parser.getLang(),
					parser.getParseCode());
			metricProcessor.processSytem();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}// end MainMetrics