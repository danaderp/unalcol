package unalcol.io;

import java.io.*;
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
public class ReadServiceWrapper extends ServiceWrapper implements
        ReadService {
    /**
     * Creates a set of persistence methods for object of the given class
     */
    public ReadServiceWrapper(){
        super("read");
    }

    /**
     * Reads an object from the given reader (The object should has a constructor with UnalcolReader as parameter)
     * @param reader The input stream from which the object will be read
     * @return An Object of the given class that is read from the inputstream
     * @throws IOException IOException
     */
    @Override
    public Object read(Object obj, ShortTermMemoryReader reader) throws
            IOException {
        try{
            Method m = obj.getClass().getMethod(method_name, new Class[] {ShortTermMemoryReader.class});
            m.invoke(obj, reader);
            return obj;
        }catch( Exception e ){
            throw reader.getException(e.getMessage());
        }
    }
}
