package unalcol.agents.examples.squares.grupo7;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Collections;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;
import java.util.Stack;

import unalcol.agents.examples.squares.Squares;

public class Matrix implements Serializable {

	public final static int LEFT_C = 0;

	public static final int RIGHT_C = 1;

	public static final int TOP_C = 2;

	public static final int BOTTOM_C = 3;

	int n, white, black;
	Box board[][];
	public List<Line> possibleLines;

	public Matrix(int n) {
		int maxLines = 2 * n * n - 2 * n;
		possibleLines = new ArrayList<>(maxLines);
		this.n = n;
		board = new Box[n][n];
		white = black = 0;

		initBoardOnly();
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if (i < n - 1 && j < n - 1) {
					possibleLines.add(new Line(i, j, BOTTOM_C));
					possibleLines.add(new Line(i, j, RIGHT_C));
				} else if (i < n - 1) {
					possibleLines.add(new Line(i, j, BOTTOM_C));
				} else if (j < n - 1) {
					possibleLines.add(new Line(i, j, RIGHT_C));
				} else {
					System.out.println(i + " " + j);
				}
			}
		}
	}

	public void initBoardOnly() {
		for (int i = 0; i < n; i++)
			for (int j = 0; j < n; j++)
				board[i][j] = new Box();

		/* Lineas del cuadro externo */
		for (int i = 0; i < board.length; i++) {
			board[0][i].setTop(true);
			board[i][0].setLeft(true);
			board[board.length - 1][i].setBottom(true);
			board[i][board.length - 1].setRight(true);
		}

		white = black = 0;
	}

	public void colorIfNeeded(String perception) {
		if (perception.equals(Squares.WHITE))
			white++;
		else if (perception.equals(Squares.BLACK))
			black++;
	}

	public void addLine(int i, int j, String side) {
		if (isLine(i, j, side))
			return;

		if (side.equals(Squares.LEFT)) {
			if (j > 0)
				board[i][j - 1].setRight(true);
			board[i][j].setLeft(true);
		} else if (side.equals(Squares.RIGHT)) {
			if (j < n - 1)
				board[i][j + 1].setLeft(true);
			board[i][j].setRight(true);
		} else if (side.equals(Squares.TOP)) {
			if (i > 0)
				board[i - 1][j].setBottom(true);
			board[i][j].setTop(true);
		} else {
			if (i < n - 1)
				board[i + 1][j].setTop(true);
			board[i][j].setBottom(true);
		}
	}

	public boolean isLine(int i, int j, String side) {
		return board[i][j].getSide(side);
	}

	public Line getRandomLine() {
		Random r = new Random();
		// System.out.println(possibleLines);
		while (possibleLines.size() > 0) {

			Line line = possibleLines.remove(r.nextInt(possibleLines.size()));
			if (!isDumb(line)) {
				return line;
			}
		}

		return null;
		/*
		 * for (int i = 0; i <n; i++) { for (int j = 0; j <n; j++) {
		 * System.out.println( " " + i + " " + j +" "+ board[i][j] ); } }
		 * System.out.println("---"); for(Line l : aux){ System.out.println(l);
		 * } throw new
		 * IllegalArgumentException("Se acabo la lista, criptoperrito.");
		 */

	}

	private void print() {
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board.length; j++) {
				System.out.print(board[i][j] + "|");
			}
			System.out.println();
		}

		System.out.println();
	}

	public boolean isDumb(Line line) {
		int i = line.i, j = line.j, side = line.side;
		Box box = board[i][j];
		Box box2 = null;

		switch (side) {
		case LEFT_C:
			if (box.left)
				return true;

			box2 = board[i][j - 1];
			if (box2.right)
				return true;
			break;

		case RIGHT_C:
			if (box.right)
				return true;

			box2 = board[i][j + 1];
			if (box2.left)
				return true;
			break;

		case TOP_C:
			if (box.top)
				return true;

			box2 = board[i - 1][j];
			if (box2.bottom)
				return true;
			break;

		case BOTTOM_C:
			if (box.bottom)
				return true;

			box2 = board[i + 1][j];
			if (box2.top)
				return true;
			break;
		}

		return box.turnedSides >= 2 || box2.turnedSides >= 2;
	}

	public void addLine(Line line) {
		switch (line.side) {
		case LEFT_C:
			addLine(line.i, line.j, Squares.LEFT);
			break;
		case RIGHT_C:
			addLine(line.i, line.j, Squares.RIGHT);
			break;
		case TOP_C:
			addLine(line.i, line.j, Squares.TOP);
			break;
		case BOTTOM_C:
			addLine(line.i, line.j, Squares.BOTTOM);
			break;
		}
	}

	private void expansion ( Box board[][], int i, int j, List<ExpandingLine> lines ) {
		Queue<Integer> Qi = new LinkedList<>();
		Queue<Integer> Qj = new LinkedList<>();
		Qi.add(i);
		Qj.add(j);
		
		Stack<Line> lines1 = new Stack<>();
		Stack<Line> toDelete = new Stack<>();
		Box current = board[i][j];
		while ( !Qi.isEmpty() ) {
			i = Qi.poll();
			j = Qj.poll();
			current = board[i][j];
			
			if ( !current.bottom ) {
				board[i][j].setBottom(true);
				board[i + 1][j].setTop(true);
				if ( board[i + 1][j].turnedSides == 3 ) {
					Qi.add(i + 1);
					Qj.add(j);
					lines1.push(new Line(i, j, BOTTOM_C));
				} else if ( board[i + 1][j].turnedSides < 3 ) {
					toDelete.push(new Line(i, j, BOTTOM_C));
				}
			}
			if ( !current.right ) {
				board[i][j].setRight(true);
				board[i][j + 1].setLeft(true);
				if ( board[i][j + 1].turnedSides == 3 ) {
					Qi.add(i);
					Qj.add(j + 1);
					lines1.push(new Line(i, j, RIGHT_C));
				} else if ( board[i][j + 1].turnedSides < 3 ) {
					toDelete.push(new Line(i, j, RIGHT_C));
				}
			}
			if ( !current.left ) {
				board[i][j].setLeft(true);
				board[i][j - 1].setRight(true);
				if ( board[i][j - 1].turnedSides == 3 ) {
					Qi.add(i);
					Qj.add(j - 1);
					lines1.push(new Line(i, j - 1, RIGHT_C));
				} else if ( board[i][j - 1].turnedSides < 3 ) {
					toDelete.push(new Line(i, j - 1, RIGHT_C));
				}
			}
			if ( !current.top ) {
				board[i][j].setTop(true);
				board[i - 1][j].setBottom(true);
				if ( board[i - 1][j].turnedSides == 3 ) {
					Qi.add(i - 1);
					Qj.add(j);
					lines1.push(new Line(i - 1, j, BOTTOM_C));
				} else if ( board[i - 1][j].turnedSides < 3 ) {
					toDelete.push(new Line(i - 1, j, BOTTOM_C));
				}
			}
		}
		
		Box board2[][] = new Box[n][n];
		for ( int k = 0; k < n; k++ )
			for ( int h = 0; h < n; h++ )
				board2[k][h] = this.board[k][h].clone();
		
		Stack<Line> lines2 = new Stack<>();
		Qi.add(i);
		Qj.add(j);
		
		//System.out.println(i+" "+j);
		while ( !Qi.isEmpty() ) {
			i = Qi.poll();
			j = Qj.poll();
			current = board2[i][j].clone();
			
			if ( !current.bottom ) {
				board2[i][j].setBottom(true);
				board2[i + 1][j].setTop(true);
				if ( board2[i + 1][j].turnedSides == 3 ) {
					Qi.add(i + 1);
					Qj.add(j);
					if ( !lines1.isEmpty() )
						lines2.push(lines1.pop());
				}
			}
			if ( !current.right ) {
				board2[i][j].setRight(true);
				board2[i][j + 1].setLeft(true);
				if ( board2[i][j + 1].turnedSides == 3 ) {
					Qi.add(i);
					Qj.add(j + 1);
					if ( !lines1.isEmpty() )
						lines2.push(lines1.pop());
				}
			}
			if ( !current.left ) {
				board2[i][j].setLeft(true);
				board2[i][j - 1].setRight(true);
				if ( board2[i][j - 1].turnedSides == 3 ) {
					Qi.add(i);
					Qj.add(j - 1);
					if ( !lines1.isEmpty() )
						lines2.push(lines1.pop());
				}
			}
			if ( !current.top ) {
				board2[i][j].setTop(true);
				board2[i - 1][j].setBottom(true);
				if ( board2[i - 1][j].turnedSides == 3 ) {
					Qi.add(i - 1);
					Qj.add(j);
					if ( !lines1.isEmpty() )
						lines2.push(lines1.pop());
				}
			}
		}
		
		/* Un box */
		if ( lines1.isEmpty() && lines2.isEmpty() &&  !toDelete.isEmpty() ) 
			lines.add( new ExpandingLine(toDelete.pop(), 1) );
		
		/* Hay dos componentes, es necesario eliminar la ultima del primero */
		if ( lines1.size() > 1 ) lines1.pop();
		
	
		//System.out.println("Todelete " + toDelete);
		while ( !toDelete.empty() ) {
			Line line = toDelete.pop();
			if ( line.side == RIGHT_C ) {
				board[line.i][line.j].setRight(false);

				if ( j < n - 1 ) 
					board[line.i][line.j + 1].setLeft(false);
			} else {
				board[line.i][line.j].setBottom(false);
				
				if ( i < n - 1 )
					board[line.i + 1][line.j].setTop(false);					
			}
		}
		
		//System.out.println("Lines1 " + lines1);
		//System.out.println("Lines2 " + lines2);
		
		if ( !lines1.isEmpty() ) lines.add( new ExpandingLine(lines1.peek(), lines1.size() + lines2.size() + 2) );
		if ( !lines2.isEmpty() ) lines.add( new ExpandingLine(lines2.peek(), lines2.size() + 1) );
	}
	
	
	
	private int labSize ( Box board[][], int i, int j ) {
		Queue<Integer> Qi = new LinkedList<>();
		Queue<Integer> Qj = new LinkedList<>();
		Qi.add(i);
		Qj.add(j);
		
		int size = 0;
		Box current = board[i][j];
		
		boolean flag = false;
		while ( !Qi.isEmpty() ) {
			i = Qi.poll();
			j = Qj.poll();
			current = board[i][j];
			
			if ( !current.bottom ) {
				board[i][j].setBottom(true);
				board[i + 1][j].setTop(true);
				if ( board[i + 1][j].turnedSides > 2 ) {
					Qi.add(i + 1);
					Qj.add(j);
					size++;
				}
			}
			if ( !current.right ) {
				board[i][j].setRight(true);
				board[i][j + 1].setLeft(true);
				if ( board[i][j + 1].turnedSides > 2 ) {
					Qi.add(i);
					Qj.add(j + 1);
					size++;
				}
			}
			if ( !current.left ) {
				board[i][j].setLeft(true);
				board[i][j - 1].setRight(true);
				if ( board[i][j - 1].turnedSides > 2 ) {
					Qi.add(i);
					Qj.add(j - 1);
					size++;
				} 
			}
			if ( !current.top ) {
				board[i][j].setTop(true);
				board[i - 1][j].setBottom(true);
				if ( board[i - 1][j].turnedSides > 2 ) {
					Qi.add(i - 1);
					Qj.add(j);
					size++;
				} 
			}
		}
		
		return size;
	}
	
	

	public ArrayList<ExpandingLine> evaluationLines() {
		ArrayList<ExpandingLine> ord = new ArrayList<>();
		
		for (int i = 0; i < n; i++)
			for (int j = 0; j < n; j++) {
				Box board2[][] = new Box[n][n];
				for ( int k = 0; k < n; k++ )
					for ( int h = 0; h < n; h++ )
						board2[k][h] = this.board[k][h].clone();
				
				if ( board[i][j].turnedSides == 2 )
					ord.add( new ExpandingLine(i, j, 9999, labSize(board2, i, j) ) );
			}
		
		Collections.sort(ord);
		Collections.reverse(ord);
		
		Box tempBoard[][] = new Box[n][n];
		for ( int k = 0; k < n; k++ )
			for ( int h = 0; h < n; h++ )
				tempBoard[k][h] = this.board[k][h].clone();
		ArrayList<ExpandingLine> lines = new ArrayList<>();
		
		for ( ExpandingLine e : ord ) {
			if (board[e.i][e.j].turnedSides == 2
					&& tempBoard[e.i][e.j].turnedSides < 3) {
				 //System.out.println( "Selected " + e.i + " " + e.j );
				expansion(tempBoard, e.i, e.j, lines);
			}
		}
		
		Collections.sort(lines);
		ArrayList<ExpandingLine> optimal = new ArrayList<>();
		for( ExpandingLine el : lines ){
			if( !board[el.i][el.j].isUsed(el.side) )
				optimal.add(el);
		}
		return optimal;
	}

	public boolean isOver() {
		return (white + black) == n * n;
	}

	public static String opposite(String player) {
		if (player.equals(Squares.WHITE))
			return Squares.BLACK;
		else if (player.equals(Squares.BLACK))
			return Squares.WHITE;
		else
			throw new IllegalArgumentException();
	}

	public Matrix deepClone() {
		try {
			// write this object
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(baos);
			oos.writeObject(this);

			// read a clone
			ByteArrayInputStream bais = new ByteArrayInputStream(
					baos.toByteArray());
			ObjectInputStream ois = new ObjectInputStream(bais);

			return (Matrix) ois.readObject();
		} catch (Exception exc) {
			System.out.println("Error clonando la matrix" + exc);
		}

		return null;
	}

	private void fillPossiblePoints(Line line, String player) {
		// System.out.println(line);
		int i = line.i, j = line.j;
		Box box2 = null;
		switch (line.side) {
		case LEFT_C:
			box2 = board[i][j - 1];
			break;

		case RIGHT_C:
			box2 = board[i][j + 1];
			break;

		case TOP_C:
			box2 = board[i - 1][j];
			break;

		case BOTTOM_C:
			box2 = board[i + 1][j];
			break;
		}
		addLine(line);

		if (board[i][j].turnedSides == 4) {
			if (player.equals(Squares.WHITE)) {
				white++;
			} else
				black++;

			Line missingLine = box2.getMissingLine(i, j);
			if (missingLine != null)
				fillPossiblePoints(missingLine, player);
		} else if (board[i][j].turnedSides == 3) {
			Line missingLine1 = board[i][j].getMissingLine(i, j);
			Line missingLine2 = box2.getMissingLine(i, j);

			if (missingLine1 != null)
				fillPossiblePoints(missingLine1, player);

			if (missingLine2 != null)
				fillPossiblePoints(missingLine2, player);
		}
	}

	public Matrix newState(Line line, String player) {
		// Clonada yaoming :v
		Matrix matrix = deepClone();
		matrix.fillPossiblePoints(line, player);

		return matrix;
	}

	public Line getAnyFreeLine() {
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if( !isLine(i, j, Squares.LEFT) )
					return new Line(i,j,LEFT_C);
				
				if( !isLine(i, j, Squares.RIGHT) )
					return new Line(i,j,RIGHT_C);
				
				if( !isLine(i, j, Squares.TOP) )
					return new Line(i,j,TOP_C);
				
				if( !isLine(i, j, Squares.BOTTOM) )
					return new Line(i,j,BOTTOM_C);
			}
		}
		return null;
	}
}

