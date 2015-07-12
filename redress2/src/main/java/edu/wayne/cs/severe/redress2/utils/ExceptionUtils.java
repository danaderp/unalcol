package edu.wayne.cs.severe.redress2.utils;

public class ExceptionUtils {

	public static void addStackTrace(Exception sourceExc, Exception targetExc) {
		targetExc.setStackTrace(sourceExc.getStackTrace());
	}

}
