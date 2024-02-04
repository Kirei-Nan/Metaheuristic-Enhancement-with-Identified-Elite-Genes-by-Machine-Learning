package EnhancedMeta_Heuristic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.Set;

public class Solution {

	/**
	 * In this case, the chromosome is an array of integers rather than a string.
	 */
	private int[] chromosome;// cities
	private double fitness = -1;
	private static Random random = new Random();
	static Problem problem;
	int bestT;

	/**
	 * Initializes individual with specific chromosome
	 * 
	 * @param chromosome The chromosome to give individual
	 */

	/**
	 * Initializes random individual
	 * 
	 * @param chromosomeLength The length of the individuals chromosome
	 */
	public Solution(Problem problem, int chromosomeLength) {
		this.problem = problem;
		// Create random individual
		int[] individual;
		individual = new int[chromosomeLength];

		/**
		 * In this case, we can no longer simply pick 0s and 1s -- we need to use every
		 * city index available. We also don't need to randomize or shuffle this
		 * chromosome, as crossover and mutation will ultimately take care of that for
		 * us.
		 */
		for (int gene = 0; gene < chromosomeLength; gene++) {
			individual[gene] = gene;
		}

		this.chromosome = individual;
	}

	/**
	 * Gets individual's chromosome
	 * 
	 * @return The individual's chromosome
	 */
	public int[] getChromosome() {
		return this.chromosome;
	}

	/**
	 * Gets individual's chromosome length
	 * 
	 * @return The individual's chromosome length
	 */
	public int getChromosomeLength() {
		return this.chromosome.length;
	}

	/**
	 * Set gene at offset
	 * 
	 * @param gene
	 * @param offset
	 */
	public void setGene(int offset, int gene) {
		this.chromosome[offset] = gene;
	}

	/**
	 * Get gene at offset
	 * 
	 * @param offset
	 * @return gene
	 */
	public int getGene(int offset) {
		return this.chromosome[offset];
	}

	/**
	 * Store individual's fitness
	 * 
	 * @param fitness The individuals fitness
	 */
	public void setFitness(double fitness) {
		this.fitness = fitness;
	}

	/**
	 * Gets individual's fitness
	 * 
	 * @return The individual's fitness
	 */
	public double getFitness() {
		return this.fitness;
	}

	/**
	 * Search for a specific integer gene in this individual.
	 * 
	 * For instance, in a Traveling Salesman Problem where cities are encoded as
	 * integers with the range, say, 0-99, this method will check to see if the city
	 * "42" exists.
	 * 
	 * @param gene
	 * @return
	 */
	public boolean containsGene(int gene) {
		for (int i = 0; i < this.chromosome.length; i++) {
			if (this.chromosome[i] == gene) {
				return true;
			}
		}
		return false;
	}

	public Solution(Problem problem) {
		this.problem = problem;
		chromosome = new int[problem.getNbCities()];
		fitness = -1;
//		generateRandom();
	}

	public Solution(Problem problem, int[] cities) {
		this.chromosome = new int[cities.length];
		for (int i = 0; i < cities.length; i++) {
			this.chromosome[i] = cities[i];
		}
		this.problem = problem;
	}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////OPT1
	public Solution generateNeighbourSolutionOPT1() {
		Solution newSolution = new Solution(this.problem, this.chromosome);
		Solution bestSolution = newSolution;
		int nbcities = problem.getNbCities();
		for (int i = 0; i < nbcities - 1; i++) {
			for (int j = i + 1; j < nbcities; j++) {
				newSolution = new Solution(this.problem, this.chromosome);// return to the original solution at
																			// beginning of each iteration
				newSolution.chromosome[i] = chromosome[j];
				newSolution.chromosome[j] = chromosome[i];

//		                        //////////print
//		        				for(int n=0;n<nbcities;n++) {
//		        					System.out.print(newSolution.cities[n]+"->");
//		        				}
//		        				System.out.println(newSolution.evaluateSolution());
//		        				///////////////

				if (newSolution.getDistance() < bestSolution.getDistance()) {
					bestSolution = newSolution;

//							for(int n=0;n<nbcities;n++) {
//		    					System.out.print(bestSolution.cities[n]+"->");
//		    				}
//							System.out.println(bestSolution.evaluateSolution());

				}

			}
		}
		return bestSolution;

	}

	public static ArrayList<int[]> EnhencedOPT1ArrayList(ArrayList<int[]> fix_solution) {
		ArrayList<int[]> newSolution = new ArrayList<>();

		ArrayList<int[]> bestSolution_array = new ArrayList<>();
		double best_dis = Double.MAX_VALUE;
		int size = fix_solution.size();
		for (int i = 0; i < size - 1; i++) {
			for (int j = i + 1; j < size; j++) {
				newSolution = (ArrayList<int[]>) fix_solution.clone();
				Collections.swap(newSolution, j, i);
				double new_dis = getdistanceArrayList(newSolution);
				if (new_dis < best_dis) {
					bestSolution_array = newSolution;
					best_dis = new_dis;
				}
			}
		}
		return bestSolution_array;
	}

