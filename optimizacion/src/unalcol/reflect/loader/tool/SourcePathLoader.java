package unalcol.reflect.loader.tool;
import unalcol.reflect.compiler.Compiler;
import java.io.*;

/**
 * <p>Keep Track of java/class files maintained in the given Paths.
 * Compiles java source code when necessary (if updated or class file does not exist).</p>
 *
 * @author Jonatan Gomez Perdomo
 * @version 1.0
 */
public class SourcePathLoader extends PathLoader{
    /**
     * Sources path
     */
    protected String source_path = null;

    /**
     * Set of loaded jar files (Jar files)
     */
    protected MultiJarLoader libraries;

    /**
     * Java Compiler (used for compiling the Java Source code
     */
    protected Compiler compiler = new Compiler();

    /**
     * Creates a Source/Class Files manager on the given path (same source and class path)
     * @param source_path Source/class Path
     * @param libraries Set of loaded libraries (Jar files)
     */
    public SourcePathLoader(String source_path, MultiJarLoader libraries) {
        super( source_path );
        this.source_path = source_path;
        this.libraries = libraries;
    }

    /**
     * Creates a Source/Class Files manager on the given paths
     * @param source_path Source Path
     * @param class_path Class Path
     * @param libraries Set of loaded libraries (Jar files)
     */
    public SourcePathLoader(String source_path, String class_path,
            MultiJarLoader libraries) {
        super( class_path );
        this.source_path = source_path;
        this.libraries = libraries;
    }

    /** Loads the bytes from file
     * @param classname Name of the resource
     * @return Class bytes from the disk if possible, null otherwise.
     */
    @Override
    public byte[] loadBytes(String classname){
        String filename = classname.replace('.', javaEnv.fileSeparator());
        String javaFilename = filename + ".java";
        String classFilename = filename + ".class";

        File javaFile = new File(source_path, javaFilename);
        File classFile = new File(class_path, classFilename);

        // Checking if source code file is newer than class file
        if (javaFile.exists() &&
            (!classFile.exists() ||
             javaFile.lastModified() > classFile.lastModified())) {
            classFile.delete();
            if( !compile(javaFile.toString(), libraries) ){
                return null;
            }
           return super.loadBytes(classname);
        }
        return null;
    }

    /** Spawns a process to compile the java source code file specified in the
     * 'javaFile' parameter using the given set of Libraries (Jar files).
     * @param javaFile Java file to be compiled
     * @param libraries Set of loaded libraries (Jar files)
     * @return <i>true</i> if the compilation worked, <i>false</i> otherwise.
     */
    private boolean compile(String javaFile, MultiJarLoader libraries){
        compiler.clear();
        compiler.setOutputPath(class_path);
        compiler.addSourcePath(source_path);
        compiler.setLibraries(libraries);
        return compiler.run(javaFile);
    }

    /**
     * Gets the source path
     * @return Source path
     */
    public String sourcePath(){
        return source_path;
    }
}