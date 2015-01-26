package unalcol.reflect.loader;

import unalcol.reflect.util.*;
import unalcol.reflect.loader.tool.*;
import java.io.*;
import java.lang.reflect.*;
import java.net.*;
import java.util.*;

/**
 * <p>A class loader for the unalcol library (reflection).
 * Unalcol uses this class loader to load and compile resources from an specific directory.
 * This class loader will also load classes and resources from JAR files. Specially designed for loading
 * Services (PlugIns)</p>
 *
 * <p> The class loader searches for classes and resources in the following order:
 * <ol>
 *  <li> Source directory</li>
 *  <li> Classes directory</li>
 *  <li> JAR files in the libraries directory</li>
 *  <li> Application directory</li>
 * </ol>
 * @author Jonatan Gomez Perdomo
 * @version 1.0
 */
public class Loader extends ClassLoader {

    /**
     * Cache of compiled and loaded resources
     */
    protected Hashtable cache = new Hashtable();

    /**
     * Set of loaded jar files
     */
    protected MultiJarLoader libraries =  new MultiJarLoader();

    /**
     * Class Loader from java files (compile and load Java files, if possible) located in a directory
     */
    protected SourcePathLoader source = null;

    /**
     * Class Loader from class files located in a directory
     */
    protected PathLoader classes = null;

    /**
     * Class Loader from class files located in the application directory
     */
    protected MultiPathLoader main = new MultiPathLoader();
    
    /**
     * Resources Manager located in a directory
     */
    ResourcePathLoader resources;

    /**
     * Creates a non-initialize Loader.
     */
    public Loader(){
    }

    public Loader(ClassLoader parent){
        super(parent);
    }

    public void init(){
        this.main.clear();
        this.libraries.clear();
        this.cache.clear();
        String[] classPath = System.getProperty("java.class.path").split(""+JavaOS.pathSeparator());

        for( int i=0; i<classPath.length; i++){
            if( classPath[i].endsWith(".jar") || classPath[i].endsWith(".zip") ){
                this.libraries.add(classPath[i]);
            }else{
                this.main.add(classPath[i]);
            }
        }
    }

    /**
     * Initializes the ClassLoader that searches in the directory path
     * passed as a parameter. The constructor automatically finds all JAR and ZIP
     * files in the path and first level of subdirectories. The JAR and ZIP files
     * are stored in a Vector for future searches.
     * @param libs Libraries Path
     */
    public void add( String libs ){
        if( libs != null ){
            this.libraries.load(JavaOS.absolutePath(libs));
        }
    }

    /**
     * Initializes the ClassLoader that searches in the directory path
     * passed as a parameter. The constructor automatically finds all JAR and ZIP
     * files in the path and first level of subdirectories. The JAR and ZIP files
     * are stored in a Vector for future searches.
     * @param libs Libraries Path
     * @param classes Classes Path
     * @param resources Resources Path
     * @param sources Sources Path
     */
    public void set( String libs, String classes, String resources,
                     String sources ){
        init();

        if( libs != null ){
            this.libraries.load(JavaOS.absolutePath(libs));
            this.resources = new ResourcePathLoader(JavaOS.absolutePath(resources));
            this.source = new SourcePathLoader(JavaOS.absolutePath(sources),
                                                JavaOS.absolutePath(sources),libraries);
            this.classes = new PathLoader(JavaOS.absolutePath(classes));
        }
    }

    /**
     * Returns a resource from the path or JAR files as a URL
     * @param name a resource name.
     */
    public URL getResource(String name) {
        // try system loader first
        URL res = super.getResource(name);

        if (res == null) {
            // try resources path
            try {
                res = resources.getResource(name);
            } catch (Exception e) {}
            if (res == null) {
                // otherwise look in JAR files
                return res = libraries.getResource(name);
            }
        }
        return res;
    }

    /**
     * Returns a resource from the path or JAR files as an InputStream
     * @param name a resource name.
     * @return a resource from the path or JAR files as an InputStream
     */
    public InputStream getResourceAsStream(String name) {
        //try the system loader first
        InputStream is = super.getResourceAsStream(name);
        if (is == null) {
            // try resources path
            try {
                is = resources.getResourceAsStream(name);
            } catch (Exception e) {}
            if (is == null) {
                //look in JAR files
                is = libraries.getResourceAsStream(name);
            }
        }
        return is;
    }

