package EnhancedMeta_Heuristic;

public class GA {
	Solution currentSolution;
//	int maxGenerations = 150;//stop condition
	int converge;
	long start1;
	long end1;
	long convergeTime1;
	double bestSolution;
	
	public Solution solve(Solution inital, int convergeConfirm) {
		    converge=0;
			Problem problem=inital.problem;
			bestSolution=inital.getDistance();
			int nbcities=problem.getNbCities();
			// Initial GA
//			1.populationSize 2. mutationRate 3.crossoverRate 4.elitismCount 5.tournamentSize(select n from population and return the best fitness)
			GeneticAlgorithm ga = new GeneticAlgorithm(125,0.002,0.90,20,15);
			
			// Initialize population
			Population population = ga.initPopulation(problem,nbcities);

			// Evaluate population
			ga.evalPopulation(population);


			// Keep track of current generation
			int generation = 1;
			// Start evolution loop
			start1=System.currentTimeMillis();
			while (converge<=convergeConfirm) {
				//ga.isTerminationConditionMet(generation, maxGenerations) == false
				//converge<=convergeConfirm
				// Print fittest individual from population
				if(population.getFittest(0).getDistance()<bestSolution) {
					end1=System.currentTimeMillis();
					bestSolution=population.getFittest(0).getDistance();
					converge=0;
				}else {
					converge++;
				}
//				System.out.println("G"+generation+" Best distance: " + bestSolution.evaluateSolution());
//				System.out.println(converge);
				// Apply crossover
				population = ga.crossoverPopulation(population,problem);

				// Apply mutation
				population = ga.mutatePopulation(population);

				// Evaluate population
				ga.evalPopulation(population);

				// Increment the current generation
				generation++;
			}
			convergeTime1=end1-start1;
//			System.out.println(convergeTime1);
//			System.out.println("Stopped after " + maxGenerations + " generations.");
//			System.out.println(population.getFittest(0).getDistance()+"         "+convergeTime1);
			return population.getFittest(0);
		}
	
}
