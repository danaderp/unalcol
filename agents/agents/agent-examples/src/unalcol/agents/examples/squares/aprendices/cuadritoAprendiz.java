package unalcol.agents.examples.squares.aprendices;

import unalcol.agents.Action;
import unalcol.agents.AgentProgram;
import unalcol.agents.Percept;
import unalcol.agents.examples.squares.Squares;
import unalcol.types.collection.vector.Vector;
// evitar 3 5 6 9 10 12 
public class cuadritoAprendiz  implements AgentProgram {
	 protected static final int LEFTt = 1;
	    protected static final int TOPt = 2;
	    protected static final int RIGHTt = 4;
	    protected static final int BOTTOMt = 8;
	    protected String color;
	    int vEvitar[]={3,5,6,9,10,12,15};
	    public cuadritoAprendiz( String color ){
	        this.color = color;        
	    }
	    
	    @Override
	    public Action compute(Percept p) {
	    	//System.out.println("mi turno");
	        long time = (long)(100 * Math.random());
	        try{
	           Thread.sleep(time);
	        }catch(Exception e){}
	        if( p.getAttribute(Squares.TURN).equals(color) ){
	            int size = Integer.parseInt((String)p.getAttribute(Squares.SIZE));
	            int i = 0;
	            int j = 0, sum=6,valaux=0;
	            //Vector<String> v = new Vector();
	            Objcuadrito [][] matriz=new Objcuadrito[size][size];
	            for(int z=0;z<size;z++)
	            	for(int k=0;k<size;k++){
	            		//sum=6;
	            		valaux=0;
	            		matriz[z][k]=new Objcuadrito();
	            		if(((String)p.getAttribute(z+":"+k+":"+Squares.LEFT)).equals(Squares.TRUE))
	            			{valaux|=LEFTt;}
	            		else matriz[z][k].getV().add(Squares.LEFT);
	            		if(((String)p.getAttribute(z+":"+k+":"+Squares.TOP)).equals(Squares.TRUE))
	            			{valaux|=TOPt;}
	            		else matriz[z][k].getV().add(Squares.TOP);
	            		if(((String)p.getAttribute(z+":"+k+":"+Squares.BOTTOM)).equals(Squares.TRUE))
	            			{valaux|=BOTTOMt;}
	            		else matriz[z][k].getV().add(Squares.BOTTOM);
	            		if(((String)p.getAttribute(z+":"+k+":"+Squares.RIGHT)).equals(Squares.TRUE))
	            			{valaux|=RIGHTt;}
	            		else matriz[z][k].getV().add(Squares.RIGHT);
	            		matriz[z][k].setValor(valaux);
	            		/*for (int f = 0; f < vEvitar.length; f++){
	            			 System.out.println(matriz[z][k]);
	            			 if(matriz[z][k].getValor()==vEvitar[f]){
	 	      	            	sum--;
	 	      	            	  break;
	 	      	            	  
	 	      	              }
	            			 if(z<size-1){ System.out.println(matriz[z+1][k]);
	            				 if(matriz[z+1][k].getValor()==vEvitar[f]){
	 	      	            	  sum--;
	 	      	            	  break;
	 	      	              }}
	 	      	              if(z>0){
	 	      	            	  System.out.println(matriz[z-1][k]);
	 	      	              if(matriz[z-1][k].getValor()==vEvitar[f]){
	 	      	            	  sum--;
	 	      	            	  break;
	 	      	              }}
	 	      	              if(k<size-1)
	 	      	            if(matriz[z][k+1].getValor()==vEvitar[f]){
	 	      	            	  sum--;
	 	      	            	  break;
	 	      	              }
	 	      	            if(k>0){System.out.println(matriz[z][k-1]);
	 	      	              if(matriz[z][k-1].getValor()==vEvitar[f]){
	 	      	            	  sum--;
	 	      	            	  break;
	 	      	              }
	 	      	              }
	            		}
	      	             //sum dir a pos act y pos lado y comparar con evitar, guardar el vector v en posicion y hacer esto despues de tener la matriz llena, vericar si aqui funciona esto ultimo
	            		if(sum==6)
	            			return new Action( z+":"+k+":"+matriz[z][k].getV().get((int)(Math.random()*matriz[z][k].getV().size())));*/
	           	}
	           Action rta= esBjugar(matriz,size);
	           if(rta!=null)
	        	   return rta;
	           else
	           {
	        	   //minmax ponga ficha y compruebe matriz  hasta donde se llena 
	           
	            //rta aleatoria
	            while(matriz[i][j].getV().size()==0){
	                i = (int)(size*Math.random());
	                j = (int)(size*Math.random());
	                if(((String)p.getAttribute(i+":"+j+":"+Squares.LEFT)).equals(Squares.FALSE))
	                  matriz[i][j].getV().add(Squares.LEFT);
	                if(((String)p.getAttribute(i+":"+j+":"+Squares.TOP)).equals(Squares.FALSE))
	                  matriz[i][j].getV().add(Squares.TOP);
	                if(((String)p.getAttribute(i+":"+j+":"+Squares.BOTTOM)).equals(Squares.FALSE))
	                  matriz[i][j].getV().add(Squares.BOTTOM);
	                if(((String)p.getAttribute(i+":"+j+":"+Squares.RIGHT)).equals(Squares.FALSE))
	                  matriz[i][j].getV().add(Squares.RIGHT);
	              }
	              return new Action( i+":"+j+":"+matriz[i][j].getV().get((int)(Math.random()*matriz[i][j].getV().size())) );
                   }
	            
	            //return new Action( i+":"+j+":"+v.get((int)(Math.random()*v.size())) );
	       }
	       // System.out.println("paso yo");
	        return new Action(Squares.PASS);
	    }

