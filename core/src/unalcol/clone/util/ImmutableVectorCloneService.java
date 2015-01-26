package unalcol.clone.util;
import unalcol.clone.Clone;
import unalcol.clone.CloneService;
import unalcol.types.collection.vector.ImmutableVector;

/**
 * <p>CloneService of Java Vectors.</p>
 * <p>Copyright: Copyright (c) 2010</p>
 *
 * @author Jonatan Gomez Perdomo
 * @version 1.0
 */
public class ImmutableVectorCloneService<T> implements CloneService<ImmutableVector<T>>{
    public ImmutableVectorCloneService() {
    }

    @Override
    public Object owner(){
        return ImmutableVector.class;
    }

    public static Object[] toArray(ImmutableVector obj){
        int size = obj.size();
        Object[] cl = new Object[size];
        for(int i=0; i<size; i++ ){
            cl[i] = Clone.get(obj.get(i));
        }
        return cl;
    }

    /**
     * Clones a Java Vector
     * @param obj The Java Vector to be cloned
     * @return A clone of the Java Vector
     */
    @Override
    public ImmutableVector<T> clone(ImmutableVector<T> obj){    
        return new ImmutableVector<T>( (T[])toArray(obj) );
    }    
}