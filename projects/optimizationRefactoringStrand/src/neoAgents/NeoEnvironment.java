/**
 * 
 */
package neoAgents;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author Daavid
 *
 */
public abstract class NeoEnvironment {
	private List<NeoAgent> listAgent;
	private List<NeoMonitor> listMonitor;
	private UUID id;
	
	public NeoEnvironment(){
		this.id = UUID.randomUUID();
		listAgent = new ArrayList<NeoAgent>();
		listMonitor = new ArrayList<NeoMonitor>();
	}
	
	//Apply Rules in the environment
	
	
	
	//Add an agent
	public void addAgent(NeoAgent agg){
		listAgent.add(agg);
	}

	public List<NeoAgent> getListAgent() {
		return listAgent;
	}

	public void setListAgent(List<NeoAgent> listAgent) {
		this.listAgent = listAgent;
	}

	public UUID getId() {
		return id;
	}

	public List<NeoMonitor> getListRule() {
		return listMonitor;
	}

	public void setListRule(List<NeoMonitor> listMonitor) {
		this.listMonitor = listMonitor;
	}
	
	
}