	public Solution shakeOPT1() {
		Solution newSolution = new Solution(this.problem, this.chromosome);
		int nbcities = problem.getNbCities();
		int pos1;
		int pos2;
		do {
			pos1 = random.nextInt(nbcities);
			pos2 = random.nextInt(nbcities);
		} while (pos1 == pos2);
		newSolution.chromosome[pos1] = chromosome[pos2];
		newSolution.chromosome[pos2] = chromosome[pos1];
		return newSolution;
	}

	public static ArrayList<int[]> shakeEnhencedOPT1ArrayList(ArrayList<int[]> fix_solution) {
		int pos1;
		int pos2;
		int size = fix_solution.size();
		ArrayList<int[]> newSolution = (ArrayList<int[]>) fix_solution.clone();
		do {
			pos1 = random.nextInt(size);
			pos2 = random.nextInt(size);
		} while (pos1 == pos2);
		newSolution.set(pos1, fix_solution.get(pos2));
		newSolution.set(pos2, fix_solution.get(pos1));
		return newSolution;
	}

	public Solution[] getNeighborsOPT1() {
		Solution newSolution = new Solution(this.problem, this.chromosome);
		int nbcities = problem.getNbCities();
		Solution[] list;
		ArrayList<Solution> temp = new ArrayList();
		int length;
		for (int i = 0; i < nbcities - 1; i++) {
			for (int j = i + 1; j < nbcities; j++) {
				newSolution = new Solution(this.problem, this.chromosome);// return to the original solution at
																			// beginning of each iteration
				newSolution.chromosome[i] = chromosome[j];
				newSolution.chromosome[j] = chromosome[i];
				temp.add(newSolution);
			}
		}
		length = temp.size();
		list = new Solution[length];
		for (int i = 0; i < length; i++) {
			list[i] = temp.get(i);
		}
		return list;
	}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////OPT2

	public Solution generateNeighbourSolutionOPT2ByOmar() {
//		long time=0;
//		long start=System.nanoTime();

		int nbCities = problem.getNbCities();
		Solution current = this;
		Solution dominant = current;

		for (int i1 = 0; i1 < nbCities; i1++) {
			for (int j1 = i1 + 2; j1 < nbCities; j1++) {
				if (((i1 != 0) || (j1 != nbCities)) && ((i1 != 0) || (j1 != nbCities - 2))) {
					Solution neighbor = this.constructSolution2OPT(i1, j1);

					if (neighbor.getDistance() < current.getDistance()) {
						dominant = neighbor;
						current = dominant;
					}
				}
			}
		}
//		long end=System.nanoTime();
//		time+=(end-start);
//		System.out.println("Traditional: "+time);
		return dominant;
	}

	public Solution constructSolution2OPT(int i1, int j1) {
		int i2 = i1 + 1;
		int j2 = j1 + 1;

		Solution neighbor = new Solution(this.problem);

		for (int i = 0; i <= i1; i++) {
			neighbor.chromosome[i] = this.chromosome[i];
		}
		int a = i1 + 1;
		for (int i = j1; i > i1; i--) {
			neighbor.chromosome[a] = this.chromosome[i];
			a++;
		}
		for (int i = j2; i < problem.getNbCities(); i++) {
			neighbor.chromosome[a] = this.chromosome[i];
			a++;
		}

		// neighbor.evaluateSolution();
		return neighbor;
	}

	public Solution shakeOPT2ByOmar() {
		
		 int nbCities = problem.getNbCities();
		 int min_i = 0;
		 int max_i = nbCities - 2;
		 int i = (int) Math.floor(Math.random() * (max_i - min_i + 1) + min_i);
		 int min_j = i;
		 int max_j = nbCities - 1;
		 int j = (int) Math.floor(Math.random() * (max_j - min_j + 1) + min_j);
		 Solution random_neighbor = constructSolution2OPT(i, j);
		 return random_neighbor;

	}

	public Solution generateNeighbourSolutionOPT2() {
//		long start;
//		long end;
//		long time=0;
//		start=System.nanoTime();
		
		Solution newSolution = new Solution(this.problem, this.chromosome);
		Solution bestSolution = newSolution;
		int nbcities = problem.getNbCities();
		for (int i = 0; i < nbcities; i++) {// i1
			for (int j = i + 2; j < nbcities; j++) {// j1
				if ((i != 0) || (j != nbcities - 1)) {
					newSolution = new Solution(this.problem, this.chromosome);// return to the original solution at
																				// beginning of each iteration
					for (int k = 1; k <= j - i; k++) {
						newSolution.chromosome[i + k] = chromosome[j - k + 1];
					}
					if (newSolution.getDistance() < bestSolution.getDistance()) {
						bestSolution = newSolution;
					}
				}
			}
		}
//		end=System.nanoTime();
//		time+=(end-start);
//		System.out.println("TraditionalTime: "+time);

		return bestSolution;
	}

