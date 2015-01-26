package unalcol.agents.examples.squares.grupo7;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import unalcol.agents.examples.squares.Squares;

public class Matrix2 {

	static final int LEFT = 0;
	static final int RIGHT = 1;
	static final int TOP = 2;
	static final int BOTTOM = 3;

	int n;
	Line board[][];
	public List<Line> possibleLines;
	
	public Matrix2(int n) {
		int maxLines = 2 * n * n - 2 * n;
		possibleLines = new ArrayList<>(maxLines);
		this.n = n;
		board = new Line[2*n + 1][n + 1];
		
		for ( int i = 0; i < 2*n + 1; i++ )
			for ( int j = 0; j < n + 1; j++ )
				board[i][j] = new Line(i, j);

		/* Lineas del cuadro externo */
		for ( int i = 1; i < 2*n; i += 2 ) {
			board[i][0].turned = true;
			board[i][n].turned = true;
		}
		
		for ( int i = 0; i < n; i++ ) {
			board[0][i].turned = true;
			board[2*n][i].turned = true;
		}

		for (int i = 1; i < 2*n; i++) {
			if ( i % 2 == 0 )
				for ( int j = 0; j < n; j++ ) {
					possibleLines.add(board[i][j]);
					board[i][j].generateNeighbors();
				}
			else
				for ( int j = 1; j < n; j++ ) {
					possibleLines.add(board[i][j]);
					board[i][j].generateNeighbors();
				}
		}
	}

	public void addLine(int i, int j) {
		board[i][j].turned = true;		
	}

	public Line getRandomLine() {
		Random r = new Random();
		while (possibleLines.size() > 0) {
			Line line = possibleLines.remove(r.nextInt(possibleLines.size()));
			if (!isDumb(line)) {
				//System.out.println(line);
				return line;
			}
		}
		
		return null;
	}

	private boolean isDumb(Line line) {
		if ( line.i % 2 == 0 ) {
			int turnedTop = 0;
			if ( board[line.i - 1][line.j].turned ) turnedTop++;
			if ( board[line.i - 1][line.j + 1].turned ) turnedTop++;
			if ( board[line.i - 2][line.j].turned ) turnedTop++;
			
			if ( turnedTop >= 2 ) return true;
			
			int turnedBottom = 0;
			if ( board[line.i + 1][line.j].turned ) turnedBottom++;
			if ( board[line.i + 1][line.j + 1].turned ) turnedBottom++;
			if ( board[line.i + 2][line.j].turned ) turnedBottom++;
			
			return turnedBottom >= 2;
		}
		
		int turnedRight = 0;
		if ( board[line.i - 1][line.j].turned ) turnedRight++;
		if ( board[line.i + 1][line.j].turned ) turnedRight++;
		if ( board[line.i][line.j + 1].turned ) turnedRight++;
		
		if ( turnedRight >= 2 ) return true;
		
		int turnedLeft = 0;
		if ( board[line.i - 1][line.j - 1].turned ) turnedLeft++;
		if ( board[line.i + 1][line.j - 1].turned ) turnedLeft++;
		if ( board[line.i][line.j - 1].turned ) turnedLeft++;
		
		return turnedLeft >= 2;
	}
	/*
	public List<Line> evaluationLines ( ) {
		boolean copy[][] = new boolean[2*n + 1][n + 1];
		for ( int i = 0; i < 2*n + 1; i++ )
			for ( int j = 0; j < n + 1; j++ )
				copy[i][j] = board[i][j];
		
		for ( int i = 0; i < 2*n + 1; i++ )
			for ( int j = 0; j < n + 1; j++ ) {
				if ( !board[i][j] ) {
					ArrayList<Line> 
				}
			}
		return null;
	}
	*/
	public class Line {
		int i, j, I, J;
		boolean turned;
		String string;
		List<Line> neighbors;
		
		public Line(int i, int j) {
			this.i = i;
			this.j = j;
			
			if ( i % 2 == 0 ) {
				I = i/2 - 1;
				J = j;
				string = I+":"+J+":"+Squares.BOTTOM;
			} else {
				I = i/2;
				J = j - 1;
				string = I+":"+J+":"+Squares.RIGHT;
			}
			turned = false;
		}
		
		public String toString() {
			return string;
		}
		
		public void generateNeighbors() {
			neighbors = new ArrayList<>(6);
			
			if ( i % 2 == 0 ) {
				neighbors.add( board[i - 1][j] );
				neighbors.add( board[i - 1][j + 1] );
				neighbors.add( board[i - 2][j] );
				neighbors.add( board[i + 1][j] );
				neighbors.add( board[i + 1][j + 1] );
				neighbors.add( board[i + 2][j] );
			} else {
				neighbors.add( board[i - 1][j] );
				neighbors.add( board[i + 1][j] );
				neighbors.add( board[i][j + 1] );
				neighbors.add( board[i - 1][j - 1] );
				neighbors.add( board[i + 1][j - 1] );
				neighbors.add( board[i][j - 1] );
			}
		}
	}
}
