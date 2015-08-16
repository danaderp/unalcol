package edu.wayne.cs.severe.redress2.entity;

/**
 * @author ojcchar
 * @version 1.0
 * @created 28-Mar-2014 17:27:27
 */
public class MethodDeclaration extends CodeObject {

	private String objName;

	public MethodDeclaration(String objName) {
		this.objName = objName;
	}

	public String getObjName() {
		return objName;
	}

	@Override
	public String toString() {
		return objName;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((objName == null) ? 0 : objName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MethodDeclaration other = (MethodDeclaration) obj;
		if (objName == null) {
			if (other.objName != null)
				return false;
		} else if (!objName.equals(other.objName))
			return false;
		return true;
	}
	
	
	

}// end MethodDeclaration