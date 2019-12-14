package formalism.random.real;


public class GaussianEventGenerator extends AbstractRealGenerator {

	public GaussianEventGenerator(double up, double lo) {
		super(up, lo);
	}

	@Override
	public double roll() {
		double mean = (this.getUpperBound() - this.getLowerBound()) / 2;
		double guess = this.engine.nextGaussian() + mean;
		guess = Math.min(guess,this.getUpperBound());
		guess = Math.max(guess,this.getLowerBound());
		return guess;
	}

}
