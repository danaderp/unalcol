/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package unalcol.agents.examples.labyrinth.teseo.cualquierCosa;


import java.util.ArrayList;

/**
 *
 * @author Alexander
 */
public class GraphNode {
    private ArrayList<Edge> neighbors;
    private int x;
    private int y;
    private int choices;
    private int walls;
    public boolean [] exploredStates;
    
    public int getWalls() {
        return walls;
    }

    public void setWalls(int walls) {
        this.walls = walls;
    }

    public int getChoices() {
        return choices;
    }

    public void setChoices(int choices) {
        this.choices = choices;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
    
    public ArrayList<Edge> getNeighbors() {
        return neighbors;
    }
    
    public void setEdges(ArrayList<Edge> neighbors) {
        this.neighbors = neighbors;
    }
    
    public void calChoices(){
        int val=0;
        if(!this.exploredStates[0]) val++;
        if(!this.exploredStates[1]) val++;
        if(!this.exploredStates[2]) val++;
        if(!this.exploredStates[3]) val++;
        this.choices=val;
    }
    
    public void addNeighbor(GraphNode a, int cost){
        this.addEdge(new Edge(a, cost));
        a.addEdge(new Edge(this, cost));
    }
    
    public void addEdge(Edge e){
        if(!e.getGNode().equals(this)){
            addBestRoute(e);
        }        
    }
    
    public void removeEdge(Edge e){
        this.neighbors.remove(e);
    }
    
    public Edge searchEdge(GraphNode a){
        for(Edge e:this.neighbors){
            if(a.equals(e.getGNode())) return e;
        }return null;
    }
    
    public void removeNeighbor(GraphNode a){
        for(Edge e:neighbors){
            if(e.getGNode().equals(a)){
                neighbors.remove(e);
                break;
            }
        }
    }
    
    public void addBestRoute(Edge edge){   //Revisa si una ruta es unica; borra la ruta mas costosa hacia cierto nodo
        for(Edge e: this.getNeighbors()){
            if(edge.getGNode().equals(e.getGNode())){
                if(edge.getEdgeCost()<e.getEdgeCost()){
                    this.removeEdge(e);
                    this.neighbors.add(edge);
                    return;
                }
            }
        }this.neighbors.add(edge);
    }
    
    public GraphNode(int x, int y){
        this.neighbors = new ArrayList<>();
        this.x=x;
        this.y=y;
        this.walls=0;
        this.exploredStates= new boolean[4];
        this.exploredStates[0]=false;
        this.exploredStates[1]=false;
        this.exploredStates[2]=false;
        this.exploredStates[3]=false;
    }
       
    public GraphNode(int x, int y, ArrayList<Edge> children, int choices){
        this.x=x;
        this.y=y;
        this.neighbors=children;
        this.choices=choices;
        this.walls=0;
        this.exploredStates = new boolean[4];
        this.exploredStates[0]=false;
        this.exploredStates[1]=false;
        this.exploredStates[2]=false;
        this.exploredStates[3]=false;
    }
}
