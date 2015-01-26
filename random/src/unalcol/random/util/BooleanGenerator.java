package unalcol.random.util;

import unalcol.random.Random;
import unalcol.random.raw.RawGenerator;
import unalcol.random.rngpack.RanMT;

/**
 * <p>Generates boolean values with a given probability</p>
 *
 * <p>Copyright: Copyright (c) 2009</p>
 *
 * @author Jonatan Gomez Perdomo
 * @version 1.0
 */
public class BooleanGenerator {
    
    protected RawGenerator g;
    
    /**
     * Probability of generating a <i>false</i> value
     */
    protected double falseProbability = 0.5;

    /**
     * Creates a boolean generator with the same probability of generating a <i>true</i> and <i>false<i> value
     */
    public BooleanGenerator(){
        g = Random.get();
    }

    /**
     * Creates a boolean generator with the given probability of generating a <i>false</i> value (1.0-falseProbability) is
     * the probability of generating a <i>true</i> value
     * @param falseProbability Probability of generating a <i>false</i> value
     */
    public BooleanGenerator(double falseProbability) {
        this.falseProbability = falseProbability;
        g = Random.get();
    }

    /**
     * Creates a boolean generator with the given probability of generating a <i>false</i> value (1.0-falseProbability) is
     * the probability of generating a <i>true</i> value
     * @param falseProbability Probability of generating a <i>false</i> value
     */
    public BooleanGenerator(double falseProbability, RawGenerator _g) {
        g = _g;
        this.falseProbability = falseProbability;
    }

    /**
     * Produces a boolean value according to the stored probability distribution
     * @return A boolean value according to the stored probability distribution
     */
    public boolean next() {
        return (g.next()>=falseProbability);
    }

    /**
     * Returns a set of random boolean values
     * @param v Array where boolean values will be stored
     * @param m The total number of random boolean values
     */
    public void generate(boolean[] v, int m) {
      for (int i = 0; i < m; i++) {
        v[i] = this.next();
      }
    }

    /**
     * Returns a set of random boolean values
     * @param m The total number of random boolean values
     * @return A set of m random boolean values
     */
    public boolean[] generate(int m) {
      boolean[] v = null;
      if (m > 0) {
        v = new boolean[m];
        generate( v, m );
      }
      return v;
    }
    
    public BooleanGenerator new_instance(){
        return new BooleanGenerator(falseProbability, g.new_instance());
    }
}