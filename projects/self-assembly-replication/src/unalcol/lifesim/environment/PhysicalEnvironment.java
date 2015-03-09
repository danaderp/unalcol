/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unalcol.lifesim.environment;

import unalcol.agents.Action;
import unalcol.agents.Agent;
import unalcol.agents.AgentArchitecture;
import unalcol.agents.Percept;
import unalcol.random.integer.UniformIntegerGenerator;
import unalcol.types.collection.vector.Vector;
import unalcol.types.real.array.DoubleArrayInit;

/**
 * This is a unidimensional environment represented by 
 * a set of Agents this kind of environment has a property
 * that never can increase the size of the array from external factors,
 * so the size buffer is always the same.
 * @author daniel
 */
public class PhysicalEnvironment extends Vector<Agent> implements Runnable, AgentArchitecture{
    
    // the constant space size
    protected final int arraySize;
    //an indicator to stop/ go thread
    public boolean flag = true;
    
    private double position_energy[];
    
    public static double TEMPERATURE =0.5;
    
    public static double DISIPATE_FACTOR = 0.01;
    
    public PhysicalEnvironment (int size){
        this.buffer = new Agent[size];
        arraySize = size;
        position_energy = DoubleArrayInit.create(size, 0.0);
        this.size = 0;
    }
    
    //The number of agent object in the array
    public int agentsNumber(){
      return this.size();
  }
    
    /**
     * Adding an agent to array, if the array size is not enough
     * the agent can not be added, this method asing a random position
     * to insert the agent.
     * 
     * @param agent to add in to the array environment
     * @return true if it was possible to find a position to insert the agent
     * and there are avaliable position where does not exist another agent; false
     * in other case
     */
    @Override
    public boolean add( Agent agent ){
      boolean cflag = !this.contains(agent);
      if( cflag && size != arraySize ){
        UniformIntegerGenerator g = new UniformIntegerGenerator(arraySize);
        int indice = g.next();
        int n =0;
        while(buffer[indice]!=null){
            indice = g.next();
        }
        buffer[indice]= agent;
        return true;
      }
      return false;
    }
    /**
     * Add an agent to the indicated position if there is not another 
     * agent in this position
     * @param index the place into the environment.
     * @param data  the agent to be inserted.
     * @return
     * @throws IndexOutOfBoundsException 
     */
    @Override
    public boolean add( int index, Agent data ) throws IndexOutOfBoundsException{
            if(buffer[index]!=null){
                this.buffer[index] = data;
                return true;
            }else{
            
                return false;
        }
    }

    


    public void stop(){
      flag = false;
      for (int i = 0; i<size; i++) {
          this.get(i).die();
      }
    }

    public void run(){
      flag = true;
      Agent a;
      for (int i = 0; i < size && flag ; i++) {
          a = this.get(i);
          a.live();
          Thread t = new Thread(a);
          a.setThread(t);
          t.start();
      }
      while(flag){
          updateEnvironmentEnergy();
      }
    }
    
    public void updateEnvironmentEnergy(){
        for(double v: this.position_energy){
            v-= DISIPATE_FACTOR;
        }
    }

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
