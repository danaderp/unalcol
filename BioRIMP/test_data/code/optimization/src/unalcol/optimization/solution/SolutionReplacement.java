/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package unalcol.optimization.solution;

import unalcol.reflect.service.Service;
import unalcol.types.collection.vector.Vector;

/**
 *
 * @author jgomez
 */
public abstract class SolutionReplacement<T> implements Service{

    /**
     * Obtains the population represented by the selected individuals (from parents and offsprings)
     * @return Next population generation
     */
    public abstract Solution<T> apply(Solution<T> solution, 
                                       Vector<Solution<T>> candidates);

    @Override
    public Object owner(){
        return Object.class;
    }
}
