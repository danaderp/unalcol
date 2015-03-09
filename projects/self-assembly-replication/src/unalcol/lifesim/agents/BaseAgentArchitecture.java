/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unalcol.lifesim.agents;

import unalcol.agents.Action;
import unalcol.agents.Agent;
import unalcol.agents.AgentArchitecture;
import unalcol.agents.Percept;
import unalcol.types.collection.vector.Vector;

/**
 *
 * @author daniel
 */
public class BaseAgentArchitecture implements AgentArchitecture {

    
    @Override
    public Percept sense(Agent agent) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean act(Agent agent, Action action) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void init(Agent agent) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Vector<Action> actions() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
