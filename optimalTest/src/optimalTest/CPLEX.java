package optimalTest;
import java.util.ArrayList;
import ilog.concert.IloException;
import ilog.concert.IloIntVar;
import ilog.concert.IloLinearNumExpr;
import ilog.concert.IloNumVar;
import ilog.concert.IloRange;
import ilog.cplex.IloCplex;


public class CPLEX {

	private static String result = null;
	static int res[] = new int[2];
	public static void MIPmodel(int N, Integer[] w, int B, int p) {
		
		try {
			IloCplex model = new IloCplex();
			//xij
			IloIntVar[][] x = new IloIntVar[N][N];
			for(int i=0;i<N;i++) {			
				for(int j=0; j<N; j++) {
						String varName="x"+(i+1)+""+(j+1)+"";
						//examples of name of variables: x11, x12, x23,...
						x[i][j] = model.intVar(0,1,varName);
					}
				}
		
			//objective function
			IloLinearNumExpr obj = model.linearNumExpr();
			for(int i=0; i<N;i++) {
				for(int j=0; j<N; j++) {
					obj.addTerm(j+1,x[i][j]);
				}
			}
			model.addMinimize(obj);		
			//Constraints
			for(int i=0; i<N;i++) {
				IloLinearNumExpr expr = model.linearNumExpr();
				for(int j=0; j<N; j++) {
					expr.addTerm(1.0, x[i][j]);
				}
				model.addEq(expr, 1.0);
			}
			
			for(int j=0; j<N; j++) {
				IloLinearNumExpr expr = model.linearNumExpr();
				for(int i=0; i<N;i++) {
					expr.addTerm(w[i], x[i][j]);
				}
				model.addLe(expr, B);
			}
			
			
			model.setOut(null);
			boolean solve = model.solve();
			if(solve) {
			res[0] =  (int) model.getObjValue();
			result += "sumCj = "+ model.getObjValue()+ "\n";
			System.out.println("CPLEX sumCj = "+ model.getObjValue());
			//result += "nb = "+ nb + "\n";
			result += "x[job][bin]= "+"\n";
				for(int i=0; i<N; i++) {
					for(int j=0;j<N;j++) {			
						if(model.getValue(x[i][j]) != 0) {
							System.out.print(x[i][j] +" = "+ model.getValue(x[i][j])+", ");
							result += x[i][j] + ", ";
							}
					}
					
				}
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
