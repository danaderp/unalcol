/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package unalcol.reflect.loader;
import java.net.*;
import java.io.*;

/**
 * <p>Resources manager for the Loader</p>
 *
 * @author Jonatan Gomez Perdomo
 * @version 1.0
 */
public interface ResourceLoaderTool {
        /**
         * Returns a resource as a URL
         * @param name a resource name.
         * @return a resource as a URL
         */
        public URL getResource(String name) throws MalformedURLException;

        /**
         * Returns a resource as an InputStream
         * @param name a resource name.
         * @return a resource as an InputStream
         */
        public InputStream getResourceAsStream(String name) throws Exception;
}
