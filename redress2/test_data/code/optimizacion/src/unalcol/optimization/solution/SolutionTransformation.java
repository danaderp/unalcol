/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package unalcol.optimization.solution;

import unalcol.optimization.OptimizationFunction;
import unalcol.optimization.generation.PopulationGeneration;
import unalcol.optimization.replacement.Replacement;
import unalcol.reflect.service.Service;
import unalcol.types.collection.vector.Vector;

/**
 *
 * @author jgomez
 */
public class SolutionTransformation <T> implements Service{
  /**
   * Init the internal state of transformation
   */
  public void init(){}

    /**
     * Replacement strategy
     */
    protected SolutionReplacement<T> replacement;

    /**
     * Offspring generation strategy
     */
    protected SolutionVariation<T> strategy;

    /**
     * Constructor
     * @param strategy Replacement strategy
     * @param replacement Offspring generation strategy (based on genetic operators)
     */
    public SolutionTransformation( SolutionVariation<T> strategy,
                                     SolutionReplacement<T> replacement ){
        this.strategy = strategy;
        this.replacement = replacement;
    }

    /**
     * Transforms the given population to another population according to its rules.
     * @param population The population to be transformed
     * @return Offspring population
     */
    public Solution<T> apply( Solution<T> solution, OptimizationFunction<T> f ){
        Vector<Solution<T>> candidates = strategy.apply(solution);
        Solution.evaluate((Vector)candidates,f);
        if( f.isNonStationary() ){
            solution.evaluate(f);
        }
        return replacement.apply(solution, candidates);
    }

    /**
     * Offspring generation strategy (based on genetic operators)
     * @return Offspring generation strategy (based on genetic operators)
     */
    public SolutionVariation<T> generation(){ return strategy; }

    /**
     * Gets the Replacement strategy used by the evolutionary transformation
     * @return Replacement strategy
     */
    public SolutionReplacement<T> replacement(){ return replacement; }

    /**
     * Owner
     */
    @Override
    public Object owner(){
        return Object.class;
    }
}
