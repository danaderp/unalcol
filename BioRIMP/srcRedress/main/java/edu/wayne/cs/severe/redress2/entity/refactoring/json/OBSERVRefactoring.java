package edu.wayne.cs.severe.redress2.entity.refactoring.json;

import java.util.List;

public class OBSERVRefactoring {

	private String type;
	private List<OBSERVRefParam> params;
	private List<OBSERVRefactoring> subRefs;
	
	
	
	public OBSERVRefactoring(String type, List<OBSERVRefParam> params) {
		this.type = type;
		this.params = params;
		this.subRefs = null;
	}

	public OBSERVRefactoring(String type, List<OBSERVRefParam> params, List<OBSERVRefactoring> subRefs) {
		this.type = type;
		this.params = params;
		this.subRefs = subRefs;
	}

	public String getType() {
		return type;
	}

	public List<OBSERVRefParam> getParams() {
		return params;
	}

	/**
	 * @return the subRefs
	 */
	public List<OBSERVRefactoring> getSubRefs() {
		return subRefs;
	}
	
	public void setType(String type){
		this.type=type;
	}
	
	public void setParams(List<OBSERVRefParam> params){
		this.params=params;
	}
	
	public void setSubRefs(List<OBSERVRefactoring> subRefs){
		this.subRefs=subRefs;
	}
	
	
	@Override
	public String toString() {
		return "OBSERVRefactoring [type=" + type + ", params=" + params
				+ ", subRefs=" + subRefs + "]";
	}

}
