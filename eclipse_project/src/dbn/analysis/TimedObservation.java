package dbn.analysis;

import bn.analysis.Observation;

public class TimedObservation extends Observation {
	
	protected int step;
	
	public TimedObservation(String variable, String value, int st) {
		super(variable,value);
		this.step = st;
	}

	public int getStep() {
		return this.step;
	}

	public void setStep(int st) {
		this.step = st;
	}
}