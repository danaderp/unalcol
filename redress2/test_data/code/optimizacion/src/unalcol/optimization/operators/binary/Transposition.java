package unalcol.optimization.operators.binary;

import unalcol.random.integer.*;
import unalcol.types.collection.bitarray.BitArray;
import unalcol.clone.*;
import unalcol.types.collection.vector.*;

/**
 * <p>Title: Transposition</p>
 * <p>Description: The simple transposition operator (without flanking)</p>
 * <p>Copyright: Copyright (c) 2010</p>
 * @author Jonatan Gomez
 * @version 1.0
 */

public class Transposition extends BinaryArityOne {
    public Transposition(){}
    
  /**
   * Interchange the bits between two positions randomly chosen
   * Example:      genome = 100011001110
   * Transposition 2-10:    101100110010
   * @param _genome Genome to be modified
   */
  public Vector<BitArray> generates(BitArray _genome) {
      try{
          BitArray genome = (BitArray) Clone.get(_genome);

          UniformIntegerGenerator gen = new UniformIntegerGenerator(genome.size());
          int start = gen.next();
          int end = gen.next();

          if (start > end) {
              int t = start;
              start = end;
              end = t;
          }
          boolean tr;

          while (start < end) {
              tr = genome.get(start);
              genome.set(start, genome.get(end));
              genome.set(end, tr);
              start++;
              end--;
          }
          Vector<BitArray> v = new Vector<BitArray>();
          v.add(genome);
          return v;
      }catch( Exception e ){}
      return null;
  }
}
