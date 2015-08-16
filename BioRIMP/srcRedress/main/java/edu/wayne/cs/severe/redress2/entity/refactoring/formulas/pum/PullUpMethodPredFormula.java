package edu.wayne.cs.severe.redress2.entity.refactoring.formulas.pum;

import java.util.ArrayList;
import java.util.List;

import edu.wayne.cs.severe.redress2.entity.CodeObject;
import edu.wayne.cs.severe.redress2.entity.MethodDeclaration;
import edu.wayne.cs.severe.redress2.entity.TypeDeclaration;
import edu.wayne.cs.severe.redress2.entity.refactoring.RefactoringOperation;
import edu.wayne.cs.severe.redress2.entity.refactoring.RefactoringParameter;
import edu.wayne.cs.severe.redress2.entity.refactoring.formulas.PredictionFormula;

/**
 * Abstract class that represents a prediction formula for the Pull Up Method
 * refactoring
 * 
 * @author ojcchar
 * 
 */
public abstract class PullUpMethodPredFormula extends PredictionFormula {

	/**
	 * Get the super class of the Pull Up Method refactoring
	 * 
	 * @param ref
	 *            the refactoring operation
	 * @return the super class
	 */
	protected TypeDeclaration getTargetClass(RefactoringOperation ref) {
		return (TypeDeclaration) ref.getParams().get("tgt").get(0).getCodeObj();
	}

	/**
	 * Get the list of subclasses of the Pull Up Method refactoring
	 * 
	 * @param ref
	 *            the refactoring operation
	 * @return the list of subclasses
	 */
	protected List<TypeDeclaration> getSourceClasses(RefactoringOperation ref) {

		List<RefactoringParameter> params = ref.getParams().get("src");
		List<TypeDeclaration> classes = new ArrayList<TypeDeclaration>();

		for (RefactoringParameter par : params) {
			CodeObject codeObj = par.getCodeObj();
			classes.add((TypeDeclaration) codeObj);
		}

		return classes;
	}

	/**
	 * Get the method to be pulled up
	 * 
	 * @param ref
	 *            the refactoring operation
	 * @return the method to be pulled up
	 */
	protected MethodDeclaration getMethod(RefactoringOperation ref) {
		return (MethodDeclaration) ref.getParams().get("mtd").get(0)
				.getCodeObj();
	}
}
