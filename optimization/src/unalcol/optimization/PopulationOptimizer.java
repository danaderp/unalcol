package unalcol.optimization;
import unalcol.math.function.Function;
import unalcol.optimization.solution.Solution;
import unalcol.types.collection.vector.Vector;

/**
 * <p>Title: PopulationOptimizer</p>
 *
 * <p>Description: Abstract definition of a Population based Optimization Technique.</p>
 *
 * <p>Copyright: Copyright (c) 2010</p>
 *
 * <p>Company: Kunsamu</p>
 *
 * @author Jonatan Gomez
 * @version 1.0
 */
public interface PopulationOptimizer<S> extends
        Function<OptimizationFunction<S>,Vector<Solution<S>>> {
}