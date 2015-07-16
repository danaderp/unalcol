package unalcol.optimization.selection;

import unalcol.optimization.solution.SolutionOrder;
import unalcol.optimization.solution.Solution;
import unalcol.clone.Clone;
import unalcol.optimization.*;
import unalcol.types.collection.vector.*;
import unalcol.random.integer.*;
import unalcol.sort.ReversedOrder;

/**
 * <p>Title: Elitism</p>
 * <p>Description: A elitist selection strategy. In this strategy the best individuals
 * (Elite percentage) are always selected and the worst individuals (cull percentange)
 * are never taken into account. The remaining part of the individual is choosen
 * randomly, and each individual has a probability to be choosen that is proportional to
 * its OptimizationFunction.</p>
 * <p>Copyright: Copyright (c) 2010</p>
 * <p>Company: Kunsamu</p>
 * @author Jonatan Gomez
 * @version 1.0
 */
public class Elitism<T> extends Selection<T> {

  /**
   * Elite percentage: Percentage of individuals to be included in the selection
   * according to their OptimizationFunction
   */
  protected double elite_percentage = 0.1;
  /**
   * Cull percentage: percentage of individuals to be excluded in the selection
   * according to their OptimizationFunction
   */
  protected double cull_percentage = 0.1;

  /**
   * Constructor: Create a Elitist selection strategy.
   * @param _environment Environment of the Population
   * @param _n Number of individuals to be choosen
   * @param _includeX If the individual given in the apply method is going to be selected always or not
   * @param _elite_percentage Percentage of individuals to be included in the selection
   * @param _cull_percentage Percentage of individuals to be excluded in the selection
   */
  public Elitism( double _elite_percentage, double _cull_percentage ){
    elite_percentage = _elite_percentage;
    cull_percentage = _cull_percentage;
  }

  /**
   * Choose a set of individuals from the population including the individual x
   * @param population Population source of the selection process
   * @param x Individual to be included in the selection
   */
  @Override
  public Vector<Solution<T>> apply( int n, Vector<Solution<T>> parents ){
      Vector<Solution<T>> sel = new Vector();
      int s = parents.size();
      parents = (Vector<Solution<T>>)Clone.get(parents);
      VectorSort<Solution<T>> sort = new VectorSort();
      sort.apply( parents, new ReversedOrder( new SolutionOrder() )  );
      int m = (int) (parents.size() * elite_percentage);
      for (int i = 0; i < n && i < m; i++) {
          sel.add(parents.get(i));
      }
      if( m<n ){
          int k = (int) (s * (1.0 - cull_percentage));
          double[] weight = new double[k];
          double total = k * (k + 1) / 2.0;
          for (int i = 0; i < k; i++) {
              weight[i] = (k - i) / total;
          }
          Roulette generator = new Roulette(weight);
          n -= m;
          int[] index = generator.generate(n);
          for (int i=0; i<n; i++) {
              sel.add(parents.get(index[i]));
          }
      }
      return sel;
  }

  /**
   * Gets a single candidate solution from the population using the selection mechanism
   * @param population Population of candidate solutions used for getting one candidate solution
   * @return A single candidate solution from the population
   */
  @Override
  public Solution<T> choose_one( Vector<Solution<T>> population ){
      SolutionOrder order = new SolutionOrder();
      Solution<T> theOne = population.get(0);
      for( int i=1; i<population.size(); i++ ){
          if( order.compare(theOne, population.get(i)) < 0 ){
              theOne = population.get(i);
          }
      }
      return theOne;
  }
}