/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unalcol.lifesim.environment;

import java.util.concurrent.atomic.AtomicReferenceArray;
import unalcol.lifesim.agents.MoleculeAgent;
import unalcol.types.collection.vector.Vector;

/**
 *
 * @author daniel
 */
public class Space extends AtomicReferenceArray<MoleculeAgent>{

    // the constant space size
    protected int usedSpace;
    
    private boolean isLocked = false;
    
    private final int spaceSize;
    
    private Vector<MoleculeAgent> molecules;
    
    
    public Space(int size) {
        super(size);
        spaceSize = size;
        this.usedSpace = 0;
        molecules = new Vector();
    }

  
    public int usedSpaceSize() {
        return this.usedSpace;
    }

    public int spaceSize() {
        return spaceSize;
    }

   
    
    public boolean add(int index, MoleculeAgent data) {
        try {
            this.set(index, data);
            this.molecules.add(data);
            usedSpace++;
            return true;
        } catch (IndexOutOfBoundsException e) {
            return false;
        }
    }

    /**
     * Returns a valid position if the number is outside the vector.
     *
     * @param position
     * @return
     */
    public int getValidPosition(int position) {
        int maxIdx = this.spaceSize - 1;
        int realIndex = position;
        if (position > maxIdx) {
            realIndex = position - maxIdx;
        } else if (position < 0) {
            realIndex = maxIdx + position + 1;
        }
        if (realIndex < 0) {
            System.err.println("fail position:" + position);
        }
        return realIndex;
    }

    public synchronized boolean move(int oldIndex, int newIndex) {
        boolean move=this.compareAndSet(newIndex, null, this.get(oldIndex));
        if(move)
            this.set(oldIndex, null);
        return move;
    }

    
    public boolean remove(int index) {
        this.set(index, null);
        usedSpace--;
        return true;
    }

   

    public synchronized void lock()
            throws InterruptedException {
        while (isLocked) {
            wait();
        }
        isLocked = true;
    }

    public synchronized void unlock() {
        isLocked = false;
        notify();
    }
    
    public boolean contains(MoleculeAgent m){
         return molecules.contains(m);
    }
    
    public Vector<MoleculeAgent> getBuffer(){
        return this.molecules;
    }

}
