package unalcol.optimization.transformation;

import unalcol.optimization.generation.PopulationGeneration;
import unalcol.optimization.replacement.Replacement;
import unalcol.optimization.OptimizationFunction;
import unalcol.optimization.solution.Solution;

import unalcol.reflect.service.*;
import unalcol.types.collection.vector.Vector;

/**
 * <p>Title: Transformation</p>
 * <p>Description: An abstract class for representing the transformation of a population</p>
 * <p>Copyright:    Copyright (c) 2010</p>
 * <p>Company: Kunsamu</p>
 * @author Jonatan Gomez
 * @version 1.0
 *
 */

public class Transformation<T> implements Service{
  /**
   * Init the internal state of transformation
   */
  public void init(){}

    /**
     * Replacement strategy
     */
    protected Replacement<T> replacement;

    /**
     * Offspring generation strategy
     */
    protected PopulationGeneration<T> strategy;

    /**
     * Constructor
     * @param strategy Replacement strategy
     * @param replacement Offspring generation strategy (based on genetic operators)
     */
    public Transformation( PopulationGeneration<T> strategy,
                             Replacement<T> replacement ){
        this.strategy = strategy;
        this.replacement = replacement;
    }

    /**
     * Transforms the given population to another population according to its rules.
     * @param population The population to be transformed
     * @return Offspring population
     */
    public Vector<Solution<T>> apply( Vector<Solution<T>> population, OptimizationFunction<T> f ){
        Vector<Solution<T>> offspring = strategy.apply(population);
        Solution.evaluate((Vector)offspring,f);
        if( f.isNonStationary() ){
            Solution.evaluate((Vector)population,f);
        }
        return replacement.apply(population, offspring);
    }

    /**
     * Offspring generation strategy (based on genetic operators)
     * @return Offspring generation strategy (based on genetic operators)
     */
    public PopulationGeneration<T> generation(){ return strategy; }

    /**
     * Gets the Replacement strategy used by the evolutionary transformation
     * @return Replacement strategy
     */
    public Replacement<T> replacement(){ return replacement; }

    /**
     * Owner
     */
    @Override
    public Object owner(){
        return Object.class;
    }
}