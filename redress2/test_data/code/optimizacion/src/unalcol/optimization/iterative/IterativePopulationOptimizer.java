package unalcol.optimization.iterative;

import unalcol.optimization.transformation.Transformation;
import unalcol.tracer.*;
import unalcol.descriptors.*;
import unalcol.algorithm.iterative.*;
import unalcol.math.logic.Predicate;
import unalcol.optimization.OptimizationFunction;
import unalcol.optimization.PopulationOptimizer;
import unalcol.optimization.solution.Solution;
import unalcol.types.collection.vector.Vector;


/**
 * <p>Title: IterativePopulationOptimizer</p>
 * <p>Description: Abstract iterative population based optimization techniique</p>
 * <p>Copyright:    Copyright (c) 2010</p>
 * <p>Company: Kunsamu</p>
 * @author Jonatan Gomez
 * @version 1.0
 */
public class IterativePopulationOptimizer<S>
        extends IterativeAlgorithm<OptimizationFunction<S>,Vector<Solution<S>>>
        implements PopulationOptimizer<S>{

  protected int generation;

  /**
   * The transformation function used to evolve the population
   */
  protected Transformation<S> transformation = null;

  public int generation(){
      return generation;
  }

  public Transformation<S> transformation(){
      return transformation;
  }

  /**
   * Constructor: Creates an evolutionary algorithm with the given population,
   * continuation condition and transformation function
   * @param population  The population to evolved
   * @param transformation The transformation operation
   * @param condition  The evolution condition (the evolutionary process is executed
   * until the condition is false)
   */
  public IterativePopulationOptimizer( Predicate condition,
                              Transformation<S> transformation, Vector<Solution<S>> population ) {
    super(condition);
    this.transformation = transformation;
    this.output = population;
  }

  /**
   * Initializes the algorithm.
   */
  @Override
  public void init() {
    super.init();
    if (transformation != null) { transformation.init(); }
  }

  @Override
  public Vector<Solution<S>> nonIterOutput( OptimizationFunction<S> f ){
      Solution.evaluate((Vector)output, f);
      generation = 0;
      updateTrace();
      return output;
  }

  public void updateTrace(){
      double[] stat = DescriptorsProvider.descriptors(this);
      double[] best = DescriptorsProvider.descriptors(output.get((int)stat[1]).get());
      if( best != null ){
          double[] nstat = new double[stat.length+best.length];
          for( int i=0; i<stat.length; i++ ){
              nstat[i] = stat[i];
          }
          int k = stat.length;
          for( int i=0; i<best.length; i++ ){
              nstat[i+k] = best[i];
          }
          stat = nstat;
      }
      TracerProvider.trace( this, stat );
  }


  /**
   * An evolutionary algorithm iteration
   */
  @Override
  public Vector<Solution<S>> iteration( int k, OptimizationFunction<S> f, 
          Vector<Solution<S>> output ) {
    this.output = transformation.apply(output, f);
    generation = k+1;
    updateTrace();
    return this.output;
  }
}