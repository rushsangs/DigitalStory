import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class PreconditionFrame extends JDialog implements ActionListener{
	private JPanel checkboxgrid = new JPanel();
	private JButton done = new JButton("OK");
	private JPanel bottom = new JPanel();
	private JCheckBox[] mycheckboxes;
	private JLabel[] leftstring;
	private JLabel[] topstring;
	private JLabel storyline = new JLabel("Story Line");
	private JLabel question;
	public boolean[][] matrix;
	public boolean isDone;
	public PreconditionFrame(String question, String[] left, String[] top, boolean[][] matrix){
		this.matrix = matrix;
		this.question = new JLabel(question);
		isDone = false;
		for(int i =0; i < left.length; i++){
			leftstring[i] = new JLabel(left[i]);
		}
		for(int i = 0; i< top.length; i++){
			topstring[i] = new JLabel(top[i]);
		}
		this.setLayout(new BorderLayout(10,10));
		checkboxgrid.setLayout(new GridLayout(left.length+1,top.length+1));
		checkboxgrid.add(storyline);
		int j = 0;
		for(int i =0; i< top.length; i++){
			checkboxgrid.add(topstring[i]);
		}
		for(int i = 0; i< left.length;i++){
			checkboxgrid.add(leftstring[i]);
			int temp = top.length +j;
			for(j =j; j< temp; j++){
				mycheckboxes[j]= new JCheckBox();
				mycheckboxes[j].setVisible(true);
				mycheckboxes[j].setSelected(false);
				checkboxgrid.add(mycheckboxes[j]);
			}
		}
		bottom.setLayout(new FlowLayout(FlowLayout.CENTER));
		done.addActionListener(this);
		bottom.add(done);
		this.add(this.question,BorderLayout.NORTH);
		this.add(checkboxgrid, BorderLayout.CENTER);
		this.add(bottom, BorderLayout.SOUTH);
		
		
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		switch(e.getActionCommand()){
		case "OK":
			int k = 0;
			for(int i = 0; i< leftstring.length;i++){
				int temp = topstring.length +k;
				for(int j = 0; j< temp; j++){
					matrix[i][j] = mycheckboxes[j].isSelected();
					k++;					
				}
			}
			this.isDone = true;
			this.dispose();
		}
	}
}
