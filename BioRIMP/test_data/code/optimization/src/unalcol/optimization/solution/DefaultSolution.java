package unalcol.optimization.solution;

public class DefaultSolution extends Solution{
    protected Object obj;
    public DefaultSolution( Object obj, double val ){
        this.obj = obj;
        this.value = val;
    }
    public Object get(){ return obj; }
}