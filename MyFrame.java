import javax.swing.*;

import java.util.*;
import java.awt.*;
import java.awt.event.*;

public class MyFrame extends JFrame implements ActionListener {
	StringBuilder storystring = new StringBuilder();
	StringBuilder objectstring = new StringBuilder();
	StringBuilder affordstring = new StringBuilder();
	//Object panel, left side
	private JPanel objectpanel = new JPanel();
		private JLabel object = new JLabel("Smart Objects");
		private JScrollPane objectpane;
		private JTextArea objectList = new JTextArea(10,10);
	//Action panel, right side
	private JPanel affordpanel = new JPanel();
		private JScrollPane affordpane;
		private JLabel affordances = new JLabel("Affordances");
		private JTextArea affordancesList = new JTextArea(10,10);
	//Story panel, center
	private JPanel storytxtpanel = new JPanel();
		private JLabel storylabel = new JLabel("Story");
		private JScrollPane storypane;
		private JTextArea storytxt = new JTextArea(10,50);
	//Bottom panel
	private JPanel bottom = new JPanel();
		 private JPanel enterpanel = new JPanel();
		 	private JLabel enterlabel = new JLabel("Text Here");
		 	private JButton enter = new JButton("Enter");
		 	private JTextField entertxt = new JTextField(50);
		 private JPanel generatepanel = new JPanel();
		 	private JButton generate = new JButton("Generate Story");
	
	
	public MyFrame(){
		this.setTitle("Story World Generator");
		
		objectpanel.setLayout(new BorderLayout(10,10));
			objectList.setEditable(false);
			objectpanel.add(object,BorderLayout.NORTH);
			objectpane = new JScrollPane(objectList);
			objectpanel.add(objectpane,BorderLayout.CENTER);
		affordpanel.setLayout(new BorderLayout(10,10));
			affordancesList.setEditable(false);
			affordpanel.add(affordances,BorderLayout.NORTH);
			affordpane = new JScrollPane(affordancesList);
			affordpanel.add(affordpane,BorderLayout.CENTER);
		storytxtpanel.setLayout(new BorderLayout(10,10));
			storytxt.setEditable(false);
			storytxtpanel.add(storylabel,BorderLayout.NORTH);
			storypane = new JScrollPane(storytxt);
			storytxtpanel.add(storypane,BorderLayout.CENTER);
		enterpanel.setLayout(new BorderLayout(10,10));
			enterpanel.add(enterlabel,BorderLayout.WEST);
			enterpanel.add(entertxt,BorderLayout.CENTER);
			enterpanel.add(enter,BorderLayout.EAST);
			enter.addActionListener(this);
			entertxt.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent e){
					String string = entertxt.getText().trim();
					String[] parts = string.split("\\s+");
					String[] objects;
					String[] affords;
					if(parts.length < 2){
						JOptionPane.showMessageDialog(MyFrame.this,"Incorrect Format: Enter as Object Action Object*");
						return;
					}			
					storystring.append(string + "\n");
					storytxt.setText(storystring.toString());
					for(int i = 0; i< parts.length;i++){
						objects = objectstring.toString().split("\\s+");
						affords = affordstring.toString().split("\\s+");
						if(i != 1){
							int j;
							for(j = 0;j<objects.length;j++){
								if(parts[i].equals(objects[j])){
									break;
								}
							}
							if( j == objects.length){
								objectstring.append(parts[i] + "\n");
								//objectList.setText(objectstring.toString());
								continue;
							}							
						}
						if(i == 1){
							int k;
							for(k = 0;k<affords.length;k++){
								if(parts[i].equals(affords[k])){
									break;
								}
							}
							if( k == affords.length){
								affordstring.append(parts[i] + "\n");
								//affordancesList.setText(affordstring.toString());
								continue;
							}							
						}									
					}
					objectList.setText(objectstring.toString());
					affordancesList.setText(affordstring.toString());
					entertxt.setText("");
					return;
				}
			});
		generatepanel.setLayout(new FlowLayout(FlowLayout.CENTER,10,10));
			generatepanel.add(generate);
			generate.addActionListener(this);
		bottom.setLayout(new BorderLayout(10,10));
			bottom.add(enterpanel,BorderLayout.NORTH);
			bottom.add(generatepanel,BorderLayout.SOUTH);
		this.setLayout(new BorderLayout(10,10));
			this.add(objectpanel,BorderLayout.WEST);
			this.add(affordpanel,BorderLayout.EAST);
			this.add(storytxtpanel,BorderLayout.CENTER);
			this.add(bottom,BorderLayout.SOUTH);		
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		switch(e.getActionCommand()){
		case "Enter":
			String string = entertxt.getText().trim();
			String[] parts = string.split("\\s+");
			if(parts.length < 2){
				JOptionPane.showMessageDialog(this,"Incorrect Format: Enter as Object Action Object*");
				break;
			}			
			storystring.append(string + "\n");
			storytxt.setText(storystring.toString());
			for(int i = 0; i< parts.length;i++){
				if(i == 0){
					objectstring.append(parts[i] + "\n");
					//objectList.setText(objectstring.toString());
					continue;
				}
				if(i == 1){
					affordstring.append(parts[i] + "\n");
					//affordancesList.setText(affordstring.toString());
					continue;
				}
				objectList.append(parts[i] + "\n");				
			}
			objectList.setText(objectstring.toString());
			affordancesList.setText(affordstring.toString());
			entertxt.setText("");
			break;
		case "Generate Story":
			JOptionPane.showMessageDialog(this,"In Progress");
			break;
		
		}
	}
	public String getAffordances(){
		return affordstring.toString();
	}
	public String getObjects(){
		return objectstring.toString();
	}
	public String getStory(){
		return storystring.toString();
	}
}
