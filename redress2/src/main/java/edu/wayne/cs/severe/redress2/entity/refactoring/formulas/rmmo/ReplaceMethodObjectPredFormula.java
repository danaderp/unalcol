package edu.wayne.cs.severe.redress2.entity.refactoring.formulas.rmmo;

import edu.wayne.cs.severe.redress2.entity.MethodDeclaration;
import edu.wayne.cs.severe.redress2.entity.TypeDeclaration;
import edu.wayne.cs.severe.redress2.entity.refactoring.RefactoringOperation;
import edu.wayne.cs.severe.redress2.entity.refactoring.formulas.PredictionFormula;

public abstract class ReplaceMethodObjectPredFormula extends PredictionFormula {

	protected TypeDeclaration getTargetClass(RefactoringOperation ref) {
		return (TypeDeclaration) ref.getParams().get("tgt").get(0).getCodeObj();
	}

	protected MethodDeclaration getMethod(RefactoringOperation ref) {
		return (MethodDeclaration) ref.getParams().get("mtd").get(0)
				.getCodeObj();
	}

	protected TypeDeclaration getSourceClass(RefactoringOperation ref) {
		return (TypeDeclaration) ref.getParams().get("src").get(0).getCodeObj();
	}
}
