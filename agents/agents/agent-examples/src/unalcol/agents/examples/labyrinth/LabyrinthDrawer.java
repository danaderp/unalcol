package unalcol.agents.examples.labyrinth;

import unalcol.agents.simulate.*;

import java.awt.*;
import unalcol.agents.simulate.gui.*;


/**
 * Panel to draw a fuzzy space, and highlight a specific set and value
 */
public class LabyrinthDrawer extends Drawer{
  public static int DIMENSION = 15;
  public static int DRAW_AREA_SIZE = 400;
  public static int CELL_SIZE = 20;
  public static int MARGIN = 10;
  public static int SHAPE_SIZE = 1;


  public Color[] colors = new Color[]{
      Color.black, Color.blue, Color.red, Color.yellow,
      Color.cyan, Color.green,
      Color.pink,  Color.orange, Color.magenta,
      Color.gray, Color.darkGray, Color.lightGray
  };

  /**
   * Default constructor
   */
  public LabyrinthDrawer( Environment _environment ) {
      super( _environment );
  }

  /**
   * Default constructor
   */
  public LabyrinthDrawer() {
  }

  protected int getCanvasValue( int val ){
    return val*CELL_SIZE+MARGIN;
  }

  public void setDimension( int width, int height ){
    DRAW_AREA_SIZE = Math.min(width,height);
    CELL_SIZE = DRAW_AREA_SIZE / (DIMENSION+1);
    MARGIN = CELL_SIZE / 2;
  }


  /**
   * Paints the graphic component
   * @param g Graphic component
   */
  public void paint(Graphics g){
    if( environment != null && ((Labyrinth)environment).structure != null ){
      Labyrinth   env = (Labyrinth)environment;
      int n = env.structure.length;
      int m = env.structure[0].length;
      g.setColor( Color.lightGray );
      int GRID_SIZE = CELL_SIZE * DIMENSION;
      for (int i = 0; i <= n; i++) {
        int j = getCanvasValue( i );
        g.drawLine( j, GRID_SIZE + MARGIN, j, MARGIN );
        g.drawLine( GRID_SIZE + MARGIN, j, MARGIN, j );
      }
      for (int i = 0; i < n; i++) {
        for (int j = 0; j < m; j++) {
          g.setColor( Color.blue );
          LabyrinthPercept p = env.getPercept(i,j);
          int x = getCanvasValue(i);
          int y = getCanvasValue(j);
          p.draw( g, x, y, CELL_SIZE, env.language );
        }
      }

      for( int i=0; i<this.environment.agentsNumber(); i++){
        SimulatedAgent agent = (SimulatedAgent)this.environment.getAgent(i);
  //      System.out.println( "The agent program is : " + agent.getProgram().getClass().getCanonicalName() );
        int direction = ( (Integer) agent.getAttribute(env.D)).intValue();
        int x = ( (Integer) agent.getAttribute(env.X)).intValue();
        int y = ( (Integer) agent.getAttribute(env.Y)).intValue();
        g.setColor( colors[i%colors.length] );
        int X = getCanvasValue( x );
        int Y = getCanvasValue( y );
        int EYE_SIZE = 6;
        g.drawOval( X + EYE_SIZE/2, Y + EYE_SIZE/2, CELL_SIZE - EYE_SIZE, CELL_SIZE - EYE_SIZE );
        switch( direction ){
          case 0: g.drawOval( X + CELL_SIZE/2 - EYE_SIZE/2, Y , EYE_SIZE, EYE_SIZE); break;
          case 1: g.drawOval( X +  CELL_SIZE - EYE_SIZE, Y + CELL_SIZE/2 - EYE_SIZE/2, EYE_SIZE, EYE_SIZE); break;
          case 2: g.drawOval( X + CELL_SIZE/2 - EYE_SIZE/2, Y + CELL_SIZE - EYE_SIZE, EYE_SIZE, EYE_SIZE); break;
          case 3: g.drawOval( X, Y + CELL_SIZE/2 - EYE_SIZE/2, EYE_SIZE, EYE_SIZE); break;
        }
      }
    }

  }

}
