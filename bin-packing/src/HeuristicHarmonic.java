import java.util.ArrayList;
import java.util.List;

public class HeuristicHarmonic {
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
	public HeuristicHarmonic(List<Machine> machineList ,List<Job> jobsList, int machineCapacity, int binCapacity) {
		this.binCapacity = binCapacity;	
		this.jobsList = jobsList;
		this.machineList = machineList;
		this.machineCapacity = machineCapacity;
		this.bins = new ArrayList<>();		
		this.openBins = new ArrayList<>();	
	}
	public void solveHarmonic() {
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
		
	}
}
