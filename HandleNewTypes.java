import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;

import javax.swing.JFrame;

public class HandleNewTypes implements Runnable{

	public ArrayList<String> objects;
	public boolean running;
	public HandleNewTypes(){
		objects = new ArrayList<String>();
		this.stop();
	}
	@Override
	public void run() {
		while(true){
			while(running){
				if(objects.isEmpty()){
					this.stop();
					break;
				}else{
					String[] types_for_objects1 = new String[1];
					String[] objects1 = (String[]) objects.toArray();
					SelectTypeFrame frame1 = new SelectTypeFrame(objects1, MyFrame.getTypes(), types_for_objects1);
					frame1.setLocationRelativeTo(null);
					frame1.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
					frame1.pack();
					frame1.setVisible(true);
					try {
						Files.write(Paths.get(PrologQueryMaster.TYPE_F), 
								MyFrame.getPrologTypesString(objects1, types_for_objects1).getBytes(), 
								StandardOpenOption.APPEND);
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					objects.clear();
					this.stop();
				}
			}
		}
		
	}
	public void stop(){
		this.running = false;
	}
	public void resume(){
		this.running = true;
	}
}
