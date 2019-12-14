package applications.koon;

import java.util.Hashtable;
import java.util.List;

import bn.analysis.distribution.Distribution;
import bn.model.BayesianNetwork;
import bn.model.Variable;
import formalism.analysis.Measure;
import utils.Utils;

public class SimpleVoter extends BayesianNetwork {

	private int N;

	private int K;

	private List<Double> fpp;

	private List<String> names;

	private List<Double> fnp;

	private final double pTrue = 0.9;

	private Hashtable<String,String> oracleTable;

	public SimpleVoter(int k, int n, List<String> names) {
		this.name = Utils.genName(names,k);
		this.oracleTable = new Hashtable<String,String>();
		this.K = k;
		this.N = n;
		this.names = names;
		this.fpp = Board.getRates(names);
		this.fnp = Board.getRates(names);
	}

	public void add(Measure m) {
		this.measures.add(m);
		this.oracleTable.put(m.getName(),Board.T);
	}

	public void build() {
		long time = System.nanoTime();
		// Voter
		Variable voter = VariableFactory.terFactory("V",this);
		// Event
		Variable event = VariableFactory.binFactory("EVENT",this);
		VariableFactory.setEventCPT(event,this,this.pTrue);
		// Externals & Feedback
		Board.resetMapping();
		// Externals
		List<String> exts = Board.getExternals();
		Variable[] exs = new Variable[exts.size()];
		for (int i=0; i<exts.size(); i++) {
			String ext = exts.get(i);
			exs[i] = VariableFactory.binFactory(Board.pfxExt + i,this);
			VariableFactory.setExternalCPT(exs[i],this,Board.getExternalRate(ext));
			Board.addExternalMapping(ext,Board.pfxExt + i);
		}
		// Feedback
		List<String> feeds = Board.getFeedbacks();
		int feedNumber = feeds.size();
		Variable[] fss = new Variable[feedNumber];
		Variable[] dfs = new Variable[feedNumber];
		for (int i=0; i<feedNumber; i++) {
			String feed = feeds.get(i);
			Feedback ff = Board.getFeedback(feed);			
			dfs[i] = VariableFactory.binFactory(Board.pfxDfs + i,this);			
			fss[i] = VariableFactory.binFactory(Board.pfxFss + i,this);			
			VariableFactory.setSensorCPT(fss[i],this,this.pTrue);
			String applicableExtName = Board.getMapping(ff.getExternal());			
			this.add("sf" + i,Board.pfxFss + i,Board.pfxDfs + i);
			this.add("ss" + i,applicableExtName,Board.pfxDfs + i);
			VariableFactory.setUnitCPT(dfs[i],this,ff.getFpp());
			Board.addExternalMapping(feed,Board.pfxDfs + i);
		}
		// Block
		Variable[] rls = new Variable[this.N];
		Variable[] uns = new Variable[this.N];
		Variable[] ses = new Variable[this.N];
		Variable[] evs = new Variable[this.N];
		for (int i=0; i<this.N; i++) {
			String cname = this.names.get(i);
			uns[i] = VariableFactory.terFactory(Board.pfxUns + i,this);			
			ses[i] = VariableFactory.binFactory(Board.pfxSes + i,this);
			evs[i] = VariableFactory.binFactory(Board.pfxEvs + i,this);
			rls[i] = VariableFactory.binFactory(Board.pfxRls + i,this);
			this.add("sr" + i,Board.pfxSes + i,Board.pfxRls + i);
			this.add("ru" + i,Board.pfxRls + i,Board.pfxUns + i);
			this.add("ee" + i,"EVENT",Board.pfxEvs + i);
			this.add("eu" + i,Board.pfxEvs + i,Board.pfxUns + i);
			this.add("uv" + i,Board.pfxUns + i,"V");
			VariableFactory.setSensorCPT(ses[i],this,this.pTrue);
			VariableFactory.setIdentityCPT(evs[i],this);
			// Extract the list of external factors
			Dependency dep = Board.getDependency(cname);
			for (int j=0; j<dep.getFactorNumber(); j++) {
				String depName = dep.getFactor(j); 
				String extVarName = Board.getMapping(depName);
				this.add("comm" + i + "_" + j,extVarName,Board.pfxUns + i);
				// Getting the proper feedback sensor from external
				List<String> controlUnits = Board.getFeedbackVariableFromExternal(depName);
				for (String control: controlUnits) {
					this.add("fr" + i + "_" + j,control,Board.pfxRls + i);
				}
			}
			VariableFactory.setRepCPT(rls[i],this);
			VariableFactory.setUnitCPT(uns[i],this,this.fpp.get(i),this.fnp.get(i),dep);
		}
		VariableFactory.setVoterCPT(voter,this,this.K);
		this.generationTime = Utils.nano2milli(System.nanoTime() - time);
	}

	private boolean ispresent(String it, String[] in) {
		boolean retval = false;
		int i = 0;
		while ((retval == false) && (i < in.length)) {
			retval = in[i].equals(it);
			i++;
		}
		return retval;
	}

	public void setObservation(String[] patterns) {
		Variable vot = this.getVariableByName("V");
		for (int i=0; i<patterns.length; i++) {
			Distribution v = this.genDistribution(patterns[i],vot);
			this.add(v);
		}		
	}
	
	public String getOracle(String name) {
		return this.oracleTable.get(name);
	}
	
	public Distribution genDistribution(String pattern, Variable v) {
		String name = Board.getDistributionName(pattern);
  		Distribution retval = new Distribution(name,v);
  		int len = pattern.length();
  		retval.add("EVENT",String.valueOf(pattern.charAt(0)));
  		for (int i=1; i<len; i++) {
  			retval.add(Board.pfxExt + (i-1),String.valueOf(pattern.charAt(i)));
  		}
  		return retval;		
	}

	public String getK() {
		return String.valueOf(this.K);
	}

	public String getN() {
		return String.valueOf(this.N);
	}
}