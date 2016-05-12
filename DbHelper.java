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
}
