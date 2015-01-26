package unalcol.optimization;

import unalcol.optimization.solution.Solution;
import unalcol.optimization.selection.Selection;
import unalcol.types.collection.vector.Vector;

/**
 * <p>Title: PopulationToSingleOptimizer</p>
 *
 * <p>Description: PopulationOptimizer to Optimizer Converter</p>
 *
 * <p>Copyright: Copyright (c) 2010</p>
 *
 * <p>Company: Kunsamu</p>
 *
 * @author Jonatan Gomez
 * @version 1.0
 */
public class PopulationToSingleOptimizer<S> implements Optimizer<S>{
    /**
     * Inner population optimizer
     */
    protected PopulationOptimizer<S> optimizer;

    /**
     * Final candidate solution selection method (takes one candidate solution from the population)
     */
    protected Selection<S> selection;

    /**
     * Creates a PopulationOptimizer to Optimizer Converter
     * @param optimizer Population optimizer
     * @param selection Final candidate solution selection method
     */
    PopulationToSingleOptimizer( PopulationOptimizer<S> optimizer,
                         Selection<S> selection ){
        this.optimizer = optimizer;
        this.selection = selection;
    }

    /**
     * Tries to optimize the given function
     * @param f Function to be optimized
     * @return A candidate solution for the optimization problem
     */
    @Override
    public Solution<S> apply( OptimizationFunction<S> f ){
        Vector<Solution<S>> pop = optimizer.apply(f);
        return selection.choose_one(pop);
    }
}