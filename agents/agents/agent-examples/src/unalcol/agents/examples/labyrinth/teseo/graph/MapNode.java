/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package unalcol.agents.examples.labyrinth.teseo.graph;

/**
 *
 * @author Jonatan
 */
public class MapNode {
    public static int NORTH = 0;
    public static int EAST = 1;
    public static int SOUTH = 2;
    public static int WEAST = 3;
    
    protected int x;
    protected int y;
    
    protected MapNode[] next;
    
    public MapNode(){
        this(0,0);
    }
    
    public MapNode( int _x, int _y ){
        x = _x;
        y = _y;
        next = new MapNode[4];
        for( int i=0; i<next.length; i++ ) next[i] = null;
    }
    
    
    public void setNode( MapNode n, int pos ){
        next[pos] = n;
    }
    
    public void setNorth( MapNode n ){
        setNode( n, NORTH );
    }

    public void setEast( MapNode n ){
        setNode( n, EAST );
    }
    
    public void setSouth( MapNode n ){
        setNode( n, SOUTH );
    }
    
    public void setWeast( MapNode n ){
        setNode( n, WEAST );
    }    
    
    public MapNode north(){
        return next[NORTH];
    }

    public MapNode east(){
        return next[EAST];
    }
    
    public MapNode south(){
        return next[SOUTH];
    }
    
    public MapNode weast(){
        return next[WEAST];
    }
}
