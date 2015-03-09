/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unalcol.lifesim.environment;

import unalcol.lifesim.agents.DummyBaseAgentProgram;
import unalcol.agents.Agent;
import unalcol.agents.simulate.Environment;

/**
 *
 * @author daniel
 */
public class EnvironmentControler {
    
    private PhysicalEnvironment environment;
    public final double initialPopPercent=0.2;
    public final double totalPopConstan=0.4;
    
    private final int environmentSize;
    
    public EnvironmentControler(int _environmentSize){
        
        environment= new PhysicalEnvironment(_environmentSize);
        environmentSize = _environmentSize;
    }
    
    public void controlPopulation(){
        int size = environment.agentsNumber();
        if(size ==0)
        {
            int nAgents = (int) (initialPopPercent*environmentSize);
            for(int i=0; i<nAgents;i++)
            {
                Agent a = createAgent();
                
                environment.add(a);
            }
        }
        //TODO: Density function
        
        //TODO: Temeperature modification
    }
    
    public Agent createAgent(){
        Agent agent = new Agent(environment, new DummyBaseAgentProgram());
        return agent;
    }
}
