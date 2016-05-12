import java.sql.*;

public class DbHelper {
	static Statement st = MyFrame.stmt;
	static ResultSet rs = MyFrame.resultset;
	
	public static void updateActionEffect(
			ActionBundle action) {
		try {
			st.execute("UPDATE Nodes SET `ActionEffect`='" +
					action.instance.effect.name() +
					"' WHERE `Name`='" +  action.affordance.name +
					"' AND `AfforderType`='" + ActionBundle.getTypeName(action.actor) +
					"' AND `AffordeeType`='" + 
					ActionBundle.getTypeName(action.instance.affordee) + "';");
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void inputEdge(String name1, String name2) {
		StringBuilder s = new StringBuilder();
		s.append("SELECT * FROM Edges WHERE (StartNode, EndNode) IN (");
		s.append("SELECT n1.NodeId, n2.NodeId FROM Nodes n1, Nodes n2");
		s.append("WHERE n1.Name=");
		s.append('"' + name1 + '"');
		s.append("AND n2.Name=");
		s.append('"' + name2 + '"');
		s.append(");");
		try {
			rs = st.executeQuery(s.toString());
			if (rs.next()) {
				s = new StringBuilder();
				s.append("UPDATE Edges SET Count=Count+1 WHERE (StartNode, EndNode) IN (");
				s.append("SELECT n1.NodeId, n2.NodeId FROM Nodes n1, Nodes n2");
				s.append("WHERE n1.Name=");
				s.append('"' + name1 + '"');
				s.append("AND n2.Name=");
				s.append('"' + name2 + '"');
				s.append(");");
				st.execute(s.toString());
			} else {
				s = new StringBuilder();
				s.append("INSERT INTO Edges VALUES(StartNode, EndNode, 1)");
				s.append("WHERE (StartNode, EndNode) IN (");
				s.append("SELECT n1.NodeId, n2.NodeId FROM Nodes n1, Nodes n2");
				s.append("WHERE n1.Name=");
				s.append('"' + name1 + '"');
				s.append("AND n2.Name=");
				s.append('"' + name2 + '"');
				s.append(");");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
