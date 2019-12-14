package formalism.core;

import java.util.Vector;

import formalism.analysis.Measure;
import formalism.analysis.Results;
import formalism.features.Measurable;
import formalism.features.Printable;

public abstract class Container extends NamedElement implements Printable {

	protected Vector<Measure> measures;

	public Container() {
		super("");
		this.measures = new Vector<Measure>();
	}

	public Container(String name) {
		super(name);
		this.measures = new Vector<Measure>();
	}
	
	public void add(Measure m) {
		this.measures.add(m);
	}

	public void addMeasure(String name, Measurable m) {
		Measure ms = new Measure(name,m);
		this.add(ms);
	}

	public int getMeasureNumber() {
		return this.measures.size();
	}

	public void delMeasure(int index) {
		this.measures.remove(index);
	}

	public Measure getMeasure(int index) {
		return this.measures.elementAt(index);
	}

	public Vector<Measure> getMeasures() {
		return this.measures;
	}

	public Measure getMeasure(String name) {
		Measure retval = null;
		int index = 0;
		while ((index < this.getMeasureNumber()) && (retval == null)) {
			String temp = this.getMeasure(index).getName();
			if (temp.equals(name)) {
				retval = this.getMeasure(index);
			}
			index++;
		}
		return retval;
	}
	
	public Results getResults(String name) {
		Measure m = this.getMeasure(name);
		return (m == null) ? null : m.getResults();
	}
}