import javax.swing.*;

import java.util.*;
import java.util.List;
import java.awt.*;
import java.awt.event.*;
public class SelectionFrame extends JFrame implements ActionListener{
	
	public SelectionFrame(String[] list, boolean[] bool, String prompt){
		this.setLayout(new BorderLayout(10,10));
		this.setTitle(prompt);
		JLabel[] mylabels = new JLabel[list.length];
		JCheckBox[] myboxes = new JCheckBox[list.length];
		JPanel mygrid = new JPanel();
		mygrid.setLayout(new GridLayout(list.length,2));
		for(int i = 0; i < list.length; i++){
			mylabels[i] = new JLabel(list[i]);
			myboxes[i] = new JCheckBox(list[i]);
			myboxes[i].setSelected(true);
			myboxes[i].setVisible(true);
		}
		
		
		
		
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
}
