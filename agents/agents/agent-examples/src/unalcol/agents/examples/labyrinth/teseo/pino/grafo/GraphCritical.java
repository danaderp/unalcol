/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unalcol.agents.examples.labyrinth.teseo.pino.grafo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

/**
 * @author awake
 */
public class GraphCritical implements Graph
{

    private Map<Coord, Node> vertices = new HashMap<>();
    private Node ultimo;
    private ArrayList<Integer> dir = new ArrayList<>();

    ;

    public static class Coord
    {

        Integer[] xy;

        public Coord(Integer[] xy)
        {
            this.xy = xy;
        }

        @Override
        public boolean equals(Object o)
        {
            if (o instanceof Coord)
            {
                Coord p = (Coord) o;
                if (this.xy[0] == p.xy[0] && this.xy[1] == p.xy[1])
                    return true;
                return false;
            }
            return false;
        }

        @Override
        public int hashCode()
        {
            int hash = 3;
            hash = 97 * hash + Arrays.deepHashCode(this.xy);
            return hash;
        }
    }

    @Override
    public boolean addNode(Node n)
    {
        n.iniNodeCrit(); // Le dice que genere espacio para almacenar vecinos criticos
        if (ultimo != null)
        {
            ultimo.addChildNodeC(n, (ArrayList) dir.clone());
            n.addChildNodeC(ultimo, invDir(dir));
            dir.clear();
        }
        ultimo = n;
        vertices.put(new Coord(n.getCoord()), n);
        return true;
    }

    public void removeNode(Integer[] xy, int giro)
    {
        Node temp = vertices.remove(new Coord(xy));
        Node.NodeCritico[] a = new Node.NodeCritico[3];
        a[0] = temp.getNodeC((giro) % 4);
        a[1] = temp.getNodeC((giro + 1) % 4);
        a[2] = temp.getNodeC((giro + 3) % 4);
        int aa[] = new int[]
        {
            0, 0
        };
        int t = 0;
        for (int i = 0; i < 3; i++)
            if (a[i] != null)
            {
                aa[t] = i;
                t++;
            }
        Node.NodeCritico vecino1, vecino2;
        vecino1 = a[aa[0]];
        if (t == 2)
        {
            vecino2 = a[aa[2]];
            t = findPos(vecino1);
            ArrayList dirT = vecino1.n.getNodeC(t).dir;
            dirT.addAll(vecino2.dir);
            vecino1.n.addChildNodeC(vecino2.n, dirT);
            int tt = findPos(vecino2);
            ArrayList dirTe = vecino2.n.getNodeC(tt).dir;
            dirTe.addAll(vecino1.dir);
            vecino2.n.addChildNodeC(vecino1.n, dirTe);
            if (vecino1.dist <= vecino2.dist)
            {
                ultimo = vecino1.n;
                dir = (ArrayList) ultimo.getNodeC(t).dir.clone();
            } else
            {
                ultimo = vecino2.n;
                dir = (ArrayList) ultimo.getNodeC(tt).dir.clone();
            }
            //No hay caminos sin explorar. Devolverse hasta algun camino sin recorrer, osea: return -1, por medio de return -3.
        } else
        {
            ultimo = vecino1.n;
            t = findPos(vecino1);
            dir = (ArrayList) ultimo.getNodeC(t).dir.clone();
            ultimo.removeNodeC(t);
        }
    }

    public int findPos(Node.NodeCritico n)
    {
        return (n.dir.get(n.dir.size() - 1) + 2) % 4;
    }

    @Override
    public Node getUltimo()
    {
        return ultimo;
    }

    public void setUltimo(Node u)
    {
        ultimo = u;
    }

    public Node getNode(Integer[] xy)
    {
        return vertices.get(new Coord(xy));
    }

    public boolean haveNode(Node n)
    {
        return vertices.containsKey(new Coord(n.getCoord()));
    }

    public void addDir(int dirr)
    {
        dir.add(dirr);
    }

    public int removeLastDir()
    {
        return dir.remove(dir.size() - 1);
    }

    public void setDir(ArrayList dirr)
    {
        dir = dirr;
    }

    public ArrayList getDir()
    {
        return dir;
    }

    public void clearDir()
    {
        dir.clear();
    }

    public ArrayList invDir(ArrayList<Integer> dir)
    {
        ArrayList<Integer> temp = new ArrayList<>();
        for (int i = dir.size() - 1; i >= 0; i--)
            temp.add((dir.get(i) + 2) % 4);
        return temp;
    }

    public int sizeDir()
    {
        return dir.size();
    }

    public void dijkstra(Node ini)
    {
        ArrayList<Node> qq = new ArrayList<>();
        ArrayList<Node> pp = new ArrayList<>();

        for (Node n : vertices.values())
        {
            n.dist = Integer.MAX_VALUE;
            n.ant = -1;
            qq.add(n);
        }
        ini.dist = 0;

        Node u, obj = null;
        Node.NodeCritico v;
        while (!qq.isEmpty())
        {
            sortedList(qq);
            u = qq.remove(0);
            if (u.dist == Integer.MAX_VALUE)
                break; // we can ignore u (and any other remaining vertices) since they are unreachable

            for (int i = 0; i < 4; i++)
            {
                v = u.getNodeC(i);
                if (v != null)
                {
                    int alternateDist = u.dist + v.dist;
                    if (alternateDist < v.n.dist)
                    {
                        v.n.dist = alternateDist;
                        v.n.ant = findPos(v);
                    }
                }
            }
            pp.add(u);
        }
        while (obj == null)
        {
            Node temp = pp.remove(0);
            if (temp.haveWayNew())
                obj = temp;
        }
        loadWay(obj);
    }

    public void sortedList(ArrayList<Node> q)
    {
        Collections.sort(q, new Comparator()
        {
            @Override
            public int compare(Object o1, Object o2)
            {
                if (o1 instanceof Node && o2 instanceof Node)
                {
                    Node p1 = (Node) o1;
                    Node p2 = (Node) o2;
                    return new Integer(p1.dist).compareTo(p2.dist);
                }
                return 0;
            }
        });
    }

    public void loadWay(Node n)
    {
        if (n.ant != -1)
        {
            dir.addAll(n.getNodeC(n.ant).dir);
            loadWay(n.getNodeC(n.ant).n);
        }
    }
}
