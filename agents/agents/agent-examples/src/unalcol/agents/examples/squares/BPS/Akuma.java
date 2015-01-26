package unalcol.agents.examples.squares.BPS;

import java.awt.Point;
import java.util.ArrayList;
import unalcol.agents.Action;
import unalcol.agents.AgentProgram;
import unalcol.agents.Percept;
import unalcol.agents.examples.squares.*;
import unalcol.types.collection.vector.Vector;

/**
 * @author bps_csp
 */
public class Akuma implements AgentProgram {

    protected String color;
    protected BoardAkuma memory;

    public Akuma(String color) {
        this.color = color;
    }

    private void fillBoard(Percept p) {
        int size = Integer.parseInt((String) p.getAttribute(Squares.SIZE));

        memory = new BoardAkuma(size);

        for (int y = 0; y < size; y++)
            for (int x = 0; x < size; x++) {
                if (((String) p.getAttribute(y + ":" + x + ":" + Squares.BOTTOM)).equals(Squares.TRUE))
                    memory.play(y, x, BoardAkuma.BOTTOM);
                if (((String) p.getAttribute(y + ":" + x + ":" + Squares.LEFT)).equals(Squares.TRUE))
                    memory.play(y, x, BoardAkuma.LEFT);
                if (((String) p.getAttribute(y + ":" + x + ":" + Squares.RIGHT)).equals(Squares.TRUE))
                    memory.play(y, x, BoardAkuma.RIGHT);
                if (((String) p.getAttribute(y + ":" + x + ":" + Squares.TOP)).equals(Squares.TRUE))
                    memory.play(y, x, BoardAkuma.TOP);
            }
    }

    private void updateBoard(Percept p) {
        int size = Integer.parseInt((String) p.getAttribute(Squares.SIZE));

        for (int y = 0; y < size; y++)
            for (int x = 0; x < size; x++) {
                if (((String) p.getAttribute(y + ":" + x + ":" + Squares.BOTTOM)).equals(Squares.TRUE))
                    memory.play(y, x, BoardAkuma.BOTTOM);
                if (((String) p.getAttribute(y + ":" + x + ":" + Squares.LEFT)).equals(Squares.TRUE))
                    memory.play(y, x, BoardAkuma.LEFT);
                if (((String) p.getAttribute(y + ":" + x + ":" + Squares.RIGHT)).equals(Squares.TRUE))
                    memory.play(y, x, BoardAkuma.RIGHT);
                if (((String) p.getAttribute(y + ":" + x + ":" + Squares.TOP)).equals(Squares.TRUE))
                    memory.play(y, x, BoardAkuma.TOP);
            }
    }

    void printBoard() {
        System.out.println(memory);
    }

    Action dummyAction(Percept p) {
        int size = Integer.parseInt((String) p.getAttribute(Squares.SIZE));
        int i = 0;
        int j = 0;
        Vector<String> v = new Vector();
        while (v.size() == 0) {
            i = (int) (size * Math.random());
            j = (int) (size * Math.random());
            if (((String) p.getAttribute(i + ":" + j + ":" + Squares.LEFT)).equals(Squares.FALSE))
                v.add(Squares.LEFT);
            if (((String) p.getAttribute(i + ":" + j + ":" + Squares.TOP)).equals(Squares.FALSE))
                v.add(Squares.TOP);
            if (((String) p.getAttribute(i + ":" + j + ":" + Squares.BOTTOM)).equals(Squares.FALSE))
                v.add(Squares.BOTTOM);
            if (((String) p.getAttribute(i + ":" + j + ":" + Squares.RIGHT)).equals(Squares.FALSE))
                v.add(Squares.RIGHT);
        }
        return new Action(i + ":" + j + ":" + v.get((int) (Math.random() * v.size())));
    }

    Action limitBreakAction(Percept p) {
        ArrayList<Point> list_posibles = getPossiblesLess(p).first;
        int[] directions = new int[]{BoardAkuma.BOTTOM, BoardAkuma.LEFT, BoardAkuma.RIGHT, BoardAkuma.TOP};
        
        Tuple<Tuple<Point, Integer>, Integer> best = new Tuple(null, Integer.MIN_VALUE);
        
        for(Point temp : list_posibles)
            for(int direction : directions){
                int fitness = evaluate(p, temp.x, temp.y, direction);
                System.out.println("Fitnes: "+fitness);
                if( fitness >= best.second)
                    best = new Tuple(new Tuple(temp, direction), fitness);
            }
        
        String dir = "";
        
        if(best.first == null) return new Action(Squares.PASS);
        
        if(best.first.second == BoardAkuma.BOTTOM)  dir = Squares.BOTTOM;
        if(best.first.second == BoardAkuma.LEFT)  dir = Squares.LEFT;
        if(best.first.second == BoardAkuma.RIGHT)  dir = Squares.RIGHT;
        if(best.first.second == BoardAkuma.TOP)  dir = Squares.TOP;
        
        return new Action(best.first.first.x+ ":" + best.first.first.y + ":" + dir);
    }
    
