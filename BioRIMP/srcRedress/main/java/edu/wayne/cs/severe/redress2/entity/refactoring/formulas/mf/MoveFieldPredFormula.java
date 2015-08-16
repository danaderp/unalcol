package edu.wayne.cs.severe.redress2.entity.refactoring.formulas.mf;

import edu.wayne.cs.severe.redress2.entity.AttributeDeclaration;
import edu.wayne.cs.severe.redress2.entity.TypeDeclaration;
import edu.wayne.cs.severe.redress2.entity.refactoring.RefactoringOperation;
import edu.wayne.cs.severe.redress2.entity.refactoring.formulas.PredictionFormula;

public abstract class MoveFieldPredFormula extends PredictionFormula {
	protected TypeDeclaration getTargetClass(RefactoringOperation ref) {
		return (TypeDeclaration) ref.getParams().get("tgt").get(0).getCodeObj();
	}

	public AttributeDeclaration getAttribute(RefactoringOperation ref) {
		return (AttributeDeclaration) ref.getParams().get("fld").get(0)
				.getCodeObj();
	}

	public TypeDeclaration getSourceClass(RefactoringOperation ref) {
		return (TypeDeclaration) ref.getParams().get("src").get(0).getCodeObj();
	}
}
