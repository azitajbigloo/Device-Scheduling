import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
public class HeuristicBFD {

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
	public HeuristicBFD(List<Machine> machineList ,List<Job> jobsList, int machineCapacity, int binCapacity) {
		this.binCapacity = binCapacity;
		this.jobsList = (ArrayList<Job>) jobsList;
		this.machineList = machineList;
		this.machineCapacity = machineCapacity;
		this.bins = new ArrayList<Bin>();		
	}
	// FFD algorithm
	public void solveBFD() {

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
		sortedJobs = jobsList; // this does not work, both list get sorted
		// sort jobs based on non-increasing oreder of size
		Collections.sort(sortedJobs, new SortByJobSize()); 		
		this.bins.add(new Bin (binCapacity, binCounter));  	// create the first bin
		for(Job currentJob : jobsList) { 
			// sort bins by increasing order of remaining capacity/ non-increasing full capacity
			Collections.sort(bins, new SortBinsByCapacity());		
			boolean retVal = false;
			for (Bin currentBin : bins) {
				retVal = currentBin.put(currentJob); // true if job fits the bin
				if(retVal) 					
					break;								
			}
			if(!retVal) {
				Bin newBin = new Bin(binCapacity, ++binCounter);
				bins.add(newBin);
				//System.out.println("new bin added");
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
				if((sortedBins.indexOf(bin)+1) % machineList.size() == currentMachine.getId()) {	
					i = currentMachine.getId();					
				}
				if((sortedBins.indexOf(bin)+1) % machineList.size() == 0) {					
					if (currentMachine.getId() == machineList.size()) {						
						i = currentMachine.getId(); // this should be last machine in the list								
					}
				}
				if(i == currentMachine.getId()) {
					//System.out.println(i);
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
		result += "Best Fit Decreasing Algorithm/Off-line"+ "\n";  
		for (Machine machine : this.machineList) {
			result += "\n" + machine.toString();
		}
		result += "\n" + "Cmax = "+ Cmax;
		return result;
	}
		
}

