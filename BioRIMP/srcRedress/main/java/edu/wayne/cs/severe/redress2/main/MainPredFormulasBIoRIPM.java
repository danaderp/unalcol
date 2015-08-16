package edu.wayne.cs.severe.redress2.main;

import java.util.List;

import org.apache.commons.cli.ParseException;

import edu.wayne.cs.severe.redress2.controller.HierarchyBuilder;
import edu.wayne.cs.severe.redress2.controller.processor.PredFormulasProcessorBIoRIPM;
import edu.wayne.cs.severe.redress2.entity.TypeDeclaration;
import edu.wayne.cs.severe.redress2.utils.ArgsParser;
import edu.wayne.cs.severe.redress2.utils.ArgsPredParser;

/**
 * @author danaderp
 * @version 1.0
 * @created 03-Agosto-2015 12:28:47
 */
public class MainPredFormulasBIoRIPM {
	
	/**
	 * 
	 * @param args
	 */
	private static List<TypeDeclaration> sysTypeDcls;
	public List<TypeDeclaration> getSysTypeDcls(){
		return  sysTypeDcls;
	}
	public void main(String[] args, 
			HierarchyBuilder builder, List<TypeDeclaration> sysTypeDcls_) {

		try {

			// parse the arguments ArgsPredParser suppresed
			ArgsParser parser = new ArgsParser();
			try {
				parser.processArgs(args);
			} catch (ParseException e) {
				System.out.println(e.getMessage());
				parser.printHelp();
				return;
			}

			// process the system (create the metrics and builder in the constructor)
			PredFormulasProcessorBIoRIPM processor = new PredFormulasProcessorBIoRIPM(
					parser.getSysPath(), parser.getSysName(), parser.getLang());
			//Getting the builder and the typesdcls
			builder = processor.getBuilder();
			sysTypeDcls = processor.getSysTypeDcls();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}// end MainPredFormulas