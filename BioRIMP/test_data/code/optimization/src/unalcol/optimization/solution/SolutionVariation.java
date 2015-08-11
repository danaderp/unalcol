/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package unalcol.optimization.solution;

import unalcol.optimization.solution.Solution;
import unalcol.reflect.service.Service;
import unalcol.types.collection.vector.Vector;

/**
 *
 * @author jgomez
 */
public abstract class SolutionVariation<T> implements Service {
    public abstract Vector<Solution<T>> apply( Solution<T> solution );    
}
