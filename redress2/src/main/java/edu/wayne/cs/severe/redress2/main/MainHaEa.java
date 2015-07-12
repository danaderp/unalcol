package edu.wayne.cs.severe.redress2.main;

import edu.wayne.cs.severe.redress2.entity.ProgLang;

/**
 * Created by Alberto on 6/20/2015.
 */
public class MainHaEa {

    public static void main(String[] argss) {
        String userPath = System.getProperty("user.dir");
        String[] args = { "-l", "Java", "-p", userPath+"\\unalcol\\agents\\agents\\agent-examples\\src","-s", "     HAEA_P      " };
        MainPredFormulas.main(args);
    }

}
