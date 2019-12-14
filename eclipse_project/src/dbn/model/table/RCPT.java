package dbn.model.table;

import java.util.Vector;

import bn.model.BayesianNetwork;
import bn.model.Variable;
import bn.model.table.CPT;


public class RCPT extends CPT {
	
	public RCPT() {
		super();
	}

	public RCPT(Variable v, BayesianNetwork bn) {
		super(v,bn);
	}
	

}