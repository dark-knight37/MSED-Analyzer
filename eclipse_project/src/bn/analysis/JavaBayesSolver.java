package bn.analysis;

import java.util.Vector;

import bn.model.BayesianNetwork;
import external.javabayes.InferenceGraphs.InferenceGraph;
import formalism.analysis.DiscreteDistribution;
import formalism.analysis.Solver;
import formalism.features.Analyzable;

public class JavaBayesSolver extends Solver {

	public JavaBayesSolver(String workingDir, String toolName) {
		super(workingDir,toolName);
	}

	public JavaBayesSolver() {
		super(null,null);
	}

	@Override
	public void analyse(Analyzable a) throws Exception {
		BayesianNetwork bn = (BayesianNetwork) a;
		String fill = bn.toSpecificFormat(null);
//		System.out.println(fill);
		InferenceGraph model = ModelFactory.create(fill);
		int measureSize = bn.getMeasureNumber();
		for (int i = 0; i < measureSize; i++) {
			BNMeasure query = (BNMeasure) bn.getMeasure(i);
			Vector<DiscreteDistribution> d = query.compute(model);
			for (DiscreteDistribution t: d) {
				query.addResults(t);
			}
		}
	}
}