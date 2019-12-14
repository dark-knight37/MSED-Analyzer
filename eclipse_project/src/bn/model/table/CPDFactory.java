package bn.model.table;

import bn.model.BayesianNetwork;
import bn.model.Variable;

public class CPDFactory {
	
	public static CPD createCPD(Variable v, BayesianNetwork bn) {
		CPD retval = null;
		int pNum = bn.getParents(v).size();
		if (pNum == 0) {
			retval = new ConstantFunction(v,bn);
		} else {
			retval = new CPT(v,bn);
		}
		return retval;
	}

}
