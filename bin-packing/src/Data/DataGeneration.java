package Data;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class DataGeneration {
	private static int numberOfJobs;
	private static int numberOfmachines;
	private static int machineCapacity;
	private static int binCapacity;
	private static int processTime;
	private static int[] jobSize ;
	private static int[] jobReleaseDates;


	public static void main(String[] args) {
		numberOfJobs = 10;  //change
		numberOfmachines = 4;   //change
		machineCapacity = 36;
		binCapacity = 36;
		processTime = 60;
		Random rnd = new Random();
		jobSize = new int[numberOfJobs];
		jobReleaseDates = new int[numberOfJobs];
		for( int j = 0; j < numberOfJobs; j++) {
			jobSize[j] = (rnd.nextInt(35) + 1);
		}
	
		int[] interArrivalTime = new int[numberOfJobs-1]; // array of job interarrival release times
		for( int j = 0; j < numberOfJobs-1; j++) {					
			interArrivalTime[j] = rnd.nextInt(40);
		}
		for( int j = 0; j < numberOfJobs; j++) {				
			if(j == 0)
				jobReleaseDates[j] = 0;
			else {
				jobReleaseDates[j] = jobReleaseDates[j-1] + interArrivalTime[j-1] ;
			}
		}
		writeData("C:\\Users\\DearUser\\Desktop\\samples\\test.csv");
	}
	

	public static void writeData(String filePath) { 
		
	    // first create file object for file placed at location 
	    // specified by filepath 
	    File file = new File(filePath); 
	    try { 
	    	System.out.println("start");
	        // create FileWriter object with file as parameter 
	        FileWriter outputfile = new FileWriter(file); 
	        String[] header1 = { "numjobs","nummachines", "machinecap", "bincap","processtime", "jobsizes","jobreleasedates after N cells"}; 
	        outputfile.append(String.valueOf(numberOfJobs));
	        outputfile.append(",");
	        outputfile.append(String.valueOf(numberOfmachines));
	        outputfile.append(",");
	        outputfile.append(String.valueOf(machineCapacity));
	        outputfile.append(",");
	        outputfile.append(String.valueOf(binCapacity));
	        outputfile.append(",");
	        outputfile.append(String.valueOf(processTime));
	        outputfile.append(",");
	        for (int i=0; i<numberOfJobs; i++) { 
	        	outputfile.append(String.valueOf(jobSize[i]));
	        	 outputfile.append(",");
	        }
	        for (int i=0; i<numberOfJobs; i++) { 
	        	outputfile.append(String.valueOf(jobReleaseDates[i]));
	        	 outputfile.append(",");
	        }

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
}
