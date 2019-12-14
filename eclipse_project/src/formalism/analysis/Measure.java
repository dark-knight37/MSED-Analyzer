package formalism.analysis;

import formalism.core.NamedElement;
import formalism.features.Measurable;

public class Measure extends NamedElement {

	protected Results r;
	
	protected Measurable m;
	
	public Measure(String name, Measurable m){
		super(name);
		this.m = m;
	}
	
	public Measure() {
		super();
	}

	public void addResults(Results r) {
		this.r = r;
	}

	public Results getResults() {
		return this.r;
	}
	
	public Measurable getMeasurable() {
		return this.m;
	}
	
	public void setMeasurable(Measurable m) {
		this.m = m;
	}
}