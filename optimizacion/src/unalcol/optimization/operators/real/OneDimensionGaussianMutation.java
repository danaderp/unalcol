/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package unalcol.optimization.operators.real;
import unalcol.random.*;
import unalcol.random.real.*;
import unalcol.types.collection.vector.*;
import unalcol.clone.*;

/**
 * <p>Title: GaussianMutation</p>
 * <p>Description: Changes one component of the encoded double[] with a number
 * randomly generated following a Gaussian distribution with mean the old value of
 * the component and the given standard deviation</p>
 * <p>Copyright: Copyright (c) 2010</p>
 * @author Jonatan Gomez
 * @version 1.0
 */
public class OneDimensionGaussianMutation extends RealArityOne {
  /**
   * Gauss number generator
   */
  protected StandardGaussianGenerator g = null;
  /**
   * sigma: standard deviation
   */
  protected double[] sigma = null;

  /**
   * Creates a Gaussian Mutation with the given standard deviation per component
   * @param _sigma Standard deviation per component
   */
  public OneDimensionGaussianMutation( double[] _sigma ) {
    sigma = _sigma;
    g = new StandardGaussianGenerator();
  }

  /**
   * Sets the standard deviation
   * @param _sigma Standard deviation per component
   */
  public void setSigma(double[] _sigma) {
    sigma = _sigma;
  }

  /**
   * Modifies the number in a random position for a guassian value with mean
   * thevalue encoded in the genome and sigma given as attribute
   * @param gen Genome to be modified
   * @return Index of the real modified
   */
  public Vector<double[]> generates(double[] gen) {
      try {
          double[] genome = (double[]) Clone.get(gen);
          int pos = Random.nextInt(genome.length);
          double x = genome[pos];
          double y = g.next() * sigma[pos];
          x += y;
          if (x < 0.0) {
              x = 0.0;
          } else {
              if (x > 1.0) {
                  x = 1.0;
              }
          }
          genome[pos] = x;
          Vector<double[]> v = new Vector<double[]>();
          v.add(genome);
          return v;
      } catch (Exception e) {
      }
      return null;
  }
}
