/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package unalcol.optimization.random;

import unalcol.optimization.solution.Solution;
import unalcol.optimization.solution.SolutionOrder;
import unalcol.optimization.solution.SolutionReplacement;
import unalcol.types.collection.vector.Vector;

/**
 *
 * @author jgomez
 */
public class SteadyState <T> extends SolutionReplacement<T>{
    protected SolutionOrder order = new SolutionOrder();
    public SteadyState() {
    }

    @Override
    public Solution<T> apply( Solution<T> solution, Vector<Solution<T>> candidates){
        Solution<T> theCandidate = Solution.best((Vector)candidates);
        if( order.compare(theCandidate, solution) >= 0 ){
            return theCandidate;
        }else{
            return solution;
        }
    }
}
