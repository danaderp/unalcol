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
public class SelfReplicationLayer extends Layer {

    public SelfReplicationLayer(Space _space, Layer _nexLayer) {
        super(_space,_nexLayer);
    }

    @Override
    public boolean add(MoleculeAgent agent) {
        return true;
    }

    @Override
    public void updateLayer() {
    }

   

    @Override
    protected boolean change(MoleculeAgent m, Action a) {
        return true;
    }

    @Override
    public MoleculePercept operates(MoleculeAgent m) {
        return null;
    
    
    }

    @Override
    public void display() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

  

    
}
