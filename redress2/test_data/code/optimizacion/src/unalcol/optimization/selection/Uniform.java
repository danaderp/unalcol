package unalcol.optimization.selection;

import unalcol.optimization.solution.Solution;
import unalcol.optimization.*;
import unalcol.random.integer.*;
import unalcol.types.collection.vector.*;

/**
 * <p>Title: Uniform</p>
 * <p>Description: The uniform selection operator. In this selection strategy all individuals
 * have the same probability to be choosen</p>
 * <p>Copyright: Copyright (c) 2010</p>
 * <p>Company: Kunsamu</p>
 * @author Jonatan Gomez
 * @version 1.0
 */

public class Uniform<T> extends Selection<T> {

  /**
   * Default constructor
   */
  public Uniform(){
  }

  /**
   * Choose an individuals from the population
   * @param generator A uniform random generator for selecting the candidate solution
   * @param population Population source of the selection process
   * @return Index of the tournament winner individual
   */
  protected Solution<T> choose_one( UniformIntegerGenerator generator,
                                    Vector<Solution<T>> population ){
    return population.get(generator.next());
  }

  /**
   * Choose an individuals from the population
   * @param generator A uniform random generator for selecting the tournament players
   * @param population Population source of the selection process
   * @return Index of the tournament winner individual
   */
  public Solution<T> choose_one( Vector<Solution<T>> population ){
    return choose_one(new UniformIntegerGenerator(population.size()), population);
  }

  /**
   * Choose a set of individuals from the population including the individual x
   * @param population Population source of the selection process
   * @param x Individual to be included in the selection
   */
  @Override
  public Vector<Solution<T>> apply( int n, Vector<Solution<T>> population ){
    UniformIntegerGenerator g =  new UniformIntegerGenerator(population.size());
    Vector<Solution<T>> sel = new Vector<Solution<T>>();
    for (int i = 0; i<n; i++) {
        sel.add(choose_one(g, population));
    }
    return sel;
  }
}