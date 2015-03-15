/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unalcol.lifesim.layers;

import processing.core.PApplet;
import unalcol.agents.Action;
import unalcol.lifesim.agents.MoleculeAgent;
import unalcol.lifesim.agents.MoleculePercept;
import unalcol.lifesim.environment.Space;
import unalcol.lifesim.layers.view.LayerView;

/**
 *
 * @author daniel
 */
public abstract class Layer{

    
    private final Layer nextLayer;
    protected final Space space;
    protected int time_c=0;
    
    protected int current_raw=0;
    
    protected PApplet viewer;
        

    
    
  
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
         this.time_c++;
         this.current_raw++;
         if(time_c%LayerView.height_visualizer==0){
             current_raw=0;
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
     * @param a
     * @return 
     */
    protected abstract boolean change(MoleculeAgent m, Action a);
    
    /**
     * Puts properties to the agent information.
     * @param m
     * @return 
     */
    protected abstract MoleculePercept operates(MoleculeAgent m);
    
    public void setViewer(PApplet p){
        this.viewer = p;
      
    }
    
    public abstract void display();
  
}
