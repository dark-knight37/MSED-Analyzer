package dbn.analysis;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

import formalism.analysis.DiscreteDistribution;
import formalism.analysis.Results;

public class TimedDistribution implements Results {

	protected Vector<DiscreteDistribution> timedistro;
	
	public TimedDistribution() {
		this.timedistro = new Vector<DiscreteDistribution>();
	}
	
	public void add(int step, DiscreteDistribution dd) {
		this.timedistro.add(step,dd);
	}
	
	public DiscreteDistribution get(int step) {
		return this.timedistro.get(step);
	}

	public int getEntryNumber() {
		return this.timedistro.size();
	}
			
	public double getProbability(int s, String value) {
		DiscreteDistribution dd = this.timedistro.get(s);
		double retval = dd.getProbability(value);
		return retval;
	}
	
	public String debug() {
		String retval = "";
		return retval;
	}
}
