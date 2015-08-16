package unalcol.reflect.loader.tool;

import unalcol.reflect.loader.*;
import unalcol.types.collection.vector.*;
import java.io.*;
import java.net.*;


/**
 * <p>Keep Track of Jars and Zip Files containing (possibly) class files and resources</p>
 *
 * @author Jonatan Gomez Perdomo
 * @version 1.0
 */
public class MultiJarLoader implements LoaderTool, ResourceLoaderTool{
    /**
     * Set of loaded jar files
     */
    protected Vector<JarLoader> jarFiles = new Vector<>();

    /**
     * Creates a Jars file manager
     */
    public MultiJarLoader() {
    }

    /**
     * Adds a jar file to the resources
     * @param f Jar file to be added
     */
    public void add(String f) {
        add( new File(f));
    }

    /**
     * Adds a jar file to the resources
     * @param f Jar file to be added
     */
    public void add(File f) {
        if (f.getName().endsWith(".jar") || f.getName().endsWith(".zip"))
            jarFiles.add( new JarLoader(f) );
    }

    /**
     * Loads the jar files from a path
     * @param path Jar Files path
     */
    public void load(String path) {
        //find all JAR files on the path and subdirectories
        File f = new File(path);
        String[] list = f.list();
        if (list == null)
            return;
        for (int i = 0; i < list.length; i++) {
            f = new File(path, list[i]);
            if (f.isFile()) {
                add(f);
            }else{
                if( f.isDirectory() ){
                    load( f.getAbsolutePath() );
                }
            }
        }
    }

    /**
     * Returns a resource from the path or JAR files as a URL
     * @param name a resource name.
     */
    @Override
    public URL getResource(String name) {
        URL url=null;
        int i = 0;
        while( i<jarFiles.size() && url==null) {
            url = jarFiles.get(i).getResource(name);
            i++;
        }
        return url;
    }

    /**
     * Returns a resource from the path or JAR files as an InputStream
     * @param name a resource name.
     * @return a resource from the path or JAR files as an InputStream
     */
    @Override
    public InputStream getResourceAsStream(String name) {
        InputStream is = null;
        int i = 0;
        while( i<jarFiles.size() && is==null) {
            is = jarFiles.get(i).getResourceAsStream(name);
            i++;
        }
        return is;
    }

    /** Loads the class bytes from JAR files in the specific path
     * @param name Name of the resource
     * @return Class bytes from a file if possible, null otherwise.
     */
    @Override
    public byte[] loadBytes(String name) {
        byte[] classBytes = null;
        int i = 0;
        while( i<jarFiles.size() && classBytes==null) {
            classBytes = jarFiles.get(i).loadBytes(name);
            i++;
        }
        return classBytes;
    }

    /**
     * Gets the set of Jar Files in the path
     * @return Jar Files in the path
     */
    public JarLoader[] jarFiles(){
        JarLoader[] jars = new JarLoader[jarFiles.size()];
        for( int i=0; i<jars.length; i++ ){
            jars[i] = jarFiles.get(i);
        }
        return jars;
    }

    @Override
    public void clear(){
        jarFiles.clear();
    }

}