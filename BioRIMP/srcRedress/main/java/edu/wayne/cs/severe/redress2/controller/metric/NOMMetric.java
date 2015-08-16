package edu.wayne.cs.severe.redress2.controller.metric;

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
public class NOMMetric extends CodeMetric {

	private static final String ACRONYM = "NOM";

	public NOMMetric(List<TypeDeclaration> sysTypeDcls, HierarchyBuilder builder) {
		super(sysTypeDcls, builder);
	}

	public NOMMetric() {
	}

	// logger
	private static Logger LOGGER = LoggerFactory.getLogger(NOMMetric.class);

	@Override
	public double computeMetric(TypeDeclaration typeDcl) throws MetricException {

		// FIXME: methods with annotations are not correcly counted because
		// srcML has a bug and does not parse well the methods
		try {
			Double numMethods = MetricUtils.getNumberOfMethods(typeDcl, typeDcl
					.getCompUnit().getSrcFile());
			return numMethods;

		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			MetricException ex = new MetricException(e.getMessage());
			ExceptionUtils.addStackTrace(e, ex);
			throw ex;
		}
	}

	@Override
	public String getMetricAcronym() {
		return ACRONYM;
	}

}// end NOMMetric