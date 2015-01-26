/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package unalcol.optimization.generation;

import unalcol.optimization.solution.*;
import unalcol.reflect.service.*;
import unalcol.types.collection.vector.*;

/**
 *
 * @author jgomez
 */
public interface PopulationGeneration<T> extends Service {
    public Vector<Solution<T>> apply( Vector<Solution<T>> population );    
}
