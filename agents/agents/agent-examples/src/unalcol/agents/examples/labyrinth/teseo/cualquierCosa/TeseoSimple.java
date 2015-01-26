/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package unalcol.agents.examples.labyrinth.teseo.cualquierCosa;

import java.util.Collections;
import java.util.Stack;

/**
 *
 * @author Alexander
 */
public class TeseoSimple extends SimpleTeseoAgentProgram {
    
    /*  Con 0 avanza al frente
        Con 1 a la derecha
        Con 2 hacia atrás
        Con 3 hacia la izquierda
        Con -1 muere   
        Con 5 ejecuta la funcion para encontar el nodo de decision mas cercano
    */
    public int getWalls(boolean PF,boolean PD,boolean PA,boolean PI){
        int walls=0;
        if(PF) walls++;
        if(PD) walls++;
        if(PA) walls++;
        if(PI) walls++;
        return walls;
    }
    
    public void computeChoices(boolean PF,boolean PD,boolean PI, boolean PA, boolean AF, boolean AD, boolean AA, boolean AI, Stack<Integer> stack){
        Compass realNorth=north;
        Stack<Integer> aux = new Stack<>();
        
        if(!PF){ aux.push(0); }
        else{
            int index = getIndexExploredStates(0);
            actualNode.exploredStates[index]=true;
        }        
        if(!PD){ aux.push(1); }
        else{
            int index = getIndexExploredStates(1);
            actualNode.exploredStates[index]=true;
        }        
        if(!PI){ aux.push(3); }
        else{
            int index = getIndexExploredStates(3);
            actualNode.exploredStates[index]=true;
        }
        
        if(!PA){
            rotate(2);
            if(actualNode.equals(myGraph.getRoot())){
                int index = getIndexExploredStates(0);
                if(!knownNode(nextMove())&&!actualNode.exploredStates[index]){
                    if(!AA) stack.push(2);
                }
            }else{
                int index = getIndexExploredStates(0);
                actualNode.exploredStates[index]=true;
            }
        }else{
            int index = getIndexExploredStates(2);
            actualNode.exploredStates[index]=true;
        }
        
        while(!aux.isEmpty()){
            north=realNorth;
            int result = aux.pop();
            rotate(result);
            if(knownNode(nextMove())){
                if(isNewNode) {
                    int index = getIndexExploredStates(0);
                    myGraph.SearchNode(nextMove().getX(), nextMove().getY()).addNeighbor(actualNode, 1);
                    myGraph.SearchNode(nextMove().getX(), nextMove().getY()).exploredStates[(index+2)%4]=true;
                    myGraph.SearchNode(nextMove().getX(), nextMove().getY()).calChoices();
                    actualNode.exploredStates[index]=true;
                }
            }else{
                int index = getIndexExploredStates(0);
                boolean AgentP=false;
                if(result==0) AgentP=AF;
                if(result==1) AgentP=AD;
                if(result==3) AgentP=AI;
                if(!actualNode.exploredStates[index] && !AgentP ){
                    stack.push(result);
                }
            }
        }
        actualNode.calChoices();
        north=realNorth;
    }

    public TeseoSimple() {}
    
    @Override
    public int accion(boolean PF, boolean PD, boolean PA, boolean PI, boolean MT,
            boolean AF, boolean AD, boolean AA, boolean AI) {
        
        if (MT) return -1;
        actualNode.setWalls(getWalls(PF,PD,PA,PI));
        if(actualNode.getWalls()==2) this.TwoWallsNodes.add(actualNode);
        Stack<Integer> nextMoves = new Stack<>();
        
        computeChoices(PF, PD, PI, PA, AF, AD, AA, AI, nextMoves);
        
        Collections.shuffle(nextMoves);// Decisión Aleatoria, si se comenta: forward, right, left
        
        if (nextMoves.isEmpty()){
            return 5; // Go back to a decision node
        }
        
        return nextMoves.pop();
    }

    @Override
    public int findOtherWay(boolean PF, boolean PD, boolean PA, boolean PI, boolean AF, boolean AD, boolean AA, boolean AI) {
        if(!PF && !AF) return 0;
        if(!PD && !AD) return 1;        
        if(!PI && !AI) return 3;        
        if(!PA && !AA) return 2;
        return 5;
    }
    
}