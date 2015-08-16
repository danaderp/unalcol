package edu.wayne.cs.severe.redress2.entity.refactoring.json;

import java.util.List;

public class OBSERVRefactorings {

	private List<OBSERVRefactoring> refactorings;

	public List<OBSERVRefactoring> getRefactorings() {
		return refactorings;
	}

	@Override
	public String toString() {
		return "OBSERVRefactorings [refactorings=" + refactorings + "]";
	}
}
