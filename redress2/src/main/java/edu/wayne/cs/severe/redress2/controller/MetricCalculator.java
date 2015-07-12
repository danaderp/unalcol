package edu.wayne.cs.severe.redress2.controller;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.wayne.cs.severe.redress2.controller.metric.CodeMetric;
import edu.wayne.cs.severe.redress2.entity.TypeDeclaration;
import edu.wayne.cs.severe.redress2.entity.refactoring.RefactoringOperation;
import edu.wayne.cs.severe.redress2.entity.refactoring.formulas.PredictionFormula;

/**
 * Computes a list of metrics on a list of types
 * 
 * @author ojcchar
 * @version 1.0
 * @created 23-Mar-2014 12:28:47
 */
public class MetricCalculator {

	private static Logger LOGGER = LoggerFactory
			.getLogger(MetricCalculator.class);

	/**
	 * Compute the list of metrics on the classes declared in the compilation
	 * units
	 * 
	 * @param classes
	 *            the list of classes
	 * @param metrics
	 *            the list of metrics
	 */
	public LinkedHashMap<String, LinkedHashMap<String, Double>> computeMetrics(
			List<TypeDeclaration> classes, List<CodeMetric> metrics) {

		LinkedHashMap<String, LinkedHashMap<String, Double>> metrCls = new LinkedHashMap<String, LinkedHashMap<String, Double>>();

		int i = 1;
		for (TypeDeclaration typeDcl : classes) {
			LinkedHashMap<String, Double> metrVals = metrCls.get(typeDcl);

			if (metrVals != null) {
				LOGGER.error("Class " + typeDcl + " already exists");
				continue;
			}

			try {

				metrVals = new LinkedHashMap<String, Double>();

				// class qualified name
				String classQName = typeDcl.getQualifiedName();

				LOGGER.debug(i + ". Computing metrics on class " + classQName);
				i++;

				// for each metric
				for (CodeMetric metric : metrics) {

					// if (!"org.argouml.ui.ShadowComboBox.ShadowFig"
					// .equals(classQName)) {
					// continue;
					// }
					//
					// LOGGER.debug("Calculating metric "
					// + metric.getMetricAcronym() + " on class "
					// + classQName);

					// compute the metric on the class
					double metrVal = metric.computeMetric(typeDcl);
					metrVals.put(metric.getMetricAcronym(), metrVal);
				}

				metrCls.put(classQName, metrVals);

			} catch (Exception e) {
				LOGGER.error("Error for class: " + typeDcl.getQualifiedName()
						+ ". " + e.getMessage(), e);
			}

		}
		return metrCls;

	}

	/**
	 * 
	 * @param refactorings
	 *            the list of classes
	 * @param metrics
	 *            the list of metrics
	 * @param prevMetrics
	 */
	public LinkedHashMap<String, LinkedHashMap<String, LinkedHashMap<String, Double>>> predictMetrics(
			List<RefactoringOperation> refactorings, List<CodeMetric> metrics,
			LinkedHashMap<String, LinkedHashMap<String, Double>> prevMetrics) {

		LinkedHashMap<String, LinkedHashMap<String, LinkedHashMap<String, Double>>> predMetrs = new LinkedHashMap<String, LinkedHashMap<String, LinkedHashMap<String, Double>>>();

		// each refactoring
		for (RefactoringOperation ref : refactorings) {

			try {

				String refId = ref.getRefId();
				LinkedHashMap<String, LinkedHashMap<String, Double>> metrsRef = predMetrs
						.get(refId);
				if (metrsRef == null) {
					metrsRef = new LinkedHashMap<String, LinkedHashMap<String, Double>>();
				}

				LOGGER.debug("Processing refactoring: " + ref);

				// each metric
				for (CodeMetric codeMetric : metrics) {

					String metricAcronym = codeMetric.getMetricAcronym();

					// formula for the metric?
					PredictionFormula predFormula = ref.getRefType()
							.getPredFormula(metricAcronym);
					if (predFormula == null) {
						continue;
					}

					// LOGGER.debug("Pred formula: "
					// + predFormula.getClass().getSimpleName());

					// predict values
					HashMap<String, Double> predictMetrVal = predFormula
							.predictMetrVal(ref, prevMetrics);

					if (predictMetrVal == null) {
						throw new Exception("The result for formula "
								+ predFormula.getClass().getSimpleName()
								+ " is null");
					}

					// LOGGER.debug("Results: " + predictMetrVal);

					Set<Entry<String, Double>> entrySet = predictMetrVal
							.entrySet();
					for (Entry<String, Double> entry : entrySet) {

						String classQName = entry.getKey();

						LinkedHashMap<String, Double> metrs = metrsRef
								.get(classQName);
						if (metrs == null) {
							metrs = new LinkedHashMap<String, Double>();
						}

						metrs.put(metricAcronym, entry.getValue());

						metrsRef.put(classQName, metrs);
					}

				}

				predMetrs.put(refId, metrsRef);

			} catch (Exception e) {
				LOGGER.error("Error for refactoring: " + ref, e);
			}

		}
		return predMetrs;
	}
}// end MetricCalculator