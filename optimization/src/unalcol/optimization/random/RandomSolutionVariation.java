/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package unalcol.optimization.random;

import unalcol.optimization.operators.ArityOne;
import unalcol.optimization.solution.Solution;
import unalcol.optimization.solution.SolutionVariation;
import unalcol.types.collection.vector.Vector;

/**
 *
 * @author jgomez
 */
public class RandomSolutionVariation <T> extends SolutionVariation<T>{
  /**
   * Set of genetic operators that are used by CEA for evolving the solution chromosomes
   */
  protected ArityOne<T> mutation = null;

  /**
   * Constructor: Creates a Hill Climbing transformation
   * @param grow Growing function from encoding to problem solution space
   * @param mutation Genetic operator used to evolve the solution
   */
  public RandomSolutionVariation( ArityOne<T> mutation ){
      this.mutation = mutation;
  }

  /**
   * Transforms the given population to another population according to its rules.
   * @param population The population to be transformed
   * @param replacement Replacement strategy
   * @param f Function to be optimized
   */
  @Override
  public Vector<Solution<T>> apply(Solution<T> solution) {
      Vector<Solution<T>> buffer = new Vector<>();
      Vector<T> v = mutation.applies(solution.get());
      for( int k=0; k<v.size(); k++ ){
        buffer.add(solution.newInstance(v.get(k)));
      }
      return buffer;
  }
  
  @Override
  public Object owner(){
      return Object.class;
  }
}
