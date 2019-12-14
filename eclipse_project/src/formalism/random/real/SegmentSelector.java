package formalism.random.real;

/**
 * @author Stefano Marrone
 */
public class SegmentSelector {
	
	protected double[] segments;
	
	protected UniformRealGenerator urg;
	
	public SegmentSelector(double[] ss) {
		this.reset(ss);
	}
	
	public SegmentSelector() {
		this.segments = null;
		this.urg = null;
	}

	public int selectSegment() {
		int ii = 0;
		double toss = this.urg.roll();
		boolean check = false; 
		do {
			check = (ii == this.segments.length) || (this.segments[ii] > toss);
			ii += (!check) ? 1 : 0;
		} while (!check);
		return ii;
	}
	
	private void reset(double[] ss) {
		this.segments = new double[ss.length];
		this.segments[0] = ss[0];
		for (int i = 1; i < ss.length; i++) {
			this.segments[i] = ss[i] + this.segments[i-1];
		}
		this.urg = new UniformRealGenerator(this.segments[ss.length-1], 0);
		this.urg.reset();
	}
}