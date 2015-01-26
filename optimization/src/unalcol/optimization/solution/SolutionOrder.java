package unalcol.optimization.solution;
import unalcol.sort.Order;

/**
 * <p>Title: SolutionOrder</p>
 * <p>Description: Ordering of Candidate Solutions (given by the value obtained by
 * candidate solutions on the optimization function.</p>
 * <p>Copyright:    Copyright (c) 2010</p>
 * <p>Company: Kunsamu</p>
 * @author Jonatan Gomez
 * @version 1.0
 */
public class SolutionOrder implements Order<Solution>{
    /**
     * Returns the Class that owns the PlugIn
     * @return Class The PlugIns owner class
     */
    public Object owner() {
        return Solution.class;
    }

    /**
     * Compares candidate solution <i>x</i> againts candidate solution <i>y</i>
     * using the value obtained by the candidate solutions on the optimization function.
     * @return <i>less than 0</i> if <i>x</i> is less than <i>y</i> according to their
     * optimization function value, <i>0</i> if <i>x</i> and <i>y</i> have the same
     * optimization function value, <i>greater than 0 </i> if <i>x</i> is greater than
     * <i>y</i> according to their optimization function value.
     */
    @Override
    public int compare(Solution x, Solution y) {
        return Double.compare(x.value(), y.value());
    }
}