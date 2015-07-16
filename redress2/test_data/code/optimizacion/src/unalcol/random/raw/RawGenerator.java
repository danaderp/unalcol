package unalcol.random.raw;

/**
 * <p>Uniform [0.0,1.0) random number generator</p>
 *
 * <p>Copyright: Copyright (c) 2009</p>
 *
 * @author Jonatan Gomez Perdomo
 * @version 1.0
 */
public abstract class RawGenerator {
    /**
     * Generates a random number in the interval [0.0,1.0)
     * @return a random number in the interval [0.0,1.0)
     */
    public abstract double next();

    /**
     * Returns a set of random double numbers
     * @param m The total number of random numbers
     */
    public void raw(double[] v, int m) {
        for (int i = 0; i < m; i++) {
            v[i] = next();
        }
    }

    /**
     * Returns a set of random double numbers
     * @param m The total number of random numbers
     * @return A set of m random double numbers
     */
    public double[] raw(int m) {
        double[] v = null;
        if (m > 0) {
            v = new double[m];
            raw(v, m);
        }
        return v;
    }
    
    public abstract RawGenerator new_instance();
}
