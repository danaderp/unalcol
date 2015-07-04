package unalcol.random.real;

import unalcol.random.raw.RawGenerator;
import unalcol.random.rngpack.RanMT;

/**
 * <p>Gaussian random number generator.</p>
 *
 * <p>Copyright: Copyright (c) 2009</p>
 * 
 * @author Jonatan Gomez Perdomo
 * @version 1.0
 */

public class StandardGaussianGenerator extends DoubleGenerator{
    
    protected RawGenerator g;
    /**
     * Creates a standard Gaussian number generator
     */
    public StandardGaussianGenerator() {
        g = new RanMT();
    }

    /**
     * Creates a standard Gaussian number generator
     */
    public StandardGaussianGenerator( RawGenerator _g ) {
        g = _g;
    }

    /**
     * Returns a random double number following the standard Gaussian distribution
     * @param x Inverse value (cumulative probability)
     * @return A random double number
     */
    @Override
    public double next() {
        double x,y;
        double r;
        do {
            x = 2.0 * g.next() - 1.0;
            y = 2.0 * g.next() - 1.0;
            r = x * x + y * y;
        } while (r >= 1.0);

        double z = Math.sqrt( -2.0 * Math.log(r) / r);
        return (y * z);
    }
    
    @Override
    public DoubleGenerator new_instance(){
        return new StandardGaussianGenerator(g.new_instance());
    }    
}
