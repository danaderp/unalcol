/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package unalcol.agents.examples.squares.jota;

import unalcol.types.collection.vector.Vector;

/**
 *
 * @author PC
 */

//Solo esta la raiz, debo crear todos los hijos y sucesivamente formar el arbol, calcular las ganancias de cada jugada para la totalidad de las jugadas

//Los envios en los metodos con matrices los toma 

public class MemoriaSquaresBool {
    public int ganancia;
    public static int tamanoTablero;
    public static int inf;
    public static int limiteIteraciones = 5;
    public static boolean estado [];   //representa el estado en el que se encuentra, por eso cada nodo tiene su propia memoria
    public int jugadasRestantes;
    public MemoriaSquaresBool raiz;
    public MemoriaSquaresBool hijo[];  //para tener referencias de tamaño variable en los hijos y solo inicializar los que necesite
    
    public MemoriaSquaresBool () {  //para crear los hijos debo enviarles el estado y a traves de maximax y minimax
        tamanoTablero = 0;
    }
    public MemoriaSquaresBool (int t) {
      tamanoTablero = t;
      //System.out.println(" Entro con tamaño:"+t);
    }
    
    public void cambioTamano (int t) {
        tamanoTablero = t;
        inf = tamanoTablero*tamanoTablero;  //para representar el numero infinito tomo el total de las posiciones con las que se juega
        jugadasRestantes = 2*(tamanoTablero-1)*tamanoTablero; //numero máximo de lineas que se pueden hacer en ese tablero
        hijo = new MemoriaSquaresBool [jugadasRestantes]; //ademas cualquiera de esas jugadas que quedan se pueden hacer y son 
        //inicializoMemoria();  //creo un vector que representa el problema

//        hijo[0]= new MemoriaSquaresBool();
        
        estado = new boolean [2*t*(t-1)];  //para inicializar el vector con el total de las posiciones
        for (int i = 0; i<2*t*(t-1); i++) {
            estado[i]=false;
            }
//        imprimir();
//        System.out.println("Se transformo la matriz a: "+tamanoTablero);
    }
    
    public void inicializoMemoria() {  //tomo el total de los movimientos que se pueden realizar
        if (estado==null) {
            estado = new boolean [2*tamanoTablero*(tamanoTablero-1)];  //para inicializar el vector con el total de las posiciones
            for (int i = 0; i<2*tamanoTablero*(tamanoTablero-1); i++) {
                estado[i]=false;
            }
        }
    }
    
    public void imprimir (boolean state[]) {
//        System.out.print("Vector resultante: \n");
        int matriz[][] = new int [tamanoTablero][tamanoTablero];
        
        //inicializo los valores para un tablero de ese tamaño en una matriz
        for (int j = 0; j<tamanoTablero; j++) {
            for (int i = 0; i<tamanoTablero; i++) {   //Coloco las lineas horizontales
                if ((i==0 && j==0) || (i==0 && j==(tamanoTablero-1))|| (i==(tamanoTablero-1) && j==0) || (i==(tamanoTablero-1) && j==(tamanoTablero-1)))    matriz [i][j]=2;
                else  if ((i==0) || j==(tamanoTablero-1)|| i==(tamanoTablero-1)|| j==0)     matriz [i][j]=1;
                       else matriz [i][j]=0;;

                }
            }

//        dummy para verificar funcionalidad
//        estado[1]=estado[4]=estado[10]=true; // solo para probar que funciona
//        estado[1]=estado[5]=estado[13]=estado[17]=true; // solo para probar que funciona
        
//        incluyo los movimientos que se han realizado hasta el momento, con la codificacion creada
        for (int k=0; k<2; k++) { //k es 2 porque reviso horizontales y verticales
                for (int i = 0; i<tamanoTablero-1 ; i++) {   //Coloco las lineas horizontales
                    for (int j = 0; j<tamanoTablero; j++) {
                        if (k==0){  //esta en las filas 
                            if (state[(i*tamanoTablero)+j]){
                                matriz [i][j] += 1;
                                matriz [i+1][j] += 1;
                            }
                        }
                        if (k==1){  //esta en las columnas
                            if (state[(((tamanoTablero*(tamanoTablero-1))+(i*tamanoTablero))+j)]){
                                matriz [j][i] += 1;
                                matriz [j][i+1] += 1;
                            }
                        }
                    }
                }
            }
        
//         imprimo finalmente la matriz como va el juego con los movimientos dados
        for (int i = 0; i<tamanoTablero; i++) {
                for (int j = 0; j<tamanoTablero; j++) {   //Coloco las lineas horizontales
                    System.out.print(matriz [i][j]);
                    System.out.print(" ");
                }
                if (i!=tamanoTablero-1) System.out.println("");
                else System.out.println("Quedan por jugar: "+cuantosPasosQuedan(state)+" lineas.");
            }
    }
    
