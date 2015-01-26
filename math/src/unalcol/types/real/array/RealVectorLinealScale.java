package unalcol.types.real.array;
import java.util.Iterator;
import unalcol.math.algebra.InvertableScale;
import unalcol.types.collection.array.ArrayCollection;
import unalcol.types.real.*;


public class RealVectorLinealScale extends InvertableScale<double[]> {
    protected double originalMin;
    protected ToZeroOneLinealScale[] scale;

    public RealVectorLinealScale( ArrayCollection<double[]> a ){
      double[] min = a.get(0).clone();
      double[] max = min.clone();
      double[] tmp;
      Iterator<double[]> iter = a.iterator();
      iter.next();
      while( iter.hasNext()){
          tmp = iter.next();
          for( int j=0; j<min.length; j++ ){
              if( tmp[j] < min[j] ){
                  min[j] = tmp[j];
              }else{
                  if( tmp[j] > max[j] ){
                      max[j] = tmp[j];
                  }
              }
          }
      }
        scale = new ToZeroOneLinealScale[min.length];
        for( int i=0; i<scale.length; i++ ){
            scale[i] = new ToZeroOneLinealScale(min[i], max[i]);
        }
      
    }
    
    public RealVectorLinealScale( double[] min, double[] max ){
        scale = new ToZeroOneLinealScale[min.length];
        for( int i=0; i<scale.length; i++ ){
            scale[i] = new ToZeroOneLinealScale(min[i], max[i]);
        }
    }


    public RealVectorLinealScale( double[] originalMin, double[] originalMax,
                             double[] targetMin, double[] targetMax ){
        scale = new ToZeroOneLinealScale[originalMin.length];
        for( int i=0; i<scale.length; i++ ){
            scale[i] = new LinealScale(originalMin[i], originalMax[i], 
                    targetMin[i], targetMax[i]);
        }
    }

    @Override
    public double[] fastApply( double[] x ){
        for( int i=0; i<x.length; i++ ){
            x[i] = scale[i].apply(x[i]);
        }
        return x;
    }

    @Override
    public double[] fastInverse( double[] x ){
        for( int i=0; i<x.length; i++ ){
            x[i] = scale[i].inverse(x[i]);
        }
        return x;
    }
}