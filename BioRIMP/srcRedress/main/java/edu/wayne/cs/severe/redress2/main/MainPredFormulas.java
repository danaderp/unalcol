package edu.wayne.cs.severe.redress2.main;

import org.apache.commons.cli.ParseException;

import edu.wayne.cs.severe.redress2.controller.processor.PredFormulasProcessor;
import edu.wayne.cs.severe.redress2.utils.ArgsPredParser;

/**
 * @author ojcchar
 * @version 1.0
 * @created 23-Mar-2014 12:28:47
 */
public class MainPredFormulas {

	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) {

		try {

			// parse the arguments
			ArgsPredParser parser = new ArgsPredParser();
			try {
				parser.processArgs(args);
			} catch (ParseException e) {
				System.out.println(e.getMessage());
				parser.printHelp();
				return;
			}

			// process the system
			PredFormulasProcessor processor = new PredFormulasProcessor(
					parser.getSysPath(), parser.getSysName(), parser.getLang(),
					parser.getRefsPath());
			processor.processSytem();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}// end MainPredFormulas