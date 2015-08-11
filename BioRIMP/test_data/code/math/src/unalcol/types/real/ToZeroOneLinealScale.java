package unalcol.types.real;

import unalcol.math.algebra.InvertableScale;

public class ToZeroOneLinealScale extends InvertableScale<Double>{
    protected double min;
    protected double length;
    
    public ToZeroOneLinealScale( double min, double max ){
        this.min = min;
        length = max - min;
    }

    
    public double apply( double x ){
        return (x-min)/length;
    }
    
    public double inverse( double x ){
        return min + length*x;
    }

    @Override
    public Double fastApply( Double x ){
        return apply((double)x);
    }

    @Override
    public Double fastInverse( Double x ){
        return inverse((double)x);
    }    
}
