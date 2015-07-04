package unalcol.clone;

import unalcol.reflect.util.*;

/**
 * <p>ServiceProvider of cloning methods.</p>
 * <p>Copyright: Copyright (c) 2010</p>
 *
 * @author Jonatan Gomez Perdomo
 * @version 1.0
 */
public class Clone{

    protected static boolean wrapperLoaded = false;

    /**
     * Gets the current cloning service for the given object
     * @param obj Object from which the CloneService will be retrieved
     * @return A CloneService if some cloning service has been defined as current
     * cloning service for the given object, <i>null</i> otherwise
     */
    public static CloneService getService( Object obj ){
        if( !wrapperLoaded ){
            wrapperLoaded = ReflectUtil.getProvider().register(CloneServiceWrapper.class);
        }
        return ((CloneService)ReflectUtil.getProvider().default_service(CloneService.class,obj));
    }

    /**
     * Creates a clone of a given object
     * @param obj Object to be cloned
     * @return A clone of the object, if a cloning service is available for the given object, <i>null</i> otherwise
     */
    public static Object get( Object obj ){
        CloneService service = getService(obj);
        if( service != null ){
            return service.clone(obj);
        }
        return null;
    }
}