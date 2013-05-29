import choco.Choco;
import choco.cp.model.CPModel;
import choco.cp.solver.CPSolver;
import choco.cp.solver.search.BranchingFactory;
import choco.cp.solver.search.integer.branching.AssignVar;
import choco.cp.solver.search.integer.valiterator.DecreasingDomain;
import choco.cp.solver.search.integer.varselector.DomOverDeg;
import choco.cp.solver.search.set.AssignSetVar;
import choco.cp.solver.search.set.MinDomSet;
import choco.cp.solver.search.set.MinEnv;
import choco.cp.solver.search.set.RandomSetValSelector;
import choco.cp.solver.search.set.StaticSetVarOrder;
import choco.kernel.model.variables.integer.IntegerVariable;
import choco.kernel.model.variables.set.SetVariable;
import choco.kernel.solver.variables.set.SetVar;
import static choco.Choco.*;

public class DecisionTree {
  public static void main(String[] args)
  {
	
  /*
    int n = 1; //nodes
    int m = 2; //examples
    int k = 1; //features
    
    int[][] e = {{1, 0},
                 {0, 1}};

    int n = 3; //nodes
    int m = 4; //examples
    int k = 2; //features
    
    int[][] e = {{0, 0, 0},
                 {0, 1, 1},
                 {1, 1, 0},
                 {1, 0, 1}};

    int n = 7; //nodes
    int m = 8; //examples
    int k = 5; //features  

    int[][] e = {{0, 0, 1, 0, 0, 0},
                 {0, 0, 0, 0, 0 ,1},
                 {0, 1, 0, 0, 0, 0},
                 {0, 1, 1, 0, 0, 1},
                 {1, 1, 1, 1, 0, 0},
                 {1, 1, 1, 1, 1, 1},
                 {1, 1, 1, 0, 1, 0},
                 {1, 1, 1, 0, 0, 1}};
*/
	int n = 4; //nodes
	int m = 14; //examples
	int k = 10; //features
	
	int[][] e = {{0, 0, 1, 0, 0, 1, 0, 1, 1, 0, 0},
	             {0, 0, 1, 0, 0, 1, 0, 1, 0, 1, 0},
	             {0, 1, 0, 0, 0, 1, 0, 1, 1, 0, 1},
	             {1, 0, 0, 0, 1, 0, 0, 1, 1, 0, 1},
	             {1, 0, 0, 1, 0, 0, 1, 0, 1, 0, 1},
	             {1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0},
	             {0, 1, 0, 1, 0, 0, 1, 0, 0, 1, 1},
	             {0, 0, 1, 0, 1, 0, 0, 1, 1, 0, 0},
	             {0, 0, 1, 1, 0, 0, 1, 0, 1, 0, 1},
	             {1, 0, 0, 0, 1, 0, 1, 0, 1, 0, 1},
	             {0, 0, 1, 0, 1, 0, 1, 0, 0, 1, 1},
	             {0, 1, 0, 0, 1, 0, 0, 1, 0, 1, 1},
	             {0, 1, 0, 0, 0, 1, 1, 0, 1, 0, 1},
	             {1, 0, 0, 0, 1, 0, 0, 1, 0, 1, 0}};
 /*
	int n = 3; //nodes
	int m = 70; //examples
	int k = 45; //features
	    
    int[][] e = {{0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0},
    	{0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0},
    	{0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0},
    	{0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0},
    	{0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0},
    	{0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0},
    	{0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0},
    	{0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0},
    	{0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0},
    	{0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0},
    	{0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0},
    	{0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0},
    	{0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0},
    	{0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0},
    	{0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0},
    	{0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0},
    	{0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0},
    	{0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0},
    	{0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0},
    	{0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0},
    	{0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0},
    	{0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0},
    	{0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0},
    	{0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0},
    	{0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0},
    	{0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0},
    	{0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0},
    	{0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0},
    	{0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0},
    	{1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1},
    	{1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1},
    	{1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1},
    	{1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1},
    	{1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1},
    	{0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1},
    	{0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1},
    	{0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1},
    	{0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1},
    	{0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1},
    	{0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1},
    	{0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1},
    	{0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1},
    	{0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1},
    	{0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1},
    	{0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1},
    	{0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1},
    	{0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1},
    	{0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1},
    	{0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1},
    	{0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1},
    	{0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1},
    	{0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1},
    	{0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1},
    	{0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1},
    	{0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1},
    	{0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1},
    	{0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1},
    	{0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1},
    	{0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1},
    	{0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1},
    	{0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1},
    	{0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1},
    	{0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1},
    	{0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1},
    	{0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1},
    	{0, 0, 1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1},
    	{0, 0, 1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1},
    	{0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1},
    	{0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1},
    	{0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1}};
*/
    
    // Build the model import
    CPModel model = new CPModel();

    //Information Gain
    //RealVariable[] IG = new RealVariable[n];
    //for(int i = 0; i < n; ++i)
    //  IG[i] = makeRealVar("IG_" + i, 0, 1);
    //make entropy constraint and then branch on it

    //Variables
    IntegerVariable[] P = new IntegerVariable[n];
    P = makeIntVarArray("P", n, 0, n-1);

    IntegerVariable[] L = new IntegerVariable[n];
    L = makeIntVarArray("L", n, 0, n-1, "cp:no_decision");

    IntegerVariable[] R = new IntegerVariable[n];
    R = makeIntVarArray("R", n, 0, n-1, "cp:no_decision");

    IntegerVariable[] N = new IntegerVariable[n];
    N = makeIntVarArray("N", n, 0, 3, "cp:no_decision");

    IntegerVariable[] F = new IntegerVariable[n];
    F = makeIntVarArray("F", n, 0, k-1);

    IntegerVariable[][] D = new IntegerVariable[n][n];
    for(int i = 0; i < n; ++i) 
      for(int j = 0; j < n; ++j) 
        D[i][j] = makeIntVar("D_" + i + "_" + j, 0, 1);

    SetVariable[] E = new SetVariable[n];
    for(int i = 1; i < n; ++i)
      E[i] = Choco.makeSetVar("E_" + i, 0, m-1);
    
    int[] examples = new int[m];
    for(int i = 0; i < m; ++i) examples[i] = i;
    E[0] = Choco.constant(examples);

    IntegerVariable[][][] T8 = new IntegerVariable[n][n][m];
    for(int i = 0; i < n; ++i) 
      for(int j = 0; j < n; ++j) 
          T8[i][j] = makeIntVarArray("T8_" + i + "_" + j, m, 0, 1, "cp:no_decision");

    IntegerVariable[][][] T9 = new IntegerVariable[n][n][m];
    for(int i = 0; i < n; ++i) 
      for(int j = 0; j < n; ++j) 
          T9[i][j] = makeIntVarArray("T9_" + i + "_" + j, m, 0, 1, "cp:no_decision");
    
    IntegerVariable[][][] T10x = new IntegerVariable[n][m][m];
    for(int i = 0; i < n; ++i) 
      for(int j = 0; j < m; ++j) 
          T10x[i][j] = makeIntVarArray("T10x_" + i + "_" + j, m, 0, 1, "cp:no_decision");
    
    IntegerVariable[][][] T10y = new IntegerVariable[n][m][m];
    for(int i = 0; i < n; ++i) 
      for(int j = 0; j < m; ++j) 
          T10y[i][j] = makeIntVarArray("T10y_" + i + "_" + j, m, 0, 1, "cp:no_decision");

    //Constraints

    //model.addConstraint(eq(P[2], 0));
    
    //Root is it's own parent
    model.addConstraint(eq(P[0], 0));
    if(n > 0)
      model.addConstraint(eq(P[1], 0));
    //No other node has itself as a parent
    for(int i = 1; i < n; ++i) 
      model.addConstraint(neq(P[i], i));

    //All non-root nodes have less than three children
    for(int i = 1; i < n; ++i)
      model.addConstraint(neq(N[i], 3));

    //If a node has two children than the left and right child must exist
    for(int i = 1; i < n; ++i) 
      model.addConstraint(ifOnlyIf(eq(N[i], 2), and(neq(L[i], i), neq(R[i], i))));
    //If a node has one child than the left xor the right child must exist
    for(int i = 1; i < n; ++i) 
      model.addConstraint(ifOnlyIf(eq(N[i], 1), and(or(eq(L[i], i), eq(R[i], i)), not(and(eq(L[i], i), eq(R[i], i))))));
    //If a node has zero children than the left and right child must not exist    
    for(int i = 1; i < n; ++i) 
      model.addConstraint(ifOnlyIf(eq(N[i], 0), and(eq(L[i], i), eq(R[i], i))));

    //TREE CONSTRAINTS
    for(int i = 0; i < n; ++i) 
    	model.addConstraint(eq(D[i][i],0));
    
    //If j is i's parent then i is a descendant of node j. j and i share the same decendants otherwise
    for(int i = 0; i < n; ++i) 
      for(int j = 0; j < n; ++j)
        if(i != j)
          for(int l = 0; l < n; ++l)
          {
            if(j != l)
              model.addConstraint(implies(eq(P[i],j), eq(D[l][i], D[l][j])));
            else
              model.addConstraint(implies(eq(P[i],j), eq(D[j][i], 1)));
           }
    //Prevents cycles in the tree
    for(int i = 0; i < n; ++i) 
      for(int j = 0; j < n; ++j)
        if(i != j)
          model.addConstraint(implies(eq(D[i][j], 1), eq(D[j][i], 0)));

    //(5) Binary Tree
    //If i is j's parent than j is the left xor the right child or i
    for(int i = 0; i < n; ++i) 
    {
      for(int j = 0; j < n; ++j)
      {
        if(i != j)
        {
          model.addConstraint(ifOnlyIf(eq(P[j], i), and(
                                                        or(eq(L[i], j), 
                                                           eq(R[i], j)
                                                        ), 
                                                        not(and(
                                                                eq(L[i], j), 
                                                                eq(R[i], j)
                                                        ))
                                                       )
                                      )
                              );
        }
      }
    }

    //(6)
    //The number of children of i is equal the number of times i appears in the parents of array
    for(int i = 0; i < n; ++i) 
      model.addConstraint(occurrence(N[i], P, i));      

    //(7) 
    //No two features are tested twice along a branch
    for(int i = 0; i < n; ++i) 
    {
      for(int j = 0; j < n; ++j) 
      {
        if(i != j)
        {
          model.addConstraint(implies(eq(D[i][j], 1), neq(F[i], F[j])));
        }
      }
    }
    
    //(8)
    //Creates the set of examples to be passed to the left child
    for(int i = 0; i < n; ++i) 
      for(int j = i+1; j < n; ++j) 
        if(i!=j)
          for(int l = 0; l < m; l++) 
          {
            model.addConstraint(nth(Choco.constant(l), F[i], e, T8[i][j][l]));
            model.addConstraint(implies(
                                        eq(L[i],j),
                                        ifOnlyIf(
                                                and(        
                                                    member(l, E[i]), 
                                                    eq(T8[i][j][l], 0)
                                                   ), 
                                                member(l, E[j])
                                                )
                                        )
                                );
          }

    //(9)
    //Creates the set of examples to be passed to the right child
    for(int i = 0; i < n; ++i) 
      for(int j = i+1; j < n; ++j) 
        if(i!=j)
          for(int l = 0; l < m; l++) 
          {
            model.addConstraint(nth(Choco.constant(l), F[i], e, T9[i][j][l]));
            model.addConstraint(implies(
                                        eq(R[i],j),
                                        ifOnlyIf(
                                                and(        
                                                    member(l, E[i]), 
                                                    eq(T9[i][j][l], 1)
                                                   ), 
                                                member(l, E[j])
                                                )
                                        )
                                );
          }

    //(10)
    //If two examples have different classification but have the same value for the feature on node i, i must have children
    for(int i = 0; i < n; ++i) 
      for(int x = 0; x < m; x++) 
        for(int y = x+1; y < m; y++) 
          if(e[x][k] != e[y][k])
          {
            model.addConstraint(nth(Choco.constant(x), F[i], e, T10x[i][x][y]));
            model.addConstraint(nth(Choco.constant(y), F[i], e, T10y[i][x][y]));
            model.addConstraint(implies(and(member(x, E[i]), 
                                            member(y, E[i]), 
                                            eq(T10x[i][x][y], T10y[i][x][y])), 
                                        gt(N[i], 0)));
          }

    //Extension of 10 where the left child can't equal itself (signifying it's a leaf node)
    for(int i = 0; i < n; ++i) 
      for(int x = 0; x < m; x++) 
        for(int y = x+1; y < m; y++) 
          if(e[x][k] != e[y][k])
          {
            model.addConstraint(nth(Choco.constant(x), F[i], e, T10x[i][x][y]));
            model.addConstraint(nth(Choco.constant(y), F[i], e, T10y[i][x][y]));
            model.addConstraint(implies(and(member(x, E[i]), 
                                            member(y, E[i]), 
                                            eq(T10x[i][x][y], 0),
                                            eq(T10y[i][x][y], 0)), 
                                        neq(L[i], i)));
          }

    //Extension of 10 where the right child can't equal itself (signifying it's a leaf node)
    for(int i = 0; i < n; ++i) 
      for(int x = 0; x < m; x++) 
        for(int y = x+1; y < m; y++) 
          if(e[x][k] != e[y][k])
          {
            model.addConstraint(nth(Choco.constant(x), F[i], e, T10x[i][x][y]));
            model.addConstraint(nth(Choco.constant(y), F[i], e, T10y[i][x][y]));
            model.addConstraint(implies(and(member(x, E[i]), 
                                            member(y, E[i]), 
                                            eq(T10x[i][x][y], 1),
                                            eq(T10y[i][x][y], 1)), 
                                        neq(R[i], i)));
          }

    //Inference
    //(11)
    for(int i = 0; i < n; ++i) 
        for(int x = 0; x < m; x++) 
          for(int y = x+1; y < m; y++) 
            if(e[x][k] != e[y][k])
            {
              model.addConstraint(nth(Choco.constant(x), F[i], e, T10x[i][x][y]));
              model.addConstraint(nth(Choco.constant(y), F[i], e, T10y[i][x][y]));
              model.addConstraint(implies(and(member(x, E[i]), 
                                              member(y, E[i])),
                                          neq(T10x[i][x][y], T10y[i][x][y])));
            }
    
    //(12)
    
    
    //(13)
    
    
    //Symmetry Breaking
    //(14)
    //Nodes are ordered from top to bottom
    for(int i = 0; i < n-1; ++i) 
    {
      model.addConstraint(leq(P[i], min(i, P[i+1])));
    }
    //(15)
    //Nodes are ordered left to right
    for(int i = 0; i < n; ++i) 
    {
      model.addConstraint(and(leq(i, R[i]), leq(R[i], 2*i + 2)));
    }
    //(16)
    //Nodes are ordered left to right
    for(int i = 0; i < n; ++i) 
    {
      model.addConstraint(and(leq(i, L[i]), leq(L[i], 2*i + 1), implies(neq(R[i], i), leq(L[i], R[i]))));
    }

    CPSolver s = new CPSolver();    
    s.read(model);
    
    SetVar[] solverE = new SetVar[n];
    for(int i = 0; i < n; ++i)
    	solverE[i] = s.getVar(E[i]);
    //s.setGeometricRestart(100, 1.5d);
    s.addGoal(new InformationGain(s, e, E, new StaticVarOrder(s, s.getVar(F)), new IncreasingDomain()));
    s.addGoal(new AssignSetVar(new StaticSetVarOrder(s, s.getVar(E)), new RandomSetValSelector()));
    //s.addGoal(new AssignVar(new StaticVarOrder(s, s.getVar(P)), new IncreasingDomain()));
    s.addGoal(BranchingFactory.randomIntSearch(s, 0));
    s.solve();
    
    
 /*   
    for(int z = 0; z < s.getSolutionCount(); ++z)
    {*/
      for(int i = 0; i < n; ++i) 
      {
        for(int j = 0; j < n; ++j)
           System.out.print(s.getVar(D[j][i]).getName() + ": " + s.getVar(D[j][i]).getVal() + ", ");       
        System.out.println();
      }
      System.out.println();

      for(int j = 0; j < n; ++j)
        System.out.print(s.getVar(P[j]).getName() + ":" + s.getVar(P[j]).getVal() + ", ");
      System.out.println();
      
      for(int j = 0; j < n; ++j)
        System.out.print(s.getVar(L[j]).getName() + ":" + s.getVar(L[j]).getVal() + ", ");
      System.out.println();

      for(int j = 0; j < n; ++j)
        System.out.print(s.getVar(R[j]).getName() + ":" + s.getVar(R[j]).getVal() + ", ");
      System.out.println();

      for(int j = 0; j < n; ++j)
        System.out.print(s.getVar(N[j]).getName() + ":" + s.getVar(N[j]).getVal() + ", ");
      System.out.println();

      for(int j = 0; j < n; ++j)
        System.out.print(s.getVar(F[j]).getName() + ":" + s.getVar(F[j]).getVal() + ", ");
      System.out.println();
    
      for(int j = 0; j < n; ++j)
      {
        System.out.print(s.getVar(E[j]).pretty() + ", ");
      }
      System.out.println();
/*
    s.nextSolution();
  }
*/
    System.out.println();
    System.out.println("Number of solutions found:" + s.getSolutionCount());
  }
}
