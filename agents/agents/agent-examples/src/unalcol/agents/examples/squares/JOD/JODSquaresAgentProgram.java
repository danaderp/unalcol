/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package unalcol.agents.examples.squares.JOD;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import unalcol.agents.Action;
import unalcol.agents.AgentProgram;
import unalcol.agents.Percept;
import unalcol.agents.examples.squares.Squares;
import unalcol.types.collection.vector.Vector;

/**
 *
 * @author Jonatan
 */
public class JODSquaresAgentProgram implements AgentProgram {
    protected String color;
    private Vector<Node> goodSquares; //opciones buenas
    private ArrayList<Node> badSquares;  //opciones malas
    private HashMap<String, Node> arbol;
    
    public JODSquaresAgentProgram( String color ){
        this.color = color;        
    }
    
    @Override
    public Action compute(Percept p) {
        //se inicializa los array y ventor que se usaran para almacenar la informacion de las casillas
        if ((goodSquares == null)||(badSquares == null)){
            goodSquares = new Vector<Node>();
            badSquares = new ArrayList<Node>();
        }
        //esta parte es para siempre explorar el tablero por el error de la memoria
        goodSquares.clear();
        explore(p);
        long time = (long)(200 * Math.random());
        try{
           Thread.sleep(time);
        }catch(Exception e){}
        if( p.getAttribute(Squares.TURN).equals(color) ){
            //inicia el juego
            return play(p);

        }
        return null;
    }
    
