/**
 * 
 */
package controller;

import java.util.ArrayList;

import edu.wayne.cs.severe.redress2.entity.refactoring.RefactoringOperation;
import edu.wayne.cs.severe.redress2.entity.refactoring.json.OBSERVRefParam;
import edu.wayne.cs.severe.redress2.entity.refactoring.json.OBSERVRefactoring;
import edu.wayne.cs.severe.redress2.entity.refactoring.json.OBSERVRefactorings;
import entity.MetaphorCode;
import entity.QubitArray;
import unalcol.evolution.GrowingFunction;
import unalcol.types.collection.bitarray.BitArray;
import unalcol.types.collection.bitarray.BitArrayConverter;
import java.util.List;

/**
 * @author Daavid
 *
 */
public class GrowingFunctionQubit 
	extends GrowingFunction<QubitArray,List<RefactoringOperation>>{
	
	private MetaphorCode metaphor;
	public GrowingFunctionQubit(MetaphorCode metaphor) { 
		this.metaphor=metaphor;
	}
	
	public List<RefactoringOperation> get(QubitArray genome) { 
		
		//return genomeObservation
		//mapping from genome to refactor
		//First mapping the refactors from the observation genome
		List<RefactoringOperation> oper=null;
		mappingRefactor(genome.getGenObservation());
		return oper; 
		
	}
	
	private OBSERVRefactorings mappingRefactor(BitArray observation){
		
		
		int max = observation.size();
		int min = 20;
		//String [] oper = new String[max / min];
		OBSERVRefactorings oper = new OBSERVRefactorings();
		int map;
		int num;
		int len;
		for(int i=0;i<=max;i=i+min){
			num = BitArrayConverter.getNumber(observation, i, min);
			map = BitArrayConverter.getNumber(observation, i, min) %
					(Refactoring.values().length-1);
			metaphor.bitAssignerClass(min);
			/*
			oper.getRefactorings().add(
					new OBSERVRefactoring(
					Refactoring.values()[map].name(),
					mappingParams(Refactoring.values()[map].name())
					)
					);
			OBSERVRefactoring e = new OBSERVRefactoring(
					Refactoring.values()[map].name(),
					mappingParams(Refactoring.values()[map].name())
					);
			oper.getRefactorings().add(e);*/
			
		}
		return oper;
	}
	
	//Params per Type of Refactoring
	
	private List<OBSERVRefParam> mappingParams(String type){
		List<OBSERVRefParam> resultList = new ArrayList<OBSERVRefParam>();
		
		if(type.equals(Refactoring.pullUpField.name())
				|| type.equals(Refactoring.moveField.name())
				|| type.equals(Refactoring.pushDownField.name())
			){
			//Generating Source Class
			//Generating Fields of Source Class
			//Generating Target Class
		    }else{
		    	if(type.equals(Refactoring.moveMethod.name())
						|| type.equals(Refactoring.replaceMethodObject.name())
						|| type.equals(Refactoring.pushDownMethod.name())
						|| type.equals(Refactoring.pullUpMethod.name())
					){
					//Generating Source Class
		    		//Generating Methods of Source Class
					//Generating Target Class
				    }else{
				    	if(type.equals(Refactoring.pullUpField.name())
								|| type.equals(Refactoring.moveField.name())
								|| type.equals(Refactoring.pushDownField.name())
							){
							//Generating Source Class
				    		//Generating Methods of Source Class
							
						    }else{
						    	if(type.equals(Refactoring.pullUpField.name())
										|| type.equals(Refactoring.moveField.name())
										|| type.equals(Refactoring.pushDownField.name())
									){
									//Generating Source Class
									//Generating Fields of Source Class
									//Generating Target Class
								    }else{
								    	if(type.equals(Refactoring.pullUpField.name())
												|| type.equals(Refactoring.moveField.name())
												|| type.equals(Refactoring.pushDownField.name())
											){
											//Generating Source Class
											//Generating Fields of Source Class
											//Generating Target Class
										    }
								    }
						    }
				    }
		    }
			
		//Generating Source Class
		//Generating Fields of Source Class
		//Generating Methods of Source Class
		//Generating Target Class
		return resultList;
	}
	
	private enum Refactoring{
		pullUpField, moveMethod, replaceMethodObject, replaceDelegationInheritance,
		moveField, extractMethod, pushDownMethod, replaceInheritanceDelegation, 
		inlineMethod, pullUpMethod, pushDownField, extractClass
	}
	/*
	public QubitArray set(List<RefactoringOperation> thing) { 
		return (QubitArray)thing; 
	}*/
	

}
