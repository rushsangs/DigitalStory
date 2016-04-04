import javax.swing.*;

import java.util.*;
import java.util.List;
import java.awt.*;
import java.awt.event.*;
public class SelectionFrame extends JFrame implements ActionListener{
	String[] list = null;
	boolean[] bool = null;
	String prompt = null;
	private JLabel[] mylabels;
	private JCheckBox[] myboxes;
	private JButton ok = new JButton("OK");
	public boolean isDone;
	
	public SelectionFrame(String[] list, boolean[] bool, String prompt){
		this.isDone = false;
		this.list = list;
		this.bool = bool;
		this.prompt = prompt;
		this.setLayout(new BorderLayout(10,10));
		this.setTitle(prompt);
		mylabels = new JLabel[list.length];
		myboxes = new JCheckBox[list.length];
		JPanel mygrid = new JPanel();
		mygrid.setLayout(new GridLayout(list.length,2));
		for(int i = 0; i < list.length; i++){
			mylabels[i] = new JLabel(list[i]);
			myboxes[i] = new JCheckBox();
			myboxes[i].setSelected(true);
			myboxes[i].setVisible(true);
			mygrid.add(mylabels[i]);
			mygrid.add(myboxes[i]);
		}
		this.add(mygrid,BorderLayout.CENTER);
		this.add(ok,BorderLayout.SOUTH);
		
		
		
		
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		switch(e.getActionCommand()){
		case "ok":
			for(int i = 0; i < myboxes.length; i++){
				bool[i] = myboxes[i].isSelected();
			}
			isDone = true;
			this.dispose();
		
		}
	}

}