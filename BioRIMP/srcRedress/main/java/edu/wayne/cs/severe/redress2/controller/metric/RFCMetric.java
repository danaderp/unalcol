package edu.wayne.cs.severe.redress2.controller.metric;

import java.util.HashSet;
import java.util.LinkedHashSet;
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
 * @created 23-Mar-2014 12:28:48
 */
public class RFCMetric extends CodeMetric {

	private static final String ACRONYM = "RFC";

	public RFCMetric(List<TypeDeclaration> sysTypeDcls, HierarchyBuilder builder) {
		super(sysTypeDcls, builder);
	}

	public RFCMetric() {
	}

	// logger
	private static Logger LOGGER = LoggerFactory.getLogger(RFCMetric.class);

	@Override
	public double computeMetric(TypeDeclaration typeDcl) throws MetricException {

		try {

			HashSet<String> methodsCalls = MetricUtils.getMethodCalls(typeDcl);

			// -------------------------------------

			LinkedHashSet<String> methods = MetricUtils.getMethods(typeDcl);
			methodsCalls.addAll(methods);

			return methodsCalls.size();

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
}// end RFCMetric