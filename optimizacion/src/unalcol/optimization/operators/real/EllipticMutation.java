/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package unalcol.optimization.operators.real;

import unalcol.clone.Clone;
import unalcol.random.real.DoubleGenerator;
import unalcol.types.collection.vector.Vector;
import unalcol.types.real.array.DoubleArrayInit;

/**
 *
 * @author jgomez
 */
public class EllipticMutation   extends RealArityOne {
  /**
   * Gauss number generator
   */
  protected DoubleGenerator[] g = null;
  /**
   * sigma: standard deviation
   */
  protected double[] sigma;

  /**
   * Creates a Gaussian Mutation with the given standard deviation per component
   * @param _sigma Standard deviation per component
   */
  public EllipticMutation( double[] _sigma, DoubleGenerator _g ) {
    sigma = _sigma.clone();
    var = new double[sigma.length];
    g = new DoubleGenerator[sigma.length];
    for( int i=0; i<g.length; i++ ){
//        g[i] = _g.new_instance();
        g[i] = _g;
    }    
  }

  /**
   * Creates a Gaussian Mutation with the given standard deviation per component
   * @param _sigma Standard deviation per component
   */
  public EllipticMutation( int n, double _sigma, DoubleGenerator _g ) {
    this( DoubleArrayInit.create(n, _sigma), _g );
  }

  
  protected double[] var;
  public double[] variation(){
      for( int i=0; i<var.length; i++){
         var[i] =  sigma[i]*g[i].next();
      }
      return var;
  }
  
  /**
   * Modifies the number in a random position for a Gaussian value with mean
   * the value encoded in the genome and sigma given as attribute
   * @param gen Genome to be modified
   * @return Index of the real modified
   */
  @Override
  public Vector<double[]> generates(double[] gen) {
      try {
          double[] genome = (double[]) Clone.get(gen);
          variation();
          for( int i=0; i<genome.length; i++ ){
              genome[i] += var[i];
          }
          Vector<double[]> v = new Vector();
          v.add(genome);
          return v;
      } catch (Exception e) {
          e.printStackTrace();
      }
      return null;
  }
}

