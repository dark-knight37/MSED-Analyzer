package formalism.analysis;

public class Scalar implements Results {
	
	protected double value;
	
	public Scalar() {
		this.value = 0.0;
	}
	
	public Scalar(double val) {
		this.value = val;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}
}