import choco.kernel.memory.IStateInt;
import choco.kernel.solver.Solver;
import choco.kernel.solver.search.integer.AbstractIntVarSelector;
import choco.kernel.solver.variables.integer.IntDomainVar;

/**
 * A variable selector selecting the first non instantiated variable according
 * to a given static order
 */
public class StaticVarOrder extends AbstractIntVarSelector {
	private final IStateInt last;

	public StaticVarOrder(Solver solver) {
		super(solver);
		this.last = solver.getEnvironment().makeInt(0);
	}

	public StaticVarOrder(Solver solver, IntDomainVar[] vars) {
		super(solver, vars);
		this.last = solver.getEnvironment().makeInt(0);
	}

	/**
	 * Select the next uninstantiated variable, according to the define policy:
	 * input order
	 * 
	 * @return the selected variable if exists, <code>null</code> otherwise
	 */
	public IntDomainVar selectVar() {
		/*for (int i = 0; i < vars.length; i++) {
			System.out.println("vars[" + i + "]: " + vars[i]);
		}*/
		for (int i = last.get(); i < vars.length; i++) {
			if (!vars[i].isInstantiated()) {
				last.set(i);
				// System.out.println("vars[" + i + "]: " + vars[i]);
				return vars[i];
			}
		}
		return null;
	}
}
