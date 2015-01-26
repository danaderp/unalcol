package unalcol.tracer;

import unalcol.reflect.service.*;

/**
 * <p>Title: Tracer</p>
 * <p>Description: Abstract definition of a Tracer of objects</p>
 * <p>Copyright: Copyright (c) 2009</p>
 * <p>Company: Kunsamu</p>
 * @author Jonatan Gomez Perdomo
 * @version 1.0
 */
public abstract class Tracer  implements Service{
    public Object owner = null;

    protected boolean tracing = true;

    public Tracer( Object owner ){
        this.owner = owner;
    }

    public boolean start(){
        boolean old = tracing;
        tracing = true;
        return old;
    }

    public boolean stop(){
        boolean old = tracing;
        tracing = false;
        return old;
    }

    /**
     * Adds an object sent by an object to the tracer
     * @param obj Traced information to be added
     */
    public abstract void add(Object obj);

    /**
     * Returns the traced object
     * @return An object representing the traced information
     */
    public abstract Object get();

    /**
     * Cleans the traced information
     */
    public abstract void clean();

    /**
     * Closes the tracer
     */
    public abstract void close();

    public Object owner(){
        return owner;
    }
}