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
import unalcol.lifesim.layers.view.LayerView;
import unalcol.random.integer.UniformIntegerGenerator;

/**
 *
 * @author daniel
 */
public class PhysicLayer extends Layer {

    //the space temperature
    public static double TEMPERATURE = 0.5;

    //The range of valence integer numbers.
    public static int MIN_VALENCE = -5;

    public static int MAX_VALENCE = 5;

    public UniformIntegerGenerator valenceGenerator;
    
    UniformIntegerGenerator g;

    public PhysicLayer(Space _space, Layer _nextLayer) {
        super(_space, _nextLayer);
        valenceGenerator = new UniformIntegerGenerator(MIN_VALENCE, MAX_VALENCE);
        g = new UniformIntegerGenerator(space.spaceSize() - 1);
        
    }

    /**
     * Adding an agent to array, if the array size is not enough the agent can
     * not be added, this method assign a random position to insert the agent.
     *
     * @param molecule to add in to the array environment
     * @return true if it was possible to find a position to insert the agent
     * and there are avaliable position where does not exist another agent;
     * false in other case
     */
    @Override
    protected boolean add(MoleculeAgent molecule) {
        //If the space is not full
        if (space.spaceSize() != space.usedSpaceSize()) {
            //Creates a random index number to put the molecule.
            
            int valence = valenceGenerator.next();
            molecule.setValence(valence);
         
            int index = g.next();
            int n = 0;
            int counter = 0;
            //It can not use the same position with other molecule and
            // can not take a position outside the space
            while (space.get(index) != null && counter < space.spaceSize()) {
                index = g.next();
                counter++;
            }
            molecule.setPosition(index);
            //Insert the molecule to the specific position.
            space.add(index, molecule);
            return true;
        }
        return false;
    }

    @Override
    protected void updateLayer() {
       /* for(MoleculeAgent a: this.space){
         if(a!=null)
         System.out.print(a.isStable());
         else
         System.out.print("0");
         }
         System.out.println("");*/
        //System.out.println(this.space.usedSpaceSize());
        this.display();
    }

    @Override
    protected synchronized boolean change(MoleculeAgent m, Action a) {
        int validIndex;
        int counter=0;
        switch (a.getCode()) {
            case "left":

                validIndex = space.getValidPosition(m.getPosition() - 1);
                
                m.changePosition(validIndex);
                for(MoleculeAgent ma:this.space.getBuffer()){
                    if(ma!=null)counter++;
                }  if(counter<this.space.usedSpaceSize()){
                                 System.out.println("left  "+counter);

                    }
                break;
            case "right":
                validIndex = space.getValidPosition(m.getPosition() + 1);
                if(space.get(validIndex)==null)
                    m.changePosition(validIndex);
                 for(MoleculeAgent ma:this.space.getBuffer()){
                    if(ma!=null)counter++;
                    
                }
                 if(counter<this.space.usedSpaceSize()){
                                 System.out.println("right  "+counter);

                    }
                break;
            case "jump":
                //UniformIntegerGenerator g = new UniformIntegerGenerator(space.spaceSize() - 1);
                validIndex = g.next();
                while (space.get(validIndex) != null) {
                    validIndex = g.next();
                }
                //TODO: put the chain in a valid position
                m.changePosition(validIndex);
                 for(MoleculeAgent ma:this.space.getBuffer()){
                    if(ma!=null)counter++;
                   
                }
                  if(counter<this.space.usedSpaceSize()){
                                 System.out.println("jump  "+counter);

                    }
                break;
            default:
                break;
        }
        //notifyAll();
        
        return true;
    }

    @Override
    protected MoleculePercept operates(MoleculeAgent m) {
        int index = m.getPosition();
        int finalIndex = index + m.vision_ratio;
        int initialIndex = index - m.vision_ratio;
        MoleculeAgent leftm = null, rightm = null;
        int left = m.vision_ratio, right = m.vision_ratio;
        for (int i = index - 1; i >= initialIndex && left == m.vision_ratio; i--) {
            int realIndex = space.getValidPosition(i);
            left += (space.get(realIndex) != null) ? -1 : 0;

            //TODO: Fix this code
            if (i == index - 1) {
                leftm = space.get(realIndex);
            }
        }
        for (int i = index + 1; i <= finalIndex && right == m.vision_ratio; i++) {
            int realIndex = space.getValidPosition(i);
            right += (space.get(realIndex) != null) ? -1 : 0;
            if (i == index + 1) {
                rightm = space.get(realIndex);
            }
        }
        MoleculePercept mp = new MoleculePercept();
        mp.setAttribute(MoleculeSenses.LEFT_SPACE, left);
        mp.setAttribute(MoleculeSenses.RIGHT_SPACE, right);
        mp.setAttribute(MoleculeSenses.RIGHT_MOL, rightm);
        mp.setAttribute(MoleculeSenses.LEFT_MOL, leftm);

        return mp;

    }

    @Override
    public void display() {
       
        for (int i = 0; i < space.spaceSize(); i++) {
            MoleculeAgent a = space.get(i);
            if (a !=null) {
               
                viewer.fill(a.pixel_c);
                if(a.getValence()==-5){
                    viewer.fill(viewer.color(255,0,0));
                }
            } else {
                viewer.fill(255);
            }
            viewer.noStroke();
            viewer.rect(i * LayerView.pixel_size, current_raw * LayerView.pixel_size, 
                    LayerView.pixel_size, LayerView.pixel_size);
        }
       
       
        /*if(current_raw==0){
            viewer.background(255);
        }*/
    }

}
