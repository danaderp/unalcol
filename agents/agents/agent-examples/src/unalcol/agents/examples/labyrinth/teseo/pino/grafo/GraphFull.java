package unalcol.agents.examples.labyrinth.teseo.pino.grafo;

import java.util.HashSet;

public class GraphFull implements Graph{
    
    private HashSet vertices = new HashSet<Node>();
    private Node ultimo;

    @Override
    public boolean addNode(Node n)
    {
        if(vertices.add(n)){
            ultimo=n;
            return true;
        }
        return false;
    }

    @Override
    public Node getUltimo()
    {
        return ultimo;
    }
    
    public void setUltimo(Node n){
        ultimo = n;
    }
}