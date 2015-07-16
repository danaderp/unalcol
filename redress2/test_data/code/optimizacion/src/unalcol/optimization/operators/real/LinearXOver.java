package unalcol.optimization.operators.real;

import unalcol.random.*;
import unalcol.types.collection.vector.*;
import unalcol.clone.*;

/**
 * <p>Title: LinearXOver</p>
 * <p>Description:Applies a linear crossover. In this case the alpha is unique
 * for each component, it use two alpha, one for the first vector and one
 * for the second vector</p>
 * <p>Copyright: Copyright (c) 2010</p>
 * @author Jonatan Gomez
 * @version 1.0
 */

public class LinearXOver extends RealArityTwo{
  /**
   * default constructor
   */
  public LinearXOver() {  }

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

          double alpha = Random.random();
          double alpha_1 = Random.random();
          double neg_alpha = 1.0 - alpha;
          double neg_alpha_1 = 1.0 - alpha_1;
          double tx;
          double ty;
          for (int i = 0; i < min; i++) {
              tx = x[i];
              ty = y[i];
              x[i] = alpha * tx + neg_alpha * ty;
              y[i] = alpha_1 * tx + neg_alpha_1 * ty;
          }
          Vector<double[]> v = new Vector<double[]>();
          v.add(x);
          v.add(y);
          return v;
      } catch (Exception e) {
      }
      return null;
  }
}
