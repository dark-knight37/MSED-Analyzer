package dbn.analysis;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

import formalism.analysis.DiscreteDistribution;
import formalism.analysis.Results;

public class TimedDiscreteDistribution implements Results {

	protected Hashtable<String,DiscreteDistribution> timedistro;
	
	public TimedDiscreteDistribution() {
		this.timedistro = new Hashtable<String,DiscreteDistribution>();
	}
	
	public void add(double time, DiscreteDistribution dd) {
		Double d = new Double(time);
		this.timedistro.put(d.toString(),dd);
	}
	
	public int getEntryNumber() {
		return this.timedistro.size();
	}
	
	public double[] getTimes() {
		Vector<String> temp = new Vector<String>();

		for (Enumeration<String> x = this.timedistro.keys(); x.hasMoreElements();) {
			temp.add(x.nextElement());
		}

		temp.sort(null);
		double retval[] = new double[this.getEntryNumber()];
		for (int i=0; i<this.getEntryNumber(); i++) {
			Double dd = new Double(temp.get(i));
			retval[i] = dd.doubleValue();
		}
		return retval;
	}
		
	public double getProbability(double t, String value) {
		Double d = new Double(t);
		String sd = d.toString();
		DiscreteDistribution dd = this.timedistro.get(sd);
		double retval = dd.getProbability(value);
		return retval;
	}
	
	
	public String debug() {
		String retval = "";
		if (this.getEntryNumber() > 0) {
			double[] times = this.getTimes();
			DiscreteDistribution dd = this.timedistro.elements().nextElement();
			int size = dd.size();
			String[] support = new String[size];
			for (int i=0; i<size; i++) {
				support[i] = dd.getKey(i);
			}
			for (int i=0; i<times.length; i++) {
				double temptime = times[i];
				retval += "Sample_" + temptime + " --> ";
				for (int j=0; j<support.length; j++) {
					retval += support[j] + " = " + this.getProbability(temptime,support[j]) + "\t";
				}
				retval += "\n";
			}
		}
		return retval;
	}
}
