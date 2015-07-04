/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package unalcol.types.collection.vector.sparse;

import unalcol.clone.Clone;
import unalcol.clone.CloneService;
import unalcol.types.collection.vector.SortedVector;

/**
 *
 * @author jgomez
 */
public class ImmutableSparseVectorCloneService<T> implements CloneService<ImmutableSparseVector<T>>{
    public ImmutableSparseVectorCloneService() {
    }

    public Object owner(){
        return ImmutableSparseVector.class;
    }

    /**
     * Clones a Java Vector
     * @param obj The Java Vector to be cloned
     * @return A clone of the Java Vector
     */
    @Override
    public ImmutableSparseVector<T> clone(ImmutableSparseVector<T> obj){    
        return new ImmutableSparseVector<T>( (SortedVector)Clone.get(obj.vector) );
    }    
}