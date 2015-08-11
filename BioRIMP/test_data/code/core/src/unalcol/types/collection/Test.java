package unalcol.types.collection;
//import unalcol.types.collection.vector.*;
//import java.util.Vector;
import java.util.Iterator;
import unalcol.types.collection.array.ArrayCollectionLocation;
import unalcol.types.collection.vector.Vector;

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
public class Test {
    public Test() {
    }

    public static void main( String[] args ){
        
//        System.out.println( 5 >> 2 );
//        System.out.println( (5 & 4) == 4 );
//        System.out.println( 10 << 2 );
        
      Vector<Integer> v = new Vector<>();
//      ArrayList<Integer> v = new ArrayList<>();
/*      long m, p, n;

      n  = System.nanoTime();
      for( int i=0; i<100000; i++ ){
          v.add( i );
      }
      p = System.nanoTime();
      m = p - n;
      System.out.print( m + " " );
      
      n = System.nanoTime();
      for(int i=0; i<100000; i++ ){
          v.add(i,-i);
      }
      p = System.nanoTime();
      m = p - n;
      System.out.print( m  + " " );

      n = System.nanoTime();
      for(int i=0; i<100000; i++ ){
          v.remove(i);
//          v.remove(i);
      }
      p = System.nanoTime();
      m = p - n;
      System.out.print( m );

      n = System.nanoTime();
      for(int i=0; i<99000; i++ ){
          v.remove(0);
//          v.remove(i);
      }
      p = System.nanoTime();
      m = p - n;
      System.out.println( m );

      for(int i=0; i<1000; i++ ){
          System.out.println( v.get(i));
      }
*/      
      
      Integer[] x = new Integer[50];
      for( int i=0; i<x.length; i++){
          x[i] = i;
      }
      v = new Vector<>(x);
      for( int i=0; i<10; i++ ){
          v.remove(i);
      }
      for(int i=0; i<v.size(); i++ ){
          System.out.println( v.get(i) );
      }
      
      for( Integer k:v){
          System.out.println( "##"+k );          
      }
      
      ArrayCollectionLocation<Integer> loc = new ArrayCollectionLocation<>(4,v);
      for(Iterator<Integer> iter = v.iterator(loc); iter.hasNext(); ){
          System.out.println( "#==#"+iter.next() );          
      }
      
    }  

}
