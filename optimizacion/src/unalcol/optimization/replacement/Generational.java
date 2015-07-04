package unalcol.optimization.replacement;
import unalcol.optimization.solution.Solution;
import unalcol.types.collection.vector.Vector;
/**
 * <p>Title: Generational</p>
 *
 * <p>Description: The Generational Genetic Algorithm Replacement Strategy</p>
 *
 * <p>Copyright: Copyright (c) 2010</p>
 *
 * @author Jonatan Gomez
 * @version 1.0
 */
public class Generational<T> extends Replacement<T>{
    public Generational() {
    }

    @Override
    public Vector<Solution<T>> apply( Vector<Solution<T>> parent, 
                                Vector<Solution<T>> offspring ){
        return offspring;
    }
}
