package TrainningDataGenerator;

import java.util.ArrayList;

public class VNS {

	public Solution solve(Solution inital, int convergeConfirm, int kmax) {
		Solution shakeNeighbor;
		Solution currentSolution;
		Solution bestSolution;
		Solution localSearchResult;
		int converge = 0;
//		int kmax = 3;
		int k;
		currentSolution = inital;
		bestSolution = inital;
		HillClimbing hc = new HillClimbing();

		while (converge < convergeConfirm) {
			k = 1;
			while (k <= kmax) {

				shakeNeighbor = shake(currentSolution, k);
				localSearchResult = hc.solve(shakeNeighbor, k);

				if (localSearchResult.getDistance() < currentSolution.getDistance()) {
					currentSolution = localSearchResult;
					k = 1;
				} else {
					k++;
				}
				if (currentSolution.getDistance() < bestSolution.getDistance()) {
					converge = 0;
					bestSolution = currentSolution;
				} else {
					converge++;
				}
			}

		}
		return bestSolution;
	}

	public Solution enhancedSolve(Solution inital, int convergeConfirm, ArrayList<int[]> rule, int kmax) {
		Solution bestSolution;
		ArrayList<int[]> fix_solution;
		ArrayList<int[]> best_solution = null;
		ArrayList<int[]> current_solution;
		ArrayList<int[]> shake_neighbor;
		ArrayList<int[]> local_search_result;
		int converge = 0;
//		int kmax = 3;
		int k;
		fix_solution = Solution.packageSolutions(rule, inital, 29);
		current_solution = (ArrayList<int[]>) fix_solution.clone();
		HillClimbing hc = new HillClimbing();
		double best_dis = Double.MAX_VALUE;
		double current_dis;

		while (converge < convergeConfirm) {
			k = 1;
			while (k <= kmax) {

				shake_neighbor = Enhencedshake(current_solution, k);
				local_search_result = hc.solveArrayList2(shake_neighbor, k);// k is used to adjust neighborhood
																					// structure
				if (Solution.getdistanceArrayList(local_search_result) < Solution
						.getdistanceArrayList(current_solution)) {
					current_solution = (ArrayList<int[]>) local_search_result.clone();
					k = 1;
				} else {
					k++;
				}

				current_dis = Solution.getdistanceArrayList(current_solution);
				if (current_dis < best_dis) {
					converge = 0;
					best_solution = current_solution;
					best_dis = current_dis;
				} else {
					converge++;
				}
			}

		}
		bestSolution = Solution.arraylistToSolution(best_solution);
		return bestSolution;
	}

	public Solution shake(Solution current, int k) {
		switch (k) {
		case 1:
			return current.shakeOPT1();

		case 2:
			return current.generateNeighbourSolutionCityInsertion();

		case 3:
			return current.shakeOPT3();

		case 4:
			return current.shakeOPT2();
			
		case 5:
			return current.shakeOPT2ByOmar();
			
		}
		return null;
	}

	public ArrayList<int[]> Enhencedshake(ArrayList<int[]> solution, int k) {
		switch (k) {
		case 1:
			return Solution.shakeEnhencedOPT1ArrayList(solution);

		case 2:
			return Solution.shakeEnhencedCityInsertionArrayList(solution);

		case 3:
			return Solution.shakeEnhencedOPT3ArrayList(solution);

		case 4:
			return Solution.shakeEnhencedOPT2ArrayList(solution);
			
		case 5:
			return Solution.shakeEnhencedOPT2ArrayListByOmar(solution);
		}
		return null;
	}
}