    public void duplicaInformacion(boolean state [], boolean temporal []) {
        for (int n = 0; n<2*tamanoTablero*(tamanoTablero-1); n++) {
            temporal[n]=state[n];
            }
    }
    //ya tengo el tamaño del tablero en tamanoTablero
    //el estado lo tienen los hijos, se envían los hijos
    public int maximin (int terminacion, boolean state [], int pos){ //esta es para el primer jugador - blancas o azules
        int maximo = inf*-1, retorno=0, jugada=0;
        int perdida=0;
        actualizaEstado(pos, state);                            //la jugada que llega la debo jugar antes de seguir ejecutando el codigo
        boolean copiaLlenarEstaIteracion [], copiaEnviarHijos [];
        
        if (terminacion<limiteIteraciones && quedanPasos(state)) {
            copiaLlenarEstaIteracion = new boolean [2*tamanoTablero*(tamanoTablero-1)];  //creo una copia del array
            duplicaInformacion(state, copiaLlenarEstaIteracion);                         //la lleno con los mismos elementos del original
            copiaEnviarHijos = new boolean [2*tamanoTablero*(tamanoTablero-1)];         //creo una copia del array
            duplicaInformacion(state, copiaEnviarHijos);                                //la lleno con los mismos elementos del original
            while (quedanPasos(copiaLlenarEstaIteracion)) { //aqui estoy jugando el primer maximax, por tanto abajo invocare un minimax
                pos=primeraJugadaFaltante(copiaLlenarEstaIteracion);
                perdida=getGanancia(copiaLlenarEstaIteracion);
//                System.out.print("Primera Jugada: "+pos+"\t");
                actualizaEstado(pos, copiaLlenarEstaIteracion);             //juego el primer paso nuevo que se puede jugar, los puntos serían del adversario
                perdida=getGanancia(copiaLlenarEstaIteracion)-perdida;    //si es 0 es porque la jugada no hizo ningún punto para el adversario
//                System.out.println("Ganancia por esa jugada: "+ganancia);

        //        ganancia=calculaGananciaJugadaDesdeMatriz(state, jugada);
                retorno=maximin(terminacion+1, copiaEnviarHijos, pos)-perdida;

                if (retorno>maximo) {
                    maximo = retorno;
                    jugada=pos;
                }
            }
        }
        return maximo; //retorna la utilidad por la jugada dada
    }

    public int minimax(int terminacion, boolean state [], int pos){ //esta es para el segundo jugador - negras o rojas
        int minimo = inf, retorno=0, jugada=0;
        int ganancia=0;
        actualizaEstado(pos, state);                            //la jugada que llega la debo jugar antes de seguir ejecutando el codigo
        boolean copiaLlenarEstaIteracion [], copiaEnviarHijos [];
        
        if (terminacion<limiteIteraciones && quedanPasos(state)) {
            copiaLlenarEstaIteracion = new boolean [2*tamanoTablero*(tamanoTablero-1)];  //creo una copia del array
            duplicaInformacion(state, copiaLlenarEstaIteracion);                         //la lleno con los mismos elementos del original
            copiaEnviarHijos = new boolean [2*tamanoTablero*(tamanoTablero-1)];         //creo una copia del array
            duplicaInformacion(state, copiaEnviarHijos);                                //la lleno con los mismos elementos del original
            while (quedanPasos(copiaLlenarEstaIteracion)) { //aqui estoy jugando el primer maximax, por tanto abajo invocare un minimax
                pos=primeraJugadaFaltante(copiaLlenarEstaIteracion);
                ganancia=getGanancia(copiaLlenarEstaIteracion);
//                System.out.print("Primera Jugada: "+pos+"\t");
                actualizaEstado(pos, copiaLlenarEstaIteracion);             //juego el primer paso nuevo que se puede jugar, los puntos serían del adversario
                ganancia=getGanancia(copiaLlenarEstaIteracion)-ganancia;    //si es 0 es porque la jugada no hizo ningún punto para el adversario
//                System.out.println("Ganancia por esa jugada: "+ganancia);

        //        ganancia=calculaGananciaJugadaDesdeMatriz(state, jugada);
                retorno=maximin(terminacion+1, copiaEnviarHijos, pos)+ganancia;

                if (retorno<minimo) {
                    minimo = retorno;
                    jugada=pos;
                }
            }
        }
        return minimo; //retorna la utilidad por la jugada dada
    }
    
