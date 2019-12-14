package dbn.model;

import bn.model.Arc;
import bn.model.Variable;

public class MemoryArc extends Arc {

	public MemoryArc(String name, Variable from, Variable to) {
		super(name,from,to);
	}
}