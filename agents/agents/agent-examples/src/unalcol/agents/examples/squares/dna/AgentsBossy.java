package unalcol.agents.examples.squares.dna;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.TreeMap;

import unalcol.agents.Action;
import unalcol.agents.AgentProgram;
import unalcol.agents.Percept;
import unalcol.agents.examples.squares.Squares;

public class AgentsBossy implements AgentProgram{

	protected String color;
	protected List<Square> posibilites;
	protected List<Square> regalar;
	protected TreeMap<Integer, TreeMap<Integer, TreeMap<Integer, Integer>>> mapa = null;
	protected Node root;
	protected TreeMap<Integer, TreeMap<Integer, Boolean>> cuadritos = null;
	protected int nuestrocuadritos; 
	protected int otroscuadritos;
	
	public AgentsBossy(String color){
		this.color = color;
		nuestrocuadritos = 0;
		otroscuadritos = 0;
	}
	
	@Override
	public Action compute(Percept p) {
		if( p.getAttribute(Squares.TURN).equals(color) ){
			int size = Integer.parseInt((String)p.getAttribute(Squares.SIZE));
			String respuesta;
			int[] raya ;
			int count;
			posibilites = new ArrayList();
			regalar = new ArrayList();
			cuadritos = new TreeMap<>();
			mapa = new TreeMap<Integer, TreeMap<Integer, TreeMap<Integer, Integer>>>();
				for(int i=0;i<size;i++){
					for(int j=0;j<size;j++){
					 raya = new int[4];
					 count = 0;
					 
					 if(!mapa.containsKey(i)){
						mapa.put(i, new TreeMap<Integer, TreeMap<Integer, Integer>>());
					 }
						 if(!mapa.get(i).containsKey(j)){
							 mapa.get(i).put(j, new TreeMap<Integer, Integer>());
						 }
						 
						 if(!cuadritos.containsKey(i)){
							 cuadritos.put(i, new TreeMap<Integer, Boolean>());
						 }
						 if(!cuadritos.get(i).containsKey(j)){
							 cuadritos.get(i).put(j, null);
						 }
						 respuesta = (String)p.getAttribute(i+":"+j+":color");
						 if(!respuesta.equals(Squares.SPACE)){
							 mapa.get(i).get(j).put(0, 1);
							 raya[0]=1;
							 count++; 
							 mapa.get(i).get(j).put(1, 1);
							 raya[1]=1;
							 count++;
							 mapa.get(i).get(j).put(2, 1);
							 raya[2]=1;
							 count++;
							 mapa.get(i).get(j).put(3, 1);
							 raya[3]=1;
							 count++;
							 if(respuesta.equals(color)){
								 cuadritos.get(i).put(j,false); //nuestro
							 }else{
								 cuadritos.get(i).put(j,true);  //del otro
							 }
							 continue;
						 }
						 respuesta = (String)p.getAttribute(i+":"+j+":"+Squares.TOP);
						 if(respuesta.equals(Squares.TRUE)){
							mapa.get(i).get(j).put(0, 1);
							 raya[0]=1;
							 count++;
						 }else{
							 //mapa.get(i).get(j).put(0, 0);
							 raya[0]=0;
						 }
					 
						 respuesta = (String)p.getAttribute(i+":"+j+":"+Squares.RIGHT);
						 if(respuesta.equals(Squares.TRUE)){
							 mapa.get(i).get(j).put(1, 1);
							 raya[1]=1;
							 count++;
						 }else{
							 //mapa.get(i).get(j).put(1, 0);
							 raya[1]=0;
						 }
						 respuesta = (String)p.getAttribute(i+":"+j+":"+Squares.BOTTOM);
						 if(respuesta.equals(Squares.TRUE)){
							 mapa.get(i).get(j).put(2, 1);
							 raya[2]=1;
							 count++;
						 }else{
							 //mapa.get(i).get(j).put(2, 0);
							 raya[2]=0;
						 }
						 respuesta = (String)p.getAttribute(i+":"+j+":"+Squares.LEFT);
						 if(respuesta.equals(Squares.TRUE)){
							 mapa.get(i).get(j).put(3, 1);
							 raya[3]=1;
							 count++;
						 }else{
							 //mapa.get(i).get(j).put(3, 0);
							 raya[3]=0;
						 }
						 if(count<2){
							 posibilites.add(new Square(i, j, raya));
						 }else{
							 if(count==2){
								 regalar.add(new Square(i,j,raya));
							 }
						 }
						 
					 }	
				 }
			if(posibilites.isEmpty()){
				if(regalar.size()==0) return new Action("0:0:top");
				Random n = new Random();
				int h = (int)(n.nextDouble()*regalar.size());
				Square square = regalar.remove(h);
				return new Action(seleccionarAlAzar(size, square, regalar, mapa));
				/*
				for(Integer keyx: mapa.keySet()){
					for(Integer keyy: mapa.get(keyx).keySet()){
						respuesta = (String)p.getAttribute(keyx+":"+keyy+":color");
						 if(respuesta.equals(this.color)){
							 nuestrocuadritos++;
						 }else{
						 if(!respuesta.equals(Squares.SPACE)){
							 otroscuadritos++;
						 }}
					}
				}
				root = new Node(mapa, null, false,0,nuestrocuadritos,otroscuadritos);
				
				List<Node> cola = new LinkedList<>();
				 cola.add(root);
				 while(!cola.isEmpty()){
					 Node current =  cola.remove(cola.size()-1);
					 for(Node nodo: generarHijos2(current, size, regalar,p, current.mapa, nuestrocuadritos, otroscuadritos)){
						 cola.add(nodo);	 
					 }
				 }
				System.out.println("SE METIO ");
				return new Action(Squares.PASS);
				*/
			}else{
				Random n = new Random();
				int h = (int)(n.nextDouble()*posibilites.size());
				Square square = posibilites.remove(h);
				return new Action(seleccionarAlAzar(size, square, posibilites, mapa));
			}
		}else{
			return new Action(Squares.PASS);
		}
	}
	
