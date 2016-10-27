import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Scanner;

public class PrologTester {
	public static void main(String[] args) {
		final String tmp = "tmp.pro";
		try {
			Files.write(Paths.get(tmp), 
					Files.readAllBytes(Paths.get(
							PrologQueryMaster.FACTS_FILE)));
			Files.write(Paths.get(tmp), 
					Files.readAllBytes(Paths.get(
							PrologQueryMaster.RULES1_FILE)), 
					StandardOpenOption.APPEND);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		PrologQueryMaster pqm = new PrologQueryMaster(tmp);
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
