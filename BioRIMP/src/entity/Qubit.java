/**
 * 
 */
package entity;

import unalcol.instance.InstanceService;
import unalcol.reflect.service.ServiceProvider;
import unalcol.types.collection.bitarray.BitArray;
import unalcol.types.collection.bitarray.BitArrayInstance;

/**
 * @author Daavid
 *
 */
public class Qubit {
	
	
	private int BITARRAYLENGTH = 2;
	private BitArray bit_one_non_act = null;
	private BitArray bit_two_non_act = null;
	private BitArray bit_act = null;
	private BitArray activeState = null;
	
	
	
	//DDefine when  a bit is dominant
	
	public Qubit(boolean random){
		bit_one_non_act = new BitArray(BITARRAYLENGTH, random);
		bit_two_non_act = new BitArray(BITARRAYLENGTH, random);
		bit_act = new BitArray(BITARRAYLENGTH, random);
		activeState = new BitArray(BITARRAYLENGTH, random);
	}
	
	public BitArray getObservationQubit(){
		//how to creata observation from bit:one and bit:two
		//Checking the state of the bits
		//One (true) means active state so it has a superposition
		//Zero (false) means non active state so it has not superposition 
		
		String obser = new String();
		if(activeState.get(0)==false && activeState.get(1)==false){
			obser = bit_act.toString();
		}else{
			if(activeState.get(0)==true && activeState.get(1)==true){
				obser = bit_one_non_act.toString() + bit_two_non_act.toString();
			}else{
				if(activeState.get(0)==false && activeState.get(1)==true){
					obser = bit_act.toString().substring(0, 1) + bit_two_non_act.toString();
				}
				else{
					if(activeState.get(0)==true && activeState.get(1)==false){
						obser = bit_act.toString().substring(1) +bit_one_non_act.toString();
					}
				}
			}
		}
		
		
		return new BitArray(obser);
	}

}
