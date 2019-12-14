package applications.koon;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import utils.Utils;

public class Dependency {
	private double rate;
	
	private Hashtable<String,Double> dependencies;
		
	public Dependency(String row) {
		this.dependencies = new Hashtable<String,Double>();
		StringTokenizer tokenizer = new StringTokenizer(row,",");
		this.rate = Double.valueOf(tokenizer.nextToken());
		while (tokenizer.hasMoreTokens()) {
			String temp = tokenizer.nextToken();
			int index1 = temp.indexOf('(');
			int index2 = temp.indexOf(')');
			String name = temp.substring(0,index1);
			String extracted = temp.substring(index1+1,index2);			
			double factor = (extracted.equals("-")) ? -1: Double.valueOf(extracted);
			this.dependencies.put(name,factor);
		}
	}
	
	public double getRate(List<String> applicableFactor) {
		double multiplier = this.rate;
		if (applicableFactor != null) {
			int i = 0;
			int size = applicableFactor.size();
			while ((i<size) && (multiplier >= 0)) {
				String key = applicableFactor.get(i);
				double temp = this.dependencies.getOrDefault(key,(double) 1);
				multiplier *= temp;
				i++;
			}
		}
		return (multiplier >= 0) ? multiplier : 1;
	}	

	public String getFactor(int i) {
		return this.getFactors().get(i);
	}	

	private List<String> getFactors() {
		List<String> retval = new ArrayList<String>();
		Enumeration<String> keys = this.dependencies.keys();
		while (keys.hasMoreElements()) {
			retval.add(keys.nextElement());
		}
		return retval;
	}	

	public int getFactorNumber() {
		return this.getFactors().size();
	}	
}