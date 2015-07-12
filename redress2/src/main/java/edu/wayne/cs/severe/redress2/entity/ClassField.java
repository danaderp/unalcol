package edu.wayne.cs.severe.redress2.entity;

import java.util.List;

public class ClassField {

	private List<String> types;
	private String name;

	public ClassField(String name, List<String> types) {
		this.name = name;
		this.types = types;
	}

	/**
	 * @return the types
	 */
	public List<String> getTypes() {
		return types;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return "ClassField [types=" + types + ", name=" + name + "]";
	}

}
