package unalcol.optimization.transformation;
import unalcol.reflect.util.*;
import unalcol.reflect.service.*;

/**
 * <p>Title: TransformationProvider</p>
 * <p>Description: Evolutionary Transformation provider. It is for compatibility with
 * the service provider infra-structure</p>
 * <p>Copyright: Copyright (c) 2010</p>
 * <p>Company: Kunsamu</p>
 * @author Jonatan Gomez
 * @version 1.0
 */

public class TransformationProvider {

    public static Transformation[] get(){
        try{
           Service[] services = ReflectUtil.getProvider().
                   available_services(Transformation.class, Object.class);
           Transformation[] tr = new Transformation[services.length];
           System.out.println( services.length );
           for( int i=0; i<services.length; i++ ){
               System.out.println(services[i].getClass().getCanonicalName());
               tr[i] = (Transformation)services[i];
           }
           return tr;
        }catch( Exception e ){
            e.printStackTrace();
        }
        return new Transformation[0];
    }

    public static Transformation get( Object obj ){
        try{
            return ((Transformation)
                    ReflectUtil.getProvider().
                    default_service(Transformation.class,obj));
        }catch( Exception e ){
        }
        return null;
    }
}
