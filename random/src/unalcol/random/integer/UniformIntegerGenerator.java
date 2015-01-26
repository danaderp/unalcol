package unalcol.random.integer;

import unalcol.random.*;

/**
 * <p>Integer random generator with uniform distribution</p>
 *
 * <p>Copyright: Copyright (c) 2009</p>
 *
 * @author Jonatan Gomez Perdomo
 * @version 1.0
 */
public class UniformIntegerGenerator extends IntegerGenerator {
    /**
     * Inf Limit
     */
    protected int min;
    /**
     * Interval Length
     */
    protected int length;

    /**
     * Creates a uniform integer number generator in the interval [0,max)
     * @param max Sup Limit
     */
    public UniformIntegerGenerator(int max) {
        this.min = 0;
        this.length = max;
    }

    /**
     * Creates a uniform integer number generator in the interval [min,max)
     * @param min Inf limit
     * @param max Sup limit
     */
    public UniformIntegerGenerator(int min, int max) {
        this.min = min;
        this.length = max - min;
    }

    /**
     * Generates a uniform integer number in the interval [min,max)
     * @return A uniform integer number in the interval [min,max)
     */
    public int next() {
        return (min + Random.nextInt(length));
    }
}
