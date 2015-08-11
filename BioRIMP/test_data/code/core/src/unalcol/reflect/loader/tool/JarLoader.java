package unalcol.reflect.loader.tool;
import unalcol.reflect.loader.ResourceLoaderTool;
import unalcol.reflect.loader.LoaderTool;
import java.io.*;
import java.net.*;
import java.util.*;
import java.util.zip.*;

/**
 * <p>Tool used by the ClassLoader for loading class Bytes and Resources from a Jar File</p>
 *
 * @author Jonatan Gomez Perdomo
 * @version 1.0
 */
public class JarLoader implements LoaderTool, ResourceLoaderTool{
    /**
     * Jar File containing class files
     */
    protected File jar;

    /**
     * Creates a Tool for loading class file bytes and resources from an specific Jar File
     * @param jar Jar File containing class files and resources
     */
    public JarLoader( File jar ){
        this.jar = jar;
    }

    /** Loads the class bytes from a Jar file
     * @param name Name of the resource
     * @return Class bytes from a file if possible, null otherwise.
     */
    public byte[] loadBytes(String name) {
        BufferedInputStream bis = null;
        try {
            ZipFile jarFile = new ZipFile(jar);
            Enumeration entries = jarFile.entries();
            while (entries.hasMoreElements()) {
                ZipEntry entry = (ZipEntry) entries.nextElement();
                String className = entry.getName();
                className = className.substring(0,
                        className.length() - 6).replace('/',
                        '.');
                if (className.equals(name)) {
                    bis = new BufferedInputStream(jarFile.getInputStream(entry));
                    int size = (int) entry.getSize();
                    byte[] data = new byte[size];
                    int b = 0, eofFlag = 0;
                    while ((size - b) > 0) {
                        eofFlag = bis.read(data, b, size - b);
                        if (eofFlag == -1)break;
                        b += eofFlag;
                    }
                    return data;
                }
            }
        } catch (Exception e) {} finally {
            try {
                if (bis != null) bis.close();
            } catch (IOException e) {}
        }
        return null;
    }

    /**
     * Returns a resource from JAR file as a URL
     * @param name a resource name.
     * @return a resource from the JAR file as a URL
     */
    public URL getResource(String name) {
        byte[] resourceBytes;
        try {
            resourceBytes = loadBytes(name);
            if (resourceBytes != null) {
                return ResourcePathLoader.make(name, jar);
            }
        } catch (MalformedURLException e) {
        }
        return null;
    }

    /**
     * Returns a resource from the JAR file as an InputStream
     * @param name a resource name.
     * @return a resource from the JAR file as an InputStream
     */
    public InputStream getResourceAsStream(String name) {
        byte[] resourceBytes;
        try {
            resourceBytes = loadBytes( name );
            if (resourceBytes != null) {
                return new ByteArrayInputStream(resourceBytes);
            }
        } catch (Exception e) {
        }
        return null;
    }

    /**
     * Gets the name of the Jar File
     * @return Jar File name
     */
    public String toString(){
        return jar.toString();
    }

    /**
     * Gets the Jar File used for loading class bytes
     * @return Jar File containing class files
     */
    public File file(){
        return jar;
    }

    public void clear(){}

}
