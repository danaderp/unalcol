package unalcol.optimization.selection;

import unalcol.optimization.solution.Solution;
import unalcol.types.collection.vector.Vector;

/**
 * <p>Title: IdSelection</p>
 * <p>Description: The id selection operator. Returns the same population</p>
 * <p>Copyright: Copyright (c) 2010</p>
 * <p>Company: Kunsamu</p>
 * @author Jonatan Gomez
 * @version 1.0
 */

public class IdSelection<T> extends Uniform<T> {

  /**
   * Default Constructor
   */
  public IdSelection(){
  }

  /**
   * Choose a set of individuals from the population including the individual x
   * @param population Population source of the selection process
   * @param x Individual to be included in the selection
   */
  @Override
  public Vector<Solution<T>> apply( int n, Vector<Solution<T>> population ){
      return population;
  }
}