    public String maximinDecision2 (boolean state []){  
//        System.out.println("Entra a maximax decision");
                int valor = inf; //*-1 para el infinito de maximax
        int retorno=0;
        int perdida=0;
        int pos=0;
        int jugada=0;
        boolean copiaLlenarEstaIteracion [], copiaEnviarHijos [];
        copiaLlenarEstaIteracion = new boolean [2*tamanoTablero*(tamanoTablero-1)];  //creo una copia del array
        duplicaInformacion(state, copiaLlenarEstaIteracion);                         //la lleno con los mismos elementos del original
        copiaEnviarHijos = new boolean [2*tamanoTablero*(tamanoTablero-1)];  //creo una copia del array
        duplicaInformacion(state, copiaEnviarHijos);                         //la lleno con los mismos elementos del original
        //cargo las jugadas que faltan
        //for (int k = 0; k<2*tamanoTablero*(tamanoTablero-1); k++)  if (!state [k]) faltantes.add(k);
        
        while (quedanPasos(copiaLlenarEstaIteracion)) { //aqui estoy jugando el primer maximax, por tanto abajo invocare un minimax
            
            perdida=getGanancia(copiaLlenarEstaIteracion);
//            System.out.println("Cuadros llenos antes de la jugada: "+ganancia);
//            imprimir(temporal);
            pos=primeraJugadaFaltante(copiaLlenarEstaIteracion);
//            System.out.print("Primera Jugada: "+pos+"\t");
            actualizaEstado(pos, copiaLlenarEstaIteracion);             //juego el primer paso nuevo que se puede jugar, los puntos serían del adversario
            perdida=getGanancia(copiaLlenarEstaIteracion)-perdida;    //si es 0 es porque la jugada no hizo ningún punto para el adversario
//            System.out.println("Perdida por esa jugada: "+perdida);
//            imprimir(temporal);
            
//            retorno = minimax(0, copiaEnviarHijos, pos)-perdida;            //El contrincante es quien llena esa jugada, pero se debe guardar la perdida por esa jugada
            imprimir(copiaLlenarEstaIteracion);
            if (perdida<valor) {
                valor = perdida;
                jugada=pos;
            }
        }
//        System.out.println(""+estado);
        return convertirPosicionBoolaInt(jugada);
    }
    
    public String maximinDecision (boolean state []){  
//        System.out.println("Entra a maximax decision");
                int valor = inf*-1; //*-1 para el infinito de maximax
        int retorno=0;
        int perdida=0;
        int pos=0;
        int jugada=0;
        boolean copiaLlenarEstaIteracion [], copiaEnviarHijos [];
        copiaLlenarEstaIteracion = new boolean [2*tamanoTablero*(tamanoTablero-1)];  //creo una copia del array
        duplicaInformacion(state, copiaLlenarEstaIteracion);                         //la lleno con los mismos elementos del original
        copiaEnviarHijos = new boolean [2*tamanoTablero*(tamanoTablero-1)];  //creo una copia del array
        duplicaInformacion(state, copiaEnviarHijos);                         //la lleno con los mismos elementos del original
        //cargo las jugadas que faltan
        //for (int k = 0; k<2*tamanoTablero*(tamanoTablero-1); k++)  if (!state [k]) faltantes.add(k);
        
        while (quedanPasos(copiaLlenarEstaIteracion)) { //aqui estoy jugando el primer maximax, por tanto abajo invocare un minimax
            
            perdida=getGanancia(copiaLlenarEstaIteracion);
//            System.out.println("Cuadros llenos antes de la jugada: "+ganancia);
//            imprimir(temporal);
            pos=primeraJugadaFaltante(copiaLlenarEstaIteracion);
//            System.out.print("Primera Jugada: "+pos+"\t");
            actualizaEstado(pos, copiaLlenarEstaIteracion);             //juego el primer paso nuevo que se puede jugar, los puntos serían del adversario
            perdida=getGanancia(copiaLlenarEstaIteracion)-perdida;    //si es 0 es porque la jugada no hizo ningún punto para el adversario
//            System.out.println("Perdida por esa jugada: "+perdida);
//            imprimir(temporal);
            
            retorno = minimax(0, copiaEnviarHijos, pos)-perdida;            //El contrincante es quien llena esa jugada, pero se debe guardar la perdida por esa jugada
            if (retorno>valor) {
                valor = retorno;
                jugada=pos;
            }
        }
//        System.out.println(""+estado);
        return convertirPosicionBoolaInt(jugada);
    }
    
