package EnhancedMeta_Heuristic;
import java.util.Arrays;

public class GeneticAlgorithm {
//	Integration integration=new Integration();
	int convergeConfirm=5;
    private int populationSize;
    private double mutationRate;
    private double crossoverRate;
    private int elitismCount;
	protected int tournamentSize;

	public GeneticAlgorithm(int populationSize, double mutationRate, double crossoverRate, int elitismCount,
			int tournamentSize) {
		
        this.populationSize = populationSize;
        this.mutationRate = mutationRate;
        this.crossoverRate = crossoverRate;
        this.elitismCount = elitismCount;
		this.tournamentSize = tournamentSize;
	}
	
    public Population initPopulation(Problem problem,int chromosomeLength){
        // Initialize population
        Population population = new Population(this.populationSize,problem,chromosomeLength);
        return population;
    }
    
	public boolean isTerminationConditionMet(int generationsCount, int maxGenerations) {
		return (generationsCount > maxGenerations);
	}
    
    public double calcFitness(Solution individual){
        // Get fitness
        double fitness = 1 / individual.getDistance();
                
        // Store fitness
        individual.setFitness(fitness);
        
        return fitness;
    }

    public void evalPopulation(Population population){
        double populationFitness = 0;
        
        // Loop over population evaluating individuals and summing population fitness
        for (Solution individual : population.getIndividuals()) {
            populationFitness += this.calcFitness(individual);
        }
        
        double avgFitness = populationFitness / population.size();
        population.setPopulationFitness(avgFitness);
    }
 
	public Solution selectParent(Population population) {
		// Create tournament
		Population tournament = new Population(this.tournamentSize);

		// Add random individuals to the tournament
		population.shuffle();
		for (int i = 0; i < this.tournamentSize; i++) {
			Solution tournamentIndividual = population.getIndividual(i);
			tournament.setIndividual(i, tournamentIndividual);
		}

		// Return the best
		return tournament.getFittest(0);
	}

	
    public Population crossoverPopulation(Population population,Problem problem){
        // Create new population
        Population newPopulation = new Population(population.size());
        
        // Loop over current population by fitness
        for (int populationIndex = 0; populationIndex < population.size(); populationIndex++) {
            // Get parent1
            Solution parent1 = population.getFittest(populationIndex);
            
            
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////            
//            parent1=parent1.generateNeighbourSolutionOPT2();
//            parent1=integration.solve(parent1, convergeConfirm, 5);
////////////////////////////////////////////////////////////////////////////////////////////////////////////////            
            
            // Apply crossover to this individual?
            //elite doesn't 
            if (this.crossoverRate > Math.random() && populationIndex >= this.elitismCount) {
                // Find parent2 with tournament selection
                Solution parent2 = this.selectParent(population);
                
                
////////////////////////////////////////////////////////////////////////////////////////////////////////////                
//             parent2=parent2.generateNeighbourSolutionOPT2();
//               parent2=integration.solve(parent2, convergeConfirm, 7);
///////////////////////////////////////////////////////////////////////////////////////////////////////////////               

                // Create blank offspring chromosome
                int offspringChromosome[] = new int[parent1.getChromosomeLength()];
                Arrays.fill(offspringChromosome, -1);
                Solution offspring = new Solution(problem,offspringChromosome);

                // Get subset of parent chromosomes
                int substrPos1 = (int) (Math.random() * parent1.getChromosomeLength());
                int substrPos2 = (int) (Math.random() * parent1.getChromosomeLength());

                // make the smaller the start and the larger the end
                final int startSubstr = Math.min(substrPos1, substrPos2);
                final int endSubstr = Math.max(substrPos1, substrPos2);

                // Loop and add the sub tour from parent1 to our child
                for (int i = startSubstr; i < endSubstr; i++) {
                    offspring.setGene(i, parent1.getGene(i));
                }

                // Loop through parent2's city tour
                for (int i = 0; i < parent2.getChromosomeLength(); i++) {
                    int parent2Gene = i + endSubstr;
                    if (parent2Gene >= parent2.getChromosomeLength()) {
                        parent2Gene -= parent2.getChromosomeLength();
                    }

                    // If offspring doesn't have the city add it
                    if (offspring.containsGene(parent2.getGene(parent2Gene)) == false) {
                        // Loop to find a spare position in the child's tour
                        for (int ii = 0; ii < offspring.getChromosomeLength(); ii++) {
                            // Spare position found, add city
                            if (offspring.getGene(ii) == -1) {
                                offspring.setGene(ii, parent2.getGene(parent2Gene));
                                break;
                            }
                        }
                    }
                }
 ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////  
//                offspring=integration.solve(offspring, convergeConfirm, 3);
//                offspring=offspring.generateNeighbourSolutionOPT2();
//                offspring=integration.solve(offspring, convergeConfirm, 5);
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                
                
                // Add child
                newPopulation.setIndividual(populationIndex, offspring);
            } else {
                // Add individual to new population without applying crossover
                newPopulation.setIndividual(populationIndex, parent1);
            }
        }
        
        return newPopulation;
    }

    public Population mutatePopulation(Population population){
        // Initialize new population
        Population newPopulation = new Population(this.populationSize);
        
        // Loop over current population by fitness
        for (int populationIndex = 0; populationIndex < population.size(); populationIndex++) {
            Solution individual = population.getFittest(populationIndex);

            // Skip mutation if this is an elite individual
            if (populationIndex >= this.elitismCount) {
                // Loop over individual's genes
                for (int geneIndex = 0; geneIndex < individual.getChromosomeLength(); geneIndex++) {   
                	// System.out.println("\tGene index "+geneIndex);
                    // Does this gene need mutation?
                    if (this.mutationRate > Math.random()) {
////////////////////////////////////////////////////////////////////////////////////////////////////////Random exchange
                        // Get new gene position
                        int newGenePos = (int) (Math.random() * individual.getChromosomeLength());
                        // Get genes to swap
                        int gene1 = individual.getGene(newGenePos);
                        int gene2 = individual.getGene(geneIndex);
                        // Swap genes
                        individual.setGene(geneIndex, gene1);
                        individual.setGene(newGenePos, gene2);
/////////////////////////////////////////////////////////////////////////////////////////////////////////////
//                        individual=individual.generateNeighbourSolutionOPT2();
//                      individual=integration.solve(individual, convergeConfirm, 5);
///////////////////////////////////////////////////////////////////////////////////////////////////////////////                        
                    }
                }

                
                
                
////////////////////////////////////////////////////////////////////////////////////////////////////after mutation               
//                individual=individual.generateNeighbourSolutionOPT1();
//                individual=integration.solve(individual, convergeConfirm, 5);
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                
                
            }
            
            // Add individual to population
            newPopulation.setIndividual(populationIndex, individual);
        }
        
        // Return mutated population
        return newPopulation;
    }

}
