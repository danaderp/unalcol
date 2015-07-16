/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package unalcol.optimization.iterative;

import unalcol.algorithm.iterative.IterativeAlgorithm;
import unalcol.descriptors.DescriptorsProvider;
import unalcol.math.logic.Predicate;
import unalcol.optimization.OptimizationFunction;
import unalcol.optimization.Optimizer;
import unalcol.optimization.solution.Solution;
import unalcol.optimization.solution.SolutionTransformation;
import unalcol.tracer.TracerProvider;

/**
 *
 * @author jgomez
 */
public class IterativeOptimizer<S>     
    extends IterativeAlgorithm<OptimizationFunction<S>,Solution<S>>
        implements Optimizer<S>{

  protected int generation;

  /**
   * The transformation function used to evolve the population
   */
  protected SolutionTransformation<S> transformation = null;

  public int generation(){
      return generation;
  }

  public SolutionTransformation<S> transformation(){
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
  public IterativeOptimizer( Predicate condition,
                               SolutionTransformation<S> transformation,
                               Solution<S> solution ) {
    super(condition);
    this.transformation = transformation;
    this.output = solution;
  }

  /**
   * Initializes the algorithm.
   */
  public void init() {
    super.init();
    if (transformation != null) { transformation.init(); }
  }

  @Override
  public Solution<S> nonIterOutput( OptimizationFunction<S> f ){
      output.evaluate(f);
      generation = 0;
      updateTrace();
      return output;
  }

  public void updateTrace(){
//      double[] stat = DescriptorsProvider.descriptors(this);
//      TracerProvider.trace( this, stat );
      TracerProvider.trace( this, this );
  }


  /**
   * An evolutionary algorithm iteration
   */
  @Override
  public Solution<S> iteration( int k, OptimizationFunction<S> f, Solution<S> output ) {
    output = transformation.apply(output, f);
    generation = k+1;
    updateTrace();
    return output;
  }    
}
