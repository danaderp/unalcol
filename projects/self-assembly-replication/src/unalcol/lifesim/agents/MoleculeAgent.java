/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unalcol.lifesim.agents;

import unalcol.agents.Agent;
import unalcol.agents.AgentArchitecture;
import unalcol.agents.AgentProgram;
import unalcol.lifesim.environment.Space;
import unalcol.types.collection.vector.Vector;

/**
 *
 * @author daniel
 */
public class MoleculeAgent extends Agent {
    /*
     The valence "electrons" that the molecule have it could be negative or positve
     */

    private int valence;

    /*
     The number that describe the stable state of a molecule, the agent have to keep
     movving until obtain this value when contact to another agent.
     */
    public static final int STABLE_STATE = 0;

    //Determine if the molecule is stable or not
    private boolean isStable = false;

    //Position of the agent into the array
    private int position = -1;

    //The number of cells vision
    public int vision_ratio = 2;

    public int remaining_v;

    //The number of neightborhoods.
    public static final int NEIGHTS = 2;

    public int pixel_c;
    protected Vector<MoleculeAgent> bonds;

    private final Space space;

    public MoleculeAgent(AgentArchitecture _architecture, AgentProgram _program, Space _space) {
        super(_architecture, _program);
        bonds = new Vector<>();
        this.space = _space;
    }

    public boolean isStable() {
        return isStable;
    }

    public void setValence(int _valence) {
        this.valence = _valence;
        this.remaining_v = _valence;
    }

    public int getValence() {
        return valence;
    }

    public synchronized void setPosition(int position) {
        this.position = position;
    }

    public void changePosition(int _position) {

            //TODO: sync
        for (MoleculeAgent m : this.bonds) {

            int dif = m.position - this.position;
            int validx = space.getValidPosition(_position + dif);
            //m.setPosition(validx);
            this.space.move(m.position, validx);

        }
        this.space.move(this.position, _position);
        this.position = _position;

    }

    public int getPosition() {
        return this.position;
    }

    public int getReminingV() {
        return this.remaining_v;
    }

    public void bond(MoleculeAgent m) {
        //Try catch occurs when the other molecule leves just when the bond is processing.
        try {
            if (!this.bonds.contains(m) && this.bonds.size() < NEIGHTS && !isStable) {
                this.remaining_v += m.valence;
                if (this.remaining_v <= 0) {
                    this.isStable = true;
                }
                this.bonds.add(m);
                m.bond(this);
            }
        } catch (NullPointerException e) {
        }

    }

    public Vector getBonds() {
        return this.bonds;
    }

    public boolean isBonded() {
        return (this.bonds.size() > 0);
    }
}