    int evaluate(Percept p, int x, int y, int direction)
    {
        BoardAkuma memory_cp = new BoardAkuma(memory);
        
        int fitness = 0;
        
        if(!memory_cp.play(y, x, direction)) return -100;
        
        memory_cp.play(y, x, get4Line(p, y, x));
        
        int size = Integer.parseInt((String) p.getAttribute(Squares.SIZE));
        
        for(int i = 0; i < size; i++)
            for(int j = 0; j < size; j++)
                fitness += memory.values[i][j] != memory_cp.values[i][j] ? 1 : 0;
        
        return -fitness;
    }
    
    int get4Line(Percept p, int x, int y)
    {
        if (((String) p.getAttribute(y + ":" + x + ":" + Squares.LEFT)).equals(Squares.FALSE))
            return (BoardAkuma.LEFT);
        if (((String) p.getAttribute(y + ":" + x + ":" + Squares.TOP)).equals(Squares.FALSE))
            return (BoardAkuma.TOP);
        if (((String) p.getAttribute(y + ":" + x + ":" + Squares.BOTTOM)).equals(Squares.FALSE))
            return (BoardAkuma.BOTTOM);
        //if (((String) p.getAttribute(x + ":" + y + ":" + Squares.RIGHT)).equals(Squares.FALSE))
        return (BoardAkuma.RIGHT);
    }

    Tuple<ArrayList<Point>, Point> getPossibles(Percept p) {

        ArrayList<Point> result = new ArrayList<>();

        int size = Integer.parseInt((String) p.getAttribute(Squares.SIZE));

        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++) {

                int count = memory.lines(i, j);

                if (count == 2) {
                    result.add(new Point(j, i));
                    if (getBannedN(p, j, i)[0].equals(Squares.TOP) || getBannedN(p, j, i)[1].equals(Squares.TOP))
                        result.add(new Point(j, i - 1));
                    if (getBannedN(p, j, i)[0].equals(Squares.BOTTOM) || getBannedN(p, j, i)[1].equals(Squares.BOTTOM))
                        result.add(new Point(j, i + 1));
                    if (getBannedN(p, j, i)[0].equals(Squares.RIGHT) || getBannedN(p, j, i)[1].equals(Squares.RIGHT))
                        result.add(new Point(j + 1, i));
                    if (getBannedN(p, j, i)[0].equals(Squares.LEFT) || getBannedN(p, j, i)[1].equals(Squares.LEFT))
                        result.add(new Point(j - 1, i));
                }
                if (count == 3)
                    return new Tuple(result, new Point(j, i));
            }

