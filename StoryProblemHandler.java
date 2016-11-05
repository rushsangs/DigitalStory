import java.util.ArrayList;
import java.util.LinkedHashSet;

public class StoryProblemHandler implements Runnable {
	public ArrayList<StoryProblemObject[]> problemsList;
	public LinkedHashSet<Integer> updatedSentencesList;
	
	public StoryProblemHandler() {
		problemsList = new ArrayList<StoryProblemObject[]>();
		updatedSentencesList = new LinkedHashSet<Integer>();
	}
	
	public boolean detectProblems(int index, String oaoText) {
		StoryProblemObject[] pset = PrologQueryMaster.getError(index);
		problemsList.add(pset);
		return pset.length>0;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (true) {
			if (!updatedSentencesList.isEmpty()) {
				
			}
		}
	}
}
