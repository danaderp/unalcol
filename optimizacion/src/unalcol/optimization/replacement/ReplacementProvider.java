package unalcol.optimization.replacement;
import unalcol.optimization.replacement.Replacement;
import unalcol.reflect.service.*;
import unalcol.reflect.util.*;

/**
 * <p>Title: ReplacementProvider</p>
 * <p>Description: Replacement provider. It is for compatibility with
 * the service provider infra-structure</p>
 * <p>Copyright: Copyright (c) 2010</p>
 * <p>Company: Kunsamu</p>
 * @author Jonatan Gomez
 * @version 1.0
 */
public class ReplacementProvider {

    public static Replacement[] get(){
        try{
           Service[] services = ReflectUtil.getProvider().
                   owned_services(Replacement.class, Object.class);
           Replacement[] tr = new Replacement[services.length];
           for( int i=0; i<services.length; i++ ){
               tr[i] = (Replacement)services[i];
           }
           return tr;
        }catch( Exception e ){
            e.printStackTrace();
        }
        return new Replacement[0];
    }

    public static Replacement get( Object obj ){
        try{
            return ((Replacement)
                    ReflectUtil.getProvider().
                    default_service(Replacement.class,obj));
        }catch( Exception e ){
        }
        return null;
    }

}
