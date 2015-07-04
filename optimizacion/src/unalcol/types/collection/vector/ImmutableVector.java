package unalcol.types.collection.vector;

import java.util.Iterator;
import unalcol.types.collection.array.*;
import unalcol.types.collection.*;

import java.util.NoSuchElementException;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2009</p>
 *
 * <p>Company: Kunsamu</p>
 *
 * @author Jonatan Gomez Perdomo
 * @version 1.0
 */
public class ImmutableVector<T> implements ArrayCollection<T>, SearchCollection<T> {
    protected int size;
    protected T[] buffer;
    public ImmutableVector( T[] buffer ) {
        this.buffer = buffer;
        size = (buffer!=null)?buffer.length:0;
    }

    @Override
    public T get( int index ) throws IndexOutOfBoundsException{
        return buffer[index];
    }

    @Override
    public int size(){
        return size;
    }

    @Override
    public boolean isEmpty(){
        return (size==0);
    }

    @Override
    public Iterator<T> iterator(){
        return new ArrayCollectionIterator( 0, this );
    }

    @Override
    public Iterator<T> iterator( Location<T> locator ){
        if( locator instanceof ArrayCollectionLocation ){
            return new ArrayCollectionIterator( (ArrayCollectionLocation)locator );
        }
        return null;
    }

    public int findIndex( T data ){
        int k=0;
        while( k<size && !data.equals(this.buffer[k]) ){ k++; }
        return (k==size)?-1:k;
    }

    /**
     * Locates the given object in the structure
     * @param data Data object to be located
     * @return A data iterator starting at the given object (when the next method is called),
     * If the element is not in the data strucuture the hasNext method will return an exception
     */
    @Override
    public Location<T> find(T data){
        return new ArrayCollectionLocation( findIndex(data), this );
    }

    /**
     * Determines if the given object belongs to the structure
     * @param data Data object to be located
     * @return <i>true</i>If the objects belongs to the structure, <i>false>otherwise</i>
     */
    @Override
    public boolean contains( T data ){
       try{
           find(data).get();
           return true;
       }catch( NoSuchElementException e ){
           return false;
       }
    }
}
