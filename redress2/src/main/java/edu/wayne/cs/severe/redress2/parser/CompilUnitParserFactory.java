package edu.wayne.cs.severe.redress2.parser;

import edu.wayne.cs.severe.redress2.entity.ProgLang;

/**
 * Factory for compilation unit parsers
 * 
 * @author ojcchar
 * 
 */
public class CompilUnitParserFactory {

	/**
	 * Create a new parser based on lang
	 * 
	 * @param lang
	 *            the programming language
	 * @return the compilation unit parser for this language
	 */
	public static CompilUnitParser getCompilUnitParser(ProgLang lang) {
		if (ProgLang.JAVA == lang) {
			return new JavaCompilUnitParser();
		} else if (ProgLang.CS == lang) {
			return new CsCompilUnitParser();
		}
		throw new RuntimeException("No language supported: " + lang.toString());
	}

}
