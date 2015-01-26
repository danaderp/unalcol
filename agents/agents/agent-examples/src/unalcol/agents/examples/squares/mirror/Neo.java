/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unalcol.agents.examples.squares.mirror;

import java.util.ArrayList;
import unalcol.agents.Action;
import unalcol.agents.AgentProgram;
import unalcol.agents.Percept;
import unalcol.agents.examples.squares.Squares;
import unalcol.types.collection.vector.Vector;

/**
 *
 * @author Camilo
 */
public class Neo implements AgentProgram {

    protected String color;
    private boolean[] mapa;
    private boolean[] mapaAnterior;

    //si es el primero en jugar
    private boolean fTurn;
    private int cFirst;

    //almacenan i, j, pos en que varian los estados tras una jugada
    private int i;
    private int j;
    private String place;
    
    private ArrayList<Last> dif;

    public Neo(String color) {
        this.color = color;

        this.i = 0;
        this.j = 0;
        this.cFirst = 0;

        this.mapaAnterior = new boolean[4 * 8 * 4];
        if (color.equals("white")) {
            fTurn = true;
            mapaAnterior[48] = true;
        }
        //NOTA: ADAPTAR ESTOS VALORES POR SI CAMBIA SIZE!
        //4 fijo (posiciones) * size * size /2
        this.mapa = new boolean[4 * 8 * 4];

        this.mapaAnterior[0] = true;
        this.mapaAnterior[4] = true;
        this.mapaAnterior[8] = true;
        this.mapaAnterior[12] = true;

        this.mapaAnterior[3] = true;
        this.mapaAnterior[35] = true;
        this.mapaAnterior[67] = true;
        this.mapaAnterior[99] = true;

        this.mapaAnterior[114] = true;
        this.mapaAnterior[118] = true;
        this.mapaAnterior[122] = true;
        this.mapaAnterior[126] = true;

        this.mapaAnterior[29] = true;
        this.mapaAnterior[61] = true;
        this.mapaAnterior[93] = true;
        this.mapaAnterior[125] = true;

        dif = new ArrayList<>();

    }

    @Override
    public Action compute(Percept p) {

        int size = Integer.parseInt((String) p.getAttribute(Squares.SIZE));
        if (p.getAttribute(Squares.TURN).equals(color)) {
            //si es el primer turno
            if (fTurn && cFirst == 0){ 
                cFirst = 1;
                //depends on size
                return new Action(3 + ":" + 3 + ":" + Squares.RIGHT);
            }
            //actualiza el tablero y deja el estado anterior
            //mapaAnterior = mapa;
            actualizarMapa(p, size);
            //halla la jugada diferente entre estados y la obtiene
            /*imprimir(mapa);
             System.out.println();
             imprimir(mapaAnterior);
             System.out.println();*/
            comparar(p);
            mapaAnterior = mapa;
            //System.out.println("i: " + i + " j: " + j + " place: " + place);
            //revisa la posicion obtenida y modifica los valores, los cuales seran usados en la accion
            revisar(i, j, place, p);//ERROR THERE

            //ultima revision para saber si se puede jugar ahi
            // System.out.println("i: " + i + " j: " + j + " place: " + place);
            if (this.place == null) {
                return def(p);
            }
            if (((String) p.getAttribute(i + ":" + j + ":" + place)).equals(Squares.FALSE)) {
                //Actualizando los mapas segun la jugada
                if (place.equals(Squares.TOP)) {
                    mapa[i * 16 + 2 * (j - (i % 2))] = true;
                }
                if (place.equals(Squares.RIGHT)) {
                    mapa[i * 16 + 2 * (j - (i % 2)) + 1] = true;
                }
                if (place.equals(Squares.BOTTOM)) {
                    mapa[i * 16 + 2 * (j - (i % 2)) + 2] = true;
                }
                if (place.equals(Squares.LEFT)) {
                    mapa[i * 16 + 2 * (j - (i % 2)) + 3] = true;
                }
                // System.out.println("jugada en " + i + " " + j);
                return new Action(i + ":" + j + ":" + place);
            } else {
                // System.out.println("default");
                return def(p);
            }
        }
        return new Action(Squares.PASS);

    }

