import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HeuristicFFD {

	private ArrayList<Bin> bins;
	private ArrayList<Bin> sortedBins; // all bins sorted in increasing order of ready time
	private ArrayList<Job> jobsList;
	private ArrayList<Job> sortedJobs; // all jobs sorted in decreasing order of size
	List<Machine> machineList;
	private int binCapacity;
	private int machineCapacity;
	private int binCounter = 1;
	private int Cmax = 0;
	
	// Constructor
	public HeuristicFFD(List<Machine> machineList ,List<Job> jobsList, int machineCapacity, int binCapacity) {
		this.binCapacity = binCapacity;
		this.jobsList = (ArrayList<Job>) jobsList;
		this.machineList = machineList;
		this.machineCapacity = machineCapacity;
		this.bins = new ArrayList<Bin>();		
	}
	// FFD algorithm
	public void solveFFD() {
		
		// if the greatest job (job 0) is bigger that the batch capacity
		int maxSize = 0;
		for (Job job : jobsList) {
			if(job.getSize() > maxSize)
				maxSize = job.getSize();
		}
		if( maxSize > binCapacity) {
			System.out.println("No feasible solution");
			return;
		}

		//Phase 1
		// sort jobs based on non-increasing release Date
		sortedJobs = jobsList; // this does not work, both list get sorted
		Collections.sort(sortedJobs, new SortByJobSize()); 		
		this.bins.add(new Bin (binCapacity, binCounter));  	// create the first bin
		for(Job currentJob : jobsList) { 
			boolean retVal = false;
			for (Bin currentBin : bins) {
				retVal = currentBin.put(currentJob); // true if job fits the bin
				if(retVal)
					break;					
			}	
			if(!retVal) {
				Bin newBin = new Bin(binCapacity, ++binCounter);
				bins.add(newBin);
				retVal = newBin.put(currentJob); // true if job fits the bin
			}
		}
		//Phase 2
		// sort bins based on increasing ready time
		sortedBins = bins; // this does not work, both list get sorted
		Collections.sort(sortedBins, new SortByBinReadyTime());	   
		for (Bin bin : bins) { // should be sorted bins			
			for( Machine currentMachine : machineList) {				
				int i = -1;
				if((sortedBins.indexOf(bin)+1) % machineList.size() == currentMachine.getId()) 
					i = currentMachine.getId();

				if((sortedBins.indexOf(bin)+1) % machineList.size() == 0) {
					if (currentMachine.getId() == machineList.size())
						i = currentMachine.getId(); // this should be last machine in the list					
				}
				if(i == currentMachine.getId()) {
					currentMachine.addBinToMachine(bin);
					if(Cmax < currentMachine.machineFinishTime) {
						Cmax = currentMachine.machineFinishTime;
					}			
					break;
				}			
			}
		}
	}	
	public String showResults() {
		String result = null;
		result +=   "First Fit Decreasing Algorithm/Off-line" + "\n";
		for (Machine machine : this.machineList) {
			result += "\n" + machine.toString();
		}
		result += "\n" + "Cmax = "+ Cmax;
		return result;	
	}
	
}
//pseudocode
/* for each job:
 * 	 for each bin:
 * 		if the job fits the bin
 * 			put the job
 * 			update bin ready time
 * 			update bin capacity
 * 			set the boolean value to true (job have been placed)
 * 			go to next job, break for
 * 		else
 * 			go to the next bin
 * 	end for
 * 	if job is in no bin, boolean value is false
 * 		open a new bin
 * 		put the job
 * 		update bin ready time
 * 		update bin capacity
 * 		set the boolean value to true (job have been placed)
 * 		go to next job, break for
 *	
 *end for
 *
 *sort bins in increasing order of ready time
 *for each bin
 *	for each machine
 *		if the bin fits the machine (capacity)
 *			add bin to the machine
 *			update machine capacity
 *			update machine ready time
 *			set the boolean value to true, bin is in machine	
 *			go to next bin
 *		else
 *			go to the next machine
 *		if all machines are full, boolean value is false
 *			find the machine with smallest finish time
 *			add the bin
 *			update machine ready time: max(previous machine finish time, bin ready time)
 *			update machine capacity
 *			update machine finish time
 *			set the boolean value to true
 *			go to the next bin
 *	end for
 *end for
 *
 *ALTERNATIVE APPROACH FOR SECOND PHASE:
 *for each bin
	 *for (i = 0, i < number of machines, i++)
	 *	if the bin id mode number of machines == 0
	 *		add bin to machine n  
	 *		update machine ready time: max(previous machine finish time, bin ready time)
	 *		update machine capacity
	 *		update machine finish time
	 *		set the boolean value to true
	 *		go to the next bin
	 *	if the bin id mode number of machines == i
	 *		add bin to machine i  
	 *		update machine ready time: max(previous machine finish time, bin ready time)
	 *		update machine capacity
	 *		update machine finish time
	 *		set the boolean value to true
	 *		go to the next bin
	 *end for		
	*/
