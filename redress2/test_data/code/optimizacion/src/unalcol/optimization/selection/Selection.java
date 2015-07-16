package unalcol.optimization.selection;
import unalcol.optimization.solution.Solution;
import unalcol.optimization.solution.Solution;

import unalcol.types.collection.vector.*;

/**
 * <p>Title: Selection</p>
 * <p>Description: Abstract selection operator on populations of candidate solutions.</p>
 * <p>Copyright: Copyright (c) 2010</p>
 * <p>Company: Kunsamu</p>
 * @author Jonatan Gomez
 * @version 1.0
 */

public abstract class Selection<T>{
  /**
   * Selects a set of candidate solutions from a population
   * @param n Number of candidate solutions to be selected
   * @param population Population of candidate solutions
   * @return The collection of selected candidate solutions
   */
  public abstract Vector<Solution<T>> apply( int n, Vector<Solution<T>> population );

  /**
   * Gets a single candidate solution from the population using the selection mechanism
   * @param population Population of candidate solutions used for getting one candidate solution
   * @return A single candidate solution from the population
   */
  public abstract Solution<T> choose_one( Vector<Solution<T>> population );

}