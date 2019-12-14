package formalism.features;

import java.util.Vector;

public interface Labellable {
	public void addLabel(String label);
	
	public boolean hasLabel(String label);
	
	public Vector<String> getLabels();
	
	public void substitute(String former, String newer);
}
