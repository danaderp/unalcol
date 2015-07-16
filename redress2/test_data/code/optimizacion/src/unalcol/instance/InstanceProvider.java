/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package unalcol.instance;

import unalcol.types.collection.vector.Vector;
import unalcol.reflect.util.ReflectUtil;

/**
 *
 * @author jgomez
 */
public class InstanceProvider {
    /**
     * Gets the current cloning service for the given object
     * @param obj Object from which the CloneService will be retrieved
     * @return A CloneService if some cloning service has been defined as current
     * cloning service for the given object, <i>null</i> otherwise
     */
    public static InstanceService getService( Object obj ){
        return ((InstanceService)ReflectUtil.getProvider().default_service(InstanceService.class,obj));
    }

    /**
     * Creates a clone of a given object
     * @param obj Object to be cloned
     * @return A clone of the object, if a cloning service is available for the given object, <i>null</i> otherwise
     */
    public static Object get( Object obj ){
        InstanceService service = getService(obj);
        if( service != null ){
            return service.get(obj);
        }
        return null;
    }

    /**
     * Creates a clone of a given object
     * @param obj Object to be cloned
     * @return A clone of the object, if a cloning service is available for the given object, <i>null</i> otherwise
     */
    public static Vector get( Object obj, int n ){
        InstanceService service = getService(obj);
        if( service != null ){
            Vector v = new Vector();
            for( int i=0; i<n; i++ ){
                v.add(service.get(obj));
            }
            return v;
        }
        return null;
    }    
}
