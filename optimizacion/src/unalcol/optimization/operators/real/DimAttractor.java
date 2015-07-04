/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
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
public class DimAttractor extends RealArityOne {
  /**
   * Default constructor
   */
  public DimAttractor() {
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
          int pos1 = Random.nextInt(genome.length);
          int pos2 = Random.nextInt(genome.length);
          if( genome[pos1] < genome[pos2] ){
              double length = (genome[pos2] - genome[pos1])/2.0;
              genome[pos1] += length * Random.random();
              genome[pos2] -= length * Random.random();
          }else{
              double length = (genome[pos1] - genome[pos2])/2.0;
              genome[pos2] += length * Random.random();
              genome[pos1] -= length * Random.random();
          }
          Vector<double[]> v = new Vector<double[]>();
          v.add(genome);
          return v;
      } catch (Exception e) {
      }
      return null;
  }
}
