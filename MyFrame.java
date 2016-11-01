import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
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

import edu.stanford.nlp.io.EncodingPrintWriter.out;

import java.sql.*;

public class MyFrame extends JFrame implements ActionListener {
	StringBuilder storystring = new StringBuilder();
	StringBuilder objectstring = new StringBuilder();
	StringBuilder affordstring = new StringBuilder();
	ArrayList<DigitalObject> objects2;
	static DigitalStoryWorld world;
	public static Connection con;
	public static Statement stmt;
	public static ResultSet resultset;
	// Object panel, left side
	private JPanel objectpanel = new JPanel();
	private JLabel object = new JLabel("Smart Objects");
	private JScrollPane objectpane;
	private JTextArea objectList = new JTextArea(10, 10);
	// Action panel, right side
	private JPanel affordpanel = new JPanel();
	private JScrollPane affordpane;
	private JLabel affordances = new JLabel("Affordances");
	private JTextArea affordancesList = new JTextArea(10, 10);
	// Story panel, center
	private JPanel storytxtpanel = new JPanel();
	private JPanel labelpanel = new JPanel();
	private JLabel storylabel = new JLabel("Input Story");
	private JScrollPane storypane;
	private JTextArea storytxt = new JTextArea(25, 25);
	// Bottom panel
	private JPanel bottom = new JPanel();
	private JPanel enterpanel = new JPanel();
	private JLabel enterlabel = new JLabel("Text Here");
	private JButton enter = new JButton("Enter");
	private JPanel box = new JPanel();
	private JTextField entertxt = new JTextField(50);
	private JPanel generatepanel = new JPanel();
	private JButton addtype = new JButton("Add Type");
	private JButton query = new JButton("Query");
	private JButton generate = new JButton("Generate Graph");
	private JButton clear = new JButton("Clear");
	private JButton getfile = new JButton("Get File");
	private JFileChooser filechooser = new JFileChooser();
	public String[] mylist = new String[]{"Prolog", "Question","Story"};
	private JComboBox<String> mybox = new JComboBox<String>(mylist);
	
