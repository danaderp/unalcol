/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package unalcol.agents.examples.squares.jota;

/**
 *
 * @author PC
 */

//Solo esta la raiz, debo crear todos los hijos y sucesivamente formar el arbol, calcular las ganancias de cada jugada para la totalidad de las jugadas

public class MemoriaSquaresInt {
    public int ganancia;
    public static int tamanoTablero;
    public static int inf;
    public int memoria [];   //representa el estado en el que se encuentra, por eso cada nodo tiene su propia memoria
    public MemoriaSquaresInt hijo[];  //para tener referencias de tamaño variable en los hijos y solo inicializar los que necesite
    
    public MemoriaSquaresInt () {  //para crear los hijos debo enviarles el estado y a traves de maximax y minimax
        
    }
    public MemoriaSquaresInt (int t) {
        tamanoTablero = t;
        inf = tamanoTablero*tamanoTablero;  //para representar el numero infinito tomo el total de las posiciones con las que se juega
        int numHijos = 2*(t-1)*t;  //este es el numero maximo de movimientos, este se da en la primera iteracion.
        hijo = new MemoriaSquaresInt [numHijos];
//        hijo[0]= new MemoriaSquaresInt();
//        hijo[numHijos]= new MemoriaSquaresInt();   //error de adrede para que falle y no se ejecute la aplicacion.
        
        
        inicializoMemoria();  //creo un vector que representa el problema
        
        imprimir();
        ganancia = minimaxDecision (0,tamanoTablero,memoria);  //retorno un numero entero que representa la accion a tomar despues de crecer el arbol
        System.out.println("Ganacia nodo raiz:"+ganancia);
        
    }
    
    public void cambioTamano (int t) {
        tamanoTablero = t;
        memoria = new int [t*t];  //para inicializar el vector con el total de las posiciones
        for (int j = 0; j<t; j++) {
            for (int i = 0; i<t; i++) {   //Coloco las lineas horizontales
                if ((i==0 && j==0) || (i==0 && j==(t-1))|| (i==(t-1) && j==0) || (i==(t-1) && j==(t-1)))    memoria [i+(j*t)]=2;
                else  if ((i==0) || j==(t-1)|| i==(t-1)|| j==0)     memoria [i+(j*t)]=1;
                       else memoria [i+(j*t)]=0;

                }
            }
        
    }
    
    public void inicializoMemoria() {
        if (memoria==null) {
            memoria = new int [tamanoTablero*tamanoTablero];  //para inicializar el vector con el total de las posiciones
            for (int j = 0; j<tamanoTablero; j++) {
                for (int i = 0; i<tamanoTablero; i++) {   //Coloco las lineas horizontales
                    if ((i==0 && j==0) || (i==0 && j==(tamanoTablero-1))|| (i==(tamanoTablero-1) && j==0) || (i==(tamanoTablero-1) && j==(tamanoTablero-1)))    memoria [i+(j*tamanoTablero)]=2;
                    else  if ((i==0) || j==(tamanoTablero-1)|| i==(tamanoTablero-1)|| j==0)     memoria [i+(j*tamanoTablero)]=1;
                           else memoria [i+(j*tamanoTablero)]=0;
                    }
                }
        }
    }
    
    public void imprimir () {
        System.out.print("Vector resultante: \n");
            for (int j = 0; j<tamanoTablero; j++) {
                for (int i = 0; i<tamanoTablero; i++) {   //Coloco las lineas horizontales
                    System.out.print(memoria [i+(j*tamanoTablero)]);
                    System.out.print(" ");
                }
                System.out.println("");
            }
    }
    
    public int maximax (int t, int sizeBoard, int stateBoard[]){ //esta es para el primer jugador - blancas o azules
        if (validaMovimientos (stateBoard )) {   //Estado de terminacion
            return 0;
        }
            
        for (int i = 0 ; i< (sizeBoard*(sizeBoard-1)*2); i++) {
            System.out.println("as");
        }
        System.out.println("Numero de la iteración: "+ t);
        return minimax(t+1, sizeBoard, stateBoard);
    }
    
    public int minimax(int t, int sizeBoard, int stateBoard[]){ //esta es para el segundo jugador - negras o rojas
        System.out.println("Entra desde maximax");
        return 15;
    }
    
    public int minimaxDecision (int t, int sizeBoard, int stateBoard[]){
        return 0;
    }
    
    public boolean validaMovimientos (int stateBoard[]) { // si aun quedan movimientos por realizarce entonces los ejecuta
        for (int j = 0; j<tamanoTablero; j++) {
                for (int i = 0; i<tamanoTablero; i++) {   //Coloco las lineas horizontales
                    System.out.print(memoria [i+(j*tamanoTablero)]);
                    System.out.print(" ");
                }
                System.out.println("");
            }
        return false;
    }
    
}
