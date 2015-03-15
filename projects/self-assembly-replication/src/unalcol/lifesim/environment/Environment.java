/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unalcol.lifesim.environment;

import java.util.logging.Level;
import java.util.logging.Logger;
import processing.core.PApplet;
import unalcol.agents.Action;
import unalcol.agents.Agent;
import unalcol.agents.AgentArchitecture;
import unalcol.agents.Percept;
import unalcol.lifesim.agents.MoleculeProgram;
import unalcol.lifesim.agents.MoleculeAgent;
import unalcol.lifesim.agents.MoleculePercept;
import unalcol.lifesim.agents.MoleculeSenses;
import unalcol.lifesim.layers.PhysicLayer;
import unalcol.lifesim.layers.SelfAssemblyLayer;
import unalcol.lifesim.layers.SelfReplicationLayer;
import unalcol.lifesim.layers.view.LayerView;
import unalcol.types.collection.vector.Vector;

/**
 * This is a unidimensional environment represented by a set of Agents this kind
 * of environment has a property that never can increase the size of the array
 * from external factors, so the size buffer is always the same.
 *
 * @author daniel
 */
public class Environment extends PApplet implements AgentArchitecture {

    //an indicator to stop/ go thread
    private boolean flag = true;

    //Layer that support the agents and represent space.
    private final PhysicLayer physicLayer;
    //Layer that contains weights or valence values that probably assembly
    private final SelfAssemblyLayer selfAssemblyLayer;
    //Layer that determine if a strand is self-replicated or not
    private final SelfReplicationLayer selfReplicationLayer;

    //Space where the agents live
    private final Space space;

    //Initial population rate
    public final double initialPopPercent = 0.4;
    //The number of population to maintain on the environment
    public final double totalPopConstan = 0.4;

    public final int environmentSize = 1000;

    /**
     *
     */
    public Environment() {
        space = new Space(environmentSize);
        selfReplicationLayer = new SelfReplicationLayer(space, null);
        selfAssemblyLayer = new SelfAssemblyLayer(space, selfReplicationLayer);
        physicLayer = new PhysicLayer(space, selfAssemblyLayer);

    }

    public void createInitialPop() {
        int usedSpace = space.usedSpaceSize();
        int size = space.spaceSize();
        if (usedSpace == 0) {
            int nMolecules = (int) (initialPopPercent * size);
            for (int i = 0; i < nMolecules; i++) {
                MoleculeAgent a = createAgent();
                physicLayer.addMolecule(a);
            }
        }
    }

    public MoleculeAgent createAgent() {
        MoleculeAgent mol = new MoleculeAgent(this, new MoleculeProgram(), this.space);

        return mol;
    }

    public void add(MoleculeAgent molecule) {
        if (!space.contains(molecule)) {

            //Applys physicLayer rules to add.
            physicLayer.addMolecule(molecule);
        }

    }

    public void stop() {
        flag = false;
        for (MoleculeAgent mol : space.getBuffer()) {
            mol.die();
        }
        super.stop();
    }

    @Override
    public Percept sense(Agent agent) {
        //System.out.println(agent.hashCode() +" Persive");
        MoleculeAgent a = (MoleculeAgent) agent;
        //TODO: I have to change this terrible step..... :(
        Percept p = new Percept();
        MoleculePercept mp = physicLayer.operate(a);
        mp.setAttribute(MoleculeSenses.VALENCE_VALUE, a.getValence());
        mp.setAttribute(MoleculeSenses.MOL_SEFL, agent);

        p.setAttribute("Param", mp);

        return p;
    }

    @Override
    public boolean act(Agent agent, Action action) {

        //System.out.println(agent.hashCode() +" actua");
        MoleculeAgent m = (MoleculeAgent) agent;
        if (action.getCode().equalsIgnoreCase("bondleft")) {
            m.bond(space.get(space.getValidPosition(m.getPosition() - 1)));
        } else if (action.getCode().equalsIgnoreCase("bondright")) {
            m.bond(space.get(space.getValidPosition(m.getPosition() + 1)));
        } else {
            physicLayer.reportChange((MoleculeAgent) agent, action);
        }
        return true;
    }

    @Override
    public void init(Agent agent) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Vector<Action> actions() {
        return null;
    }

    private synchronized void updateEnvironmentLayers() {
        try {
            wait(5);
        } catch (InterruptedException ex) {
            Logger.getLogger(Environment.class.getName()).log(Level.SEVERE, null, ex);
        }
        physicLayer.update();
    }

    @Override
    public void setup() {
        size(space.spaceSize() * LayerView.pixel_size, LayerView.height_visualizer);
        background(255);
        frameRate(30);
        flag = true;
        physicLayer.setViewer(this);
        selfAssemblyLayer.setViewer(this);
        this.createInitialPop();

        Vector<MoleculeAgent> mols = space.getBuffer();
        Agent a;
        for (int i = 0; i < mols.size(); i++) {
            a = mols.get(i);

            a.live();
            Thread t = new Thread(a);
            a.setThread(t);
            t.start();
        }

    }

    @Override
    public void draw() {

        updateEnvironmentLayers();

    }
}
