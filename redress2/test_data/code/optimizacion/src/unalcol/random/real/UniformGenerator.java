package unalcol.random.real;

import unalcol.random.raw.RawGenerator;

/**
 * <p>Simple uniform random number generator.</p>
 *
 * <p>Copyright: Copyright (c) 2009</p>
 * 
 * @author Jonatan Gomez Perdomo
 * @version 1.0
 *
 */

public class UniformGenerator extends InverseDoubleGenerator {

    /**
     * The inferior range of the random number generator interval
     */
    protected double min = 0.0;

    /**
     * The length of the range
     */
    protected double length = 1.0;

    /**
     * Constructor: Creates a uniform random number generator that generates numbers in the interval [minVal, maxVal)
     * @param minVal Inf limit
     * @param maxVal Sup Limit
     */
    public UniformGenerator(double minVal, double maxVal) {
        super();
        min = minVal;
        length = maxVal - minVal;
    }

    /**
     * Constructor: Creates a uniform random number generator that generates numbers in the interval [minVal, maxVal)
     * @param minVal Inf limit
     * @param maxVal Sup Limit
     */
    public UniformGenerator(double minVal, double maxVal, RawGenerator g ) {
        super(g);
        min = minVal;
        length = maxVal - minVal;
    }

    /**
     * Returns a random double number
     * @param x Inverse value (cumulative probability)
     * @return A random double number
     */
    public double next(double x) {
        return (min + length * x);
    }
    
  @Override
  public DoubleGenerator new_instance(){
      return new UniformGenerator(min, length+min, g.new_instance());
  }    
    
}
