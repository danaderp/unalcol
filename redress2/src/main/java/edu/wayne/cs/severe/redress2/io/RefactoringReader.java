package edu.wayne.cs.severe.redress2.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

import edu.wayne.cs.severe.redress2.controller.HierarchyBuilder;
import edu.wayne.cs.severe.redress2.entity.ProgLang;
import edu.wayne.cs.severe.redress2.entity.TypeDeclaration;
import edu.wayne.cs.severe.redress2.entity.refactoring.RefactoringOperation;
import edu.wayne.cs.severe.redress2.entity.refactoring.RefactoringParameter;
import edu.wayne.cs.severe.redress2.entity.refactoring.json.JSONRefactoring;
import edu.wayne.cs.severe.redress2.entity.refactoring.json.JSONRefactorings;
import edu.wayne.cs.severe.redress2.entity.refactoring.opers.ExtractClass;
import edu.wayne.cs.severe.redress2.entity.refactoring.opers.ExtractMethod;
import edu.wayne.cs.severe.redress2.entity.refactoring.opers.InlineMethod;
import edu.wayne.cs.severe.redress2.entity.refactoring.opers.MoveField;
import edu.wayne.cs.severe.redress2.entity.refactoring.opers.MoveMethod;
import edu.wayne.cs.severe.redress2.entity.refactoring.opers.PullUpField;
import edu.wayne.cs.severe.redress2.entity.refactoring.opers.PullUpMethod;
import edu.wayne.cs.severe.redress2.entity.refactoring.opers.PushDownField;
import edu.wayne.cs.severe.redress2.entity.refactoring.opers.PushDownMethod;
import edu.wayne.cs.severe.redress2.entity.refactoring.opers.RefactoringType;
import edu.wayne.cs.severe.redress2.entity.refactoring.opers.ReplaceDelegationInheritance;
import edu.wayne.cs.severe.redress2.entity.refactoring.opers.ReplaceInheritanceDelegation;
import edu.wayne.cs.severe.redress2.entity.refactoring.opers.ReplaceMethodObject;
import edu.wayne.cs.severe.redress2.exception.ReadException;
import edu.wayne.cs.severe.redress2.exception.RefactoringException;
import edu.wayne.cs.severe.redress2.utils.ExceptionUtils;

/**
 * @author ojcchar
 * @version 1.0
 * @created 28-Mar-2014 17:27:28
 */
public class RefactoringReader {

	private HashMap<String, RefactoringType> refMappings = null;

	private static Logger LOGGER = LoggerFactory
			.getLogger(RefactoringReader.class);

	public RefactoringReader(List<TypeDeclaration> sysTypeDcls, ProgLang lang,
			HierarchyBuilder builder) {

		refMappings = new HashMap<String, RefactoringType>();

		String[] types = { "pullUpField", "moveMethod", "replaceMethodObject",
				"moveField", "extractClass", "replaceDelegationInheritance",
				"extractMethod", "pushDownMethod",
				"replaceInheritanceDelegation", "inlineMethod", "pullUpMethod",
				"pushDownField" };
		RefactoringType[] typeObjs = { new PullUpField(sysTypeDcls),
				new MoveMethod(sysTypeDcls, builder),
				new ReplaceMethodObject(sysTypeDcls, lang, builder),
				new MoveField(sysTypeDcls, lang),
				new ExtractClass(sysTypeDcls, lang, builder),
				new ReplaceDelegationInheritance(sysTypeDcls, builder),
				new ExtractMethod(sysTypeDcls, lang),
				new PushDownMethod(sysTypeDcls, builder),
				new ReplaceInheritanceDelegation(sysTypeDcls, builder),
				new InlineMethod(sysTypeDcls, lang),
				new PullUpMethod(sysTypeDcls, lang, builder),
				new PushDownField(sysTypeDcls, lang) };

		for (int i = 0; i < types.length; i++) {
			String type = types[i];
			refMappings.put(type, typeObjs[i]);
		}
	}

	/**
	 * 
	 * @param refFile
	 * @throws FileNotFoundException
	 * @throws JsonIOException
	 * @throws JsonSyntaxException
	 */
	public List<RefactoringOperation> getRefactOperations(File refFile)
			throws ReadException {

		try {
			Gson gson = new Gson();
			JSONRefactorings jsonParams = gson.fromJson(
					new FileReader(refFile), JSONRefactorings.class);

			List<JSONRefactoring> refs = jsonParams.getRefactorings();
			List<RefactoringOperation> opers = new ArrayList<RefactoringOperation>();

			int id = 1;
			for (JSONRefactoring ref : refs) {
				RefactoringOperation oper = getRefactoringOper(ref,
						String.valueOf((id++)));
				if (oper != null) {
					opers.add(oper);
				}
			}

			return opers;
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			ReadException ex = new ReadException(e.getMessage());
			ExceptionUtils.addStackTrace(e, ex);
			throw ex;
		}
	}

	private RefactoringOperation getRefactoringOper(JSONRefactoring ref,
			String id) throws RefactoringException {
		RefactoringType refType = refMappings.get(ref.getType());

		if (refType == null) {
			throw new RefactoringException("The refactoring \"" + ref.getType()
					+ "\" is not supported");
		}

		HashMap<String, List<RefactoringParameter>> params = refType
				.getRefactoringParams(ref.getParams());
		List<RefactoringOperation> subRefs = getSubRefs(ref.getSubRefs(), id);
		RefactoringOperation oper = new RefactoringOperation(refType, params,
				refType.getAcronym() + "-" + id, subRefs);
		return oper;
	}

	private List<RefactoringOperation> getSubRefs(
			List<JSONRefactoring> jsonSubRefs, String id)
			throws RefactoringException {

		if (jsonSubRefs == null) {
			return null;
		}

		List<RefactoringOperation> subRefs = new ArrayList<RefactoringOperation>();
		int i = 0;
		for (JSONRefactoring jSubRef : jsonSubRefs) {
			subRefs.add(getRefactoringOper(jSubRef, id + "-" + (i++)));
		}

		return subRefs;
	}

}// end RefactoringReader