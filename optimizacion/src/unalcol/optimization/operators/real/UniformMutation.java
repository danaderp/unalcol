package unalcol.optimization.operators.real;
import unalcol.random.*;
import unalcol.types.collection.vector.*;
import unalcol.clone.*;

/**
 * <p>Title: UniformMutation</p>
 * <p>Description: A uniform mutation of a single component. The new value is generated
* in the interval defined for the component being modified</p>
 * <p>Copyright: Copyright (c) 2010</p>
 * @author Jonatan Gomez
 * @version 1.0
 */

public class UniformMutation extends RealArityOne {
  
  /**
   * Default constructor
   */
  public UniformMutation() {
  }

  /**
   * Modifies the number in a random position for a guassian value with mean
   * thevalue encoded in the genome and sigma given as attribute
   * @param gen Genome to be modified
   * @return Index of the real modified
   */
  public Vector<double[]> generates(double[] gen) {
      try {
          double[] genome = (double[]) Clone.get(gen);
          int posX = Random.nextInt(genome.length);
          double prob = 1.0/gen.length;
          for( int pos=0; pos<genome.length; pos++ ){
              if(  pos == posX /*Math.random() < prob*/ ){
                  double x = genome[pos] + Random.random();
                  if (x < 0.0) {
                      x = 0.0;
                  } else {
                      if (x > 1.0) {
                          x = 1.0;
                      }
                  }
                  genome[pos] = x;
              }
          }    
          Vector<double[]> v = new Vector<double[]>();
          v.add(genome);
          return v;
      } catch (Exception e) {
      }
      return null;
  }

}