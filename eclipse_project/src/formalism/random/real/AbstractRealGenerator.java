package formalism.random.real;

import formalism.random.RandomGenerator;

/**
 * @author Stefano Marrone
 */
public abstract class AbstractRealGenerator extends RandomGenerator implements RealNumberGenerator {
	
	protected double upperBound;
	
	protected double lowerBound;
	
	public AbstractRealGenerator(double up, double lo) {
		super();
		this.upperBound = Math.max(up,lo);
		this.lowerBound = Math.min(up,lo);
	}
	
	public double getUpperBound() {
		return this.upperBound;
	}

	public double getLowerBound() {
		return this.lowerBound;
	}
}