package neoAgents;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public abstract class NeoAgent implements Runnable{
	List<Sensor> listSensor;
	List<Effectors> listActuador; 
	List<NeoRule> listRule;
	private UUID id;
	
	public NeoAgent(){
		listSensor = new ArrayList<Sensor>();
		listActuador= new ArrayList <Effectors>();
		listRule = new ArrayList <NeoRule>();
		this.id = UUID.randomUUID();
	}
	
	public List<Sensor> getListSesor() {
		return listSensor;
	}
	public void setListSesor(List<Sensor> listSesor) {
		this.listSensor = listSesor;
	}
	public List<Effectors> getListActuador() {
		return listActuador;
	}
	public void setListActuador(List<Effectors> listActuador) {
		this.listActuador = listActuador;
	}
	
	
	public List<NeoRule> getListRule() {
		return listRule;
	}


	public void setListRule(List<NeoRule> listRule) {
		this.listRule = listRule;
	}


	public UUID getId() {
		return id;
	}
	
}
