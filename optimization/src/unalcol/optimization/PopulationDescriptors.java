package unalcol.optimization;

import unalcol.optimization.solution.Solution;
import unalcol.types.real.matrix.*;
import unalcol.types.real.*;
import unalcol.descriptors.*;
import unalcol.types.collection.vector.*;

/**
 * <p>Title: PopulationDescriptors</p>
 *
 * <p>Description: Numeric Description of a population of candidate solutions.
 * Basically, a population is described by the index of the best candidate solution,
 * the maximum, minimum, average and standard deviation of the optimization function
 * values obtained by the candidate solutions of the population.</p>
 *
 * <p>Copyright: Copyright (c) 2010</p>
 *
 * <p>Company: Kunsamu</p>
 *
 * @author Jonatan Gomez
 * @version 1.0
 */
public class PopulationDescriptors implements DescriptorsService{
    /**
     * Returns the Class that owns the PlugIn
     * @return Class The PlugIns owner class
     */
    @Override
    public Object owner() {
        return PopulationDescriptors.class;
    }

    /**
     * Gets a numeric Description of a population of candidate solutions.
     * @param stat Statistical information of the population
     * @return Numeric Description of a population of candidate solutions.
     * Basically, a population is described by the index of the best candidate solution,
     * the maximum, minimum, average and standard deviation of the optimization function
     * values obtained by the candidate solutions of the population.
     */
    public double[] apply(Statistics[] stat) {
        double[] s = new double[5];
        // Fitness stats
        s[0] = stat[0].maxIndex;
        s[1] = stat[0].max;
        s[2] = stat[0].min;
        s[3] = stat[0].avg;
        s[4] = stat[0].deviation;
        return s;
    }

    /**
     * Gets a numeric Description of a population of candidate solutions.
     * @param obj Population to be analized
     * @return Numeric Description of a population of candidate solutions.
     * Basically, a population is described by the index of the best candidate solution,
     * the maximum, minimum, average and standard deviation of the optimization function
     * values obtained by the candidate solutions of the population.
     */
    public double[] descriptors(Object obj) {
        return descriptors((Vector<Solution>) obj);
    }

    /**
     * Gets a numeric Description of a population of candidate solutions.
     * @param pop Population to be analized
     * @return Numeric Description of a population of candidate solutions.
     * Basically, a population is described by the index of the best candidate solution,
     * the maximum, minimum, average and standard deviation of the optimization function
     * values obtained by the candidate solutions of the population.
     */
    public double[] descriptors(Vector<Solution> pop) {
        DescriptorsService descriptors = DescriptorsProvider.get(pop.get(0).getClass());
        int n = pop.size();
        double[][] indStat = new double[n][];
        for (int i = 0; i < n; i++) {
            indStat[i] = descriptors.descriptors(pop.get(i));
        }
        return apply(DoubleMatrixUtil.statistics(indStat, true));
    }
}