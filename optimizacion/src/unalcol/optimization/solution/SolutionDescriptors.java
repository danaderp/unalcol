package unalcol.optimization.solution;
import unalcol.descriptors.*;

/**
 * <p>Title: SolutionDescriptors</p>
 *
 * <p>Description: Numeric description of a candidate solution. Basically returns
 * the value obtained by the candidate solution on the Optimization function.</p>
 *
 * <p>Copyright: Copyright (c) 2010</p>
 *
 * <p>Company: Kunsamu</p>
 *
 * @author Jonatan Gomez
 * @version 1.0
 */
public class SolutionDescriptors implements DescriptorsService{

    /**
     * Returns the Class that owns the PlugIn
     * @return Class The PlugIns owner class
     */
    public Object owner() {
        return Solution.class;
    }

    /**
     * Gets a numeric description of the given object (the object should be of class Solution)
     * @param obj Object to be described using numeric values
     * @return Numeric description of the given object (the object should be of class Solution)
     */
    public double[] descriptors(Object obj) {
        return descriptors((Solution) obj);
    }

    /**
     * Gets a numeric description of the given candidate solution
     * @param obj Candidate Solution to be described using numeric values
     * @return Numeric description of the given object (the object should be of class Solution)
     */
    public double[] descriptors(Solution ind) {
        double[] s = new double[1];
        s[0] = ind.value();
        return s;
    }
}