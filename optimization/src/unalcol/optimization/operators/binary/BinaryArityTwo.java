package unalcol.optimization.operators.binary;
import unalcol.optimization.operators.ArityTwo;
import unalcol.types.collection.bitarray.BitArray;

/**
 * <p>Title:  BinaryArityTwo</p>
 * <p>Description: An Arity Two binary genetic operator</p>
 * <p>Copyright: Copyright (c) 2010</p>
 * @author Jonatan Gomez
 * @version 1.0
 */
public abstract class BinaryArityTwo extends ArityTwo<BitArray>{
  /**
   * Class of objects the operator is able to process
   * @return Class of objects the operator is able to process
   */
  public Object owner(){
      return BitArray.class;
  }
}
