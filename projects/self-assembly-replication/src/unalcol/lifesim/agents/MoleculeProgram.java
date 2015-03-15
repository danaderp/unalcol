/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unalcol.lifesim.agents;

import unalcol.agents.Action;
import unalcol.agents.AgentProgram;
import unalcol.agents.Percept;
import unalcol.random.util.BooleanGenerator;

/**
 *
 * @author daniel
 */
public class MoleculeProgram implements AgentProgram {

    @Override
    public Action compute(Percept p) {
        MoleculePercept mp = (MoleculePercept) p.getAttribute("Param");
        int leftDistance = (Integer) mp.get(MoleculeSenses.LEFT_SPACE);
        int rightDistance = (Integer) mp.get(MoleculeSenses.RIGHT_SPACE);
        int leftValences[] = (int[]) mp.get(MoleculeSenses.LEFT_VALENCE_VALUE);
        int rightValences[] = (int[]) mp.get(MoleculeSenses.RIGHT_VALENCE_VALUE);
        int valence = (Integer) mp.get(MoleculeSenses.VALENCE_VALUE);
        MoleculeAgent leftm, rightm;
        rightm = (MoleculeAgent) mp.get(MoleculeSenses.RIGHT_MOL);
        leftm = (MoleculeAgent) mp.get(MoleculeSenses.LEFT_MOL);
        MoleculeAgent itself = (MoleculeAgent) mp.get(MoleculeSenses.MOL_SEFL);

        Action action = new Action("");
        if (itself.isStable()|| itself.getBonds().size()==MoleculeAgent.NEIGHTS) {
            return action;
        }

        int sign = (valence < 0) ? -1 : 1;

        if (leftm != null) {
            if (!itself.getBonds().contains(leftm)) {
                int sign2 = (leftm.getReminingV() < 0) ? -1 : 1;

                if (sign + sign2 == 0) {
                    action = new Action("bondleft");
                    return action;
                }
            }
        }
        if (rightm != null) {
            if (!itself.getBonds().contains(rightm)) {
                int sign2 = (rightm.getReminingV() < 0) ? -1 : 1;
                if (sign + sign2 == 0) {
                    action = new Action("bondright");
                    return action;
                }
            }
        }
        //Program Logic to move the agent

        if (leftDistance < rightDistance) {
            if ((leftValences[0] + valence) < valence) {
                action = new Action("left");
            } else {
                action = new Action("right");
            }
        } else if (leftDistance > rightDistance) {
            if ((rightValences[0] + valence) < valence) {
                action = new Action("right");
            } else {
                action = new Action("left");
            }
        } else if (!itself.isBonded()) {
            action = new Action("jump");//new Action("left");
        } else {
            BooleanGenerator g = new BooleanGenerator(0.8);
            if (g.next()) {
                action = new Action("right");
            } else {
                action = new Action("left");
            }
        }

        return action;
    }

    @Override
    public void init() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
