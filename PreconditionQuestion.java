import javax.swing.JFrame;

public class PreconditionQuestion implements Question {
	DigitalStoryWorld world;
	String b;
	String m;
	String e;
	String[] bA, mA, eA, meA;
	boolean[][] matrix1;
	boolean[][] matrix2;
	
	public PreconditionQuestion(DigitalStoryWorld world, String b, String m, String e) {
		this.world = world;
		this.b = b;
		this.m = m;
		this.e = e;
	}

	@Override
	public void promptUser() {
		bA = (b.length()==0)? new String[0] : b.split("\\n");
		mA = (m.length()==0)? new String[0] : m.split("\\n");
		eA = (e.length()==0)? new String[0] : e.split("\\n");
		
		meA = new String[mA.length + eA.length];
		int meA_index = 0;
		for (int i = 0; i<mA.length; i++, meA_index++) {
			meA[meA_index] = mA[i];
		}
		for (int i = 0; i<eA.length; i++, meA_index++) {
			meA[meA_index] = eA[i];
		}
		
		matrix1 = new boolean[bA.length][meA.length];
		matrix2 = new boolean[mA.length][eA.length];
		
		PreconditionFrame frame1 = new PreconditionFrame("alskjfs #1", bA, meA, matrix1);
		frame1.setLocationRelativeTo(null);
		frame1.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame1.pack();
		frame1.setVisible(true);
		// matrix1 populated
		
		PreconditionFrame frame2 = new PreconditionFrame("l;kj;ll #2", mA, eA, matrix2);
		frame2.setLocationRelativeTo(null);
		frame2.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame2.pack();
		frame2.setVisible(true);
		// matrix2 populated

	}

	@Override
	public void applyAnswer() {
		// SEND matrix1 TO DATABASE
		for (int i = 0; i<matrix1.length; i++) {
			for (int j = 0; j<matrix1[i].length; j++) {
				if (matrix1[i][j]) {
					DbHelper.inputEdge(bA[i], meA[j]);
				}
			}
		}
	}

}
