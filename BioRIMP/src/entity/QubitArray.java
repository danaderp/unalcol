/**
 * 
 */
package entity;

import unalcol.random.integer.UniformIntegerGenerator;
import unalcol.types.collection.bitarray.BitArray;

/**
 * @author Daavid
 *
 */
public class QubitArray implements Cloneable {
	/**
	  * Qubit array used to store the Qubits
	  */
	private Qubit[] data = null;
	
	private BitArray genObservation = null;
	//concatenate all observations from Qubit
	
	/**
	 * The number of Qubits in the Qubit array
	 */
	private int n = 0;
	
	/**
	 * Constructor: Creates a bit array of n bits, in a random way or with all bit in false according to the randomly argument
	 * @param n The size of the bit array
	 * 
	 */
	public  QubitArray(int n, boolean random) {
	  this.n = n;
	  data = new Qubit[n];
	  String source = new String();
	  
	  for(int i=0; i < n; i++){
		  data[i] = new Qubit(random);
		  source = source + data[i].getObservationQubit().toString();
	  }
	  
	  genObservation = new BitArray(source);
	}
	
	/**
	   * Gets the dimension of the bits array
	   * @return The dimension
	   */
	  public int dimension() {
		  return n;
	  }

	public BitArray getGenObservation() {
		return genObservation;
	}

	public void setGenObservation(BitArray genObservation) {
		this.genObservation = genObservation;
	}
	  
	  

}
