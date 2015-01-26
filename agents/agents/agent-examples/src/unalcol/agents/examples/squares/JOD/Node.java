/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package unalcol.agents.examples.squares.JOD;

import java.util.Collections;
import java.util.Comparator;

/**
 *
 * @author Tato
 */
public class Node{
    
    private int i;
    private int j;
    private int lineas;
    private int top,rigth,bottom,left;
    private int perdidas;


    
    public Node(int x, int y){
        this.i = x;
        this.j = y;
        this.top = 1;
        this.rigth = 1;
        this.bottom = 1;
        this.left = 1;
        this.perdidas=0;
        
    }

    /**
     * @return the i
     */
    public int getI() {
        return i;
    }

    public int getPerdidas() {
        return perdidas;
    }

    public void setPerdidas(int perdidas) {
        this.perdidas = perdidas;
    }
    /**
     * @param i the i to set
     */
    public void setI(int i) {
        this.i = i;
    }

    /**
     * @return the j
     */
    public int getJ() {
        return j;
    }

    /**
     * @param j the j to set
     */
    public void setJ(int j) {
        this.j = j;
    }

    /**
     * @return the lineas
     */
    public int getLineas() {
        return lineas;
    }

    /**
     * @param lineas the lineas to set
     */
    public void setLineas(int lineas) {
        this.lineas = lineas;
    }

    /**
     * @return the top
     */
    public int getTop() {
        return top;
    }

    /**
     * @param top the top to set
     */
    public void setTop(int top) {
        this.top = top;
    }

    /**
     * @return the rigth
     */
    public int getRigth() {
        return rigth;
    }

    /**
     * @param rigth the rigth to set
     */
    public void setRigth(int rigth) {
        this.rigth = rigth;
    }

    /**
     * @return the bottom
     */
    public int getBottom() {
        return bottom;
    }

    /**
     * @param bottom the bottom to set
     */
    public void setBottom(int bottom) {
        this.bottom = bottom;
    }

    /**
     * @return the left
     */
    public int getLeft() {
        return left;
    }

    /**
     * @param left the left to set
     */
    public void setLeft(int left) {
        this.left = left;
   }
    


}
