package unalcol.optimization.replacement;
import unalcol.optimization.solution.Solution;
import unalcol.types.collection.vector.*;
import unalcol.optimization.*;
import unalcol.clone.*;

/**
 * <p>Title: SteadeState</p>
 *
 * <p>Description: Steady State replacement strategy</p>
 *
 * <p>Copyright: Copyright (c) 2010</p>
 *
 * @author Jonatan Gomez
 * @version 1.0
 */
public class SteadyState<T> extends Replacement<T>{
    public SteadyState() {
    }

    @Override
    public Vector<Solution<T>> apply( Vector<Solution<T>> parent, Vector<Solution<T>> offspring){
        try{
            Vector<Solution<T>> candidates = new Vector();
            int n = offspring.size();
            for (int i = 0; i < n; i++) {
                candidates.add(offspring.get(i));
            }
            n = parent.size();
            for (int i = 0; i < n; i++) {
                candidates.add(parent.get(i));
            }
            Solution.sort((Vector)candidates);
            int k = parent.size();
            Vector buffer = new Vector();
            for (int i = 0; i < k; i++) {
                buffer.add(candidates.get(i));
            }
            return buffer;
        }catch( Exception e ){
            e.printStackTrace();
        }
        return null;
    }
}
