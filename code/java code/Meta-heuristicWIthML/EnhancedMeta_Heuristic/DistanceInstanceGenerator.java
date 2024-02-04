package EnhancedMeta_Heuristic;

import java.util.Scanner;

public class DistanceInstanceGenerator {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int random;
		int range=1000;
		int averageController=40;
		Scanner input=new Scanner(System.in);
		System.out.println("Please enter the size: ");
		int size=input.nextInt();
		for(int i=0;i<size-1;i++) {
			for(int j=0;j<size-1-i;j++) {
				random=(int) (Math.random()*range)+averageController;
				System.out.print(random+" ");
			}
			System.out.println();
		}
		
	}

}
