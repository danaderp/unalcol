/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package unalcol.optimization.testbed.real;
import unalcol.optimization.OptimizationFunction;
import unalcol.optimization.testbed.real.lsgo_benchmark.*;
/**
 *
 * @author jgomez
 */
public class LSGOFunctionWrap extends OptimizationFunction<double[]> {
    protected Function f;
    protected int evaluations = 0;
    protected int resolution;
    protected double factor = 1.0;
    protected int cres = 0;
    protected boolean multires = false;
    
    protected Function[] F = new Function[]{
        new F1(), new F2(), new F3(), new F4(), new F5(), 
        new F6(), new F7(), new F8(), new F9(), new F10(),
        new F11(), new F12(), new F13(), new F14(), new F15(), 
        new F16(), new F17(), new F18(), new F19(), new F20()
    };
    
    public LSGOFunctionWrap( int f, int res ){
        this.f = F[f-1];
        this.resolution = res;
    }
    
    public LSGOFunctionWrap( Function f, int res ){
        this.f = f;
        this.resolution = res;
    }
    
    /**
     * Evaluate the OptimizationFunction function over the real vector given
     * @param x Real vector to be evaluated
     * @return the OptimizationFunction function over the real vector
     */
    @Override
    public Double apply( double[] x ){
        evaluations++;
        if( multires ){
            int nres = evaluations / resolution;
            if( cres != nres ){
                factor *= 2.0;
                cres++;
            }

            if( cres < 30 ){
                for( int i=0; i<x.length; i++ ){
                    x[i] = Math.round(x[i]*factor)/factor;
                }
            }
        }
        return -f.compute(x);
    }
    
    /**
     * Determines if the fitness function is stationary or not, i.e.,
     * if the value of the function for a given value can change in time (non-stationary) 
     * or not (stationary)
     * @return true if the fitness function is not stationary, false if it is stationary
     */
    @Override
    public boolean isNonStationary() { return multires && (evaluations-1)%resolution==0; }
    
    
    public int dim(){ return f.getDimension(); }
    public double min(){ return f.getMin(); }
    public double max(){ return f.getMax(); }    
    
    public static void main( String[] args ){
        double x = Math.random();
        System.out.println(x);
        double res = 1.0;
        for(int i=0; i<20; i++){
            double y = Math.round(x*res)/res;
            System.out.println(res + "," + y);
            res *= 10.0;
        }
    }
}