class Line implements Serializable {
	int i, j, side;

	public Line(int i, int j, int side) {
		this.i = i;
		this.j = j;
		this.side = side;
	}

	public String getStringSide() {
		switch (side) {
		case Matrix.LEFT_C:
			return Squares.LEFT;

		case Matrix.RIGHT_C:
			return Squares.RIGHT;

		case Matrix.TOP_C:
			return Squares.TOP;

		case Matrix.BOTTOM_C:
			return Squares.BOTTOM;

		default:
			throw new IllegalArgumentException("Bad side!");
		}
	}

	public String toString() {
		return i + " " + j + " " + getStringSide();
	}
}

class ExpandingLine extends Line implements Comparable<ExpandingLine> {

	public int expandedBoxes;

	public ExpandingLine(Line line, int ex) {
		super(line.i, line.j, line.side);
		expandedBoxes = ex;
	}
	
	public ExpandingLine(int i, int j, int side, int ex) {
		super(i, j, side);
		expandedBoxes = ex;
	}

	@Override
	public int compareTo(ExpandingLine e) {
		return this.expandedBoxes - e.expandedBoxes;
	}

	@Override
	public String toString() {
		return super.toString() + " " + expandedBoxes;
	}
}

class Box implements Serializable {
	boolean top, bottom, left, right;
	int turnedSides;

