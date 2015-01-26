package unalcol.tracer;

import unalcol.reflect.util.*;
import unalcol.reflect.service.*;


/**
 * <p>Title: Tracing</p>
 *
 * <p>Description: Unalcol class for maintaining the set of tracers for given objects</p>
 *
 * <p>Copyright: Copyright (c) 2009</p>
 *
 * <p>Company: Kunsamu</p>
 *
 * @author Jonatan Gomez
 * @version 1.0
 */
public class TracerProvider{

    public static Tracer[] get( Object obj ){
        try{
           Service[] services = ReflectUtil.getProvider().owned_services(Tracer.class, obj);
           Tracer[] tracers = new Tracer[services.length];
           for( int i=0; i<services.length; i++ ){
               tracers[i] = (Tracer)services[i];
           }
           return tracers;
        }catch( Exception e ){
            e.printStackTrace();
        }
        return new Tracer[0];
    }

    /**
     * Adds a data object to each tracer associated to a given object
     * @param obj Object being traced
     * @param data Object to be added to each tracer associated to the object
     */
    public static void trace(Object obj, Object data) {
        Tracer[] services = get(obj);
        for( int i=0; i<services.length; i++ ){
            services[i].add(data);
        }
    }

    /**
     * Closes each tracer associated to a given object
     * @param obj Object being traced
     */
    public static void close(Object obj) {
        Tracer[] services = get(obj);
        for( int i=0; i<services.length; i++ ){
            services[i].close();
        }
    }

    /**
     * Cleans each tracer associated to a given object
     * @param obj Object being traced
     */
    public static void clean(Object obj) {
        Tracer[] services = get(obj);
        for( int i=0; i<services.length; i++ ){
            services[i].clean();
        }
    }
    
}