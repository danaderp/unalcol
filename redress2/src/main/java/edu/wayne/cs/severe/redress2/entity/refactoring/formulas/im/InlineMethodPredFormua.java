package edu.wayne.cs.severe.redress2.entity.refactoring.formulas.im;

import edu.wayne.cs.severe.redress2.entity.TypeDeclaration;
import edu.wayne.cs.severe.redress2.entity.refactoring.RefactoringOperation;
import edu.wayne.cs.severe.redress2.entity.refactoring.formulas.PredictionFormula;

public abstract class InlineMethodPredFormua extends PredictionFormula {

	protected TypeDeclaration getSourceClass(RefactoringOperation ref) {
		return (TypeDeclaration) ref.getParams().get("src").get(0).getCodeObj();
	}

}
