package edu.wayne.cs.severe.redress2.io;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import edu.wayne.cs.severe.redress2.controller.metric.CodeMetric;
import edu.wayne.cs.severe.redress2.exception.WritingException;
import edu.wayne.cs.severe.redress2.utils.ExceptionUtils;

/**
 * Writes the list of metric values of classes in a CSV file
 * 
 * @author ojcchar
 * @version 1.0
 * @created 23-Mar-2014 12:28:47
 */
public class MetricsWriter {

	// constants
	private static final String COL_SEPARATOR = ",";
	public static final String METRIC_VALS_FOLDER = "metric_vals";
	public static final String CSV_EXT = ".csv";
	public static final String FILE_PREFIX = "METRICS_";
	private static final String FILE_PREFIX2 = "PRED_METRICS_";

	// target system parameters
	private File systemPath;
	private String sysName;

	/**
	 * Default constructor
	 * 
	 * @param sysName
	 * @param systemPath
	 */
	public MetricsWriter(String sysName, File systemPath) {
		this.systemPath = systemPath;
		this.sysName = sysName;
	}

	/**
	 * 
	 * 
	 * @param metricValues
	 * @param metrics
	 * @throws WritingException
	 */
	public File saveMetrics(
			LinkedHashMap<String, LinkedHashMap<String, Double>> metricValues,
			List<CodeMetric> metrics) throws WritingException {

		File file = createOutputFile();

		FileWriter writer = null;

		try {

			writer = new FileWriter(file);

			// write the headers
			writer.write("Class");
			for (CodeMetric codeMetric : metrics) {
				writer.write("," + codeMetric.getMetricAcronym());
			}
			writer.write("\n");

			ArrayList<Entry<String, LinkedHashMap<String, Double>>> list = new ArrayList<Entry<String, LinkedHashMap<String, Double>>>(
					metricValues.entrySet());
			for (Entry<String, LinkedHashMap<String, Double>> entry : list) {

				// write the class
				writer.write(entry.getKey());
				writer.write(COL_SEPARATOR);

				// write the metric values for the class
				Set<Entry<String, Double>> entrySet2 = entry.getValue()
						.entrySet();
				for (Entry<String, Double> entry2 : entrySet2) {
					writer.write(entry2.getValue().toString());
					writer.write(COL_SEPARATOR);
				}
				writer.write("\n");
			}

		} catch (Exception e) {
			WritingException e2 = new WritingException(e.getMessage());
			ExceptionUtils.addStackTrace(e, e2);
			throw e2;
		} finally {
			if (writer != null) {
				try {
					writer.close();
				} catch (IOException e) {
					throw new WritingException(e.getMessage());
				}
			}
		}

		return file;
	}

	/**
	 * Creates the CSV output file
	 * 
	 * @return
	 */
	private File createOutputFile() {
		File outFolder = new File(systemPath.getAbsolutePath() + File.separator
				+ METRIC_VALS_FOLDER);
		outFolder.mkdir();
		File file = new File(outFolder.getAbsolutePath() + File.separator
				+ FILE_PREFIX + sysName + CSV_EXT);
		return file;
	}

	/**
	 * Creates the CSV output file
	 * 
	 * @return
	 */
	private File createOutputFilePredicted() {
		File outFolder = new File(systemPath.getAbsolutePath() + File.separator
				+ METRIC_VALS_FOLDER);
		outFolder.mkdir();
		File file = new File(outFolder.getAbsolutePath() + File.separator
				+ FILE_PREFIX2 + sysName + CSV_EXT);
		return file;
	}

	public File savePredictedMetrics(
			LinkedHashMap<String, LinkedHashMap<String, LinkedHashMap<String, Double>>> predictMetrics,
			ArrayList<CodeMetric> metrics) throws WritingException {
		File file = createOutputFilePredicted();

		FileWriter writer = null;

		try {

			writer = new FileWriter(file);

			// write the headers
			writer.write("Refactoring,Class");
			for (CodeMetric codeMetric : metrics) {
				writer.write("," + codeMetric.getMetricAcronym());
			}
			writer.write("\n");

			Set<Entry<String, LinkedHashMap<String, LinkedHashMap<String, Double>>>> entrySet = predictMetrics
					.entrySet();

			for (Entry<String, LinkedHashMap<String, LinkedHashMap<String, Double>>> entry : entrySet) {

				Set<Entry<String, LinkedHashMap<String, Double>>> entrySet3 = entry
						.getValue().entrySet();
				for (Entry<String, LinkedHashMap<String, Double>> entry2 : entrySet3) {

					// write the refactoring type
					writer.write(entry.getKey());
					writer.write(COL_SEPARATOR);

					// write the class
					writer.write(entry2.getKey());
					writer.write(COL_SEPARATOR);

					for (CodeMetric codeMetric : metrics) {
						Double metrVal = entry2.getValue().get(
								codeMetric.getMetricAcronym());
						if (metrVal == null) {
							writer.write("--");
						} else {
							writer.write(metrVal.toString());
						}
						writer.write(COL_SEPARATOR);
					}

					writer.write("\n");
				}
			}

		} catch (Exception e) {
			WritingException e2 = new WritingException(e.getMessage());
			ExceptionUtils.addStackTrace(e, e2);
			throw e2;
		} finally {
			if (writer != null) {
				try {
					writer.close();
				} catch (IOException e) {
					throw new WritingException(e.getMessage());
				}
			}
		}

		return file;
	}

}// end MetricsWriter