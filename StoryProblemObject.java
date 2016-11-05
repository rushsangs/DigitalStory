public class StoryProblemObject {
	
	public String[] oaoText;
	public String originalNLText;
	public String errorMessage;
	
	public StoryProblemObject(String[] oaoText, String originalNLPText, String errorMessage) {
		this.oaoText = oaoText;
		this.originalNLText = originalNLPText;
		this.errorMessage = errorMessage;
	}
}
