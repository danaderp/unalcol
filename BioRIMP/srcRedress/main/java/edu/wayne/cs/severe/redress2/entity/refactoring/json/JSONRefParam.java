package edu.wayne.cs.severe.redress2.entity.refactoring.json;

import java.util.List;

public class JSONRefParam {

	private String name;
	private List<String> value;

	public String getName() {
		return name;
	}

	public List<String> getValue() {
		return value;
	}

	@Override
	public String toString() {
		return "JSONRefParam [name=" + name + ", values=" + value + "]";
	}

}
