import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import gnu.prolog.term.AtomTerm;
import gnu.prolog.term.AtomicTerm;
import gnu.prolog.term.CompoundTerm;
import gnu.prolog.term.Term;
import gnu.prolog.term.VariableTerm;
import gnu.prolog.vm.Environment;
import gnu.prolog.vm.Interpreter;
import gnu.prolog.vm.PrologCode;
import gnu.prolog.vm.PrologException;

public class PrologQueryMaster {
	public static final String FACTS_FILE = "prolog/facts.pro";
	public static final String RULES1_FILE = "prolog/rules1.pro";
	public static final String RULES2_FILE = "prolog/rules2.pro";
	private static final String TMP_FILE = "prolog/tmp.pro";
	
	private Environment env;
	private Interpreter interpreter;
	
	public PrologQueryMaster(String fileName) {
		env = new Environment();
		env.ensureLoaded(AtomTerm.get(PrologQueryMaster.class.getResource(fileName).getFile()));
		interpreter = env.createInterpreter();
		env.runInitialization(interpreter);
	}
	
	public boolean query(String fnName, String[] argNames) {
		Term[] args = new Term[argNames.length];
		for (int i = 0; i<args.length; i++) {
			if (Character.isLowerCase(argNames[i].charAt(0))) {
				args[i] = AtomTerm.get(argNames[i]);
			} else if (argNames[i].equals("_")) {
				args[i] = new VariableTerm();
			} else {
				args[i] = new VariableTerm(argNames[i]);
			}
		}
		CompoundTerm goalTerm = new CompoundTerm(AtomTerm.get(fnName), args);
		try {
			int rc = interpreter.runOnce(goalTerm);
			if (rc == PrologCode.SUCCESS || rc == PrologCode.SUCCESS_LAST) {
				return true;
			}
		} catch (PrologException e) {
			System.out.println(e.getStackTrace());
		}
		return false;
	}
	
	public static int[][] guessPreconditions() {
		ArrayList<Integer> preconditions = new ArrayList<Integer>();
		ArrayList<Integer> postconditions = new ArrayList<Integer>();
		try {
			Files.write(Paths.get(TMP_FILE), 
					Files.readAllBytes(Paths.get(RULES1_FILE)));
		} catch (Exception e) {
			
		}
		
		
		return new int[][]{
			preconditions.stream().mapToInt(i -> i).toArray(),
			postconditions.stream().mapToInt(i -> i).toArray()};
	}
	
	

}
