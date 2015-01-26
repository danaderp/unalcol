/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package unalcol.agents.examples.reversi;

import unalcol.agents.Agent;
import unalcol.agents.examples.sudoku.naive.NaiveSudokuAgentProgram;
import unalcol.reflect.service.ServiceProvider;
import unalcol.reflect.util.ReflectUtil;

/**
 *
 * @author Jonatan
 */
public class ReversiMain {
  public static void main( String[] argv ){
    // Reflection
    ServiceProvider provider = ReflectUtil.getProvider("services/");
    Agent w_agent = null; //new Agent( new InteractiveAgentProgram( getLanguage() ) );
    Agent b_agent = null; //new Agent( new InteractiveAgentProgram( getLanguage() ) );
    //Agent b_agent = new Agent( new ReversiSinGrupoAPv2(Reversi.BLACK) );
    //Agent w_agent = new Agent( new NoTanDummiReversiAgentProgram(Reversi.WHITE) );
    ReversiMainFrame frame = new ReversiMainFrame( w_agent, b_agent );
    frame.setVisible(true);
  }
    
}
