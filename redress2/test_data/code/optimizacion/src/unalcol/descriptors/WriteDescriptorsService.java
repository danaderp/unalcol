/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package unalcol.descriptors;

import java.io.Writer;
import unalcol.io.Persistency;
import unalcol.io.WriteService;

/**
 *
 * @author jgomez
 */
public class WriteDescriptorsService implements WriteService{

    @Override
    public void write(Object obj, Writer writer) throws Exception {
        double[] d = DescriptorsProvider.descriptors(obj);
        Persistency.write(d, writer);
    }

    @Override
    public Object owner() {
        return Object.class;
    }
    
}
