package unalcol.optimization.operators.real;

import unalcol.random.*;
import unalcol.types.collection.vector.*;

import unalcol.clone.*;

/**
 * <p>Title: LinearXOver</p>
 * <p>Description:Applies a linear crossover to a single component</p>
 * <p>Copyright: Copyright (c) 2010</p>
 * @author Jonatan Gomez
 * @version 1.0
 */
public class OneDimensionLinearXOver extends RealArityTwo {
  /**
   * Default constructor
   */
  public OneDimensionLinearXOver() {
  }

  /**
   * Apply the 2-ary genetic operator over the individual genomes
   * @param c1 First Individuals genome to be modified by the genetic operator
   * @param c2 Second Individuals genome to be modified by the genetic operator
   * @return extra information of the genetic operator
   */
  public Vector<double[]> generates(double[] c1, double[] c2) {
      try {
          double[] x = (double[]) Clone.get(c1);
          double[] y = (double[]) Clone.get(c2);
          int min = Math.min(x.length, y.length);

          int pos = Random.nextInt(min);

          double alpha = Random.random();
          double alpha_1 = 1.0 - alpha;
          double tx, ty;
          tx = x[pos];
          ty = y[pos];
          x[pos] = alpha * tx + alpha_1 * ty;
          y[pos] = alpha_1 * tx + alpha * ty;
          Vector<double[]> v = new Vector<double[]>();
          v.add(x);
          v.add(y);
          return v;
      } catch (Exception e) {
      }
      return null;
  }
}