	public MyFrame(DigitalStoryWorld world) {
		this.initializeDB();
		this.setTitle("Story World Generator");
		this.world = world;
		objectpanel.setLayout(new BorderLayout(10, 10));
		objectList.setEditable(false);
		objectpanel.add(object, BorderLayout.NORTH);
		objectpane = new JScrollPane(objectList);
		objectpanel.add(objectpane, BorderLayout.CENTER);
		affordpanel.setLayout(new BorderLayout(10, 10));
		affordancesList.setEditable(false);
		affordpanel.add(affordances, BorderLayout.NORTH);
		affordpane = new JScrollPane(affordancesList);
		affordpanel.add(affordpane, BorderLayout.CENTER);
		storytxtpanel.setLayout(new BorderLayout(10, 10));
		storytxt.setEditable(false);
		labelpanel.setLayout(new BorderLayout(10, 10));
		labelpanel.add(storylabel, BorderLayout.CENTER);
		storytxtpanel.add(labelpanel, BorderLayout.NORTH);
		storypane = new JScrollPane(storytxt);
		storytxtpanel.add(storypane, BorderLayout.CENTER);
		enterpanel.setLayout(new BorderLayout(10, 10));
		enterpanel.add(enterlabel, BorderLayout.WEST);
		enterpanel.add(entertxt, BorderLayout.CENTER);
		box.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
		box.add(mybox);
		mybox.addActionListener(this);
		box.add(enter);
		enterpanel.add(box, BorderLayout.EAST);
		enter.addActionListener(this);
		entertxt.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
//				if(mybox.getSelectedItem().equals("Story")){
//					boolean object1correct = false;
//					boolean actioncorrect = false;
//					boolean object2correct = false;
//					System.out.println(entertxt.getText());
//					StringBuilder newOAO = new StringBuilder(
//							QANLPConnector.convertNLPToOAO(QANLPConnector.analyze(entertxt.getText())));
//					System.out.print("Input in OAO is:  " + newOAO.toString() + "\n");
//					String[] oaoparts = newOAO.toString().split(" ");
//					for(int i = 0; i < oaoparts.length; i++){
//						oaoparts[i] = oaoparts[i].toLowerCase();
//					}
//					String[] storyobjects = objectstring.toString().split("\n");
//					String[] storyactions = affordstring.toString().split("\n");
//					if(oaoparts.length == 2){
//						for(int i = 0; i < storyobjects.length; i++){
//							if((oaoparts[0]).equals(storyobjects[i].toLowerCase())){
//								object1correct = true;
//								break;
//							}
//						}
//						for(int i = 0; i < storyactions.length; i++){
//							if((oaoparts[1]).equals(storyactions[i].toLowerCase())){
//								actioncorrect = true;
//								break;
//							}
//						}
//						if(object1correct == false){
//							//newOAO.append(" " + "new object added");
//							objectstring.append(oaoparts[0] + "\n");
//							objectList.setText(objectstring.toString());
//							String[] types_for_objects1 = new String[1];
//							String[] objects1 = {oaoparts[0]};
//							SelectTypeFrame frame1 = new SelectTypeFrame(objects1, getTypes(), types_for_objects1);
//							frame1.setLocationRelativeTo(null);
//							frame1.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//							frame1.pack();
//							frame1.setVisible(true);
//							try {
//								Files.write(Paths.get(PrologQueryMaster.FACTS_FILE), 
//										getPrologTypesString(objects1, types_for_objects1).getBytes(), 
//										StandardOpenOption.APPEND);
//							} catch (IOException e1) {
//								e1.printStackTrace();
//							}
//						}
//						if(actioncorrect == false){
//							//newOAO.append(" " + "new affordance added for " + oaoparts[1]);
//							affordstring.append(oaoparts[1] + "\n");
//							affordancesList.setText(affordstring.toString());
//							NLPConnector.convertOAOToProlog(newOAO.toString(), PrologQueryMaster.FACTS_FILE);
//						}
//						storystring.append(newOAO.toString());
//						entertxt.setText("");
//						storytxt.setText(storystring.toString());
//					}
//					if(oaoparts.length == 3){
//						for(int i = 0; i < storyobjects.length; i++){
//							if((oaoparts[0]).equals(storyobjects[i].toLowerCase())){
//								object1correct = true;
//								break;
//							}
//						}
//						for(int i = 0; i < storyactions.length; i++){
//							System.out.println(storyactions[i]);
//							if((oaoparts[1]).equals(storyactions[i].toLowerCase())){
//								actioncorrect = true;
//								break;
//							}
//						}
//						for(int i = 0; i < storyobjects.length; i++){
//							System.out.println(storyobjects[i]);
//							if((oaoparts[2]).equals(storyobjects[i].toLowerCase())){
//								actioncorrect = true;
//								break;
//							}
//						}
//						if(object1correct == false){
//							//newOAO.append(" " + "new object added");
//							objectstring.append(oaoparts[0] + "\n");
//							objectList.setText(objectstring.toString());
//							String[] types_for_objects1 = new String[1];
//							String[] objects1 = {oaoparts[0]};
//							SelectTypeFrame frame1 = new SelectTypeFrame(objects1, getTypes(), types_for_objects1);
//							frame1.setLocationRelativeTo(null);
//							frame1.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//							frame1.pack();
//							frame1.setVisible(true);
//							try {
//								Files.write(Paths.get(PrologQueryMaster.FACTS_FILE), 
//										getPrologTypesString(objects1, types_for_objects1).getBytes(), 
//										StandardOpenOption.APPEND);
//							} catch (IOException e1) {
//								e1.printStackTrace();
//							}
//						}
//						if(object2correct == false){
//							objectstring.append(oaoparts[2] + "\n");
//							objectList.setText(objectstring.toString());
//							String[] types_for_objects1 = new String[1];
//							String[] objects1 = {oaoparts[2]};
//							SelectTypeFrame frame1 = new SelectTypeFrame(objects1, getTypes(), types_for_objects1);
//							frame1.setLocationRelativeTo(null);
//							frame1.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//							frame1.pack();
//							frame1.setVisible(true);
//							try {
//								Files.write(Paths.get(PrologQueryMaster.FACTS_FILE), 
//										getPrologTypesString(objects1, types_for_objects1).getBytes(), 
//										StandardOpenOption.APPEND);
//							} catch (IOException e1) {
//								e1.printStackTrace();
//							}
//						}
//						if(actioncorrect == false){
//							//newOAO.append(" " + "new affordance added for " + oaoparts[1]);
//							affordstring.append(oaoparts[1] + "\n");
//							affordancesList.setText(affordstring.toString());
//							NLPConnector.convertOAOToProlog(newOAO.toString(), PrologQueryMaster.FACTS_FILE);
//						}
//						storystring.append(newOAO.toString());
//						entertxt.setText("");
//						storytxt.setText(storystring.toString());
//					}else{
//						entertxt.setText("");
//						JOptionPane.showMessageDialog(MyFrame.this,"Input was not able to be converted into a proper format. \n Please modify your setence.");
//					}
//				}
//				String string = entertxt.getText().trim();
//				String[] parts = string.split("\\s+");
//				String[] objects = {};
//				String[] types = { "human", "animal", "none" };
//				String[] affords;
//				String[] types_for_objects = {};
//				if (parts.length < 2) {
//					JOptionPane.showMessageDialog(MyFrame.this, "Incorrect Format: Enter as Object Action Object*");
//					return;
//				}
//				MyFrame.analyze(string, MyFrame.world);
//				storystring.append(string + "\n");
//				for (int i = 0; i < parts.length; i++) {
//					objects = objectstring.toString().split("\\s+");
//					affords = affordstring.toString().split("\\s+");
//					if (i != 1) {
//						// Checks if existing object, if not, then adds to
//						// object string
//						int j;
//						for (j = 0; j < objects.length; j++) {
//							if (parts[i].equals(objects[j])) {
//								break;
//							}
//						}
//						if (j == objects.length) {
//							objectstring.append(parts[i] + "\n");
//							// objectList.setText(objectstring.toString());
//							continue;
//						}
//					}
//					if (i == 1) {
//						// Checks if action is already in the action string, if
//						// not then adds it
//						int k;
//						if (parts[i].equals("is")) {
//							break;
//						}
//						for (k = 0; k < affords.length; k++) {
//							if (parts[i].equals(affords[k])) {
//								break;
//							}
//						}
//						if (k == affords.length) {
//							affordstring.append(parts[i] + "\n");
//							// affordancesList.setText(affordstring.toString());
//							continue;
//						}
//					}
//				}
//				objectList.setText(objectstring.toString());
//				affordancesList.setText(affordstring.toString());
//				entertxt.setText("");
//				SelectTypeFrame frame1 = new SelectTypeFrame(objects, getTypes(), types_for_objects);
//				frame1.setLocationRelativeTo(null);
//				frame1.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//				frame1.pack();
//				frame1.setVisible(true);
//				getPrologTypesString(objects, types_for_objects);
//
//				return;
//			if(mybox.getSelectedItem().equals("Prolog")){
//				PrologQueryMaster pqm = new PrologQueryMaster(PrologQueryMaster.FACTS_FILE);
//				String query = entertxt.getText();
//				String[] firstpart = query.split("\\(");
//				String[] queryparts = firstpart[1].split("\\)|,|\\.");
//				System.out.print(firstpart[0] + " " + queryparts[0] + " " + queryparts[1]);
//				System.out.println(pqm.verify(firstpart[0], queryparts));
//				JOptionPane.showMessageDialog(null,pqm.verify(firstpart[0], queryparts));
//				return;
//			}else{
//				System.out.print("I GOT HERE");
//				return;
//			}
//			}
			}
		});
		generatepanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
		generatepanel.add(query);
		generatepanel.add(addtype);
		addtype.addActionListener(this);
		query.addActionListener(this);
		generatepanel.add(generate);
		generate.addActionListener(this);
		generatepanel.add(clear);
		clear.addActionListener(this);
		generatepanel.add(getfile);
		getfile.addActionListener(this);
		bottom.setLayout(new BorderLayout(10, 10));
		bottom.add(enterpanel, BorderLayout.NORTH);
		bottom.add(generatepanel, BorderLayout.SOUTH);
		this.setLayout(new BorderLayout(10, 10));
		this.add(objectpanel, BorderLayout.WEST);
		this.add(affordpanel, BorderLayout.EAST);
		this.add(storytxtpanel, BorderLayout.CENTER);
		this.add(bottom, BorderLayout.SOUTH);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
		// button enter
		case "Add Type":
			String type = entertxt.getText().trim();
			if(type.equals("")){
				JOptionPane.showMessageDialog(this, "Please enter a type");
				break;
			}
			addType(type);
			entertxt.setText("");
			break;
		case "Query":
			if(mybox.getSelectedItem().equals("Prolog")){
				boolean isDone = false;
				PrologQueryMaster.updateFacts();
				PrologQueryMaster pqm = new PrologQueryMaster(PrologQueryMaster.FACTS_FILE);
				String query = entertxt.getText();
				String[] firstpart = query.split("\\(");
				String[] queryparts = firstpart[1].split("\\)|,|\\.");
				for(int i = 0 ; i < queryparts.length; i++){
					queryparts[i] = queryparts[i].trim();
				}
				System.out.println(firstpart[1].toString() + "******" + "\n");
				for(int i = 0; i < queryparts.length; i++){ //check if case query else do verify
					if(Character.isUpperCase(queryparts[i].codePointAt(0))){
						System.out.println("first part is: " + firstpart[0] + "\n");
						System.out.println("query parts are: " + queryparts[0] + " " + queryparts[1] + " " + queryparts[2]);
						String[][] resultset = pqm.query(firstpart[0], queryparts);
						StringBuilder output = new StringBuilder();
						for(int  j = 0; j < resultset.length; j ++){
							for (int k = 0; k < resultset[j].length; k++){
								if(k == (resultset[j].length-1)){
									output.append(resultset[j][k] + "\n");									
								}else {
									output.append(resultset[j][k] + " ");
								}								
							}
						}
						JOptionPane.showMessageDialog(this, entertxt.getText() + "\n" + output.toString());
						isDone = true;
						entertxt.setText("");
						break;
					}
				}
				if(isDone == false){
					System.out.print(firstpart[0] + " " + queryparts[0] + " " + queryparts[1]);
					System.out.println(pqm.verify(firstpart[0], queryparts));
					JOptionPane.showMessageDialog(this,entertxt.getText() + "\n" + pqm.verify(firstpart[0], queryparts));
					entertxt.setText("");
					break;
				}
				break;
			}
			else if(mybox.getSelectedItem().equals("Question")){
				boolean isDone = false;
				System.out.println(entertxt.getText());
				StringBuilder questionOAO = new StringBuilder(
						QANLPConnector.convertNLPToOAO(QANLPConnector.analyze(entertxt.getText())));
				System.out.print("Query in OAO is:  " + questionOAO.toString() + "\n");
				String[] oaoparts = questionOAO.toString().split(" ");
				if(oaoparts.length == 2){
					for(int i = 0; i < oaoparts.length; i++){
						if(oaoparts[i].toLowerCase().contains("who")){
							oaoparts[i] = "X";
							PrologQueryMaster.updateFacts();
							PrologQueryMaster pqm = new PrologQueryMaster(PrologQueryMaster.FACTS_FILE);
							String firstpart1 = "trait";
							String[][] resultset = pqm.query(firstpart1, oaoparts);
							StringBuilder output = new StringBuilder();
							for(int  j = 0; j < resultset.length; j ++){
								for (int k = 0; k < resultset[j].length; k++){
									if(k == (resultset[j].length-1)){
										output.append(resultset[j][k] + "\n");									
									}else {
										output.append(resultset[j][k] + " ");
									}								
								}
							}
							JOptionPane.showMessageDialog(this, entertxt.getText() + "\n" + output.toString());
							isDone = true;
							entertxt.setText("");
							break;
						}
					}
					if(isDone == false){
					//case  WAS with AMOD
						PrologQueryMaster.updateFacts();
						PrologQueryMaster pqm = new PrologQueryMaster(PrologQueryMaster.FACTS_FILE);
						String firstpart1 = "trait";
						JOptionPane.showMessageDialog(this, entertxt.getText() + pqm.verify(firstpart1, oaoparts));
						entertxt.setText("");
					}
					break;
				}
				if( questionOAO.toString().toLowerCase().contains("who")){
					for(int i = 0; i < oaoparts.length; i++){
						if(oaoparts[i].toLowerCase().equals("who")){
							oaoparts[i] = "X";
							break;
						}
					}
					PrologQueryMaster.updateFacts();
					PrologQueryMaster pqm = new PrologQueryMaster(PrologQueryMaster.FACTS_FILE);
					String firstpart1 = "action";
					System.out.println("case WHO oao to query: " + oaoparts[0] + " " + oaoparts[1] + " " + oaoparts[2] );
					String[][] resultset = pqm.query(firstpart1, oaoparts);
					StringBuilder output = new StringBuilder();
					for(int  j = 0; j < resultset.length; j ++){
						for (int k = 0; k < resultset[j].length; k++){
							if(k == (resultset[j].length-1)){
								output.append(resultset[j][k] + "\n");									
							}else {
								output.append(resultset[j][k] + " ");
							}								
						}
					}
					JOptionPane.showMessageDialog(this, entertxt.getText() + "\n" + output.toString());
					entertxt.setText("");
					break;
				}
				if( questionOAO.toString().toLowerCase().contains("what")){
					for(int i = 0; i < oaoparts.length; i++){
						if(oaoparts[i].toLowerCase().equals("what")){
							oaoparts[i] = "X";
							break;
						}
					}
					PrologQueryMaster.updateFacts();
					PrologQueryMaster pqm = new PrologQueryMaster(PrologQueryMaster.FACTS_FILE);
					String firstpart1 = "action";
					System.out.println("case WHAT oao to query: " + oaoparts[0] + " " + oaoparts[1] + " " + oaoparts[2] );
					String[][] resultset = pqm.query(firstpart1, oaoparts);
					StringBuilder output = new StringBuilder();
					for( int j = 0; j < resultset.length; j++){
						for(int k = 0; k < resultset[j].length; k++){
							if(k == (resultset[j].length -1)){
								output.append(resultset[j][k] + "\n");
							}else {
								output.append(resultset[j][k] + " ");
							}
						}
					}
					JOptionPane.showMessageDialog(this, entertxt.getText() + "\n" + output.toString());
					entertxt.setText("");
					break;
				}
				if( questionOAO.toString().toLowerCase().contains("what")){
					for(int i = 0; i < oaoparts.length; i++){
						if(oaoparts[i].toLowerCase().equals("what")){
							oaoparts[i] = "X";
							break;
						}
				}
				PrologQueryMaster.updateFacts();
				PrologQueryMaster pqm = new PrologQueryMaster(PrologQueryMaster.FACTS_FILE);
				String firstpart1 = "action";
				System.out.println("case WHAT oao to query: " + oaoparts[0] + " " + oaoparts[1] + " " +  oaoparts[2] );
				String[][] resultset = pqm.query(firstpart1, oaoparts);
				StringBuilder output = new StringBuilder();
				for( int j = 0; j < resultset.length; j++){
					for(int k = 0; k < resultset[j].length; k++){
						if(k == (resultset[j].length -1)){
							output.append(resultset[j][k] + "\n");
						}else {
							output.append(resultset[j][k] + " ");
						}
					}
				}
				JOptionPane.showMessageDialog(this, entertxt.getText() + "\n" + output.toString());
				entertxt.setText("");
				break;
				}
				if( questionOAO.toString().toLowerCase().contains("why")){
					//case using precondition
					//TODO
					break;
				}else { //this falls through to DID DOES for action
					String firstpart1 ="action";
					PrologQueryMaster.updateFacts();
					PrologQueryMaster pqm = new PrologQueryMaster(PrologQueryMaster.FACTS_FILE);
					JOptionPane.showMessageDialog(this, entertxt.getText() + pqm.verify(firstpart1, oaoparts));
					entertxt.setText("");
				}
				break;
			}
			//
			
		case "Enter":
			if(mybox.getSelectedItem().equals("Story")){
				boolean object1correct = false;
				boolean actioncorrect = false;
				boolean object2correct = false;
				System.out.println(entertxt.getText());
				StringBuilder newOAO = new StringBuilder(
						QANLPConnector.convertNLPToOAO(QANLPConnector.analyze(entertxt.getText())));
				System.out.print("Input in OAO is:  " + newOAO.toString() + "\n");
				String[] oaoparts = newOAO.toString().split(" ");
				for(int i = 0; i < oaoparts.length; i++){
					oaoparts[i] = oaoparts[i].toLowerCase();
				}
				String[] storyobjects = objectstring.toString().split("\n");
				String[] storyactions = affordstring.toString().split("\n");
				if(oaoparts.length == 2){
					for(int i = 0; i < storyobjects.length; i++){
						if((oaoparts[0]).equals(storyobjects[i].toLowerCase())){
							object1correct = true;
							break;
						}
					}
					for(int i = 0; i < storyactions.length; i++){
						if((oaoparts[1]).equals(storyactions[i].toLowerCase())){
							actioncorrect = true;
							break;
						}
					}
					if(object1correct == false){
						//newOAO.append(" " + "new object " + oaoparts[0] + " added \n");
						objectstring.append(oaoparts[0] + "\n");
						objectList.setText(objectstring.toString());
						String[] types_for_objects1 = new String[1];
						String[] objects1 = {oaoparts[0]};
						SelectTypeFrame frame1 = new SelectTypeFrame(objects1, getTypes(), types_for_objects1);
						frame1.setLocationRelativeTo(null);
						frame1.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
						frame1.pack();
						frame1.setVisible(true);
						try {
							Files.write(Paths.get(PrologQueryMaster.TYPE_F), 
									getPrologTypesString(objects1, types_for_objects1).getBytes(), 
									StandardOpenOption.APPEND);
						} catch (IOException e1) {
							e1.printStackTrace();
						}
					}
					if(actioncorrect == false){
						//newOAO.append(" " + "new affordance " + oaoparts[1] + " added \n");
						affordstring.append(oaoparts[1] + "\n");
						affordancesList.setText(affordstring.toString());
						NLPConnector.convertOAOToProlog(newOAO.toString(), PrologQueryMaster.FACTS_FILE);
					}
					storystring.append(newOAO.toString());
					entertxt.setText("");
					storytxt.setText(storystring.toString());
					break;
				}
				if(oaoparts.length == 3){
					for(int i = 0; i < storyobjects.length; i++){
						if((oaoparts[0]).equals(storyobjects[i].toLowerCase())){
							object1correct = true;
							break;
						}
					}
					if(object1correct == false){
						//newOAO.append(" " + "new object " + oaoparts[0] + " added \n");
						objectstring.append(oaoparts[0] + "\n");
						objectList.setText(objectstring.toString());
						String[] types_for_objects1 = new String[1];
						String[] objects1 = {oaoparts[0]};
						SelectTypeFrame frame1 = new SelectTypeFrame(objects1, getTypes(), types_for_objects1);
						frame1.setLocationRelativeTo(null);
						frame1.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
						frame1.pack();
						frame1.setVisible(true);
						try {
							Files.write(Paths.get(PrologQueryMaster.TRAIT_F), 
									getPrologTypesString(objects1, types_for_objects1).getBytes(), 
									StandardOpenOption.APPEND);
						} catch (IOException e1) {
							e1.printStackTrace();
						}
					}
					for(int i = 0; i < storyactions.length; i++){
						if((oaoparts[1]).equals(storyactions[i].toLowerCase())){
							actioncorrect = true;
							break;
						}
					}
					for(int i = 0; i < storyobjects.length; i++){
						if((oaoparts[2]).equals(storyobjects[i].toLowerCase())){
							object2correct = true;
							break;
						}
					}
					if(object1correct == false){
						//newOAO.append(" " + "new object " + oaoparts[0] + " added \n");
						objectstring.append(oaoparts[0] + "\n");
						objectList.setText(objectstring.toString());
						String[] types_for_objects1 = new String[1];
						String[] objects1 = {oaoparts[0]};
						SelectTypeFrame frame1 = new SelectTypeFrame(objects1, getTypes(), types_for_objects1);
						frame1.setLocationRelativeTo(null);
						frame1.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
						frame1.pack();
						frame1.setVisible(true);
						try {
							Files.write(Paths.get(PrologQueryMaster.TRAIT_F), 
									getPrologTypesString(objects1, types_for_objects1).getBytes(), 
									StandardOpenOption.APPEND);
						} catch (IOException e1) {
							e1.printStackTrace();
						}
					}
					if(object2correct == false){
						//newOAO.append(" " + "new object " + oaoparts[2] + " added \n");
						if(oaoparts[0].trim().equals(oaoparts[2].trim())){
							break;
						}
						objectstring.append(oaoparts[2] + "\n");
						objectList.setText(objectstring.toString());
						String[] types_for_objects1 = new String[1];
						String[] objects1 = {oaoparts[2]};
						SelectTypeFrame frame1 = new SelectTypeFrame(objects1, getTypes(), types_for_objects1);
						frame1.setLocationRelativeTo(null);
						frame1.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
						frame1.pack();
						frame1.setVisible(true);
						try {
							Files.write(Paths.get(PrologQueryMaster.TRAIT_F), 
									getPrologTypesString(objects1, types_for_objects1).getBytes(), 
									StandardOpenOption.APPEND);
						} catch (IOException e1) {
							e1.printStackTrace();
						}
					}
					if(actioncorrect == false){
						//newOAO.append(" " + "new affordance " + oaoparts[1] + " added \n");
						affordstring.append(oaoparts[1] + "\n");
						affordancesList.setText(affordstring.toString());
						NLPConnector.convertOAOToProlog(newOAO.toString(), PrologQueryMaster.FACTS_FILE);
					}
					storystring.append(newOAO.toString() + "\n");
					entertxt.setText("");
					storytxt.setText(storystring.toString());
					break;
				}else{
					entertxt.setText("");
					JOptionPane.showMessageDialog(MyFrame.this,"Input was not able to be converted into a proper format. \n Please modify your setence.");
				}
				break;
			}
