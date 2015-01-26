package unalcol.types.integer.array;
import java.io.*;

import unalcol.io.*;


/**
 * <p>Integer array persistent method</p>
 *
 * <p>Copyright: Copyright (c) 2009</p>
 * 
 * @author Jonatan Gomez Perdomo
 * @version 1.0
 */

public abstract class IntArrayPersistent implements WriteService{
    protected int[] theBase = new int[0];
    /**
     * Returns the Class that owns the PlugIn
     * @return Class The PlugIns owner class
     */
    public Object owner() {
        return theBase.getClass();
    }

    /**
     * Writes an array to the given writer
     * @param obj array to write
     * @param out The writer object
     * @throws IOException IOException
     */
    public abstract void write(int[] obj, Writer out) throws IOException;

    /**
     * Writes an object to the given writer (The object should has a write method)
     * @param obj Object to write
     * @param out The writer object
     * @throws IOException IOException
     */
    public void write(Object obj, Writer out) throws IOException{
        write((int[])obj, out );
    }
}