	/*private List<Node> generarHijos2(Node current, int size, List<Square> regalar, Percept p, TreeMap<Integer, TreeMap<Integer, TreeMap<Integer, Integer>>> currentMapa, int cuadros1, int cuadros2){
		List<Node> nodes = new LinkedList<>();
	    TreeMap<Integer, TreeMap<Integer, TreeMap<Integer, Integer>>> mapa2 = new TreeMap<Integer, TreeMap<Integer, TreeMap<Integer, Integer>>>();
	    String respuesta;
	    
	    for(int integer: currentMapa.keySet()){
			if(!mapa2.containsKey(integer)){
				mapa2.put(integer, new TreeMap<Integer, TreeMap<Integer, Integer>>());
			}
			for(int into: currentMapa.get(integer).keySet()){
				if(!mapa2.get(integer).containsKey(into)){
					mapa2.get(integer).put(into, new TreeMap<Integer, Integer>());
					for(int l=0;l<4;l++){
						if(currentMapa.get(integer).get(into).containsKey(l)){
							mapa2.get(integer).get(into).put(l,1);
						}
					}
				}
			}
		}
	    
		for(int i=0;i<size;i++){
			for(int j=0;j<size;j++){
				for(int h=0;h<4;h++){
					
					if(!mapa2.get(i).get(j).containsKey(h)){
						if(mapa2.get(i).get(j).size()==3){
							mapa2.get(i).get(j).put(h, 1);
							recursion3(mapa2, i, j, h, current.turno,size, cuadros1, cuadros2);
							break;
						}
					}
				}
			}
		}
	    
	    
	    
		for(int i=0;i<regalar.size();i++){
			for(int j=0;j<4;j++){
				if(regalar.get(i).border[j]==0){
					TreeMap<Integer, TreeMap<Integer, TreeMap<Integer, Integer>>> subir = new TreeMap<Integer, TreeMap<Integer, TreeMap<Integer, Integer>>>();
					
					for(int integer: mapa2.keySet()){
						if(!subir.containsKey(integer)){
							subir.put(integer, new TreeMap<Integer, TreeMap<Integer, Integer>>());
						}
						for(int into: mapa2.get(integer).keySet()){
							if(!subir.get(integer).containsKey(into)){
								subir.get(integer).put(into, new TreeMap<Integer, Integer>());
								/*respuesta = (String)p.getAttribute(integer+":"+into+":color");
								if(!respuesta.equals("space")){
									subir.get(integer).get(into).put(0,1);
									subir.get(integer).get(into).put(1,1);
									subir.get(integer).get(into).put(2,1);
									subir.get(integer).get(into).put(3,1);
									if(respuesta.equals(color)){
										 cuadritos.get(integer).put(into,false); //nuestro
									 }else{
										 cuadritos.get(integer).put(into,true);  //del otro
									 }
									continue;
								}*
								for(int l=0;l<4;l++){
									if(mapa2.get(integer).get(into).containsKey(l)){
										subir.get(integer).get(into).put(l,1);
									}
								}
							}
						}
					}
					if(!subir.get(regalar.get(i).x).get(regalar.get(i).y).containsKey(j)){
						subir.get(regalar.get(i).x).get(regalar.get(i).y).put(j , 1);
						recursion3(mapa2, regalar.get(i).x, regalar.get(i).y, j, !current.turno,size, );
						nodes.add(new Node(subir, current, !current.turno,current.nivel+1,0,0));
					}
				}
			}
		}
		return nodes;
		
	}*/
	
