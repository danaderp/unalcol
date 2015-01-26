package unalcol.optimization;
import unalcol.algorithm.Algorithm;

/**
 * <p>Title: OptimizationFunction</p>
 *
 * <p>Description: Abstract definition of an optimization function. An optimization function
 * is a map f:T -> R  where T is any set and R is the real numbers set.</p>
 *
 * <p>Copyright: Copyright (c) 2010</p>
 *
 * <p>Company: Kunsamu</p>
 *
 * @author Jonatan Gomez
 * @version 1.0
 */
public abstract class OptimizationFunction<T> extends Algorithm<T,Double>{
    /**
     * Determines if the fitness function is non-stationary, i.e., the value of
     * the function for a given value can change in time. It will help the optimizer
     * to evaluate the fitness function whenever is required
     */
    protected boolean nonStationary = false;

    /**
     * Determines if the fitness function is stationary or not, i.e.,
     * if the value of the function for a given value can change in time (non-stationary) 
     * or not (stationary)
     * @return true if the fitness function is not stationary, false if it is stationary
     */
    public boolean isNonStationary() { return nonStationary; }

    /**
     * Makes the optimization function to be non stationary, i.e., the value of
     * the function for a given value can change in time.
     */
    public void makeNonStationary(){
        nonStationary = true;
    }

    /**
     * Makes the optimization function to be stationary, i.e., the value of
     * the function for a given value cannot change in time.
     */
    public void makeStationary(){
        nonStationary = false;
    }

    /** Updates the fitness function if it is nonstationary
     * @param k Current iteration of the optimizer
     */
    public void update( int k ){}
}