import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class StoryProblemHandler implements Runnable {
	private class Sentence {
		int index;
		String[] oaoText;
		String nLText;
		
		public Sentence(int index, String[] oaoText, String nLText) {
			this.index = index;
			this.oaoText = oaoText;
			this.nLText = nLText;
		}
	}
	public int lastIndex;
	public LinkedHashMap<Integer, Sentence> updatedSentencesList;
	
	public HashMap<Integer, String[]> oaoList;
	
	public ArrayList<StoryProblemObject[]> problemsList;
	
	public StoryProblemHandler() {
		lastIndex = -1;
		updatedSentencesList = new LinkedHashMap<Integer, Sentence>();
		
		problemsList = new ArrayList<StoryProblemObject[]>();
		oaoList = new HashMap<Integer, String[]>();
	}
	
	public void appendSentence (String oaoText, String nLText) {
		lastIndex++;
		updateSentence(lastIndex, oaoText, nLText);
	}
	
	public void updateSentence(int index, String oaoText, String nLText) {
		updatedSentencesList.put(index, 
				new Sentence(index, oaoText.split("\\n"), nLText));
	}
	
	private boolean updateProblems(int index, String[] oaoText, String nLText) {
		StoryProblemObject[] pset = PrologQueryMaster.getError(index, oaoList, oaoText, nLText);
		if (pset.length>0) {
			problemsList.set(index, pset);
			return true;
		} else {
			// the sentence is correct
			problemsList.set(index, null);
			oaoList.put(index, oaoText);
			return false;
		}
	}
	
	public StoryProblemObject[] detectProblems(int index) {
		return problemsList.get(index);
	}

	@Override
	public void run() {
		while (true) {
			if (!updatedSentencesList.isEmpty()) {
				int nextIndex = updatedSentencesList.keySet().iterator().next();
				Sentence updatedSentence = updatedSentencesList.get(nextIndex);
				oaoList.put(nextIndex, updatedSentence.oaoText);
				updatedSentencesList.remove(nextIndex);
				
				updateProblems(nextIndex, updatedSentence.oaoText, updatedSentence.nLText);
			}
		}
	}
}