    @Override
    public void init() {

    }
    /**
     * imprime el mapa con el estado del tablero
     *
     * @param mapa
     */
    private void imprimir(boolean[] mapa) {
        //depends on size
        for (int i = 0; i < 16 * 8; i++) {
            if (i % 16 == 0) {
                System.out.println();
            }
            if (i % 4 == 0) {
                System.out.print(" ");
            }
            if (mapa[i]) {//falso implica que no se ha jugado ahi, verdadero que si
                System.out.print("-");
                //System.out.println(i+" ");
            } else {
                System.out.print(".");
            }
        }
    }

    private void actualizarMapa(Percept p, int size) {
        for (int i = 0; i < size; i++) {
            //depends on size
            for (int j = 0; j < size; j++) {
                if (i % 2 == j % 2) {
                    if (i % 2 == 0) {
                        //de i,j a mapa
                        //+ 2 * (j - i%2)
                        mapa[i * 16 + 2 * j] = ((String) p.getAttribute(i + ":" + j + ":" + Squares.TOP)).equals(Squares.TRUE);
                        mapa[i * 16 + 2 * j + 1] = ((String) p.getAttribute(i + ":" + j + ":" + Squares.RIGHT)).equals(Squares.TRUE);
                        mapa[i * 16 + 2 * j + 2] = ((String) p.getAttribute(i + ":" + j + ":" + Squares.BOTTOM)).equals(Squares.TRUE);
                        mapa[i * 16 + 2 * j + 3] = ((String) p.getAttribute(i + ":" + j + ":" + Squares.LEFT)).equals(Squares.TRUE);
                    } else if (i % 2 == 1) {
                        mapa[i * 16 + 2 * (j - 1)] = ((String) p.getAttribute(i + ":" + j + ":" + Squares.TOP)).equals(Squares.TRUE);
                        mapa[i * 16 + 2 * (j - 1) + 1] = ((String) p.getAttribute(i + ":" + j + ":" + Squares.RIGHT)).equals(Squares.TRUE);
                        mapa[i * 16 + 2 * (j - 1) + 2] = ((String) p.getAttribute(i + ":" + j + ":" + Squares.BOTTOM)).equals(Squares.TRUE);
                        mapa[i * 16 + 2 * (j - 1) + 3] = ((String) p.getAttribute(i + ":" + j + ":" + Squares.LEFT)).equals(Squares.TRUE);
                    }
                }
            }
        }
    }

    /**
     * revisa el tablero y obtiene el valor diferente
     *
     * @param p
     */
    private void comparar(Percept p) {
        //System.out.println("comparando");
        int contDif = 0;
        //depends on size
        for (int i = 0; i < 4 * 8 * 4; i++) {
            if (mapa[i] != mapaAnterior[i]) {
                contDif++;
                //mapa a i,j,pos

                this.i = i / 16;

                if (i % 4 == 0) {
                    this.j = (i - 16 * this.i) / 2 + this.i % 2;
                    this.place = Squares.TOP;
                }
                if (i % 4 == 1) {
                    this.j = (i - 1 - 16 * this.i) / 2 + this.i % 2;
                    this.place = Squares.RIGHT;
                }
                if (i % 4 == 2) {
                    this.j = (i - 2 - 16 * this.i) / 2 + this.i % 2;
                    this.place = Squares.BOTTOM;
                }
                if (i % 4 == 3) {
                    this.j = (i - 3 - 16 * this.i) / 2 + this.i % 2;
                    this.place = Squares.LEFT;
                }
                //System.out.println("cont " + contDif);
                //System.out.println("place " + this.place);
                dif.add(new Last(this.i, this.j, this.place));
            }
        }
        //en caso de que haya mas de uno
        if (contDif > 1) {
            for (Last l : this.dif) {
                if (!(((String) p.getAttribute(l.getI() + ":" + l.getJ() + ":" + Squares.TOP)).equals(Squares.TRUE) && ((String) p.getAttribute(l.getI() + ":" + l.getJ() + ":" + Squares.RIGHT)).equals(Squares.TRUE) && ((String) p.getAttribute(l.getI() + ":" + l.getJ() + ":" + Squares.BOTTOM)).equals(Squares.TRUE) && ((String) p.getAttribute(l.getI() + ":" + l.getJ() + ":" + Squares.LEFT)).equals(Squares.TRUE))) {
                    this.i = l.getI();
                    this.j = l.getJ();
                    this.place = l.getPlace();
                }
            }
        }

    }

