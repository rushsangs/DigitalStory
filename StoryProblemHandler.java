import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;

public class StoryProblemHandler implements Runnable {
	public ArrayList<StoryProblemObject[]> problemsList;
	public HashMap<Integer, String[]> sentences;
	public LinkedHashSet<Integer> updatedSentencesList;
	
	public StoryProblemHandler() {
		problemsList = new ArrayList<StoryProblemObject[]>();
		sentences = new HashMap<Integer, String[]>();
		updatedSentencesList = new LinkedHashSet<Integer>();
	}
	
	public void appendSentence (String oaoText, String nLText) {
		problemsList.add(null);
		sentences.put(problemsList.size()-1, new String[]{oaoText, nLText});
		updatedSentencesList.add(problemsList.size()-1);
	}
	
	public void updateSentence(int index, String oaoText, String nLText) {
		problemsList.set(index, null);
		sentences.put(index, new String[]{oaoText, nLText});
		updatedSentencesList.add(index);
	}
	
	public boolean detectProblems() {
		return detectProblems(problemsList.size()-1);
	}
	public boolean detectProblems(int index) {
		StoryProblemObject[] pset = PrologQueryMaster.getError(index, sentences);
		if (pset.length>0) {
			problemsList.set(index, pset);
			return true;
		} else {
			problemsList.set(index, null);
			return false;
		}
	}

	@Override
	public void run() {
		while (true) {
			if (!updatedSentencesList.isEmpty()) {
				int nextIndex = updatedSentencesList.iterator().next();
				detectProblems(nextIndex);
				updatedSentencesList.remove(nextIndex);
			}
		}
	}
}
