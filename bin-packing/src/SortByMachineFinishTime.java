import java.util.Comparator;

public class SortByMachineFinishTime implements Comparator<Machine>{

		@Override
		public int compare(Machine arg0, Machine arg1) {
			// TODO Auto-generated method stub
			 return arg0.machineFinishTime -  arg1.machineFinishTime ; 
	}
}

