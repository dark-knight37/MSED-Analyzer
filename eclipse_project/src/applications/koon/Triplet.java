package applications.koon;

import java.util.Hashtable;

public class Triplet {

	private Hashtable<String,Double> response;
	
	public Triplet() {
		this.response = new Hashtable<String,Double>();
	}
	
	public void set(String kind, double value) {
		this.response.put(kind,value);
	}
	
	public double get(String kind) {
		return this.response.get(kind);
	}
}
