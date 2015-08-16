package edu.wayne.cs.severe.redress2.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedHashMap;

/**
 * @author ojcchar
 * @version 1.0
 * @created 23-Mar-2014 12:28:47
 */
public class MetricsReader {

	private File metrFile;

	public MetricsReader(File systemPath, String sysName) {

		metrFile = new File(systemPath.getAbsolutePath() + File.separator
				+ MetricsWriter.METRIC_VALS_FOLDER + File.separator
				+ MetricsWriter.FILE_PREFIX + sysName + MetricsWriter.CSV_EXT);
	}

	/**
	 * 
	 * @param metrFile
	 * @throws IOException
	 */
	public LinkedHashMap<String, LinkedHashMap<String, Double>> readMetrics()
			throws IOException {

		LinkedHashMap<String, LinkedHashMap<String, Double>> metrs = new LinkedHashMap<String, LinkedHashMap<String, Double>>();

		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(metrFile));
			String line = br.readLine();

			String[] acronyms = line.split(",");

			while ((line = br.readLine()) != null) {
				String[] lineSpls = line.split(",");

				LinkedHashMap<String, Double> metrsCl = new LinkedHashMap<String, Double>();

				for (int i = 1; i < lineSpls.length; i++) {
					String lSpl = lineSpls[i];
					metrsCl.put(acronyms[i], Double.valueOf(lSpl));
				}

				metrs.put(lineSpls[0], metrsCl);

			}

		} finally {
			if (br != null) {
				br.close();
			}
		}

		return metrs;
	}

}// end MetricsReader