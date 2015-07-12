package edu.wayne.cs.severe.redress2.utils;

import java.util.ArrayList;
import java.util.List;

import edu.wayne.cs.severe.redress2.entity.AttributeDeclaration;
import edu.wayne.cs.severe.redress2.entity.MethodDeclaration;
import edu.wayne.cs.severe.redress2.entity.refactoring.RefactoringOperation;
import edu.wayne.cs.severe.redress2.entity.refactoring.formulas.mf.MoveFieldPredFormula;
import edu.wayne.cs.severe.redress2.entity.refactoring.formulas.mm.MoveMethodPredFormula;
import edu.wayne.cs.severe.redress2.entity.refactoring.opers.MoveField;
import edu.wayne.cs.severe.redress2.entity.refactoring.opers.MoveMethod;
import edu.wayne.cs.severe.redress2.entity.refactoring.opers.RefactoringType;

public class ExtractClassUtils {
	

	public static List<AttributeDeclaration> getAttributesToMove(RefactoringOperation ref,
			MoveFieldPredFormula preFormMF) {

		List<AttributeDeclaration> attrs = new ArrayList<AttributeDeclaration>();
		List<RefactoringOperation> subRefs = ref.getSubRefs();
		for (RefactoringOperation subRef : subRefs) {
			if (subRef.getRefType() instanceof MoveField) {
				AttributeDeclaration attr = preFormMF.getAttribute(subRef);
				attrs.add(attr);
			}
		}
		return attrs;
	}


	public static List<MethodDeclaration> getMethodsToMove(RefactoringOperation ref,
			MoveMethodPredFormula preFormMM) {

		List<MethodDeclaration> methods = new ArrayList<MethodDeclaration>();
		List<RefactoringOperation> subRefs = ref.getSubRefs();
		for (RefactoringOperation subRef : subRefs) {
			if (subRef.getRefType() instanceof MoveMethod) {
				MethodDeclaration method = preFormMM.getMethod(subRef);
				methods.add(method);
			}
		}
		return methods;
	}

	public static double numFieldsToMove(RefactoringOperation ref) {
		List<RefactoringOperation> subRefs = ref.getSubRefs();

		if (subRefs == null) {
			return 0;
		}

		int num = 0;

		// for each sub-refactoring
		for (RefactoringOperation subRef : subRefs) {

			RefactoringType refType = subRef.getRefType();
			// it is a move field
			if (refType instanceof MoveField) {
				++num;
			}
		}

		return num;
	}

	public static double numMethodsToMove(RefactoringOperation ref) {
		List<RefactoringOperation> subRefs = ref.getSubRefs();

		if (subRefs == null) {
			return 0;
		}

		int num = 0;

		// for each sub-refactoring
		for (RefactoringOperation subRef : subRefs) {

			RefactoringType refType = subRef.getRefType();
			// it is a move method
			if (refType instanceof MoveMethod) {
				++num;
			}
		}

		return num;
	}
}
