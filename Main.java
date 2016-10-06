import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import javax.swing.JFrame;

public class Main {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		//initialize frame
		//ArrayList<DigitalObject> objects= new ArrayList<DigitalObject>();
		Files.createDirectory(Paths.get(PrologQueryMaster.DIR));
		DigitalStoryWorld world= new DigitalStoryWorld(new ArrayList<DigitalObject>(), new ArrayList<DigitalObject>());
		MyFrame frame = new MyFrame(world);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
//		String[] types = {"human","animal"};
//		String[] characters = {"bob","alice","wolf"};
//		String[] results = new String[characters.length];
//		
//
//		SelectTypeFrame frame1 = new SelectTypeFrame(characters, types, results);
//		frame1.setLocationRelativeTo(null);
//		frame1.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//		frame1.pack();
//		frame1.setVisible(true); 
	}
//		System.out.println("Enter the story in text form");
//		BufferedReader br =new BufferedReader(new InputStreamReader(System.in));
//		String story=br.readLine();
//		printData(analyze(story, objects));
	}
//	
//	private static ArrayList<DigitalObject> analyze(String story, ArrayList<DigitalObject> objects)
//	{
//		//read the story sentence by sentence, separated by period
//		String[] sentences = story.split("\\.");
//		
//
//		
//		//for each sentence: first word is Active Object, second word is action, third word is passive object?
//		for(int i =0;i<sentences.length; ++i)
//		{
//			sentences[i]=sentences[i].trim();
//			String[] words=sentences[i].split(" ");
//			//first word is active object
//			DigitalObject object = null;
//			boolean found=false;
//			for(int j=0; j<objects.size(); ++j)
//			{
//				if(objects.get(j).name.equalsIgnoreCase(words[0]))
//				{
//					object=objects.get(j);
//					found=true;
//					break;
//				}
//			}
//			if(!found)
//			{
//				object = new DigitalObject(words[0]);
//				objects.add(object);
//				
//			}
//			
//			//second word is action, add to active object
//			object.addAction(new DigitalAction(new DigitalAffordance(words[1]), new ArrayList<DigitalObject>(), new DigitalState(words[1].concat(words[2]))));
//			//TODO: third word: what do we do with it?
//			
//		}
//		return objects;
//	}
//	public static void printData(ArrayList<DigitalObject> objects)
//	{
//		for(int i =0; i < objects.size(); ++i)
//		{
//			DigitalObject object=objects.get(i);
//			System.out.println("Object is :"+object.name);
//			ArrayList<DigitalAction> actions= object.actions;
//			for( int j = 0; j<actions.size();++j)
//			{
//				System.out.println("Action for this object is: "+actions.get(j).affordance.name);
//			}
//		}
//	}

