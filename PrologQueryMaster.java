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
	private static final String factsFile = "prolog/facts.pro";
	private static final String rules1File = "prolog/rules1.pro";
	private static final String rules2File = "prolog/rules2.pro";
	
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

}
