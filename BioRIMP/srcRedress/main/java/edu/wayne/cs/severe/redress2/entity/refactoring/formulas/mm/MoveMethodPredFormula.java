package edu.wayne.cs.severe.redress2.entity.refactoring.formulas.mm;

import edu.wayne.cs.severe.redress2.entity.MethodDeclaration;
import edu.wayne.cs.severe.redress2.entity.TypeDeclaration;
import edu.wayne.cs.severe.redress2.entity.refactoring.RefactoringOperation;
import edu.wayne.cs.severe.redress2.entity.refactoring.formulas.PredictionFormula;

/**
 * @author ojcchar
 * @version 1.0
 * @created 28-Mar-2014 17:27:28
 */
public abstract class MoveMethodPredFormula extends PredictionFormula {

	public TypeDeclaration getTargetClass(RefactoringOperation ref) {
		return (TypeDeclaration) ref.getParams().get("tgt").get(0).getCodeObj();
	}

	public MethodDeclaration getMethod(RefactoringOperation ref) {
		return (MethodDeclaration) ref.getParams().get("mtd").get(0)
				.getCodeObj();
	}

	public TypeDeclaration getSourceClass(RefactoringOperation ref) {
		return (TypeDeclaration) ref.getParams().get("src").get(0).getCodeObj();
	}

}// end MoveMethodPredFormula