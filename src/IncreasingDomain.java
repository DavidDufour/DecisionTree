import java.awt.List;
import java.util.ArrayList;

import choco.kernel.model.variables.set.SetVariable;
import choco.kernel.solver.ContradictionException;
import choco.kernel.solver.Solver;
import choco.kernel.solver.search.ValIterator;
import choco.kernel.solver.variables.integer.IntDomainVar;

public class IncreasingDomain implements ValIterator<IntDomainVar> {
	/**
	 * testing whether more branches can be considered after branch i, on the
	 * alternative associated to variable x
	 * 
	 * @param x
	 *            the variable under scrutiny
	 * @param i
	 *            the index of the last branch explored
	 * @return true if more branches can be expanded after branch i
	 */
	public boolean hasNextVal(IntDomainVar x, int i) {
		return (i < x.getSup());
	}

	/**
	 * Accessing the index of the first branch for variable x
	 * 
	 * @param x
	 *            the variable under scrutiny
	 * @return the index of the first branch (such as the first value to be
	 *         assigned to the variable)
	 */
	public int getFirstVal(IntDomainVar x, Solver s, int[][] e, SetVariable[] E) {
		/*int node = Integer.parseInt(x.getName().replaceAll("[\\D]", ""));
		int[] left, right;

		double currentGain;
		double maxGain = 0;

		int f = 0;
		
		int k = e[0].length-1;
		for (int i = 0; i < k;) 
		{
			currentGain = informationGain(s, e, E, node, i, k);
			System.out.println(i);
			if (currentGain > maxGain)
			{
				f = i;
				maxGain = currentGain;
			}
			
			System.out.println(maxGain);
			
			i = x.getNextDomainValue(i);
		}
		
		System.out.println("Max Gain: " + maxGain);
		System.out.println(s.getVar(E[node]));
		
		ArrayList<Integer> l = new ArrayList<Integer>();
		ArrayList<Integer> r = new ArrayList<Integer>();
		
		for (int j : s.getVar(E[node]).getValue())
		{
			if (e[j][f] == 0)
				l.add(j);
			else
				r.add(j);
		}
		
		int[] l2 = new int[l.size()];
		int[] r2 = new int[r.size()];
		
		for(int i = 0; i < l2.length; ++i)
			l2[i] = l.get(i);
		for(int i = 0; i < r2.length; ++i)
			r2[i] = r.get(i);
		
		try {
			s.getVar(E[1]).setVal(l2);
			s.getVar(E[2]).setVal(r2); 
			
			System.out.println(s.getVar(E[1]));
		} catch (ContradictionException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} 
		
		return f;
	*/
		return x.getInf();
	}

	/**
	 * generates the index of the next branch after branch i, on the alternative
	 * associated to variable x
	 * 
	 * @param x
	 *            the variable under scrutiny
	 * @param i
	 *            the index of the last branch explored
	 * @return the index of the next branch to be expanded after branch i
	 */
	public int getNextVal(IntDomainVar x, int i, Solver s, int[][] e,
			SetVariable[] E) {
		// calculate info gain here?
		// System.out.println("x:" + x + ", i: " + i + " -- " +
		// x.getNextDomainValue(i));
		int node = Integer.parseInt(x.getName().replaceAll("[\\D]", ""));
		// calculate info gain here?
		//System.out.println(node + ": " + s.getVar(E[1]).getValue().length);
		//System.out.println(x + ": " + x.getInf());

		return x.getNextDomainValue(i);
	}

	@Override
	public int getFirstVal(IntDomainVar arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getNextVal(IntDomainVar arg0, int arg1) {
		// TODO Auto-generated method stub
		return 0;
	}

	private double informationGain(Solver s, int[][] e, SetVariable[] E, int node, int i, int k) {
		double nExamples, nPosExamples = 0, nNegExamples = 0;
		double nLeftExamples = 0, nLeftPosExamples = 0, nLeftNegExamples = 0;
		double nRightExamples = 0, nRightPosExamples = 0, nRightNegExamples = 0;

		// go through features, i = x.getNextDomainValue(i);
		nExamples = s.getVar(E[node]).getValue().length;

		for (int j : s.getVar(E[node]).getValue())
		{
			if (e[j][i] == 0) {
				++nLeftExamples;
				if (e[j][k] == 0) {
					++nNegExamples;
					++nLeftNegExamples;
				} else {
					++nPosExamples;
					++nLeftPosExamples;
				}
			} else {
				++nRightExamples;
				if (e[j][k] == 0) {
					++nNegExamples;
					++nRightNegExamples;
				} else {
					++nPosExamples;
					++nRightPosExamples;
				}
			}
		}

		//for (int j : s.getVar(E[node]).getValue())
		//	System.out.print(j + " ");
		//System.out.println();
		
		//System.out.println(nExamples + ", " + nPosExamples + ", " + nNegExamples
		//		 + ", " + nLeftExamples + ", " + nLeftPosExamples + ", " + nLeftNegExamples
		//		 + ", " + nRightExamples + ", " + nRightPosExamples + ", " + nRightNegExamples);

		if(nNegExamples == 0 || nPosExamples == 0 || nLeftNegExamples == 0 || nLeftPosExamples == 0 || nRightNegExamples == 0 || nRightPosExamples == 0)
			return 0;
		
		return (
				  -	((nNegExamples / nExamples) * (Math.log(nNegExamples / nExamples) / Math.log(2)))
				  - ((nPosExamples / nExamples) * (Math.log(nPosExamples / nExamples) / Math.log(2)))
				  - (
						(
							nLeftExamples * (
							- ((nLeftNegExamples / nLeftExamples) * (Math.log(nLeftNegExamples / nLeftExamples) / Math.log(2)))
							- ((nLeftPosExamples / nLeftExamples) * (Math.log(nLeftPosExamples / nLeftExamples) / Math.log(2))))
							+ 
							nRightExamples * (
							- ((nRightNegExamples / nRightExamples) * (Math.log(nRightNegExamples / nRightExamples) / Math.log(2)))
							- ((nRightPosExamples / nRightExamples) * (Math.log(nRightPosExamples / nRightExamples) / Math.log(2))))
						) 
						/ nExamples
					)
			   );
	}
}