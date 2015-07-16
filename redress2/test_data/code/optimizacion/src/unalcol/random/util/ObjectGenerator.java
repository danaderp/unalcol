package unalcol.random.util;

import unalcol.random.integer.*;

/**
 * <p>A random generator of predefined objects</p>
 *
 * <p>Copyright: Copyright (c) 2009</p>
 *
 * @author Jonatan Gomez Perdomo
 * @version 1.0
 */
public class ObjectGenerator<T> {
    /**
     * Set of predefined objects that can be randomly generated
     */
    protected T[] objects;
    /**
     * Objects density function
     */
    protected IntegerGenerator g;

    /**
     * Created a random generator of predefined objects
     * @param objects Set of predefined objects that can be randomly generated
     */
    public ObjectGenerator(T[] objects) {
        this.objects = objects;
        g = new UniformIntegerGenerator(this.objects.length);
    }

    /**
     * Created a random generator of predefined objects
     * @param objects Set of predefined objects that can be randomly generated
     * @param g Objects density function
     */
    public ObjectGenerator(T[] objects, IntegerGenerator g) {
        this.objects = objects;
        this.g = g;
    }

    /**
     * Generates a predefined object following the associated objects distribution
     * @return A predefined object following the associated objects distribution
     */
    public T next() {
        return objects[g.next()];
    }

    /**
     * Returns a set of random objects
     * @param v Array where objects will be stored
     * @param m The total number of random objects to be generated
     */
    public void raw(T[] v, int m) {
        int[] index = g.generate(m);
        for (int i = 0; i < m; i++) {
            v[i] = objects[index[i]];
        }
    }

    /**
     * Returns a set of random objects
     * @param m The total number of random objects to be generated
     * @return A set of m random objects
     */
    public T[] raw(int m) {
        T[] v = null;
        if (m > 0) {
            v = (T[])new Object[m];
            raw(v, m);
        }
        return v;
    }
}
