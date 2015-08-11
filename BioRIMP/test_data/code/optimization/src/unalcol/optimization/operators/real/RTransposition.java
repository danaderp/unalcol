/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package unalcol.optimization.operators.real;
import unalcol.random.*;
import unalcol.types.collection.vector.*;
import unalcol.clone.*;
import unalcol.random.integer.*;

/**
 *
 * @author jgomez
 */
public class RTransposition  extends RealArityOne {
    public RTransposition(){}
    
  /**
   * Interchange the bits between two positions randomly chosen
   * Example:      genome = 100011001110
   * Transposition 2-10:    101100110010
   * @param _genome Genome to be modified
   */
  public Vector<double[]> generates(double[] gen) {
      try {
          double[] genome = (double[]) Clone.get(gen);

          UniformIntegerGenerator g = new UniformIntegerGenerator(genome.length);
          int start = g.next();
          int end = g.next();

          if (start > end) {
              int t = start;
              start = end;
              end = t;
          }
          
          double tr;

          while (start < end) {
              tr = genome[start];
              genome[start] = genome[end];
              genome[end] = tr;
              start++;
              end--;
          }
          Vector<double[]> v = new Vector<double[]>();
          v.add(genome);
          return v;
      }catch( Exception e ){}
      return null;
  }
    
}
