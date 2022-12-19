import java.util.ArrayList;
import java.util.List;

public class Machine {
	private int id;
	private int capacity;
	int currentSize;
	private List<Bin> binsInMachine; 
	int machineReadyTime = 0;
	int machineFinishTime = 0;
	int machineStartTime = 0;
	boolean status = true;
	int processTime;
	

	public Machine(int id, int capacity, int processTime) {
		this.capacity = capacity;
		this.id = id;
		this.binsInMachine = new ArrayList<Bin>();
		this.processTime = processTime;
	}	
	public int getId() { return id;	}
	
	
// add the bin to the machine just based on finish time, without capacity checking
	public void addBinToMachine(Bin bin) {
		binsInMachine.add(bin);
		currentSize += bin.getBinCurrentSize();
		if(bin.binReadyTime > machineFinishTime) 
			machineFinishTime = bin.binReadyTime + processTime;
		
		else
			machineFinishTime = machineFinishTime + processTime ;
	}
	public int getReadyTime() {
		for( Bin bin : binsInMachine) {
			if(bin.binReadyTime > machineFinishTime) {
				if(bin.binReadyTime > machineReadyTime)
					machineReadyTime = bin.binReadyTime;
			}
			else
				machineReadyTime = machineFinishTime;
		}
		
		return machineReadyTime;
	}
	/*	public int getStartTime() {
		for(Bin bin : binsInMachine) {
			if(bin.binReadyTime > machineFinishTime) {
				if(bin.binReadyTime > machineReadyTime)
					machineReadyTime = bin.binReadyTime;
			}
			else
				machineReadyTime = machineFinishTime;
		}
		return machineReadyTime;
	}
	*/
	
	@Override
	public String toString() {
		
		String contentOfMachine = "* Bins processed on Machine #"+ this.id+": ";
		for(Bin bin : this.binsInMachine) {
			contentOfMachine += bin.getId() + " ";
		}
		for(Bin bin : this.binsInMachine) {
			contentOfMachine += "\n" + ""+ bin.toString();
		}
		contentOfMachine += "\n"+"machine ready time = " + machineReadyTime +" ";
		contentOfMachine += "\n"+"machine finish time = " + machineFinishTime +" \n";
		return contentOfMachine;
	}	

			
	
	
}
