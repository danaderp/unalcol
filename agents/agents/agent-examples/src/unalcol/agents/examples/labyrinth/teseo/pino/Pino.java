package unalcol.agents.examples.labyrinth.teseo.pino;

import java.util.ArrayList;
import java.util.Arrays;
import unalcol.agents.Action;
import unalcol.agents.Percept;
import unalcol.agents.examples.labyrinth.teseo.pino.grafo.*;
import unalcol.agents.examples.labyrinth.teseo.simple.SimpleTeseoAgentProgram;

//The Agent is named Pino...
public class Pino extends SimpleTeseoAgentProgram
{

    public static final int EXPLORAR = 0, VOLVER = 1;

    public static int counter = 0;
    
    GraphFull grafo = new GraphFull();
    GraphCritical grafoC = new GraphCritical();
    int x, y, giro, state;

    public void Pino()
    {
        x = y = giro = state = 0; // Ultima coordenada de la posicion del agente.
    }

    @Override
    public int accion(boolean PF, boolean PD, boolean PA, boolean PI, boolean MT)
    {
        Node anterior = grafo.getUltimo();

        Node actual = new Node(
                new Integer[]
                {
                    x, y
                }
        );

        if (grafo.addNode(actual))
        {
//          si el nodo es visitado por 1ra vez se añade al grafo.
            int bifurca = (PF ? 0 : 1) + (PD ? 0 : 1) + (PA ? 0 : 1) + (PI ? 0 : 1);

//          Si es el nodo inicial o bifurca mas de 2 caminos se añade al grafo de críticos            
            if (grafoC.getUltimo() == null || bifurca > 2)
                grafoC.addNode(actual);

            actual.setChildlen(
                    modificarDir(
                            new boolean[]
                            {
                                PF, PD, PA, PI
                            }
                    )
            ); //Añada qué hijos tiene.

            //comprueba antes de añadir parentesco entre nodos que no se haya añadido.
            //Comprueba que no sea el nodo inicial
            if (anterior != null)
            {
                anterior.addChildNode(actual, giro); // relaciona anterior con actual
                actual.addChildNode(anterior, (giro + 2) % 4); // relaciona actual con anterior
            }

            // Devuelve la acción en términos de las coordenadas relativas que maneja el laberinto.
            boolean escogido = false;
            while (!escogido)
            {
                int rnd = (int) (Math.random() * 5);
                switch (rnd)
                {
                    case 0:
                        if (!PI && actual.getChild()[(giro + 3) % 4] == null)
                            return movEscogido(3);
                    case 1:
                        if (!PD && actual.getChild()[(giro + 1) % 4] == null)
                            return movEscogido(1);
                    case 2:
                        if (!PA && actual.getChild()[(giro + 2) % 4] == null)
                            return movEscogido(2);
                    default:
                        if (!PF && actual.getChild()[giro] == null)
                            return movEscogido(0);
                }

                //Si es un nodo callejon el agente se devuelve y elimina la existencia de ese nodo en el laberinto
                if (bifurca == 1)
                    return -2; // camino cerrado.
            }
        }
        //Implementación cuando se llega a un nodo ya visitado.
        actual = grafoC.getNode(new Integer[]
        {
            x, y
        });
        grafo.setUltimo(actual);
        if (actual != grafoC.getUltimo())
        {
            anterior.addChildNode(actual, giro); // relaciona anterior con actual
            actual.addChildNode(anterior, (giro + 2) % 4); // relaciona actual con anterior
            anterior = grafoC.getUltimo();
            ArrayList dir = (ArrayList) grafoC.getDir().clone();
            anterior.addChildNodeC(actual, dir);
            actual.addChildNodeC(anterior, grafoC.invDir(dir));
            grafoC.clearDir();
            grafoC.setUltimo(actual);
        } else
        {
            int pos = (giro + 2) % 4;
            actual.haveChild[pos] = false;
            pos = (int) grafoC.getDir().get(0);
            actual.haveChild[pos] = false;
            int bif = 0;
            for (int i = 0; i < 4; i++)
                if (actual.haveChild[i])
                    bif++;
            grafoC.removeNode(new Integer[]
            {
                x, y
            }, giro);
            if (bif == 1)
                return -2;
        }
        return -3;
    }

