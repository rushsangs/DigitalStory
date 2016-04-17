import java.util.ArrayList;

public class DigitalStoryWorld {
	public static final String KEYWORD_IS = "is";
	
	public ArrayList<DigitalObject> objects;
	public ArrayList<DigitalObject> types;
	
	public DigitalStoryWorld(ArrayList<DigitalObject> objects, ArrayList<DigitalObject> types)
	{
		this.objects=objects;
		this.types=types;
	}
}
