/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unalcol.lifesim.layers;

import unalcol.agents.Action;
import unalcol.lifesim.agents.MoleculeAgent;
import unalcol.lifesim.agents.MoleculePercept;
import unalcol.lifesim.environment.Space;

/**
 *
 * @author daniel
 */
public abstract class Layer{

    
    private Layer nextLayer;
    protected Space space;
    
    public Layer(Layer _nextLayer){
       nextLayer= _nextLayer;
    }
    public Layer(Space _space, Layer _nextLayer){
       nextLayer= _nextLayer;
       space = _space;
    }
    
  
    
    protected abstract boolean add( MoleculeAgent molecule );
    
    public  boolean addMolecule(MoleculeAgent molecule){
        boolean result = add(molecule);
        if(nextLayer!=null && result){
            result= nextLayer.addMolecule(molecule);
        }
        return result;
    }
    
    /**
     * This method is planed to be the updating step of time;
     */
    public void update(){
        this.updateLayer();
         if(nextLayer!=null){
            nextLayer.update();
        }
    }
    
    /**
     *The implementation of the action on each time step;
     */
    protected abstract void updateLayer();
    
    public  MoleculePercept operate(MoleculeAgent m){
        MoleculePercept result = operates(m);
        if(nextLayer!=null && result!=null){
            result.addPerceptions (nextLayer.operate(m));
        }
        return result;
    }
    
    /**
     * Repot a change to the chain of layers
     * @param m
     * @param a
     * @return 
     */
    public boolean reportChange(MoleculeAgent m, Action a){
        boolean result = change(m,a);
        if(nextLayer!=null && result){
            result= nextLayer.reportChange(m,a);
        }
        return result;
    }
    /**
     * Used when a molecule moves, for example
     * @param m
     * @return 
     */
    protected abstract boolean change(MoleculeAgent m, Action a);
    
    /**
     * Puts properties to the agent information.
     * @param m
     * @return 
     */
    protected abstract MoleculePercept operates(MoleculeAgent m);
    
  
}