	public static ArrayList<int[]> EnhencedOPT2ArrayList(ArrayList<int[]> fix_solution) {
//		long time=0;
//		long start=System.nanoTime();
//		long end;
		
		ArrayList<int[]> newSolution = new ArrayList<>();
		ArrayList<int[]> bestSolution_array = new ArrayList<>();
		double best_dis = Double.MAX_VALUE;
		double new_dis;
		int size = fix_solution.size();
		for (int i = 0; i < size; i++) {// i1
			for (int j = i + 2; j < size; j++) {// j1
				if ((i != 0) || (j != size - 1)) {
					newSolution = (ArrayList<int[]>) fix_solution.clone();

					for (int k = 1; k <= j - i; k++) {
						newSolution.set(i + k, fix_solution.get(j - k + 1));
					}
					new_dis = getdistanceArrayList(newSolution);

					if (best_dis > new_dis) {
						bestSolution_array = newSolution;
						best_dis = new_dis;

					}
				}
			}
		}
		
		
//		end=System.nanoTime();
//		time+=(end-start);
//		System.out.println("EnhancedOPT2: "+time);
		return bestSolution_array;
	}

	public static ArrayList<int[]> EnhencedOPT2ArrayListByOmar(ArrayList<int[]> fix_solution) {
		 int nbCities = fix_solution.size();
		 int min_i = 0;
		 int max_i = nbCities - 2;
		 int i = (int) Math.floor(Math.random() * (max_i - min_i + 1) + min_i);
		 int min_j = i;
		 int max_j = nbCities - 1;
		 int j = (int) Math.floor(Math.random() * (max_j - min_j + 1) + min_j);
		 ArrayList<int[]> random_neighbor = constructSolution2OPT(i, j,fix_solution);
		 return random_neighbor;
	}

	private static ArrayList<int[]> constructSolution2OPT(int i1, int j1, ArrayList<int[]> fix_Solution) {

		int i2 = i1 + 1;
		int j2 = j1 + 1;
		int size = fix_Solution.size();

		ArrayList<int[]> neighbor = (ArrayList<int[]>) fix_Solution.clone();

		for (int i = 0; i <= i1; i++) {
			neighbor.set(i, fix_Solution.get(i));
		}
		int a = i1 + 1;
		for (int i = j1; i > i1; i--) {
			neighbor.set(a, fix_Solution.get(i));
			a++;
		}
		for (int i = j2; i < size; i++) {
			neighbor.set(a, fix_Solution.get(i));
			a++;
		}

		return neighbor;
	}

	public static ArrayList<int[]> shakeEnhencedOPT2ArrayListByOmar(ArrayList<int[]> fix_Solution) {
		int nbCities = fix_Solution.size();
		ArrayList<int[]> neighbor = null;
		boolean flag = true;
		while (flag) {
			int i1 = random.nextInt(nbCities - 2);
			int j1 = random.nextInt(nbCities - i1 - 2) + (i1 + 2);
			if (((i1 != 0) || (j1 != nbCities)) && ((i1 != 0) || (j1 != nbCities - 2))) {
				neighbor = constructSolution2OPT(i1, j1, fix_Solution);
				flag = false;
			}
		}
		return neighbor;
	}

	public Solution shakeOPT2() {
		Solution newSolution = new Solution(this.problem, this.chromosome);
		int nbcities = problem.getNbCities();
		int i = random.nextInt(nbcities);// i1
		int j = random.nextInt(i + 2) + (nbcities - i - 3);// j1
		for (int k = 1; k <= j - i; k++) {
			newSolution.chromosome[i + k] = chromosome[j - k + 1];
		}
		return newSolution;

	}

	public static ArrayList<int[]> shakeEnhencedOPT2ArrayList(ArrayList<int[]> fix_solution) {
		ArrayList<int[]> newSolution = (ArrayList<int[]>) fix_solution.clone();
		int size = fix_solution.size();
		int i = random.nextInt(size);// i1
		int j = random.nextInt(i + 2) + (size - i - 3);// j1
		for (int k = 1; k <= j - i; k++) {
			newSolution.set(i + k, fix_solution.get(j - k + 1));
		}
		return newSolution;
	}

