package unalcol.descriptors;

import unalcol.reflect.service.*;
import java.lang.reflect.Method;

/**
 * <p>Descriptors wrappper method. Used for classes that already define a descriptors method</p>
 *
 * <p>Copyright: Copyright (c) 2010</p>
 *
 * @author Jonatan Gomez Perdomo
 * @version 1.0
 */
public class DescriptorsServiceWrapper extends ServiceWrapper implements
        DescriptorsService {
    /**
     * Creates a descriptors wrapped method for a given class
     */
    public DescriptorsServiceWrapper(){
        super("descriptors");
    }

    /**
     * Obtains the descriptors of an object
     * @param obj Object to be analized
     * @return An array of double values used for describing the object
     */
    public double[] descriptors(Object obj) {
        try {
            Method m = obj.getClass().getMethod(method_name) ;
            return (double[]) m.invoke(obj);
        } catch (Exception e) {
        }
        return null;
    }
}