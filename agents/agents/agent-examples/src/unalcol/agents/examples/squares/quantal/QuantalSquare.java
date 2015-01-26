/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unalcol.agents.examples.squares.quantal;

import java.util.ArrayList;
import unalcol.agents.Action;
import unalcol.agents.AgentProgram;
import unalcol.agents.Percept;
import static unalcol.agents.examples.squares.quantal.Board.*;

/**
 *
 * @author Cesar Galvis
 */
public class QuantalSquare implements AgentProgram {

    protected String color;
    protected int size;

    public QuantalSquare(String color) {
        this.color = color;
    }

    @Override
    public Action compute(Percept p) {
        long time = (long) (200 * Math.random());
        try {
            Thread.sleep(time);
        } catch (Exception e) {
        }
        if (p.getAttribute(Squares.TURN).equals(color)) {
            size = Integer.parseInt((String) p.getAttribute(Squares.SIZE));

//            System.out.println("IT");
            //esto deberia retornar la accion, corregir mas adelante
            return decision(p);
        }
        return new Action(Squares.PASS);
    }

    @Override
    public void init() {
    }

    //tomar una decision de una jugada teniendo en cuenta el algoritmo minimax
    public Action decision(Percept p) {
        Board tablero = crearTablero(p);

        //Raíz del árbol
        NodoG arbol = new NodoG(size);

        //Copiamos el tablero a la raiz
        arbol.tablero = clonarTablero(tablero);

        //verificamos si somos white o no
        boolean white = false;
        if (color.equals(Squares.WHITE)) {
            white = true;
        }
        
        //se calcula la mejor jugada con el algoritmo minimax
        while(arbol.mejorDireccion==0){
//            if(size != Integer.parseInt((String) p.getAttribute(Squares.SIZE)))
//                size = Integer.parseInt((String) p.getAttribute(Squares.SIZE));
//            movMiniMax(arbol, white);
            
//            System.out.println(alfaBeta(arbol, white, - (int) Double.POSITIVE_INFINITY, (int) Double.POSITIVE_INFINITY));
            alfaBeta(arbol, white, - (int) Double.POSITIVE_INFINITY, (int) Double.POSITIVE_INFINITY);
        }
        
        String result="";
        switch (arbol.mejorDireccion) {
            case 1:
            result="left";
            break;
            case 2:
            result="top";
            break;
            case 4:
            result="right";
            break;
            case 8:
            result="bottom";
            break;
        }
        System.out.println("color: "+color+" movimiento: "+arbol.mejorMovimientoI + ":" + arbol.mejorMovimientoJ + ":" + result);
//        impVal(arbol.tablero.values);
        return new Action(arbol.mejorMovimientoI + ":" + arbol.mejorMovimientoJ + ":" + result);
    }

    /* Metodo recursivo, que genera los nodos con los movimientos. */
//    public void movMiniMax(NodoG raiz, boolean white) {
//
//        //se verifica si hay un ganador
//        int ganador = resultado(raiz.tablero, white);
//
//        //se guardan las posibles jugadas que se pueden realizar con el tablero
//        //de la raiz
//        raiz.nodos = buscarHijos(white, raiz.tablero);
//        
//        if (ganador != 0 || raiz.nodos.length == 0) {
//            raiz.ganador = ganador;
//        } else {
//
//            //Creamos los datos de cada hijo
//            for (int i = 0; i < raiz.nodos.length; i++) {
//                //Cambiamos el turno de los hijos
//                raiz.nodos[i].miTurno = !raiz.miTurno;
//                
//                movMiniMax(raiz.nodos[i], !white);
//            }
//
//            // Minimax
//            if (!raiz.miTurno) {
//                raiz.ganador = Max(raiz);
//            } else {
//                raiz.ganador = Min(raiz);
//            }
//        }
//    }
    
