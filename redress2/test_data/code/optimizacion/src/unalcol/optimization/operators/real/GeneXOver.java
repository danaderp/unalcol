package unalcol.optimization.operators.real;

import unalcol.random.*;
import unalcol.types.collection.vector.*;
import unalcol.clone.*;

/**
 * <p>Title: LinearXOverPerDimension</p>
 * <p>Description:Applies a linear crossover per dimension. in this case each alpha
 * is different for each component.</p>
 * <p>Copyright: Copyright (c) 2010</p>
 * @author Jonatan Gomez
 * @version 1.0
 */

public class GeneXOver extends RealArityTwo {
  /**
   * Default constructor
   */
  public GeneXOver() {
  }

  /**
   * Apply the 2-ary genetic operator over the individual genomes
   * @param c1 First Individuals genome to be modified by the genetic operator
   * @param c2 Second Individuals genome to be modified by the genetic operator
   * @return extra information of the genetic operator
   */
  public Vector<double[]> generates(double[] c1, double[] c2) {
      try{
          double[] x = (double[]) Clone.get(c1);
          double[] y = (double[]) Clone.get(c2);
          double a;
          double a_1;
          double tx, ty;
          int min = Math.min(x.length, y.length);
          for (int i = 0; i < min; i++) {
              a = Random.random();
              if (a < 0.5) {
                  a = 0.0;
              } else {
                  a = 1.0;
              }
              a_1 = 1.0 - a;
              tx = x[i];
              ty = y[i];
              x[i] = a * tx + a_1 * ty;
              y[i] = a_1 * tx + a * ty;
          }
          Vector<double[]> v = new Vector<double[]>();
          v.add(x);
          v.add(y);
          return v;
      }catch( Exception e ){
      }
      return null;
  }
}
