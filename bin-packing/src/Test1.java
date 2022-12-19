import cern.jet.random.Uniform;
import cern.jet.random.engine.MersenneTwister;
import cern.jet.random.engine.RandomSeedGenerator;

public class Test1 {
	static int N = 3; // number of jobs
	static int M = 2; // number of machines
	static int B = 5; // batch capacity
	static int p = 15; // job processing time (seconds)
	static RandomSeedGenerator rsg;
	
/*	public void initialise() {

		//double[] w = new double[N]; // array of job sizes
		double[] w = UniformWeights();
		//double[] r = new double[N]; // array of job release times
		double[] r = UniformReleasDates();
		// nb = roundup(sum(w[j]/B)); // lower bound for number of batches
		double sum_w = 0;
		for(int j = 0 ; j < N; j++) {
			sum_w += w[j];	
		}
		int nb = (int) Math.ceil(sum_w/B);
		System.out.println(nb);

	}
*/	
 	public static int[] UniformWeights() {
		Uniform[] jobs_weight = new Uniform[N];
		int weights[] = new int[N];
		for (int i = 0; i < N; i++){
		//	weightSeed[i] = rsg.nextSeed();
			jobs_weight[i] = new Uniform(1.0, 10.0, new MersenneTwister(rsg.nextSeed()));
			weights[i] = jobs_weight[i].nextInt();
	    }
		return weights;
	}
 	public static double[] UniformReleasDates() {
		Uniform[] jobs_releaseDate = new Uniform[N];	
		double releaseDates[] = new double[N];
		for (int i = 0; i < N; i++){
			jobs_releaseDate[i] = new Uniform(0.0, 60, new MersenneTwister(rsg.nextSeed()));
			releaseDates[i] = jobs_releaseDate[i].nextDouble();
	    }
		return releaseDates;
	}
	 
				
}	

