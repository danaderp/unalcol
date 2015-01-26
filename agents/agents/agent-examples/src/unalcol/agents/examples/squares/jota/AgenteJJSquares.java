/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 * 
 * Programado por Jose Francisco Parra, 
 * Error al presionar simulate sin presionar init despues de terminar un juego
 * La ultima jugada es de white y es un dummy
 */
package unalcol.agents.examples.squares.jota;

import java.util.StringTokenizer;
import unalcol.agents.Action;
import unalcol.agents.Percept;
import unalcol.types.collection.vector.Vector;
import unalcol.agents.AgentProgram;
import unalcol.agents.examples.squares.Squares;

/**
 *
 * @author PC
 * Nota: el rojo es negro, azul es el blanco
 * 
 */
public class AgenteJJSquares implements AgentProgram {
    protected String color;
    protected static MemoriaSquaresBool mem;
    static boolean bandera;
    int contador = 0;
    
    public AgenteJJSquares( String color ){
        this.color = color;
        bandera = true;
        if (mem==null) mem = new MemoriaSquaresBool (); //inicializo el tamaño de la memoria con un valor cualquiera
    }
    
    @Override
    public Action compute(Percept p) {
        
        long time = (long)(200 * Math.random());
        try{
           Thread.sleep(time);
        }
        catch(Exception e){
        }
        
        if( p.getAttribute(Squares.TURN).equals(color) ){
            String sigPaso = "";  // puede tomar valores: left, top, bottom, y right
            int i = 0;
            int j = 0;
            int pos=0;
            //si cambio el tamaño del tablero vuelvo a hacer la memoria
            if (mem.tamanoTablero != Integer.parseInt((String)p.getAttribute(Squares.SIZE)))    {
                mem.cambioTamano(Integer.parseInt((String)p.getAttribute(Squares.SIZE)));
                bandera=true;
            }
            if (bandera) { //con un criterio pongo a funcionar los aleatorios antes de formar el árbol
                int size = Integer.parseInt((String)p.getAttribute(Squares.SIZE));
                Vector<String> v = new Vector();
                contador =0;    
                while(v.size()==0 && bandera){
                    contador++;
//                    System.out.println("contador "+contador);
                    if (contador>=10) { 
                        // la bandera se encarga que no vuelva a entrar en el aleatorio, es el mismo codigo de la linea 118
                        System.out.println("\n\n\nDeja de ser aleatorio.");
                        bandera=false;
                        if (p.getAttribute(Squares.TURN).equals("white")) { // en play entro a white
                            sigPaso=mem.maximinDecision2(mem.estado);
                        }
                        else {
                            sigPaso=mem.minimaxDecision2(mem.estado);
                        }
                        StringTokenizer cadena = new StringTokenizer(sigPaso);
                        sigPaso = cadena.nextToken();
                        i=Integer.parseInt(cadena.nextToken());
                        j=Integer.parseInt(cadena.nextToken());
                    }
                    else { //aqui se generan los numeros aleatorios que llenan los tableros
                        pos=0;
                        i = (int)(size*Math.random()); 
                        j = (int)(size*Math.random());
                            //Squares.LEFT retorna true si esta ocupado a la izquierda
                        if(((String)p.getAttribute(i+":"+j+":"+Squares.LEFT)).equals(Squares.FALSE)){
                            pos++;
                            if (validaAdyacente(p,i,j-1)) v.add(Squares.LEFT);
                        }  
                        if(((String)p.getAttribute(i+":"+j+":"+Squares.TOP)).equals(Squares.FALSE)){
                            pos++;
                            if (validaAdyacente(p,i-1,j)) v.add(Squares.TOP);
                        }
                        if(((String)p.getAttribute(i+":"+j+":"+Squares.BOTTOM)).equals(Squares.FALSE)){
                            pos++;
                            if (validaAdyacente(p,i+1,j)) v.add(Squares.BOTTOM);
                        }
                        if(((String)p.getAttribute(i+":"+j+":"+Squares.RIGHT)).equals(Squares.FALSE)){
                            pos++;
                            if (validaAdyacente(p,i,j+1)) v.add(Squares.RIGHT);
                        }

                        if (pos<=2) {
                            while (v.size()!=0) {v.remove(0);}
                        }
                        else{
                            sigPaso = v.get((int)(Math.random()*v.size()));
                            //transformo la jugada al formato almacenado, solo derecha y abajo
                            if (sigPaso == "left")  { j--; sigPaso="right";  }
                            if (sigPaso == "top")   { i--; sigPaso="bottom"; }
                        }
                    }
                }
            }
            else {  //aqui creo y/o actualizo la memoria y ya no se vuelve a jugar aleatorio
                if (p.getAttribute(Squares.TURN).equals("white")) {
                    sigPaso=mem.maximinDecision2(mem.estado);
                }
                else {
                    sigPaso=mem.minimaxDecision2(mem.estado);
                }
                StringTokenizer cadena = new StringTokenizer(sigPaso);
                sigPaso = cadena.nextToken();
                i=Integer.parseInt(cadena.nextToken());
                j=Integer.parseInt(cadena.nextToken());
                
//                mem.actualizaEstado(i,j,sigPaso);  //Alrededor
            }

//            mem.estado=
              mem.actualizaEstado(i,j,sigPaso,mem.estado);
            
//            System.out.println("\t\t\t Paso jugado: "+i+":"+j+":"+ sigPaso+" Turno Blancas: "+p.getAttribute(Squares.TURN).equals("white"));//black
//            mem.imprimir(mem.estado);
//            System.out.println("");
            
            //si se termina el juego borro la memoria
            if (!mem.quedanPasos(mem.estado) && p.getAttribute(Squares.TURN).equals("white"))    {
                mem.tamanoTablero=0;
            }
            
            return new Action( i+":"+j+":"+ sigPaso);  //aleatoriamente coge una de las libres para ese vector
            
        }
        //como la opcion anterior no era viable ahora debe jugar otra, o porque se le acabo el tiempo original
        //el "paso" es pq no sirve la pos i j  para jugar, ent busca otra
        return new Action(Squares.PASS);
    }
    private boolean validaAdyacente (Percept p, int i, int j){
//        System.out.println("i:"+i+", j:"+j);
        Vector<String> lista = new Vector();
        boolean retorna = false;
        if(((String)p.getAttribute(i+":"+j+":"+Squares.LEFT)).  equals(Squares.FALSE))    lista.add(Squares.LEFT);
        if(((String)p.getAttribute(i+":"+j+":"+Squares.TOP)).   equals(Squares.FALSE))    lista.add(Squares.TOP);
        if(((String)p.getAttribute(i+":"+j+":"+Squares.BOTTOM)).equals(Squares.FALSE))    lista.add(Squares.BOTTOM);
        if(((String)p.getAttribute(i+":"+j+":"+Squares.RIGHT)). equals(Squares.FALSE))    lista.add(Squares.RIGHT);

        if (lista.size()>=3) retorna = true;
        
        return retorna;
    }
    
    @Override
    public void init() {
    }
}