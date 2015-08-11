package main;

import edu.wayne.cs.severe.redress2.entity.ProgLang;
import edu.wayne.cs.severe.redress2.main.MainMetrics;
import edu.wayne.cs.severe.redress2.main.MainPredFormulas;

/**
 * Created by Alberto on 6/20/2015.
 */
public class MainHaEa {

    public static void main(String[] argss) {
        String userPath = System.getProperty("user.dir");
        //String[] args = { "-l", "Java", "-p", userPath+"\\test_data\\code\\optimizacion\\src","-s", "     optimizacion      " };
        // MainMetrics.main(args);
        String[] args = { "-l", "JAVA", "-p", userPath+"\\test_data\\code\\optimization\\src", "-s", "     optimization      ",
				"-r", userPath+"\\test_data\\refs\\refsDummyOpt.txt" };
        MainPredFormulas.main(args);
    }

}
