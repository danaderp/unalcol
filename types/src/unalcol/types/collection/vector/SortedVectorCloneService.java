/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package unalcol.types.collection.vector;

import unalcol.clone.Clone;
import unalcol.clone.CloneService;
import unalcol.types.collection.vector.sparse.SparseVector;

/**
 *
 * @author jgomez
 */
public class SortedVectorCloneService<T> implements CloneService<SortedVector<T>>{
    public SortedVectorCloneService() {
    }

    public Object owner(){
        return SortedVector.class;
    }

    /**
     * Clones a Java Vector
     * @param obj The Java Vector to be cloned
     * @return A clone of the Java Vector
     */
    @Override
    public SortedVector<T> clone(SortedVector<T> obj){    
        return new SortedVector<T>( (T[])Clone.get(obj.buffer), obj.size(), obj.order );
    }    

}
