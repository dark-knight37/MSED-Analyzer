package dbn.analysis;

import java.util.Vector;

import bn.analysis.JavaBayesSolver;
import dbn.model.DynamicBayesianNetwork;
import formalism.features.Analyzable;

public class DBNSolver extends JavaBayesSolver {
	
	private int slices;
	
	public DBNSolver(int s) {
		this.slices = s;
	}
	
	@Override
	public void analyse(Analyzable a) throws Exception {
		DynamicBayesianNetwork model = (DynamicBayesianNetwork) a;
//		System.out.println(fill);
		int measures = model.getMeasureNumber();
  		for (int i = 0; i < measures; i++) {
  			DBNMeasure query = (DBNMeasure) model.getMeasure(i);
  	  		Vector<TimedDistribution> d = query.compute(model,this.slices);
	  		for (TimedDistribution t: d) {
	  			query.addResults(t);
	  		}
  		}
	}
}