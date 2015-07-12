package edu.wayne.cs.severe.redress2.entity.refactoring.formulas;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import edu.wayne.cs.severe.redress2.entity.refactoring.formulas.ec.ExctractClassPFTest;
import edu.wayne.cs.severe.redress2.entity.refactoring.formulas.em.ExtractMethodPFTest;
import edu.wayne.cs.severe.redress2.entity.refactoring.formulas.im.InlineMethodPFTest;
import edu.wayne.cs.severe.redress2.entity.refactoring.formulas.mf.AllTestsMF;
import edu.wayne.cs.severe.redress2.entity.refactoring.formulas.mm.AllTestsMM;
import edu.wayne.cs.severe.redress2.entity.refactoring.formulas.pdf.PushDownFieldPFTest;
import edu.wayne.cs.severe.redress2.entity.refactoring.formulas.pdm.PushDownMethodPFTest;
import edu.wayne.cs.severe.redress2.entity.refactoring.formulas.puf.AllTestsPUF;
import edu.wayne.cs.severe.redress2.entity.refactoring.formulas.pum.PullUpMethodPFTest;
import edu.wayne.cs.severe.redress2.entity.refactoring.formulas.rdi.AllTestsRDI;
import edu.wayne.cs.severe.redress2.entity.refactoring.formulas.rid.ReplaceInherDelegPFTest;
import edu.wayne.cs.severe.redress2.entity.refactoring.formulas.rmmo.AllTestsRMMO;

@RunWith(Suite.class)
@SuiteClasses({ AllTestsPUF.class, ExctractClassPFTest.class,
		ExtractMethodPFTest.class, InlineMethodPFTest.class, AllTestsMF.class,
		AllTestsMM.class, PushDownFieldPFTest.class,
		PushDownMethodPFTest.class, AllTestsPUF.class,
		PullUpMethodPFTest.class, AllTestsRDI.class,
		ReplaceInherDelegPFTest.class, AllTestsRMMO.class })
public class AllTestsFormulas {

}
