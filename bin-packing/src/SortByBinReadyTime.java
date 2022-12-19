import java.util.Comparator;

public class SortByBinReadyTime implements Comparator<Bin>{


	@Override
	public int compare(Bin arg0, Bin arg1) {
		// TODO Auto-generated method stub
		 return arg0.binReadyTime - arg1.binReadyTime; 
	}
}