    public int primeraJugadaFaltante (boolean temp []){
        for (int i = 0; i<2*tamanoTablero*(tamanoTablero-1); i++)  if (!temp [i]) return i;
        return 0;
    }
    
    public String minimaxDecision2 (boolean state []){  //ya tengo acceso al estado y al tamaño del tablero
//        System.out.println("Entra a maximax decision");
        int valor = inf; //*-1 para el infinito de maximax
        int retorno=0;
        int ganancia =0;
        int pos=0;
        int jugada=0;
        boolean copiaLlenarEstaIteracion [], copiaEnviarHijos [];
        copiaLlenarEstaIteracion = new boolean [2*tamanoTablero*(tamanoTablero-1)];  //creo una copia del array
        duplicaInformacion(state, copiaLlenarEstaIteracion);                         //la lleno con los mismos elementos del original
        copiaEnviarHijos = new boolean [2*tamanoTablero*(tamanoTablero-1)];  //creo una copia del array
        duplicaInformacion(state, copiaEnviarHijos);                         //la lleno con los mismos elementos del original
        //cargo las jugadas que faltan
        //for (int k = 0; k<2*tamanoTablero*(tamanoTablero-1); k++)  if (!state [k]) faltantes.add(k);
        
        while (quedanPasos(copiaLlenarEstaIteracion)) { //aqui estoy jugando el primer maximax, por tanto abajo invocare un minimax
            
            ganancia=getGanancia(copiaLlenarEstaIteracion);
//            System.out.println("Cuadros llenos antes de la jugada: "+ganancia);
//            imprimir(temporal);
            pos=primeraJugadaFaltante(copiaLlenarEstaIteracion);
//            System.out.print("Primera Jugada: "+pos+"\t");
            actualizaEstado(pos, copiaLlenarEstaIteracion);             //juego el primer paso nuevo que se puede jugar, los puntos serían del adversario
            ganancia=getGanancia(copiaLlenarEstaIteracion)-ganancia;    //si es 0 es porque la jugada no hizo ningún punto para el adversario
//            System.out.println("Perdida por esa jugada: "+perdida);
//            imprimir(temporal);
            
            imprimir(copiaLlenarEstaIteracion);
//            retorno = minimax(0, copiaEnviarHijos, pos)+ganancia;            //El contrincante es quien llena esa jugada, pero se debe guardar la perdida por esa jugada
            if (ganancia<valor) {
                valor = ganancia;
                jugada=pos;
            }
        }
//        System.out.println(""+estado);
        return convertirPosicionBoolaInt(jugada);
    }
    
    public String minimaxDecision (boolean state []){  //ya tengo acceso al estado y al tamaño del tablero
//        System.out.println("Entra a maximax decision");
        int valor = inf; //*-1 para el infinito de maximax
        int retorno=0;
        int ganancia =0;
        int pos=0;
        int jugada=0;
        boolean copiaLlenarEstaIteracion [], copiaEnviarHijos [];
        copiaLlenarEstaIteracion = new boolean [2*tamanoTablero*(tamanoTablero-1)];  //creo una copia del array
        duplicaInformacion(state, copiaLlenarEstaIteracion);                         //la lleno con los mismos elementos del original
        copiaEnviarHijos = new boolean [2*tamanoTablero*(tamanoTablero-1)];  //creo una copia del array
        duplicaInformacion(state, copiaEnviarHijos);                         //la lleno con los mismos elementos del original
        //cargo las jugadas que faltan
        //for (int k = 0; k<2*tamanoTablero*(tamanoTablero-1); k++)  if (!state [k]) faltantes.add(k);
        
        while (quedanPasos(copiaLlenarEstaIteracion)) { //aqui estoy jugando el primer maximax, por tanto abajo invocare un minimax
            
            ganancia=getGanancia(copiaLlenarEstaIteracion);
//            System.out.println("Cuadros llenos antes de la jugada: "+ganancia);
//            imprimir(temporal);
            pos=primeraJugadaFaltante(copiaLlenarEstaIteracion);
//            System.out.print("Primera Jugada: "+pos+"\t");
            actualizaEstado(pos, copiaLlenarEstaIteracion);             //juego el primer paso nuevo que se puede jugar, los puntos serían del adversario
            ganancia=getGanancia(copiaLlenarEstaIteracion)-ganancia;    //si es 0 es porque la jugada no hizo ningún punto para el adversario
//            System.out.println("Perdida por esa jugada: "+perdida);
//            imprimir(temporal);
            
            retorno = minimax(0, copiaEnviarHijos, pos)+ganancia;            //El contrincante es quien llena esa jugada, pero se debe guardar la perdida por esa jugada
            if (retorno<valor) {
                valor = retorno;
                jugada=pos;
            }
        }
//        System.out.println(""+estado);
        return convertirPosicionBoolaInt(jugada);
    }
    
