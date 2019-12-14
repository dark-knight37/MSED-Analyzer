package applications.koon;

public class Feedback {
	private String external;
	
	private double errorRate;
	
	public Feedback(String ext, double err) {
		this.external = ext;
		this.errorRate = err;
	}

	public String getExternal() {
		return external;
	}

	public void setExternal(String external) {
		this.external = external;
	}

	public double getErrorRate() {
		return errorRate;
	}

	public void setErrorRate(double errorRate) {
		this.errorRate = errorRate;
	}

	public double getFpp() {
		return this.errorRate;
	}

	public double getFnp() {
		return this.errorRate;
	}
}