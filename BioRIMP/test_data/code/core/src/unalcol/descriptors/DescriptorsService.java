package unalcol.descriptors;

import unalcol.reflect.service.*;

/**
 * <p>Obtains a set of real value descriptors for a given object</p>
 *
 * <p>Copyright: Copyright (c) 2010</p>
 *
 * @author Jonatan Gomez Perdomo
 * @version 1.0
 */
public interface DescriptorsService extends Service {
    /**
     * Obtains the descriptors of an object
     * @param obj Object to be analyzed
     * @return An array of double values used for describing the object
     */
    public double[] descriptors(Object obj);
}