    /**
     * a partir de la jugada previa, cambia los valores de i, j y place para
     * indicar donde sera la proxima jugada
     *
     * @param i
     * @param j
     * @param place
     * @param p
     */
    private void revisar(int i, int j, String place, Percept p) {

        if (j == 3 && place.equals(Squares.RIGHT) && ((i != 3 && color.equals("white")) || (!color.equals("white")))) {
            //depends on size, i = size -1
            int aux = 0;
            int aux1 = 0;
            boolean flag = true;
            int col = 0;
            while (flag) {
                if (((String) p.getAttribute(col + ":" + j + ":" + Squares.LEFT)).equals(Squares.TRUE)) {
                    aux++;
                }
                if (((String) p.getAttribute(col + ":" + j + ":" + Squares.TOP)).equals(Squares.TRUE)) {
                    aux++;
                }
                if (((String) p.getAttribute(col + ":" + j + ":" + Squares.BOTTOM)).equals(Squares.TRUE)) {
                    aux++;
                }
                if (((String) p.getAttribute(col + ":" + (j + 1) + ":" + Squares.RIGHT)).equals(Squares.TRUE)) {//ERROR HERE
                    aux1++;
                }
                if (((String) p.getAttribute(col + ":" + (j + 1) + ":" + Squares.TOP)).equals(Squares.TRUE)) {
                    aux1++;
                }
                if (((String) p.getAttribute(col + ":" + (j + 1) + ":" + Squares.BOTTOM)).equals(Squares.TRUE)) {
                    aux1++;
                }
                if (aux < 2 && aux1 < 2) {
                    //   System.out.println("i raro "+this.i);
                    this.i = col;
                    flag = false;
                }
                col++;

                if (col > 7) {
                    flag = false;
                }
            }
            
        } else { //caso normal
            if (this.place.equals(Squares.LEFT)) {
                this.place = Squares.RIGHT;

            } else if (this.place.equals(Squares.RIGHT)) {
                this.place = Squares.LEFT;
            }
            //depends on size
            this.j = -j + 7;
        }

    }

    private Action def(Percept p) {
        int size = Integer.parseInt((String) p.getAttribute(Squares.SIZE));

        int i = 0;
        int j = 0;
        Vector<String> v = new Vector();
        while (v.size() == 0) {
            i = (int) (size * Math.random());
            j = (int) (size * Math.random());
            
            if (((String) p.getAttribute(i + ":" + j + ":" + Squares.LEFT)).equals(Squares.FALSE)) {
                v.add(Squares.LEFT);
            }
            if (((String) p.getAttribute(i + ":" + j + ":" + Squares.TOP)).equals(Squares.FALSE)) {
                v.add(Squares.TOP);
            }
            if (((String) p.getAttribute(i + ":" + j + ":" + Squares.BOTTOM)).equals(Squares.FALSE)) {
                v.add(Squares.BOTTOM);
            }
            if (((String) p.getAttribute(i + ":" + j + ":" + Squares.RIGHT)).equals(Squares.FALSE)) {
                v.add(Squares.RIGHT);
            }
        }

        int aux = (int) (Math.random() * v.size());

        //se actualiza el mapa 
        if (v.get(aux).equals(Squares.TOP)) {
            mapa[i * 16 + 2 * (j - (i % 2))] = true;
        }
        if (v.get(aux).equals(Squares.RIGHT)) {
            mapa[i * 16 + 2 * (j - (i % 2)) + 1] = true;
        }
        if (v.get(aux).equals(Squares.BOTTOM)) {
            mapa[i * 16 + 2 * (j - (i % 2)) + 2] = true;
        }
        if (v.get(aux).equals(Squares.LEFT)) {
            mapa[i * 16 + 2 * (j - (i % 2)) + 3] = true;
        }
        //  System.out.println("jugada en " + i + " " + j);

        return new Action(i + ":" + j + ":" + v.get(aux));
    }

}
