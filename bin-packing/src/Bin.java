import java.util.ArrayList;
import java.util.List;

public class Bin{
	private int id;
	private int capacity;
	private int currentSize;
	private List<Job> JobsInBin; 
	int binReadyTime = 0;
	int binStartTime = 0; // the time bin starts processing on machine

	
	public Bin(int capacity, int id) {
		this.capacity = capacity;
		this.id = id;
		this.JobsInBin = new ArrayList<Job>();
	}
	/*	public boolean checkCapacity(Job job) {
		boolean retvalue;
		if(this.currentSize + job.getSize() > this.capacity) 
			retvalue = false;
		else
			retvalue = true;
		return retvalue;
	}
	*/
	public boolean put(Job job) {
		boolean retvalue = false;
		if(this.currentSize + job.getSize() > this.capacity) 
			retvalue = false;
		else {
			this.JobsInBin.add(job);
			this.currentSize += job.getSize();
			if(job.getReleaseDate() > this.binReadyTime)
				this.binReadyTime = job.getReleaseDate();
			
			retvalue = true;
		}
		binStartTime = binReadyTime;
		return retvalue;		
	}
	
	public boolean checkCloseRule() {
		boolean binStatus = false;
		if(this.currentSize == this.capacity)
			binStatus = true;
		else
			binStatus = false;

		return binStatus;
	}
		
	public int getBinCurrentSize() {
		return currentSize;
	}
	public int getBinReadyTime() {
		return binReadyTime;
	}
	public int getId() {
		return id;
	}
	
	@Override
	public String toString() {
		String contentOfBin = " Jobs in bin #"+ this.id+": ";
		for(Job job : this.JobsInBin) {
			contentOfBin += job.toString() + " ";
		}
		contentOfBin += "\n"+"   bin ready time = " + binReadyTime +" ";
		contentOfBin += "\n"+"   bin used capacity = " + currentSize +" ";
		return contentOfBin;
	}
}