    public Action play(Percept p){
        
        int s = goodSquares.size(); //cantidad de buenas opciones
        
        if(s > 0){
            int index =(int)(Math.random()*s);  
            int i = goodSquares.get(index).getI();
            int j = goodSquares.get(index).getJ();
            //System.out.println("i:"+i+" j:"+j);
            int lineas = goodSquares.get(index).getLineas();
            
            
            int countLines = 3;//lineas del vecino
            
                //evaluamos vecino izquierdo
            if(goodSquares.get(index).getLeft()== 0 ){
                    if(((String)p.getAttribute(i+":"+(j-1)+":"+Squares.LEFT)).equals(Squares.FALSE)){                       
                         countLines--;
                     }
                    if(((String)p.getAttribute(i+":"+(j-1)+":"+Squares.TOP)).equals(Squares.FALSE)){                       
                         countLines--;
                     }
                    if(((String)p.getAttribute(i+":"+(j-1)+":"+Squares.BOTTOM)).equals(Squares.FALSE)){                       
                         countLines--;
                     }
                    //si el vecino tiene menos de 2 lineas es una buena jugada
                    if(countLines<2){
                        //actualiza lado y lineas de la casiila actual
                        goodSquares.get(index).setLeft(1);
                        goodSquares.get(index).setLineas(lineas+1);
                        
                        if(goodSquares.get(index).getLineas()==2){
                            //si la casilla pasa a ser mala opciÃ³n la cambiamos de arreglo
                             goodSquares.remove(index);
                            // badSquares.add(m);
                        }
                        
                        
                        //Busca al vecino y actualiza los valores que cambiaran al hacer la jugada
                        for(int k = 0; k<goodSquares.size();k++){
                                if(goodSquares.get(k).getI()==(i) && goodSquares.get(k).getJ()==(j-1)){
                                     goodSquares.get(k).setRigth(1);
                                     goodSquares.get(k).setLineas(goodSquares.get(k).getLineas()+1);
                                        if(goodSquares.get(k).getLineas()==2){
                                            //si la casilla pasa a ser mala opciÃ³n la cambiamos de arreglo 
                                             goodSquares.remove(k);
                                            // badSquares.add(m);
                                        }
                                        break;
                                        
                                }
                        }
                        //Verifica la disponibilidad de la casiila para futuras jugadas

                        return new Action( i+":"+j+":"+Squares.LEFT);

                    }
             }
    
               //evaluamos vecino superior
                    
              if(goodSquares.get(index).getTop()== 0){
                    if(((String)p.getAttribute((i-1)+":"+j+":"+Squares.LEFT)).equals(Squares.FALSE)){                       
                         countLines--;
                     }
                    if(((String)p.getAttribute((i-1)+":"+j+":"+Squares.TOP)).equals(Squares.FALSE)){                       
                         countLines--;
                     }
                    if(((String)p.getAttribute((i-1)+":"+j+":"+Squares.RIGHT)).equals(Squares.FALSE)){                       
                         countLines--;
                     }
                    //si el vecino tiene menos de 2 lineas es una buena jugada
                    if(countLines<2){
                        //actualiza lado y lineas de la casiila actual
                        goodSquares.get(index).setTop(1);
                        goodSquares.get(index).setLineas(lineas+1);
                        
                        
                       if(goodSquares.get(index).getLineas()==2){
                            //si la casilla pasa a ser mala opciÃ³n la cambiamos de arreglo
                             goodSquares.remove(index);
                            // badSquares.add(m);
                        }
                        
                        //Busca al vecino y actualiza los valores que cambiaran al hacer la jugada
                        for(int k = 0; k<goodSquares.size();k++){
                                if(goodSquares.get(k).getI()==(i-1) && goodSquares.get(k).getJ()==(j)){
                                     goodSquares.get(k).setBottom(1);
                                     goodSquares.get(k).setLineas(goodSquares.get(k).getLineas()+1);
                                        if(goodSquares.get(k).getLineas()==2){
                                            //si la casilla pasa a ser mala opciÃ³n la cambiamos de arreglo
                                             goodSquares.remove(k);
                                            // badSquares.add(m);
                                        }    
                                     break;   
                                }
                        }

                        
                        return new Action( i+":"+j+":"+Squares.TOP);

                    }          
                }
                
                //evaluamos vecino derecho
              
              if(goodSquares.get(index).getRigth()== 0){
                    if(((String)p.getAttribute(i+":"+(j+1)+":"+Squares.BOTTOM)).equals(Squares.FALSE)){                       
                         countLines--;
                     }
                    if(((String)p.getAttribute(i+":"+(j+1)+":"+Squares.TOP)).equals(Squares.FALSE)){                       
                         countLines--;
                     }
                    if(((String)p.getAttribute(i+":"+(j+1)+":"+Squares.RIGHT)).equals(Squares.FALSE)){                       
                         countLines--;
                     }
                    //si el vecino tiene menos de 2 lineas es una buena jugada
                    if(countLines<2){
                        //actualiza lado y lineas de la casiila actual
                        goodSquares.get(index).setRigth(1);
                        goodSquares.get(index).setLineas(lineas+1);
                        
                        if(goodSquares.get(index).getLineas()==2){
                            //si la casilla pasa a ser mala opciÃ³n la cambiamos de arreglo
                             goodSquares.remove(index);
                            // badSquares.add(m);
                        }
                        //Busca al vecino y actualiza los valores que cambiaran al hacer la jugada
                        for(int k = 0; k<goodSquares.size();k++){
                                if(goodSquares.get(k).getI()==(i) && goodSquares.get(k).getJ()==(j+1)){
                                     goodSquares.get(k).setLeft(1);
                                     goodSquares.get(k).setLineas(goodSquares.get(k).getLineas()+1);
                                        if(goodSquares.get(k).getLineas()==2){
                                            //si la casilla pasa a ser mala opciÃ³n la cambiamos de arreglo
                                             goodSquares.remove(k);
                                            // badSquares.add(m);
                                        }       
                                        break;
                                }
                        }

                        
                        return new Action( i+":"+j+":"+Squares.RIGHT);

                    }          
                }
              
              //evaluamos el vecino inferior
              
                  if(goodSquares.get(index).getBottom()== 0){
                    if(((String)p.getAttribute((i+1)+":"+j+":"+Squares.BOTTOM)).equals(Squares.FALSE)){                       
                         countLines--;
                     }
                    if(((String)p.getAttribute((i+1)+":"+j+":"+Squares.LEFT)).equals(Squares.FALSE)){                       
                         countLines--;
                     }
                    if(((String)p.getAttribute((i+1)+":"+j+":"+Squares.RIGHT)).equals(Squares.FALSE)){                       
                         countLines--;
                     }
                    //si el vecino tiene menos de 2 lineas es una buena jugada
                    if(countLines<2){
                        //actualiza lado y lineas de la casiila actual
                        goodSquares.get(index).setBottom(1);
                        goodSquares.get(index).setLineas(lineas+1);
                        
                        if(goodSquares.get(index).getLineas()==2){
                            //si la casilla pasa a ser mala opciÃ³n la cambiamos de arreglo
                             goodSquares.remove(index);
                            // badSquares.add(m);
                        }
                        //Busca al vecino y actualiza los valores que cambiaran al hacer la jugada
                        for(int k = 0; k<goodSquares.size();k++){
                                if(goodSquares.get(k).getI()==(i+1) && goodSquares.get(k).getJ()==(j)){
                                     goodSquares.get(k).setTop(1);
                                     goodSquares.get(k).setLineas(goodSquares.get(k).getLineas()+1);
                                        if(goodSquares.get(k).getLineas()==2){
                                            //si la casilla pasa a ser mala opciÃ³n la cambiamos de arreglo
                                             goodSquares.remove(k);
                                            // badSquares.add(m);
                                        }       
                                        break;
                                }
                        }

                        
                        return new Action( i+":"+j+":"+Squares.BOTTOM);

                    }          
                }
        }
        
        else if(s==0){
             //System.out.println("paso 2");
        
            //actualizamos la lista de todas las casillas que generan perdidas
            //solo lo hacemos una vez luego solo reexploramos el arbol
            if (arbol==null){
                arbol = new HashMap<String,Node>();
                explore_lost(p);
                minmax_lost();
            }
            
           
            //se calcula el total de perdidas que genera cada uno
            
            //se toma y se compara cual es el menor numero disponible
            boolean selection=false;
            if (!badSquares.isEmpty()){
                while(!selection){
                    Node nodo = badSquares.get(0);
                    int i = nodo.getI(),j =nodo.getJ(), lineas = 4;
                    if(((String)p.getAttribute(i+":"+j+":"+Squares.LEFT)).equals(Squares.FALSE)) {
                        lineas--;
                    }
                    if(((String)p.getAttribute(i+":"+j+":"+Squares.TOP)).equals(Squares.FALSE)) {
                          lineas--;
                      }
                    if(((String)p.getAttribute(i+":"+j+":"+Squares.BOTTOM)).equals(Squares.FALSE)) {
                          lineas--;
                      }
                    if(((String)p.getAttribute(i+":"+j+":"+Squares.RIGHT)).equals(Squares.FALSE)) {
                          lineas--;
                      }
                    if(lineas == 4){
                       badSquares.remove(0);
                    }else{
                        //toma cualquier linea que este libre puesto que no afecta
                        if (nodo.getLeft()!=1)
                            return new Action( i+":"+j+":"+Squares.LEFT);
                        else if(nodo.getRigth()!=1)
                            return new Action( i+":"+j+":"+Squares.RIGHT);
                        else if(nodo.getTop()!=1)
                            return new Action( i+":"+j+":"+Squares.TOP);
                        else if(nodo.getBottom()!=1)
                            return new Action( i+":"+j+":"+Squares.BOTTOM);
                    }
                }
            }
        }
        
        return new Action(Squares.PASS);
        
    
    }
    
    
    
