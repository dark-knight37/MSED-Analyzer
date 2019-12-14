package applications.ress16;

import applications.koon.VariableFactory;
import bn.model.BayesianNetwork;
import bn.model.Variable;

public class SteadyState extends BayesianNetwork {

	private int N;
	private int K;
	private double[] fpp;
	private double[] fnp;
	private final double pTrue = 0.9;
	
	public SteadyState(String name, int k, int n, double[] fppVal, double[] fnpVal) {
		super(name);
		this.K = k;
		this.N = n;
		this.fpp = fppVal;
		this.fnp = fnpVal;
	}
	
	public SteadyState(String name, int k, int n, double[] fVal) {
		super(name);
		this.K = k;
		this.N = n;
		this.fpp = fVal;
		this.fnp = fVal;
	}

	public void set() {
		Variable voter = VariableFactory.terFactory("V",this);
		Variable[] uns = new Variable[this.N];
		Variable[] ses = new Variable[this.N];
		Variable[] evs = new Variable[this.N];
		Variable[] rel = new Variable[this.N];
		for (int i=0; i<this.N; i++) {
			rel[i] = VariableFactory.terFactory("REL" + i,this);			
			uns[i] = VariableFactory.terFactory("DET" + i,this);			
			ses[i] = VariableFactory.binFactory("SEN" + i,this);
			evs[i] = VariableFactory.binFactory("EV" + i,this);
			this.add("su" + i,"SEN" + i,"DET" + i);
			this.add("eu" + i,"EV" + i,"DET" + i);
			this.add("ur" + i,"DET" + i,"REL" + i);
			this.add("rv" + i,"REL" + i,"V");
			VariableFactory.setSensorCPT(ses[i],this,this.pTrue);
			VariableFactory.setEventCPT(evs[i],this,this.pTrue);
			VariableFactory.setRelCPT(rel[i],this,1-(this.fpp[i] + this.fnp[i])/2);
			VariableFactory.setUnitCPT(uns[i],this,this.fpp[i],this.fnp[i],null);
		}
		VariableFactory.setVoterCPT(voter,this,this.K);
	}
}
