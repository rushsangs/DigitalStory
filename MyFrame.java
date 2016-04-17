import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class MyFrame extends JFrame implements ActionListener {
	StringBuilder storystring = new StringBuilder();
	StringBuilder objectstring = new StringBuilder();
	StringBuilder affordstring = new StringBuilder();
	ArrayList<DigitalObject> objects2;
	DigitalStoryWorld world;
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
		 	private JButton clear = new JButton("Clear");
		 	private JButton getfile = new JButton("Get File");
	private JFileChooser filechooser = new JFileChooser();
	public int numoflines;
	
	public MyFrame(DigitalStoryWorld world){
		this.setTitle("Story World Generator");
		this.world=world;
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
					analyze(string, world);
					storystring.append(string + "\n");
					numoflines++;
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
			generatepanel.add(clear);
			clear.addActionListener(this);
			generatepanel.add(getfile);
			getfile.addActionListener(this);			
		bottom.setLayout(new BorderLayout(10,10));
			bottom.add(enterpanel,BorderLayout.NORTH);
			bottom.add(generatepanel,BorderLayout.SOUTH);
		this.setLayout(new BorderLayout(10,10));
			this.add(objectpanel,BorderLayout.WEST);
			this.add(affordpanel,BorderLayout.EAST);
			this.add(storytxtpanel,BorderLayout.CENTER);
			this.add(bottom,BorderLayout.SOUTH);
		numoflines = 0;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		switch(e.getActionCommand()){
		case "Enter":
			String string = entertxt.getText().trim();
			String[] parts = string.split("\\s+");
			String[] objects;
			String[] affords;
			if(parts.length < 2){
				JOptionPane.showMessageDialog(this,"Incorrect Format: Enter as Object Action Object*");
				break;
			}
			numoflines++;
			analyze(string, world);
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
			break;
		case "Clear":
			objects2.clear();
			storystring = new StringBuilder();
			objectstring = new StringBuilder();
			affordstring = new StringBuilder();
			storytxt.setText("");
			objectList.setText("");
			affordancesList.setText("");
			numoflines = 0;
			break;
		case "Get File":
			try{
				if(filechooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION){
					File file = filechooser.getSelectedFile();
					Scanner input = new Scanner(file);
					while(input.hasNext()){
						String string1 = input.nextLine();
						String[] parts1 = string1.split("\\s+");
						String[] objects1;
						String[] affords1;
						//analyze(string, objects2);
						storystring.append(string1 + "\n");
						numoflines++;
						storytxt.setText(storystring.toString());
						for(int i = 0; i< parts1.length;i++){
							objects1 = objectstring.toString().split("\\s+");
							affords1 = affordstring.toString().split("\\s+");
							if(i != 1){
							// Checks if existing object, if not, then adds to object string
								int j;
								for(j = 0;j<objects1.length;j++){
									if(parts1[i].equals(objects1[j])){
										break;
									}
								}
								if( j == objects1.length){
									objectstring.append(parts1[i] + "\n");
									//objectList.setText(objectstring.toString());
									continue;
								}							
							}
							if(i == 1){
							//Checks if action is already in the action string, if not then adds it 
								int k;
								for(k = 0;k<affords1.length;k++){
									if(parts1[i].equals(affords1[k])){
										break;
									}
								}
								if( k == affords1.length){
									affordstring.append(parts1[i] + "\n");
									//affordancesList.setText(affordstring.toString());
									continue;
								}							
							}									
						}
						objectList.setText(objectstring.toString());
						affordancesList.setText(affordstring.toString());
					}
				}
			}catch(Exception e1){
				e1.printStackTrace();
			}
			break;
		case "Generate Story":
			
			 //PSEUDO UNIT TESTING FOR PASSIVE OBJECTS
			//objects2.add(new DigitalObject("Forest"));
			//objects2.add(new DigitalObject("Cake"));
			//objects2.add(new DigitalObject("Granny"));
			
			//process input into object structure
			//Main.printData(objects2);
			
			//COMMENTING OUT STUFF BELOW FOR TESTING
//			MyQuestions.ask(objects2, storystring.toString());
//			
//			int i = 0;
//			
//			
//			String[] lines = storystring.toString().split("\n");
//			for(i = 0;i < lines.length;i++){
//				//ToDo
//			}
//			//then generate new story randomly
//			StoryGenerator sg = new StoryGenerator(objects2);
//			outputtxt.setText(sg.writeStory());
//			break;
			printOutWorld(world);
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
	private static ArrayList<DigitalObject> analyze(String story, DigitalStoryWorld world)
	{
		ArrayList<DigitalObject> objects=world.objects;
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
			
			//if second word is "is" IT IS NOT AN AFFORDANCE
			if(words.length>2 && words[1]=="is")
			{
				//words[2] is the type!
				System.out.println("is is used!");
			}
			
			ArrayList<DigitalObject> affordees=new ArrayList<DigitalObject>();
			if(words.length>2)
			{
				//add affordees to the object's action
				for(int x=2;x<words.length;++x)
				{
					boolean fl=false;
					for(int y=0;y<objects.size();++y)
					{
						if(objects.get(y).name.equalsIgnoreCase(words[x]))
						{
							affordees.add(objects.get(y));
							fl=true;
						}
					}
					if(!fl)
					{
						objects.add(new DigitalObject(words[x]));
						affordees.add(objects.get(objects.size()-1));
					}
					
				}
			}
			else
			{
				//this is a sentence like "Wolf dies", we add emptyObject to the affordees instead.
				affordees.add(DigitalObject.EMPTY_OBJECT);
			}
			
			//second word is action, add to active object
			DigitalAffordance affordance=new DigitalAffordance(words[1]);
			affordance.addActionTuple(affordees, ActionType.NORM);
			object.addAffordance(affordance);
			
			
		}
		return objects;
	}
	public void printOutWorld(DigitalStoryWorld world)
	{
		System.out.println("Objects: \n");
		for(int i=0;i<world.objects.size();++i)
		{
			DigitalObject obj=world.objects.get(i);
			System.out.println("\nNAME: "+ obj.name);
			//object contains affordances too!
			for(int j=0;j<obj.affordances.size();++j)
			{
				DigitalAffordance aff= obj.affordances.get(j);
				System.out.println("Affordance: "+ obj.affordances.get(j).name);
				//affordance has tuples, etc
				for(int k=0;k<aff.instances.size();++k)
				{
					ActionTuple tuple = aff.instances.get(k);
					System.out.println("Affordee: "+ tuple.affordee.name);
					System.out.println("Type: "+ tuple.type.name());
					System.out.println("Active: " + tuple.active);
				}
			}
		}
	}
}