    public String convertirPosicionBoolaInt (int p) {
    String texto = "";
    int i=0,j=0;
    if (p<tamanoTablero*(tamanoTablero-1)) { //la jugada esta en las filas
        i=p/tamanoTablero;
        j=p-(i*tamanoTablero);
        texto = "bottom "+i+" "+j;
        }
        else {//la jugada esta en las columnas
        p=p-tamanoTablero*(tamanoTablero-1);
        j=p/tamanoTablero;
        i=p-(j*tamanoTablero);
        texto = "right "+i+" "+j;
        }
    return texto;
    }
    
    
    public int getGanancia (boolean state []) {
        int ganancia = 0;
        int matriz[][] = new int [tamanoTablero][tamanoTablero];
        
        //inicializo los valores para un tablero de ese tamaño en una matriz
        for (int j = 0; j<tamanoTablero; j++) {
            for (int i = 0; i<tamanoTablero; i++) {
                if ((i==0 && j==0) || (i==0 && j==(tamanoTablero-1))|| (i==(tamanoTablero-1) && j==0) || (i==(tamanoTablero-1) && j==(tamanoTablero-1)))    matriz [i][j]=2;
                else  if ((i==0) || j==(tamanoTablero-1)|| i==(tamanoTablero-1)|| j==0)     matriz [i][j]=1;
                       else matriz [i][j]=0;;

                }
            }

//        incluyo los movimientos que se han realizado hasta el momento, con la codificacion creada
        for (int k=0; k<2; k++) { //k es 2 porque reviso horizontales y verticales
                for (int i = 0; i<tamanoTablero-1 ; i++) {   //Coloco las lineas horizontales
                    for (int j = 0; j<tamanoTablero; j++) {
                        if (k==0){  //esta en las filas 
                            if (state[(i*tamanoTablero)+j]){ matriz [i][j] += 1; matriz [i+1][j] += 1; } }
                        if (k==1){  //esta en las columnas
                            if (state[(((tamanoTablero*(tamanoTablero-1))+(i*tamanoTablero))+j)]){ matriz [j][i] += 1; matriz [j][i+1] += 1; } }
                    }
                }
            }
        
//         imprimo finalmente la matriz como va el juego con los movimientos dados
        for (int i = 0; i<tamanoTablero; i++) {
                for (int j = 0; j<tamanoTablero; j++) {   //Coloco las lineas horizontales
                    if (matriz[i][j]==4)
                        ganancia++;
                }
            }
        return ganancia;
    }

    public void actualizaEstado(int posicion, boolean state []) {
        String texto="";
        int x=0,y=0;
        if (posicion<(tamanoTablero*(tamanoTablero-1))){
            texto="bottom";
            x=(int)(posicion/tamanoTablero);
            y=posicion%tamanoTablero;
        }
        else {
            texto="right";
            posicion=posicion-(tamanoTablero*(tamanoTablero-1));
            y=(int)(posicion/tamanoTablero);
            x=posicion%tamanoTablero;
        }
//        System.out.println("La posicion que se actualiza x"+x+", y"+y+", Sig:"+texto);
        actualizaEstado(x, y, texto, state);
        
    }
    public void actualizaEstado(int i, int j, String sigPaso, boolean state []) {//bottom, y right, no retorno porque opera directamente sobre el state que llega
//        System.out.println("va a actualizar i:"+i+", j:"+j+", sigPaso:"+sigPaso);
        if (sigPaso.equals("bottom")) {  //es horizontal
            state[(i*tamanoTablero)+j]=true;  //en este punto realizo la jugada
            actualizaEstadoAlrededor(i, j, state);
            actualizaEstadoAlrededor(i+1, j, state);
        }
        else {//es vertical  right
            state[(j*tamanoTablero)+i+(tamanoTablero*(tamanoTablero-1))]=true;
            actualizaEstadoAlrededor(i, j, state);
            actualizaEstadoAlrededor(i, j+1, state);
        }
//        return state;
    }
    
