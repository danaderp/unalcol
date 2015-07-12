package edu.wayne.cs.severe.redress2.entity.refactoring.formulas.pdm;

import java.util.ArrayList;
import java.util.List;

import edu.wayne.cs.severe.redress2.entity.CodeObject;
import edu.wayne.cs.severe.redress2.entity.MethodDeclaration;
import edu.wayne.cs.severe.redress2.entity.TypeDeclaration;
import edu.wayne.cs.severe.redress2.entity.refactoring.RefactoringOperation;
import edu.wayne.cs.severe.redress2.entity.refactoring.RefactoringParameter;
import edu.wayne.cs.severe.redress2.entity.refactoring.formulas.PredictionFormula;

public abstract class PushDownPredFormula extends PredictionFormula {

	protected TypeDeclaration getSourceClass(RefactoringOperation ref) {
		return (TypeDeclaration) ref.getParams().get("src").get(0).getCodeObj();
	}

	protected List<TypeDeclaration> getTargetClasses(RefactoringOperation ref) {

		List<RefactoringParameter> params = ref.getParams().get("tgt");
		List<TypeDeclaration> classes = new ArrayList<TypeDeclaration>();

		for (RefactoringParameter par : params) {
			CodeObject codeObj = par.getCodeObj();
			classes.add((TypeDeclaration) codeObj);
		}

		return classes;
	}

	protected MethodDeclaration getMethod(RefactoringOperation ref) {
		return (MethodDeclaration) ref.getParams().get("mtd").get(0)
				.getCodeObj();
	}
}