    //explora el tablaero inicial
    public void explore(Percept p){
        int sizeB = Integer.parseInt((String)p.getAttribute(Squares.SIZE));
        //Vector<String> successors = new Vector();
        //byte eval[][] = null;
        int lineas;       
        for(int i = 0; i<sizeB; i++){
            for( int j = 0; j<sizeB; j++){
                Node current = new Node(i,j);
                lineas = 4;
                if(((String)p.getAttribute(i+":"+j+":"+Squares.LEFT)).equals(Squares.FALSE)) {
                    //successors.add(Squares.LEFT);
                    current.setLeft(0);
                    lineas--;
                    //lineas = -1;
                }
              if(((String)p.getAttribute(i+":"+j+":"+Squares.TOP)).equals(Squares.FALSE)) {
                    //successors.add(Squares.TOP);
                    current.setTop(0);
                    lineas--;
                   // lineas -= 2 ;
                }
              if(((String)p.getAttribute(i+":"+j+":"+Squares.BOTTOM)).equals(Squares.FALSE)) {
                    //successors.add(Squares.BOTTOM);
                    current.setBottom(0);
                    lineas--;
                    //lineas -= 4;
                }
              if(((String)p.getAttribute(i+":"+j+":"+Squares.RIGHT)).equals(Squares.FALSE)) {
                    //successors.add(Squares.RIGHT);
                  current.setRigth(0);
                  lineas--;
                    //lineas -= 8;
                }
                current.setLineas(lineas);
                
                if(lineas < 2){
                  goodSquares.add(current);
                }
              //eval[i][j]=lineas;
            }
        
        }
   
    } 
    
