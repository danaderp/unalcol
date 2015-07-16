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
public class StandardGeneralizedPowerLawGenerator extends StandardPowerLawGenerator{
    protected double coarse_b=1.0;
    public StandardGeneralizedPowerLawGenerator( double alpha ){
      super(alpha);
    }
    
    public StandardGeneralizedPowerLawGenerator( double alpha, RawGenerator g ){
      super(alpha, g);
    }
    
    @Override
    public double next(double x){
        return coarse_b * super.next(x) - coarse_b;
    }    
    
    @Override
    public DoubleGenerator new_instance(){
        return new StandardGeneralizedPowerLawGenerator(1.0-1.0/coarse_alpha, g.new_instance());
    }        
    
}

