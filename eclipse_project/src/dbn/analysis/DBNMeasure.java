package dbn.analysis;

import java.util.Vector;

import bn.analysis.BNMeasure;
import bn.analysis.JavaBayesSolver;
import bn.analysis.Observation;
import bn.analysis.distribution.Distribution;
import bn.model.BayesianNetwork;
import bn.model.Variable;
import dbn.model.DynamicBayesianNetwork;
import external.javabayes.InferenceGraphs.InferenceGraph;
import formalism.analysis.DiscreteDistribution;
import formalism.analysis.Measure;
import formalism.analysis.MultipleMeasure;
import formalism.features.Measurable;

public class DBNMeasure extends MultipleMeasure {
	
	protected Vector<TimedObservation> observation;
	
	public DBNMeasure(String name, Measurable m) {
		super(name, m);
		this.observation = new Vector<TimedObservation>();
	}
	
	public void add(int step, String variable, String value) {
		this.observation.add(new TimedObservation(variable,value,step));
	}
	
	public void add(String variable, String value) {
		this.observation.add(new TimedObservation(variable,value,-1));
	}

	public void add(TimedObservation to) {
		this.observation.add(to);
	}

	public TimedObservation get(int i) {
		return this.observation.get(i);
	}

	public int getTime(int i) {
		return this.observation.get(i).getStep();
	}

	public int size() {
		return this.observation.size();
	}

	public Vector<TimedDistribution> compute(DynamicBayesianNetwork model, int slices) throws Exception {
		Variable target = (Variable) this.getMeasurable();
		String targetName = target.getName();
		
		BayesianNetwork bn = model.flatline(slices);
		Distribution dd = new Distribution(this.getName());
		bn.add(dd);
		for (int i=0; i<slices; i++) {
			dd.addMeasurable(bn.getVariableByName(targetName + "_SFX" + i));
		}
		for (int i=0; i<this.size(); i++) {
			String name = this.get(i).getVariable();
			String value = this.get(i).getValue();
			int step= this.get(i).getStep();
			String newname = name + "_SFX" + ((step >= 0) ? step : "inf");
			System.out.println(">>> " + newname + " = " + value);
			dd.add(newname,value);
		}
  		JavaBayesSolver jbs = new JavaBayesSolver();
  		jbs.analyse(bn);
  		// TODO: back to timed
		int measures = bn.getMeasureNumber();
  		for (int i = 0; i < measures; i++) {
  			BNMeasure bnm = (BNMeasure) bn.getMeasure(i);
  			int results = bnm.size();
  	  		for (int j = 0; j < results; j++) {
  	  	  		Variable vv = (Variable) bnm.getMeasurable(j);
  				DiscreteDistribution res = (DiscreteDistribution) bnm.getResults(j);
  	  	  		Vector<String> values = vv.getValues();
  	  	  		for (String val: values) {
  	  	  			double prob = res.getProbability(val);
  	  	  	  		System.out.println("\t" + val + " " + prob);
  	  	  		}
  	  	  		System.out.println();
  	  		}
  		}

  		
  		
		return null;
	}
}