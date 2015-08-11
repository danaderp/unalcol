package unalcol.optimization.generation;
import unalcol.reflect.service.*;
import unalcol.reflect.util.*;

/**
 * <p>Title: CandidatesGenerationProvider</p>
 * <p>Description: Candidates Generation provider. It is for compatibility with
 * the service provider infra-structure</p>
 * <p>Copyright: Copyright (c) 2010</p>
 * <p>Company: Kunsamu</p>
 * @author Jonatan Gomez
 * @version 1.0
 */

public class CandidatesGenerationProvider {

    public static PopulationGeneration[] get(){
        try{
           Service[] services = ReflectUtil.getProvider().
                   owned_services(PopulationGeneration.class, Object.class);
           PopulationGeneration[] tr = new PopulationGeneration[services.length];
           for( int i=0; i<services.length; i++ ){
               tr[i] = (PopulationGeneration)services[i];
           }
           return tr;
        }catch( Exception e ){
            e.printStackTrace();
        }
        return new PopulationGeneration[0];
    }

    public static PopulationGeneration get( Object obj ){
        try{
            return ((PopulationGeneration)
                    ReflectUtil.getProvider().
                    default_service(PopulationGeneration.class,obj));
        }catch( Exception e ){
        }
        return null;
    }
}
