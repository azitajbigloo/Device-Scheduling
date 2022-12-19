package simulationData;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class DataGetenerator {

	private static int numberOfJobs;
	private static int numberOfmachines;
	private static int machineCapacity;
	private static int binCapacity;
	private static int processTime;
	private static int[] jobSize ;
	private static int[] jobInterArrivalTimes;
	private static int runId;
	private static int jobId;
	private static int[][] data;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int x = 720;
		runId = 180;
		jobId = 1;
		numberOfJobs = 50;  //change
		numberOfmachines = 1;   //change
		machineCapacity = 36;
		binCapacity = 36;
		processTime = 60;
		Random rnd = new Random();
		Random rnd2 = new Random();
		jobSize = new int[numberOfJobs];
		jobInterArrivalTimes = new int[numberOfJobs];
		int rows = runId*numberOfJobs;
		data = new int[rows][4];
		int i = 0;
		
		for( i = 0; i < runId ; i++) {
			for( int j = 0; j < numberOfJobs; j++) {
				data[j+i*numberOfJobs][1]  = j+ 1;
				//runId[i] = i+1;//
				data[j+i*numberOfJobs][0] = i+ 1 + x;
				//jobSize[j] = (rnd.nextInt(35) + 1);
				data[j+i*numberOfJobs][3] =  (rnd2.nextInt(35) + 1);
			}
			//int[] interArrivalTime = new int[numberOfJobs-1]; // array of job interarrival release times
			for( int j = 0; j < numberOfJobs-1; j++) {					
				//interArrivalTime[j] = (rnd.nextInt(39)+1);
				data[j+i*numberOfJobs][2] =  (rnd.nextInt(39)+1);
			}
			
		}
		for(int p = 0; p < runId ; p++) {
			for( int j = 0; j < numberOfJobs; j++) {
				System.out.print(data[j+p*numberOfJobs][0]+ "  ");
				System.out.print(data[j+p*numberOfJobs][1]+"  ");
				System.out.print(data[j+p*numberOfJobs][2]+"  ");
				System.out.print(data[j+p*numberOfJobs][3]);
				System.out.println();
			}
		}
		
		writeData("C:\\Users\\azita\\Desktop\\test\\tests.csv");
	}

	
public static void writeData(String filePath) { 
		
	    // first create file object for file placed at location 
	    // specified by filepath 
	    File file = new File(filePath); 
	    try { 
	    	System.out.println("start");
	        // create FileWriter object with file as parameter 
	        FileWriter outputfile = new FileWriter(file); 
	       // String[] header = { "RunId","JobId", "InterArrival Time", "Job Size"}; 
	       // outputfile.append(String.valueOf(header));
	       // outputfile.append("\n");
	      
	   
	        for(int p = 0; p < runId ; p++) {
				for( int j = 0; j < numberOfJobs; j++) {
					outputfile.append(String.valueOf(data[j+p*numberOfJobs][0]));
		        	outputfile.append(",");
		        	outputfile.append(String.valueOf(data[j+p*numberOfJobs][1]));
			        outputfile.append(",");
			        outputfile.append(String.valueOf(data[j+p*numberOfJobs][2]));
				    outputfile.append(",");
				    outputfile.append(String.valueOf(data[j+p*numberOfJobs][3]));
					outputfile.append("\n");
					
					
				}
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
