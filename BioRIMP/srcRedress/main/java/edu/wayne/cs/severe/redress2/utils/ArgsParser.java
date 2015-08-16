package edu.wayne.cs.severe.redress2.utils;

import java.io.File;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.PosixParser;

import edu.wayne.cs.severe.redress2.entity.ProgLang;

/**
 * Arguments parser for the metrics calculation program
 * 
 * @author ojcchar
 * 
 */
public class ArgsParser {

	// arguments to extract
	private String sysName;
	private File sysPath;
	private ProgLang lang;
	private boolean parseCode = true;

	// command line arguments
	private final String sPathArg = "p";
	private final String sysArg = "s";
	private final String langArg = "l";
	private final String readArg = "r";

	private Options options;

	@SuppressWarnings("static-access")
	public ArgsParser() {
		options = new Options();
		Option opt = OptionBuilder.withArgName("syspath").hasArg()
				.withDescription("Target system path").create(sPathArg);
		options.addOption(opt);
		opt = OptionBuilder.withArgName("sysname").hasArg()
				.withDescription("Target system name").create(sysArg);
		options.addOption(opt);
		opt = OptionBuilder.withArgName("lang").hasArg()
				.withDescription("Target system prog. language (Java or C#)")
				.create(langArg);
		options.addOption(opt);
		options.addOption(readArg, false,
				"Read the parsed source code instead of reprocess it again");
	}

	/**
	 * Process the arguments of the program
	 * 
	 * @param args
	 */
	public void processArgs(String[] args) throws ParseException {

		CommandLineParser parser = new PosixParser();

		try {

			// the options are parsed
			CommandLine parse = parser.parse(options, args);

			// are all the options provided
			boolean argsProvided = !(parse.hasOption(sPathArg)
					&& parse.hasOption(sysArg) && parse.hasOption(sysArg));

			if (!argsProvided) {

				String sPath = parse.getOptionValue(sPathArg);
				sysName = parse.getOptionValue(sysArg);
				String langName = parse.getOptionValue(langArg);

				// validation of sysPath
				sysPath = new File(sPath);
				if (!sysPath.exists() || !sysPath.isDirectory()) {
					throw new ParseException(
							"The path of the target system is invalid or does not exist");
				}

				// validation of sysName
				if (sysName == null || sysName.trim().isEmpty()) {
					throw new ParseException("The system name is invalid");
				}
				sysName = sysName.trim();

				// validation of langName
				if (langName == null || langName.trim().isEmpty()) {
					throw new ParseException("The prog. language is invalid");
				}

				// assign the language
				if (GeneralConstants.JAVA.equalsIgnoreCase(langName)) {
					lang = ProgLang.JAVA;
				} else if (GeneralConstants.CS.equalsIgnoreCase(langName)) {
					lang = ProgLang.CS;
				} else {
					throw new ParseException(
							"The prog. language is not supported");
				}

				if (parse.hasOption(readArg)) {
					this.parseCode = false;
				}

			} else {
				throw new ParseException(
						"Please provide the arguments of the program");
			}

		} catch (ParseException e) {
			throw e;
		}
	}

	public void printHelp() {
		HelpFormatter formatter = new HelpFormatter();
		formatter.printHelp("metric-calc",
				"Calculates code metric for specified target system.", options,
				null, true);
	}

	public String getSysName() {
		return sysName;
	}

	public File getSysPath() {
		return sysPath;
	}

	public ProgLang getLang() {
		return lang;
	}

	public boolean getParseCode() {
		return parseCode;
	}

}
