/**
 * 
 */
package agentRefactoringStrand;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Daavid
 *
 */
public class Strand {
	private List<Refactor> listStrand = new ArrayList<Refactor>(); //Lista ordenada de Refactors
	private Metaphor metaphor = new Metaphor();
	
	//Adding a Refactor
	public void addbyRefactor(Refactor ref){
		listStrand.add(ref);
	}
	
	//Remove a Refactor
	public void removeRefactor(int index){
		listStrand.remove(index);
	}
}
