package unalcol.types.real.array;
import unalcol.descriptors.*;

/**
 * <p>Descriptor Service for double arrays</p>
 *
 * <p>Copyright: Copyright (c) 2010</p>
 *
 * @author Jonatan Gomez Perdomo
 * @version 1.0
 *
 */
public class DoubleArrayDescriptors implements DescriptorsService{
    /**
     * Returns the Class that owns the PlugIn
     * @return Class The PlugIns owner class
     */
    public Object owner() {
        return double[].class;
    }

    /**
     * Gets a numeric description of the given object (the object should be of class Solution)
     * @param obj Object to be described using numeric values
     * @return Numeric description of the given object (the object should be of class Solution)
     */
    public double[] descriptors(Object obj) {
        return descriptors((double[]) obj);
    }

    /**
     * Gets a numeric description of the given numeric values (this method produces just a clone)
     * @param ind Numeric values to be described using numeric values
     * @return Numeric description of the given numeric values (just creates a clone)
     */
    public double[] descriptors(double[] ind) {
        return ind.clone();
    }
}