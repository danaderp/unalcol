/**
 * 
 */
package entity;

import java.util.HashMap;
import java.util.List;

import edu.wayne.cs.severe.redress2.controller.HierarchyBuilder;
import edu.wayne.cs.severe.redress2.entity.TypeDeclaration;
import unalcol.types.collection.bitarray.BitArray;
import unalcol.types.collection.bitarray.BitArrayConverter;

/**
 * @author Daavid
 *
 */
public class MetaphorCode {
	
	private HierarchyBuilder builder;
	private List<TypeDeclaration> sysTypeDcls;
	private HashMap<TypeDeclaration,BitArray> mapClass=
			new HashMap<TypeDeclaration,BitArray>();
	
	public MetaphorCode() {
	}
	
	//Method for assigning a bit representation to each parameter
	public HashMap<TypeDeclaration,BitArray> bitAssignerClass(int tamBitArray){
		BitArray array; 
		int i=0;
		for (TypeDeclaration typeDcl : sysTypeDcls) {
			array = new BitArray(20,false);
			BitArrayConverter.setNumber(array, 0, tamBitArray, i++);
			mapClass.put(typeDcl, array);
		}
		return mapClass;
	}
	
	public HierarchyBuilder getMetaphorBuilder() {
		return builder;
	}

	public void setMetaphorBuilder(HierarchyBuilder builder) {
		this.builder = builder;
	}


	public HierarchyBuilder getBuilder() {
		return builder;
	}


	public void setBuilder(HierarchyBuilder builder) {
		this.builder = builder;
	}


	public List<TypeDeclaration> getSysTypeDcls() {
		return sysTypeDcls;
	}


	public void setSysTypeDcls(List<TypeDeclaration> sysTypeDcls) {
		this.sysTypeDcls = sysTypeDcls;
	}
	
	
}
