/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package unalcol.random.real;

import unalcol.random.Random;
import unalcol.random.util.BooleanGenerator;

/**
 *
 * @author jgomez
 */
public class SymmetricGenerator extends DoubleGenerator {
    protected DoubleGenerator g;
    protected BooleanGenerator b = new BooleanGenerator();
    public SymmetricGenerator(DoubleGenerator _g){
        g = _g;
    }
    /**
     * Returns a random double number
     * @return A random double number
     */
    @Override
    public double next() {
        return b.next()?g.next():-g.next();
    }     
    
    public SymmetricGenerator( DoubleGenerator _g, BooleanGenerator _b ){
        g = _g;
        b = _b;
    }
    
    @Override
    public DoubleGenerator new_instance(){
        return new SymmetricGenerator(g.new_instance(), b.new_instance());
    }        
}
