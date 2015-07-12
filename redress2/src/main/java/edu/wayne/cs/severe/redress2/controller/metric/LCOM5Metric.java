package edu.wayne.cs.severe.redress2.controller.metric;

import java.io.File;
import java.util.HashSet;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.wayne.cs.severe.redress2.controller.HierarchyBuilder;
import edu.wayne.cs.severe.redress2.controller.MetricUtils;
import edu.wayne.cs.severe.redress2.entity.TypeDeclaration;
import edu.wayne.cs.severe.redress2.exception.MetricException;
import edu.wayne.cs.severe.redress2.utils.ExceptionUtils;

/**
 * @author ojcchar
 * @version 1.0
 * @created 23-Mar-2014 12:28:47
 */
public class LCOM5Metric extends CodeMetric {

	private static final String ACRONYM = "LCOM5";

	public LCOM5Metric(List<TypeDeclaration> sysTypeDcls,
			HierarchyBuilder builder) {
		super(sysTypeDcls, builder);
	}

	public LCOM5Metric() {
	}

	// logger
	private static Logger LOGGER = LoggerFactory.getLogger(LCOM5Metric.class);

	@Override
	public double computeMetric(TypeDeclaration typeDcl) throws MetricException {
		try {

			File compUnitFile = typeDcl.getCompUnit().getSrcFile();
			HashSet<String> fields = MetricUtils.getFields(typeDcl);

			double numFieldUsage = 0.0;
			for (String field : fields) {
				int numField = MetricUtils.getNumberOfMethodsUsingString(
						typeDcl, field);
				numFieldUsage += numField;
			}

			double numFields = fields.size();
			Double numMethods = MetricUtils.getNumberOfMethods(typeDcl,
					compUnitFile);

			double metric = (numMethods == 1 || numFields == 0) ? 0
					: ((numFieldUsage / numFields) - numMethods)
							/ (1 - numMethods);

			return metric;
		} catch (Exception e) {
			LOGGER.error("Error for class: " + typeDcl.getQualifiedName()
					+ " - " + e.getMessage(), e);
			MetricException ex = new MetricException(e.getMessage());
			ExceptionUtils.addStackTrace(e, ex);
			throw ex;
		}
	}

	@Override
	public String getMetricAcronym() {
		return ACRONYM;
	}

}// end LCOM5Metric