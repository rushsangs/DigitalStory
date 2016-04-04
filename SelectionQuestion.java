import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import javax.swing.JCheckBox;
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
		if (list.isEmpty()) {
			return;
		}
		String[] list2 = new String[list.size()];
		for (int i = 0; i<list.size(); i++) {
			list2[i] = getName(list.get(i));
		}
		boolean[] selected2 = new boolean[list.size()];
		for (int i = 0; i<list.size(); i++) {
			if (selected.contains(list.get(i))) {
				selected2[i] = true;
			}
		}
		SelectionFrame frame = new SelectionFrame(list2, selected2, prompt);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
		//while (!frame.isDone); // stall until pop-up is closed
		return;
	}
	
	public abstract void applyAnswer();
	
	public abstract String getName(T t);
}
