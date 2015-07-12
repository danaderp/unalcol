package edu.wayne.cs.severe.redress2.entity;

import java.util.List;

public class TypeDeclaration extends CodeObject {

	private String pack;
	private String name;
	private int type = 0;
	private List<TypeDeclaration> subTypes;
	private boolean hasParams = false;

	private CompilationUnit compUnit;

	public TypeDeclaration(String pack, String name, boolean hasParams,
			CompilationUnit compUnit) {
		this.pack = pack;
		this.name = name;
		this.hasParams = hasParams;
		this.compUnit = compUnit;
	}

	public TypeDeclaration(String pack, String name) {
		this.pack = pack;
		this.name = name;
	}

	public String getPack() {
		return pack;
	}

	public void setPack(String pack) {
		this.pack = pack;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<TypeDeclaration> getSubTypes() {
		return subTypes;
	}

	public void setSubTypes(List<TypeDeclaration> subTypes) {
		this.subTypes = subTypes;
	}

	public String getQualifiedName() {
		return this.pack + this.name;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public boolean isHasParams() {
		return hasParams;
	}

	public void setHasParams(boolean hasParams) {
		this.hasParams = hasParams;
	}

	/**
	 * @return the compUnit
	 */
	public CompilationUnit getCompUnit() {
		return compUnit;
	}

	/**
	 * @param compUnit
	 *            the compUnit to set
	 */
	public void setCompUnit(CompilationUnit compUnit) {
		this.compUnit = compUnit;
	}

	@Override
	public String toString() {
		return "TypeDeclaration [name=" + name + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((getQualifiedName() == null) ? 0 : getQualifiedName()
						.hashCode());
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
		TypeDeclaration other = (TypeDeclaration) obj;
		if (getQualifiedName() == null) {
			if (other.getQualifiedName() != null)
				return false;
		} else if (!getQualifiedName().equals(other.getQualifiedName()))
			return false;
		return true;
	}

}
