import java.util.List;

import javax.swing.JTextArea;

public class EnterTextThread implements Runnable {
	public SentenceThread root;
	public int firstdelimiter;
	public int lastdelimiter;
	public boolean basecase;
	public boolean running;
	JTextArea textArea;
	int problemSentenceNo;

	public EnterTextThread() {
		root = null;
		basecase = false;
		running = true;
		lastdelimiter = 0;
		this.problemSentenceNo = -1;
	}

	@Override
	public void run() {
		String storystring;
		System.out.println("Run begins");
		while (MyFrame.twostorytxt == null) {
			continue;
		}
		textArea = MyFrame.twostorytxt;
		while (true) {
			while ((storystring = MyFrame.getEntertxt()) == null) {
				continue;
			}
			String [] sentences = storystring.split("\\.");
//			System.out.println(storystring+ "       qqqqq      ");
			int cursorLocation = MyFrame.twostorytxt.getCaretPosition();
			int cursorCurrentSentence = storystring.substring(0, cursorLocation).split("\\.").length - 1;
//			System.out.println("Cursor current sentence is " + cursorCurrentSentence + " and number of sentences are " + sentences.length);
			if (cursorCurrentSentence == sentences.length - 1) {
				// user is towards the end and is editing things
				while (basecase == false) {
					while (storystring == null) {
						storystring = MyFrame.getEntertxt();
					}
					if (storystring.indexOf(".") == -1) {
						storystring = MyFrame.getEntertxt();
						continue;
					} else {
						String substring = storystring.substring(0, storystring.indexOf("."));
//						System.out.println(substring + "append sentence");
						root = new SentenceThread(substring); // TODO
																// appendsentence
						root.start();
						lastdelimiter = storystring.indexOf(".") + 1;
						basecase = true;
					}
				}
				while (running) {
					// System.out.println("inside while");
					storystring = MyFrame.getEntertxt();
					// System.out.println(storystring);
					// System.out.println(lastdelimiter + "\n");
					if(textArea.getCaretPosition()<lastdelimiter)
						break;
					if (storystring.indexOf(".", lastdelimiter) == -1) {
						storystring = MyFrame.getEntertxt();
						continue;
					} else {
						String substring = storystring.substring(lastdelimiter,
								storystring.indexOf(".", lastdelimiter));
						lastdelimiter = storystring.indexOf(".", lastdelimiter) + 1;
						System.out.println(substring + "append sentence");
						// TODO append
						insertNode(substring);
					}
					

				}
//				System.out.println("broken out");
			} else {
				// user is in the middle and is editing some string with errors
//				System.out.println("editing string");
				storystring = MyFrame.getEntertxt();
				problemSentenceNo = cursorCurrentSentence;
				cursorLocation = textArea.getCaretPosition();
				cursorCurrentSentence = storystring.substring(0, cursorLocation).split("\\.").length - 1;
				while (cursorCurrentSentence == problemSentenceNo) {
					storystring = MyFrame.getEntertxt();
					cursorLocation = textArea.getCaretPosition();
					cursorCurrentSentence = storystring.substring(0, cursorLocation).split("\\.").length - 1;
					continue;
				}
				// sentence editting complete
				String newSentence = storystring.split("\\.")[problemSentenceNo];
				Thread root = new SentenceThread(newSentence);
				root.start();
//				System.out.println(newSentence + " updateSentence");
				// TODO updateSentence
				//TODO replace OAO in UI when changed

			}
		}
	}

	public void stop() {
		this.running = false;
	}

	public void insertNode(String string) {
		SentenceThread temp = root;
		while (temp != null) {
			temp = temp.next;
		}
		temp = new SentenceThread(string);
		temp.start();
		return;
	}
}