    /**
     * Returns a Class from the path or JAR files. Classes are automatically resolved.
     * @param className a class name without the .class extension.
     * @return a Class from the path or JAR files. Classes are automatically resolved.
     */
    public Class loadArrayClass(String className) throws ClassNotFoundException {
        int k = className.lastIndexOf("[");
        if (k >= 0) {
            int index = className.indexOf("@");
            if (index >= 0) {
                className = className.substring(0, index);
            }
            return Class.forName(className, true, this);
        }
        return loadClass(className);
    }

    /**
     * Returns a Class from the path or JAR files. Classes are automatically resolved.
     * @param className a class name without the .class extension.
     * @return a Class from the path or JAR files. Classes are automatically resolved.
     */
    public Class loadClass(String className) throws ClassNotFoundException {
        // If trying to load the Loader class then use the system class already loaded
        Class result = findLoadedClass(className);
        if( result != null ){
            return result;
        }

        result = (Class)cache.get(className);
        if( result != null ) return result;
        
        if( !getClass().getName().equals(System.getProperty("java.system.class.loader")) ){
            result = loadSystemClass(className);
        }
        if( result != null ){
            return result;
        }
        if( className.equals(this.getClass().getName())){
            return loadSystemClass(className);
        }
        if (className.length() > 0 && className.charAt(0) == '[')
            return loadArrayClass(className);
        return (loadClass(className, true));
    }

