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
	/*
	private List<OBSERVRefParam> mappingParams(String type){
		return new List<OBSERVRefParam>();
	}*/
	
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
