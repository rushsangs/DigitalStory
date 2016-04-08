import java.util.ArrayList;

class ActionTuple {
	public DigitalObject affordee;
	public DigitalState active;
	public ActionType type;
	
	public ActionTuple(DigitalObject affordee, DigitalState active, ActionType type) {
		this.affordee = affordee;
		this.active = active;
		this.type = type;
	}
}

public class DigitalAffordance {
	
	//add state here
	public String name;
	public ArrayList<ActionTuple> instances;
	public DigitalAffordance(String name, ArrayList<ActionTuples> instances) {
		// TODO Auto-generated constructor stub
		this.name = name;
		this.instances = instances;
	}
}

