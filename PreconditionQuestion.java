import javax.swing.JFrame;

public class PreconditionQuestion implements Question {
	DigitalStoryWorld world;
	String b;
	String m;
	String e;
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
		// TODO Auto-generated method stub
		String[] bA = b.split("\\n");
		String[] mA = m.split("\\n");
		String[] eA = e.split("\\n");
		
		String[] meA = new String[mA.length + eA.length];
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
		// TODO Auto-generated method stub
		
	}
	
	private String sqlStatement(String name1, String name2) {
		StringBuilder s = new StringBuilder();
		s.append("SELECT * FROM Edges WHERE (StartNode, EndNode) IN (");
		s.append("SELECT n1.NodeId, n2.NodeId FROM Nodes n1, Nodes n2");
		s.append("WHERE n1.Name=");
		s.append('"' + name1 + '"');
		s.append("AND n2.Name=");
		s.append('"' + name2 + '"');
		s.append(");");
		return s.toString();
	}

}
