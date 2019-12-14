package bn.model.table;

import bn.model.BayesianNetwork;
import bn.model.Variable;
import formalism.core.Container;
import formalism.utils.Sorter;

public class ConstantFunction extends CPD {
	
	protected Row values;

	public ConstantFunction(Variable v, BayesianNetwork bn) {
		super(v,bn);
		this.values = new Row(v);
	}

	public Row getValues() {
		return values;
	}

	public void setValues(Row values) {
		this.values = values;
	}

	@Override
	public double getProbability(String value, Entry e) {
		return this.values.getProbability(value);
	}

	@Override
	public void setProbability(String value, double p, Entry e) {
		this.values.setProbability(value,p);		
	}

	@Override
	public void setProbability(String value, double p, String[] pp, String[] ev) {
		this.values.setProbability(value,p);		
	}

	@Override
	protected String printParents(Container c) {
		return "";
	}

	@Override
	protected String printTable(Container c) {
		String retval = "\ttable ";
		String[] values = new String[this.owner.getValueNumber()];
		for (int p=0; p<this.owner.getValueNumber(); p++) {
			values[p] = this.owner.getValue(p);
		}
		values = Sorter.sort(values);
		for (int p=0; p<this.owner.getValueNumber(); p++) {
			String s = values[p];
			double d = this.values.getProbability(s);
			retval += d + " ";
		}
		retval += ";\n";
		return retval;
	}
	
	public Object clone(BayesianNetwork bn) {
		ConstantFunction cf = new ConstantFunction(this.owner,null);
		Row r = this.getValues();
		cf.setValues(new Row(r));
		return cf;
	}

	@Override
	public void substitute(String oldname, String newname) {
		// this is a void method		
	}
}
