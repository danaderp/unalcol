package unalcol.reflect.service;
import unalcol.reflect.loader.tool.JarLoader;
import unalcol.reflect.loader.Loader;
import unalcol.reflect.util.*;
import java.io.File;
import java.util.zip.ZipFile;
import java.util.zip.ZipEntry;
import java.util.Enumeration;

/**
 * <p>Loads service from the current Class Loader (looking throught 
 * paths, jars, etc)</p>
 *
 * @author Jonatan Gomez Perdomo
 * @version 1.0
 */
public class ServiceLoader {

    /**
     * Class Loader
     */
    protected Loader loader;

    /**
     * The service hierarchy infra-structure
     */
    protected ServiceProvider provider;

    public ServiceLoader( Loader loader, ServiceProvider provider ){
        this.loader = loader;
        this.provider = provider;
    }

    public ServiceLoader( Loader loader ){
        this.loader = loader;
        this.provider = new ServiceProvider();
    }

    /**
     * Returs the ClassLoader used by the PlugInProvider
     * @return ClassLoader used by the PlugInProvider
     */
    public Loader loader(){
        return loader;
    }

    public ServiceProvider provider(){
        return provider;
    }

    /**
     * Loads the plugIns inside source and class paths
     */
    public void load() {
        String[] paths = loader.systemClassPath();
        for( int i=0; i<paths.length; i++ ){
            load( paths[i], paths[i] );
        }
        if( loader.usingPlugInPaths() ){
            String source_path = loader.sourcePath();
            String class_path = loader.classPath();
            load( source_path, source_path );
            if (!source_path.equals(class_path)) {
                load( class_path, class_path );
            }
            loadFromJars();
        }
    }

    /**
     * Adds a plugIn to the associated PlugInProvider if possible
     * @param result Candidate PlugIn to be included by the PlugInProvider
     * @return <i>true</i> if the given class represents a PlugIn that can be
     * associated to the given PlugInProvider and it was succesfully included in the Provider,
     * <i>false</i> otherwise
     */
    protected boolean add(Class result) {
        try {
            if(getClass() != result && provider.register(result) ){
                return true;
            }
        } catch (Exception e) {
        }
        return false;
    }

    /**
     * Adds the given class to the PlugIn Provider as PlugIn if possible
     * @param className Class representing a PlugIn that should be added to the PlugIn Provider
     * @return <i>true</i> if the given class represents a PlugIn that can be
     * associated to the given PlugInProvider and it was succesfully included in the Provider,
     * <i>false</i> otherwise
     */
    protected boolean add( String className ){
        try {
            Class result = loader.loadClass(className);
            if (result != null) {
                return add(result);
            }
        } catch (Exception e) {
        }

        return false;
    }

    /**
     * Adds a jar file to the resources
     * @param f Jar file to be added
     */
    protected void add(File f) {
        try {
            ZipFile jarFile = new ZipFile(f);
            Enumeration entries = jarFile.entries();
            while (entries.hasMoreElements()) {
                ZipEntry entry = (ZipEntry) entries.nextElement();
                if (entry.getName().endsWith(".class")) {
                    String className = entry.getName();
                    className = className.substring(0,
                            className.length() - 6).replace('/',
                            '.');
                    add( className );
                }
            }
        } catch (Exception e) {} finally {
        }
    }

    /**
     * Loads the services defined in the source and class paths
     * @param path (Source/Class) path being analized
     * @param rootPath Root path in the recursive process
     */
    protected void load(String path, String rootPath) {
        //find all JAR files on the path and subdirectories
        File f = new File(path);
        String[] list = f.list();
        if (list == null)
            return;
        for (int i = 0; i < list.length; i++) {
            f = new File(path, list[i]);
            if (f.isFile()) {
                load(f, path, rootPath);
            } else {
                if (f.isDirectory()) {
                    load(f.getAbsolutePath(), rootPath);
                }
            }
        }
    }

    /**
     * Loads the plugIns represented by the given file
     * @param f File representing a PlugIn
     * @param path Source/class path
     * @param rootPath Root path in the recursive process
     */
    protected void load( File f, String path, String rootPath ){
        if (f.getName().endsWith(".class") || f.getName().endsWith(".java")) {
            String classname = f.getAbsolutePath();
            classname = classname.substring(rootPath.length());
            classname = classname.substring(0,classname.lastIndexOf('.'));
            classname = classname.replace(JavaOS.fileSeparator(), '.');
            if( classname.charAt(0) == '.' ){
                classname = classname.substring(1);
            }
            add(classname);
        }
    }

    /**
     * Loads the jar files from the libraries path
     */
    protected void loadFromJars() {
        JarLoader[] jarFiles = loader.jarFiles();

        int n = jarFiles.length;
        for( int i=0; i<n; i++ ){
           add(jarFiles[i].file());
        }
    }
}