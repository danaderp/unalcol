/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package unalcol.optimization.operators.real;

import unalcol.random.real.DoubleGenerator;
import unalcol.types.real.array.DoubleArrayInit;

/**
 *
 * @author jgomez
 */
public class RotatedEllipticMutation  extends EllipticMutation{
    double[][] M;
  /**
   * Creates a Gaussian Mutation with the given standard deviation per component
   * @param _sigma Standard deviation per component
   */
  public RotatedEllipticMutation( double[][] _M, double[] _sigma, DoubleGenerator _g ) {
    super( _sigma, _g );
    M = _M;
  }

  /**
   * Creates a Gaussian Mutation with the given standard deviation per component
   * @param _sigma Standard deviation per component
   */
  public RotatedEllipticMutation( double[][] _M, double _sigma, DoubleGenerator _g ) {
    super( _M.length, _sigma, _g );
    M = _M;
  }    
  
  @Override
  public double[] variation(){
      super.variation();
      double[] y = var.clone();
      for( int i=0; i<var.length; i++ ){
          var[i] = 0.0;
          for( int j=0; j<sigma.length; j++){
             var[i] += M[i][j] * y[j];
          }           
      }
      return var;
  }
  
}
