/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unalcol.lifesim;

import processing.core.PApplet;
import unalcol.lifesim.environment.Environment;

/**
 * <p>
 * Self Assembly and Self replication  </p>
 *
 * <p>
 * Description: </p>
 *
 * <p>
 * Copyright: Copyright (c) 2015</p>
 *
 * <p>
 * Company: Universidad Nacional de Colombia</p>
 *
 * @author Daniel Rodríguez
 * @version 1.0
 */
public class Lifesim {

   

    public static void main(String[] args) {
        
        PApplet.main(new String[] { "--present", "unalcol.lifesim.environment.Environment" });

    }
}
