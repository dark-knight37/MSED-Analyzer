package bn.model.table;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Set;
import java.util.Vector;

import bn.model.Variable;

public class Row implements Cloneable {

	private Hashtable<String,Double> values;
	
	public Row(Variable v) {
		this.values = new Hashtable<String, Double>();
		@SuppressWarnings("unchecked")
		Vector<String> temp = (Vector<String>) v.getValues().clone();
		for (String s: temp) {
			this.values.put(s,0.0);
		}
	}
	
	public Row(Row r) {
		this.values = new Hashtable<String, Double>();
		for (String s: r.getLabels()) {
			double val = r.getProbability(s);
			this.values.put(s,val);
		}
	}

	public double getProbability(String value) {
		return this.values.get(value);
	}
	
	public void setProbability(String value, double p) {
		this.values.put(value,p);
	}

	public Vector<String> getLabels() {
		Vector<String> retval = new Vector<String>();
		Enumeration<String> kk = this.values.keys();
		for (int i=0; i<this.values.size(); i++) {
			String e = kk.nextElement();
			retval.add(e);
		}
		return retval;
	}

	public boolean check() {
		double sum = 0;
		for (double d: this.values.values()) {
			sum += d;
		}
		return ((sum >= 0.99) && (sum <= 1.01));
	}
	
	public double[] getArray() {
		int size = this.values.size();
		double[] retval = new double[size];
		int i = 0;
		for (Object o: this.values.values()) {
			retval[i] = (double) o;
			i++;
		}
		return retval; 
	}
	
	public Object clone() {
		Row retval = new Row(this);
		return retval;
	}
}