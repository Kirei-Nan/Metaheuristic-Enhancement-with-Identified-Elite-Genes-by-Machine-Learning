package EnhancedMeta_Heuristic;

import java.util.ArrayList;

public class VNSComparisonMainClass {
	public static String SRC = "./resource/";
	public static String INPUT_DIR = SRC + "input/";
	public static String instance = "bays29";// berlin52, eil76, eil101,bays29,dataByGenerator
	public static int datasize = 1000;
	public static int kmax = 4;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int convergeConfirm = 100;
		int step1 = 0;
		int step2 = 0;
		int nbCities = 29;
		String distFileName = INPUT_DIR + instance + "/" + instance + "_dist.txt";
		Problem pb = new Problem(nbCities, distFileName);
		pb.constructDistances();
//		pb.printDistances();
		Solution initialSolution = new Solution(pb);
		VNS vns = new VNS();
		int[] fix = new int[3];
		int[] remove = new int[1];
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////old one for direction test (bays29)
		fix[0] = 293;
		fix[1] = 80;
		fix[2] = 328;
		remove[0] = 185;

		String[] ruleString = { "0", "1", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "14", "15", "18", "19",
				"20", "22", "23", "24", "25", "26", "27", "21,13,", "2,28,", "16,17," };

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////bays29 fix&remove rule
//		fix[0] = 402;
//		fix[1] = 80;
//		remove[0] = 366;
//		String[] ruleString = { "0", "1", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16",
//				"17", "18", "19", "20", "21", "22", "23", "24", "26", "27", "25,28,2",};
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////berlin52 fix&remove rule
//		fix[0] = 55;
//		fix[1] = 380;
//		remove[0] = -1;
//		String[] ruleString = { "0", "2", "3", "4", "5", "7", "10", "11", "12", "13", "14", "15", "16", "17", "18",
//				"19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35",
//				"36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "9,8,",
//				"6,1,", };
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////eil76 fix&remove rule
//		fix[0] = 2723;
//		fix[1] = 2724;
//		remove[0] = -1;
//		String[] ruleString = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15",
//				"16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32",
//				"33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49",
//				"50", "51", "52", "53", "54", "55", "56", "57", "58", "60", "61", "62", "63", "64", "65", "66", "67",
//				"68", "71", "72", "73", "74", "75", "70,59,69,", };
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////eil101 fix&remove rule
//		fix[0] = 2153;
//		fix[1] = 1908;
//		remove[0] = -1;
//		String[] ruleString = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15",
//				"16", "17", "18", "19", "20", "22", "23", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34",
//				"35", "36", "37", "38", "39", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52",
//				"53", "55", "56", "57", "58", "59", "60", "61", "62", "63", "64", "65", "66", "67", "68", "69", "70",
//				"71", "72", "73", "74", "75", "76", "77", "78", "79", "80", "81", "82", "83", "84", "85", "86", "87",
//				"88", "89", "90", "91", "92", "93", "94", "95", "96", "97", "98", "99", "100", "21,40", "24,54,", };
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		ArrayList<int[]> rule = new ArrayList<>();
//		for (int i = 0; i < fix.length; i++) {
//			rule.add(Solution.edgeIndexToCity(fix[i], nbCities));
//		}

		for (int i = 0; i < ruleString.length; i++) {

			if (ruleString[i].contains(",")) {
				String[] arr = ruleString[i].split(",");
				int[] ints = new int[arr.length];

				for (int a = 0; a < arr.length; a++) {
					ints[a] = Integer.parseInt(arr[a]);
				}
				rule.add(ints);
			}
		}

		double totalDistance1 = 0;
		double totalDistance2 = 0;
		long start1 = 0;
		long end1 = 0;
		long time1 = 0;
		long start2 = 0;
		long end2 = 0;
		long time2 = 0;
////////////////////////////////////////////////////////////////////////////////		
		Solution[] enhancedGenerator = Solution.constructWithString(datasize, ruleString, remove, nbCities, pb);
		Solution[] enhancedVNS = new Solution[datasize];
		Solution[] traditionalVNS = new Solution[datasize];

		for (int i = 0; i < datasize; i++) {
			System.out.println(i);

			initialSolution.generateRandom();
///////////////////////////////////////////////////////////////////////////////Traditional	

			start1 = System.currentTimeMillis();
			traditionalVNS[i] = vns.solve(initialSolution, convergeConfirm, kmax);
//			traditionalVNS[i].printSolution();
			end1 = System.currentTimeMillis();
			time1 += (end1 - start1);
			totalDistance1 += traditionalVNS[i].getDistance();

///////////////////////////////////////////////////////////////////////////////Enhanced			
			start2 = System.currentTimeMillis();
//			enhancedGenerator[i].printSolution();
			enhancedVNS[i] = vns.enhancedSolve(enhancedGenerator[i], convergeConfirm, rule, kmax, nbCities);
			end2 = System.currentTimeMillis();
//			enhancedVNS[i].printSolution();
			time2 += (end2 - start2);
			totalDistance2 += enhancedVNS[i].getDistance();
		}

		System.out.println("Test datasize: " + datasize);
		System.out.println("Traditional VNS distance average: " + totalDistance1 / datasize);
		System.out.println("Traditional VNS time average: " + time1 * 1.0 / datasize + "ms");
		System.out.println("Enhanced VNS distance average: " + totalDistance2 / datasize);
		System.out.println("Enhanced VNS time average: " + time2 * 1.0 / datasize + "ms");

	}

}