	/*private List<Node> generarHijos(Node current, int size) {
		// TODO Auto-generated method stub
		List<Node> nodos = new ArrayList<>();
		TreeMap<Integer, TreeMap<Integer, TreeMap<Integer, Integer>>> mapaCurrent =  new TreeMap<Integer, TreeMap<Integer, TreeMap<Integer, Integer>>>();
		TreeMap<Integer, TreeMap<Integer, Boolean>> cuadritos2 = new TreeMap<Integer, TreeMap<Integer, Boolean>>();
		mapaCurrent.putAll(current.mapa);
		cuadritos2.putAll(current.cuadritos);
		for(int i=0;i<size;i++){
			for(int j=0;j<size;j++){
				for(int h=0;h<4;h++){
					
					if(!mapaCurrent.get(i).get(j).containsKey(h)){
						if(mapaCurrent.get(i).get(j).size()==3){
							mapaCurrent.get(i).get(j).put(h, 1);
							cuadritos.get(i).put(j, current.turno);
							recursion2(mapaCurrent, i, j, h, current.turno,size);
						}
					}
				}
			}
		}
		for(int i=0;i<size;i++){
			for(int j=0;j<size;j++){
				for(int h=0;h<4;h++){
					TreeMap<Integer, TreeMap<Integer, TreeMap<Integer, Integer>>> subir = new TreeMap<Integer, TreeMap<Integer, TreeMap<Integer, Integer>>>();
					for(int integer: mapaCurrent.keySet()){
						if(!subir.containsKey(integer)){
							subir.put(integer, new TreeMap<Integer, TreeMap<Integer, Integer>>());
						}
						for(int into: mapaCurrent.get(integer).keySet()){
							if(!subir.get(integer).containsKey(into)){
								subir.get(integer).put(into, new TreeMap<Integer, Integer>());
								for(int l=0;l<4;l++){
									if(mapaCurrent.get(integer).get(into).containsKey(l)){
										subir.get(integer).get(into).put(l,1);
									}
								}
							}
						}
					}
					if(!subir.get(i).get(j).containsKey(h)){
						subir.get(i).get(j).put(h, 1);
						nodos.add(new Node(subir, current, !current.turno, cuadritos2, current.nivel+1));
					}
				}
			}
		}
		return nodos;
	}*/
	
	

