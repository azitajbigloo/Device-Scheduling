	import java.util.Comparator;
public class SortBinsByCapacity implements Comparator<Bin>{

	@Override
	public int compare(Bin arg0, Bin arg1) {
		// TODO Auto-generated method stub
		 return arg1.getBinCurrentSize() - arg0.getBinCurrentSize(); 
	}
}

