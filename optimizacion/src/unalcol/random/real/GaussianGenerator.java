package unalcol.random.real;

import unalcol.random.raw.RawGenerator;

/**
 * <p>Gaussian random number generator.</p>
 * <p>Copyright: Copyright (c) 2009</p>
 * 
 * @author Jonatan Gomez Perdomo
 * @version 1.0
 */

public class GaussianGenerator extends StandardGaussianGenerator {
  /**
   * mean
   */
  double miu = 0.0;
  /**
   * standard deviation
   */
  double sigma = 1.0;

  /**
   * Constructor: Creates a Gaussian Number Generator G~(miu,sigma)
   * @param miu1 Mean
   * @param sigma1 standard deviation
   */
  public GaussianGenerator( double miu1, double sigma1 ){
      super();
    miu = miu1;
    sigma = sigma1;
  }

  /**
   * Constructor: Creates a Gaussian Number Generator G~(mu,sigma)
   * @param miu1 Mean
   * @param sigma1 standard deviation
   */
  public GaussianGenerator( double miu1, double sigma1, RawGenerator g ){
      super(g);
      miu = miu1;
      sigma = sigma1;
  }

  /**
   * Returns a random double number
   * @return A random double number
   */
  public double next() {
    return (sigma*super.next() + miu);
  }

  @Override
  public DoubleGenerator new_instance(){
      return new GaussianGenerator(miu, sigma, g.new_instance());
  }    
  
}
