package unalcol.random.raw;

/**
 * <p>The Java raw generator</p>
 *
 * <p>Copyright: Copyright (c) 2009</p>
 *
 * @author Jonatan Gomez Perdomo
 * @version 1.0
 */
public class JavaGenerator extends RawGenerator {
    /**
     * Generates a random number in the interval [0.0,1.0)
     * @return a random number in the interval [0.0,1.0)
     */
    public double next() {
        return Math.random();
    }
    
    @Override
    public RawGenerator new_instance(){
        return this;
    }
}