package unalcol.agents.examples.squares.grupo7;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

import unalcol.agents.examples.squares.Squares;

public class SquareStructure {
	int n;
	ArrayList< ArrayList<Integer> > graph;
	LinkedList<Integer> whitePoints, blackPoints;
	
	public SquareStructure( int n ){
		graph = new ArrayList<>(n);
		whitePoints = new LinkedList<>();
		blackPoints = new LinkedList<>();
		this.n = n;
		int m = (n+1)*(n+1);
		for (int i = 0; i < m; i++) {
			graph.add( new ArrayList<Integer>(4) );
			
		}
		
		for (int i = 0; i < n; i++) {
			addEdge(i, i+1);
			addEdge(i + n*(n+1), i+1 + n*(n+1));
		}
		for (int i = 0; i < m-n-1; i++) {
			if( i % (n+1) == 0 ){
				addEdge(i, i+n+1);
				addEdge(i + n, i+n+1 + n);
			}
		}
		
	}
	
	private SquareStructure( ArrayList< ArrayList<Integer> > graph, int n,LinkedList<Integer> whitePoints
			,LinkedList<Integer> blackPoints){
		this.graph = graph;
		this.n = n;
		this.whitePoints = whitePoints;
		this.blackPoints = blackPoints;
	}
	
	public int evaluationFunction(String player, int depth){
		if( player.equals(Squares.WHITE) )
			if( whitePoints.size() == blackPoints.size() )
				return 0;
			else if( whitePoints.size() > blackPoints.size() )
				return 1;
			else
				return -1;
		else if( whitePoints.size() == blackPoints.size() )
				return 0;
			else if( whitePoints.size() < blackPoints.size() )
				return 1;
			else
				return -1;
	}
	
	public void putLine( int i, int j, String side ){
		
		int index = n*i + j;
		int upperLeft = index + ((index - (index % n))/n); 
		int upperRight = upperLeft + 1;
		int lowerLeft = upperLeft + n + 1;
		int lowerRight = upperRight + n + 1;
		
		if( side.equals(Squares.LEFT) )
			addEdge(upperLeft, lowerLeft);
		else if( side.equals(Squares.RIGHT) )
			addEdge(upperRight, lowerRight);
		else if( side.equals(Squares.TOP) )
			addEdge(upperLeft, upperRight);
		else if( side.equals(Squares.BOTTOM) )
			addEdge(lowerLeft, lowerRight);
	}
	
	@SuppressWarnings("unchecked")
	public SquareStructure deepClone(){
		return new SquareStructure( (ArrayList< ArrayList<Integer> >) graph.clone(), n, whitePoints, blackPoints );
	}
	
	public SquareStructure newState(Link move, String player){
		SquareStructure copy = deepClone();
		copy.addEdge(move.a, move.b);
		copy.fillSquares();
		colorClosedSquares(player);
		return copy;
	}
	
	private void colorClosedSquares(String player){
		int upperLeft, upperRight, lowerLeft, lowerRight;
		boolean f1,f2,f3,f4;
		for( int i=0; i<n*n; i++ ){
			upperLeft = i + ((i - (i % n))/n); 
			upperRight = upperLeft + 1;
			lowerLeft = upperLeft + n + 1;
			lowerRight = upperRight + n + 1;
			f1 = isEdge(upperLeft, upperRight);
			f2 = isEdge(upperLeft, lowerLeft);
			f3 = isEdge(lowerLeft, lowerRight);
			f4 = isEdge(upperRight, lowerRight);
			
			if( f1 && f2 && f3 && f4 )
				if( player.equals(Squares.WHITE) )
					whitePoints.add(i);
				else
					blackPoints.add(i);
		}
	}
	
	private void fillSquares(){
		int upperLeft, upperRight, lowerLeft, lowerRight;
		int c = 0;
		boolean f1,f2,f3,f4;
		
		while( true ){
			boolean exit = false;
			for( int i=0; i<n*n; i++ ){
				
				upperLeft = i + ((i - (i % n))/n); 
				upperRight = upperLeft + 1;
				lowerLeft = upperLeft + n + 1;
				lowerRight = upperRight + n + 1;
				
				c = 0;
				f1 = isEdge(upperLeft, upperRight);
				f2 = isEdge(upperLeft, lowerLeft);
				f3 = isEdge(lowerLeft, lowerRight);
				f4 = isEdge(upperRight, lowerRight);
				if( f1 ) c++;
				if( f2 ) c++;
				if( f3 ) c++;
				if( f4 ) c++;
				
				if( c == 3 ){
					exit = true;
					if( !f1 )
						addEdge(upperLeft, upperRight);
					else if( !f2 )
						addEdge(upperLeft, lowerLeft);
					else if( !f3 )
						addEdge(lowerLeft, lowerRight);
					else if( !f4 )
						addEdge(upperRight, lowerRight);
				}
			}
			
			if(!exit) break;
		}
	}
	
	public Collection<Link> getPossibleMoves(){
		HashSet<Link> list = new HashSet<>();
		int upperLeft, upperRight, lowerLeft, lowerRight;
		for( int i=0; i<n*n; i++ ){
			upperLeft = i + ((i - (i % n))/n); 
			upperRight = upperLeft + 1;
			lowerLeft = upperLeft + n + 1;
			lowerRight = upperRight + n + 1;
			
			if( !isEdge(upperLeft, upperRight)  )
				list.add(new Link(upperLeft,upperRight));
			
			if( !isEdge(upperLeft, lowerLeft) )
				list.add( new Link(upperLeft,lowerLeft) );
			
			if( !isEdge(lowerLeft, lowerRight) )
				list.add( new Link(lowerLeft, lowerRight) );
			
			if( !isEdge(upperRight, lowerRight) )
				list.add( new Link( upperRight, lowerRight ) );
		}
		
		return list;
	}
	
	private boolean isEdge(int a, int b){
		return graph.get(a).contains(b);			
	}
	
	private void addEdge( int a, int b ){
		graph.get(a).add(b);
		graph.get(b).add(a);
	}
	
	public class Link{
		int a, b;
		
		public Link(int a, int b){
			this.a = a;
			this.b = b;
		}
		
		public String toString(){
			return a + " " + b;
		}
		
		public boolean equals( Object o ){
			return a == ((Link)o).a && b == ((Link)o).b;
		}
		
		public int hashCode(){
			return (a+b)*(a+b+1)/2 + b;
		}
	}
	
	public String toString(){
		return graph.toString();
	}
	
	public static void main(String[] args) {
		SquareStructure st = new SquareStructure(2);
		st.addEdge(1, 4);
		System.out.println(st); 
		st.fillSquares();
		System.out.println(st); 
		/*
		Collection<Link> c = st.getPossibleMoves();
		System.out.println(c.size());
		for(Link l : c){
			System.out.println(l);
		}*/
	}
}
