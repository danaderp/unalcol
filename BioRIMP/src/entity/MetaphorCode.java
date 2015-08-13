/**
 * 
 */
package entity;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;

import edu.wayne.cs.severe.redress2.controller.HierarchyBuilder;
import edu.wayne.cs.severe.redress2.controller.MetricUtils;
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
	private HashMap<BitArray,TypeDeclaration> mapClass=
			new HashMap<BitArray,TypeDeclaration>();
	
	public MetaphorCode() {
	}
	
	//Method for assigning a bit representation to each Class
	public HashMap<BitArray,TypeDeclaration> bitAssignerClass(int tamBitArray){
		BitArray array; 
		int i=0;
		for (TypeDeclaration typeDcl : sysTypeDcls) {
			array = new BitArray(20,false);
			BitArrayConverter.setNumber(array, 0, tamBitArray, i++);
			mapClass.put(array, typeDcl);
		}
		return mapClass;
	}
	
	//Get the complete list of Methods of a specific class
	public LinkedHashSet<String> getMethodsFromClass(TypeDeclaration typeDcl) {
		LinkedHashSet<String> methods = new LinkedHashSet<String>();
		try {
			methods = MetricUtils.getMethods(typeDcl);
			
		} catch (Exception e) {
			System.out.println("Error for class: " + typeDcl.getQualifiedName()
			+ " - " + e.getMessage());
			methods = null;
		}
		
		return methods;
	
	}
	
	//Get the complete list of Fields of a specific class
	public HashSet<String> getFieldsFromClass(TypeDeclaration typeDcl) {
		HashSet<String> fields = new HashSet<String>();
		try {
			fields = MetricUtils.getFields(typeDcl);
			
		} catch (Exception e) {
			System.out.println("Error for class: " + typeDcl.getQualifiedName()
			+ " - " + e.getMessage());
			fields = null;
		}
		
		return fields;
	
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
