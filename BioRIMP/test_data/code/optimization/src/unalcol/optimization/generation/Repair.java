/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package unalcol.optimization.generation;

import unalcol.reflect.service.Service;

/**
 *
 * @author jgomez
 */
public class Repair<T> implements Service{
    public T repair( T obj ){ return obj; }
    public Object owner(){ return Object.class; }
}
