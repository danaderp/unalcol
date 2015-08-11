package unalcol.optimization.solution;

import unalcol.optimization.OptimizationFunction;
import unalcol.sort.ReversedOrder;
import unalcol.types.collection.vector.Vector;
import unalcol.types.collection.vector.VectorSort;

/**
 * <p>Title: Solution</p>
 *
 * <p>Description: Abstract class representing a candidate solution of an
 * optimization problem (solution point and value)</p>
 *
 * <p>Copyright: Copyright (c) 2010</p>
 *
 * <p>Company: Kunsamu</p>
 *
 * @author Jonatan Gomez
 * @version 1.0
 */
public class Solution<T> {
    
    protected T object = null;
    
    /**
     * Value obtained by the candidate solution
     */
    protected double value = Double.NaN;

    /**
     * Function being optimized
     */
    protected OptimizationFunction<T> f = null;
    
    public Solution(){        
    }

    public Solution( T _object ){
        object = _object;
    }

    /**
     * Gets the candidate solution
     * @return Candidate object solution represented by the solution
     */
    public T get(){
        return object;
    }
    
    public Solution<T> newInstance( T object ){
        return new Solution(object);
    }

    /**
     * Gets the value obtained by the candidate solution
     * @return Value obtained by the candidate solution
     */
    public double value(){
        //if( f != null && (mutable() || f.isNonStationary()) ){
        //    value =  f.apply(get());
        //}
        return value;
    }

    public boolean mutable(){ return false; }

    /**
     * Evaluates the candidate solution using the given optimization function
     * @param f Optimization function used for evaluating the candidate solution
     * @return Value obtained by the candidate solution on the optimization function
     */
    public double evaluate( OptimizationFunction<T> f ){
        if( this.f != f || mutable() || f.isNonStationary() || value == Double.NaN ){
            this.f = f;
            value = f.apply(get());
        }
        return value;
    }

    /**
     * Gets a numeric description of the solution
     * @return Numeric description of the solution
     */
    public double[] descriptors(){
        return (new SolutionDescriptors()).descriptors(this);
    }
    
    
    public static void evaluate( Vector<Solution> v, OptimizationFunction f ){
        for( int i=0; i<v.size(); i++ ){
            v.get(i).evaluate(f);
        }
    }
    
    public static Solution best( Vector<Solution> v ){
        SolutionOrder order = new SolutionOrder();
        if( v.size() > 0 ){
            Solution s = v.get(0);
            for( int i=1; i<v.size(); i++ ){
                if( order.compare(v.get(i), s) > 0 ){
                    s = v.get(i);
                }
            }
            return s;
        }
        return null;
    }
    
    public static void sort( Vector<Solution> v ){
        VectorSort sort_alg =
                new VectorSort(new ReversedOrder( new SolutionOrder() ) );
        sort_alg.apply(v);
    }
}