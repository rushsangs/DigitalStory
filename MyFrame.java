import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import java.sql.*;

public class MyFrame extends JFrame implements ActionListener {
	StringBuilder storystring = new StringBuilder();
	StringBuilder objectstring = new StringBuilder();
	StringBuilder affordstring = new StringBuilder();
	StringBuilder beginstring = new StringBuilder();
	StringBuilder middlestring = new StringBuilder();
	StringBuilder endstring = new StringBuilder();
	ArrayList<DigitalObject> objects2;
	static DigitalStoryWorld world;
	public static Connection con;
	public static Statement stmt;
	public static ResultSet resultset;
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
		private JScrollPane beginpane;
		private JScrollPane middlepane;
		private JScrollPane endpane;
		private JScrollPane output;
		private JTextArea begintxt = new JTextArea(10,25);
		private JTextArea middletxt = new JTextArea(10,25);
		private JTextArea endtxt = new JTextArea(10,25);
		private JTextArea outputtxt = new JTextArea(30,25);
		private JPanel storytextpanel = new JPanel();
	//Bottom panel
	private JPanel bottom = new JPanel();
		 private JPanel enterpanel = new JPanel();
		 	private JLabel enterlabel = new JLabel("Text Here");
		 	private JButton enter = new JButton("Enter");
		 	String[] sections = {"begin","middle","end"};
		 	JComboBox<String> storysections = new JComboBox<String>(sections);
		 	private JPanel box = new JPanel();
		 	private JTextField entertxt = new JTextField(50);
		 private JPanel generatepanel = new JPanel();
		 	private JButton generate = new JButton("Generate Graph");
		 	private JButton clear = new JButton("Clear");
		 	private JButton getfile = new JButton("Get File");
	private JFileChooser filechooser = new JFileChooser();
	public int numoflines;
	
