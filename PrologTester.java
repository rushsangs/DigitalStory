import java.util.Scanner;

public class PrologTester {
	public static void main(String[] args) {
		PrologQueryMaster pqm = new PrologQueryMaster("prolog/facts.pro");
		Scanner sc = new Scanner(System.in);
		while (sc.hasNext()) {
			// System.out.println(pqm.verify(sc.nextLine(), sc.nextLine().split(" ")));
			String[][] aa = pqm.query(sc.nextLine(), sc.nextLine().split(" "));
			for ( int i = 0; i < aa.length ; i++){
				for (int j = 0 ; j < aa[i].length; j++){
					System.out.print(aa[i][j]+" ");
				}
				System.out.println();
			}
		}
		
	}
}
