package unalcol.sort;

/**
 * <p>Reverses a given order</p>
 * <p>Copyright: Copyright (c) 2010</p>
 * 
 * @author Jonatan Gomez Perdomo
 * @version 1.0
 *
 */
public class ReversedOrder<T> implements Order<T> {
    /**
     * Order to be reversed
     */
    protected Order<T> original_order = null;

    /**
     * Creates a reversed order for the given order
     * @param _original_order Order to be reversed
     */
    public ReversedOrder(Order<T> _original_order) {
        original_order = _original_order;
    }

    /**
     * Determines if object one is less than (in the reversed order) object two
     * @param one The first object to compare
     * @param two The second object to compare
     * @return (one<two) <-> (two<one in the original_order)
     */
    public int compare(T one, T two) {
        return original_order.compare(two, one);
    }

    public Object owner(){ return original_order.owner(); }
}