package unalcol.agents.examples.squares.grupo7;

import unalcol.agents.examples.squares.Squares;

public class TestMatrix {
	public static void main(String[] args) {
		Matrix m = new Matrix(4);
		m.addLine(new Line(0,1,Matrix.BOTTOM_C));
		m.addLine(new Line(0,2,Matrix.BOTTOM_C));
		m.addLine(new Line(1,0,Matrix.RIGHT_C));
		Matrix c = m.newState(new Line(0,1,Matrix.RIGHT_C), Squares.WHITE);
		System.out.println(c.white + " " + c.black);
	}
}
