package unalcol.optimization.operators;
import unalcol.reflect.service.Service;
import unalcol.algorithm.*;
import unalcol.optimization.generation.RepairProvider;
import unalcol.types.collection.vector.*;

/**
 * <p>Title: Operator</p>
 * <p>Description: An abstract version of a genetic operator. A genetic operator
 * uses a collection (Vector) of genomes for producing a collection (Vector) of genomes </p>
 * <p>Copyright: Copyright (c) 2010</p>
 * @author Jonatan Gomez
 * @version 1.0
 */
public abstract class Operator<T> extends ThreadFunction<Vector<T>,Vector<T>>
        implements Service{
  /**
   * Return the genetic operator arity (number of genomes required by the genetic
   * operator for producing new genomes
   * @return the genetic operator arity
   */
  public abstract int getArity();

  public Vector<T> apply(Vector<T> pop){
      pop = generates(pop);
      for( int i=0; i<pop.size(); i++ ){
        pop.set( i, (T)RepairProvider.repair(pop.get(i)) );
      } 
      return pop;
  }
  
  public abstract Vector<T> generates(Vector<T> pop);
  
  public Class base(){ return Operator.class; }

}