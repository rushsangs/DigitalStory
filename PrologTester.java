import java.util.Scanner;

public class PrologTester {
	public static void main(String[] args) {
		PrologQueryMaster pqm = new PrologQueryMaster("prolog/facts.pro");
		Scanner sc = new Scanner(System.in);
		while (sc.hasNext()) {
			System.out.println(pqm.query(sc.nextLine(), sc.nextLine().split(" ")));
		}
		
	}
}