//			if(mybox.getSelectedItem().equals("Prolog")){
//				PrologQueryMaster pqm = new PrologQueryMaster(PrologQueryMaster.FACTS_FILE);
//				String query = entertxt.getText();
//				String[] firstpart = query.split("\\(");
//				String[] queryparts = firstpart[1].split("\\)|,|\\.");
//				System.out.print(firstpart[0] + " " + queryparts[0] + " " + queryparts[1]);
//				System.out.println(pqm.verify(firstpart[0], queryparts));
//				JOptionPane.showMessageDialog(this,pqm.verify(firstpart[0], queryparts));
//				break;
//			}
//			else if(mybox.getSelectedItem().equals("Question")){
//				System.out.println(entertxt.getText());
//				StringBuilder questionOAO = new StringBuilder(
//						QANLPConnector.convertNLPToOAO(QANLPConnector.analyze(entertxt.getText())));
//				System.out.print("Query in OAO is:  " + questionOAO.toString() + "\n");
//				String[] oaoparts = questionOAO.toString().split(" ");
//				if(oaoparts.length == 2){
//					//case  WAS with AMOD
//					PrologQueryMaster pqm = new PrologQueryMaster(PrologQueryMaster.FACTS_FILE);
//					String firstpart1 = "trait";
//					JOptionPane.showMessageDialog(this, entertxt.getText() + pqm.verify(firstpart1, oaoparts));
//					entertxt.setText("");
//					break;
//				}
//				if( questionOAO.toString().toLowerCase().contains("who")){
//					for(int i = 0; i < oaoparts.length; i++){
//						if(oaoparts[i].toLowerCase().equals("who")){
//							oaoparts[i] = "X";
//							break;
//						}
//					}
//					PrologQueryMaster pqm = new PrologQueryMaster(PrologQueryMaster.FACTS_FILE);
//					String firstpart1 = "action";
//					JOptionPane.showMessageDialog(this, entertxt.getText() + pqm.verify(firstpart1, oaoparts));
//					entertxt.setText("");
//					break;
//				}
//				if( questionOAO.toString().toLowerCase().contains("what")){
//					for(int i = 0; i < oaoparts.length; i++){
//						if(oaoparts[i].toLowerCase().equals("what")){
//							oaoparts[i] = "X";
//							break;
//						}
//					}
//					PrologQueryMaster pqm = new PrologQueryMaster(PrologQueryMaster.FACTS_FILE);
//					String firstpart1 = "action";
//					JOptionPane.showMessageDialog(this, entertxt.getText() + pqm.verify(firstpart1, oaoparts));
//					entertxt.setText("");
//					break;
//				}else { //this falls through to DID DOES AND WAS for action
//					String firstpart1 ="action";
//					PrologQueryMaster pqm = new PrologQueryMaster(PrologQueryMaster.FACTS_FILE);
//					JOptionPane.showMessageDialog(this, entertxt.getText() + pqm.verify(firstpart1, oaoparts));
//					entertxt.setText("");
//				}
//				break;
//			}			
		case "Clear":
			world = new DigitalStoryWorld(new ArrayList<DigitalObject>(), new ArrayList<DigitalObject>());
			objectstring = new StringBuilder();
			affordstring = new StringBuilder();
			storystring = new StringBuilder();
			objectList.setText("");
			affordancesList.setText("");	
			break;
		case "Get File": 
			try {
				if (filechooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
					File file = filechooser.getSelectedFile();
					Scanner input = new Scanner(file);
					String storytxt = "";
					while (input.hasNextLine()) {
						storytxt += input.nextLine();
					}
					input.close();
					StringBuilder storystring2 = new StringBuilder(
							NLPConnector.convertNLPToOAO(NLPConnector.analyze(storytxt, "abc.txt")));
					System.out.println("OAO facts are : \n" + storystring2.toString());
					// ???????
					NLPConnector.convertOAOToProlog(storystring2.toString(), PrologQueryMaster.FACTS_FILE);
					this.storytxt.setText(storystring2.toString().replace("_",""));
					String[] parts1 = storystring2.toString().split("\\n+");
					String[] objects1 = {};
					String[] affords1;
					analyze(storystring2.toString(), world);
					storystring = storystring2;

					for (int j = 0; j < parts1.length; j++) {
						String[] parts2 = parts1[j].split("\\s+");
						for (int i = 0; i < 3; ++i) {
							objects1 = objectstring.toString().split("\\s+");
							affords1 = affordstring.toString().split("\\s+");

							if (i != 1 && parts2.length !=2) {

								// Checks if existing object, if not, then adds
								// to object string
								int k;
								for (k = 0; k < objects1.length; k++) {
									if (parts2[i].equals(objects1[k])) {
										break;
									}
								}
								if (k == objects1.length) {
									objectstring.append(parts2[i] + "\n");
									// objectList.setText(objectstring.toString());
									continue;
								}
							}
							if (i == 1) {
								// Checks if action is already in the action
								// string, if not then adds it
								int k;
								if (parts2[i].equals("is")) {
									break;
								}
								for (k = 0; k < affords1.length; k++) {
									if (parts2[i].equals(affords1[k])) {
										break;
									}
								}
								if (k == affords1.length) {
									affordstring.append(parts2[i] + "\n");
									// affordancesList.setText(affordstring.toString());
									continue;
								}
							}
						}
					}

					objectList.setText(objectstring.toString());
					affordancesList.setText(affordstring.toString());

					String[] types_for_objects1 = new String[objects1.length];
					SelectTypeFrame frame1 = new SelectTypeFrame(objects1, getTypes(), types_for_objects1);
					frame1.setLocationRelativeTo(null);
					frame1.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
					frame1.pack();
					frame1.setVisible(true);
					Files.write(Paths.get(PrologQueryMaster.FACTS_FILE), 
							getPrologTypesString(objects1, types_for_objects1).getBytes(), 
							StandardOpenOption.APPEND);
				}
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			break;
		case "Generate Graph":
			try {
				createNodes(world);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			MyQuestions.ask(world, storystring.toString());
			printOutWorld(world);
			GraphVisualizer gv = new GraphVisualizer(DbHelper.getAllNodes(), DbHelper.getAllEdges());
			gv.visualize();
		}
	}

	public String removeIsFromString(String storysection) {
		String[] lines = storysection.split("\\n");
		String newString = "";
		for (int i = 0; i < lines.length; i++) {
			if (lines[i].contains(" is ")) {
				continue;
			}
			newString += lines[i] + "\n";
		}
		return newString;
	}

	public String getAffordances() {
		return affordstring.toString();
	}

	public String getObjects() {
		return objectstring.toString();
	}

	public String getStory() {
		return storystring.toString();
	}

	private void initializeDB() {
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			System.out.println("Driver loaded\n");
		} catch (Exception e) {
			e.printStackTrace();
		}
		con = null;
		String url = "jdbc:mysql://storyuniversedb.c99bh6mgf9xw.us-west-2.rds.amazonaws.com:3306/";
		String user = "root";
		String password = "group5group5";
		String dbname = "mydb";
		try {
			con = DriverManager.getConnection(url + dbname, user, password);
			System.out.println("Database connected\n");
		} catch (SQLException e) {
			System.err.println("SQLException: " + e.getMessage());
			// System.err.println("SQLState: " + e,getSQLState());
		}
		try {
			stmt = con.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public String sqlStatement(String name1, String name2) {
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

	// this function creates Nodes for ALL affordances for the types
	private void createNodes(DigitalStoryWorld world) throws SQLException {
		System.out.println("yo");
		DigitalObject ObjectType;
		// for each Type, it checks if the type exists. If doesn't exist, insert
		// it.
		for (int i = 0; i < world.types.size(); ++i) {
			ObjectType = world.types.get(i);
			String sql = "SELECT * FROM Types WHERE Name = '" + ObjectType.name + "';";
			if (stmt.execute(sql) && !stmt.getResultSet().first()) {
				// we didn't get any rows, insert type.
				sql = "INSERT INTO Types(Name) VALUES('" + ObjectType.name.trim() + "');";
				try {
					stmt.execute(sql);
				} catch (Exception e) {
					System.out.println("The following statement failed: " + sql);
				}
			}

		}
		// for all digital objects that don't have a type
		for (int i = 0; i < world.objects.size(); ++i) {
			if (world.objects.get(i).ObjectType != null)
				continue;
			// add types if they don't exist
			DigitalObject obj = world.objects.get(i);
			String sql = "SELECT * FROM Types WHERE Name = '" + obj.name + "';";
			if (stmt.execute(sql) && !stmt.getResultSet().first()) {
				// we didn't get any rows, insert type.
				sql = "INSERT INTO Types(Name) VALUES('" + obj.name.trim() + "');";
				try {
					stmt.execute(sql);
				} catch (Exception e) {
					System.out.println("The following statement failed: " + sql);
				}
			}

		}
		for (int i = 0; i < world.types.size(); ++i) {
			// now that the type exists, add nodes for each type's affordance's
			// tuple
			String sql;
			ObjectType = world.types.get(i);
			for (int j = 0; j < ObjectType.affordances.size(); ++j) {
				DigitalAffordance aff = ObjectType.affordances.get(j);
				for (int k = 0; k < aff.instances.size(); ++k) {
					ActionTuple instance = aff.instances.get(k);
					sql = "SELECT * FROM Nodes WHERE Name= '" + aff.name + "' AND AfforderType = '" + ObjectType.name
							+ "' AND ActionEffect = '" + instance.effect.toString() + "' AND AffordeeType = '"
							+ instance.affordee.name + "';";
					if (stmt.execute(sql) && !stmt.getResultSet().first()) {
						sql = "INSERT INTO `Nodes` (`Name`, `AfforderType`, `ActionEffect`, `AffordeeType`) VALUES ('"
								+ aff.name + "', '" + ObjectType.name + "', '" + instance.effect.toString() + "', '"
								+ instance.affordee.name + "');";
						try {
							stmt.execute(sql);
						} catch (Exception e) {
							System.out.println("The following statement failed: " + sql);
						}
					}
				}
			}
		}
		for (int i = 0; i < world.objects.size(); ++i) {
			if (world.objects.get(i).ObjectType != null)
				continue;
			DigitalObject obj = world.objects.get(i);
			// we add nodes for all the unique affordance tuples for this object
			for (int j = 0; j < obj.affordances.size(); ++j) {
				DigitalAffordance aff = obj.affordances.get(j);
				for (int k = 0; k < aff.instances.size(); ++k) {
					ActionTuple instance = aff.instances.get(k);
					String sql = "SELECT * FROM Nodes WHERE Name= '" + aff.name + "' AND AfforderType = '" + obj.name
							+ "' AND ActionEffect = '" + instance.effect.toString() + "' AND AffordeeType = '"
							+ instance.affordee.name + "';";
					if (stmt.execute(sql) && !stmt.getResultSet().first()) {
						sql = "INSERT INTO `Nodes` (`Name`, `AfforderType`, `ActionEffect`, `AffordeeType`) VALUES ('"
								+ aff.name + "', '" + obj.name + "', '" + instance.effect.toString() + "', '"
								+ instance.affordee.name + "');";
						try {
							stmt.execute(sql);
						} catch (Exception e) {
							System.out.println("The following statement failed: " + sql);
						}
					}
				}
			}
		}

	}

	private static ArrayList<DigitalObject> analyze(String story, DigitalStoryWorld world) {
		ArrayList<DigitalObject> objects = world.objects;
		// read the story sentence by sentence, separated by period
		String[] sentences = story.split("\\.");

		// for each sentence: first word is Active Object, second word is
		// action, third word is passive object?
		for (int i = 0; i < sentences.length; ++i) {
			sentences[i] = sentences[i].trim();
			String[] words = sentences[i].split(" ");
			// first word is active object
			DigitalObject object = null;
			boolean found = false;
			for (int j = 0; j < objects.size(); ++j) {
				if (objects.get(j).name.equalsIgnoreCase(words[0])) {
					object = objects.get(j);
					found = true;
					break;
				}
			}
			if (!found) {
				object = new DigitalObject(words[0]);
				objects.add(object);

			}

			// if second word is "is" IT IS NOT AN AFFORDANCE
			if (words.length > 2 && words[1].equalsIgnoreCase("is")) {
				// words[2] is the type!
				System.out.println("is is used!");
				// search for the Type in current Story World, if doesn't exist,
				// add it
				DigitalObject type = null;
				for (int z = 0; z < world.types.size(); ++z) {
					if (world.types.get(z).name.equalsIgnoreCase(words[2]))
						type = world.types.get(z);
				}

				if (type == null) {
					// Create new type and link
					type = new DigitalObject(words[2]);
					object.ObjectType = type;
					// we also copy all of current object's affordances into the
					// type
					type.affordances = new ArrayList<DigitalAffordance>(object.affordances);
					world.types.add(type);
					// delete all of object's current affordances
					object.affordances.clear();
					object.affordances = object.ObjectType.affordances;
				} else {
					// the Object Type already existed, we don't create one, we
					// update it with newer affordances that may be possessed by
					// this other object
					// Basically we are updating the Object Type and making it
					// smarter
					type.affordances.addAll(object.affordances);
					object.ObjectType = type;
					object.affordances = object.ObjectType.affordances;
				}
			} else {

				ArrayList<DigitalObject> affordees = new ArrayList<DigitalObject>();
				if (words.length > 2) {
					// add affordees to the object's action
					for (int x = 2; x < words.length; ++x) {
						boolean fl = false;
						for (int y = 0; y < objects.size(); ++y) {
							if (objects.get(y).name.equalsIgnoreCase(words[x])) {
								// we found the object, lets try adding its
								// object type name if possible
								if (objects.get(y).ObjectType != null) {
									affordees.add(objects.get(y).ObjectType);
								} else {
									affordees.add(objects.get(y));
								}
								fl = true;
							}
						}
						if (!fl) {
							objects.add(new DigitalObject(words[x]));
							affordees.add(objects.get(objects.size() - 1));
						}

					}
				} else {
					// this is a sentence like "Wolf dies", we add emptyObject
					// to the affordees instead.
					affordees.add(DigitalObject.EMPTY_OBJECT);
				}

				// second word is action, add to active object
				DigitalAffordance affordance = new DigitalAffordance(words[1]);
				affordance.addActionTuple(affordees, ActionType.NORM);
				object.addAffordance(affordance);

			}
		}
		world.cleanObjectTypes();
		return objects;
	}

	public void printOutWorld(DigitalStoryWorld world) {
		System.out.println("Objects: \n");
		for (int i = 0; i < world.objects.size(); ++i) {
			DigitalObject obj = world.objects.get(i);
			System.out.println("\nNAME: " + obj.name);
			// object contains affordances too!
			for (int j = 0; j < obj.affordances.size(); ++j) {
				DigitalAffordance aff = obj.affordances.get(j);
				System.out.println("Affordance: " + obj.affordances.get(j).name);
				// affordance has tuples, etc
				for (int k = 0; k < aff.instances.size(); ++k) {
					ActionTuple tuple = aff.instances.get(k);
					System.out.println("Affordee: " + tuple.affordee.name);
					System.out.println("Type: " + tuple.type.name());
					System.out.println("Effect: " + tuple.effect.name());
					System.out.println("Active: " + tuple.active);
				}
			}
		}
		System.out.println("Object Types: \n");
		for (int i = 0; i < world.types.size(); ++i) {
			DigitalObject obj = world.types.get(i);
			System.out.println("\nNAME: " + obj.name);
			// object contains affordances too!
			for (int j = 0; j < obj.affordances.size(); ++j) {
				DigitalAffordance aff = obj.affordances.get(j);
				System.out.println("Affordance: " + obj.affordances.get(j).name);
				// affordance has tuples, etc
				for (int k = 0; k < aff.instances.size(); ++k) {
					ActionTuple tuple = aff.instances.get(k);
					System.out.println("Affordee: " + tuple.affordee.name);
					System.out.println(" Type: " + tuple.type.name());
					System.out.println(" Effect: " + tuple.effect.name());
					System.out.println(" Active: " + tuple.active);
				}
			}
		}
	}

	/**
	 * Creates prolog type statements and returns the string Prolog type
	 * statements are of the form: type(Character, Type).
	 */
	public String getPrologTypesString(String[] characters, String[] types) {
		String result = "";
		for (int i = 0; i < characters.length; ++i) {
			result += "type(" + characters[i].toLowerCase() + "," + types[i].toLowerCase() + ").\n";
		}
		out.println("Prolog type statements are: \n" + result);
		return result;
	}
	public void addType(String type){
		try {
			resultset = stmt.executeQuery("SELECT Name " +
										  "FROM Types");
			while(resultset.next()) {
				if(type.equals(resultset.getString(1))){
					System.out.print("type already exists");
					return;
				}
			}
			String sql = "INSERT INTO Types(Name) VALUES('" + type + "');";
			stmt.execute(sql);
			System.out.print("Type " + type + "inserted");
			return;
		
		}catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public String[] getTypes(){
		int numOfRows = 0;
		int i = 0;
		String[] types = null;
		try{
			resultset = stmt.executeQuery("SELECT Name " +
										  "FROM Types");
			
			while(resultset.next()){
				//types[i] = resultset.getString(1);
				numOfRows++;
			}
			types = new String[numOfRows];
			System.out.println(numOfRows);
			resultset = stmt.executeQuery("SELECT Name " +
					  "FROM Types");

			while(resultset.next()){
				types[i] = resultset.getString(1);
				i++;
			}
			return types;
		}catch (SQLException e){
			e.printStackTrace();
		}
		return types;
	}
}
