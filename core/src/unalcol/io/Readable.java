/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package unalcol.io;

/**
 *
 * @author jgomez
 */
public interface Readable<T> {
    public T read(ShortTermMemoryReader reader);
}
