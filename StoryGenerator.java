import java.util.*;
import java.util.List;

public class StoryGenerator {
	
	enum StoryAlgorithm { 
		STARTFALSE, STARTRANDOM
	}
	
	private ArrayList<DigitalObject> objects;
	private int MAX_LENGTH;
	private int MIN_LENGTH;
	private StoryAlgorithm sa;
	
	public StoryGenerator(ArrayList<DigitalObject> objs) {
		objects = objs;
		setStoryAlgorithm(StoryAlgorithm.STARTFALSE); //default story algo is start false
	}
	
	public StoryGenerator(ArrayList<DigitalObject> objs, StoryAlgorithm e) {
		objects = objs;
		setStoryAlgorithm(e);
	}
	
	public String writeStory() {
		Random rng = new Random();
		//randomly pick a story length
		int length = rng.nextInt(MAX_LENGTH-MIN_LENGTH) + MIN_LENGTH;
		String result = "";
		//set up initial state of the world
		switch(sa) {
		case STARTFALSE:
			for(int i = 0; i < objects.size(); i++) {
				for(int j = 0; j < objects.get(i).states.size(); j++) {
					objects.get(i).states.get(j).isActive = false;
				}
			}
			break;
		case STARTRANDOM:
			for(int i = 0; i < objects.size(); i++) {
				for(int j = 0; j < objects.get(i).states.size(); j++) {
					objects.get(i).states.get(j).isActive = rng.nextBoolean();
				}
			}
			break;
		}
		//for each line of the story
		for(int i = 0; i < length; i++) {
			//pick an active object to perform an action 
			boolean active = false;
			int obj = 0;
			while (!active) {
				obj = rng.nextInt(objects.size());
				if(objects.get(obj).actions.size() != 0) {
					active = true;
				}
			}
			result += objects.get(obj).name + " ";
			//pick an action for it to perform
			int act = rng.nextInt(objects.get(obj).actions.size());
			result += objects.get(obj).actions.get(act).affordance.name + " ";
			//pick at least one object for it to happen to
			boolean[] affs = new boolean[objects.get(obj).actions.get(act).affordees.size()];
			boolean min = false;
			while(!min) {
				for(int j = 0; j < affs.length; j++) {
					affs[j] = rng.nextBoolean();
					if(affs[j]) {
						min = true;
						result += objects.get(obj).actions.get(act).affordees.get(j).name + " ";
					}
				}
			}
			result += "\n";
		}
		return result;
	}
	
	public void setStoryAlgorithm(StoryAlgorithm e) {
		sa = e;
		switch(e) {
			case STARTFALSE:
				MAX_LENGTH = 40;
				MIN_LENGTH = 10;
				break;
			case STARTRANDOM:
				MAX_LENGTH = 40;
				MIN_LENGTH = 10;
				break;
			default:
				setStoryAlgorithm(StoryAlgorithm.STARTFALSE);
				break;
		}
	}
}