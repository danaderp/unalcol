package unalcol.io;

import java.io.*;

import unalcol.reflect.util.*;

/**
 * <p>Title: Persistent</p>
 * <p>Description: Class for object's persistency.</p>
 * <p>Copyright: Copyright (c) 2009</p>
 * <p>Company: Kunsamu</p>
 * @author Jonatan Gomez Perdomo
 * @version 1.0
 */
public class Persistency{
    public static WriteService getWrite( Object obj ){
        try{
            return ((WriteService)ReflectUtil.getProvider().default_service(WriteService.class,obj));
        }catch( Exception e ){
        }
        return null;
    }

    public static ReadService getRead( Object obj ){
        try{
            return ((ReadService)ReflectUtil.getProvider().default_service(ReadService.class,obj));
        }catch( Exception e ){
        }
        return null;
    }

    /**
     * Writes an object to the given writer (The object should has a write method)
     * @param obj Object to write
     * @param writer The writer object
     * @throws IOException IOException
     */
    public static void write(Object obj, Writer writer) throws Exception {
        WriteService method = getWrite(obj);
        method.write(obj, writer);
    }

    /**
     * Reads an object from the given reader (The object should has a read method)
     * @param obj Object to read
     * @param reader The reader object
     * @throws IOException IOException
     */
    public static Object read(Object obj, ShortTermMemoryReader reader) throws IOException {
        ReadService method = getRead(obj);
        return method.read(obj, reader);
    }

    /**
     * Gets the persistent version of an object in String version. The Class which the
     * object belongs to should have associated a ClassPersistence object in the
     * Persistence class
     * @param obj Object that will be stored in an string
     * @return String containing the persistent version of the object
     */
    public static String toString(Object obj) {
        try {
            StringWriter sw = new StringWriter();
            write(obj, sw);
            sw.close();
            return sw.toString();
        } catch (Exception e) {}
        return obj.toString();
    }
    
    public static void readSeparator( ShortTermMemoryReader reader, 
            char separator ) throws IOException{
        try{
            char c = (char)reader.read();
            while( c!=separator && Character.isSpaceChar(c)){
                c = (char)reader.read();
            }
            if( c != separator && c != (char)-1 ){
                throw new Exception("Non available separator...");
            }
        }catch( Exception e ){
            throw reader.getException("Double Array Parser Error "+e.getMessage());
        }
    }


}