    public void actualizaEstadoAlrededor(int i, int j, boolean state[]) {//bottom, y right
        int arriba,abajo,izquierda,derecha,count=0;//de una vez sumo la que estoy colocando
        arriba=((i-1)*tamanoTablero)+j;
        abajo=(i*tamanoTablero)+j;
        izquierda= ((j-1)*tamanoTablero)+i+(tamanoTablero*(tamanoTablero-1));
        derecha= (j*tamanoTablero)+i+(tamanoTablero*(tamanoTablero-1));
//        System.out.print("entro i:"+i+", j:"+j);
//        System.out.print("\n");
        if (i==0) {
            count++;                                          //extremo superior
            if (state[abajo]) count++;         //abajo
            }
        else {if (!(i==tamanoTablero-1)) //esta en el centro
                {   if (state[arriba]) count++; //arriba
                    if (state[abajo]) count++;     //abajo
                }
            else { count++;                                     //extremo inferior
                  if (state[arriba]) count++; //arriba
                 }
            }
        if (j==0) {
            count++;                                                                      //extremo izquierdo
            if (state[derecha]) count++;  //derecha
            }
        else {if (!(j==tamanoTablero-1)) //esta en el centro
                {   if (state[derecha]) count++;         //derecha
                    if (state[izquierda]) count++;     //izquierda
                }
            else { count++;                                     //extremo derecho
                  if (state[izquierda]) count++; //izquierda
                 }
            }
        if (count==3) {
//            System.out.print("lo voy a reenviar i:"+i+", j:"+j+"\t\t");
            if (i!=0) {if (!state[arriba]) {                state[arriba]=true;    actualizaEstadoAlrededor(i-1,j,state); }} //arriba
            if (i!=tamanoTablero-1) {{if (!state[abajo])    state[abajo]=true;     actualizaEstadoAlrededor(i+1,j,state); }} //abajo
            if (j!=0) {if (!state[izquierda]) {             state[izquierda]=true; actualizaEstadoAlrededor(i,j-1,state); }} //izquierda
            if (j!=tamanoTablero-1) {if (!state[derecha]) { state[derecha]=true;   actualizaEstadoAlrededor(i,j+1,state);  }} //derecha
        }
//        return state;
    }
    
    public boolean validaMovimientos (int stateBoard[]) { // si aun quedan movimientos por realizarce entonces los ejecuta
        for (int j = 0; j<tamanoTablero; j++) {
                for (int i = 0; i<tamanoTablero; i++) {   //Coloco las lineas horizontales
                    System.out.print(estado [i+(j*tamanoTablero)]);
                    System.out.print(" ");
                }
                System.out.println("");
            }
        return false;
    }
    
    public boolean quedanPasos (boolean estado []) {
//        boolean bandera=false;
        for (int i = 0; i<2*tamanoTablero*(tamanoTablero-1); i++) {
            if (!estado [i]) return true;
        }
        return false;
    }
    
    public int cuantosPasosQuedan (boolean temp []) {
        int bandera=0;
        for (int i = 0; i<2*tamanoTablero*(tamanoTablero-1); i++) {
            if (!temp [i]) bandera++;
        }
        return bandera;
    }
    
    public static void main (String[] argv) {
        int jugada = 8, residuo;
        MemoriaSquaresBool memoriaPrueba = new MemoriaSquaresBool();
        memoriaPrueba.cambioTamano(3);
        //memoriaPrueba.estado[1]=memoriaPrueba.estado[4]=true;
        memoriaPrueba.estado[0]=memoriaPrueba.estado[3]=memoriaPrueba.estado[6]=memoriaPrueba.estado[7]=memoriaPrueba.estado[8]=memoriaPrueba.estado[10]=true;
        System.out.println(memoriaPrueba.maximinDecision(estado));
        
        
        
    }
}