import choco.kernel.solver.search.ValSelector;
import choco.kernel.solver.variables.integer.IntDomainVar;

public class MinVal implements ValSelector<IntDomainVar> {
	/**
	 * selecting the lowest value in the domain
	 * 
	 * @param x
	 *            the variable under consideration
	 * @return what seems the most interesting value for branching
	 */
	public int getBestVal(IntDomainVar x) {
		return x.getInf();
	}
}