package EnhancedMeta_Heuristic;

import java.util.Scanner;

public class edgeIndexToCity {
	public static void main(String arg[]) {
		int cityNb = 76;
		Scanner input = new Scanner(System.in);
		System.out.println("Input index: ");
		int index = input.nextInt();
		int[] cities = edgeIndexToCity(index, cityNb);
		for(int i=0;i<cities.length;i++) {
			System.out.print(cities[i]+"  ");
		}
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
	
}
