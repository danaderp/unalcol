package unalcol.optimization.operators.binary;
import unalcol.optimization.operators.ArityOne;
import unalcol.types.collection.bitarray.BitArray;

/**
 * <p>Title:  BinaryArityOne</p>
 * <p>Description: An Arity One binary genetic operator</p>
 * <p>Copyright: Copyright (c) 2010</p>
 * @author Jonatan Gomez
 * @version 1.0
 */
public abstract class BinaryArityOne extends ArityOne<BitArray>{
  /**
   * Class of objects the operator is able to process
   * @return Class of objects the operator is able to process
   */
  public Object owner(){
      return BitArray.class;
  }
}