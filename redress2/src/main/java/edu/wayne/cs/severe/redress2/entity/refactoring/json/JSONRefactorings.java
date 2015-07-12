package edu.wayne.cs.severe.redress2.entity.refactoring.json;

import java.util.List;

public class JSONRefactorings {

	private List<JSONRefactoring> refactorings;

	public List<JSONRefactoring> getRefactorings() {
		return refactorings;
	}

	@Override
	public String toString() {
		return "JSONRefactorings [refactorings=" + refactorings + "]";
	}
}
