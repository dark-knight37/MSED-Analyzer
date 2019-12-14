package formalism.random.event;


public abstract class ThresholdEventGenerator extends RandomEventGenerator {

	protected double threshold;
	
	public ThresholdEventGenerator() {
		super();
		this.threshold = 0.5;
	}

	public ThresholdEventGenerator(double threshold) {
		double temp = threshold;
		temp = Math.min(1.0,temp);
		temp = Math.max(0.0,temp);
		this.threshold = temp;
	}

	public double getThreshold() {
		return threshold;
	}

	public void setThreshold(double threshold) {
		this.threshold = threshold;
		this.reset();
	}
}