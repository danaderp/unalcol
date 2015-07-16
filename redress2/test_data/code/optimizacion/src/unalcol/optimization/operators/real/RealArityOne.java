package unalcol.optimization.operators.real;
import unalcol.optimization.operators.ArityOne;

/**
 * <p>Title:  RealArityOne</p>
 * <p>Description: An Arity One real vector genetic operator</p>
 * <p>Copyright: Copyright (c) 2010</p>
 * @author Jonatan Gomez
 * @version 1.0
 */
public abstract class RealArityOne extends ArityOne<double[]>{
  /**
   * Class of objects the operator is able to process
   * @return Class of objects the operator is able to process
   */
  public Object owner(){
      return double[].class;
  }
}
