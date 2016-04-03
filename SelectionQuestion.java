import java.util.HashSet;
import java.util.List;

/**
 * 
 * @author Patrick
 *
 * given a list of affordances 
 * or objects and a property, 
 * asks the user to divide the 
 * list into two sublists: 
 * members who have the 
 * property and members who do 
 * not have the property
 */
public abstract class SelectionQuestion<T> {
	private String prompt;
	public List<T> list;
	public HashSet<T> selected;
	
	public SelectionQuestion(String prompt, List<T> list) {
		this.prompt = prompt;
		this.list = list;
		this.selected = new HashSet<T>();
	}
	
	public void setDefaultSelection(List<T> selection) {
		for (T t : selection) {
			if (list.contains(t)) {
				selected.add(t);
			}
		}
	}
	
	public void promptUser() {
		// swing code
		// display prompt
		// display selection list
		// save button that on click closes the prompt and updates selected
		//	(if selected is not updated every time the selection list is changed)
	}
	
	public abstract void applyAnswer();
}
