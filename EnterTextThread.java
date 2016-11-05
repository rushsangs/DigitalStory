
public class EnterTextThread implements Runnable{
	public SentenceThread root;
	public int firstdelimiter;
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
		String storystring;
		while( basecase == false){
			while(( storystring = MyFrame.getEntertxt()) == null){
				continue;
			}
			if(storystring.indexOf(".") == -1){
				storystring = MyFrame.getEntertxt();
//				System.out.println(storystring);
				continue;
			}
			else{
				String substring = storystring.substring(0,storystring.indexOf("."));
				root = new SentenceThread(substring);
				root.start();
				lastdelimiter = storystring.indexOf(".") + 1;
				//System.out.println("inside else");
				basecase = true;
//				while(root.running == true){
//					System.out.println("here");
//					continue;
//				}
			}
		}
		while(running){
			//System.out.println("inside while");
			storystring = MyFrame.getEntertxt();
			//System.out.println(storystring);
			//System.out.println(lastdelimiter + "\n");
			if(storystring.indexOf(".",lastdelimiter) == -1){
				storystring = MyFrame.getEntertxt();
				continue;
			}
			else{
				String substring = storystring.substring(lastdelimiter, storystring.indexOf(".",lastdelimiter));
				lastdelimiter = storystring.indexOf(".",lastdelimiter) + 1;
				insertNode(substring);
//				root = new SentenceThread(substring);
//				root.run();
//				while(root.running == true){
//					System.out.println("while");
//					continue;
//				}
				
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
		temp.start();
		return;
	}
}
