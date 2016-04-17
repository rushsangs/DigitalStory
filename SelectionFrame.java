import javax.swing.*;

import java.util.*;
import java.util.List;
import java.awt.*;
import java.awt.event.*;
public class SelectionFrame extends JDialog implements ActionListener{
	String[] list = null;
	enum[] enums = null;
	String prompt = null;
	private JLabel[] mylabels;
	private JLabel myquestion;
	//private JCheckBox[] myboxes;
	//new code
	private JRadioButton norm[];
	private JRadioButton LDorm[];
	private JRadioButton RDorm[];
	private JRadioButton LRDorm[];
	private JRadioButton RTerm[];
	private JRadioButton RTerm[];
	private JRadioButton LTerm[];
	private JRadioButton LRTerm[];
	private ButtonGroup group[];
	//end button stuff
	private JButton ok = new JButton("OK");
	public boolean isDone;
	
	public SelectionFrame(String[] list, enum[] enums, String prompt){
		this.isDone = false;
		this.list = list;
		this.selected = selected;
		this.prompt = prompt;
		this.setLayout(new BorderLayout(10,10));
		this.setModal(true);
		//this.setTitle(prompt);
		myquestion = new JLabel(prompt);
		mylabels = new JLabel[list.length];
		//myboxes = new JCheckBox[list.length];
		//new code
		norm = new JRadioButton[list.length];
		LDorm = new JRadioButton[list.length];
		RDorm = new JRadioButton[list.length];
		LRDorm = new JRadioButton[list.length];
		LTerm = new JRadioButton[list.length];
		RTerm = new JRadioButton[list.length];
		LRTerm = new JRadioButton[list.length];
		group = new ButtonGroup[list.length];
		
		JPanel mygrid = new JPanel();
		mygrid.setLayout(new GridLayout(list.length,2));
		for(int i = 0; i < list.length; i++){
			mylabels[i] = new JLabel(list[i]);
			norm[i] = new JRadioButton("Norm");
			LDorm[i] = new JRadioButton("LDorm");
			RDorm[i] = new JRadioButton("RDorm");
			LRDorm[i] = new JRadioButton("LRDorm");
			LTerm[i] = new JRadioButton("LTerm");
			RTerm[i] = new JRadioButton("RTerm");
			LRTerm[i] = new JRadioButton("LRTerm");
			norm[i].setSelected(true);
			group[i] = new ButtonGroup();
			group[i].add(norm[i]);
			group[i].add(LDorm[i]);
			group[i].add(RDorm[i]);
			group[i].add(LRorm[i]);
			group[i].add(LTorm[i]);
			group[i].add(RTorm[i]);
			group[i].add(LRorm[i]);
			//myboxes[i] = new JCheckBox();
			//myboxes[i].setSelected(true);
			//myboxes[i].setVisible(true);
			mygrid.add(mylabels[i]);
			mygrid.add(group[i]);
		}
		this.add(mygrid,BorderLayout.CENTER);
		this.add(myquestion,BorderLayout.NORTH);
		this.add(ok,BorderLayout.SOUTH);
		ok.addActionListener(this);
				
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		switch(e.getActionCommand()){
		case "OK":
			for(int i = 0; i < list.length; i++){
				if(norm[i].isSelected()){
					enum[i] = NORM;
					continue;
				}
				if(LDorm[i].isSelected()){
					enum[i] = LDORM;
					continue;
				}
				if(RDorm[i].isSelected()){
					enum[i] = RDorm;
					continue;
				}
				if(LRDorm[i].isSelected()){
					enum[i] = LRDorm;
					continue;
				}
				if(LTerm[i].isSelected()){
					enum[i] = LTerm;
					continue;
				}
				if(RTerm[i].isSelected()){
					enum[i] = RTerm;
					continue;
				}
				if(LRTerm[i].isSelected()){
					enum[i] = LRTerm;
					continue;
				}
			}
			isDone = true;
			this.dispose();
		
		}
	}

}