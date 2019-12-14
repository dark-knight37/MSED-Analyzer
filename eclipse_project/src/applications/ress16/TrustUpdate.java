package applications.ress16;

import java.util.Vector;

import applications.koon.Board;
import applications.koon.VariableFactory;
import bn.analysis.Observation;
import bn.model.Variable;
import bn.model.table.Entry;
import bn.model.table.EntryPoint;
import dbn.analysis.TimedBNMeasure;
import dbn.analysis.TimedDiscreteDistribution;
import tvbn.TimeVaryingDynamicBayesianNetwork;

public class TrustUpdate extends TimeVaryingDynamicBayesianNetwork {
	
	private int N;
	private int K;
	private double[] fpp;
	private double[] fnp;
	private final double pTrue = 0.9;
	private double[] reputation;
	
	public TrustUpdate(String name, int k, int n, double[] fppVal, double[] fnpVal) {
		super(name);
		this.K = k;
		this.N = n;
		this.fpp = fppVal;
		this.fnp = fnpVal;
		this.reputation = new double[n];
		for (int i=0; i<n; i++) {
			this.reputation[i] = 1 - (this.fpp[i] + this.fnp[i]) / 2;
		}
	}

	public TrustUpdate(String name, int k, int n, double[] frs) {
		this(name,k,n,frs,frs);
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
			VariableFactory.setUnitCPT(uns[i],this,this.fpp[i]);
		}
		VariableFactory.setVoterCPT(voter,this,this.K);
	}

	@Override
	public void updateMode(double begin, double t, double end) {
		TimedBNMeasure tbm = (TimedBNMeasure) this.getMeasure("voter");
		TimedDiscreteDistribution tdd = (TimedDiscreteDistribution) tbm.getResults();
		String voted = this.getVoted(tdd,t); 
		if (!voted.equals(Board.U)) {
			Vector<Observation> obs = tbm.getBetween(begin,end);
			for (int i=0; i<3; i++) {
				String singlevote = this.getObsByName(obs,"EV" + i).getValue();
				Variable r = this.getVariableByName("REL" + i);
				Entry e = new Entry();
				e.add(new EntryPoint("DET" + i,Board.T));
				double trust = r.getDistribution().getProbability(Board.T, e);
				if (singlevote.equals(voted)) {
					trust = this.incrTrust(trust);
				} else {
					trust = this.decrTrust(trust);
				}
				VariableFactory.setRelCPT(r,this,trust);
			}
		}
	}
	
	private double incrTrust(double trust) {
		double retval = trust;
		if (retval < 1) {
			if (retval == 0) {
				retval = 0.7;
			}
			retval = retval + (1-retval) * 0.1;
		}
		return retval;
	}

	private double decrTrust(double trust) {
		double retval = trust;
		if (retval > 0) {
			if (retval == 1) {
				retval = 0.965;
			}
			retval = retval * 0.9;
		}
		return retval;
	}

	private Observation getObsByName(Vector<Observation> obs, String string) {
		Observation oo = null;
		boolean found  = false;
		int index = 0;
		while ((!found) && (index < obs.size())) {
			found = obs.get(index).getVariable().equals(string);
			if (found) {
				oo = obs.get(index);
			} else {
				index++;
			}
		}
		return oo;
	}

	private String getVoted(TimedDiscreteDistribution distro, double t) {
		double pF = distro.getProbability(t,Board.F);
		double pT = distro.getProbability(t,Board.T);
		double pU = distro.getProbability(t,Board.U);
		String voted;
		if (pF > Math.max(pT,pU)) {
			voted = Board.F;
		} else if (pT > Math.max(pF,pU)) {
			voted = Board.T;
		} else {
			voted = Board.U;
		}
		return voted;
	}
}