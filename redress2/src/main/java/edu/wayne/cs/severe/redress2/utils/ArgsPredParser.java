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

public class ArgsPredParser {

	// arguments to extract
	private String sysName;
	private File sysPath;
	private ProgLang lang;
	private File refsPath;

	// command line arguments
	private final String sPathArg = "p";
	private final String sysArg = "s";
	private final String langArg = "l";
	private final String rPathArg = "r";

	private Options options;

	@SuppressWarnings("static-access")
	public ArgsPredParser() {
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
		opt = OptionBuilder.withArgName("refspath").hasArg()
				.withDescription("Refactorings file path").create(rPathArg);
		options.addOption(opt);
	}

	public void processArgs(String[] args) throws ParseException {

		CommandLineParser parser = new PosixParser();

		try {

			// the options are parsed
			CommandLine parse = parser.parse(options, args);

			// are all the options provided
			boolean argsProvided = parse.hasOption(sPathArg)
					&& parse.hasOption(sysArg) && parse.hasOption(sysArg)
					&& parse.hasOption(rPathArg);

			if (!argsProvided) {
				throw new ParseException(
						"Please provide the arguments of the program");
			}

			String sPath = parse.getOptionValue(sPathArg);
			sysName = parse.getOptionValue(sysArg);
			String langName = parse.getOptionValue(langArg);
			String rPath = parse.getOptionValue(rPathArg);

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
				throw new ParseException("The prog. language is not supported");
			}

			// validation of sysPath
			refsPath = new File(rPath);
			if (!refsPath.exists() || !refsPath.isFile()) {
				throw new ParseException(
						"The path of the refactoring file is invalid or does not exist");
			}

		} catch (ParseException e) {
			throw e;
		}
	}

	public void printHelp() {
		HelpFormatter formatter = new HelpFormatter();
		formatter
				.printHelp(
						"metric-pred",
						"Predicts code metric changes for specified target system and refactorings.",
						options, null, true);
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

	public File getRefsPath() {
		return refsPath;
	}

}
