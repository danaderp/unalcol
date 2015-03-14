/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unalcol.lifesim.agents;

import java.util.HashMap;

/**
 *
 * @author daniel
 */
public class MoleculePercept extends HashMap<MoleculeSenses, Object> {

    public Object getAttribute(MoleculeSenses code) {
        return this.get(code);
    }

    public void setAttribute(MoleculeSenses key, Object value) {
        this.put(key, value);
    }

    public void addPerceptions(MoleculePercept mp) {
        if (mp !=null) {
            this.putAll(mp);
        }
    }
}
