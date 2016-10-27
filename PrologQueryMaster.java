import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import gnu.prolog.io.OperatorSet;
import gnu.prolog.io.ReadOptions;
import gnu.prolog.io.WriteOptions;
import gnu.prolog.term.AtomTerm;
import gnu.prolog.term.AtomicTerm;
import gnu.prolog.term.CompoundTerm;
import gnu.prolog.term.Term;
import gnu.prolog.term.VariableTerm;
import gnu.prolog.vm.Environment;
import gnu.prolog.vm.Interpreter;
import gnu.prolog.vm.Interpreter.Goal;
import gnu.prolog.vm.PrologCode;
import gnu.prolog.vm.PrologException;

public class PrologQueryMaster {
	public static final String DIR = "prolog";
	public static final String FACTS_FILE = "prolog/facts.pro";
	public static final String RULES1_FILE = "prolog/rules1.pro";
	public static final String RULES2_FILE = "prolog/rules2.pro";
	private static final String TMP_FILE = "prolog/tmp.pro";
	
	private Environment env;
	private Interpreter interpreter;
	
	public PrologQueryMaster(String fileName) {
		env = new Environment();
		env.ensureLoaded(AtomTerm.get(fileName));
		interpreter = env.createInterpreter();
		env.runInitialization(interpreter);
	}
	
	public boolean verify(String fnName, String[] argNames) {
		CompoundTerm goalTerm = prep(fnName, argNames);
		try {
			int rc = interpreter.runOnce(goalTerm);
			if (rc == PrologCode.SUCCESS || rc == PrologCode.SUCCESS_LAST) {
				return true;
			}
		} catch (IllegalArgumentException e) {
			return false;
		} catch (PrologException e) {
			System.out.println(e.getStackTrace());
		}
		return false;
	}
	public String[][] query(String fnName, String[] argNames) {
		CompoundTerm goalTerm = prep(fnName, argNames);
		ArrayList<ArrayList<String>> output = 
				new ArrayList<ArrayList<String>>();
		try {
			Goal goal = interpreter.prepareGoal(goalTerm);
			while (true) {
				int rc = interpreter.execute(goal);
				switch (rc) {
				case PrologCode.SUCCESS:
				case PrologCode.SUCCESS_LAST:
					ArrayList<String> row = new ArrayList<String>();
					for (Term t : goalTerm.args) {
						row.add(t.dereference().toString());
					}
					output.add(row);
					break;
				case PrologCode.FAIL:
					interpreter.stop(goal);
					break;
				case PrologCode.HALT:
					break;
				}
			}
		} catch (IllegalArgumentException e) {
			// for some reason, always reaches here at the end
			return output.stream().map(
					(u) -> u.toArray(new String[0]))
					.toArray(String[][]::new);
		} catch (PrologException e) {
			System.out.println(e.getStackTrace());
		}
		return null;
	}
	private CompoundTerm prep(String fnName, String[] argNames) {
		Term[] args = new Term[argNames.length];
		for (int i = 0; i<args.length; i++) {
			argNames[i] = argNames[i].trim();
			if (Character.isLowerCase(argNames[i].charAt(0))) {
				args[i] = AtomTerm.get(argNames[i]);
			} else if (argNames[i].equals("_")) {
				args[i] = new VariableTerm();
			} else {
				args[i] = new VariableTerm(argNames[i]);
			}
		}
		return new CompoundTerm(AtomTerm.get(fnName), args);
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