	public Solution[] getNeighborsOPT2() {
		Solution newSolution = new Solution(this.problem, this.chromosome);
		int nbcities = problem.getNbCities();
		Solution[] list;
		ArrayList<Solution> temp = new ArrayList();
		int length;
		for (int i = 0; i < nbcities; i++) {// i1
			for (int j = i + 2; j < nbcities; j++) {// j1
				if ((i != 0) || (j != nbcities - 1)) {
					newSolution = new Solution(this.problem, this.chromosome);// return to the original solution at
																				// beginning of each iteration

					for (int k = 1; k <= j - i; k++) {
						newSolution.chromosome[i + k] = chromosome[j - k + 1];
					}
					temp.add(newSolution);
				}

			}
		}
		length = temp.size();
		list = new Solution[length];
		for (int i = 0; i < length; i++) {
			list[i] = temp.get(i);
		}
		return list;
	}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////OPT3
	public Solution generateNeighbourSolutionOPT3() {

		Solution newSolution = new Solution(this.problem, this.chromosome);
		Solution bestSolution = newSolution;
		int nbcities = problem.getNbCities();
		int temp;

		for (int i = 1; i < nbcities - 3; ++i) {
			for (int j = i + 1; j < nbcities - 2; ++j) {
				for (int k = j + 1; k < nbcities - 1; ++k) {
					newSolution = new Solution(this.problem, this.chromosome);// return to the original solution at
																				// beginning of each iteration
					// Perform the 3 way swap and test the length
					// swap i,k
					newSolution.chromosome[i] = chromosome[k];
					newSolution.chromosome[k] = chromosome[i];
					// swap j,k
					temp = newSolution.chromosome[j];
					newSolution.chromosome[j] = newSolution.chromosome[k];
					newSolution.chromosome[k] = temp;

					if (newSolution.getDistance() < bestSolution.getDistance()) {
						bestSolution = newSolution;
					}
				}
			}
		}
		return bestSolution;
	}

	public static ArrayList<int[]> EnhencedOPT3ArrayList(ArrayList<int[]> fix_solution) {

		ArrayList<int[]> newSolution = new ArrayList<>();
		ArrayList<int[]> bestSolution_array = new ArrayList<>();

		double best_dis = Double.MAX_VALUE;
		double new_dis;
		int size = fix_solution.size();
		for (int i = 1; i < size - 3; ++i) {
			for (int j = i + 1; j < size - 2; ++j) {
				for (int k = j + 1; k < size - 1; ++k) {
					newSolution = (ArrayList<int[]>) fix_solution.clone();
					newSolution.set(i, fix_solution.get(k));
					newSolution.set(k, fix_solution.get(i));

//							temp.add(newSolution.get(j));
//							newSolution.set(j, newSolution.get(k));
//							newSolution.set(k, temp.get(0));
//							temp.remove(0);

					Collections.swap(newSolution, j, k);
					new_dis = getdistanceArrayList(newSolution);
					if (new_dis < best_dis) {
						bestSolution_array = newSolution;
						best_dis = new_dis;
					}
				}
			}
		}
		return bestSolution_array;
	}

	public Solution shakeOPT3() {
//		    	int randNumber = rand.nextInt(MAX - MIN + 1) + MIN;
		int temp;
		Solution newSolution = new Solution(this.problem, this.chromosome);
		int nbcities = problem.getNbCities();
		int i = random.nextInt(nbcities - 4) + 1;
		int j = random.nextInt(nbcities - i - 3) + i + 1;
		int k = random.nextInt(nbcities - j - 2) + j + 1;
		// swap i,k
		newSolution.chromosome[i] = chromosome[k];
		newSolution.chromosome[k] = chromosome[i];
		// swap j,k
		temp = newSolution.chromosome[j];
		newSolution.chromosome[j] = newSolution.chromosome[k];
		newSolution.chromosome[k] = temp;
		return newSolution;
	}

	public static ArrayList<int[]> shakeEnhencedOPT3ArrayList(ArrayList<int[]> fix_solution) {
		ArrayList<int[]> newSolution = (ArrayList<int[]>) fix_solution.clone();
		int size = fix_solution.size();
		int i = random.nextInt(size - 4) + 1;
		int j = random.nextInt(size - i - 3) + i + 1;
		int k = random.nextInt(size - j - 2) + j + 1;
		// swap i,k
		newSolution.set(i, fix_solution.get(k));
		newSolution.set(k, fix_solution.get(i));
		// swap j,k
		Collections.swap(newSolution, j, k);
		return newSolution;

	}

