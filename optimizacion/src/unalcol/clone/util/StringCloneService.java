/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package unalcol.clone.util;

import unalcol.clone.CloneService;

/**
 *
 * @author jgomez
 */
public class StringCloneService implements CloneService<String> {
    public StringCloneService() {
    }

    @Override
    public Object owner(){
        return String.class;
    }

    /**
     * Clones an String (just a Shallow copy)
     * @param obj The string to be cloned
     * @return A clone of the string (shallow copy)
     */
    @Override
    public String clone(String obj){
        return obj;
    }

    
}
