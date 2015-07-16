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
public class RealVectorScaleRepair  extends Repair<double[]>{
    protected double[] min;
    protected double[] max;
    
    public RealVectorScaleRepair( double[] _min, double[] _max ){
        min = _min;
        max = _max;
    }
    
    @Override
    public double[] repair(double[] x){
        for( int i=0; i<x.length; i++){
            if( x[i] < min[i] ){
                x[i] = min[i];
            }else{
                if( x[i] > max[i] ){
                    x[i] = max[i];
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
