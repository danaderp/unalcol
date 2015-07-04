package unalcol.io;

import java.io.Writer;

import unalcol.reflect.service.*;

/**
 * <p>Title: Persistentmethod</p>
 *
 * <p>Description: Persistency methods for a given object</p>
 *
 * <p>Copyright: Copyright (c) 2009</p>
 *
 * <p>Company: Kunsamu</p>
 *
 * @author Jonatan Gomez Perdomo
 * @version 1.0
 */
public interface WriteService<T> extends Service {
    /**
     * Writes an object to the given writer
     * @param obj Object to write
     * @param writer The writer object
     * @throws IOException IOException
     */
    public void write(T obj, Writer writer) throws Exception;
}