	public Box() {
		top = false;
		bottom = false;
		right = false;
		left = false;
		turnedSides = 0;
	}

	public boolean isUsed(int side) {
		switch (side) {
		case Matrix.LEFT_C:
			return left;

		case Matrix.RIGHT_C:
			return right;

		case Matrix.TOP_C:
			return top;

		default: /*case Matrix.BOTTOM_C:*/
			return bottom;
		}
	}

	public Line getMissingLine(int i, int j) {

		if (turnedSides != 3)
			return null;

		if (!top && bottom && left && right)
			return new Line(i - 1, j, Matrix.BOTTOM_C);

		if (top && !bottom && left && right)
			return new Line(i + 1, j, Matrix.TOP_C);

		if (top && bottom && !left && right)
			return new Line(i, j - 1, Matrix.RIGHT_C);

		if (top && bottom && left && !right)
			return new Line(i, j + 1, Matrix.LEFT_C);

		throw new IllegalArgumentException(
				"Hay algo mal porque el turnedSiedes es 3 pero en realidad no hay 3 booleanos en falso");
	}

	public boolean getSide(String side) {
		if (side.equals(Squares.LEFT))
			return left;
		else if (side.equals(Squares.RIGHT))
			return right;
		else if (side.equals(Squares.TOP))
			return top;
		else
			/* if( side.equals(Squares.BOTTOM) ) */
			return bottom;
	}

	public String toString() {
		return top + " " + bottom + " " + left + " " + right + " "
				+ turnedSides;
	}

	protected Box clone() {
		Box clone = new Box();
		clone.top = top;
		clone.bottom = bottom;
		clone.left = left;
		clone.right = right;
		clone.turnedSides = turnedSides;
		return clone;
	}

	public void setTop(boolean top) {
		if (top && !this.top)
			turnedSides++;
		else if (!top && this.top)
			turnedSides--;
		this.top = top;
	}

	public void setBottom(boolean bottom) {
		if (bottom && !this.bottom)
			turnedSides++;
		else if (!bottom && this.bottom)
			turnedSides--;
		this.bottom = bottom;
	}

	public void setLeft(boolean left) {
		if (left && !this.left)
			turnedSides++;
		else if (!left && this.left)
			turnedSides--;
		this.left = left;
	}

	public void setRight(boolean right) {
		if (right && !this.right)
			turnedSides++;
		else if (!right && this.right)
			turnedSides--;
		this.right = right;
	}

}