	public Solution[] getNeighborsOPT3() {
		Solution newSolution = new Solution(this.problem, this.chromosome);
		int nbcities = problem.getNbCities();
		Solution[] list;
		ArrayList<Solution> temparr = new ArrayList();
		int length;
		int temp;
		for (int i = 1; i < nbcities - 3; ++i) {
			for (int j = i + 1; j < nbcities - 2; ++j) {
				for (int k = j + 1; k < nbcities - 1; ++k) {
					newSolution = new Solution(this.problem, this.chromosome);// return to the original solution at
																				// beginning of each iteration
					// Perform the 3 way swap and test the length
					// swap i,k
					newSolution.chromosome[i] = chromosome[k];
					newSolution.chromosome[k] = chromosome[i];
					// swap j,k
					temp = newSolution.chromosome[j];
					newSolution.chromosome[j] = newSolution.chromosome[k];
					newSolution.chromosome[k] = temp;

					temparr.add(newSolution);
				}
			}
		}
		length = temparr.size();
		list = new Solution[length];
		for (int i = 0; i < length; i++) {
			list[i] = temparr.get(i);
		}
		return list;

	}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////CityInsertion
	public Solution generateNeighbourSolutionCityInsertion() {
		Solution newSolution = new Solution(this.problem, this.chromosome);
		Solution bestSolution = newSolution;
		int nbcities = problem.getNbCities();

		for (int i = 0; i < nbcities - 1; i++) {// number of cities
			for (int j = 0; j < nbcities - 1 - i; j++) {// number of iteration for each city

				newSolution = new Solution(this.problem, this.chromosome);

				int t = i;
				for (int k = i + 1; k <= i + j + 1; k++) {
					newSolution.chromosome[t++] = chromosome[k];
				}
				newSolution.chromosome[t++] = chromosome[i];
				for (int p = t; p < nbcities; p++) {
					newSolution.chromosome[p] = chromosome[p];
				}
				if (newSolution.getDistance() < bestSolution.getDistance()) {
					bestSolution = newSolution;
				}

			}
		}
		return bestSolution;
	}

	public static ArrayList<int[]> EnhencedCityInsertionArrayList(ArrayList<int[]> fix_solution) {
		ArrayList<int[]> newSolution = new ArrayList<>();
		ArrayList<int[]> bestSolution_array = new ArrayList<>();
		double best_dis = Double.MAX_VALUE;
		int size = fix_solution.size();
		double new_dis;
		for (int i = 0; i < size - 1; i++) {// number of cities
			for (int j = 0; j < size - 1 - i; j++) {// number of iteration for each city
				newSolution = (ArrayList<int[]>) fix_solution.clone();
				int t = i;
				for (int k = i + 1; k <= i + j + 1; k++) {
					newSolution.set(t++, fix_solution.get(k));
				}
				newSolution.set(t++, fix_solution.get(i));
				for (int p = t; p < size; p++) {
					newSolution.set(p, fix_solution.get(p));
				}
				new_dis = getdistanceArrayList(newSolution);
				if (new_dis < best_dis) {
					bestSolution_array = newSolution;
					best_dis = new_dis;
				}
			}
		}
		return bestSolution_array;
	}

	public Solution shakeCityInsertion() {
		Solution newSolution = new Solution(this.problem, this.chromosome);
		int nbcities = problem.getNbCities();
		int i = random.nextInt(nbcities - 1);
		int j = random.nextInt(nbcities - 1 - i);
		int t = i;
		for (int k = i + 1; k <= i + j + 1; k++) {
			newSolution.chromosome[t++] = chromosome[k];
		}
		newSolution.chromosome[t++] = chromosome[i];
		for (int p = t; p < nbcities; p++) {
			newSolution.chromosome[p] = chromosome[p];
		}
		return newSolution;
	}

	public static ArrayList<int[]> shakeEnhencedCityInsertionArrayList(ArrayList<int[]> fix_solution) {
		ArrayList<int[]> newSolution = (ArrayList<int[]>) fix_solution.clone();
		int size = fix_solution.size();
		int i = random.nextInt(size - 1);
		int j = random.nextInt(size - 1 - i);
		int t = i;
		for (int k = i + 1; k <= i + j + 1; k++) {
			newSolution.set(t++, fix_solution.get(k));
		}
		newSolution.set(t++, fix_solution.get(i));
		for (int p = t; p < size; p++) {
			newSolution.set(p, fix_solution.get(p));
		}
		return newSolution;
	}

	public Solution[] getNeighborsCityInsertion() {
		Solution newSolution = new Solution(this.problem, this.chromosome);
		int nbcities = problem.getNbCities();
		Solution[] list;
		ArrayList<Solution> temp = new ArrayList();
		int length;
		for (int i = 0; i < nbcities - 1; i++) {// number of cities
			for (int j = 0; j < nbcities - 1 - i; j++) {// number of iteration for each city

				newSolution = new Solution(this.problem, this.chromosome);

				int t = i;
				for (int k = i + 1; k <= i + j + 1; k++) {
					newSolution.chromosome[t++] = chromosome[k];
				}
				newSolution.chromosome[t++] = chromosome[i];
				for (int p = t; p < nbcities; p++) {
					newSolution.chromosome[p] = chromosome[p];
				}
				temp.add(newSolution);
			}
		}
		length = temp.size();
		list = new Solution[length];
		for (int i = 0; i < length; i++) {
			list[i] = temp.get(i);
		}
		return list;
	}
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////Print Solutions

