
public class Job {
	 private int id;
	 private int releaseDate;
	 private int jobSize;
	 
	public Job(int id, int releaseDate, int jobSize) {
		this.releaseDate = releaseDate;
		this.jobSize = jobSize;
		this.id = id;
	}	
	public int getReleaseDate() { return releaseDate; }
	public int getSize() { return jobSize; }
	public int getId() { return id; }

	public String toString() {
		String jobId = this.id+" ";
		return jobId;		
	}
}
