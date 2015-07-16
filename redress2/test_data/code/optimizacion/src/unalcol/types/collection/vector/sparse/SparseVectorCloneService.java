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
public class SparseVectorCloneService<T> implements CloneService<SparseVector<T>>{
    public SparseVectorCloneService() {
    }

    public Object owner(){
        return SparseVector.class;
    }

    /**
     * Clones a Java Vector
     * @param obj The Java Vector to be cloned
     * @return A clone of the Java Vector
     */
    @Override
    public SparseVector<T> clone(SparseVector<T> obj){    
        return new SparseVector<T>( (SortedVector)Clone.get(obj.vector) );
    }    
}
