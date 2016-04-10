import java.util.*;
import java.util.List;

public class StoryGenerator {
	
	enum StoryAlgorithm { 
		RK9, KIPPS
	}
	
	private ArrayList<DigitalObject> objects;
	private int MAX_LENGTH;
	private int MIN_LENGTH;
	private StoryAlgorithm sa;
	
	public StoryGenerator(ArrayList<DigitalObject> objs) {
		objects = objs;
		setStoryAlgorithm(StoryAlgorithm.KIPPS); //default story algo is start false
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
		case RK9:
			for(int i = 0; i < objects.size(); i++) {
				for(int j = 0; j < objects.get(i).affordances.size(); j++) {
					for(int k = 0; k < objects.get(i).affordances.get(j).instances.size(); k++) {
						objects.get(i).affordances.get(j).instances.get(k).active = false;
					}
				}
			}
			//for each line of the story
			for(int i = 0; i < length; i++) {
				//pick an active object to perform an action 
				boolean active = false;
				int obj = 0;
				while (!active) {
					obj = rng.nextInt(objects.size());
					if(!objects.get(obj).isPassive) {
						active = true;
					}
				}
				result += objects.get(obj).name + " ";
				//pick an action for it to perform
				int act = rng.nextInt(objects.get(obj).affordances.size());
				result += objects.get(obj).affordances.get(act).name + " ";
				//pick at least one object for it to happen to
				boolean[] affs = new boolean[objects.get(obj).affordances.get(act).instances.size()];
				boolean min = false;
				while(!min) {
					for(int j = 0; j < affs.length; j++) {
						affs[j] = rng.nextBoolean();
						if(affs[j]) {
							min = true;
							result += objects.get(obj).affordances.get(act).instances.get(j).affordee.name + " ";
						}
					}
				}
				result += "\n";
			}
			break;
		case KIPPS:
			
			break;
		}
		return result;
	}
	
	public void setStoryAlgorithm(StoryAlgorithm e) {
		sa = e;
		switch(e) {
			case RK9:
				MAX_LENGTH = 10;
				MIN_LENGTH = 5;
				break;
			case KIPPS:
				MAX_LENGTH = 40;
				MIN_LENGTH = 10;
				break;
			default:
				setStoryAlgorithm(StoryAlgorithm.KIPPS);
				break;
		}
	}
}