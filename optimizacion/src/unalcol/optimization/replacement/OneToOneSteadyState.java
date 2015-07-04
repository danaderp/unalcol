/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package unalcol.optimization.replacement;

import unalcol.optimization.solution.Solution;
import unalcol.types.collection.vector.Vector;

/**
 *
 * @author jgomez
 */
public class OneToOneSteadyState<T>  extends Replacement<T>{
    public OneToOneSteadyState() {
    }

    @Override
    public Vector<Solution<T>> apply( Vector<Solution<T>> parent, Vector<Solution<T>> offspring){
        try{
            Vector<Solution<T>> candidates = new Vector();
            int n = offspring.size();
            int m = parent.size();
            int r = n<m?n:m;
            for (int i = 0; i < r; i++) {
                if( parent.get(i).value() > offspring.get(i).value() ){
                    candidates.add(parent.get(i));
                }else{
                    candidates.add(offspring.get(i));
                }
            }
            return candidates;
        }catch( Exception e ){
            e.printStackTrace();
        }
        return null;
    }
}
