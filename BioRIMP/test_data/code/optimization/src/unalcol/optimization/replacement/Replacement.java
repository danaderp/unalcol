package unalcol.optimization.replacement;
import unalcol.optimization.solution.Solution;
import unalcol.types.collection.vector.*;
import unalcol.reflect.service.*;

/**
 * <p>Title: Replacement</p>
 *
 * <p>Description: Replacement strategy. Mechanism used by the evolutionary
 * algorithm for determining the candidate solutions that will be maintained in
 * the next generation (selection between parents and offspring)</p>
 *
 * <p>Copyright: Copyright (c) 2010</p>
 *
 * @author Jonatan Gomez
 * @version 1.0
 */
public abstract class Replacement<T> implements Service{

    /**
     * Obtains the population represented by the selected individuals (from parents and offsprings)
     * @return Next population generation
     */
    public abstract Vector<Solution<T>> apply(Vector<Solution<T>> parents, 
                                        Vector<Solution<T>> offspring);

    @Override
    public Object owner(){
        return Object.class;
    }
}