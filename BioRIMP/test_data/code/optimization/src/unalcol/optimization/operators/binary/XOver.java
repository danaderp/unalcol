package unalcol.optimization.operators.binary;
import unalcol.clone.*;
import unalcol.random.*;
import unalcol.types.collection.bitarray.BitArray;
import unalcol.types.collection.vector.*;

/**
 * <p>Title: XOver</p>
 * <p>Description: The simple point crossover operator (variable length)</p>
 * <p>Copyright: Copyright (c) 2010</p>
 * @author Jonatan Gomez
 * @version 1.0
 */

public class XOver extends BinaryArityTwo {
    public XOver(){}

  /**
   * The crossover point of the last xover execution
   */
  protected int cross_over_point;

  /**
   * Apply the simple point crossover operation over the given genomes at the given
   * cross point
   * @param child1 The first parent
   * @param child2 The second parent
   * @param xoverPoint crossover point
   * @return The crossover point
   */
  public Vector<BitArray> generates(BitArray child1, BitArray child2, int xoverPoint) {
      try{
          BitArray child1_1 = (BitArray) Clone.get(child1);
          BitArray child2_1 = (BitArray) Clone.get(child2);
          BitArray child1_2 = (BitArray) Clone.get(child1);
          BitArray child2_2 = (BitArray) Clone.get(child2);

          cross_over_point = xoverPoint;

          child1_2.leftSetToZero(cross_over_point);
          child2_2.leftSetToZero(cross_over_point);
          child1_1.rightSetToZero(cross_over_point);
          child2_1.rightSetToZero(cross_over_point);
          child1_2.or(child2_1);
          child2_2.or(child1_1);
          Vector v = new Vector();
          v.add(child1_2);
          v.add(child2_2);
          return v;
      }catch( Exception e ){}
      return null;
  }

  /**
   * Apply the simple point crossover operation over the given genomes
   * @param child1 The first parent
   * @param child2 The second parent
   * @return The crossover point
   */
  public Vector<BitArray> generates( BitArray child1, BitArray child2 ){
    return generates(child1, child2, Random.nextInt(Math.min(child1.size(), child2.size())));
  }

 /**
  * Testing function
  */
  public static void main(String[] argv){
    System.out.println("*** Generating a genome of 20 genes randomly ***");
    BitArray parent1 = new BitArray(20, true);
    System.out.println(parent1.toString());

    System.out.println("*** Generating a genome of 10 genes randomly ***");
    BitArray parent2 = new BitArray(10, true);
    System.out.println(parent2.toString());

    XOver xover = new XOver();

    System.out.println("*** Applying the croosover ***");
    Object pos = xover.generates(parent1, parent2);
    System.out.println("Position " + pos);

    System.out.println("*** Child 1 ***");
    System.out.println(parent1.toString());
    System.out.println("*** Child 2 ***");
    System.out.println(parent2.toString());

    System.out.println("*** Applying the croosover with parent2, parent1 ***");
    pos = xover.generates(parent2, parent1);
    System.out.println("Position " + pos);

    System.out.println("*** Child 1 ***");
    System.out.println(parent1.toString());
    System.out.println("*** Child 2 ***");
    System.out.println(parent2.toString());
  }
}
