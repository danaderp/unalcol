package unalcol.tracer;

import unalcol.types.collection.vector.*;

/**
 * <p>Title: VectorTracer</p>
 * <p>Description: A Tracer that stores the traced information into a Vector.</p>
 * <p>Copyright: Copyright (c) 2009</p>
 * <p>Company: Kunsamu</p>
 * @author Jonatan Gomez Perdomo
 * @version 1.0
 */
public class VectorTracer extends Tracer {
    /**
     * Objects added by the source
     */
    private Vector tracedObjects = null;
    
    protected int resolution = 1;
    
    protected int counter = 0;

    /**
     * Creates a new VectorTracer
     */
    public VectorTracer(Object owner) {
        super( owner );
        tracedObjects = new Vector();
    }

    /**
     * Creates a new VectorTracer
     */
    public VectorTracer(Object owner, int resolution) {
        super( owner );
        this.resolution = resolution;
        tracedObjects = new Vector();
    }

    /**
     * Adds a new Object into the Tracer
     * @param obj Traced information to be added
     */
    @Override
    public void add(Object obj) {
        if( tracing ){
            if( resolution == 1 ){
                tracedObjects.add(obj);
            }else{
                if( counter == 0 ){
                    tracedObjects.add(obj);
                }    
                counter = (counter + 1)%resolution;
            }    
        }
    }

    /**
     * Returns the traced information
     * @return A Vector of objects representing the traced information
     */
    @Override
    public Object get() {
        return tracedObjects;
    }

    /**
     * Cleans the traced information
     */
    @Override
    public void clean() {
        tracedObjects.clear();
        tracedObjects = null;
        counter = 0;
    }

    /**
     * Closes the tracer (does nothing)
     */
    @Override
    public void close() {}
}