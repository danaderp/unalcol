/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package unalcol.types.collection.vector.sparse;

import unalcol.types.collection.vector.SortedVector;

/**
 *
 * @author jgomez
 */
public class SparseVector<T> extends ImmutableSparseVector<T>{
    
    public SparseVector(){
        super( new SortedVector(new SparseElementOrder()) );
    }
    
    public SparseVector( SortedVector<SparseElement<T>> vector ){
        super( vector );
    }
    
    public boolean set(int index, T data) {
        loc.index = index;
        index = vector.findIndex(loc);
        if( index >= 0 ){
            vector.get(index).setValue(data);
        }else{
            vector.add(new SparseElement<>(loc.index, data));
        }
        return true;
    }

    public void clear() {
        vector.clear();
    }

    public boolean del(int index) {
        loc.index = index;
        return vector.del(loc);
    }    
}
