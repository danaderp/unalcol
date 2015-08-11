package unalcol.reflect.service;

/**
 * <p>A Service definition. It is used for defining instance or class methods without
 * modifying or extending the class
 * (for example persistency, comparison, etc). See the ServiceLoader class</p>
 *
 * @author Jonatan Gomez Perdomo
 * @version 1.0
 */
public interface Service {
    /**
     * Returns the "Object" that owns the Service
     * @return The "Object" that owns the Service
     */
    public Object owner();
}