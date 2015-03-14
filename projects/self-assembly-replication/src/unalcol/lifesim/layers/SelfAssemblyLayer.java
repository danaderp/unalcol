/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unalcol.lifesim.layers;

import unalcol.agents.Action;
import unalcol.lifesim.agents.MoleculeAgent;
import unalcol.lifesim.agents.MoleculePercept;
import unalcol.lifesim.agents.MoleculeSenses;
import unalcol.lifesim.environment.Space;

/**
 *
 * @author daniel
 */
public class SelfAssemblyLayer extends Layer {

    public static int DISIPATE_FACTOR = 1;
    public static int STEP_DISIPATE = 500;
    public static int INITIAL_VALUE = 0;
    private final int[] assemblyWeights;
    private int time = 0;

    /**
     *
     * @param _space
     * @param _nexLayer
     */
    public SelfAssemblyLayer(Space _space, Layer _nexLayer) {
        super(_space, _nexLayer);
        assemblyWeights = new int[space.spaceSize()];
        for (int a : assemblyWeights) {
            a = INITIAL_VALUE;
        }
    }

    @Override
    public boolean add(MoleculeAgent molecule) {

        assemblyWeights[molecule.getPosition()] = molecule.getValence();
        irradiateWeight(molecule.getPosition(), molecule.getValence());
        return true;
    }

    @Override
    public void updateLayer() {
        //TODO reduce weight, disipate energy

        for (int a : this.assemblyWeights) {

            System.out.print(a);

        }
        System.out.println("");
        if ((time % STEP_DISIPATE) == 0) {
            this.calculateAssemblyWeights();
        }
        time++;
    }

    protected void calculateAssemblyWeights() {
        for (int i = 0; i < this.assemblyWeights.length; i++) {
            MoleculeAgent m = space.get(i);
            if (this.assemblyWeights[i] > 0) {
               
                this.assemblyWeights[i] -= DISIPATE_FACTOR;
            } else if (this.assemblyWeights[i] < 0) {
                this.assemblyWeights[i] += DISIPATE_FACTOR;
            }
         
                
            
        }
    }

    protected void irradiateWeight(int position, int _valence) {
        int valence = _valence;

        int sign = (valence < 0) ? -1 : 1;

        valence = Math.abs(valence);
        int fLimit = valence + position - 1;
        int iLimit = position - valence + 1;
        int counter = 1;
        while (position < fLimit) {
            int value = counter * sign;
            int validIndex = space.getValidPosition(fLimit);

            if (counter > Math.abs(assemblyWeights[validIndex])) {
                assemblyWeights[validIndex] = value;

            }
            validIndex = space.getValidPosition(iLimit);
            if (iLimit < position && counter > Math.abs(assemblyWeights[validIndex])) {
                assemblyWeights[validIndex] = value;
            }

            iLimit++;
            counter++;
            fLimit--;
        }
    }

    @Override
    protected boolean change(MoleculeAgent m, Action a) {
        this.irradiateWeight(m.getPosition(), m.getValence());
        m.sleep(1000);
        return true;
    }

    @Override
    public MoleculePercept operates(MoleculeAgent m) {
        int index = m.getPosition();
        int finalIndex = index + m.vision_ratio;
        int initialIndex = index - m.vision_ratio;

        int[] left = new int[m.vision_ratio];
        int[] right = new int[m.vision_ratio];

        for (int i = index; i < finalIndex; finalIndex--) {
            int validIndex = space.getValidPosition(finalIndex);
            right[finalIndex - i - 1] = this.assemblyWeights[validIndex];
        }
        for (int i = initialIndex; initialIndex < index; initialIndex++) {
            int validIndex = space.getValidPosition(initialIndex);
            left[initialIndex - i] = this.assemblyWeights[validIndex];
        }

        MoleculePercept mp = new MoleculePercept();
        mp.setAttribute(MoleculeSenses.LEFT_VALENCE_VALUE, left);
        mp.setAttribute(MoleculeSenses.RIGHT_VALENCE_VALUE, right);
        return mp;
    }

}
