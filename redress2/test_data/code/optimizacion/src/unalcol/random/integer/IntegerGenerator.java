package unalcol.random.integer;


/**
 * <p>Integer random generator</p>
 *
 * <p>Copyright: Copyright (c) 2010</p>
 *
 * @author Jonatan Gomez Perdomo
 * @version 1.0
 */
public abstract class IntegerGenerator {
    /**
     * Generates an integer number
     * @return An integer number
     */
    public abstract int next();

    /**
     * Returns a set of random integer numbers
     * @param v Array where integer numbers will be stored
     * @param m The total number of integer numbers
     */
    public void generate(int[] v, int m) {
        for (int i = 0; i < m; i++) {
            v[i] = next();
        }
    }

    /**
     * Returns a set of random integer numbers
     * @param m The total number of random integer numbers
     * @return A set of m random integer numbers
     */
    public int[] generate(int m) {
        int[] v = null;
        if (m > 0) {
            v = new int[m];
            generate(v, m);
        }
        return v;
    }
}
