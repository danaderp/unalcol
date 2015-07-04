/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package unalcol.optimization.util;

import unalcol.optimization.generation.Repair;

/**
 *
 * @author jgomez
 */
public class RealVectorRepair extends Repair<double[]>{
    @Override
    public double[] repair(double[] x){
        for( int i=0; i<x.length; i++){
            if( x[i] < 0.0 ){
                x[i] = 0.0;
            }else{
                if( x[i] > 1.0 ){
                    x[i] = 1.0;
                }
            }
            //System.out.print(" " + x[i]);
        }
        //System.out.println();
        return x;
    }
    
    @Override
    public Object owner(){
        return double[].class;
    }
}
