package unalcol.io;

import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.*;

import unalcol.reflect.service.*;

/**
 * <p>Title: PersistentWrapper</p>
 * <p>Description: Persistence methods for objects of a given class</p>
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: Kunsamu</p>
 * @author Jonatan Gomez
 * @version 1.0
 */
public class WriteServiceWrapper extends ServiceWrapper implements
        WriteService {
    /**
     * Creates a set of persistence methods for object of the given class
     */
    public WriteServiceWrapper(){
        super("write");
    }

    /**
     * Writes an object to the given writer (The object should has a write method)
     * @param obj Object to write
     * @param writer The writer object
     * @throws IOException IOException
     */
    @Override
    public void write(Object obj, Writer writer) throws IOException {
        try {
            Method m = obj.getClass().getMethod(method_name, new Class[] {Writer.class});
            m.invoke(obj, new Object[] {writer});
        } catch (Exception e) {
            throw new IOException(e.getMessage());
        }
    }
}
