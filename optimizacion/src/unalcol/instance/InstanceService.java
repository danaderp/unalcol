/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package unalcol.instance;

import unalcol.reflect.service.Service;

/**
 *
 * @author jgomez
 */
public interface InstanceService<T> extends Service {
    /**
     * Creates a clone of a given object
     * @param toClone Object to be cloned
     * @return A clone of the object
     */
    public T get( T obj );
}
