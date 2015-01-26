package unalcol.optimization.selection;

import unalcol.optimization.solution.Solution;
import unalcol.optimization.*;
import unalcol.types.collection.vector.*;
import unalcol.random.integer.UniformIntegerGenerator;

/**
 * <p>Title: Tournament</p>
 * <p>Description: A tournament selection strategy. In this strategy each individual that
 * is choosen is selected from a group of individuals. The group of individuals are choosen
 * randomly from the population with a uniform probability. From this group of individuals
 * one is choosen using the OptimizationFunction as the probability to win the game.</p>
 * <p>Copyright: Copyright (c) 2010</p>
 * <p>Company: Kunsamu</p>
 * @author Jonatan Gomez
 * @version 1.0
 */
 public class Tournament<T> extends Uniform<T>{
  /**
   * The tournament size
   */
  protected int m = 4;

  /**
   * Selection mechanism used for selecting the tournament winner
   */
  protected Selection<T> inner = new Elitism<T>(1.0,0.0);

  /**
   * Constructor: Create a tournament selection strategy with m players.
   * @param m The number of players in the tournament
   */
  public Tournament( int m ){
    this.m = m;
  }

  /**
   * Constructor: Create a tournament selection strategy with m players, using the given
   * selection strategy for selecting the tournament winner.
   * @param m The number of players in the tournament
   * @param s The inner selection st¿rategy for determining the tournament winner
   */
  public Tournament( int m, Selection<T> s ){
    this.m = m;
    this.inner = s;
  }

  /**
   * Choose an individuals from the population
   * @param generator A uniform random generator for selecting the tournament players
   * @param population Population source of the selection process
   * @return Index of the tournament winner individual
   */
  @Override
  protected Solution<T> choose_one( UniformIntegerGenerator generator,
                                    Vector<Solution<T>> population ){
    Vector<Solution<T>> candidates = new Vector<Solution<T>>();
    for( int i=0; i<m; i++ ){
      candidates.add( population.get( generator.next() ) );
    }
    return inner.choose_one(candidates);
  }
}