	public static void printArray(int[] list) {
		for (int i = 0; i < list.length; i++) {
			System.out.print(list[i] + " ");
		}
		System.out.println();
	}

	public static void printArrayList(ArrayList<int[]> newSolution) {
		for (int t = 0; t < newSolution.size(); t++) {
			for (int y = 0; y < newSolution.get(t).length; y++) {
				System.out.print(newSolution.get(t)[y] + "=");
			}
		}
		System.out.print("=> " + Solution.getdistanceArrayList(newSolution) + "m");
		System.out.println();
	}

	public void printSolution() {

		for (int i = 0; i < problem.getNbCities(); i++)
			System.out.print(chromosome[i] + "-");

		System.out.println("--> " + getDistance() + " km");
	}
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////Solution operations

	public boolean isBetter(Solution s) {
		if (this.getDistance() < s.getDistance()) {
			return true;
		} else {
			return false;
		}
	}

	public void copy(Solution source) {
		for (int i = 0; i < problem.getNbCities(); i++) {
			chromosome[i] = source.chromosome[i];
		}
	}

	public static int getRandom(int borne) {
		Random randomGenerator = new Random();
		int randomInt = randomGenerator.nextInt(borne);
		return randomInt;
	}

	// Initialize a solution : a cycle passing by all cities
	public void generateRandom() {
		int a = -1;
		boolean recommence = true;// ensure that there are no repetitive cities were generated
//		chromosome[0] = 0; // Arbitrary with the city 0

		for (int i = 0; i < problem.getNbCities(); i++) {
			recommence = true;
			while (recommence) {// check if there is repetition
				recommence = false;
				a = getRandom(problem.getNbCities());
				for (int j = 0; j < i; j++) {
					if (a == chromosome[j])
						recommence = true;
				}
			}

			chromosome[i] = a;
		}

	}

	public void generateCircle() {
		chromosome[0] = 0; // Arbitrary with the city 0

		for (int i = 1; i < problem.getNbCities(); i++) {
			chromosome[i] = i;
		}
	}

	public String edgeString() {// 1 means select
		int edge[] = new int[problem.getNbCities()];
		int select[] = new int[problem.getNbCities() * (problem.getNbCities() - 1) / 2];
		int smallerIndex = 0;
		int biggerIndex = 0;
		for (int i = 0; i < chromosome.length; i++) {
			select[i] = 0;
		}
		String s = "";
		for (int i = 1; i < chromosome.length; i++) {
			if (chromosome[i - 1] < chromosome[i]) {
				smallerIndex = chromosome[i - 1];
				biggerIndex = chromosome[i];
			} else {
				smallerIndex = chromosome[i];
				biggerIndex = chromosome[i - 1];
			}
			edge[i - 1] = edgeIndex(smallerIndex + 1, biggerIndex + 1);
			select[edge[i - 1] - 1] = 1;
		}
		if (chromosome[chromosome.length - 1] < chromosome[0]) {
			smallerIndex = chromosome[chromosome.length - 1];
			biggerIndex = chromosome[0];
		} else {
			smallerIndex = chromosome[0];
			biggerIndex = chromosome[chromosome.length - 1];
		}
		edge[chromosome.length - 1] = edgeIndex(smallerIndex + 1, biggerIndex + 1);
		select[edge[chromosome.length - 1] - 1] = 1;

		for (int i = 0; i < chromosome.length * (chromosome.length - 1) / 2; i++) {// iterate all edges
			s = s + (select[i] + ",");
		}
		return s;
	}
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////Get distance

	public double getDistance() {
		double temp = 0;

		for (int i = 0; i < problem.getNbCities() - 1; i++)
			temp += problem.getDistances()[chromosome[i]][chromosome[i + 1]];

		temp += problem.getDistances()[chromosome[0]][chromosome[problem.getNbCities() - 1]];// connect the start and
																								// the end
		return temp;
	}

	public static double getdistanceArrayList(ArrayList<int[]> list) {
		double temp = 0;
		int cache = list.get(0)[0];
		for (int i = 0; i < list.size(); i++) {
			for (int j = 0; j < list.get(i).length; j++) {
				int current = list.get(i)[j];
				temp += problem.getDistances()[cache][current];
				cache = list.get(i)[j];
			}
		}
		temp += problem.getDistances()[list.get(0)[0]][list.get(list.size() - 1)[list.get(list.size() - 1).length - 1]];
		return temp;
	}

	public static double getdistanceLinkedList(LinkedList<int[]> list) {
		double temp = 0;
		int cache = list.get(0)[0];
		int[] array;
		for (Iterator iter = list.iterator(); iter.hasNext();) {
			array = (int[]) iter.next();
			for (int j = 0; j < array.length; j++) {
				int current = array[j];
				temp += problem.getDistances()[cache][current];
				cache = array[j];
			}
		}
		temp += problem.getDistances()[list.get(0)[0]][list.get(list.size() - 1)[list.get(list.size() - 1).length - 1]];
		return temp;
	}
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////Enhanced generator

