package edu.wayne.cs.severe.redress2.controller.metric;

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
public class MPCMetric extends CodeMetric {

	private static final String ACRONYM = "MPC";

	public MPCMetric(List<TypeDeclaration> sysTypeDcls, HierarchyBuilder builder) {
		super(sysTypeDcls, builder);
	}

	public MPCMetric() {
	}

	// logger
	private static Logger LOGGER = LoggerFactory.getLogger(MPCMetric.class);

	@Override
	public double computeMetric(TypeDeclaration typeDcl) throws MetricException {
		try {

			HashSet<String> methodsCalls = MetricUtils.getMethodCalls(typeDcl);

			HashSet<String> methods = MetricUtils.getMethods(typeDcl);

			methodsCalls.removeAll(methods);

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

}// end MPCMetric