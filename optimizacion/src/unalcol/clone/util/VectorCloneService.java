/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package unalcol.clone.util;

import unalcol.clone.*;
import unalcol.types.collection.vector.*;

/**
 *
 * @author jgomez
 */
public class VectorCloneService<T> implements CloneService<Vector<T>>{
    public VectorCloneService() {
    }

    @Override
    public Object owner(){
        return Vector.class;
    }

    /**
     * Clones a Vector
     * @return The vector's clone
     */
    @Override
    public Vector<T> clone(Vector<T> obj){
        return new Vector( (T[])ImmutableVectorCloneService.toArray(obj), obj.size() );
    }
}
