package TrainningDataGenerator;

import java.util.ArrayList;

public class HillClimbing {

	public Solution solve(Solution current, int k) {// s is inital solution
//		long start;
//		long end;
//		long time=0;
//		start=System.nanoTime();

		int t = 0;// iteration times
		Solution newSolution = current;
		Solution bestSolution = current;
		boolean stop = false;

		while (stop == false) {
			t++;
//			start=System.nanoTime();
			newSolution = getBestNeighbor(current, k);// best neighbor
//			end=System.nanoTime();
//			time+=(end-start);

			if (newSolution.getDistance() < bestSolution.getDistance()) {
				bestSolution = newSolution;
				bestSolution.bestT = t;
				current = newSolution;
			} else {
				stop = true;
			}
		}

//		end=System.nanoTime();
//		time+=(end-start);
//		System.out.println("TraditionalTime: "+time);

		return bestSolution;
	}

	public Solution solveArrayList(Solution current, ArrayList<int[]> rule, int k) {// s is inital solution
//		long start;
//		long end;
//		long time=0;
//		start=System.nanoTime();

		Solution bestSolution;
		int t = 0;// iteration times
		ArrayList<int[]> fix_solution = Solution.packageSolutions(rule, current, 29);
		boolean stop = false;
		ArrayList<int[]> newSolution = (ArrayList<int[]>) fix_solution.clone();
		ArrayList<int[]> bestSolution_array = (ArrayList<int[]>) newSolution.clone();
		double newSolution_dis;
		double bestSolution_array_dis = Double.MAX_VALUE;

		while (stop == false) {
			t++;
//			start=System.nanoTime();
			newSolution = getBestNeighborArrayList(fix_solution, k);// best neighbor
//			end=System.nanoTime();
//			time+=(end-start);

			newSolution_dis = Solution.getdistanceArrayList(newSolution);

			if (newSolution_dis < bestSolution_array_dis) {
				bestSolution_array_dis = newSolution_dis;
				bestSolution_array = (ArrayList<int[]>) newSolution.clone();
				fix_solution = bestSolution_array;
			} else {
				stop = true;
			}
		}

		bestSolution = Solution.arraylistToSolution(bestSolution_array);

//		end=System.nanoTime();
//		time+=(end-start);
//		System.out.println("Enhanced: "+time);
		return bestSolution;

	}

	public ArrayList<int[]> solveArrayList2(ArrayList<int[]> fix_solution, int k) {// s is inital
																					// solution
		int t = 0;// iteration times

		ArrayList<int[]> newSolution = (ArrayList<int[]>) fix_solution.clone();
		ArrayList<int[]> bestSolution = new ArrayList<>();
		double best_dis = Double.MAX_VALUE;
		double new_dis;
		boolean stop = false;

		while (stop == false) {
			t++;
			newSolution = getBestNeighborArrayList(fix_solution, k);// best neighbor

			new_dis = Solution.getdistanceArrayList(newSolution);
			if (new_dis < best_dis) {
				best_dis = new_dis;
				bestSolution = newSolution;
				fix_solution = (ArrayList<int[]>) newSolution.clone();
			} else {
				stop = true;
			}
		}
		return bestSolution;
	}

	public Solution getBestNeighbor(Solution s, int k) {
		switch (k) {
		case 1:
			return s.generateNeighbourSolutionOPT1();
		case 2:
			return s.generateNeighbourSolutionCityInsertion();
		case 3:
			return s.generateNeighbourSolutionOPT3();
		case 4:
			return s.generateNeighbourSolutionOPT2();
		default:
			return s.generateNeighbourSolutionOPT2ByOmar();
			
		}

	}

	public ArrayList<int[]> getBestNeighborArrayList(ArrayList<int[]> s, int k) {
		switch (k) {
		case 1:
			return Solution.EnhencedOPT1ArrayList(s);
		case 2:
			return Solution.EnhencedCityInsertionArrayList(s);
		case 3:
			return Solution.EnhencedOPT3ArrayList(s);
		case 4:
			return Solution.EnhencedOPT2ArrayList(s);
			
		default:
			return Solution.EnhencedOPT2ArrayListByOmar(s);
		}

	}

}
