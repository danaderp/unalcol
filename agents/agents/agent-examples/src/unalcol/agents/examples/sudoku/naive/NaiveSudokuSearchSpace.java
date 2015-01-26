package unalcol.agents.examples.sudoku.naive;
import unalcol.agents.examples.sudoku.*;
import unalcol.agents.search.*;
import unalcol.agents.Action;
import unalcol.types.collection.vector.*;
import java.io.StreamTokenizer;
import java.io.StringReader;
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
public class NaiveSudokuSearchSpace implements SearchSpace{
  public NaiveSudokuSearchSpace() {
  }
  public boolean feasible( State state ){
    NaiveSudokuBoardState sudoku_state = (NaiveSudokuBoardState)state;
    return sudoku_state.board.valid();
  }

  public State succesor( State state, Action action ){
    NaiveSudokuBoardState next_state = new NaiveSudokuBoardState((NaiveSudokuBoardState)state);
    int[] values = new int[3];
    int k = 0;
    try {
        StreamTokenizer tokenizer = new StreamTokenizer(new
                StringReader(
                        action.getCode()));
        while (k < 3 && tokenizer.nextToken() != StreamTokenizer.TT_EOF) {
            if (tokenizer.ttype == StreamTokenizer.TT_NUMBER) {

                values[k] = (int) (tokenizer.nval);
                k++;
            }
        }
        next_state.board.hardSet(values[0],values[1],values[2]);
    } catch (Exception e) {
    }
    return next_state;
  }

  public Vector<Action> succesor( State state ){
    Vector<Action> actions = new Vector<Action>();
    NaiveSudokuBoardState sudoku_state = (NaiveSudokuBoardState)state;
    int n = sudoku_state.n;
    int i=0;
    int j=0;
    boolean filled = true;
    while( i<n && filled ){
      j=0;
      while( j<n && filled ){
        filled = (sudoku_state.board.get(i,j) != 0);
        if( filled ){ j++; }
      }
      if( filled ){ i++; }
    }
    if( !filled ){
      for( int k=0; k<n; k++ ){
        actions.add( new Action( "<" + i +"," + j + "," + (k+1) + ">" ) );
      }
    }
    return actions;
  }

}