    public int alfaBeta(NodoG raiz, boolean white, int alfa, int beta){
        
        //se verifica si hay un ganador
        int ganador = resultado(raiz.tablero,white,raiz.miTurno);
        
        if (ganador != 0) {
//            impVal(raiz.tablero.values);
//            System.out.println("ganador: "+ganador+", mi turno:"+raiz.miTurno); 
            raiz.ganador = ganador;
            return ganador;
        } else {
            
            //se guardan las posibles jugadas que se pueden realizar con el tablero
            //de la raiz
            raiz.nodos = buscarHijos(white, raiz.tablero);
            
            if (raiz.miTurno) {
                for (int i = 0; i < raiz.nodos.length; i++) {
                    raiz.nodos[i].miTurno = !raiz.miTurno;
                    alfa = Math.max(alfa, alfaBeta(raiz.nodos[i], !white, alfa, beta));
                    if(beta<=alfa)
                        break;
                }
//                raiz.ganador = alfa;
                raiz.ganador = Max(raiz);
                return alfa;
            } else {
                for (int i = 0; i < raiz.nodos.length; i++) {
                    raiz.nodos[i].miTurno = !raiz.miTurno;
                    beta = Math.min(alfa, alfaBeta(raiz.nodos[i], !white, alfa, beta));
                    if(beta<=alfa)
                        break;
                }
//                raiz.ganador = beta;
                raiz.ganador = Min(raiz);
                return beta;
            }
        }
    }

    //crea un tablero en base a las percepciones actuales del agente
    public Board crearTablero(Percept p) {
        Board tablero = new Board(size);

        for (int a = 0; a < size; a++) {
            for (int b = 0; b < size; b++) {
                if (((String) p.getAttribute(a + ":" + b + ":" + Squares.COLOR)).equals(Squares.SPACE)) {
                    if (((String) p.getAttribute(a + ":" + b + ":" + Squares.LEFT)).equals(Squares.TRUE)) {
                        tablero.values[a][b] |= Board.LEFT;
                    }
                    if (((String) p.getAttribute(a + ":" + b + ":" + Squares.TOP)).equals(Squares.TRUE)) {
                        tablero.values[a][b] |= Board.TOP;
                    }
                    if (((String) p.getAttribute(a + ":" + b + ":" + Squares.RIGHT)).equals(Squares.TRUE)) {
                        tablero.values[a][b] |= Board.RIGHT;
                    }
                    if (((String) p.getAttribute(a + ":" + b + ":" + Squares.BOTTOM)).equals(Squares.TRUE)) {
                        tablero.values[a][b] |= Board.BOTTOM;
                    }
                } else {
                    if (((String) p.getAttribute(a + ":" + b + ":" + Squares.WHITE)).equals(Squares.WHITE)) {
                        tablero.values[a][b] = Board.WHITE;
                    } else {
                        tablero.values[a][b] = 15;
                    }
                }
            }
        }
        return tablero;
    }

    //retorna la copia de un tablero ingresado como parametro
    public Board clonarTablero(Board tabl) {
        Board tablero = new Board(size);

        for (int a = 0; a < size; a++) {
            for (int b = 0; b < size; b++) {
                tablero.values[a][b] = tabl.values[a][b];
            }
        }
        return tablero;
    }

    //cuenta los cuadros blancos del tablero (el del profe parece que no funciona)
    public int white_count(Board tablero) {
        int count = 0;
        for (int a = 0; a < size; a++) {
            for (int b = 0; b < size; b++) {
                if (tablero.values[a][b] == Board.WHITE) {
                    count++;
                }
            }
        }
        return count;
    }

