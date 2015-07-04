package unalcol.sort;
import unalcol.reflect.service.*;

/**
 * <p>Abstract class, determines if the object one is less, greater or equal than object two</p>
 * <p>Copyright: Copyright (c) 2010</p>
 * 
 * @author Jonatan Gomez
 * @version 1.0
 *
 */
public interface Order<T> extends Service{
    /**
     * Determines if one elements is less, equal or greather than other.
     * A value < 0 indicates that one is less than two, a value = 0 indicates
     * that one is equal to two and a value > 0 indicates that one is greather than two
     * @param one First object to be compared
     * @param two Second object to be compared
     * @return a value < 0 if one < two, 0 if one == two and > 0 if one > two.
     */
    public int compare(T one, T two);
}