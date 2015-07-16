/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package unalcol.random.real;

import unalcol.random.raw.RawGenerator;

/**
 *
 * @author jgomez
 */
public class PowerLawGenerator extends StandardPowerLawGenerator{
    double x_min = 1.0;
    
    public PowerLawGenerator( double _alpha, double _x_min ){
        super( _alpha );
        x_min = _x_min;
    }
    
    public PowerLawGenerator( double _alpha, double _x_min, RawGenerator g ){
        super( _alpha, g );
        x_min = _x_min;
    }
    
    @Override
    public double next(double x){
        return x_min*super.next(x);
    }    
    
    @Override
    public DoubleGenerator new_instance(){
        return new PowerLawGenerator(1.0-1.0/coarse_alpha, x_min, g.new_instance());
    }        
    
}
