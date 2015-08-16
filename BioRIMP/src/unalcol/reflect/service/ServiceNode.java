package unalcol.reflect.service;

import unalcol.types.collection.vector.*;
import java.util.Hashtable;

/**
 * <p>A node in the Service Hierarchy infra-structure.</p>
 *
 * @author Jonatan Gomez Perdomo
 * @version 1.0
 */
public class ServiceNode {
    /**
     * The class of services this node maintains
     */
    protected Class service_class = null;

    /**
     * Instances of the service class maintained by this node
     */
    protected Vector<Service> instances = null;

    /**
     * The collection of services sub-classes (specialized services) that has been
     * defined for the service class maintained by the node
     */
    protected Vector<ServiceNode> sub_classes = new Vector();

    /**
     * The collection of super classes the class maintained by this node is defining
     */
    protected Vector<ServiceNode> super_classes = new Vector();

    /**
     * Currently used service for the class this node is maintaining
     */
    protected Hashtable<Object, Service> default_service = new Hashtable();

    /**
     * The class of service this node will maintain
     * @param service Service class
     */
    public ServiceNode( Class service ){
        this.service_class = service;
        this.instances = new Vector();
        try{
            Service instance = (Service)service.newInstance();
            this.instances.add(instance);
        }catch( Exception e ){
        }
    }

    public boolean add( Service s ){
        if( defaultService(s.owner()) == null ){
            setDefaultService(s.owner(), s);
        }
        return instances.add( s );
    }

    public boolean add( ServiceNode s_node ){
        s_node.super_classes.add(this);
        return sub_classes.add( s_node );
    }

    public ServiceNode locate( Class service ){
        if( this.service_class != service ){
            int k=0;
            while( k<sub_classes.size() &&
                   !sub_classes.get(k).service_class.isAssignableFrom(service) ){
                k++;
            }
            if( k < sub_classes.size() ){
                return sub_classes.get(k).locate( service );
            }else{
                return null;
            }
        }else{
            return this;
        }
    }

    public boolean canUse( Service service, Class owner ){
        return owner != null &&
               ( service.owner() == owner ||
                 canUse( service, owner.getSuperclass() ) );
    }

    public boolean canUse( Service service, Object owner ){
        return owner != null &&
               ( service.owner() == owner ||
                 canUse( service, owner.getClass() ) );
    }

    protected void available_services( Object owner, Vector<Service> collection ){
        for( int i=0; i<instances.size(); i++ ){
            if( canUse( instances.get(i), owner ) ){
                collection.add(instances.get(i));
            }
        }
        for( int i=0; i<sub_classes.size(); i++ ){
           sub_classes.get(i).available_services(owner, collection);
        }
    }

    protected Service[] convert( Vector<Service> col ){
        Service[] scol = new Service[col.size()];
        for( int i=0; i<col.size(); i++ ){
            scol[i] = col.get(i);
        }
        col.clear();
        return scol;
    }

    public Service[] available_services( Object owner ){
        Vector<Service> col = new Vector();
        available_services(owner, col);
        return convert(col);
    }

    protected void owned_services( Object owner, Vector<Service> collection ){
        for( int i=0; i<instances.size(); i++ ){
            if( instances.get(i).owner() == owner ){
                collection.add(instances.get(i));
            }
        }
        for( int i=0; i<sub_classes.size(); i++ ){
           sub_classes.get(i).owned_services(owner, collection);
        }
    }

    public Service[] owned_services( Object owner ){
        Vector<Service> col = new Vector();
        owned_services(owner, col);
        return convert(col);
    }

    public boolean remove_owned_service( Object owner, Service service ){
        boolean flag = false;
        int i=0;
        while( i<instances.size() ){
            if( instances.get(i).owner() == owner && instances.get(i) == service ){
                instances.remove(i);                
                flag = true;
            }else{
                i++;
            }
        }
        for( i=0; i<sub_classes.size(); i++ ){
           flag |= sub_classes.get(i).remove_owned_service(owner, service);
        }
        return flag;
    }


    public Service defaultService( Object owner ){
        Service s = null;
        if( owner != null ){
            s = default_service.get(owner);
            if( s == null ){
                if( Class.class.isInstance(owner) ){
                    Class own = (Class)owner;
                    s = defaultService(own.getSuperclass());
                    int i=0;
                    Class[] super_interfaces = own.getInterfaces();
                    while( i<super_interfaces.length && s == null ){
                        s = defaultService(super_interfaces[i]);
                        i++;
                    }
                }else{
                    s = defaultService(owner.getClass());
                }
            }
        }
        return s;
    }

    public Service setDefaultService( Object owner, Service service ){
        Service old = default_service.put(owner, service);
        for( int i=0; i<super_classes.size(); i++){
            super_classes.get(i).setDefaultService(owner, service);
        }
        return old;
    }

    protected void serviceClasses( Vector<Class> classes ){
        classes.add(service_class);
        for( int i=0; i<sub_classes.size(); i++ ){
            sub_classes.get(i).serviceClasses(classes);
        }
    }

    public Class[] serviceClases(){
        Vector<Class> classes = new Vector();
        this.serviceClasses(classes);
        Class[] array = new Class[classes.size()];
        for( int i=0; i<classes.size(); i++){
            array[i] = classes.get(i);
        }
        return array;
    }
}