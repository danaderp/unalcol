package unalcol.random.util;

import unalcol.random.integer.*;
//import unalcol.types.collection.vector.*;

/**
 * <p>Shuffles an array (vector) of objects</p>
 *
 * <p>Copyright: Copyright (c) 2009</p>
 *
 * <p>Company: Kunsamu</p>
 *
 * @author Jonatan Gomez Perdomo
 * @version 1.0
 */
public class Shuffle<T> {
    /**
     * Generates an array with all the integers in the interval [0,n) stored in a random fashion
     * @param n Sup limit (the generated array has <i>n</i> elements (the integer numbers in the interval [0,n))
     * @return An array with all the integers in the interval [0,n) stored in a random fashion
     */
    public int[] apply(int n) {
        int[] set = new int[n];
        for (int i = 0; i < n; i++) {
            set[i] = i;
        }
        apply(set);
        return set;
    }

    /**
     * Suffles the given array of integers
     * @param set Array of integers to be suffle
     */
    public void apply(int[] set) {
        int m = 0;
        int j, k;
        int temp;
        int n = set.length;
        UniformIntegerGenerator g = new UniformIntegerGenerator(n);
        int[] indices = g.generate(2 * n);
        for (int i = 0; i < n; i++) {
            j = indices[m];
            m++;
            k = indices[m];
            m++;
            temp = set[j];
            set[j] = set[k];
            set[k] = temp;
        }
    }

    /**
     * Suffles the given array of Objects
     * @param set Array of objects to be suffle
     */
    public void apply(T[] set) {
        int m = 0;
        int j, k;
        T temp;
        int n = set.length;
        UniformIntegerGenerator g = new UniformIntegerGenerator(n);
        int[] indices = g.generate(2 * n);
        for (int i = 0; i < n; i++) {
            j = indices[m];
            m++;
            k = indices[m];
            m++;
            temp = set[j];
            set[j] = set[k];
            set[k] = temp;
        }
    }

    /**
     * Suffles the given Objects Vector
     * @param set Vector of objects to be suffle
     */
/*    public void apply(Vector<T> set) {
        int m = 0;
        int j, k;
        T temp;
        int n = set.size();
        UniformIntegerGenerator g = new UniformIntegerGenerator(n);
        int[] indices = g.generate(2 * n);
        for (int i = 0; i < n; i++) {
            j = indices[m];
            m++;
            k = indices[m];
            m++;
            temp = set.get(j);
            set.set(j, set.get(k));
            set.set(k, temp);
        }
    }
*/
}