    //Devuelvase hasta el último nodo crítico
    public int irAPunto()
    {
        int d = (grafoC.removeLastDir() + 6 - giro) % 4;
        if (grafoC.sizeDir() == 0)//Ya llegó al nodo crítico
            state = 3;
        return movEscogido(d);
    }

    //Busca algún camino sin explorar o retorna -1.
    public int explorar(Node actual, Node[] hijos)
    {
        for (int i = 0; i < 4; i++)
            if (actual.haveChild[(giro + i) % 4] && hijos[(giro + i) % 4] == null)
            {
                state = 0;
                grafoC.addDir((giro + i) % 4);
                return movEscogido(i);
            }
        //Implementación en caso que no hayan caminos sin explorar en este nodo.

        //Si no es nodo critico váyase al último nodo crítico
        if (!grafoC.haveNode(actual))
        {
            state = 2;
            return irAPunto();
        }
        //Aplicar Dijkstra para irse al nodo crítico más cercano con caminos disponibles 
        grafoC.dijkstra(actual);
        int mov = (grafoC.removeLastDir() + 6 - giro) % 4;
        if (grafoC.sizeDir() != 0)//Nodo crítico no está a un paso.
            state = 2;
        else
            state = 3;
        return movEscogido(mov);
        // implementar caso sin caminos nuevos para explorar.. return -1.
    }

    @Override
    public Action compute(Percept p)
    {
        try
        {
            boolean AF = (Boolean) p.getAttribute("afront");
            boolean AD = (Boolean) p.getAttribute("aright");
            boolean AA = (Boolean) p.getAttribute("aback");
            boolean AI = (Boolean) p.getAttribute("aleft");
            if ((AF || AD || AA || AI) && counter < 7)
            {
                counter++;
                return new Action("no_op");
            }
        } catch (Exception e)
        {
        }
        counter = 0;
        if ((Boolean) p.getAttribute("exit"))
        {
            return new Action("no_op");
        }
        int d = 0;
        if (cmd.size() == 0)
        {
            if (state == EXPLORAR)
            {
                boolean PF = (Boolean) p.getAttribute("front");
                boolean PD = (Boolean) p.getAttribute("right");
                boolean PA = (Boolean) p.getAttribute("back");
                boolean PI = (Boolean) p.getAttribute("left");
                
                d = accion(PF, PD, PA, PI, false);
                
                if (d < 0)
                {
                    switch (d)
                    {
                        case -3:
                            d = explorar(grafo.getUltimo(), grafo.getUltimo().getChild());
                            break;
                        case -2:
                            state = 2;
                            d = irAPunto();
                            break;
                    }
                } else
                    grafoC.addDir(giro);
            } else if (state == 2)
            {
                d = irAPunto();
            } else if (state == 3)
            {
                Node actual = grafoC.getNode(new Integer[]
                {
                    x, y
                });
                if (actual == grafoC.getUltimo())
                {
                    Node[] hijos = actual.getChild();
                    //Podar camino cerrado por infructuoso
                    actual.haveChild[(giro + 2) % 4] = false;
                    hijos[(giro + 2) % 4] = null;
                } else
                    grafoC.setUltimo(actual);
                grafo.setUltimo(actual);
                d = explorar(actual, actual.getChild());
            }
            loadMov(d);
        }

        String x = cmd.get(0);
        cmd.remove(0);
        return new Action(x);
    }

    private void loadMov(int d)
    {
        for (int i = 1; i <= d; i++)
            cmd.add(language.getAction(3)); //rotate
        cmd.add(language.getAction(2)); // advance
    }

    @Override
    public boolean goalAchieved(Percept p)
    {
        return (((Boolean) p.getAttribute(language.getPercept(4))));
    }

    private int movEscogido(int o)
    {
        giro += o;
        giro %= 4;
        switch (giro)
        {
            case 0:
                y++;
                break;
            case 1:
                x++;
                break;
            case 2:
                y--;
                break;
            default:
                x--;
                break;
        }
        return o;
    }

    private boolean[] modificarDir(boolean[] paredes)
    {
        boolean[] temp = Arrays.copyOf(paredes, paredes.length);
        paredes[giro] = temp[0];
        paredes[(giro + 1) % 4] = temp[1];
        paredes[(giro + 2) % 4] = temp[2];
        paredes[(giro + 3) % 4] = temp[3];
        return paredes;
    }
}