	private void recursion3(
			TreeMap<Integer, TreeMap<Integer, TreeMap<Integer, Integer>>> mapa2,
			int i, int j, int h, boolean turno, int size, int cuadro1, int cuadro2) {
		 
		List<Integer> x = new LinkedList<>();
		List<Integer> y = new LinkedList<>();
		x.add(i);
		y.add(j);
		int cuadros1 = cuadro1;
		int cuadros2 = cuadro2;
		while(!x.isEmpty()){
			i = x.remove(0);
			j = y.remove(0);
			for(int l=0;l<4;l++){
				if(!mapa2.get(i).get(j).containsKey(l)){
					mapa2.get(i).get(j).put(l, 1);
					if(!turno){
						cuadros2++;
					}else{
						cuadros1++;
					}
					switch(l){
					case 0:
						if(i==0) break;
						x.add(i-1);
						y.add(j);
						break;
					case 1:
						if(j==size-1) break;
						x.add(i);
						y.add(j+1);
						break;
					case 2:
						if(i==size-1) break;
						x.add(i+1);
						y.add(j);
						break;
					case 3:
						if(j==0) break;
						x.add(i);
						y.add(j-1);
						break;
					}
				}
			}
		}
		
	}

	/*private void recursion2(
			TreeMap<Integer, TreeMap<Integer, TreeMap<Integer, Integer>>> mapaCurrent,
			int i, int j, int h, boolean turno, int size) {
		// TODO Auto-generated method stub
		
		switch(h){
			case 0:
				i--;
				if(i<0) break;
				mapaCurrent.get(i).get(j).put(h,1);
				for(int l=0;l<4;l++){
					if(!mapaCurrent.get(i).get(j).containsKey(l)){
						mapaCurrent.get(i).get(j).put(l,1);
						recursion2(mapaCurrent, i, j, l, turno,size);
						break;
					}
				}
				break;
			case 1:
				j++;
				if(j>=size) break;
				mapaCurrent.get(i).get(j).put(h,1);
				for(int l=0;l<4;l++){
					if(!mapaCurrent.get(i).get(j).containsKey(l)){
						mapaCurrent.get(i).get(j).put(l,1);
						recursion2(mapaCurrent, i, j, l, turno,size);
						break;
					}
				}
				break;
			case 2:
				i++;
				if(i>=size) break;
				mapaCurrent.get(i).get(j).put(h,1);
				for(int l=0;l<4;l++){
					if(!mapaCurrent.get(i).get(j).containsKey(l)){
						mapaCurrent.get(i).get(j).put(l,1);
						recursion2(mapaCurrent, i, j, l, turno,size);
						break;
					}
				}
				break;
			case 3:
				j--;
				if(j<0) break;
				mapaCurrent.get(i).get(j).put(h,1);
				for(int l=0;l<4;l++){
					if(!mapaCurrent.get(i).get(j).containsKey(l)){
						mapaCurrent.get(i).get(j).put(l,1);
						recursion2(mapaCurrent, i, j, l, turno,size);
						break;
					}
				}
				break;
		}
		
	}

	/*private void recursion(
			TreeMap<Integer, TreeMap<Integer, TreeMap<Integer, Integer>>> mapaCurrent,
			int i, int j, int h, boolean turno, int size) {
		switch (h){
			
			case 0:
				i--;
				if(i<0) break;
				mapaCurrent.get(i).get(j).put(h,1);
				for(int k=0;k<4;k++){
					if(!mapaCurrent.get(i).get(j).containsKey(k)){
						if(mapaCurrent.get(i).get(j).size()==3){
							mapaCurrent.get(i).get(j).put(k, 1);
							cuadritos.get(i).put(j, turno);
							recursion(mapaCurrent, i, j, k, turno, size);
						}
					}
				}
				break;
			case 1:
				j++;
				if(j>=size) break;
				mapaCurrent.get(i).get(j).put(h,1);
				for(int k=0;k<4;k++){
					if(!mapaCurrent.get(i).get(j).containsKey(k)){
						if(mapaCurrent.get(i).get(j).size()==3){
							mapaCurrent.get(i).get(j).put(k, 1);
							cuadritos.get(i).put(j, turno);
							recursion(mapaCurrent, i, j, k, turno,size);
						}
					}
				}
				break;
			case 2:
				i++;
				if(i>=size) break;
				mapaCurrent.get(i).get(j).put(h,1);
				for(int k=0;k<4;k++){
					if(!mapaCurrent.get(i).get(j).containsKey(k)){
						if(mapaCurrent.get(i).get(j).size()==3){
							mapaCurrent.get(i).get(j).put(k, 1);
							cuadritos.get(i).put(j, turno);
							recursion(mapaCurrent, i, j, k, turno,size);
						}
					}
				}
				break;
			case 3:
				j--;
				if(j<0) break;
				mapaCurrent.get(i).get(j).put(h,1);
				for(int k=0;k<4;k++){
					if(!mapaCurrent.get(i).get(j).containsKey(k)){
						if(mapaCurrent.get(i).get(j).size()==3){
							mapaCurrent.get(i).get(j).put(k, 1);
							cuadritos.get(i).put(j, turno);
							recursion(mapaCurrent, i, j, k, turno,size);
						}
					}
				}
				break;
		
		}
		
		
	}*/

