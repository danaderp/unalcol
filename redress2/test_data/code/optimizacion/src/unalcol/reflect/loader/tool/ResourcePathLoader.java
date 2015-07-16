package unalcol.reflect.loader.tool;

import unalcol.reflect.loader.ResourceLoaderTool;
import java.net.URL;
import java.io.File;
import java.net.MalformedURLException;
import java.io.FileInputStream;
import java.io.InputStream;

/**
 * <p>Tool used by the ClassLoader for loading resources from a given Path</p>
 *
 * @author Jonatan Gomez Perdomo
 * @version 1.0
 */
public class ResourcePathLoader implements ResourceLoaderTool {
        /**
         * Resources Path
         */
        protected String path;

        /** Makes a URL from a file
         * @param fil File to be converted to URL
         * @return A URL for the given file
         */
        public static URL make(File fil) throws MalformedURLException {
            URL url = new URL("file", "", fil.toString());
            return url;
        }

        /** Makes a URL from a file within a JAR
         * @param name Name of the resource to be converted to a URL
         * @param jar Jar File containing the resource
         * @return A URL for the given resource in the jar file
         */
        public static URL make(String name, File jar) throws MalformedURLException {
            StringBuilder filename = new StringBuilder("file:///");
            filename.append(jar.toString());
            filename.append("!/");
            filename.append(name);
            String sf = filename.toString();
            String sfu = sf.replace('\\', '/');
            URL url = new URL("jar", "", sfu);
            return url;
        }

        /**
         * Creates a tool used by the ClassLoader for loading resources from a given Path
         * @param path Resources Path
         */
        public ResourcePathLoader( String path ){
            this.path = path;
        }

        /**
         * Returns a resource from the path as a URL
         * @param name a resource name.
         * @return a resource from the path as a URL
         */
        @Override        
        public URL getResource(String name) throws MalformedURLException{
            return make(new File(path, name));
        }

        /**
         * Returns a resource from the path as an InputStream
         * @param name a resource name.
         * @return a resource from the path as an InputStream
         */
        @Override
        public InputStream getResourceAsStream(String name) throws Exception{
                return new FileInputStream(new File(path, name));
        }
}