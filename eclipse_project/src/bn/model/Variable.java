package bn.model;

import java.util.Random;
import java.util.Vector;

import bn.model.table.CPD;
import formalism.core.Container;
import formalism.core.Node;
import formalism.features.Measurable;
import formalism.features.Printable;
import formalism.utils.Sorter;

/**
 * The class represents the BN variable.
 * @author stefano
 * @todo Currently, the code deals only with discrete variable without stating it formally. Future works will be oriented to expand the hierarchy.  
 */
public class Variable extends Node implements Printable, Cloneable, Measurable {
	
	/**
	 * Discrete vector of the values which can be assigned to the variable.
	 */
	protected Vector<String> values;
	
	protected CPD distribution;

    public Variable(String name) {
        super(name);
        this.values = new Vector<String>();
        this.distribution = null; 
    }

    public void add(String val) {
    	this.values.add(val);
    }
    
    public int getValueIndex(String val) {
    	return this.values.indexOf(val);
    }
    
    public String getValue(int index) {
    	return this.values.get(index);
    }

    protected void setValues(Vector<String> list) {
    	this.values = list;
    }

    public int getValueNumber() {
    	return this.values.size();
    }

    public CPD getDistribution() {
		return this.distribution;
	}

	public void setDistribution(CPD d) {
		this.distribution = d;
	}

	@Override
	public String toSpecificFormat(Container c) {
		String retval = "variable  \"" + this.getName() + "\" {\n";
		// Values
		String[] values = new String[this.getValueNumber()];
		for (int p=0; p<this.getValueNumber(); p++) {
			values[p] = this.getValue(p);
		}
		values = Sorter.sort(values);

		retval += "\ttype discrete[" + this.getValueNumber() + "] { ";
		for (int i = 0; i < this.getValueNumber(); i++) {
			retval += " \"" + values[i] + "\" "; 
		}
		retval += "};\n";
		// Fake position
		Random engine = new Random();
		int x = engine.nextInt(1000); 
		int y = engine.nextInt(1000); 
		retval += "\tproperty \"position = (" + x + ", " + y + ")\" ;\n}\n";
		return retval;
	}

	public Vector<String> getValues() {
		return this.values;
	}
	
	public Object clone(BayesianNetwork bn) {
		Variable retval = new Variable(this.getName());
		for (String l: this.labels) {
			retval.addLabel(l);
		}
		for (String v: this.values) {
			retval.add(v);
		}
		retval.setDistribution((CPD) this.getDistribution().clone(bn));
		retval.getDistribution().setOwner(retval);
		return retval;
	}

	public Variable copyStructure(String sfx) {
		Variable retval = new Variable(this.getName() + sfx);
		for (String l: this.labels) {
			retval.addLabel(l);
		}
		for (String v: this.values) {
			retval.add(v);
		}
		return retval;
	}
}