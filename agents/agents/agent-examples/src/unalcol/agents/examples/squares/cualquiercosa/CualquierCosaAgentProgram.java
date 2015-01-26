/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package unalcol.agents.examples.squares.cualquiercosa;


import unalcol.agents.Action;
import unalcol.agents.AgentProgram;
import unalcol.agents.Percept;
import unalcol.types.collection.vector.Vector;
import unalcol.agents.examples.squares.*;
import java.util.Random;

/**
 * @author Jonatan
 */
public class CualquierCosaAgentProgram implements AgentProgram {
    private String color;
    private int boardSize = 0;
    private int[] nodes;
    private Vector<Integer> threeNodes;

    public CualquierCosaAgentProgram(String color) {
        this.color = color;
    }

    @Override
    public Action compute(Percept p) {
        if (p.getAttribute(Squares.TURN).equals(color)) {
            // initializes the board
            initBoard(p);
            updateEdges(p);
            int node = 0;
            Vector<String> v = new Vector<String>();
            while (threeNodes.size() > 0) {
                int pos = new Random().nextInt(threeNodes.size());
                node = threeNodes.get(pos);
                threeNodes.remove(pos);
                v = checkPlayable(node, p);
                if ( v.size() > 0) {
                    break;
                }
            }
            int i = 0, j = 0;
            if (node != 0 && v.size()!= 0) {
                i = node / boardSize;
                j = node % boardSize;
            } else {
                while (v.size() == 0) {
                    i = (int) (boardSize * Math.random());
                    j = (int) (boardSize * Math.random());
                    if (p.getAttribute(i + ":" + j + ":" + Squares.LEFT).equals(Squares.FALSE))
                        v.add(Squares.LEFT);
                    if (p.getAttribute(i + ":" + j + ":" + Squares.TOP).equals(Squares.FALSE))
                        v.add(Squares.TOP);
                    if (p.getAttribute(i + ":" + j + ":" + Squares.BOTTOM).equals(Squares.FALSE))
                        v.add(Squares.BOTTOM);
                    if (p.getAttribute(i + ":" + j + ":" + Squares.RIGHT).equals(Squares.FALSE))
                        v.add(Squares.RIGHT);
                }
            }
            String action = i + ":" + j + ":" + v.get((int) (Math.random() * v.size()));
            System.out.println("WHITE:" + action);
            return new Action(action);
        }
        return new Action(Squares.PASS);
    }

    private void initBoard(Percept p) {
        if (boardSize != Integer.parseInt((String) p.getAttribute(Squares.SIZE))) {
            boardSize = Integer.parseInt((String) p.getAttribute(Squares.SIZE));
            nodes = new int[boardSize * boardSize];
        }
    }

    private Vector<String> checkPlayable(int node, Percept p) {
        int i = node / boardSize;
        int j = node % boardSize;
        Vector<String> v = new Vector();
        //up
        if (threeNodes.contains(node - boardSize) && p.getAttribute(i + ":" + j + ":" + Squares.TOP).equals(Squares.FALSE)) v.add(Squares.TOP);
        //down
        if (threeNodes.contains(node + boardSize) && p.getAttribute(i + ":" + j + ":" + Squares.BOTTOM).equals(Squares.FALSE)) v.add(Squares.BOTTOM);
        //left
        if (node % boardSize != 0 && threeNodes.contains(node - 1) && p.getAttribute(i + ":" + j + ":" + Squares.LEFT).equals(Squares.FALSE)) v.add(Squares.LEFT);
        //right
        if (node % boardSize != boardSize - 1 && threeNodes.contains(node + 1) && p.getAttribute(i + ":" + j + ":" + Squares.RIGHT).equals(Squares.FALSE)) v.add(Squares.RIGHT);
        return v;
    }

    private void countOptions() {
        Vector<Integer> v = new Vector<Integer>();
        for (int i = 0; i < nodes.length; i++) {
            if (nodes[i] >= 3) {
                v.add(i);
            }
        }
        this.threeNodes = v;
    }

    private void updateEdges(Percept p) {
        int n = 0, c;
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                c = 0;
                if (p.getAttribute(i + ":" + j + ":" + Squares.LEFT).equals(Squares.FALSE)) c++;
                if (p.getAttribute(i + ":" + j + ":" + Squares.TOP).equals(Squares.FALSE)) c++;
                if (p.getAttribute(i + ":" + j + ":" + Squares.BOTTOM).equals(Squares.FALSE)) c++;
                if (p.getAttribute(i + ":" + j + ":" + Squares.RIGHT).equals(Squares.FALSE)) c++;
                nodes[n] = c;
                n++;
            }
        }
        countOptions();
    }

    @Override
    public void init() {
    }

}

