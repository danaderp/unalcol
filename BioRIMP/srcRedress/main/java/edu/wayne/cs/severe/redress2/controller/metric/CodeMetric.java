package edu.wayne.cs.severe.redress2.controller.metric;

import java.util.List;

import edu.wayne.cs.severe.redress2.controller.HierarchyBuilder;
import edu.wayne.cs.severe.redress2.entity.TypeDeclaration;
import edu.wayne.cs.severe.redress2.exception.MetricException;

/**
 * Abstract class that represents a code metric calculator
 * 
 * @author ojcchar
 * @version 1.0
 * @created 23-Mar-2014 12:28:47
 */
public abstract class CodeMetric {

	List<TypeDeclaration> sysTypeDcls;
	HierarchyBuilder builder;

	public CodeMetric() {
	}

	public CodeMetric(List<TypeDeclaration> sysTypeDcls,
			HierarchyBuilder builder) {
		this.sysTypeDcls = sysTypeDcls;
		this.builder = builder;
	}

	/**
	 * Computes the code metric on the type declaration in the compilation unit
	 * file
	 * 
	 * @param typeDcl
	 *            the type declaration
	 */
	public abstract double computeMetric(TypeDeclaration typeDcl)
			throws MetricException;

	/**
	 * Get the acronym of the current code metric
	 * 
	 * @return the code metric acronym
	 */
	public abstract String getMetricAcronym();

}// end CodeMetric