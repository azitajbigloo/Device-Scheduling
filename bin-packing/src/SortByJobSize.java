import java.util.Comparator;

public class SortByJobSize implements Comparator<Job>{


	@Override
	public int compare(Job arg0, Job arg1) {
		// TODO Auto-generated method stub
		 return arg1.getSize() - arg0.getSize(); 
	}
}
