package unalcol.types.collection.vector;
import unalcol.types.collection.array.*;
import unalcol.types.collection.*;
import unalcol.random.integer.*;

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
//61, 39, 22, 17, 5
public class YVector<T> extends ImmutableVector<T> implements MutableArrayCollection<T> {
    protected double alpha = 1.61803;
    protected double alphaBufferSize = 0;

    protected static final int DEFAULT_SIZE = 100;

    public YVector( T[] buffer ){
        super( buffer );
        size = buffer.length;
        alphaBufferSize = this.alpha * ((buffer.length>1)?buffer.length:2);
    }

    public YVector( T[] buffer, int s ){
        super( buffer );
        size = s;
        alphaBufferSize = this.alpha * ((buffer.length>1)?buffer.length:2);
    }

    public YVector(){
        super( (T[])new Object[DEFAULT_SIZE] );
        size = 0;
        alphaBufferSize = this.alpha * DEFAULT_SIZE;
    }

    @Override
    public void clear(){
        size = 0;
        buffer=(T[])new Object[DEFAULT_SIZE];
        alphaBufferSize = this.alpha * this.buffer.length;
    }


    /**
     * Inserts a data element in the structure
     * @param data Data element to be inserted
     * @return <i>true</i> if the element could be added, <i>false</i> otherwise
     */
    @Override
    public boolean add(T data){
        if( this.buffer.length == this.size ){
            T[] newData = (T[])new Object[(int)this.alphaBufferSize];
            System.arraycopy( this.buffer, 0, newData, 0, this.size );
            this.buffer = newData;
            this.alphaBufferSize = this.alpha * this.buffer.length;
        }
        this.buffer[this.size] = data;
        this.size++;
        return true;
    }

    /**
     * Inserts a data element in the structure
     * @param data Data element to be inserted
     * @return <i>true</i> if the element could be added, <i>false</i> otherwise
     */
    @Override
    public boolean del(T data) {
        int k = findIndex( data );
        if( k != -1 ){
            leftShift(k);
            return true;
        }
        return false;
    }

    /**
     * Removes the element indicated by the locator
     * @param locator The location information of the object to be deleted
     * @return <i>true</i> if the element could be removed, <i>false</i> otherwise
     */
    @Override
    public boolean del( Location<T> locator ){
        if( locator instanceof ArrayCollectionLocation ){
            ArrayCollectionLocation<T> loc = ((ArrayCollectionLocation)locator);
            leftShift( loc.getPos() );
            return true;
        }
        return false;
    }

    protected void leftShift( int index ) throws IndexOutOfBoundsException{
        this.size--;
        if( this.buffer.length > DEFAULT_SIZE &&
            this.buffer.length + this.alpha * this.size < this.alphaBufferSize ){
            T[] newData = (T[])new Object[this.buffer.length>>1];
            System.arraycopy(this.buffer, 0, newData, 0, index );
            System.arraycopy(this.buffer, index+1, newData, index, this.size-index );
            this.buffer = newData;
            this.alphaBufferSize = this.alpha * this.buffer.length;
        }else{
            System.arraycopy(this.buffer, index+1, this.buffer, index, this.size-index );
        }
    }

    protected void rightShift( int index ) throws IndexOutOfBoundsException{
        T[] newData = this.buffer;
        if( this.buffer.length == this.size ){
            newData = (T[])new Object[(int)this.alphaBufferSize];
            System.arraycopy( buffer, 0, newData, 0, index );
            this.alphaBufferSize = this.alpha * newData.length;
        }
        System.arraycopy(buffer, index, newData, index+1, this.size-index);
        this.buffer = newData;
        this.size++;
    }


    @Override
    public boolean set( int index, T data ) throws IndexOutOfBoundsException{
        if( 0 <= index && index < size ){
            this.buffer[index] = data;
            return true;
        }else{
            throw new ArrayIndexOutOfBoundsException( index );
        }
    }

    @Override
    public boolean add( int index, T data ) throws IndexOutOfBoundsException{
            rightShift(index);
            this.buffer[index] = data;
            return true;
    }

    @Override
    public boolean remove( int index ) throws IndexOutOfBoundsException{
            leftShift(index);
            return true;
    }

    public void shuffle(){
        int m = 0;
        int j, k;
        T temp;
        int n = size();
        UniformIntegerGenerator g = new UniformIntegerGenerator(n);
        int[] indices = g.generate(2 * n);
        for (int i = 0; i < n; i++) {
            j = indices[m];
            m++;
            k = indices[m];
            m++;
            temp = buffer[j];
            buffer[j] = buffer[k];
            buffer[k] = temp;
        }
    }
        
}
