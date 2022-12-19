package optimalTest;

import java.util.Arrays;
import java.util.Collections;

public class FFD {
	private int N;
	private Integer[] w;
	private int[] r;
	private int B;
	private int p;
	
	public  FFD(int N, Integer[] w, int B, int p) {
		this.N = N;
		this.w = w;
		this.p = p;
		this.B = B;
	}
	public int[] FFDAlg() {
		int sumCj = 0;
		int numbins = 0;
		int res[] = new int[2];
		int[] bin_for_item = new int[N];
		int[] bin_space = new int[N];
		
		Integer w1[]= new Integer[w.length];		
		for(int i=0; i<N; i++) {
			w1[i]=w[i]; 
			bin_for_item[i] = -1;
			bin_space[i]= B;
		}
		Arrays.sort(w1, Collections.reverseOrder());
		
		for(int i=0; i<N; i++) {
			for( int j=0 ; j < bin_space.length; j++) {
				//System.out.println(w[i] + " "+ w1[i]);
				if(w1[i] <= bin_space[j]) {
					bin_for_item[i] = j;
					bin_space[j] -= w1[i];
				}
				if(bin_for_item[i] != -1) {
					//System.out.println("bin space: "+ bin_space[j]);	
					break;					
				}	
			}
			//System.out.println("item size: "+ w1[i]);
			//System.out.println("bin for item: "+ bin_for_item[i]);
			
		}
		for(int i=0; i<N; i++) {
			if(numbins < bin_for_item[i])
				numbins = bin_for_item[i];
		}
		
		for(int i=0; i<N; i++) {
			sumCj += (bin_for_item[i]+1)*p;
			//System.out.println("sumCj: "+ sumCj);
		}
		System.out.println("**FFD sumCj: "+ sumCj + "  #Bins: "+ numbins);
		res[0]= sumCj;
		res[1]= numbins;
		
		return res;
		
	}

}



