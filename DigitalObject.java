import java.util.ArrayList;

public class DigitalObject {

	//object class for each object in the story
	public String name;
	public ArrayList<DigitalState> states = new ArrayList<DigitalState>();
	public ArrayList<DigitalAffordance> affordances=new ArrayList<DigitalAffordance>();
	boolean isPassive;
	public DigitalObject(String name) {
		// TODO Auto-generated constructor stub
		this.name=name;
		this.isPassive = false;
	}
	public void addAffordance(DigitalAffordance affordance)
	{
		affordances.add(affordance);
	}
	
//	public void addState(DigitalState state)
//	{
//		states.add(state);
//	}
}
