import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
	public static final String ACTION_F = "prolog/actionf.pro";
	public static final String ACTION_R1 = "prolog/actionr1.pro";
	public static final String ACTION_R2 = "prolog/actionr2.pro";
	public static final String TRAIT_F = "prolog/traitf.pro";
	public static final String TRAIT_R = "prolog/traitr.pro";
	public static final String TYPE_F = "prolog/typef.pro";
	public static final String ERROR_R = "prolog/errorr.pro";
	
	//processing files
	public static final String FACTS_FILE = "prolog/facts.pro";
	public static final String TMP = "prolog/tmp.pro";
	
	public static final String[] ALL_FILES_TO_CREATE = {
			ACTION_F, ACTION_R1, ACTION_R2, 
			TRAIT_F, TRAIT_R, 
			TYPE_F,
			ERROR_R
			};
	
	private Environment env;
	private Interpreter interpreter;
	
	public static void updateFacts() {
		try {
			boolean isFirstWrite = true;
			for (String file : new String[]
					{ACTION_F, ACTION_R1,
					 TRAIT_F, TRAIT_R,
					 TYPE_F,
					 ERROR_R}
			) {
				if (isFirstWrite) {
					Files.write(Paths.get(FACTS_FILE), 
							Files.readAllBytes(Paths.get(file)));
					isFirstWrite = false;
				} else {
					Files.write(Paths.get(FACTS_FILE), 
							Files.readAllBytes(Paths.get(file)), 
							StandardOpenOption.APPEND);
				}	
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
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
					if (rc==PrologCode.SUCCESS_LAST) {
						return output.stream().map(
							(u) -> u.toArray(new String[0]))
							.toArray(String[][]::new);
					}
					break;
				case PrologCode.FAIL:
					interpreter.stop(goal);
					break;
				case PrologCode.HALT:
					System.out.println("HALT");
					break;
				}
			}
		} catch (IllegalArgumentException e) {
			// for some reason, always reaches here at the end
			//e.printStackTrace();
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
	
	
	public static boolean[][] guessPreconditions() {
		boolean[][] ret = null;
		try {
			List<String> actions = Files.readAllLines(Paths.get(ACTION_F));
			List<String> otherStmts = new ArrayList<String>();
			for (String file : new String[]
					{ACTION_R1, ACTION_R2, // include precondition rules
					 TRAIT_F, TRAIT_R,
					 TYPE_F}
			) {
				otherStmts.addAll(Files.readAllLines(Paths.get(file)));
			}
			ret = new boolean[actions.size()][actions.size()];
			for (int i = 0 ; i<actions.size(); i++) {
				for (int j = i+1; j<actions.size(); j++) {
					Files.write(Paths.get(TMP), actions.get(i).getBytes());
					Files.write(Paths.get(TMP), otherStmts, 
							StandardOpenOption.APPEND);
					PrologQueryMaster pqm = new PrologQueryMaster(TMP);
					// split action into fnName and argNames
					String[] arr = actions.get(j).split("[\\(\\)]");
					ret[i][j] = pqm.verify(arr[0], arr[1].split(","));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}
	
	

}
