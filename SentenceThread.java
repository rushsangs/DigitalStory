import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class SentenceThread extends Thread implements Runnable{
	public String sentence;
	public SentenceThread next;
	public SentenceThread(String sentence){
		this.sentence = sentence;
		this.next = null;
	}
	@Override
	public void run() {
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
				if(object1correct == true ){
					MyFrame.newobjects.add(oaoparts[0]);
					System.out.println("new object added:" + oaoparts[0]);
				}
				continue;
			}
			if(i == 1){
				actioncorrect = MyFrame.checkNewAffordance(oaoparts[1]);
				continue;
			}
			if( i == 2){
				object2correct = MyFrame.checkNewObject(oaoparts[2]);
				if(object2correct == true ){
					MyFrame.newobjects.add(oaoparts[2]);
					System.out.println("new object added:" + oaoparts[2]);
				}
				continue;
			}
		}
		
		MyFrame.updateStorytxt(newOAO.toString());
//		this.stop();
		return;
		//this.notifyAll();
		//TODO CALL PATRICK FUNCTION FOR POSSIBLE ERROR
//		this.stop();
//		while(!running){
//			continue;
//		}
//		//start handling problems
//		System.out.println("object1correct" + object1correct);
//		System.out.println("object1correct" + actioncorrect);
//		System.out.println("object1correct" + object2correct);
//		if(object1correct == false){
//			String[] types_for_objects1 = new String[1];
//			String[] objects1 = {oaoparts[0]};
//			SelectTypeFrame frame1 = new SelectTypeFrame(objects1, MyFrame.getTypes(), types_for_objects1);
//			frame1.setLocationRelativeTo(null);
//			frame1.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//			frame1.pack();
//			frame1.setVisible(true);
//			try {
//				Files.write(Paths.get(PrologQueryMaster.TYPE_F), 
//						MyFrame.getPrologTypesString(objects1, types_for_objects1).getBytes(), 
//						StandardOpenOption.APPEND);
//			} catch (IOException e1) {
//				e1.printStackTrace();
//			}
//		}
//		if(actioncorrect == false){
//			NLPConnector.convertOAOToProlog(newOAO.toString(), PrologQueryMaster.FACTS_FILE);			}
//		if(object2correct == false && oaoparts.length == 3){
//			String[] types_for_objects1 = new String[1];
//			String[] objects1 = {oaoparts[2]};
//			SelectTypeFrame frame1 = new SelectTypeFrame(objects1, MyFrame.getTypes(), types_for_objects1);
//			frame1.setLocationRelativeTo(null);
//			frame1.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//			frame1.pack();
//			frame1.setVisible(true);
//			try {
//				Files.write(Paths.get(PrologQueryMaster.TYPE_F), 
//						MyFrame.getPrologTypesString(objects1, types_for_objects1).getBytes(), 
//						StandardOpenOption.APPEND);
//			} catch (IOException e1) {
//				e1.printStackTrace();
//			}
//		}
	}

}
