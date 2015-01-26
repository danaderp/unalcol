/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unalcol.agents.examples.squares.BPS;

/**
 *
 * @author bps_csp
 */
public class Tuple<F, S>
{

    public Tuple(F first, S second)
    {
        this.first = first;
        this.second = second;
    }

    public F first;
    public S second;
}
