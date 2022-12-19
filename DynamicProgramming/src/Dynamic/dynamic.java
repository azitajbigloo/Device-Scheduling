package Dynamic;

public class dynamic {

	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[] a = {3,3,3};
		int count =0;
		for(int i1=0; i1<=a[0] ; i1++) {
			for(int i2=0; i2<=a[1] ; i2++) {
				for(int i3=0; i3<=a[2] ; i3++) {
				//	for(int i4=0; i4<=a[3] ; i4++) {
						for(int x1=0; x1<=i1 ; x1++) {
							for(int x2=0; x2<=i2 ; x2++) {
								for(int x3=0; x3<=i3 ; x3++) {
							//		for(int x4=0; x4<=i4 ; x4++) {
										count++;
										//System.out.println(i1+"  "+ 
										//i2+"  "
										//+ x1+"  "
										//+x2
										//);
								//	}
								}
							}
					//	}
					}
				}
			}
			
		}
		System.out.println(count);
		int count2 =1;
		for (int i=0; i<a.length; i++) {
			count2 = count2* (((a[i]+1)*(a[i]+2))/2);
		
	}
		System.out.println(count2);
}
}
