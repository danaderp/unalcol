package unalcol.agents.examples.labyrinth.teseoeater;

import unalcol.agents.*;
import unalcol.agents.simulate.*;
import unalcol.agents.simulate.util.*;

import java.awt.*;
import unalcol.agents.simulate.gui.*;
import unalcol.agents.examples.labyrinth.*;

/**
 * Panel to draw a fuzzy space, and highlight a specific set and value
 */
public class TeseoEaterLabyrinthDrawer extends LabyrinthDrawer{
  public Color[] energy_colors = new Color[]{
      Color.red, Color.orange, Color.yellow, Color.blue, Color.green, Color.green
  };



  /**
   * Default constructor
   */
  public TeseoEaterLabyrinthDrawer( Environment _environment ) {
      super( _environment );
  }

  /**
   * Default constructor
   */
  public TeseoEaterLabyrinthDrawer() {
  }

  protected int getCanvasValue( int val ){
    return val*CELL_SIZE+MARGIN;
  }


  /**
   * Paints the graphic component
   * @param g Graphic component
   */
  public void paint(Graphics g){
    super.paint(g);
    if( environment != null ){
      int energy_level = ( (TeseoEaterLabyrinth) environment).energy_level;
      Color e_color = energy_colors[energy_level * (energy_colors.length - 1) /
          TeseoEaterLabyrinth.MAX_ENERGY_LEVEL];
      g.setColor(e_color);
      Labyrinth env = (Labyrinth) environment;
      SimulatedAgent agent = (SimulatedAgent)this.environment.getAgent();
      int x = ( (Integer) agent.getAttribute(env.X)).intValue();
      int y = ( (Integer) agent.getAttribute(env.Y)).intValue();
      int X = getCanvasValue(x);
      int Y = getCanvasValue(y);
      int DELTA = CELL_SIZE / 8;
      g.fillOval(X + 2*DELTA, Y + 2*DELTA, CELL_SIZE - 4 * DELTA,
                 CELL_SIZE - 4 * DELTA);
    }
  }

}
