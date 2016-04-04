import java.util.HashSet;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

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
	private JFrame parent;
	private String prompt;
	public List<T> list;
	public HashSet<T> selected;
	
	public SelectionQuestion(JFrame parent, String prompt, List<T> list) {
		this.parent = parent;
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
//		JCheckBox checkbox = new JCheckBox("Do not show this message again.");
//		String message = "Are you sure you want to disconnect the selected products?";
//		Object[] params = new Object[list.size()+1];
//		params.add(prompt);
//		int n = JOptionPane.showConfirmDialog(parent, params, "Disconnect Products", JOptionPane.YES_NO_OPTION);
//		boolean dontShow = checkbox.isSelected();
		
		// display prompt
		// display selection list
		// save button that on click closes the prompt and updates selected
		//	(if selected is not updated every time the selection list is changed)
	}
	
	public abstract void applyAnswer();
}
