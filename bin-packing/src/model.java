import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import com.opencsv.*; 


public class model {
	private static final String csvfile= "C:\\Users\\DearUser\\Desktop\\samples\\test.csv";
	//static int numberOfJobs = 8;
	//static int numberOfmachines = 4;
	//static int machineCapacity = 10;
	//static int binCapacity = 5;
	//static int processTime = 10; 
	//static int[] jobSize = {2,3,4,5,3,4,2,1};		 // input data
	//static int[] jobReleaseDates = {1,2,10,14,20,22,22,23};  // input data, sorted for now
																// **for sorting: create the array																// jobs like jobs[][] so the dates and sizes are linked when you sort.
		private static int numberOfJobs;
		private static int numberOfmachines;
		private static int machineCapacity;
		private static int binCapacity;
		private static int processTime;
		private static int[] jobSize ;
		private static int[] jobReleaseDates;
		private static String result = null;

		public static void main(String[] args) {
		
		readData(csvfile);
		List<Job> jobsList = new ArrayList<>(numberOfJobs); 	
		List<Machine> machineList = new ArrayList<>(numberOfmachines);
									
		// add the jobs to the List
		for(int i = 0; i < numberOfJobs; i++) {
			Job job = new Job(i+1, jobReleaseDates[i],jobSize[i]);
			jobsList.add(job);
		}
		for(int i = 0; i < numberOfmachines; i++) {
			Machine machine = new Machine(i+1, machineCapacity, processTime);
			machineList.add(machine);
		}
		 
				/******************************* SOLVE	********************************/ 
	/**HEURISTIC FFD: FIRST FIT DECREASING ALGORITHM - OFF-LINE**/
		/*	HeuristicFFD algFFD = new HeuristicFFD(machineList, jobsList, machineCapacity, binCapacity);
		algFFD.solveFFD();	   
	    result = algFFD.showResults();
		writeData("C:\\Users\\DearUser\\Desktop\\samples\\testresultFFD.csv");
	  
	/***HEURISTIC BFD: BEST FIT DECREASING ALGORITHM - OFF-LINE ***/
		/*		HeuristicBFD algBFD = new HeuristicBFD(machineList, jobsList, machineCapacity, binCapacity);
		algBFD.solveBFD();
		result = algBFD.showResults();
		writeData("C:\\Users\\DearUser\\Desktop\\samples\\testresultBFD.csv");	  
	 
	/***HEURISTIC FF: FIRST FIT ALGORITHM - ON-LINE***/
		/*		HeuristicFF algFF = new HeuristicFF(machineList, jobsList, machineCapacity, binCapacity);
		algFF.solveFF();
		result = algFF.showResults();
		writeData("C:\\Users\\DearUser\\Desktop\\samples\\testresultFF.csv");
	   
	/***HEURISTIC BF: BEST FIT ALGORITHM - ON-LINE***/
		/*HeuristicBF algBF = new HeuristicBF(machineList, jobsList, machineCapacity, binCapacity);
		algBF.solveBF();		 
		result = algBF.showResults();
		writeData("C:\\Users\\DearUser\\Desktop\\samples\\testresultBF.csv");
		/*/
	
	}		
		
	public static void writeData(String filePath) { 
			
	    // first create file object for file placed at location 
	    // specified by filepath 
	    File file = new File(filePath); 
	    try { 
	    	System.out.println("start");
	        // create FileWriter object with file as parameter 
	        FileWriter outputfile = new FileWriter(file); 
	       // BufferedWriter bwr = new BufferedWriter(outputfile);
	         //create CSVWriter object filewriter object as parameter 
	        //CSVWriter writer = new CSVWriter(outputfile); 
	       // PrintWriter pwr = new PrintWriter(bwr);
	       // String[] header1 = { "numjobs","nummachines", "machinecap", "bincap","processtime"}; 
	       // writer.writeAll(result); 
	        outputfile.append(String.valueOf(result));
	     
	        
	      // String[] res = {result};
	      // writer.write(res); 
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
	            String[] nextRecord; 
	            nextRecord = csvReader.readNext();
	         
	            System.out.println(nextRecord.length);
	            	numberOfJobs = Integer.valueOf(nextRecord[0]); 
	            	numberOfmachines = Integer.valueOf(nextRecord[1]); 
	            	machineCapacity = Integer.valueOf(nextRecord[2]); 
	            	binCapacity = Integer.valueOf(nextRecord[3]); 
	            	processTime = Integer.valueOf(nextRecord[4]); 
	            
	            	 jobSize = new int[numberOfJobs];
	            	 jobReleaseDates = new int[numberOfJobs];
	                for (int i=0; i<numberOfJobs; i++) { 
	                	jobSize[i] =  Integer.valueOf(nextRecord[i+5]);	                
	                } 
	                System.out.println();
	                for (int i=0; i<numberOfJobs; i++) { 	           
	                	jobReleaseDates[i] =  Integer.valueOf(nextRecord[i+5+numberOfJobs]);	                	
	                }

	            csvReader.close();
	        } 
	        catch (Exception e) { 
	            e.printStackTrace(); 
	        } 
	    }
	
	// Function to sort by column 
	public static void sortbyColumn(int arr[][], int col) 
	{ 
        // Using built-in sort function Arrays.sort 
        Arrays.sort(arr, new Comparator<int[]>() { 
            
          @Override              
          // Compare values according to columns 
          public int compare(final int[] entry1,  
                             final int[] entry2) { 
  
            // To sort in descending order revert  
            // the '>' Operator 
            if (entry1[col] > entry2[col]) 
                return 1; 
            else
                return -1; 
          } 
        });  // End of function call sort(). 
    }
    // print the matrix
    public static void printData(int[][] matrix) {
	    // Display the sorted Matrix 
    	System.out.println("Job ID | release Date | Size");
	    for (int i = 0; i < matrix.length; i++) { 
	        for (int j = 0; j < matrix[i].length; j++) 
	            System.out.print(matrix[i][j] + "           "); 
	        System.out.println(); 
	    } 
    }
}


