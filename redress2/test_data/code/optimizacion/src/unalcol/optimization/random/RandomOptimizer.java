/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package unalcol.optimization.random;

import unalcol.optimization.operators.ArityOne;
import unalcol.optimization.solution.SolutionTransformation;

/**
 *
 * @author jgomez
 */
public class RandomOptimizer<T> extends SolutionTransformation<T> {
    public RandomOptimizer( ArityOne<T> mutation ){
        super( new RandomSolutionVariation( mutation),
               new SteadyState() );
    }
}
