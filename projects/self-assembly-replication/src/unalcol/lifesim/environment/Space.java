/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unalcol.lifesim.environment;

import unalcol.lifesim.agents.MoleculeAgent;
import unalcol.types.collection.vector.Vector;

/**
 *
 * @author daniel
 */
public class Space extends Vector<MoleculeAgent> {

   

    // the constant space size
    protected int usedSpace;

    public Space(int size) {
        
        
        this.buffer = new MoleculeAgent[size];
        this.size= size;
        this.usedSpace = 0;
    }
    
   public MoleculeAgent[] getBuffer(){
       return buffer;
   }

    public int usedSpaceSize() {
        return this.usedSpace;
    }
    
    public int spaceSize(){
        return size;
    }
    public boolean add( int index, MoleculeAgent data ) {
        try{
            this.buffer[index] = data;
            usedSpace++;
            return true;
        }catch(IndexOutOfBoundsException e){
            return false;
        }
    }
    /**
     * Returns a valid position if the number is 
     * outside the vector.
     * @param position
     * @param index
     * @return 
     */
    public int getValidPosition(int position){
        int maxIdx = this.size-1;
        int realIndex= position;
        if(position>maxIdx){
           realIndex=position%maxIdx-1; 
        }else if(position<0){
            realIndex=maxIdx+position+1;
        }
        return realIndex;
    }
    
    public boolean remove( int index ){
        this.buffer[index]=null;
        return true;
    }

}
