package unalcol.optimization;
import unalcol.optimization.solution.Solution;
import unalcol.math.function.Function;

/**
 * <p>Title: Optimizer</p>
 *
 * <p>Description: Abstract definition of an optimization algorithm</p>
 *
 * <p>Copyright: Copyright (c) 2010</p>
 *
 * <p>Company: Kunsamu</p>
 *
 * @author Jonatan Gomez
 * @version 1.0
 */
public interface Optimizer<S> extends
        Function<OptimizationFunction<S>,Solution<S>>{
}
