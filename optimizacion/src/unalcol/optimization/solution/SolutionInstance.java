/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package unalcol.optimization.solution;

import unalcol.instance.InstanceProvider;
import unalcol.instance.InstanceService;

/**
 *
 * @author jgomez
 */
public class SolutionInstance implements InstanceService<Solution> {
    
    public SolutionInstance(){
    }
    
    @Override
    public Solution get( Solution sol ) {
        return new Solution( InstanceProvider.get(sol.get()) );
    }

    @Override
    public Object owner() {
        return Solution.class;
    }    
}
