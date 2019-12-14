package formalism.core;

import java.util.Vector;

import formalism.features.Labellable;

public abstract class Node extends NamedElement implements Labellable {

	protected Vector<String> labels;
		
	public Node(String name) {
		super(name);
		labels = new Vector<String>();
	}
	
	public void addLabel(String label) {
		labels.add(label);
	}
	
	public boolean hasLabel(String label) {
		return labels.contains(label);
	}
	
	public Vector<String> getLabels() {
		return labels;
	}
	
	public void deleteLabler(String label) {
		this.labels.remove(label);
	}

	public void substitute(String former, String newer) {
		if (this.hasLabel(former)) {
			this.deleteLabler(former);
			this.addLabel(newer);
		}
	}
	
	public void copyLabels(Node n) {
		for (int i = 0; i < this.labels.size(); i++) {
			n.addLabel(this.labels.elementAt(i));
		}
	}
	
}
