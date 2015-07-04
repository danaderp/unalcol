package unalcol.optimization.operators.real;
import unalcol.random.*;
import unalcol.types.collection.vector.*;
import unalcol.clone.*;

/**
 * <p>Title: FlipMutation</p>
 * <p>Description: Changes one component of the encoded double[] with the complement
 * in the interval defined for the component (x = min + max - x)</p>
 * <p>Copyright: Copyright (c) 2010</p>
 * @author Jonatan Gomez
 * @version 1.0
 */
public class FlipMutation extends RealArityOne {
  /**
   * Default constructor
   */
  public FlipMutation() {
  }

  /**
   * Modifies one component of the encoded double[] with the complement
   * in the interval defined for the component (x=min+max-x)
   * @param gen Genome to be modified
   * @return Index of the real modified
   */
  public Vector<double[]> generates(double[] gen) {
      try {
          double[] genome = (double[]) Clone.get(gen);
          int posX = Random.nextInt(genome.length);
          double prob = 1.0/gen.length;
          for( int pos=0; pos<genome.length; pos++ ){
              if( pos == posX /*Math.random() < prob*/ ){
                  double x = genome[pos];
                  genome[pos] = 1.0 - x;
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