    //busca posibles jugadas en un estado (tablero) 
    //HAY QUE MEJORAR ESTO!
    public NodoG[] buscarHijos(boolean white, Board tablero) {
        ArrayList<Board> listaTableros = new ArrayList<>();
        ArrayList<Integer> ies = new ArrayList<>();
        ArrayList<Integer> js = new ArrayList<>();
        ArrayList<Integer> mov = new ArrayList<>();

        for (int a = 0; a < size; a++) {
            for (int b = 0; b < size; b++) {
                if (tablero.values[a][b] < 15) {
                    if ((tablero.values[a][b] & Board.LEFT) != Board.LEFT) {
                        proceso(tablero, white, listaTableros, ies, js, mov, Board.LEFT, a, b, size);
                    }
                    if ((tablero.values[a][b] & Board.TOP) != Board.TOP) {
                        proceso(tablero, white, listaTableros, ies, js, mov, Board.TOP, a, b, size);
                    }
                    if ((tablero.values[a][b] & Board.RIGHT) != Board.RIGHT) {
                        proceso(tablero, white, listaTableros, ies, js, mov, Board.RIGHT, a, b, size);
                    }
                    if ((tablero.values[a][b] & Board.BOTTOM) != Board.BOTTOM) {
                        proceso(tablero, white, listaTableros, ies, js, mov, Board.BOTTOM, a, b, size);
                    }
                }
            }
        }
//        parche(tablero);
//        return listaTableros;
        NodoG[] nodos = new NodoG[listaTableros.size()];
        if (nodos.length > 0) {
//            System.out.println("*");
            for (int a = 0; a < nodos.length; a++) {
                //tal vez haya que clonar!
                nodos[a] = new NodoG(size);
                nodos[a].tablero = listaTableros.get(a);
//                impVal(listaTableros.get(a).values);
                nodos[a].i = ies.get(a);
                nodos[a].j = js.get(a);
                nodos[a].direccion = mov.get(a);
            }
        }
        return nodos;
    }

    //verifica si se puede hacer una jugada falsa y verifica si no hay jugadas iguales
    public void proceso(Board tablero, boolean white, ArrayList<Board> listaTableros, ArrayList<Integer> ies, ArrayList<Integer> js,ArrayList<Integer> mov, int direccion, int fil, int col, int size) {
        Board tablTemp = clonarTablero(tablero);
//        System.out.println(white+"-"+ fil+"-"+  col+"-"+  direccion);
        tablTemp.play(white, fil, col, direccion);
//        if(!white) parche(tablTemp);
        parche(tablTemp);
        if (!duplicado(listaTableros, tablTemp.values)) {
            listaTableros.add(tablTemp);
            ies.add(fil);
            js.add(col);
            mov.add(direccion);
        }
    }

    //imprime una matriz de valores
    public static void impVal(int[][] val) {
        for (int[] val1 : val) {
            for (int b = 0; b < val.length; b++) {
                System.out.print(val1[b] + " ");
            }
            System.out.println();
        }
        System.out.println("------------------");
    }

    //verifica si un tablero no esta repetido en un arraylist
    public boolean duplicado(ArrayList<Board> listaTableros, int[][] values2) {
        int cont = 0;
        for (Board tabl : listaTableros) {
            for (int a = 0; a < values2.length; a++) {
                for (int b = 0; b < values2.length; b++) {
                    if (tabl.values[a][b] == values2[a][b]) {
                        cont++;
                    }
                }
            }
            if (cont == Math.pow(values2.length, 2)) {
                return true;
            }
            cont = 0;
        }
        return false;
    }

    
    //no es un buen metodo de programación pero es para ver si funciona
    public void parche(Board tablero){
        int x=tablero.values.length;
        for (int a = 0; a < x; a++) {
            for (int b = 0; b < x; b++) {
                if (tablero.values[a][b]==16)
                    tablero.values[a][b]=31;
            }
        }
    }
    //verifica que el tablero este lleno (el del profe parece no funcionar por 
    //no leer los cuadros blancos)
//    public boolean full(Board tablero) {
//        return white_count(tablero) + tablero.black_count() == Math.pow(size, 2);
//    }

