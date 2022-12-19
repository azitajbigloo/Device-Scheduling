package optimalTest;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

public class test {	
	private static int N;
	private static int B ;
	private static int p =1;
	private static Integer[] w ;
	private static String result;

	public static void main(String[] args) {
		
		int numRuns = 10;
		for(int p = 0; p < numRuns; p++) {
			N = generateRandomIntIntRange(10,30);
			w = new Integer[N];
			B = generateRandomIntIntRange(60,70);
			System.out.println( "N "+N);
			System.out.println( "B "+B);
			int x = generateRandomIntIntRange(1,7);
			// System.out.println( "x "+x);
			int[] z = new int[N];
			for(int i=0 ; i< N; i++) {	
				 z[i] = generateRandomIntIntRange(1,5);
				// System.out.println( z[i]);
			}
			for(int i=0 ; i< N; i++) {		
				int y = (int) Math.pow(2, z[generateRandomIntIntRange(0,6)]);
				// System.out.println( y);
				//w[i] =  generateRandomIntIntRange(1,B);
				if( y <= B) {
					w[i] = y;
					System.out.println(w[i]);
				}
				else {
					i--;
					System.out.println("NO");
				}
				
			}
			
			//for(int i=0 ; i< N; i++) {	
				//System.out.println( w[i]);
				//if( w[i]<= B)
				//	System.out.println( "yes");
			//}
			int[] FFI = new int[2];
			int[] FFD = new int[2];
			FFI solveFFI = new FFI(N,w,B,p);
			FFI = solveFFI.FFIAlg();
			FFD solveFFD = new FFD(N,w,B,p);
			FFD = solveFFD.FFDAlg();
			CPLEX cplex = new CPLEX();
			cplex.MIPmodel(N, w, B, p);
			
			if((CPLEX.res[0]< FFI[0] ) || (CPLEX.res[0]< FFD[0] ))
				break;
				//System.out.println("                      *************ERROR*****************");
				
				
			
		}
		
		
		//System.out.println("*****"+ N +" "+ M+" "+B+" "+p);
		//for(int i = 0 ; i< N; i++) {
			//System.out.println(w[i]+" "+ r[i]) ;
		//}
		
	
			//writeData("C:\\Users\\azita\\Desktop\\samples\\optimalTest.csv");
	}
	
	public static void writeData(String filePath) { 	
	    // first create file object for file placed at location 
	    // specified by filepath 
	    File file = new File(filePath); 
	    try { 
	    	System.out.println("start");
	        FileWriter outputfile = new FileWriter(file); 
	        if (result != null)
	        	outputfile.append(String.valueOf(result));
            //closing writer connection 
	        outputfile.flush();
	        outputfile.close(); 
	        System.out.println("end");          
	    } 
	    catch (IOException e) { 
	        // TODO Auto-generated catch block 
	        e.printStackTrace(); 
	        System.out.println("Error");
	    } 
	}	
	public static int generateRandomIntIntRange(int min, int max) {
	    Random r = new Random();
	    return r.nextInt((max - min) + 1) + min;
	}
	public static void getStreamOfRandomIntsWithRange(int num, int min, int max) {
	    Random random = new Random();
	    random.ints(num,min, max).sorted().forEach(System.out::println);
	}
}		


