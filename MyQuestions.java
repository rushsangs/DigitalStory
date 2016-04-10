import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MyQuestions {
	public static void ask(ArrayList<DigitalObject> objects2) {
		List<DigitalObject> passiveCandidates = new ArrayList<DigitalObject>();
		int i = 0;
		while(i < objects2.size()){
			if(objects2.get(i).affordances.isEmpty()){
//				objects2.get(i).isPassive = true;
				passiveCandidates.add(objects2.get(i));
			}
			i++;
		}
		SelectionQuestion<DigitalObject> isPassive = new SelectionQuestion<DigitalObject>(
				"Please check all smart objects that are passive.", passiveCandidates) {
			@Override
			public void applyAnswer() {
				for (DigitalObject o : list) {
					o.isPassive = selected.contains(o);
				}
			}
			@Override
			public String getName(DigitalObject o) {
				return o.name;
			}
		};
		isPassive.setDefaultSelection(passiveCandidates);
		Set<DigitalAffordance> uniqueActions = new HashSet<DigitalAffordance>();
		for (DigitalObject o : objects2) {
			uniqueActions.addAll(o.affordances);
		}
		ArrayList<DigitalAffordance> actions = new ArrayList<DigitalAffordance>(uniqueActions);
		SelectionQuestion<DigitalAffordance> isTerminal = 
				new SelectionQuestion<DigitalAffordance>(
						"Please check all actions that result in termination.", actions) {

							@Override
							public void applyAnswer() {
								for (DigitalAffordance a : list) {
									if(selected.contains(a))
									{
										//TODO: PATRICK! Set the enum to "terminal R" or something
									}
								}
							}

							@Override
							public String getName(DigitalAffordance t) {
								return t.name;
							}
			
		};
		//TODO: set default isTerminal selection
		
		Question[] questions = {isPassive, isTerminal};
		for (Question q : questions) {
			q.promptUser();
			q.applyAnswer();
		}
		
		// OUTPUT TO CONSOLE
		System.out.println("isPassive");
		System.out.println("---------");
		for (DigitalObject o : objects2) {
			System.out.println(o.name + ": " + ((o.isPassive)? "Passive" : "Active"));
		}
		System.out.println();
		System.out.println("isTerminal");
		System.out.println("----------");
		for (DigitalAffordance a : actions) {
			//TODO: MAybe delete? System.out.println(a.name + ": " + ((a.isTerminal)? "Terminal" : "Normal"));
		}
	}
}
