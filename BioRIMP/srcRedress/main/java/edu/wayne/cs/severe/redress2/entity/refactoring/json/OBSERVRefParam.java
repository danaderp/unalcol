package edu.wayne.cs.severe.redress2.entity.refactoring.json;

import java.util.List;

public class OBSERVRefParam {

	private String name;
	private List<String> value;

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name=name;
	}
	
	

	public List<String> getValue() {
		return value;
	}
	
	public void setValue(List<String> value) {
		this.value=value;
	}

	@Override
	public String toString() {
		return "OBSERVRefParam [name=" + name + ", values=" + value + "]";
	}

}
