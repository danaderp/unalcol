/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unalcol.lifesim;

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

    //The Environment size
    public final int environmentSize = 100;

    public Lifesim() {

       
        Environment environment = new Environment(environmentSize);
        environment.createInitialPop();

        Thread thread = new Thread(environment);
        thread.start();


    }

    public static void main(String[] args) {
        Lifesim lifesim = new Lifesim();

    }
}
