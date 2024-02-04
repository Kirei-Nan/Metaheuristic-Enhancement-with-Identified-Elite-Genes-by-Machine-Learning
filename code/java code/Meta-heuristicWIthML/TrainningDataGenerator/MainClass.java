package TrainningDataGenerator;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import IOTest.CSVUtils;

public class MainClass {

	 public static String SRC="./resource/";
	 public static String INPUT_DIR = SRC+"input/";
	 public static String instance = "dataByGenerator";//berlin52, eil76, eil101,bays29,dataByGenerator
	 static int n0=0;//opt1
	 static int n1=0;//opt2
	 static int n2=0;//opt3
	 static int n3=0;//CityInsertion
	 static int datasize=1000;
	 static int currentDataSize=0;
	 static String result="";
	 static boolean add=false;
	 static List<String>data=new ArrayList<String>();//OPT1
	 static List<String>OPT2=new ArrayList<String>();//OPT2
	 static List<String>OPT3=new ArrayList<String>();//OPT2
	 static List<String>CI=new ArrayList<String>();//CityInsertion
	 static LinkedHashSet<String> set=new LinkedHashSet<String>();
	 static LinkedHashSet<String> tempSet=new LinkedHashSet<String>();
	 static HillClimbing hc=new HillClimbing();
	 static VNS vns=new VNS();
	 static int lastRecord=0;
	 
    public static void main(String[] args) {
	// TODO Auto-generated method stub
//    	int k=1;// 1.genetic 2.hillclimbing 3. rvns(fast) 4. vnd 5. vns 6. sa 7.tabu
    	int convergeConfirm=200;// if the bestsolution didn't change in convergeConfirm loops then it shows that it converges
    	int nbCities = 15;
    	int kmax=4;
    	int confirm=100;
    	String distFileName= INPUT_DIR+instance+"/"+instance+"_dist.txt";
    	
    	Problem pb = new Problem(nbCities, distFileName);
    	pb.constructDistances();
//    	pb.printDistances();

	
//	Solution s0 = new Solution(pb);
//	s0.generateRandom();
//	System.out.println("The initial solution is "+s0.getDistance());
//	Solution enhanced=integration.solve(s0,convergeConfirm,5);
//	System.out.println("The Enhanced solution is "+enhanced.getDistance());
    	
//////////////////////////////////////////////////////////////////////////////generate data
    	Solution x1=new Solution(pb);
    	Solution x2=new Solution(pb);
		do {
			Solution s=new Solution(pb);
			s.generateRandom();
//			s.printSolution();
			x1.copy(s);
			x2=hc.solve(s, 4);
//			x2.printSolution();
//			x2=vns.solve(s, confirm, kmax);
			
//          	  System.out.println(n);
          	  if(x2.isBetter(x1)) {//x1坏，x2好
              	  if(lastRecord==1) {//上个是好的，就放坏的
              		  result=x1.edgeString()+0;
              		  add=set.add(result);
              		  if(add==true) {
              			  lastRecord=0;
              			currentDataSize++;
              		  }
              	  }else {//上个是坏的，就放好的
              		 result=x2.edgeString()+1;
              		 add=set.add(result);
             		  if(add==true) {
             			  lastRecord=1;
             			currentDataSize++;
             		  }
              	  }
                }else {//1 x1是好的
              	  if(lastRecord==0) {
              		  result=x1.edgeString()+1;
              		  add=set.add(result);
              		  if(add==true) {
              			  lastRecord=1;
              			currentDataSize++;
              		  }
              	  }
              	  
                }
          	  
			System.out.println(currentDataSize);
		}while(currentDataSize<datasize);
		
		String features="";
		for(int i=0;i<(nbCities*(nbCities-1))/2;i++) {
			features=features+(i+",");
		}
		
//		for(int i=0;i<nbCities;i++) {
//			features=features+(i+",");
//		}
		
		features=features+"y";
		data.add(features);
		
		 for (String str : set) {
	    	 data.add(str);
	     }
	     
	     boolean isSuccess=CSVUtils.exportCsv(new File("/Users/nanzhenghan/Desktop/HCByGenerator.csv"), data);
	     System.out.println(isSuccess);
	
    }
	
}
