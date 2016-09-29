

import java.nio.file.Files;

public class PrologTester {
	public static void main(String[] args) {
		PrologQueryMaster pqm = new PrologQueryMaster("patrick_test.pro");
		if (pqm.query("action", new String[]{"ned", "stop", "her"})) {
			System.out.println("ned stop her true");
		}
		if (pqm.query("action", new String[]{"X", "stop", "her"})) {
			System.out.println("X stop her true");
		}
		if (pqm.query("action", new String[]{"john", "stop", "her"})) {
			System.out.println("john stop her true");
		}
		if (pqm.query("action", new String[]{"stark", "met", "robert"})) {
			System.out.println("stark met robert true");
		}
		
	}
}
