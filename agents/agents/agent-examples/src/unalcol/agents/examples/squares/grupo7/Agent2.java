package unalcol.agents.examples.squares.grupo7;

import unalcol.agents.Action;
import unalcol.agents.AgentProgram;
import unalcol.agents.Percept;
import unalcol.agents.examples.squares.Squares;
import unalcol.agents.examples.squares.grupo7.Matrix2.Line;

public class Agent2 implements AgentProgram {

	Matrix2 matrix;
	String color;

	public Agent2(String color) {
		this.color = color;
	}
	
	@Override
	public Action compute(Percept p) {
		if(matrix == null)
			matrix = new Matrix2(Integer.parseInt(p.getAttribute(Squares.SIZE).toString()));

		
		//try{ Thread.sleep(300); }catch(Exception e){}
		
		if (p.getAttribute(Squares.TURN).equals(color)) {
			for (int i = 0; i < matrix.n; i++)
				for (int j = 0; j < matrix.n; j++) {
						if (((String) p.getAttribute(i + ":" + j + ":"
								+ Squares.BOTTOM)).equals(Squares.TRUE)) {
							matrix.addLine((i + 1)*2, j);
						}
						
						if (((String) p.getAttribute(i + ":" + j + ":"
								+ Squares.RIGHT)).equals(Squares.TRUE)) {
							matrix.addLine(i*2 + 1, j + 1);
						}					
				}
		}

		Line line = matrix.getRandomLine();
		if( line != null ){
			matrix.addLine(line.i, line.j);
			return new Action(line.toString());
		}
		
		try{ Thread.sleep(30000); }catch(Exception e){}
		return null;
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		
	}

}
