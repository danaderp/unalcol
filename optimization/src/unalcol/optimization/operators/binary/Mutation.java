package unalcol.optimization.operators.binary;

import unalcol.random.util.*;
import unalcol.types.collection.bitarray.BitArray;
import unalcol.types.collection.vector.*;
import unalcol.clone.*;

/**
 * <p>Title: Mutation</p>
 * <p>Description: The simple bit mutation operator</p>
 * <p>Copyright: Copyright (c) 2010</p>
 * @author Jonatan Gomez
 * @version 1.0
 */

public class Mutation extends BinaryArityOne {
  /**
   * Probability of mutating one single bit
   */
  protected double bit_mutation_rate = 0.0;

  /**
   * Constructor: Creates a mutation with a mutation probability depending on the size of the genome
   */
  public Mutation() {}

  /**
   * Constructor: Creates a mutation with the given mutation rate
   * @param bit_mutation_rate Probability of mutating each single bit
   */
  public Mutation(double bit_mutation_rate) {
    this.bit_mutation_rate = bit_mutation_rate;
  }

  /**
   * Flips a bit in the given genome
   * @param gen Genome to be modified
   * @return Number of mutated bits
   */
  public Vector<BitArray> generates(BitArray gen) {
    try{
      BitArray genome = (BitArray) Clone.get(gen);
      int count = 0;
      double rate = 1.0 - ((bit_mutation_rate == 0.0)?1.0/genome.size():bit_mutation_rate);
      BooleanGenerator g = new BooleanGenerator(rate);
      for (int i = 0; i < genome.size(); i++) {
        if (g.next()) {
          genome.not(i);
          count++;
        }
      }
      Vector<BitArray> v = new Vector<BitArray>();
      v.add(genome);
      return v;
    }catch( Exception e ){ 
        e.printStackTrace();
        System.err.println("[Mutation]"+e.getMessage()); }
    return null;
  }



 /**
  * Testing function
  */
  public static void main(String[] argv){
    System.out.println("*** Generating a genome of 21 genes randomly ***");
    BitArray genome = new BitArray(21, true);
    System.out.println(genome.toString());

    Mutation mutation = new Mutation(0.05);

    System.out.println("*** Applying the mutation ***");
    BitArray mutated = mutation.generates(genome).get(0);
    System.out.println("Mutated array " + mutated );

  }

}
