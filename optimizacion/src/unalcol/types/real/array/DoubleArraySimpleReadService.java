/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package unalcol.types.real.array;
import unalcol.io.*;
import unalcol.types.integer.*;
import unalcol.types.real.*;
import java.io.*;
import unalcol.reflect.service.ServiceProvider;
import unalcol.reflect.util.ReflectUtil;

/**
 *
 * @author jgomez
 */
public class DoubleArraySimpleReadService implements ReadService<double[]>{
    /**
     * Character used for separating the values in the array
     */
    protected char separator = ' ';
    
    public static final int READ_DIMENSION = -1;
    public static final int USE_ARGUMENT_DIMENSION = -2;    
    protected int dim = READ_DIMENSION;

    protected IntegerReadService integer = new IntegerReadService();
    protected DoubleReadService real = new DoubleReadService();

    /**
     * Creates an integer array persistent method that uses an space for separatng the array values
     */
    public DoubleArraySimpleReadService() {}

    /**
     * Creates a double array persistent method that uses the give charater for separating the array values
     * @param separator Character used for separating the array values
     */
    public DoubleArraySimpleReadService(char separator) {
        this.separator = separator;
    }
    
    public DoubleArraySimpleReadService(int dim){
        this.dim = dim;
    }

    public DoubleArraySimpleReadService(int dim, char separator){
        this.dim = dim;
        this.separator = separator;
    }

    /**
     * Returns the Class that owns the PlugIn
     * @return Class The PlugIns owner class
     */
    public Object owner() {
        return double[].class;
    }
    
    public double[] read( double[] x, ShortTermMemoryReader reader ) throws IOException{
        int d = dim;
        if(d==USE_ARGUMENT_DIMENSION){
            d = x.length;
        }
        return read(reader, d);
    }
    
    public double[] read( ShortTermMemoryReader reader, int n ) throws IOException{
        if( n==READ_DIMENSION){
           n = (Integer)integer.read(reader);
           Persistency.readSeparator(reader, separator);
        }
        double[] d = new double[n];
        for( int i=0; i<n; i++ ){
            d[i] = (Double)real.read(reader);
            Persistency.readSeparator(reader, separator);
        }
        return d;
    }

    public static void main(String[] args ){
        // Reflection
        ServiceProvider provider = ReflectUtil.getProvider("services/");
//        StringReader r = new StringReader("  3,  -1234.4555e-123, 345.6789, 23.456");
//        DoubleArraySimpleReadService service = new DoubleArraySimpleReadService(',');
        StringReader r = new StringReader("  3  -1234.4555e-123 345.6789 23.456");
        ShortTermMemoryReader reader = new ShortTermMemoryReader(r);
        double[] x = new double[0];
        try{
           x = (double[])Persistency.read(x, reader);
           System.out.println(Persistency.toString(x));
        }catch(Exception e ){
            e.printStackTrace();
        }
    }

}
