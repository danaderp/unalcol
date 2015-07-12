package edu.wayne.cs.severe.redress2.parser;

import java.util.ArrayList;
import java.util.List;

import edu.wayne.cs.severe.redress2.entity.CompilationUnit;
import edu.wayne.cs.severe.redress2.entity.TypeDeclaration;
import edu.wayne.cs.severe.redress2.exception.CompilUnitException;

/**
 * This is a compilation unit parser. It parses the srcML files to get the
 * information on those.
 * 
 * @author ojcchar
 * 
 */
public abstract class CompilUnitParser {

	List<TypeDeclaration> getAllTypes(List<TypeDeclaration> sysTypeDcls) {
		List<TypeDeclaration> allTypes = new ArrayList<TypeDeclaration>();

		if (sysTypeDcls == null) {
			return allTypes;
		}

		for (TypeDeclaration typeDcl : sysTypeDcls) {
			allTypes.add(typeDcl);
			List<TypeDeclaration> allTypes2 = getAllTypes(typeDcl.getSubTypes());
			allTypes.addAll(allTypes2);
		}
		return allTypes;
	}

	/**
	 * Parses the compilation units and return the list of type declarations
	 * implemented in the units
	 * 
	 * @param compUnits
	 *            the compilation units
	 * @return the list of type declarations
	 * @throws CompilUnitException
	 *             if something goes wrong
	 */
	public abstract List<TypeDeclaration> getClasses(
			List<CompilationUnit> compUnits) throws CompilUnitException;

}
