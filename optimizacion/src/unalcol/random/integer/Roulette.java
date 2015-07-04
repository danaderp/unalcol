package unalcol.random.integer;

import unalcol.random.*;

/**
 * <p>Generates integer numbers following a Weighted probability density (Roulette)</p>
 *
 * <p>Copyright: Copyright (c) 2009</p>
 *
 * @author Jonatan Gomez Perdomo
 * @version 1.0
 */
public class Roulette extends IntegerGenerator {
    /**
     * Probability of generating an integer number [0,length(density))
     */
    protected double[] density;

    /**
     * Creates an integer number generator with the given probability density
     * @param density Probability of generating an integer number [0,length(density))
     */
    public Roulette(double[] density) {
        this.density = density;
    }

    /**
     * Generates an integer number following the associated density function
     * @return An integer number following the associated density function
     */
    public int next() {
        int length = density.length;
        double x = Random.random();
        int i = 0;
        while (i < length && x >= density[i]) {
            x -= density[i];
            i++;
        }
        return i;
    }

    /**
     * Defines the density function of the generated integers
     * @param density Probability of generating an integer number [0,length(density))
     */
    public void setDensity(double[] density) {
        this.density = density;
    }
}
