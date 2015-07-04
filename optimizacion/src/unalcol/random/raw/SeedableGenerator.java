package unalcol.random.raw;

import java.util.*;

/**
 * <p>Abstract pseudo random number generator.</p>
 *
 * <p>Copyright: Copyright (c) 2010</p>
 * 
 * @author Jonatan Gomez Perdomo, based on the RangPack definition of seedable
 * @version 1.0
 *
 */
public abstract class SeedableGenerator extends RawGenerator {

    /**
     * Generators Seed
     */
    protected long seed;

    /**
     * Creates a seedable generator with a time dependent seed (using the current time)
     */
    public SeedableGenerator() {
        this.initSeed();
    }

    /**
     * Creates a seedable generator with the given time dependent seed
     * @param seed The time information used for defining the seed
     */
    public SeedableGenerator(Date seed) {
        this.initSeed(seed.getTime());
    }

    /**
     * Creates a seedable generator with the given seed
     * @param seed The seed
     */
    public SeedableGenerator(long seed) {
        this.initSeed(seed);
    }

    /**
     *
     * Returns a seed calculated from the date.
     * @return a long integer seed
     *
     */
    public long initSeed() {
        return this.initSeed((new Date()).getTime());
    }

    /**
     *
     * Returns a seed calculated from the date.
     * @param seed Time information used for determining the seed
     * @return a long integer seed
     *
     */
    public long initSeed(Date seed) {
        return this.initSeed(seed.getTime());
    }

    /**
     *
     * Returns a seed calculated from given seed
     * @param seed The seed
     * @return a long integer seed
     *
     */
    public long initSeed(long seed) {
        this.seed = seed;
        return seed;
    }

    /**
     * Returns the seed used by the generator
     * @return Seed used by the generator
     */
    public long getSeed() {
        return seed;
    }
}