	    @Override
	    public void init() {
	    }
	    
	    private Action esBjugar(Objcuadrito[][] matriz, int size){
            for(int z=0;z<size;z++)// y
            	for(int k=0;k<size;k++){// x
            		if(matriz[z][k].getV().size()>2)
            		for(int m=0;m<matriz[z][k].getV().size();m++){
            			if(matriz[z][k].getV().get(m).equals("bottom")){
            				//preguntar que susede en top del otro
            				if(z<size-1)
            					if(matriz[z+1][k].getValor()!=9 && matriz[z+1][k].getValor()!=12 && matriz[z+1][k].getValor()!=5)
            					{
            						//juegue
            						return new Action( z+":"+k+":"+matriz[z][k].getV().get(m));
            					}
            			}if(matriz[z][k].getV().get(m).equals("right")){
            				//preguntar que susede en left del otro
            				if(k<size-1)
                					if(matriz[z][k+1].getValor()!=12 && matriz[z][k+1].getValor()!=6 && matriz[z][k+1].getValor()!=10){
            						//juegue
            						return new Action( z+":"+k+":"+matriz[z][k].getV().get(m));
            					}
            			}
            			if(matriz[z][k].getV().get(m).equals("left")){
            				//preguntar que susede en right del otro
            				if(k>0)
           					 if(matriz[z][k-1].getValor()!=3 && matriz[z][k-1].getValor()!=9 && matriz[z][k-1].getValor()!=10){
            						//juegue
            						return new Action( z+":"+k+":"+matriz[z][k].getV().get(m));
            					}
            			}
            			if(matriz[z][k].getV().get(m).equals("top")){
            				//preguntar que susede en bottom del otro
            				 if(z>0)
                 					if(matriz[z-1][k].getValor()!=3 && matriz[z-1][k].getValor()!=6  && matriz[z-1][k].getValor()!=5){
            						//juegue
            						 return new Action( z+":"+k+":"+matriz[z][k].getV().get(m));
            					 } 
            			}
            		}
            		
            	}
			return null;
	    }
}
