package unalcol.clone;

import unalcol.reflect.service.*;

/**
 * <p>Method for cloning objects</p>
 *
 * <p>Copyright: Copyright (c) 2010</p>
 *
 * @author Jonatan Gomez Perdomo
 * @version 1.0
 */
public interface CloneService<T> extends Service {
    /**
     * Creates a clone of a given object
     * @param toClone Object to be cloned
     * @return A clone of the object
     */
    public T clone(T toClone);
}