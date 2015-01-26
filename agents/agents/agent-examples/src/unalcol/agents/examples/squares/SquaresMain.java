/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package unalcol.agents.examples.squares;

import unalcol.agents.Agent;
import unalcol.agents.examples.squares.BPS.Akuma;
import unalcol.agents.examples.squares.JOD.JODSquaresAgentProgram;
import unalcol.agents.examples.squares.aprendices.cuadritoAprendiz;
import unalcol.agents.examples.squares.cualquiercosa.CualquierCosaAgentProgram;
import unalcol.agents.examples.squares.dna.AgentsBossy;
import unalcol.agents.examples.squares.grupo7.Grupo7BoxesAgent;
import unalcol.agents.examples.squares.jota.AgenteJJSquares;
import unalcol.agents.examples.squares.mirror.Neo;
import unalcol.agents.examples.squares.quantal.QuantalSquare;
import unalcol.agents.examples.squares.ungrupo.UNGrupoAgentSquares;
import unalcol.reflect.service.ServiceProvider;
import unalcol.reflect.util.ReflectUtil;

/**
 *
 * @author Jonatan
 */
public class SquaresMain  {
  public static void main( String[] argv ){
    // Reflection
    
    Agent[] agents = new Agent[12];
    agents[0] = new Agent( new Akuma( Squares.WHITE ));
    agents[1] = new Agent( new JODSquaresAgentProgram( Squares.BLACK ));
    agents[2] = new Agent( new CualquierCosaAgentProgram( Squares.BLACK ));
    agents[3] = new Agent( new cuadritoAprendiz( Squares.BLACK ));
    agents[4] = new Agent( new AgentsBossy( Squares.WHITE ));
    agents[5] = new Agent( new Grupo7BoxesAgent( Squares.WHITE ));
    agents[6] = new Agent( new AgenteJJSquares( Squares.WHITE ));
    agents[7] = new Agent( new Neo( Squares.BLACK ));
    agents[8] = new Agent( new QuantalSquare( Squares.BLACK ));
    agents[9] = new Agent( new UNGrupoAgentSquares( Squares.WHITE ));
    agents[10] = new Agent( new DummySquaresAgentProgram( Squares.BLACK ));
    
    Agent w_agent = new Agent( new DummySquaresAgentProgram( Squares.WHITE ));
    Agent b_agent =  new Agent( new DummySquaresAgentProgram( Squares.BLACK ));
    //Agent b_agent = new Agent( new ReversiSinGrupoAPv2(Reversi.BLACK) );
    //Agent w_agent = new Agent( new NoTanDummiReversiAgentProgram(Reversi.WHITE) );
    SquaresMainFrame frame = new SquaresMainFrame( agents[5], agents[2] );
    frame.setVisible(true);
  }
    
}