	private String seleccionarAlAzar(int size,Square square, List<Square> posibilites, TreeMap<Integer, TreeMap<Integer, TreeMap<Integer, Integer>>> mapa) {
		//System.out.println(square.x+" "+square.y+" recursion");
		for(int i=0;i<4;i++){
			if(square.border[i]==0){
				switch(i){
					case 0:
						if(square.x==0) break;
						if(mapa.get(square.x-1).get(square.y).size()<2){
							return new String(square.x+":"+square.y+":top");
						}
						break;
					case 1:
						if(square.y==size-1) break;
						if(mapa.get(square.x).get(square.y+1).size()<2){
							return new String(square.x+":"+square.y+":right");
						}
						break;
					case 2:
						if(square.x==size-1) break;
						if(mapa.get(square.x+1).get(square.y).size()<2){
							return new String(square.x+":"+square.y+":bottom");
						}
						break;
					case 3:
						if(square.y==0) break;
						if(mapa.get(square.x).get(square.y-1).size()<2){
							return new String(square.x+":"+square.y+":left");
						}
						break;
						
				
				}
			}
		}
		if(posibilites.size()==1){
			square = posibilites.remove(0);
			String[] h = {"top","right","bottom","left"};
			for(int i=0;i<4;i++){
				if(square.border[i]==0){
					return new String(square.x+":"+square.y+":"+h[i]);
				}
			}
			return null;
		}else{
			Random r = new Random();
			square = posibilites.remove((int)(r.nextDouble()*posibilites.size()));
			return seleccionarAlAzar(size, square, posibilites, mapa);
		}
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		
	}

}

class Square{
	
	int x;
	int y;
	int[] border;
	
	public Square(int x, int y, int[] border){
		this.x = x;
		this.y = y;
		this.border = border.clone();
		
	}
	
}

class Node{
	
	TreeMap<Integer, TreeMap<Integer, TreeMap<Integer, Integer>>>  mapa;
	boolean turno;
	Node parent;
	int nivel;
	int nuestrocuadritos;
	int otroscuadritos;
	
	public Node(TreeMap<Integer, TreeMap<Integer, TreeMap<Integer, Integer>>> mapa2, Node parent, boolean turno, int nivel,int  nuestrocuadrito, int otroscuadritos){
		this.mapa = mapa2;
		this.parent = parent;
		this.turno = turno;
		this.nivel = nivel;
		this.nuestrocuadritos = nuestrocuadrito;
		this.otroscuadritos = otroscuadritos;
	}
	
}
