package unalcol.reflect.loader.tool;
import unalcol.reflect.loader.*;
import unalcol.types.collection.vector.*;

/**
 * <p>Keeps Track of paths conntaining java class, source or resources.</p>
 *
 * @author Jonatan Gomez Perdomo
 * @version 1.0
 */
public class MultiPathLoader implements LoaderTool{
    protected Vector<PathLoader> paths = new Vector<>();
    
    public MultiPathLoader(){
    }

    /** Loads the bytes from file
     * @param classname Name of the resource
     * @return Class bytes from the disk if possible, null otherwise.
     */
    @Override
    public byte[] loadBytes(String classname){

        byte[] buf = null;
        int n = paths.size();
        if( n > 0 ){
            int i=0;
            while( i<n && buf == null ){
                buf = paths.get(i).loadBytes(classname);
                i++;
            }
        }
        return buf;
    }

    /**
     * Add a new path to the paths collection
     * @param path Path to be added
     */
    public void add( String path ){
        paths.add(new PathLoader(path) );
    }

    /**
     * Gets the path names
     * @return  Path Names
     */
    public String[] paths(){
        String[] pths = new String[this.paths.size()];
        for( int i=0; i<this.paths.size(); i++){
            pths[i] = this.paths.get(i).path();
        }
        return pths;
    }

    @Override
    public void clear(){
        paths.clear();
    }
}
