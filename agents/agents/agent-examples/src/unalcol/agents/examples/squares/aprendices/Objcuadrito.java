package unalcol.agents.examples.squares.aprendices;

import unalcol.types.collection.vector.Vector;

public class Objcuadrito {
	private int valor=0;
	Vector<String> v = new Vector();
	public Objcuadrito(int valor, Vector<String> v) {
		this.valor = valor;
		this.v = v;
	}
	public Objcuadrito() {
		// TODO Auto-generated constructor stub
	}
	public int getValor() {
		return valor;
	}
	public void setValor(int valor) {
		this.valor = valor;
	}
	public Vector<String> getV() {
		return v;
	}
	public void setV(Vector<String> v) {
		this.v = v;
	}
	
}
