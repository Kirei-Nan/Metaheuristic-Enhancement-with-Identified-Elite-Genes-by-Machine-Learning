package EnhancedMeta_Heuristic;

import java.util.ArrayList;

public class EnhancedOPT2Test {
	 public static String SRC="./resource/";
	 public static String INPUT_DIR = SRC+"input/";
	 public static String instance = "bays29";
	 public static int datasize=3;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int nbCities = 29;
		String distFileName= INPUT_DIR+instance+"/"+instance+"_dist.txt";
    	int fix[]=new int[3];
    	int remove[]=new int[1];
    	fix[0]=293;
    	fix[1]=80;
    	fix[2]=328;
    	remove[0]=185;
    	Problem pb = new Problem(nbCities, distFileName);
    	pb.constructDistances();
    	ArrayList<int[]> rule = new ArrayList<>();
    	for(int i=0;i<fix.length;i++) {
    		rule.add(Solution.edgeIndexToCity(fix[i], nbCities));
    	}
    	
		Solution[] e_opt2=new Solution[datasize];
		String[] ruleString = {"0", 
				"1", 
				"3", 
				"4", 
				"5", 
				"6", 
				"7", 
				"8", 
				"9", 
				"10", 
				"11", 
				"12", 
				"14", 
				"15", 
				"18", 
				"19", 
				"20", 
				"22", 
				"23", 
				"24", 
				"25", 
				"26", 
				"27", 
				"13,21,", 
				"2,28,", 
				"16,17,"};
		long start1;
		long end1;
		long time1=0;
		long start2;
		long end2;
		long time2=0;
		Solution opt2Enhanced=null;
		ArrayList<int[]>temp=null;
		Solution opt2Enhanced2=null;
		
		
		Solution[] control=Solution.constructWithString(datasize,ruleString,remove,nbCities,pb);
		start1=System.nanoTime();
		for(int i=0;i<datasize;i++) {
			
			Solution sol=new Solution(pb);
    		sol.generateRandom();
    		opt2Enhanced=sol.generateNeighbourSolutionOPT2();
		}
		end1=System.nanoTime();
		time1+=(end1-start1);
		
		
		start2=System.nanoTime();
		for(int i=0;i<datasize;i++) {
			temp=Solution.EnhencedOPT2ArrayList(Solution.packageSolutions(rule,control[i],nbCities));	
		}
		end2=System.nanoTime();
		time2+=(end2-start2);
		opt2Enhanced2=Solution.arraylistToSolution(temp);
		
		
		System.out.println("Traditional: "+time2*1.0/datasize);
		opt2Enhanced.printSolution();
		System.out.println("Enhanced: "+time1*1.0/datasize);
		opt2Enhanced2.printSolution();
		
	}

}
