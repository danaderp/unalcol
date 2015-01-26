package unalcol.agents.search.classic;
import unalcol.sort.*;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: Universidad Nacional de Colombia</p>
 *
 * @author Jonatan Gómez
 * @version 1.0
 */
public class ClassicSearchNodeOrder implements Order<ClassicSearchNode>{
  public String getCanonicalName(){
    return ClassicSearchNode.class.getCanonicalName();
  }

  public ClassicSearchNodeOrder() {
  }

  public Class base(){
    return Order.class;
  }

  public Class owner(){
    return Order.class;
  }

  public int compare( ClassicSearchNode one, ClassicSearchNode two ){
    return (int)(one.cost - two.cost);
  }
}
