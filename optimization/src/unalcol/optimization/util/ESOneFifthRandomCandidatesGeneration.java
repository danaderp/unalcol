/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package unalcol.optimization.util;

import unalcol.optimization.generation.PopulationGeneration;
import unalcol.optimization.operators.ArityOne;
import unalcol.optimization.operators.real.GaussianMutation;
import unalcol.optimization.solution.Solution;
import unalcol.types.collection.vector.Vector;

/**
 *
 * @author Jonatan
 */
public class ESOneFifthRandomCandidatesGeneration implements PopulationGeneration<double[]>{
  /**
   * Set of genetic operators that are used by CEA for evolving the solution chromosomes
   */
  protected GaussianMutation mutation = null;
  
  protected double[][] sigma;

  /**
   * Constructor: Creates a Hill Climbing transformation
   * @param grow Growing function from encoding to problem solution space
   * @param mutation Genetic operator used to evolve the solution
   */
  public ESOneFifthRandomCandidatesGeneration( GaussianMutation mutation ){
      this.mutation = mutation;
  }

  public void setSigma( double[][] sigma ){
      this.sigma = sigma;
  }
  /**
   * Transforms the given population to another population according to its rules.
   * @param population The population to be transformed
   * @param replacement Replacement strategy
   * @param f Function to be optimized
   */
  @Override
  public Vector<Solution<double[]>> apply(Vector<Solution<double[]>> population) {
      int size = population.size();
      Vector<Solution<double[]>> buffer = new Vector<>();
      for (int i = 0; i < size; i++) {
          Solution<double[]> sol = population.get(i);
          mutation.setSigma(sigma[i]);
          Vector<double[]> v = mutation.applies(sol.get());
          for( int k=0; k<v.size(); k++ ){
              buffer.add(sol.newInstance(v.get(k)));
          }
      }
      return buffer;
  }
  
  @Override
  public Object owner(){
      return Object.class;
  }
}
