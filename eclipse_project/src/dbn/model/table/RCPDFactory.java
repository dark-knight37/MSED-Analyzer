package dbn.model.table;

import bn.model.BayesianNetwork;
import bn.model.Variable;
import bn.model.table.CPD;
import bn.model.table.ConstantFunction;

public class RCPDFactory {
	
	public static CPD createCPD(Variable v, BayesianNetwork bn) {
		CPD retval = null;
		int pNum = bn.getParents(v).size();
		if (pNum == 0) {
			retval = new ConstantFunction(v,bn);
		} else {
			retval = new RCPT(v,bn);
		}
		return retval;
	}

}
