/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package unalcol.optimization.util;
import unalcol.optimization.generation.RandomCandidatesGeneration;
import unalcol.optimization.transformation.Transformation;
import unalcol.optimization.replacement.*;
import unalcol.optimization.operators.*;
/**
 *
 * @author jgomez
 */
public class HillClimbing<T> extends Transformation<T>{
    public HillClimbing( ArityOne<T> mutation ){
        super( new RandomCandidatesGeneration(mutation), new OneToOneSteadyState());
    }
}

