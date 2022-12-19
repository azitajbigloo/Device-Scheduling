package CPLEXMODEL;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

public class main {
	private static final String csvfile= "C:\\Users\\DearUser\\Desktop\\samples\\test.csv";	
	private static int N;
	private static int M;
	private static int B;
	private static int p;
	private static int[] w;
	private static int[] r;
	private static String result;
	
	public static void main(String[] args) {
		
		readData(csvfile);
		//System.out.println("*****"+ N +" "+ M+" "+B+" "+p);
		//for(int i = 0 ; i< N; i++) {
			//System.out.println(w[i]+" "+ r[i]) ;
		//}
	// CPLEX model:
	 	/*	int N = 25; // number of jobs
			int M = 2; // number of machines
			int B = 36; // batch capacity
			int p = 60; // job processing time (seconds)
			//double[] w = new double[N]; // array of job sizes
			//int[] w = Test1.UniformWeights();
			Random rnd = new Random();
			int[] w = new int[N];
			for( int j = 0; j < N; j++) {
				w[j] = (rnd.nextInt(35) + 1);
			}
					
			int[] interArrivalTime = new int[N-1]; // array of job interarrival release times
			//double[] r =Test1.UniformReleasDates();
			int[] r = new int[N];
			for( int j = 0; j < N-1; j++) {					
				interArrivalTime[j] = rnd.nextInt(40);
			}
			for( int j = 0; j < N; j++) {				
				if(j == 0)
					r[j] = 0;
				else {
					r[j] = r[j-1] + interArrivalTime[j-1] ;
				}
			}*/
			// nb = roundup(sum(w[j]/B)); // lower bound for number of batches
			double sum_w = 0;
			for(int j = 0 ; j < N; j++) {
				sum_w += w[j];
				//System.out.print(w[j] +","); //" w["+(j+1)+"]="+
			}
			//System.out.println();
			//for(int j = 0 ; j < N; j++) {		
				//System.out.print(r[j]+","); //" r["+(j+1)+"]="+			
			//}
			//System.out.println();
			//for(int j = 0 ; j < N-1; j++) {					
				//System.out.print(interArrivalTime[j]+",");
			//}
			//System.out.println();
			int nb = (int) Math.ceil(sum_w/B);
		//	System.out.println("nb = "+ nb);
			
			CplexModel cplxmodel = new CplexModel();
			CplexModel.MIPmodel(N,M,B,p,w,r,nb);
			result = cplxmodel.toString();	
			//result = String.copyValueOf(CplexModel.MIPmodel(N,M,B,p,w,r,nb));
			writeData("C:\\Users\\DearUser\\Desktop\\samples\\testresultCPLEX.csv");
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
	
	  public static void readData(String file) { 
	        try { 
	            // Create an object of filereader class 
	            // with CSV file as a parameter. 
	            FileReader filereader = new FileReader(file); 
	  
	            // create csvReader object passing 
	            // filereader as parameter 
	            CSVReader csvReader = new CSVReaderBuilder(filereader).build(); 	    
	           // CSVReader csvReader = new CSVReaderBuilder(filereader).withSkipLines(1).build();
	            String[] nextRecord; 
	            nextRecord = csvReader.readNext();
	         
	           // System.out.println(nextRecord.length);
	            	N = Integer.valueOf(nextRecord[0]); 
	            	M = Integer.valueOf(nextRecord[1]); 
	            //	machineCapacity = Integer.valueOf(nextRecord[2]); 
	            	B = Integer.valueOf(nextRecord[3]); 
	            	p = Integer.valueOf(nextRecord[4]); 
	            
	            	 w = new int[N];
	            	 r = new int[N];
	                for (int i=0; i<N; i++) { 
	                	w[i] =  Integer.valueOf(nextRecord[i+5]);	                
	                } 
	                System.out.println();
	                for (int i=0; i<N; i++) { 	           
	                	r[i] =  Integer.valueOf(nextRecord[i+5+N]);	                	
	                }

	            csvReader.close();
	        } 
	        catch (Exception e) { 
	            e.printStackTrace(); 
	        } 
	    }
		
}		


