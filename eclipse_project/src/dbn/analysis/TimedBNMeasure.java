package dbn.analysis;

import java.util.Vector;

import bn.analysis.Observation;
import formalism.analysis.Measure;
import formalism.features.Measurable;

public class TimedBNMeasure extends Measure {
	
	protected double stoptime; 
	
	protected Vector<TimedObservation> observation;
	
	public TimedBNMeasure(String name, Measurable m, double stop) {
		super(name, m);
		this.observation = new Vector<TimedObservation>();
		this.stoptime = stop;
	}
	
	public double getStopTime() {
		return stoptime;
	}

	public void setStopTime(double stoptime) {
		this.stoptime = stoptime;
	}

	public void add(int time, String variable, String value) {
		this.observation.add(new TimedObservation(variable,value,time));
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

	public Vector<Observation> getBetween(double start, double end) {
		Vector<Observation> retval = new Vector<Observation>();
		for (int j=0; j<this.size(); j++) {
			double t = this.getTime(j);
			if ((t>=start) && (t<=end)) {
				retval.add(this.get(j));
			}
		}
		return retval;
	}
}