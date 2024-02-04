package EnhancedMeta_Heuristic;

public class ruleString {

	public static void main(String[] args) {
		// TODO Auto-generated method stub				
		int[] fix = new int[2];
		fix[0] = 2153;
		fix[1] = 1908;
		String[] ruleString = getRuleString(fix, 101);
		
		for(int i=0;i<ruleString.length;i++) {
			System.out.print("\""+ruleString[i]+"\",");
			
		}
	}
	
	
	public static String[] getRuleString(int[] fix, int problem_size) {
		int[] array = new int[problem_size];
		for(int i=0;i<problem_size;i++) {
			array[i] = i;
		}

		
		String[] ruleString = new String[problem_size-fix.length];
		int String_index_rear = ruleString.length-1;
		
		for(int j=0;j<fix.length;j++) {
			int[] cities = edgeIndexToCity(fix[j], problem_size);
			ruleString[String_index_rear] = arrayTransformString(cities);
			String_index_rear--;
			
			for(int a=0;a<cities.length;a++) {
				for(int b=0;b<array.length;b++) {
					if(array[b]==cities[a]) {
						array[b]=-1;
					}
				}
			}
		}
		
		int String_index_head = 0;
		for(int k=0;k<array.length;k++) {
			if(array[k]!=-1) {
				ruleString[String_index_head] = Integer.toString(array[k]);
				String_index_head++;
			}
		}

		return ruleString;
		
		
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
	
	public static String arrayTransformString(int[] array) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < array.length; i++) {
			sb.append(array[i] + ",");
		}
		return sb.toString();
	}
	

}
