package unalcol.optimization.operators.real;

import unalcol.random.*;
import unalcol.types.collection.vector.*;

import unalcol.clone.*;

/**
 * <p>Title: OneDimensionSimpleXOver</p>
 * <p>Description:Exchanges one component of the first individual with
 * the same component of the second individual</p>
 * <p>Copyright: Copyright (c) 2010</p>
 * @author Jonatan Gomez
 * @version 1.0
 */

public class OneDimensionSimpleXOver extends RealArityTwo {
  /**
   * Default constructor
   */
  public OneDimensionSimpleXOver() {}

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
          double t = x[pos];
          x[pos] = y[pos];
          y[pos] = t;
          Vector<double[]> v = new Vector<double[]>();
          v.add(x);
          v.add(y);
          return v;
      } catch (Exception e) {
      }
      return null;
  }
}