    //retorna 0 si no se ha completado el juego y el numero de casillas obtenidas
    //por el jugador activo
    public int resultado(Board tablero,boolean white, boolean miTurno) {
        if (!tablero.full()) {
            return 0;
        }
//        if (!full(tablero)) {
//            return 0;
//        }
        if (white) {
//            return white_count(tablero);
            int nWhite =tablero.white_count();
            if(miTurno)
                return nWhite;
            return -nWhite;
        }
        int nBlack = tablero.black_count();
        if(miTurno)
            return nBlack;
        return -nBlack;
//        if (color.equals(Squares.WHITE)) {
////            return white_count(tablero);
//            return tablero.white_count();
//        }
//        return tablero.black_count();
    }
    /*Método que calcula el MÁXIMO de los nodos hijos de MIN*/

    public int Max(NodoG raiz) {
        int Max = -(int) Double.POSITIVE_INFINITY;
        /*Máximo para la computadora, buscamos el valor donde gane.*/
        for (int i = 0; i < raiz.nodos.length; i++) {
            /*Preguntamos por un nodo con un valor alto MAX*/
            if (raiz.nodos[i].ganador > Max && raiz.nodos[i].ganador!=0) {
                /*Lo asignamos y pasamos el mejor movimiento a la raíz.*/
                Max = raiz.nodos[i].ganador;
                raiz.mejorMovimientoI = raiz.nodos[i].i;
                raiz.mejorMovimientoJ = raiz.nodos[i].j;
                raiz.mejorDireccion = raiz.nodos[i].direccion;
                /*Terminamos de buscar.*/
//                if (Max == 1) {
//                    break;
//                }
            }
        }
        /*Borramos los nodos.*/
        raiz.nodos = null;
        return Max;
    }
    /*Método que calcula el MÍNIMO de los nodos hijos de MAX.*/

    public int Min(NodoG raiz) {
        int Min = (int) Double.POSITIVE_INFINITY;
        /*Mínimo para el jugador*/
        for (int i = 0; i < raiz.nodos.length; i++) {
            if (raiz.nodos[i].ganador < Min && raiz.nodos[i].ganador!=0) {
                Min = raiz.nodos[i].ganador;
                raiz.mejorMovimientoI = raiz.nodos[i].i;
                raiz.mejorMovimientoJ = raiz.nodos[i].j;
                raiz.mejorDireccion = raiz.nodos[i].direccion;
//                if (Min == -1) {
//                    break;
//                }
            }
        }
        /*Borramos los nodos.*/
        raiz.nodos = null;
        return Min;
        
    }
    
    public static void main( String[] args ){
        Board b = new Board(3);
        impVal(b.values);
        System.out.println("white: "+b.white_count()+" black: "+b.black_count());
        System.out.println("************************************");
        b.play(true,0, 0, RIGHT);
        impVal(b.values);
        System.out.println("white: "+b.white_count()+" black: "+b.black_count());
        System.out.println("************************************");
        b.play(false,0, 1, RIGHT);
        impVal(b.values);
        System.out.println("white: "+b.white_count()+" black: "+b.black_count());
        System.out.println("************************************");
        b.play(true,1, 0, RIGHT);
        impVal(b.values);
        System.out.println("white: "+b.white_count()+" black: "+b.black_count());
        System.out.println("************************************");
        b.play(false,1, 1, RIGHT);
        impVal(b.values);
        System.out.println("white: "+b.white_count()+" black: "+b.black_count());
    }
    
    class NodoG {

        int mejorMovimientoI;
        int mejorMovimientoJ;
        int mejorDireccion;
        /*Nodos hijos.*/
        NodoG nodos[];
        /*Tablero del juego.*/
        public Board tablero;
        /*Turno de la computadora.*/
        boolean miTurno = true;
        /*Indices del movimiento.*/
        int i;
        int j;
        int direccion;
        /*Ganador.*/
        int ganador = 0;

        NodoG(int size) {
            tablero = new Board(size);
        }
    }
}