	// Get random between 0 and born -1

	public int edgeIndex(int city1, int city2) {// city1 should smaller than city2***
		int result = (city1 - 1) * (2 * problem.getNbCities() - city1) / 2 + (city2 - city1);
		return result;
	}

	public static Solution[] constructWithString(int size, String[] fix, int[] remove, int cityNb, Problem pb) {
		Solution[] result = new Solution[size];
		int[][] solutionSet = constructSolutions(size, fix, cityNb, remove);
		for (int i = 0; i < size; i++) {
			Solution s = new Solution(pb);
			for (int j = 0; j < cityNb; j++) {
				s.setGene(j, solutionSet[i][j]);
			}
			result[i] = s;
		}
//				for(int a=0;a<result.length;a++) {
//					result[a].printSolution();
//				}

		return result;
	}

	public static Solution[] constructWithArray(int size, int[] fix, int[] remove, int cityNb, Problem pb) {
		Solution[] result = new Solution[size];
		int[][] fixCityIndex = new int[fix.length][2];
		for (int i = 0; i < fix.length; i++) {
			fixCityIndex[i] = edgeIndexToCity(fix[i], cityNb);
		}
		//////// fix print
//				System.out.println("Fix:");
//		    	for(int i=0;i<fix.length;i++) {
//		    			System.out.println(fixCityIndex[i][0]+"    "+fixCityIndex[i][1]);
//		    	}
		//////////////////////////////////
		String[] array = setCombination(fixCityIndex, cityNb);
		int[][] solutionSet = constructSolutions(size, array, cityNb, remove);
		for (int i = 0; i < size; i++) {
			Solution s = new Solution(pb);
			for (int j = 0; j < cityNb; j++) {
				s.setGene(j, solutionSet[i][j]);
			}
			result[i] = s;
		}
//				for(int a=0;a<result.length;a++) {
//					result[a].printSolution();
//				}

		return result;
	}

	public static int[] edgeIndexToCity(int index, int cityNb) {
		int[] cities = new int[2];
		int city1 = 0;// row
		int city2 = 0;// column
		int sum = cityNb - 1;
		while (sum < index + 1) {
			city1++;
			sum += (cityNb - (city1 + 1));
		}
		city2 = city1 + (index + 1 - (sum - (cityNb - (city1 + 1))));
		cities[0] = city1;
		cities[1] = city2;
		return cities;
	}

	public static int[][] constructSolutions(int size, String[] array, int cityNb, int[] remove) {
		boolean add = false;
		int[][] result = new int[size][cityNb];
		int[] solution;
		Set<int[]> temp = new HashSet<>();
		int k = 0;
		int[][] check = new int[remove.length][2];
		for (int i = 0; i < remove.length; i++) {
			check[i] = edgeIndexToCity(remove[i], cityNb);
		}
		///////// remove print
//				System.out.println("Remove:");
//				for(int i=0;i<remove.length;i++) {
//					System.out.println(check[i][0]+"    "+check[i][1]);
//				}
		/////////

		while (temp.size() != size) {
//			String[] copy = array;
			int i = 0;
			int random;
			solution = new int[cityNb];
			ArrayList<String> list = new ArrayList();
			for (int j = 0; j < array.length; j++) {
				list.add(array[j]);
			}

			while (i < solution.length) {
				random = (int) (Math.random() * list.size());
				String[] value = list.remove(random).split(",");

				for (int j = 0; j < value.length; j++) {
					int city = Integer.parseInt(value[j]);
					solution[i] = city;
					i++;
				}

			}

			for (int j = 0; j < remove.length; j++) {
				for (int h = 0; h < solution.length; h++) {
					if (solution[h] == check[j][0]) {
						if (h - 1 > 0 && solution[h - 1] == check[j][1]) {
							add = false;
						} else if (h + 1 < solution.length && solution[h + 1] == check[j][1]) {
							add = false;
						} else {
							add = true;
						}
					}
				}
			}

			if (add == true) {
				temp.add(solution);
			}
		}

		Iterator iter = temp.iterator();
		while (iter.hasNext()) {
			result[k++] = (int[]) iter.next();
		}

		return result;
	}

