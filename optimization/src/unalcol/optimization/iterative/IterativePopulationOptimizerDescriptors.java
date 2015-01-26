package unalcol.optimization.iterative;
import unalcol.descriptors.*;
import unalcol.optimization.PopulationDescriptors;
import unalcol.optimization.solution.Solution;
import unalcol.optimization.transformation.Transformation;
import unalcol.types.collection.vector.Vector;


/**
 * <p>Title: IterativePopulationOptimizerDescriptors</p>
 *
 * <p>Description: Numeric Description of an iterative population based optimizer. It includes:</p>
 * <p> * current iteration</p>
 * <p> * Numeric description of the current population.</p>
 * <p> * Numeric description of the transformation applied (if available).</p>
 *
 * <p>Copyright: Copyright (c) 2010</p>
 *
 * <p>Company: Kunsamu</p>
 *
 * @author Jonatan Gomez
 * @version 1.0
 */
public class IterativePopulationOptimizerDescriptors implements DescriptorsService{
    /**
     * Returns the Class that owns the PlugIn
     * @return Class The PlugIns owner class
     */
    public Object owner() {
        return IterativePopulationOptimizer.class;
    }

    /**
     * <p>Gets a numeric description of an Iterative Population Optimizer. It includes:</p>
     * <p>current iteration</p>
     * <p>Numeric description of the current population.</p>
     * <p>Numeric description of the transformation applied (if available).</p>
     * @param obj Population base iterative optimizer
     * @return Numeric description of an Iterative Population Optimizer.
     */
    public double[] descriptors(Object obj) {
        return descriptors((IterativePopulationOptimizer) obj);
    }

    /**
     * <p>Gets a numeric description of an Iterative Population Optimizer. It includes:</p>
     * <p>current iteration</p>
     * <p>Numeric description of the current population.</p>
     * <p>Numeric description of the transformation applied (if available).</p>
     * @param obj Population base iterative optimizer
     * @return Numeric description of an Iterative Population Optimizer.
     */
    public double[] descriptors(IterativePopulationOptimizer ea) {
        Vector<Solution> p = (Vector<Solution>)ea.output();
        DescriptorsService d = DescriptorsProvider.get(PopulationDescriptors.class);
        double[] pd = d.descriptors(p);
        int np = pd.length;
        Transformation t = ea.transformation();
        d = DescriptorsProvider.get(t.getClass());
        double[] td = (d != null) ? d.descriptors(t) : new double[0];
        int nt = (td!=null)?td.length:0;
        double[] st = new double[np + nt + 1];
        st[0] = ea.generation();
        for(int i = 0; i < np; i++) {
            st[i + 1] = pd[i];
        }
        for (int i = 0; i < nt; i++) {
            st[i + np + 1] = td[i];
        }
        return st;
    }
}