
# Metaheuristic Optimization with Machine Learning for TSP

This project employs a novel approach, integrating machine learning with metaheuristic algorithms to solve the Traveling Salesman Problem (TSP). By identifying elite genes using a decision tree model, we enhance the traditional Variable Neighborhood Search (VNS) algorithm, yielding a Genetically Modified VNS (GM-VNS) that demonstrates superior performance on TSP instances.

## Project Structure

- `code`: Contains the Python code for the decision tree model to identify elite genes.
- `java_project`: Includes all Java files for the enhanced VNS algorithm and its testing.
- `results`: Stores the output and comparative analysis between traditional VNS and GM-VNS.

## Components

- `VNS.java`: Implements standard and enhanced VNS algorithms.
- `Problem.java`: Manages the TSP distance matrix.
- `Solution.java`: Represents a TSP route.
- `VNSComparisonMainClass.java`: The main class that compares VNS algorithms.

## Elite Genes Identification

The Python code in the `code` directory uses machine learning (a decision tree model) to analyze the structure of the TSP solution space and identify elite genes. These genes are crucial edges that significantly impact the quality of TSP solutions.

## Enhanced VNS Algorithm

The Java project in the `java_project` directory applies these elite genes to modify the traditional VNS, creating a GM-VNS that retains the advantageous properties of the identified elite genes throughout the optimization process.

## Results and Analysis

The `results` directory contains the experimental data showcasing the effectiveness of GM-VNS over traditional VNS, validated across various real-world TSP instances.

## Running the Project

1. Run the Python code in the `code` directory to identify elite genes.
2. Compile the Java files in the `java_project` directory.
3. Execute `VNSComparisonMainClass.java` to compare the performance of traditional VNS and GM-VNS.

## License

[MIT](LICENSE.md)
