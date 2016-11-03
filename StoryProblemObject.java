
public class StoryProblemObject {
	public String[] oao;
	public String[] gettypes;
	public boolean type;
	public boolean error;
	StoryProblemObject next;
	
	public StoryProblemObject(String OAO,String[] gettypes, boolean type, boolean error){
		this.oao = OAO.toString().split("\\s+");
		this.gettypes = gettypes;
		this.type = type;
		this.error = error;
		next = null;
	}
}
