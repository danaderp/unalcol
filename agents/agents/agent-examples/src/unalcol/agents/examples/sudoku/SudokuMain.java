package unalcol.agents.examples.sudoku;
import unalcol.agents.*;

import unalcol.agents.simulate.util.*;
import unalcol.agents.simulate.gui.*;
import unalcol.agents.examples.sudoku.naive.*;
import unalcol.reflect.service.ServiceProvider;
import unalcol.reflect.util.ReflectUtil;

public class SudokuMain {
  private static Language getLanguage(){
    return  new SudokuLanguage();
  }

  public static void main( String[] argv ){
    // Reflection
    ServiceProvider provider = ReflectUtil.getProvider("services/");
    //    Agent agent = new Agent( new InteractiveAgentProgram( getLanguage() ) );
    Agent agent = new Agent( new NaiveSudokuAgentProgram() );
    SudokuMainFrame frame = new SudokuMainFrame( agent, getLanguage() );
    frame.setVisible(true);
  }
}
