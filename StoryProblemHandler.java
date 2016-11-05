import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class StoryProblemHandler implements Runnable {
	public int lastIndex;
	public LinkedHashMap<Integer, String[]> updatedSentencesList;
	
	public HashMap<Integer, String[]> sentences;
	
	public ArrayList<StoryProblemObject[]> problemsList;
	
	public StoryProblemHandler() {
		lastIndex = -1;
		updatedSentencesList = new LinkedHashMap<Integer, String[]>();
		
		problemsList = new ArrayList<StoryProblemObject[]>();
		sentences = new HashMap<Integer, String[]>();
	}
	
	public void appendSentence (String oaoText, String nLText) {
		lastIndex++;
		updatedSentencesList.put(lastIndex, new String[]{oaoText, nLText});
	}
	
	public void updateSentence(int index, String oaoText, String nLText) {
		updatedSentencesList.put(index, new String[]{oaoText, nLText});
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
				int nextIndex = updatedSentencesList.keySet().iterator().next();
				sentences.put(nextIndex, updatedSentencesList.get(nextIndex));
				updatedSentencesList.remove(nextIndex);
				
				detectProblems(nextIndex);
			}
		}
	}
}
