package unalcol.optimization.operators.binary;

import unalcol.random.*;
import unalcol.types.collection.bitarray.BitArray;
import unalcol.clone.*;
import unalcol.types.collection.vector.*;

/**
 * <p>Title: SingleBitMutation</p>
 * <p>Description: Flips one bit in the chromosome. The flipped bit is randomly selected
 * with uniform probability distribution</p>
 * <p>Copyright: Copyright (c) 2010</p>
 * @author Jonatan Gomez
 * @version 1.0
 */

public class SingleBitMutation extends BinaryArityOne {

  /**
   * Flips a bit in the given genome
   * @param genome Genome to be modified
   * @return Index of the flipped bit
   */
  public Vector<BitArray> generates(BitArray genome) {
      try {
          genome = (BitArray) Clone.get(genome);
          int pos = -1;
          try {
              pos = Random.nextInt(genome.size());
              genome.not(pos);
          } catch (Exception e) {
              System.err.println("[Mutation]" + e.getMessage());
          }
          Vector<BitArray> v = new Vector<BitArray>();
          v.add(genome);
          return v;
      } catch (Exception e) {}
      return null;
  }

 /**
  * Testing function
  */
  public static void main(String[] argv){
      System.out.println("*** Generating a genome of 21 genes randomly ***");
      BitArray genome = new BitArray(21, true);
      System.out.println(genome.toString());


      SingleBitMutation mutation = new SingleBitMutation();

      System.out.println("*** Applying the mutation ***");
      BitArray mutated = mutation.generates(genome).get(0);
      System.out.println("Mutated array " + mutated );
   }

}
