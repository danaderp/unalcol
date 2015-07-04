package unalcol.optimization.operators.real;

import unalcol.random.*;
import unalcol.random.real.*;
import unalcol.types.collection.vector.*;
import unalcol.clone.*;
import unalcol.types.real.array.*;
import unalcol.types.real.*;
/**
 * <p>Title: GaussianMutation</p>
 * <p>Description: Changes one component of the encoded double[] with a number
 * randomly generated following a Gaussian distribution with mean the old value of
 * the component and the given standard deviation</p>
 * <p>Copyright: Copyright (c) 2010</p>
 * @author Jonatan Gomez
 * @version 1.0
 */
public class GaussianMutation extends RealArityOne {
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
  public GaussianMutation( double[] _sigma ) {
    sigma = _sigma;
    g = new StandardGaussianGenerator();
  }

  /**
   */
  public double[] getSigma(){
     return sigma;
  }
  /**
   * Sets the standard deviation
   * @param _sigma Standard deviation per component
   */
  public void setSigma(double[] _sigma) {
    sigma = _sigma;
  }

  /**
   * Modifies the number in a random position for a Gaussian value with mean
   * the value encoded in the genome and sigma given as attribute
   * @param gen Genome to be modified
   * @return Index of the real modified
   */
  public Vector<double[]> generates(double[] gen) {
      try {
          double[] genome = (double[]) Clone.get(gen);
          double[] delta = DoubleArrayInit.random(genome.length);
          boolean reduced = false;
          if( reduced ){
              for( int i=0; i<delta.length; i++ ){
                  if( Math.random() > 0.01 ){
                      delta[i] = 0.0;
                  }
              }
          }
          double norm = 0.0;
          for( int i=0; i<delta.length; i++ ){
              norm += delta[i]*delta[i];
          }
          norm = Math.sqrt(norm);
          for( int i=0; i<delta.length; i++ ){
              delta[i] /= norm;
          }
          double gNumber = g.next();
          for( int pos=0; pos<genome.length; pos++ ){
              double x = genome[pos];
              double y = gNumber * delta[pos] * sigma[pos];
              x += y;
              if (x < 0.0) {
                  x = 0.0;
              } else {
                  if (x > 1.0) {
                      x = 1.0;
                  }
              }
              genome[pos] = x;
          }
          Vector<double[]> v = new Vector<double[]>();
          v.add(genome);
          return v;
      } catch (Exception e) {
      }
      return null;
  }
}