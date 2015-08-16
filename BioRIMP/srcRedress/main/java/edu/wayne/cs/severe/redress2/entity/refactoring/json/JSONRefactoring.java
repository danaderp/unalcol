package edu.wayne.cs.severe.redress2.entity.refactoring.json;

import java.util.List;

public class JSONRefactoring {

	private String type;
	private List<JSONRefParam> params;
	private List<JSONRefactoring> subRefs;

	public String getType() {
		return type;
	}

	public List<JSONRefParam> getParams() {
		return params;
	}

	/**
	 * @return the subRefs
	 */
	public List<JSONRefactoring> getSubRefs() {
		return subRefs;
	}

	@Override
	public String toString() {
		return "JSONRefactoring [type=" + type + ", params=" + params
				+ ", subRefs=" + subRefs + "]";
	}

}
