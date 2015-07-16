package unalcol.optimization.operators.binary.varlength;
import unalcol.optimization.operators.binary.BinaryArityOne;
import unalcol.random.*;
import unalcol.types.collection.bitarray.BitArray;
import unalcol.types.collection.vector.*;
import unalcol.clone.*;

/**
 * <p>Title: AddGen</p>
 * <p>Description: The gene addition operator. Add a gene generated randomly</p>
 * <p>Copyright: Copyright (c) 2010</p>
 * @author Jonatan Gomez
 * @version 1.0
 */

public class AddGen extends BinaryArityOne {
  /**
   * If the added gene is added to the end of the genome or not (randomly added)
   */
  protected boolean append = true;

  protected int gene_size;
  protected int min_length;
  protected int max_length;

  public AddGen(int gene_size, int min_length, int max_length) {
      this.gene_size = gene_size;
      this.min_length = min_length;
      this.max_length = max_length;
  }

  /**
   * Constructor: creates an addition gene operator that adds a gene according to
   * the variable _append
   * @param append If the added gene is added to the end of the genome or not (randomly added)
   */
  public AddGen(int gene_size, int min_length, int max_length, boolean append) {
      this(gene_size, min_length, max_length);
      this.append = append;
  }

  /**
   * Add to the end of the given genome a new gene
   * @param gen Genome to be modified
   * @return The added gene or a String
   */
  public Vector<BitArray> generates(BitArray gen) {
      try{
          BitArray genome = (BitArray) Clone.get(gen);
          if (genome.size() < max_length) {
              BitArray gene = new BitArray(gene_size, true);
              if (append) {
                  genome.add(gene);
              } else {
                  int size = (genome.size() - min_length) / gene_size;
                  int k = Random.nextInt(size + 1);
                  if (k == size) {
                      genome.add(gene);
                  } else {
                      BitArray right = genome.subBitArray(min_length +
                              k * gene_size);
                      genome.del((size - k) * gene_size);
                      genome.add(gene);
                      genome.add(right);
                  }
              }
          }
          Vector<BitArray> v = new Vector<BitArray>();
          v.add(genome);
          return v;
      }catch( Exception e ){
          e.printStackTrace();
      }
      return null;
  }

 /**
  * Testing function
  * */
  public static void main(String[] argv){
    System.out.println("*** Generating a genome of 21 genes randomly ***");
    BitArray genome = new BitArray(21, true);
    System.out.println(genome.toString());

    System.out.println("*** Generating a Addition Gen operation with gen length of 3 ***");
    AddGen add = new AddGen(21, 27, 3);
    System.out.println("*** Applying the addition ***");
    BitArray gene = add.generates(genome).get(0);

    System.out.println("*** Mutated genome ***");
    System.out.println(gene);
  }
}
