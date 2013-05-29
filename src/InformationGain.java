import choco.kernel.model.variables.set.SetVariable;
import choco.kernel.solver.ContradictionException;
import choco.kernel.solver.Solver;
import choco.kernel.solver.branch.AbstractLargeIntBranchingStrategy;
import choco.kernel.solver.search.IntBranchingDecision;
import choco.kernel.solver.variables.set.SetVar;


/**
* A class for branching schemes that consider n branches:
* - assigning a value v_i to an variable (X == v_i)
*/

public class InformationGain extends AbstractLargeIntBranchingStrategy 
{
  protected final StaticVarOrder varSelector;
  protected IncreasingDomain valIterator;
  protected SetVariable[] E;
  protected Solver s;
  protected int[][] e;

  public InformationGain(Solver s, int[][] e, SetVariable[] E, StaticVarOrder varSelector, IncreasingDomain valIterator)
  {
    this.varSelector = varSelector;
    this.valIterator = valIterator;
    this.s = s;
    this.e = e;
    this.E = E;
  }

  /**
  * Select the variable to constrained
  *
  * @return the branching object
  */
  public Object selectBranchingObject() throws ContradictionException 
  {
    return varSelector.selectVar();
  }

  /**
  * Select the first value to assign, and set it in the decision object in parameter
  *
  * @param decision the first decision to apply
  */
  public void setFirstBranch(final IntBranchingDecision decision) 
  {
	//pass set of examples associated with
    decision.setBranchingValue(valIterator.getFirstVal(decision.getBranchingIntVar(), s, e, E));
  }

  /**
  * Select the next value to assign, and set it in the decision object in parameter
  *
  * @param decision the next decision to apply
  */
  public void setNextBranch(final IntBranchingDecision decision) 
  {
    decision.setBranchingValue(valIterator.getNextVal(decision.getBranchingIntVar(),
    decision.getBranchingValue(), s, e, E));
	  //decision.setBranchingValue(valIterator.getFirstVal(decision.getBranchingIntVar(), s, e, E));
  }

  /**
  * Check whether there is still a value to assign
  *
  * @param decision the last decision applied
  * @return <code>false</code> if there is still a branching to do
  */
  public boolean finishedBranching(final IntBranchingDecision decision) 
  {
    return !valIterator.hasNextVal(decision.getBranchingIntVar(), decision.getBranchingValue());
  }

  /**
  * Apply the computed decision building the i^th branch.
  * --> assignment: the variable is instantiated to the value
  *
  * @param decision the decision to apply.
  * @throws ContradictionException if the decision leads to an incoherence
  */
  @Override
  public void goDownBranch(final IntBranchingDecision decision) throws ContradictionException
  {
    decision.setIntVal();
  }

  /**
  * Reconsider the computed decision, destroying the i^th branch
  * --> forbiddance: the value is removed from the domain of the variable
  *
  * @param decision the decision that has been set at the father choice point
  * @throws ContradictionException if the non-decision leads to an incoherence
  */
  @Override
  public void goUpBranch(final IntBranchingDecision decision) throws ContradictionException 
  {
    decision.remIntVal();
  }

  /**
  * Create and return the message to print, in case of strong verbosity
  * @param decision current decision
  * @return pretty print of the current decision
  */
  @Override
  public String getDecisionLogMessage(IntBranchingDecision decision) 
  {
    return decision.getBranchingObject() + "==" + decision.getBranchingValue();
  }
}
