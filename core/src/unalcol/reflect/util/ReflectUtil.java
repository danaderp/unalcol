package unalcol.reflect.util;

import unalcol.reflect.service.*;
import unalcol.reflect.loader.Loader;

/**
 * <p>Set of methods for simplifying the use of the Reflection infra-structure</p>
 *
 * @author Jonatan Gomez Perdomo
 * @version 1.0
 */
public class ReflectUtil {
    /**
     * The Service hierarchy infra-structure
     */
    protected static ServiceProvider provider = null;
    
    /**
     * Gets the Loader (ClassLoader) used by the given class
     * @return Loader (ClassLoader) used by the given class
     */
    public static Loader getLoader(){
        Loader loader = null;
        if( ClassLoader.getSystemClassLoader() instanceof Loader ){
            loader = (Loader)ClassLoader.getSystemClassLoader();
        }else{
            loader = new Loader();
        }
        return loader;
    }

    /**
     * Returns The service provider
     * @return The service provider
     */
    public static ServiceProvider getProvider(){
        if( provider == null ){
            Loader loader = getLoader();
            ServiceLoader sl = new ServiceLoader(loader);
            sl.load();
            provider = sl.provider();
        }
        return provider;
    }

    /**
     * Initializes the ClassLoader using the same path for libraries, resources, classes and sources
     */
    public static void init(){
        if( provider == null ){
            Loader loader = getLoader();
            loader.init();
            provider = null;
        }
    }

     /**
     * Initializes the ClassLoader that searches in the directory path
     * passed as a parameter. The constructor automatically finds all JAR and ZIP
     * files in the path and subdirectories. The JAR and ZIP files
     * @param libs Libraries Path
     * @param classes Classes Path
     * @param resources Resources Path
     * @param sources Sources Path
      * @return The service provider
     */
    public static ServiceProvider getProvider(String libs, String classes,
            String resources, String sources) {
        if( provider == null ){
            Loader loader = getLoader();
            loader.set(libs, classes, resources, sources);
            ServiceLoader sl = new ServiceLoader(loader);
            sl.load();
            provider = sl.provider();
        }
        return provider;
    }

     /**
     * Initializes the ClassLoader that searches in the directory path
     * passed as a parameter. The constructor automatically finds all JAR and ZIP
     * files in the path and subdirectories. 
     * @param path Libraries, Classes, Resources and Sources Path
      * @return The service provider
     */
    public static ServiceProvider getProvider(String path) {
        return getProvider(path, path, path, path);
    }
}