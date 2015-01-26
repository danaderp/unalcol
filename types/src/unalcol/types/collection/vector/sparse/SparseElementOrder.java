package unalcol.types.collection.vector.sparse;
import unalcol.sort.Order;
import unalcol.*;

/**
 * <p>Title: SparseValueOrder</p>
 * <p>Description: Compares two SparseValue.</p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: Kunsamu</p>
 * @author Jonatan Gomez
 * @version 1.0
 */

public class SparseElementOrder<T> implements Order<SparseElement<T>> {

    /**
     * Returns the Class that owns the PlugIn
     * @return Class The PlugIns owner class
     */
    public Object owner() {
        return SparseElement.class;
    }

    /**
     * The base PlugIn class its is overwritring
     * @return Class PlugIn being overwritten
     */
    public Class base() {
        return Order.class;
    }

    /**
     * Determines if the object is less than (in some order) the given object
     * @param x Object to Compare
     * @param y Object to Compare
     * @return true if the object is less than the given object x. false in other case
     */
    @Override
    public int compare(SparseElement<T> x, SparseElement<T> y) {
        return (x.index() - y.index());
    }
}
