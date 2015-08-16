package edu.wayne.cs.severe.redress2.entity;

public class AttributeDeclaration extends CodeObject {

	private String objName;

	public AttributeDeclaration(String objName) {
		this.objName = objName;
	}

	public String getObjName() {
		return objName;
	}

	@Override
	public String toString() {
		return objName;
	}

}