        return new Tuple(result, null);
    }

    Tuple<ArrayList<Point>, Point> getPossiblesLess(Percept p) {

        ArrayList<Point> result = new ArrayList<>();

        int size = Integer.parseInt((String) p.getAttribute(Squares.SIZE));

        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++) {

                int count = memory.lines(i, j);

                if (count == 2)
                    result.add(new Point(j, i));
                if (count == 3)
                    return new Tuple(result, new Point(j, i));
            }

        return new Tuple(result, null);
    }

    Action testAction(Percept p, ArrayList<Point> banned, int recursion) {

        if((Integer.parseInt(String.valueOf((p.getAttribute(this.color + "_" + Squares.TIME).toString().charAt(4))))) < 2 && (Integer.parseInt(String.valueOf((p.getAttribute(this.color + "_" + Squares.TIME).toString().charAt(2))))) == 0)
        //if(p.getAttribute(this.color + "_" + Squares.TIME) < 2) 
            return dummyAction(p);
        if (recursion > 1)
            return limitBreakAction(p);

        int size = Integer.parseInt((String) p.getAttribute(Squares.SIZE));
        int i = -1;
        int j = -1;
        Vector<String> v = new Vector();

        for (int counter = 0; counter < size && v.size() == 0; counter++) {
            i++;
            for (int counter2 = 0; counter2 < size && v.size() == 0; counter2++) {
                j++;

                boolean continue_flag = false;

                for (Point banned1 : banned)
                    if (banned1.x == j % size && banned1.y == i % size) {
                        continue_flag = true;
                        break;
                    }

                if (continue_flag)
                    continue;

                if (((String) p.getAttribute(i % size + ":" + j % size + ":" + Squares.LEFT)).equals(Squares.FALSE))
                    v.add(Squares.LEFT);
                if (((String) p.getAttribute(i % size + ":" + j % size + ":" + Squares.TOP)).equals(Squares.FALSE))
                    v.add(Squares.TOP);
                if (((String) p.getAttribute(i % size + ":" + j % size + ":" + Squares.BOTTOM)).equals(Squares.FALSE))
                    v.add(Squares.BOTTOM);
                if (((String) p.getAttribute(i % size + ":" + j % size + ":" + Squares.RIGHT)).equals(Squares.FALSE))
                    v.add(Squares.RIGHT);
            }
        }

        //System.out.println("Point: " + i % size + ", " + j % size + ", Pared: " + v.get(0));
        if (v.isEmpty())
            return testAction(p, getPossiblesLess(p).first, recursion + 1);

        return new Action(i % size + ":" + j % size + ":" + v.get((int) (Math.random() * v.size())));
    }

    Action randomAction(Percept p) {

        int size = Integer.parseInt((String) p.getAttribute(Squares.SIZE));
        int i = -1;
        int j = -1;
        Vector<String> v = new Vector();

        for (int counter = 0; counter < size && v.size() == 0; counter++) {
            i++;
            for (int counter2 = 0; counter2 < size && v.size() == 0; counter2++) {
                j++;

                if (((String) p.getAttribute(i % size + ":" + j % size + ":" + Squares.LEFT)).equals(Squares.FALSE))
                    v.add(Squares.LEFT);
                if (((String) p.getAttribute(i % size + ":" + j % size + ":" + Squares.TOP)).equals(Squares.FALSE))
                    v.add(Squares.TOP);
                if (((String) p.getAttribute(i % size + ":" + j % size + ":" + Squares.BOTTOM)).equals(Squares.FALSE))
                    v.add(Squares.BOTTOM);
                if (((String) p.getAttribute(i % size + ":" + j % size + ":" + Squares.RIGHT)).equals(Squares.FALSE))
                    v.add(Squares.RIGHT);
            }
        }

        System.out.println("Point: " + i % size + ", " + j % size + ", Pared: " + v.get(0));

        return new Action(i % size + ":" + j % size + ":" + v.get((int) (Math.random() * v.size())));
    }

    String getDirLine(Percept p, int y, int x) {

        if (((String) p.getAttribute(x + ":" + y + ":" + Squares.LEFT)).equals(Squares.FALSE))
            return (Squares.LEFT);
        if (((String) p.getAttribute(x + ":" + y + ":" + Squares.TOP)).equals(Squares.FALSE))
            return (Squares.TOP);
        if (((String) p.getAttribute(x + ":" + y + ":" + Squares.BOTTOM)).equals(Squares.FALSE))
            return (Squares.BOTTOM);
        if (((String) p.getAttribute(x + ":" + y + ":" + Squares.RIGHT)).equals(Squares.FALSE))
            return (Squares.RIGHT);

        return Squares.PASS;
    }

    String[] getBannedN(Percept p, int y, int x) {

        String[] banned = new String[]{"", ""};

        int index = 0;

        if (((String) p.getAttribute(x + ":" + y + ":" + Squares.LEFT)).equals(Squares.FALSE))
            banned[index++] = (Squares.LEFT);
        if (((String) p.getAttribute(x + ":" + y + ":" + Squares.TOP)).equals(Squares.FALSE))
            banned[index++] = (Squares.TOP);
        if (((String) p.getAttribute(x + ":" + y + ":" + Squares.BOTTOM)).equals(Squares.FALSE))
            banned[index++] = (Squares.BOTTOM);
        if (((String) p.getAttribute(x + ":" + y + ":" + Squares.RIGHT)).equals(Squares.FALSE))
            banned[index++] = (Squares.RIGHT);

        return banned;
    }

    @Override
    public Action compute(Percept p) {

        //printBoard();
        if (memory != null)
            updateBoard(p);

        if (p.getAttribute(Squares.TURN).equals(color)) {

            if (this.memory == null)
                fillBoard(p);

            Tuple<ArrayList<Point>, Point> temp = getPossibles(p);

            if (temp.second != null)
                return new Action(temp.second.x + ":" + temp.second.y + ":" + getDirLine(p, temp.second.x, temp.second.y));

            return testAction(p, temp.first, 0);
        }

        return new Action(Squares.PASS);
    }

    @Override
    public void init() {
    }
}
