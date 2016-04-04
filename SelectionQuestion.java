import java.awt.Dialog;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
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
	public boolean[] selected2;
	
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
		selected2 = new boolean[list.size()];
		for (int i = 0; i<list.size(); i++) {
			if (selected.contains(list.get(i))) {
				selected2[i] = true;
			}
		}
		final int LIST_SIZE = list.size();
		final List<T> LIST = list;
		final HashSet<T> SELECTED = selected;
		final boolean[] SELECTED2 = selected2;
		SelectionFrame frame = new SelectionFrame(list2, selected2, prompt) {
			
			@Override 
			public void dispose() {
				for (int i = 0; i<LIST_SIZE; i++) {
					if (SELECTED2[i]) {
						SELECTED.add(LIST.get(i));
					} else {
						SELECTED.remove(LIST.get(i));
					}
				}
				super.dispose();
			}
		};
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
		System.out.println(frame.isDone);
	}
	
	public abstract void applyAnswer();
	
	public abstract String getName(T t);
}
