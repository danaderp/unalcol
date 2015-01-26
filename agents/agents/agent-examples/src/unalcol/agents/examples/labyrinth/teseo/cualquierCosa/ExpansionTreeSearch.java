/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package unalcol.agents.examples.labyrinth.teseo.cualquierCosa;


import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Stack;
import java.util.concurrent.LinkedBlockingQueue;

/**
 *
 * @author Alexander
 */
public class ExpansionTreeSearch {
    private SearchTree tree;
    private final GraphNode root;
    private GraphNode goal;
    private TreeNode actualNode;
    private int limitDepth;
    private boolean idsNoPosibleSolution;
    private HashMap<Point,Object> nodes;
    
    public ExpansionTreeSearch(GraphNode root, GraphNode goal){
        this.root=root;
        this.tree = new SearchTree(root);
        this.goal=goal;
        this.nodes= new HashMap<>(130);
        this.nodes.put(new Point(root.getX(),root.getY()), null);
    }
    
    public ExpansionTreeSearch(GraphNode root){
        this.root=root;
        this.tree = new SearchTree(root);
        this.nodes= new HashMap<>(130);
        this.nodes.put(new Point(root.getX(),root.getY()), null);
    }
    
    public final void initTree(){
        this.tree.initTree();
        this.nodes.clear();
        this.nodes.put(new Point(root.getX(),root.getY()), null);
    }

    public SearchTree getTree() {
        return tree;
    }

    public void setTree(SearchTree tree) {
        this.tree = tree;
    }

    public GraphNode getGoal() {
        return goal;
    }

    public void setGoal(GraphNode goal) {
        this.goal = goal;
    }

    public TreeNode getActualNode() {
        return actualNode;
    }

    public void setActualNode(TreeNode actualNode) {
        this.actualNode = actualNode;
    }
    
    public boolean isRoot(){
        return actualNode.equals(tree.getRoot());
    }
    
    public boolean isPreviousState1(GraphNode a){ 
        return (a.getX()==actualNode.getParent().getMyGraphNode().getX() && a.getY()==actualNode.getParent().getMyGraphNode().getY());
    }
    
    private boolean isPreviousState2(GraphNode node){ // Determina si uno nodo ya fue añadido a la exploracion para no volver a pasar por el (evita ciclos)
        return this.nodes.containsKey(new Point (node.getX(),node.getY()));
    }
    
    public TreeNode Bfs(){ //No usar, hay que revisarlo
        initTree();
        LinkedBlockingQueue queue = new LinkedBlockingQueue();
        queue.add(tree.getRoot());
        while(!queue.isEmpty()){
            actualNode = (TreeNode)queue.remove();
            if(actualNode.getMyGraphNode().equals(goal)) return actualNode;
            ArrayList<TreeNode> list = new ArrayList<>();            
            sucesor(list);
            Collections.shuffle(list);
            for(TreeNode n:list){
                queue.add(n);
            }
        }
        return null;
    }
    
    public void sucesor(ArrayList<TreeNode> list){ //No usarlo, hay que revisarlo
        boolean isRoot = isRoot();
        
        for(Edge e :actualNode.getMyGraphNode().getNeighbors()){
            if(isRoot|| !isPreviousState1(e.getGNode())){
                TreeNode node = new TreeNode(e.getGNode());
                node.setParent(actualNode);
                actualNode.addChild(node, e.getEdgeCost());
                node.costFromRoot();
                tree.addNode(node);
                list.add(node);
            }else{
                if(!actualNode.getParent().getMyGraphNode().equals(e.getGNode())){
                    TreeNode node = new TreeNode(e.getGNode());
                    node.setParent(actualNode);
                    actualNode.addChild(node, e.getEdgeCost());
                    node.costFromRoot();
                    tree.addNode(node);
                    list.add(node);
                }
            }
        }
    }
    
    public void sucesorDFS(ArrayList<TreeNode> list){
        
        for(Edge e :actualNode.getMyGraphNode().getNeighbors()){
            if( isRoot() || (!isPreviousState2(e.getGNode()) && e.getGNode().getWalls()!=3 ) ){
                TreeNode node = new TreeNode(e.getGNode());
                node.setParent(actualNode);
                actualNode.addChild(node, e.getEdgeCost());
                node.setCostFromRoot(actualNode.getCostFromRoot()+e.getEdgeCost());
                if(node.getCostFromRoot()>this.limitDepth){
                    actualNode.removeChild(node);
                    this.idsNoPosibleSolution=false;
                }else{
                    tree.addNode(node);
                    list.add(node);
                }
            }
        }
    }
    
    public TreeNode Dfs(int limite){ //No usar, hay que arreglalo
        initTree();
        this.limitDepth=limite;
        Stack<TreeNode> stack = new Stack<>();
        stack.push(tree.getRoot());
        while(!stack.isEmpty()){
            actualNode = stack.pop();
            if(actualNode.getMyGraphNode().equals(goal)) return actualNode;
            ArrayList<TreeNode> list = new ArrayList<>();            
            sucesorDFS(list);
            Collections.shuffle(list);
            for(TreeNode n:list){
                stack.push(n);
            }
        }
        return null;
    }
    
    public TreeNode Ids(int lim){
        this.idsNoPosibleSolution=false;
        ArrayList<TreeNode> list = new ArrayList<>();
        for(int i=1;i<=lim;i++){
            System.gc();
            this.idsNoPosibleSolution=true;
            initTree();
            this.limitDepth=i;
            Stack<TreeNode> stack = new Stack<>();
            stack.push(tree.getRoot());
            while(!stack.isEmpty()){
                actualNode = stack.pop();
                if(actualNode.getMyGraphNode().getChoices()>=1 && !actualNode.getMyGraphNode().equals(this.root)){
                    return actualNode;
                }
                list.clear();
                sucesorDFS(list);
                Collections.shuffle(list);
                for(TreeNode n:list){
                    stack.push(n);
                    this.nodes.put(new Point(n.getMyGraphNode().getX(),n.getMyGraphNode().getY()), null);                    
                }
            }
            if(this.idsNoPosibleSolution) return null;
        }return null;
    }
    
}
