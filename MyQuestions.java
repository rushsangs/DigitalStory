import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MyQuestions {	
	public static final String IS_PASSIVE_TEXT = "Please check all smart objects that are passive.";
	public static final String IS_TERMINAL_TEXT = "Please check all actions that result in termination for either the actor or the affordees.";
	
	public static void ask(ArrayList<DigitalObject> objects2, String storystring) {
		
		int i = 0;
		
//		List<DigitalObject> passiveCandidates = new ArrayList<DigitalObject>();
//		while(i < objects2.size()){
//			if(objects2.get(i).affordances.isEmpty()){
//				passiveCandidates.add(objects2.get(i));
//			}
//			i++;
//		}
//		SelectionQuestion<DigitalObject> isPassive = 
//				new SelectionQuestion<DigitalObject>(
//						IS_PASSIVE_TEXT, 
//						passiveCandidates) {
//			@Override
//			public void applyAnswer() {
//				for (DigitalObject o : list) {
//					o.isPassive = selected.contains(o);
//				}
//			}
//			@Override
//			public String getName(DigitalObject o) {
//				return o.name;
//			}
//		};
//		isPassive.setDefaultSelection(passiveCandidates);
		
		HashMap<String, DigitalObject> objectLookup = 
				new HashMap<String, DigitalObject>();
		for (DigitalObject o : objects2) {
			objectLookup.put(o.name, o);
		}
		class ActionBundle {
			public DigitalObject actor;
			public DigitalAffordance affordance;
			public ActionTuple instance;
		}
		HashMap<DigitalObject, ActionBundle> LTermCandidates = 
				new HashMap<DigitalObject, ActionBundle>();
		HashMap<DigitalObject, ActionBundle> RTermCandidates =
				new HashMap<DigitalObject, ActionBundle>();
		String[] sentences = storystring.split("\n");
		for (i = 0; i<sentences.length; i++) {
			String[] sentence = sentences[i].trim().split(" ");
			if (sentence.length<=2) {
				ActionBundle a = new ActionBundle();
				a.actor = objectLookup.get(sentence[0]);
				a.affordance = affordanceLookup(a.actor, sentence[1]);
				a.instance = a.affordance.instances.get(0);
				LTermCandidates.put(a.actor, a);
			} else {
				for (int j = 2; j<sentence.length; j++) {
					ActionBundle a = new ActionBundle();
					a.actor = objectLookup.get(sentence[0]);
					a.affordance = affordanceLookup(a.actor, sentence[1]);
					a.instance = a.affordance.instances.get(j-2);
					LTermCandidates.put(a.actor, a);
					RTermCandidates.put(a.instance.affordee, a);
				}
			}		
		}
		
		HashMap<DigitalAffordance, DigitalObject> parents = 
				new HashMap<DigitalAffordance, DigitalObject>();
		for (DigitalObject o : objects2) {
			for (DigitalAffordance affordance : o.affordances) {
				parents.put(affordance, o);
			}
		}
		
		HashSet<ActionBundle> uniqueActions = new HashSet<ActionBundle>();
		uniqueActions.addAll(LTermCandidates.values());
		uniqueActions.addAll(RTermCandidates.values());		
		SelectionQuestion<ActionBundle> isTerminal = 
				new SelectionQuestion<ActionBundle>(
						IS_TERMINAL_TEXT, 
						new ArrayList<ActionBundle>(uniqueActions)) {

							@Override
							public void applyAnswer() {
								for (ActionBundle a : list) {
//									if(selected.contains(a))
//									{
//										a.instance.type = ActionType.TERM;
//									}
								}
							}

							@Override
							public String getName(ActionBundle t) {
								StringBuilder s = new StringBuilder();
								s.append(t.actor.name);
								s.append(" ");
								s.append(t.affordance.name);
								if (null!=t.instance) {
									s.append(" ");
									s.append(t.instance.affordee.name);
								}
								return s.toString();
							}
			
		};
		//TODO: set default isTerminal selection
		
//		Question[] questions = {isPassive, isTerminal};
		Question[] questions = {isTerminal};
		for (Question q : questions) {
			q.promptUser();
			q.applyAnswer();
		}
		
		// OUTPUT TO CONSOLE
//		System.out.println("isPassive");
//		System.out.println("---------");
//		for (DigitalObject o : objects2) {
//			System.out.println(o.name + ": " + ((o.isPassive)? "Passive" : "Active"));
//		}
//		System.out.println();
		System.out.println("isTerminal");
		System.out.println("----------");
		for (DigitalAffordance a : parents.keySet()) {
			for (ActionTuple instance : a.instances) {
				System.out.println(parents.get(a).name + " " +
						a.name + " " + instance.affordee.name + 
						": "+ instance.type);
			}
		}
	}
	
	public static DigitalAffordance affordanceLookup(DigitalObject actor, String name) {
		for (DigitalAffordance aff : actor.affordances) {
			if (aff.name.equals(name)) {
				return aff;
			}
		}
		return null;
	}
}
