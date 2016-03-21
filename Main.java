import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Main {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		//initialize frame
		MyFrame frame = new MyFrame();
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
		
		System.out.println("Enter the story in text form");
		BufferedReader br =new BufferedReader(new InputStreamReader(System.in));
		String story=br.readLine();
		printData(analyze(story));
	}
	
	private static ArrayList<DigitalObject> analyze(String story)
	{
		//read the story sentence by sentence, separated by period
		String[] sentences = story.split("\\.");
		
		//traverse through each sentence and build an array of objects first
		ArrayList<DigitalObject> objects= new ArrayList<DigitalObject>();
		
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
			object.addAction(new DigitalAction(words[1]));
			//TODO: third word: what do we do with it?
			
		}
		return objects;
	}
	public static void printData(ArrayList<DigitalObject> objects)
	{
		for(int i =0; i < objects.size(); ++i)
		{
			DigitalObject object=objects.get(i);
			System.out.println("Object is :"+object.name);
			ArrayList<DigitalAction> actions= object.actions;
			for( int j = 0; j<actions.size();++j)
			{
				System.out.println("Action for this object is: "+actions.get(j).name);
			}
		}
	}

}
