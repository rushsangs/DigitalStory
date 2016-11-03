
public class EnterTextThread implements Runnable{
	public SentenceThread root;
	public int lastdelimiter;
	public boolean basecase;
	public boolean running;
	public EnterTextThread(){
		root = null;
		basecase = false;
		running = true;
		lastdelimiter = 0;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while( basecase == false){
			String storystring = MyFrame.getEntertxt();
			if(storystring.indexOf(".") == -1){
				storystring = MyFrame.getEntertxt();
				System.out.println(storystring);
				continue;
			}
			else{
				String substring = storystring.substring(0,storystring.indexOf("."));
				root = new SentenceThread(substring);
				root.run();
				lastdelimiter = storystring.indexOf(".") + 1;
				System.out.println("inside else");
				basecase = true;
			}
		}
		while(!running){
			System.out.println("inside while");
			String storystring = MyFrame.getEntertxt();
			if(storystring.indexOf(".",lastdelimiter) == -1){
				continue;
			}
			else{
				String substring = storystring.substring(lastdelimiter, storystring.indexOf(".",lastdelimiter));
				lastdelimiter = storystring.indexOf(".",lastdelimiter) + 1;
				insertNode(substring);
			}
		}
		
	}
	
	public void stop(){
		this.running = false;
	}
	public void insertNode(String string){
		SentenceThread temp = root;
		while(temp != null){
			temp = temp.next;
		}
		temp = new SentenceThread(string);
		temp.run();
		return;
	}
}