	public MyFrame(DigitalStoryWorld world){
		this.initializeDB();
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
			begintxt.setEditable(false);
			middletxt.setEditable(false);
			endtxt.setEditable(false);
			labelpanel.setLayout(new BorderLayout(10,10));
			labelpanel.add(storylabel,BorderLayout.WEST);
			labelpanel.add(outputlabel, BorderLayout.EAST);
			storytxtpanel.add(labelpanel, BorderLayout.NORTH);
			beginpane = new JScrollPane(begintxt);
			middlepane = new JScrollPane(middletxt);
			endpane = new JScrollPane(endtxt);
			storytextpanel.setLayout(new GridLayout(3,1));
				storytextpanel.add(beginpane);
				storytextpanel.add(middlepane);
				storytextpanel.add(endpane);
			storytxtpanel.add(storytextpanel,BorderLayout.WEST);
			output = new JScrollPane(outputtxt);
			storytxtpanel.add(output, BorderLayout.EAST);
		enterpanel.setLayout(new BorderLayout(10,10));
			enterpanel.add(enterlabel,BorderLayout.WEST);
			enterpanel.add(entertxt,BorderLayout.CENTER);
			box.setLayout(new FlowLayout(FlowLayout.CENTER,10,10));
			box.add(storysections);
			box.add(enter);
			enterpanel.add(box,BorderLayout.EAST);
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
					MyFrame.analyze(string, MyFrame.world);
					storystring.append(string + "\n");
					numoflines++;
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
							if(parts[i].equals("is")){
								break;
							}
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
					if(storysections.getSelectedItem().toString().equals("begin")){
						beginstring.append(string + "\n");
						begintxt.setText(beginstring.toString());
					}else if(storysections.getSelectedItem().toString().equals("middle")){
						middlestring.append(string + "\n");
						middletxt.setText(middlestring.toString());
					}else if(storysections.getSelectedItem().toString().equals("end")){
						endstring.append(string + "\n");
						endtxt.setText(endstring.toString());
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
					if(parts[i].equals("is")){
						break;
					}
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
			if(storysections.getSelectedItem().toString().equals("begin")){
				beginstring.append(string + "\n");
				begintxt.setText(beginstring.toString());
			}else if(storysections.getSelectedItem().toString().equals("middle")){
				middlestring.append(string + "\n");
				middletxt.setText(middlestring.toString());
			}else if(storysections.getSelectedItem().toString().equals("end")){
				endstring.append(string + "\n");
				endtxt.setText(endstring.toString());
			}
			objectList.setText(objectstring.toString());
			affordancesList.setText(affordstring.toString());
			entertxt.setText("");
			break;
		case "Clear":
			world = new DigitalStoryWorld(new ArrayList<DigitalObject>(), new ArrayList<DigitalObject>());
			storystring = new StringBuilder();
			objectstring = new StringBuilder();
			affordstring = new StringBuilder();
			beginstring = new StringBuilder();
			middlestring = new StringBuilder();
			endstring = new StringBuilder();
			begintxt.setText("");
			middletxt.setText("");
			endtxt.setText("");
			outputtxt.setText("");
			objectList.setText("");
			affordancesList.setText("");
			numoflines = 0;
			break;
		case "Get File": //how to make work with story sections?
			try{
				if(filechooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION){
					File file = filechooser.getSelectedFile();
					Scanner input = new Scanner(file);
					while(input.hasNext()){
						String string1 = input.nextLine();
						String[] parts1 = string1.split("\\s+");
						String[] objects1;
						String[] affords1;
						analyze(string1, world);
						storystring.append(string1 + "\n");
						numoflines++;
						begintxt.setText(storystring.toString());
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
								if(parts1[i].equals("is")){
									break;
								}
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
		case "Generate Graph":
			try {
				createNodes(world);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			 //PSEUDO UNIT TESTING FOR PASSIVE OBJECTS
			//objects2.add(new DigitalObject("Forest"));
			//objects2.add(new DigitalObject("Cake"));
			//objects2.add(new DigitalObject("Granny"));
			
			//process input into object structure
			//Main.printData(objects2);
			
			//COMMENTING OUT STUFF BELOW FOR TESTING
			MyQuestions.ask(world, storystring.toString(), 
					removeIsFromString(beginstring.toString()), removeIsFromString(middlestring.toString()), removeIsFromString(endstring.toString()));
			
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
			GraphVisualizer gv = new GraphVisualizer(DbHelper.getAllNodes(), DbHelper.getAllEdges());
			gv.visualize();
		}
	}
	public String removeIsFromString(String storysection){
		String[] lines = storysection.split("\\n");
		String newString = "";
		for(int i = 0; i< lines.length; i++){
			if(lines[i].contains(" is ")){
				continue;
			}
			newString += lines[i] + "\n";
		}
		return newString;
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
	private void initializeDB(){
		try { 
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			System.out.println("Driver loaded\n");
		} catch (Exception e){
			e.printStackTrace();
		}
		con = null;
		String url = "jdbc:mysql://storyuniversedb.c99bh6mgf9xw.us-west-2.rds.amazonaws.com:3306/";
		String user = "root";
		String password = "group5group5";
		String dbname = "mydb";
		try{
			con = DriverManager.getConnection(url + dbname, user, password);
			System.out.println("Database connected\n");
		}catch (SQLException e){
			System.err.println("SQLException: " + e.getMessage());
			//System.err.println("SQLState: " + e,getSQLState());			
		}
		try{
			stmt = con.createStatement();
		}catch (SQLException e){
			e.printStackTrace();
		}		
	}
	
	public String sqlStatement (String name1, String name2){
		StringBuilder s = new StringBuilder();
		s.append("SELECT * FROM Edges WHERE(StartNode, EndNode) IN(");
		s.append("SELECT n1.NodeId, n2.NodeId From Nodes n1, Nodes n2");
		s.append("WHERE n1.name =");
		s.append("" + name1 + "");
		s.append("AND n2.Name=");
		s.append("" + name2 + "");
		s.append(");");
		return s.toString();
	}
	//this function creates Nodes for ALL affordances for the types
		private void createNodes(DigitalStoryWorld world) throws SQLException{
			System.out.println("yo");
			DigitalObject ObjectType;
			//for each Type, it checks if the type exists. If doesn't exist, insert it.
			for(int i=0; i< world.types.size();++i)
			{
				ObjectType=world.types.get(i);
				String sql="SELECT * FROM Types WHERE Name = '" + ObjectType.name + "';";
				if(stmt.execute(sql) && !stmt.getResultSet().first())
				{
					//we didn't get any rows, insert type.
					sql = "INSERT INTO Types(Name) VALUES('" + ObjectType.name.trim() + "');";
					try
					{
						stmt.execute(sql);
					}
					catch(Exception e)
					{
						System.out.println("The following statement failed: "+sql);
					}
				}
				
			}
			// for all digital objects that don't have a type
			for(int i=0;i<world.objects.size();++i)
			{
				if(world.objects.get(i).ObjectType!=null)
					continue;
				//add types if they don't exist
				DigitalObject obj=world.objects.get(i);
				String sql="SELECT * FROM Types WHERE Name = '" + obj.name + "';";
				if(stmt.execute(sql) && !stmt.getResultSet().first())
				{
					//we didn't get any rows, insert type.
					sql = "INSERT INTO Types(Name) VALUES('" + obj.name.trim() + "');";
					try
					{
						stmt.execute(sql);
					}
					catch(Exception e)
					{
						System.out.println("The following statement failed: "+sql);
					}
				}
				
			}
			for(int i=0; i< world.types.size();++i)
			{
				//now that the type exists, add nodes for each type's affordance's tuple
				String sql;
				ObjectType=world.types.get(i);
				for(int j=0; j< ObjectType.affordances.size(); ++j)
				{
					DigitalAffordance aff=ObjectType.affordances.get(j);
					for(int k=0;k<aff.instances.size();++k)
					{
						ActionTuple instance=aff.instances.get(k);
						sql="SELECT * FROM Nodes WHERE Name= '"+ aff.name +"' AND AfforderType = '"+ ObjectType.name + "' AND ActionEffect = '"+ instance.effect.toString() + "' AND AffordeeType = '"+ instance.affordee.name +"';";
						if(stmt.execute(sql) && !stmt.getResultSet().first())
						{
							sql="INSERT INTO `Nodes` (`Name`, `AfforderType`, `ActionEffect`, `AffordeeType`) VALUES ('"+ aff.name +"', '"+ ObjectType.name + "', '"+ instance.effect.toString() + "', '"+ instance.affordee.name +"');";
							try
							{
								stmt.execute(sql);
							}
							catch(Exception e)
							{
								System.out.println("The following statement failed: "+sql);
							}
						}
					}
				}
			}
			for(int i=0;i<world.objects.size();++i)
			{
				if(world.objects.get(i).ObjectType!=null)
					continue;
				DigitalObject obj=world.objects.get(i);
				//we add nodes for all the unique affordance tuples for this object
				for(int j=0;j<obj.affordances.size();++j)
				{
					DigitalAffordance aff=obj.affordances.get(j);
					for(int k=0;k< aff.instances.size();++k)
					{
						ActionTuple instance=aff.instances.get(k);
						String sql="SELECT * FROM Nodes WHERE Name= '"+ aff.name +"' AND AfforderType = '"+ obj.name + "' AND ActionEffect = '"+ instance.effect.toString() + "' AND AffordeeType = '"+ instance.affordee.name +"';";
						if(stmt.execute(sql) && !stmt.getResultSet().first())
						{
							sql="INSERT INTO `Nodes` (`Name`, `AfforderType`, `ActionEffect`, `AffordeeType`) VALUES ('"+ aff.name +"', '"+ obj.name + "', '"+ instance.effect.toString() + "', '"+ instance.affordee.name +"');";
							try
							{
								stmt.execute(sql);
							}
							catch(Exception e)
							{
								System.out.println("The following statement failed: "+sql);
							}
						}
					}
				}
			}
		
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
			if(words.length>2 && words[1].equalsIgnoreCase("is"))
			{
				//words[2] is the type!
				System.out.println("is is used!");
				//search for the Type in current Story World, if doesn't exist, add it
				DigitalObject type=null;
				for(int z=0;z<world.types.size();++z)
				{
					if(world.types.get(z).name.equalsIgnoreCase(words[2]))
						type=world.types.get(z);
				}
				
				if(type==null)
				{
					//Create new type and link
					type=new DigitalObject(words[2]);
					object.ObjectType=type;
					//we also copy all of current object's affordances into the type
					type.affordances= new ArrayList<DigitalAffordance>(object.affordances);
					world.types.add(type);
					//delete all of object's current affordances
					object.affordances.clear();
					object.affordances=object.ObjectType.affordances;
				}
				else
				{
					//the Object Type already existed, we don't create one, we update it with newer affordances that may be possessed by this other object
					//Basically we are updating the Object Type and making it smarter
					type.affordances.addAll(object.affordances);
					object.ObjectType = type;
					object.affordances= object.ObjectType.affordances;
				}
			}
			else{
				
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
							//we found the object, lets try adding its object type name if possible
							if(objects.get(y).ObjectType!=null)
							{
								affordees.add(objects.get(y).ObjectType);
							}
							else{
								affordees.add(objects.get(y));
							}
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
		}
		world.cleanObjectTypes();
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
					System.out.println("Effect: " + tuple.effect.name());
					System.out.println("Active: " + tuple.active);
				}
			}
		}
		System.out.println("Object Types: \n");
		for(int i=0;i<world.types.size();++i)
		{
			DigitalObject obj=world.types.get(i);
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
					System.out.println(" Type: "+ tuple.type.name());
					System.out.println(" Effect: "+ tuple.effect.name());
					System.out.println(" Active: " + tuple.active);
				}
			}
		}
	}
}
