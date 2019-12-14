package dbn.model;

import bn.model.Variable;

public class PreviousVariable extends Variable {

	public PreviousVariable(String name) {
		super(name);
	}
	
	public PreviousVariable(Variable y) {
		super(y.getName());
		this.setDistribution(y.getDistribution());
		this.setValues(y.getValues());
	}

	public String getName() {
		String temp = super.getName(); 
		return "(" + temp + ")";
	}
}