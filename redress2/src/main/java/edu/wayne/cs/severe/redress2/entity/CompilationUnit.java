package edu.wayne.cs.severe.redress2.entity;

import java.io.File;

/**
 * @author ojcchar
 * @version 1.0
 * @created 23-Mar-2014 12:28:47
 */
public class CompilationUnit {

	private File srcFile;

	public CompilationUnit(File srcFile) {
		this.srcFile = srcFile;
	}

	public File getSrcFile() {
		return srcFile;
	}

	public void setSrcFile(File srcFile) {
		this.srcFile = srcFile;
	}

}// end CompilationUnit