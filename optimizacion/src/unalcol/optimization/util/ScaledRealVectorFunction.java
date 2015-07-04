/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package unalcol.optimization.util;

import unalcol.optimization.OptimizationFunction;
import unalcol.types.real.array.RealVectorLinealScale;
/**
 *
 * @author jgomez
 */
public class ScaledRealVectorFunction extends OptimizationFunction<double[]> {
    protected RealVectorLinealScale scale;
    protected OptimizationFunction<double[]> f;
    
    public ScaledRealVectorFunction( double[] min, double[] max, 
            OptimizationFunction<double[]> _f ){
        scale = new RealVectorLinealScale(min, max);
        f = _f;
    }
    
    public ScaledRealVectorFunction( RealVectorLinealScale _scale, 
            OptimizationFunction<double[]> _f ){
        scale = _scale;
        f = _f;
    }
    
    @Override
    public Double apply( double[] x ){
/*        if( Math.random() < 0.1 ){
            System.out.println(Persistency.toString(x));
            System.out.println("****"+Persistency.toString(scale.inverse(x)));
        } */
        return f.apply(scale.inverse(x));
    }
}
