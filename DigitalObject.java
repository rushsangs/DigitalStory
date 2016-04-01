import java.util.ArrayList;

public class DigitalObject {

	//object class for each object in the story
	public String name;
	public ArrayList<DigitalState> states = new ArrayList<DigitalState>();
	public ArrayList<DigitalAction> actions=new ArrayList<DigitalAction>();
	public DigitalObject(String name) {
		// TODO Auto-generated constructor stub
		this.name=name;
	}
	public void addAction(DigitalAction action)
	{
		actions.add(action);
	}
	public void addState(DigitalState state)
	{
		states.add(state);
	}
}
