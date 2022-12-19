import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HeuristicBF {

	private List<Bin> bins;
	private List<Bin> openBins;
	private List<Bin> SortedBins; // all bins sorted in increasing order of ready time
	private List<Job> jobsList;
	List<Machine> machineList;	
	private int binCapacity;
	private int machineCapacity;
	private int binCounter = 1;
	private int Cmax = 0;
	
	// Constructor
	public HeuristicBF(List<Machine> machineList ,List<Job> jobsList, int machineCapacity, int binCapacity) {
		this.binCapacity = binCapacity;	
		this.jobsList = jobsList;
		this.machineList = machineList;
		this.machineCapacity = machineCapacity;
		this.bins = new ArrayList<>();		
		this.openBins = new ArrayList<>();	
	}
	// BF method
	public void solveBF() {

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

/* for each job
	 * boolean value, turns true when the job fits a batch and put(). 
	 * for open batch
	 	 * 	if the batch is open
			 *  if job fits in the batch: 	+
			 * 		put the job in the batch +
			 * 		update batch ready time, +
			 * 		value is true
			 * 		if batch is full: 
			 * 			close the batch, 
			 * 			schedule on machine
			 * 		else 
			 * 			go to the next job
			 *	else
			 * 		go to the next batch
		 * if it does not fit in any of the open batches, the returned value is false:	
			 * 		open a new batch, 
			 * 		add it to the open batch list
			 * 		put the job in the batch
			 * 		update batch ready time,
			 * 		if batch is full: 
			 * 			close the batch, 	
			 * 			schedule on machine
			 * 		else
			 * 			go to the next job
*/
		this.openBins.add(new Bin (binCapacity, binCounter));  	// create the first bin	
		for(Job currentJob : jobsList) {
			Collections.sort(bins, new SortBinsByCapacity());		
			//System.out.println("id "+currentJob.getId());
			boolean retVal = false;
			for (Bin currentBin : openBins) {				
				retVal = currentBin.put(currentJob); // true if job fits the bin
				if(retVal) {
					if(currentBin.checkCloseRule()) {
						scheduleBinOnMachine(currentBin);
						openBins.remove(currentBin);
					}
					break;
				}						
			}
			if(!retVal) {
				Bin newBin = new Bin(binCapacity, ++binCounter);
				openBins.add(newBin);
				retVal = newBin.put(currentJob); // true if job fits the bin
				if(newBin.checkCloseRule()) { // status is false -> bin is closed
					scheduleBinOnMachine(newBin);
					openBins.remove(newBin);
				}
			}
		// schedule all bins on machines after the last job has been scheduled, with out checking the close rule
			if(currentJob.getId() == jobsList.size()) {
				System.out.println("id "+currentJob.getId()+" size " +jobsList.size());
				if(openBins.size() > 0) {
					for(Bin bin: openBins) {					
						scheduleBinOnMachine(bin);
					}					
				}
			}
		}		
	}
	
	public void scheduleBinOnMachine(Bin bin) {		
		bins = openBins;
		Collections.sort(machineList, new SortByMachineFinishTime()); 
		machineList.get(0).addBinToMachine(bin); 
		Collections.sort(machineList, new SortByMachineFinishTime());
		Cmax = machineList.get(machineList.size()-1).machineFinishTime;
	}	
		
	public String showResults() {
		String result = null;
		result += "Best Fit Algorithm/On-line" + "\n"; 
		for (Machine machine : this.machineList) {
			result += "\n" + machine.toString();
		}
		result += "\n" + "Cmax = "+ Cmax;
		return result;
	}
	
	/*		// create the first bin
	this.bins.add(new Bin (binCapacity, binCounter)); 	
	for(Job currentJob : jobsList ) {			

		// fits, if the batch is full-> close it and add to machine, go to the next job. update the batch ready time.
		boolean isBinTrue = false; // track whether we have put the item into a bin or not
		int currentBin = 0;
		
		while(!isBinTrue) {
			if(currentBin == bins.size()) { // item does not fit in last bin, create a new bin
				Bin newBin = new Bin(binCapacity, ++binCounter);
				newBin.put(currentJob);
				bins.add(newBin);
				isBinTrue = true;
			} else if(bins.get(currentBin).put(currentJob)) { //current item fits in the given bin
				isBinTrue = true;
			} else {
				currentBin++; //trying the next bin
			}
		}					
	}
	
	for(Machine machine : machineList) {
		for(Bin bin : bins) {
			if(machine.addBinToMachine(bin))
				machine.machineFinishTime = machine.getReadyTime() + processTime;				
		}
	}
	
}
*/
}
