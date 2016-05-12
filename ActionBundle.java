public class ActionBundle {
	public DigitalObject actor;
	public DigitalAffordance affordance;
	public ActionTuple instance;
	
	public static String getTypeName(DigitalObject o) {
		DigitalObject o2 = o;
		while (o2.ObjectType!=null && o2.ObjectType!=o2) {
			o2 = o2.ObjectType;
		}
		return o2.name;
	}
	
}
