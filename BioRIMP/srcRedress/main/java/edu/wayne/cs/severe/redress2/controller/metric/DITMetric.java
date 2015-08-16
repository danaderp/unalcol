package edu.wayne.cs.severe.redress2.controller.metric;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.wayne.cs.severe.redress2.controller.HierarchyBuilder;
import edu.wayne.cs.severe.redress2.entity.TypeDeclaration;
import edu.wayne.cs.severe.redress2.exception.MetricException;
import edu.wayne.cs.severe.redress2.utils.ExceptionUtils;

/**
 * @author ojcchar
 * @version 1.0
 * @created 23-Mar-2014 12:28:47
 */
public class DITMetric extends CodeMetric {

	private static final String ACRONYM = "DIT";
	// logger
	private static Logger LOGGER = LoggerFactory.getLogger(CBOMetric.class);

	public DITMetric(List<TypeDeclaration> sysTypeDcls, HierarchyBuilder builder) {
		super(sysTypeDcls, builder);
	}

	public DITMetric() {
	}

	public static LinkedHashMap<String, Double> DITs = new LinkedHashMap<String, Double>();

	@Override
	public double computeMetric(TypeDeclaration typeDcl) throws MetricException {

		String qName = typeDcl.getQualifiedName();
		try {

			Double dit = DITs.get(qName);
			if (DITs.get(qName) != null) {
				return dit;
			}

			dit = 0.0;
			DITs.put(qName, dit);

			List<TypeDeclaration> superClasses = builder.getParentClasses()
					.get(qName);

			if (superClasses != null && superClasses.size() != 0) {

				// System.out.println("SuperClasses of " + qName + ": "
				// + superClasses);

				// get the dit for every class
				List<Double> ditsClasses = getDitsClasses(superClasses);

				// choose the greatest
				dit = Collections.max(ditsClasses);

				// add 1 and store it
				dit++;
			}

			DITs.put(qName, dit);

			return dit;

		} catch (Exception e) {
			LOGGER.error("Error for class: " + qName + " - " + e.getMessage(),
					e);
			MetricException ex = new MetricException(e.getMessage());
			ExceptionUtils.addStackTrace(e, ex);
			throw ex;
		}

	}

	public List<Double> getDitsClasses(List<TypeDeclaration> superClasses)
			throws Exception {
		List<Double> dits = new ArrayList<Double>();

		for (TypeDeclaration supClass : superClasses) {

			Double dit = computeMetric(supClass);

			dits.add(dit);
		}
		return dits;
	}

	public String getMetricAcronym() {
		return ACRONYM;
	}

}// end DITMetric