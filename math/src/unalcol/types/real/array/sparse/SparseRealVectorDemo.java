/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package unalcol.types.real.array.sparse;

import unalcol.reflect.util.*;
import unalcol.io.WriteService;
import unalcol.reflect.service.ServiceProvider;
import unalcol.types.real.array.DoubleArraySimpleWriteService;

/**
 *
 * @author jgomez
 */
public class SparseRealVectorDemo {
    public static void main( String[] args ){
         // Reflection
        ServiceProvider provider = ReflectUtil.getProvider("services/");
        DoubleArraySimpleWriteService key = new DoubleArraySimpleWriteService(',');
        provider.register(key);
        provider.setDefault_service(WriteService.class,double[].class,key);
       
        SparseRealVector x = new SparseRealVector(100);
        for( int i=0; i<10; i++){
            x.set(i*10, i+1);
        }
        
        System.out.println( x.values.size() );
        SparseRealVector y = new SparseRealVector(100);
        for( int i=0; i<10; i++){
            y.set(i*5, i+1);
        }
        System.out.println( y.values.size() );
        for( int i=0; i<100; i++ ){
            System.out.println(x.get(i));
        }
        
        System.out.println("######################");
        SparseRealVectorSpace add = new SparseRealVectorSpace();
        try{
            SparseRealVector z = add.plus(x, y);
            for( int i=0; i<100; i++ ){
                System.out.println(z.get(i));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        
        SparseRealVectorDotProduct product = new SparseRealVectorDotProduct();
        System.out.println("Product"+product.apply(x, y));
        
        SparseRealVectorSphereNormalization norm =new SparseRealVectorSphereNormalization();
        norm.fastApply(x);
        for( int i=0; i<100; i++ ){
            System.out.println(x.get(i));
        }
    }
    
}
