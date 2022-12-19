package CPLEXMODEL;
import java.util.ArrayList;

import ilog.concert.IloException;
import ilog.concert.IloIntVar;
import ilog.concert.IloLinearNumExpr;
import ilog.concert.IloNumVar;
import ilog.concert.IloRange;
import ilog.cplex.IloCplex;

public class CplexModel {
	private static String result = null;
	public static void MIPmodel(int N, int M, int B, int p, int w[], int r[], int nb) {
		
		try {
			IloCplex model = new IloCplex();
			// Version 1:
/*		//decision variables
			// Xjkm = 1; if job j is in batch k on machine m, 0; otherwise
			IloNumVar[][][] x = new IloNumVar[N][N][];
        	for (int j=0; j < N; j++) {
        		for(int k; k < N; k++ ){
        			//for(int m; m < M; m++) {
        				x[j][k] = cplex.numVarArray(M, 0, 1, IloNumVarType.Int);
        			//}
        		}
        	}
        	//Bkm = 1; if batch k is created on machine m, 0; otherwise
        	IloNumVar[][] b = new IloNumVar[N][];
    		for(int k; k < N; k++ ){
    				b[k] = cplex.numVarArray(M, 0, 1, IloNumVarType.Int);
    		}
    		// Skm = ready time of batch k on machine m
    		IloNumVar[][] S = new IloNumVar[N][];        
    		for(int k; k < N; k++ ){
    				S[k] = cplex.numVarArray(M, 0, Double.MAX_VALUE);
    		}
        	// Cmax = makespan
    		IloNumVar Cmax = cplex.numVar(0, Double.MAX_VALUE);
 */   		
    		// Version 2:
			//decision variables
			// Xjkm = 1; if job j is in batch k on machine m, 0; otherwise
			IloIntVar[][][] x = new IloIntVar[N][N][M];
			for(int j=0;j<N;j++) {			
				for(int k=0; k<N; k++) {
					for(int m=0; m<M; m++) {
						String varName="x"+(j+1)+""+(k+1)+""+(m+1);
						//examples of name of variables: x111, x112, x113,...
						x[j][k][m] = model.intVar(0,1,varName);
					}
				}
			}
			//Bkm = 1; if batch k is created on machine m, 0; otherwise
			IloIntVar[][] b = new IloIntVar[N][M];		
			for(int k=0; k<N; k++) {
				for(int m=0; m<M; m++) {
					String varName="b"+(k+1)+""+(m+1);
					//examples of name of variables: b11, b12, b13,...
					b[k][m] = model.intVar(0,1,varName);
				}
			}
			// Skm = ready time of batch k on machine m
			IloNumVar[][] S = new IloNumVar[N][M];     
			for(int k=0; k < N; k++ ){
				for(int m=0; m<M; m++) {
					String varName="S"+(k+1)+""+(m+1);
					//examples of name of variables: S11, S12, S13,...
    				S[k][m] = model.numVar(0, Double.MAX_VALUE, varName);
				}
			}
			// Cmax = makespan
			IloNumVar Cmax = model.numVar(0, Double.MAX_VALUE);
		
			//objective function
			IloLinearNumExpr obj = model.linearNumExpr();
			obj.addTerm(1.0, Cmax);
			model.addMinimize(obj);
			
		//Constraints
			//Constraint 1:
		//	ArrayList<IloRange> jobs = new ArrayList<IloRange>();
			ArrayList<IloRange> constraints = new ArrayList<IloRange>();
			for(int j = 0; j < N; j++) {
				IloLinearNumExpr constraint1 = model.linearNumExpr();
				for(int k = 0; k < N; k++) {
					for(int m = 0; m < M; m++) {
						constraint1.addTerm(1.0, x[j][k][m]);
					}
				}
				constraints.add(model.addEq(constraint1, 1.0));
			}
			//Constraint 2:				
			for(int k = 0; k < N; k++) {
				for(int m = 0; m < M; m++) {
					IloLinearNumExpr constraint2 = model.linearNumExpr();
					for(int j = 0; j < N; j++) {
						constraint2.addTerm(w[j], x[j][k][m]);
					}
					//model.addLe(constraint2,model.prod(B, b[k][m]));
					constraints.add((IloRange) model.addLe(constraint2,model.prod(B, b[k][m])));	
				}				
			}
			//Constrain 3:
			for(int k = 0; k < N; k++) {
				IloLinearNumExpr constraint3 = model.linearNumExpr();
				for(int m = 0; m < M; m++) {
					constraint3.addTerm(1.0, b[k][m]);
				}
				constraints.add(model.addLe(constraint3, 1.0));
			}
			//Constraint 4:
			for(int j = 0; j < N; j++) {				
				for(int k = 0; k < N; k++) {
					for(int m = 0; m < M; m++) {
						IloLinearNumExpr constraint4 = model.linearNumExpr();
						constraint4.addTerm(1.0, S[k][m]);
						// model.addGe(constraint4, model.prod(x[j][k][m], r[j]));
						constraints.add((IloRange) model.addGe(constraint4, model.prod(x[j][k][m], r[j])));
					}
				}			
			}
			//Constraint 5:						
			for(int k = 1; k < N; k++) {
				for(int m = 0; m < M; m++) {
					IloLinearNumExpr constraint5 = model.linearNumExpr();
					constraint5.addTerm(1.0, S[k][m]);
					// model.addGe(constraint5, model.sum(S[k-1][m], model.prod(p,b[k-1][m]));
					constraints.add((IloRange) model.addGe(constraint5, model.sum(S[k-1][m], model.prod(p,b[k-1][m]))));
				}
			}
			//Constraint 6:									
			for(int m = 0; m < M; m++) {
				IloLinearNumExpr constraint6 = model.linearNumExpr();
				constraint6.addTerm(1.0, Cmax);
				// model.addGe(constraint6, model.sum(S[N][m], model.prod(p,b[N][m]));
				constraints.add((IloRange) model.addGe(constraint6, model.sum(S[N-1][m], model.prod(p,b[N-1][m]))));
			}
			//Constraint 7:						
			for(int k = 0; k < nb; k++) {				
				IloLinearNumExpr constraint7 = model.linearNumExpr();
				constraint7.addTerm(1.0, b[k][(k % M)]);
				// model.addGe(constraint7, 1.0);
				constraints.add(model.addGe(constraint7, 1.0));
			}
			//Constraint 8:
			for(int k = nb; k < N; k++) {
				for(int m = 0; m < M; m++) {
					if(m != (k % M)+1) {
						IloLinearNumExpr constraint8 = model.linearNumExpr();
						constraint8.addTerm(1.0, b[k][m]);
						// model.addGe(constraint8, 0.0);
						constraints.add(model.addEq(constraint8, 0.0));
					}
				}
			}
			boolean solve = model.solve();
			if(solve) {
			
			result += "Cmax = "+ model.getObjValue()+ "\n";
			System.out.println("Cmax = "+ model.getObjValue());
			result += "nb = "+ nb + "\n";
			result += "x[job][bin][machine] = "+"\n";
			for(int m=0; m<M; m++) {
				for(int k=0; k<N; k++) {
					for(int j=0;j<N;j++) {			
						if(model.getValue(x[j][k][m]) != 0) {
							System.out.print(x[j][k][m] +" = "+ model.getValue(x[j][k][m])+", ");
							result += x[j][k][m] + ", ";
							}
					}
					
				}
			}
			System.out.println();
			result += "\n";
			result += "b[bin][machine]"+"\n";
			double bins = 0;
			for(int k=0; k<N; k++) {
				for(int m=0; m<M; m++) {
					if(model.getValue(b[k][m]) != 0)
						{System.out.print(b[k][m]+ ", ");
						result += b[k][m]+ ", ";
						bins++;}
				}
			}
			System.out.println();
			result += "\n";
			System.out.println("number of bins = " + bins);
			result += "number of bins = " + bins;
			}
			else {
				System.out.println("no solution found");
				result += "no solution found";
			}
			model.close();
			//System.out.println(constraints);
			
		}catch (IloException exc) {
			exc.printStackTrace();
		}
	}
	
	public String toString() {
		return result;
	}
}
