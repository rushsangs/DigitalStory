import javax.swing.*;

import java.util.*;
import java.util.List;
import java.awt.*;
import java.awt.event.*;

public class MyFrame extends JFrame implements ActionListener {
	StringBuilder storystring = new StringBuilder();
	StringBuilder objectstring = new StringBuilder();
	StringBuilder affordstring = new StringBuilder();
	ArrayList<DigitalObject> objects2;
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
		private JPanel labelpanel = new JPanel();
		private JLabel storylabel = new JLabel("                         Input Story");
		private JLabel outputlabel = new JLabel("Output Story                         ");
		private JScrollPane storypane;
		private JScrollPane output;
		private JTextArea storytxt = new JTextArea(10,25);
		private JTextArea outputtxt = new JTextArea(10,25);
	//Bottom panel
	private JPanel bottom = new JPanel();
		 private JPanel enterpanel = new JPanel();
		 	private JLabel enterlabel = new JLabel("Text Here");
		 	private JButton enter = new JButton("Enter");
		 	private JTextField entertxt = new JTextField(50);
		 private JPanel generatepanel = new JPanel();
		 	private JButton generate = new JButton("Generate Story");
	
	
	public MyFrame(ArrayList<DigitalObject> objects){
		this.setTitle("Story World Generator");
		objects2=objects;
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
			labelpanel.setLayout(new BorderLayout(10,10));
			labelpanel.add(storylabel,BorderLayout.WEST);
			labelpanel.add(outputlabel, BorderLayout.EAST);
			storytxtpanel.add(labelpanel, BorderLayout.NORTH);
			storypane = new JScrollPane(storytxt);
			storytxtpanel.add(storypane,BorderLayout.WEST);
			output = new JScrollPane(outputtxt);
			storytxtpanel.add(output, BorderLayout.EAST);
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
					//analyze(string, objects2);
					storystring.append(string + "\n");
					storytxt.setText(storystring.toString());
					for(int i = 0; i< parts.length;i++){
						objects = objectstring.toString().split("\\s+");
						affords = affordstring.toString().split("\\s+");
						if(i != 1){
						// Checks if existing object, if not, then adds to object string
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
						//Checks if action is already in the action string, if not then adds it 
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
			//analyze(string, objects2);
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
			
			 //PSEUDO UNIT TESTING
			objects2.add(new DigitalObject("Alice"));
			objects2.add(new DigitalObject("Bob"));
			objects2.add(new DigitalObject("Eve"));
			objects2.add(new DigitalObject("Cake"));
			
			//process input into object structure
			//Main.printData(objects2);
			List<DigitalObject> passiveCandidates = new ArrayList<DigitalObject>();			
			int i = 0;
			while(i < objects2.size()){
				if(objects2.get(i).actions.isEmpty()){
//					objects2.get(i).isPassive = true;
					passiveCandidates.add(objects2.get(i));
				}
				i++;
			}
			SelectionQuestion<DigitalObject> isPassive = new SelectionQuestion<DigitalObject>(
					"Please check all smart objects that are passive.", passiveCandidates) {
				@Override
				public void applyAnswer() {
					for (DigitalObject o : list) {
						o.isPassive = selected.contains(o);
					}
				}
				@Override
				public String getName(DigitalObject o) {
					return o.name;
				}
			};
			isPassive.setDefaultSelection(passiveCandidates);
			isPassive.promptUser();
			isPassive.applyAnswer();
//			for (DigitalObject o : objects2) {
//				System.out.println(o.name + ": " + ((o.isPassive)? "Passive" : "Active"));
//			}
			
			String[] lines = storystring.toString().split("\n");
			for(i = 0;i < lines.length;i++){
				//ToDo
			}
			//then generate new story randomly
			//StoryGenerator sg = new StoryGenerator(objects2);
			//outputtxt.setText(sg.writeStory());
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
	private static ArrayList<DigitalObject> analyze(String story, ArrayList<DigitalObject> objects)
	{
		//read the story sentence by sentence, separated by period
		String[] sentences = story.split("\\.");
		

		
		//for each sentence: first word is Active Object, second word is action, third word is passive object?
		for(int i =0;i<sentences.length; ++i)
		{
			sentences[i]=sentences[i].trim();
			String[] words=sentences[i].split(" ");
			//first word is active object
			DigitalObject object = null;
			boolean found=false;
			for(int j=0; j<objects.size(); ++j)
			{
				if(objects.get(j).name.equalsIgnoreCase(words[0]))
				{
					object=objects.get(j);
					found=true;
					break;
				}
			}
			if(!found)
			{
				object = new DigitalObject(words[0]);
				objects.add(object);
				
			}
			
			//second word is action, add to active object
			object.addAction(new DigitalAction(new DigitalAffordance(words[1]), new ArrayList<DigitalObject>(), new DigitalState(words[1].concat(words[2]))));
			//TODO: third word: what do we do with it?
			
		}
		return objects;
	}
}
