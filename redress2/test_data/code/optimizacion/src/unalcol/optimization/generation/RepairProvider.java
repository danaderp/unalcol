/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package unalcol.optimization.generation;

import unalcol.reflect.util.ReflectUtil;

/**
 *
 * @author jgomez
 */
public class RepairProvider {
    public static Object repair( Object obj ){
        Repair r = get(obj);
        return r.repair(obj);
    }
    public static Repair get( Object obj ){
        try{
            return ((Repair)
                    ReflectUtil.getProvider().
                    default_service(Repair.class,obj));
        }catch( Exception e ){
        }
        return null;
    }
    
}