    @Override
    public void init() {
    }
       
    public void minmax_lost(){//calcula el total de perdidas al marcar una casill de las ubicadas en hashmap

        ArrayList<String> action;
        action  = new ArrayList<String>();
        int i=0,x,y;
        badSquares.clear();
        action.clear();
        for (String key : arbol.keySet()) {
            //solo ejecuta la busqueda por una casilla para que el arbol solo 
            //tenga una jugada disponible y no todas las que juntan la misma jugada
            Node nodo = arbol.get(key);
            x = nodo.getI();
            y = nodo.getJ();
            String vfz = x+","+y;
            if (!action.contains(vfz)){
                nodo.setPerdidas(total_Perdidas(x,y,action));            
                badSquares.add(nodo);
            }
        }
        
        Collections.sort(badSquares, new Comparator<Node>(){  
            public int compare( Node a, Node b ) {
                int resultado = Integer.compare( a.getPerdidas(), b.getPerdidas());
                return resultado; 
            }
        });
    }
    
    public int total_Perdidas(int x, int y,ArrayList<String> visitados){
        //retornara si algun vecino puede seguirse llenando(se usa minimax_lost)
            String vf = x+","+y;
            int lost=1,v1=0,v2=0,v3=0,v4=0;
            boolean exist = arbol.containsKey(vf),vecino;
            
            if (exist){
                if (!visitados.contains(vf)){
                    visitados.add(vf);
                    vf = (x+1)+","+y;
                    vecino = arbol.containsKey(vf);
                    if (vecino)//se verifica que el lado de la casilla no limite 
                        //porque significa que la otra casilla pertenece a otro grupo de perdidas
                        if (arbol.get(vf).getTop()==0)
                            v1 = total_Perdidas(x+1,y,visitados);
                    vf = (x-1)+","+y;
                    vecino = arbol.containsKey(vf);
                    if (vecino)
                        if (arbol.get(vf).getBottom()==0)
                            v2 = total_Perdidas(x-1,y,visitados);
                    vf = x+","+(y+1);
                    vecino = arbol.containsKey(vf);
                    if (vecino)
                        if (arbol.get(vf).getLeft()==0)
                            v3 = total_Perdidas(x,y+1,visitados);
                    vf = x+","+(y-1);
                    vecino = arbol.containsKey(vf);
                    if (vecino)
                        if (arbol.get(vf).getRigth()==0)
                            v4 = total_Perdidas(x,y-1,visitados);
                    return lost+v1+v2+v3+v4;
                }
            }
        return 0;
        
    }
    
    public void explore_lost(Percept p){//llena el hashmap en donde estan las casillas con opcion de perdida
        //se llena el arbol
        int sizeB = Integer.parseInt((String)p.getAttribute(Squares.SIZE));
        int lineas;       
        for(int i = 0; i<sizeB; i++){
            for( int j = 0; j<sizeB; j++){
                Node current = new Node(i,j);
                lineas = 4;
                if(((String)p.getAttribute(i+":"+j+":"+Squares.LEFT)).equals(Squares.FALSE)) {
                    current.setLeft(0);
                    lineas--;
                }
              if(((String)p.getAttribute(i+":"+j+":"+Squares.TOP)).equals(Squares.FALSE)) {
                    current.setTop(0);
                    lineas--;
                }
              if(((String)p.getAttribute(i+":"+j+":"+Squares.BOTTOM)).equals(Squares.FALSE)) {
                    current.setBottom(0);
                    lineas--;
                }
              if(((String)p.getAttribute(i+":"+j+":"+Squares.RIGHT)).equals(Squares.FALSE)) {
                  current.setRigth(0);
                    lineas--;
                }
                current.setLineas(lineas);

                if(lineas != 4){
                  String vf = i+","+j;
                  arbol.put(vf, current);
                }
            }
        }
    } 
}
