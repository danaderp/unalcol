package unalcol.reflect.loader.tool;
import unalcol.reflect.loader.LoaderTool;
import unalcol.reflect.util.JavaOS;
import java.io.*;

/**
 * <p>Keeps Track of class files maintained in a given Path</p>
 *
 * @author Jonatan Gomez Perdomo
 * @version 1.0
 */
public class PathLoader implements LoaderTool{
    /**
     * Class path
     */
    protected String class_path = null;

    /**
     * Operative System
     */
    protected JavaOS javaEnv = new JavaOS();

    /**
     * Creates a tool for loading class files from a given path
     * @param class_path Class path
     */
    public PathLoader( String class_path ){
        this.class_path = class_path;
    }

    /** Loads the bytes from file
     * @param classname Name of the resource
     * @return Class bytes from the disk if possible, null otherwise.
     */
    @Override
    public byte[] loadBytes(String classname){
        String filename = classname.replace('.', javaEnv.fileSeparator());
        String classFilename = filename + ".class";

        File classFile = new File(class_path, classFilename);

        byte[] buf = null;
        try {
            if (classFile.exists()) {
                InputStream is = new FileInputStream(classFile);
                int bufsize = (int) classFile.length();
                buf = new byte[bufsize];
                is.read(buf, 0, bufsize);
                is.close();
            }
        } catch (Exception e) {
        }

        return buf;
    }

    /**
     * Gets the Class path
     * @return Class path
     */
    public String path(){
        return class_path;
    }

    @Override
    public void clear(){}

}