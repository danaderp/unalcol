package unalcol.agents.examples.labyrinth.teseo.cualquierCosa;


import java.awt.Point;
import java.util.ArrayList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */



/**
 *
 * @author Alexander
 */
public class Edge {
    private TreeNode tNode;
    private GraphNode gNode;
    private ArrayList<Point> states; //States between a path

    public TreeNode getTNode() {
        return tNode;
    }

    public void setTNode(TreeNode tNode) {
        this.tNode = tNode;
    }

    public GraphNode getGNode() {
        return gNode;
    }

    public void setGNode(GraphNode gNode) {
        this.gNode = gNode;
    }
    private int edgeCost;


    public int getEdgeCost() {
        return edgeCost;
    }

    public void setEdgeCost(int edgeCost) {
        this.edgeCost = edgeCost;
    }

    public ArrayList<Point> getStates() {
        return states;
    }

    public void setStates(ArrayList<Point> states) {
        this.states = states;
    }
    
    public Edge(TreeNode a, int cost){
        this.tNode=a;
        this.gNode=null;
        this.edgeCost=cost;
    }
    
    public Edge(GraphNode a, int cost){
        this.states= new ArrayList<>();
        this.gNode=a;
        this.tNode=null;
        this.edgeCost=cost;
    }
    
}
