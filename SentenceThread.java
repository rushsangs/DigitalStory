import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class SentenceThread implements Runnable{
	public String sentence;
	private boolean running;
	public SentenceThread next;
	public SentenceThread(String sentence){
		this.sentence = sentence;
		this.next = null;
	}
	@Override
	public void run() {
		running = true;
		boolean object1correct = false;
		boolean actioncorrect = false;
		boolean object2correct = false;
		StringBuilder newOAO = new StringBuilder(
				NLPConnector.convertNLPToOAO(NLPConnector.analyze(this.sentence)));
		System.out.print("Input in OAO is:  " + newOAO.toString() + "\n");
		String[] oaoparts = newOAO.toString().split("\\s+");
		for(int i = 0; i < oaoparts.length; i++){
			oaoparts[i] = oaoparts[i].toLowerCase();
		}
		for(int i = 0; i < oaoparts.length;i++){
			if(i == 0){
				object1correct = MyFrame.checkNewObject(oaoparts[0]);
				continue;
			}
			if(i == 1){
				actioncorrect = MyFrame.checkNewAffordance(oaoparts[1]);
				continue;
			}
			if( i == 2){
				object2correct = MyFrame.checkNewObject(oaoparts[2]);
				continue;
			}
		}
		MyFrame.updateStorytxt(newOAO.toString());
		//TODO CALL PATRICK FUNCTION FOR POSSIBLE ERROR
		this.stop();
		while(!running){
			continue;
		}
		//start handling problems
		if(object1correct == false){
			String[] types_for_objects1 = new String[1];
			String[] objects1 = {oaoparts[0]};
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
		}
		if(actioncorrect == false){
			NLPConnector.convertOAOToProlog(newOAO.toString(), PrologQueryMaster.FACTS_FILE);			}
		if(object2correct == false && oaoparts.length == 3){
			String[] types_for_objects1 = new String[1];
			String[] objects1 = {oaoparts[2]};
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
		}
		return;
	}
	public void stop(){
		this.running = false;
	}
	
	public void resume(){
		this.running = true;
	}

}
