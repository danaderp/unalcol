/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package unalcol.optimization.iterative;

import unalcol.descriptors.DescriptorsProvider;
import unalcol.descriptors.DescriptorsService;
import unalcol.optimization.solution.Solution;
import unalcol.optimization.solution.SolutionTransformation;

/**
 *
 * @author jgomez
 */
public class IterativeOptimizerDescriptors implements DescriptorsService{
    /**
     * Returns the Class that owns the PlugIn
     * @return Class The PlugIns owner class
     */
    @Override
    public Object owner() {
        return IterativeOptimizer.class;
    }

    /**
     * <p>Gets a numeric description of an Iterative Population Optimizer. It includes:</p>
     * <p>current iteration</p>
     * <p>Numeric description of the current population.</p>
     * <p>Numeric description of the transformation applied (if available).</p>
     * @param obj Population base iterative optimizer
     * @return Numeric description of an Iterative Population Optimizer.
     */
    @Override
    public double[] descriptors(Object obj) {
        return descriptors((IterativeOptimizer) obj);
    }

    /**
     * <p>Gets a numeric description of an Iterative Population Optimizer. It includes:</p>
     * <p>current iteration</p>
     * <p>Numeric description of the current population.</p>
     * <p>Numeric description of the transformation applied (if available).</p>
     * @param obj Population base iterative optimizer
     * @return Numeric description of an Iterative Population Optimizer.
     */
    public double[] descriptors(IterativeOptimizer optimizer) {
        Solution solution = (Solution)optimizer.output();
        DescriptorsService d = DescriptorsProvider.get(solution);
        double[] sd = d.descriptors(solution);
        int nd = sd.length;
        SolutionTransformation t = optimizer.transformation();
        d = DescriptorsProvider.get(t.getClass());
        double[] td = (d != null) ? d.descriptors(t) : new double[0];
        int nt = (td!=null)?td.length:0;
        double[] st = new double[nd + nt + 1];
        st[0] = optimizer.generation();
        for(int i = 0; i < nd; i++) {
            st[i + 1] = sd[i];
        }
        for (int i = 0; i < nt; i++) {
            st[i + nd + 1] = td[i];
        }
        return st;
    }
}
