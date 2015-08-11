package edu.wayne.cs.severe.redress2.entity.refactoring.opers;

import java.util.HashMap;
import java.util.List;

import edu.wayne.cs.severe.redress2.entity.TypeDeclaration;
import edu.wayne.cs.severe.redress2.entity.refactoring.RefactoringParameter;
import edu.wayne.cs.severe.redress2.entity.refactoring.formulas.PredictionFormula;
import edu.wayne.cs.severe.redress2.entity.refactoring.json.JSONRefParam;
import edu.wayne.cs.severe.redress2.entity.refactoring.json.OBSERVRefParam;
import edu.wayne.cs.severe.redress2.exception.RefactoringException;

/**
 * @author ojcchar
 * @version 1.0
 * @created 28-Mar-2014 17:27:28
 */
public abstract class RefactoringType {

	List<TypeDeclaration> sysTypeDcls;
	protected HashMap<String, PredictionFormula> formulas = new HashMap<String, PredictionFormula>();

	public RefactoringType(List<TypeDeclaration> sysTypeDcls) {
		this.sysTypeDcls = sysTypeDcls;
	}

	public abstract String getAcronym();

	/**
	 * 
	 * @param metrAcronym
	 */
	public PredictionFormula getPredFormula(String metrAcronym) {
		return formulas.get(metrAcronym);
	}

	public abstract HashMap<String, List<RefactoringParameter>> getRefactoringParams(
			List<JSONRefParam> jsonParams) throws RefactoringException;

	//danaderp
	//new method for controlling OBSERVRefParam
	public abstract HashMap<String, List<RefactoringParameter>> getOBSERVRefactoringParams(
			List<OBSERVRefParam> jsonParams) throws RefactoringException;


}// end RefactoringType