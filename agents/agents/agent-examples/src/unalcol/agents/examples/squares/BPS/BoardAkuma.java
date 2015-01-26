package unalcol.agents.examples.squares.BPS;

/**
 * @author bps_csp
 */
public class BoardAkuma {
    public static final int LEFT = 1;
    public static final int TOP = 2;
    public static final int RIGHT = 4;
    public static final int BOTTOM = 8;
    public static final int WHITE = 16;
    
    protected int[][] values;
    
    protected static int[][] init( int n, int m ){        
        int[][] values = new int[n][m];        
        for( int i=0; i<n; i++ ){
            values[i][0] = LEFT;
            values[i][m-1] = RIGHT;
        }
        for( int i=0; i<m; i++ ){
            values[0][i] |= TOP;
            values[n-1][i] |= BOTTOM;
        }
        return values;
    }
    
    public BoardAkuma( int n ){
        this(n, n);
    }
    
    public BoardAkuma(BoardAkuma original){
        values = new int[(original.values.length)][(original.values.length)];
        for(int i = 0; i < original.values.length; i++)
            for(int j = 0; j < original.values.length; j++)
                values[i][j] = original.values[i][j];
    }
    
    public BoardAkuma( int n, int m ){
        values = init(n, m);
    }
       
    public boolean invalid( int i, int j, int val ){
      return i<0 || values.length<=i || j<0 || values[0].length <= j ||
             val<=0 || val>BOTTOM || (values[i][j] & val) == val;
    }
    
    public int lines(int i, int j){
      int c=(values[i][j] & LEFT)==LEFT?1:0;
      c+=(values[i][j] & TOP)==TOP?1:0;
      c+=(values[i][j] & RIGHT)==RIGHT?1:0;
      c+=(values[i][j] & BOTTOM)==BOTTOM?1:0;
      return c;
    }
    
    public boolean play(int i, int j, int val ){
        if( invalid(i,j,val) ){ return false; }
        values[i][j] |= val; 
        switch(val){
            case LEFT:
              values[i][j-1] |= RIGHT;  
            break;    
            case TOP:
              values[i-1][j] |= BOTTOM;
            break;    
            case RIGHT:
              values[i][j+1] |= LEFT;  
            break;    
            case BOTTOM:
              values[i+1][j] |= TOP;  
            break;    
        }
        return true;        
    }
    
    protected boolean closable(int i, int j){
        return lines(i,j)==3;
    }
        
    public String toString(){
      StringBuilder sb = new StringBuilder();
      for( int j=0; j<values[0].length; j++ ){
        sb.append(' ');
        if( (values[0][j] & TOP)==TOP ) sb.append('_'); else sb.append(' ');
      }
      sb.append('\n');
      for( int i=0; i<values.length; i++ ){
          for( int j=0; j<values[i].length; j++ ){
              if( (values[i][j] & LEFT)==LEFT ) sb.append('|'); else sb.append(' ');
              if( (values[i][j] & BOTTOM)==BOTTOM ) sb.append('_'); else sb.append(' ');
          }
          if( (values[i][values[i].length-1] & RIGHT)==RIGHT ) sb.append('|'); else sb.append(' ');
          sb.append('\n');
      }
      return sb.toString();
    }
    
    public boolean full(){
        boolean flag = true;
        for( int i=0; i<values.length&&flag; i++){
            for( int j=0; j<values[0].length&&flag; j++){
                flag &= (lines(i,j)==4);
            }
        }
        return flag;
    }
    
    public static void main( String[] args ){
        BoardAkuma b = new BoardAkuma(10);
        System.out.println(b);
        System.out.println("************************************");
        b.play(4, 6, LEFT);
        System.out.println(b);
        System.out.println("************************************");
        b.play(2, 5, LEFT);
        System.out.println(b);
        System.out.println("************************************");
        b.play(8, 3, LEFT);
        System.out.println(b);
        System.out.println("************************************");
        b.play(9, 7, LEFT);
        System.out.println(b);
        System.out.println("************************************");
        b.play(1, 0, BOTTOM);
        System.out.println(b);
        System.out.println("************************************");
        b.play(0, 1, LEFT);
        System.out.println(b);
        System.out.println("************************************");
        
        int test = 0;
        test |= LEFT;
        test |= RIGHT;
        //test |= TOP;
        test |= BOTTOM;
        
        System.out.println("test: "+(test&TOP)+"→"+TOP);
        System.out.println("test: "+(4&2)+"→"+TOP);
    }        
}

