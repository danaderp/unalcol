/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package unalcol.io;
import java.io.IOException;
import unalcol.reflect.service.*;

/**
 *
 * @author jgomez
 */
public interface ReadService<T> extends Service{
    /**
     * Reads an object from the given reader
     * @param reader The input stream from which the object will be read
     * @return An Object of the given class that is read from the input stream
     * @throws IOException IOException
     */
    public T read(T obj, ShortTermMemoryReader reader) throws
            IOException;

}
