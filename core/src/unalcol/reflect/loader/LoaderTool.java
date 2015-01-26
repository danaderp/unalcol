package unalcol.reflect.loader;

/**
 * <p>Abstract tool used by the ClassLoader for loading class Bytes from a File</p>
 *
 * @author Jonatan Gomez Perdomo
 * @version 1.0
 */
public interface LoaderTool {
    /** Loads the class bytes from file
     * @param classname Name of the resource
     * @return Class bytes from a file if possible, null otherwise.
     */
    public byte[] loadBytes(String classname);

    public void clear();
}
