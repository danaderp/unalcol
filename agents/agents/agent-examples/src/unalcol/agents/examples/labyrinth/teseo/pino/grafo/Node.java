package unalcol.agents.examples.labyrinth.teseo.pino.grafo;

import java.util.ArrayList;
import java.util.Arrays;

public class Node {
    private Integer[] xy=new Integer[1];
    private Node[] child;
    public boolean[] haveChild;
    private NodeCritico[] nodeC;
    int dist,ant;
    
    public Node(Integer[] xy)
    {
        this.xy = xy;
        child = new Node[4];
        haveChild = new boolean[4]; // 0 hijo al frente, 1 a la derecha, etc.
    }
    
    public static class NodeCritico {
        ArrayList<Integer> dir;
        int dist;
        Node n;
        
        public NodeCritico(ArrayList<Integer> dir, Node n){
            this.dir = dir;
            this.dist = dir.size();
            this.n = n;
        }
    }
    
    public void addChildNodeC(Node n, ArrayList<Integer> dir){
        nodeC[dir.get(0)]=new NodeCritico(dir,n);
    }
        
    public void iniNodeCrit(){
        nodeC = new NodeCritico[4];        
    }
    
    public NodeCritico getNodeC(int pos){
        return nodeC[pos];
    }
    
    public void removeNodeC(int pos){
        nodeC[pos] = null;
    }

    public void addChildNode(Node adj,int pos){
        this.child[pos] = adj;
    }  
    
    public void setChildlen(boolean[] childlen){
        for (int i = 0; i < 4; i++)
            haveChild[i] = !childlen[i];
    }

    public Node[] getChild(){
        return child;
    }
    
    public boolean haveWayNew(){
        for(int i=0;i<4;i++)if(haveChild[i] && child[i]==null)
            return true;
        return false;
    }

    public Integer[] getCoord(){
        return xy;
    }
    
    @Override
    public boolean equals(Object o){
        if (o instanceof Node) {
            Node p = (Node)o;
            if(this.xy[0]==p.xy[0] && this.xy[1]==p.xy[1])return true;          
            return false;
            }
        return false;
        } 

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Arrays.hashCode(this.xy);
        return hash;
    }
    
//    public int compareTo(Node other) {
//            int d=Integer.compare(dist, other.dist);
//        if(d==0)return 1;        
//        return d;
//    }

}