    /**
     * Loads a Class from the system class loader if possible
     * @param className Name of the class to be loaded
     * @return The class object if the System Class Loader can load it, <i>null</i> otherwise.
     */
    public Class loadSystemClass(String className) {
        try {
            return getParent().loadClass(className);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Returns a Class from the path or JAR files. Classes are resolved if resolveIt is true.
     * @param className a String class name without the .class extension.
     *        resolveIt a boolean (should almost always be true)
     * @return a Class from the path or JAR files. Classes are resolved if resolveIt is true.
     */
    public synchronized Class loadClass(String className, boolean resolveIt) throws
            ClassNotFoundException {

        // If trying to load the Loader class then use the system class already loaded
        if( className.equals(this.getClass().getName())){
            return loadSystemClass(className);
        }

        Class result = null;
        if( !getClass().getName().equals(System.getProperty("java.system.class.loader")) ){
            result = loadSystemClass(className);
            if( result != null ){ return result; }
        }


        // try the local cache of classes
        result = (Class) cache.get(className);
        if (result == null) {
            // Try to load it from source code, class path or jars directory
            result = loadClassBytes(className, resolveIt);
            if( result == null &&
                getClass().getName().equals(System.getProperty("java.system.class.loader")) ){
            // try the system class loader
                result = loadSystemClass(className);
            }
            // Adding to the local cache
            if (result != null) {
                cache.put(className, result);
            }else{
                throw new ClassNotFoundException(className);
            }
        }
        return result;
    }

    /**
     * Loads the class bytes from the disk. Returns an array of bytes that will
     * be defined as a Class. This should be overloaded to have the Class Loader
     * look in more places.
     * @param name a class name without the .class extension.
     * @return Class bytes from the disk if possible, null otherwise.
     */
    protected Class loadClassBytes(String name, boolean resolveIt){
        Class result = null;
        byte[] classBytes = loadBytes(name);
        if (classBytes != null) {
            try{
                result = defineClass(name, classBytes, 0, classBytes.length);
                //Resolve if necessary
                if (result != null && resolveIt){
                    resolveClass(result);
                }
            } catch (Exception e) {
                try{
                    result =   this.getParent().loadClass(name);
                }catch( Exception e1 ){}
            }
        }
        return result;
    }

    /** Loads the bytes from file
     * @param path Path where the resource is stored (a Java file or a Class file)
     * @param classname Name of the resource
     * @return Class bytes from the disk if possible, null otherwise.
     */
    private byte[] loadBytes(String classname){

        byte[] buf = main.loadBytes(classname);
        if( buf == null && usingPlugInPaths() ){
            buf = source.loadBytes(classname);
            if( buf == null ){
                buf = classes.loadBytes(classname);
                if( buf == null ){
                    buf = libraries.loadBytes(classname);
                }
            }
        }
        return buf;
    }

    /**
     * Creates an instance of primitive types
     * @param the_class Primitive Type
     * @return New Instance of the given primitive class
     */
    public Object newInstancePrimitive(Class the_class) {
        if (the_class == Integer.TYPE)
            return 0;
        else if (the_class == Long.TYPE)
            return 0L;
        else if (the_class == Short.TYPE)
            return 0;
        else if (the_class == Byte.TYPE)
            return 0;
        else if (the_class == Double.TYPE)
            return 0.0;
        else if (the_class == Float.TYPE)
            return 0.0f;
        else if (the_class == Character.TYPE)
            return ' ';
        else return false;
    }

    /**
     * Creates a new instance of an object using the simplest constructor
     * @param the_class Class which will produce a new instance
     * @return New instance of the given class
     * @throws Exception
     */
    public Object newInstanceSimplestConstructor(Class the_class) throws
            Exception {
        Constructor[] constructors = the_class.
                                     getConstructors();
        int k = 0;
        int primitives = 0;
        int non_primitives = Integer.MAX_VALUE;
        for (int i = 0; i < constructors.length; i++) {
            Class[] parameters = constructors[i].
                                 getParameterTypes();
            int prim = 0;
            int non_prim = 0;
            for (int j = 0; j < parameters.length; j++) {
                if (parameters[j].isPrimitive()) {
                    prim++;
                } else {
                    non_prim++;
                }
            }
            if (primitives < prim ||
                (primitives == prim &&
                 non_primitives > non_prim)) {
                k = i;
                primitives = prim;
                non_primitives = non_prim;
            }
        }

        if( k<constructors.length ){
            Class[] parameter_class = constructors[k].
                                      getParameterTypes();
            Object[] parameter = new Object[parameter_class.
                                 length];
            for (int i = 0; i < parameter.length; i++) {
                parameter[i] = newInstance(parameter_class[i]);
            }
            return constructors[k].newInstance(parameter);
        }
        return null;
    }

    /**
     * Creates a new instance of an object (includes arrays, Double, Long, Short, Byte, Integer, Float, and Character classes)
     * @param the_class Class which will produce a new instance
     * @return New instance of the given class
     * @throws Exception
     */
    public Object newInstance(Class the_class) throws Exception {
        if (the_class.isPrimitive()) {
            return newInstancePrimitive(the_class);
        } else {
            if (the_class.isArray()) {
                return Array.newInstance(the_class.getComponentType(), 0);
            } else {
                try {
                    return the_class.newInstance();
                } catch (Exception e) {
                    return newInstanceSimplestConstructor(the_class);
                }
            }
        }
    }

    /**
     * Creates a new instance of an object (includes arrays, Double, Long, Short, Byte, Integer, Float, and Character classes)
     * @param className Name of the class which will produce a new instance
     * @return New instance of the given class
     * @throws Exception
     */
    public Object newInstance(String className) throws Exception {
        return newInstance(loadClass(className));
    }

    /**
     * Gets the applications path
     * @return Applications path
     */
    public String[] systemClassPath(){
        return main.paths();
    }

    /**
     * Gets the source path
     * @return Source path
     */
    public String sourcePath(){
        return source.sourcePath();
    }

    /**
     * Gets the class path
     * @return Class path
     */
    public String classPath(){
        return classes.path();
    }

    /**
     * Puts a Class in the cache of the Loader
     * @param cl Class to be added to the Cache of the Loader
     */
    public void put( Class cl ){
        cache.put(cl.getName(), cl);
    }

    /**
     * Gets the set of Jar Files in the libraries path
     * @return Jar Files in the libraries path
     */
    public JarLoader[] jarFiles(){
        return libraries.jarFiles();
    }

    /**
     * Determines if the Loader is using PlugIn paths or not
     * @return <i>true</i> If the Loader is using plugIns path, <i>false</i> otherwise.
     */
    public boolean usingPlugInPaths(){
        return(source!=null);
    }
}