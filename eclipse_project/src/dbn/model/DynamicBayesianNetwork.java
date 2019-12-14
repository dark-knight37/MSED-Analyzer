package dbn.model;

import java.util.Hashtable;
import java.util.Vector;

import bn.model.Arc;
import bn.model.BayesianNetwork;
import bn.model.Variable;
import bn.model.table.CPD;
import bn.model.table.CPDFactory;
import bn.model.table.ConstantFunction;
import formalism.core.Container;
import formalism.features.Analyzable;
import formalism.utils.LabelGenerator;

public class DynamicBayesianNetwork extends BayesianNetwork implements Analyzable {
	
	public DynamicBayesianNetwork(String name) {
		super(name);
	}
	
	public DynamicBayesianNetwork() {
		super();
	}

	public void addMemoryArc(String name, Variable from, Variable to) {
		MemoryArc a = new MemoryArc(name,from,to);
		this.arcs.add(a);
	}

	public void addMemoryArc(String name, String from, String to) {
		Variable vFrom = this.getVariableByName(from);
		Variable vTo = this.getVariableByName(to);
		this.addMemoryArc(name, vFrom, vTo);
	}

	public void addMemoryArc(String from, String to) {
		Variable vFrom = this.getVariableByName(from);
		Variable vTo = this.getVariableByName(to);
		this.addMemoryArc(vFrom, vTo);
	}

	public void addMemoryArc(Variable from, Variable to) {
		String name = LabelGenerator.get("a");
		this.addMemoryArc(name,from,to);
	}
	
	public BayesianNetwork flatline(int replicas) {
		BayesianNetwork flatlined = new BayesianNetwork(this.getName() + "_fltlnd");
		Vector<String> cmplxVars = new Vector<String>();
		Vector<String> listOfSuffixes = new Vector<String>();
		for (int counter=0; counter<replicas; counter++) {
			listOfSuffixes.add("_SFX" + counter);
		}
		// Copied the variables
		for (int i=0; i<this.getVariableNumber(); i++) {
			Variable it = this.getVariable(i);
			for (String sfx: listOfSuffixes) {
				flatlined.add(it.copyStructure(sfx));
			}
		}
		// Copied the arcs
		for (int i=0; i<this.getArcNumber(); i++) {
			Arc it = this.getArc(i);
			String aname = it.getName();
			String src = it.getFrom().getName();
			String tgt = it.getTo().getName();
			if (it instanceof MemoryArc) {
				cmplxVars.add(tgt);
				for (int counter=0; counter<replicas-1; counter++) {
					String sfx_pre = listOfSuffixes.elementAt(counter);
					String sfx_post = listOfSuffixes.elementAt(counter+1);
					flatlined.add(aname + sfx_pre + sfx_post,src + sfx_pre,tgt + sfx_post);
				}
			} else {
				for (int counter=0; counter<replicas; counter++) {
					String sfx = listOfSuffixes.elementAt(counter);
					flatlined.add(aname + sfx,src + sfx,tgt + sfx);
				}
			}
		}
		// Construction of the CPTs
		for (int i=0; i<this.getVariableNumber(); i++) {
			Variable dbnVar = this.getVariable(i);
			CPD dbnVarDistro = dbnVar.getDistribution();
			String dbnVarName = dbnVar.getName();
			for (int counter = 0; counter < listOfSuffixes.size(); counter++) {
				String suffix = listOfSuffixes.elementAt(counter);
				Variable fltdVar = flatlined.getVariableByName(dbnVarName + suffix);
//				fltdVarDistro = (CPD) dbnVarDistro.clone(flatlined);
				CPD fltdVarDistro = null;
				if (cmplxVars.contains(dbnVarName)) {
					fltdVarDistro = (CPD) dbnVarDistro.clone(this);
					Vector<Variable> parents = this.getParents(dbnVar);
					for (Variable parent: parents) {
						String parentName = parent.getName();
						if (parentName.startsWith("(") && parentName.endsWith(")")) {
							String newParentName = parentName.substring(1,parentName.length()-1) + suffix;
							if (counter == 0) {
								newParentName = dbnVarName + suffix.substring(0,suffix.length()-1) + "inf";
								Variable addedOne = new Variable(newParentName);
								flatlined.add(addedOne);
								flatlined.add(newParentName + "_init", newParentName, dbnVarName + suffix);
					  	  		ConstantFunction cAdded = new ConstantFunction(addedOne,flatlined);
								for (int cc=0; cc<parent.getValueNumber(); cc++) {
									String value = parent.getValue(cc);
									addedOne.add(value);
									Double d = new Double(1.0/parent.getValueNumber());
						  	  		cAdded.setProbability(value,d.doubleValue(),null);
								}
								addedOne.setDistribution(cAdded);
					  	  		cAdded.setOwner(addedOne);
							}
							fltdVarDistro.substitute(parentName,newParentName);
//							fltdVar.setDistribution(fltdVarDistro);
//							fltdVarDistro.setOwner(fltdVar);
						}
					}
				} else {
					fltdVarDistro = (CPD) dbnVarDistro.clone(this);
				}
				fltdVar.setDistribution(fltdVarDistro);
				fltdVar.getDistribution().setOwner(fltdVar);
			}
		}
		return flatlined;
	}

	public Vector<Variable> getParents(Variable v) {
		Vector<Variable> retval = new Vector<Variable>();
		for (Arc a: this.arcs) {
			if (a.isIncomingTo(v)) {
				Variable y = (Variable) a.getFrom();
				if (a instanceof MemoryArc) {
					PreviousVariable x = new PreviousVariable(y);
					retval.add(x);
				} else {
					retval.add(y);
				}
			}
		}
		return retval;
	}

	public Object clone() {
		DynamicBayesianNetwork retval = new DynamicBayesianNetwork(this.name);
		Hashtable<Variable,Variable> mapping = new Hashtable<Variable,Variable>();
		for (Variable x: this.variables) {
			Variable y = (Variable) x.clone(this);
			retval.add(x);
			mapping.put(x,y);
		}
		for (Arc i: this.arcs) {
			Arc j = null; 
			if (i instanceof MemoryArc) {
				j = new MemoryArc(i.getName(),mapping.get(i.getFrom()),mapping.get(i.getTo()));
			} else {
				j = new Arc(i.getName(),mapping.get(i.getFrom()),mapping.get(i.getTo()));
			}
			retval.addArc(j);
		}
		return retval;
	}
}