	public static String[] setCombination(int[][] fixCityIndex, int cityNb) {
		ArrayList<Set<String>> list = new ArrayList();// to store those sets with single values
		ArrayList<Queue<String>> list2 = new ArrayList();// to store those sets with multiple values
		Set<String> set;
		for (int i = 0; i < cityNb; i++) {// initialize
			set = new HashSet<>();
			set.add(i + "");
			list.add(set);
		}

		for (int i = 0; i < fixCityIndex.length; i++) {// first combination(combine single value set)
			Queue<String> queue = new LinkedList<String>();
			queue.offer(fixCityIndex[i][0] + "");
			queue.offer(fixCityIndex[i][1] + "");
			list2.add(queue);
			list.set(fixCityIndex[i][0], null);
			list.set(fixCityIndex[i][1], null);
		}
		for (int i = list.size() - 1; i >= 0; i--) {
			if (list.get(i) == null) {
				list.remove(i);
			}
		}
		Queue<String> record = new LinkedList<String>();
		int k = 0;
		while (k < list2.size() - 1) {// second combination(combine multiple value set)
			record.clear();
			record.addAll(list2.get(k));
			boolean flag = true;
			while (flag == true) {
				flag = false;
				for (int i = k + 1; i < list2.size(); i++) {
					Queue<String> set1 = list2.get(k);
					Queue<String> set2 = list2.get(i);
					Queue<String> copy1 = new LinkedList<String>();
					Queue<String> copy2 = new LinkedList<String>();

					copy1.addAll(set1);
					copy2.addAll(set2);
					set1.retainAll(set2);
					if (!set1.isEmpty()) {
						flag = true;
						if (copy1.peek() == set1.peek()) {// 2,1
							copy1.removeAll(set1);
							copy2.removeAll(set1);
							copy2.addAll(set1);
							copy2.addAll(copy1);
							list2.set(k, copy2);
						} else {// 1,2
							copy1.removeAll(set1);
							copy2.removeAll(set1);
							copy1.addAll(set1);
							copy1.addAll(copy2);
							list2.set(k, copy1);
						}
						record.clear();
						record.addAll(list2.get(k));
						list2.remove(i);
						i--;
					} else {
						Queue<String> value = new LinkedList<String>();
						value.addAll(record);
						list2.set(k, value);
					}

				}
			}

			k++;
		}

		String result[] = new String[list2.size() + list.size()];
		for (int i = 0; i < list.size(); i++) {
			Iterator iter = list.get(i).iterator();
			while (iter.hasNext()) {
				String str = (String) iter.next();
				result[i] = str;
			}
		}

		for (int i = list.size(); i < result.length; i++) {
			String s = "";
			Iterator iter = list2.get(i - list.size()).iterator();
			while (iter.hasNext()) {
				String temp = (String) iter.next();
				s = s + temp + ",";
			}
			result[i] = s;
		}

//				for(int i=0;i<result.length;i++) {
//					System.out.println(result[i] + ", ");
//				}

		return result;
	}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////Transformation between arraylist and solutions 
	public static ArrayList<int[]> packageSolutions(ArrayList<int[]> rule, Solution s, int nbCities) {
		
		long start1 = System.currentTimeMillis();
		LinkedList<int[]> linked_fix_solution = new LinkedList<int[]>();
		int global_index = 0; // index
		while (global_index < nbCities) {
			int key = s.getGene(global_index); // current city
			int size = getBingingSize(rule, key); // get the size of the package which contain this city
//			System.out.println("size: " + size);
			int[] list = new int[size];
			for (int i = 0; i < list.length; i++) {								
				list[i] = s.getGene(global_index + i);					
			}
//			System.out.println("mark: " + (global_index));
			linked_fix_solution.add(list); // make the sub array secure
			global_index += size;
		}
		long end1 = System.currentTimeMillis();
		long time1 = end1 - start1;
		// System.out.println("package:" + time1);
		ArrayList<int[]> fix_solution = new ArrayList<int[]>(linked_fix_solution);
		return fix_solution;

	}

	public static String getSubString(String[] rule, String key) {
		String s = "";
		for (int j = 0; j < rule.length; j++) {
			String[] as = rule[j].split(",");
			for (int i = 0; i < as.length; i++) {
				if (as[i].equals(key + "")) {
					s = rule[j];
					break;
				}
			}
		}
		return s;
	}

	/*
	 * Find the size of the package which contain the key city
	 */
	public static int getBingingSize(ArrayList<int[]> rule, int key) {
		int size = 0;
		for (int i = 0; i < rule.size(); i++) {
			for (int j = 0; j < rule.get(i).length; j++) {
				if (rule.get(i)[j] == key) {
					return rule.get(i).length;
				}
			}
		}
		return (size + 2) / 2;
	}

	public static Solution arraylistToSolution(ArrayList<int[]> bestSolution_array) {

		Solution bestSolution = new Solution(problem);
		int index_gene = 0;
		for (int a = 0; a < bestSolution_array.size(); a++) {
			for (int b = 0; b < bestSolution_array.get(a).length; b++) {
				bestSolution.setGene(index_gene, bestSolution_array.get(a)[b]);
				index_gene++;
			}
		}

		return bestSolution;
	}

}
