package edu.wayne.cs.severe.redress2.controller.metric;

import java.util.List;

import edu.wayne.cs.severe.redress2.controller.HierarchyBuilder;
import edu.wayne.cs.severe.redress2.entity.TypeDeclaration;
import edu.wayne.cs.severe.redress2.exception.MetricException;

/**
 * @author ojcchar
 * @version 1.0
 * @created 23-Mar-2014 12:28:47
 */
public class NOCMetric extends CodeMetric {

	private static final String ACRONYM = "NOC";

	public NOCMetric(List<TypeDeclaration> sysTypeDcls, HierarchyBuilder builder) {
		super(sysTypeDcls, builder);
	}

	public NOCMetric() {
	}

	@Override
	public double computeMetric(TypeDeclaration typeDcl) throws MetricException {

		List<TypeDeclaration> children = builder.getChildClasses().get(
				typeDcl.getQualifiedName());
		double numChildren = 0.0;
		if (children != null) {
			numChildren = children.size();
		}
		return numChildren;
	}

	@Override
	public String getMetricAcronym() {
		return ACRONYM;
	}

}// end NOCMetric