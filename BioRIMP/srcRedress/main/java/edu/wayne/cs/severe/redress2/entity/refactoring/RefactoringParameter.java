package edu.wayne.cs.severe.redress2.entity.refactoring;

import edu.wayne.cs.severe.redress2.entity.CodeObject;

/**
 * @author ojcchar
 * @version 1.0
 * @created 28-Mar-2014 17:27:28
 */
public class RefactoringParameter {

	private CodeObject codeObj;
	private CodeObjState objState = CodeObjState.EXISTS;

	public RefactoringParameter(CodeObject codeObj) {
		this.codeObj = codeObj;
	}

	public RefactoringParameter(CodeObject codeObj, CodeObjState objState) {
		this.codeObj = codeObj;
		this.objState = objState;
	}

	public CodeObject getCodeObj() {
		return codeObj;
	}

	public CodeObjState getObjState() {
		return objState;
	}

	@Override
	public String toString() {
		return codeObj + "|" + objState.toString();
	}

}// end RefactoringParameter