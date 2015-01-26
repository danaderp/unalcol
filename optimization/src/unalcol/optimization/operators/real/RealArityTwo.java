/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package unalcol.optimization.operators.real;
import unalcol.optimization.operators.ArityTwo;

/**
 * <p>Title:  RealArityTwo</p>
 * <p>Description: An Arity Two real vector genetic operator</p>
 * <p>Copyright: Copyright (c) 2010</p>
 * @author Jonatan Gomez
 * @version 1.0
 */
public abstract class RealArityTwo extends ArityTwo<double[]>{
  /**
   * Class of objects the operator is able to process
   * @return Class of objects the operator is able to process
   */
  public Object owner(){
      return double[].class;
  }
}