package edu.wayne.cs.severe.redress2.entity.refactoring.formulas;

import java.util.HashMap;
import java.util.LinkedHashMap;

import edu.wayne.cs.severe.redress2.controller.metric.CodeMetric;
import edu.wayne.cs.severe.redress2.entity.refactoring.RefactoringOperation;

/**
 * Abstract class that represents a prediction formula
 * 
 * @author ojcchar
 * @version 1.0
 * @created 28-Mar-2014 17:27:28
 */
public abstract class PredictionFormula {

	/**
	 * Default constructor
	 */
	public PredictionFormula() {
	}

	/**
	 * Computes the prediction for the metric value based on the refactoring
	 * operation and the previous metrics
	 * 
	 * @param ref
	 *            the refactoring operation which will impact some metric
	 * @param prevMetrics
	 *            the previous metric values of the entire system
	 * @return the predicted values of the classes involved in the refactoring
	 *         operation
	 * @throws Exception
	 *             if some error occurs
	 */
	public abstract HashMap<String, Double> predictMetrVal(
			RefactoringOperation ref,
			LinkedHashMap<String, LinkedHashMap<String, Double>> prevMetrics)
			throws Exception;

	/**
	 * Get the code metric corresponding to this formula
	 * 
	 * @return the code metric
	 */
	public abstract CodeMetric getMetric();

}// end PredictionFormula