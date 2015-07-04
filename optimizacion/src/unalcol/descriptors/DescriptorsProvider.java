package unalcol.descriptors;

import unalcol.reflect.util.*;

/**
 * <p>Descriptors Service Provider</p>
 *
 * <p>Copyright: Copyright (c) 2010</p>
 *
 * @author Jonatan Gomez Perdomo
 * @version 1.0
 */
public class DescriptorsProvider{

    public static DescriptorsService get( Object obj ){
        try{
            return ((DescriptorsService)ReflectUtil.getProvider().default_service(DescriptorsService.class,obj));
        }catch( Exception e ){
            e.printStackTrace();
        }
        return null;
    }


    /**
     * Obtains the descriptors of an object
     * @param obj Object to be analized
     * @return An array of double values used for describing the object
     */
    public static double[] descriptors(Object obj) {
        DescriptorsService method = get(obj);
        return method.descriptors(obj);
    }
}