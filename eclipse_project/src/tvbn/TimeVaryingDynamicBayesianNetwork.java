package tvbn;

import bn.model.BayesianNetwork;

public abstract class TimeVaryingDynamicBayesianNetwork extends BayesianNetwork {
	
	public TimeVaryingDynamicBayesianNetwork(String name) {
		super(name);
	}
	
	public abstract void updateMode(double begin, double t, double end);
}

