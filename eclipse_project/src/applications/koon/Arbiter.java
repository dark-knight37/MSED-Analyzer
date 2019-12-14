package applications.koon;

import java.util.ArrayList;
import java.util.List;

public class Arbiter {
	
	private Triplet t;
	
	private String[] testpattern;
	
	private String[] parameters;
	
	public Arbiter(String[] testpattern, String[] par, Dependency dependency) {
		this.parameters = par;
		this.testpattern = testpattern;
		this.t = new Triplet();
		if (!this.sensorDown()) {
			t.set(Board.U,0);			
			List<String> actives = this.getActiveExternals();
			double errorRate = dependency.getRate(actives); 
			if (this.isEventTrue()) {
				t.set(Board.T,1-errorRate);
				t.set(Board.F,errorRate);
			} else {
				t.set(Board.T,errorRate);
				t.set(Board.F,1-errorRate);
			}
		} else {
			t.set(Board.T,0);
			t.set(Board.F,0);
			t.set(Board.U,1);			
		}
	}
	
	private List<String> getActiveExternals() {
		List<String> retval = new ArrayList<String>();
		for (int i=0; i<this.parameters.length; i++) {
			String parameter = this.parameters[i];
			if (parameter.startsWith("XTR")) {
				if (this.testpattern[i].equals(Board.T)) {
					String external = Board.reverseMapping(parameter);
					retval.add(external);
				}
			}
		}
		return retval;
	}

	private boolean sensorDown() {
		boolean retval = false;
		int i = 0;
		while ((retval == false) && (i < this.parameters.length)) {
			if (this.parameters[i].startsWith(Board.pfxRls)) {
				retval = testpattern[i].equals(Board.F);
			}
			i++;
		}
		return retval;
	}
	
	private boolean isEventTrue() {
		boolean retval = false;
		int i = 0;
		while ((retval == false) && (i < this.parameters.length)) {
			if (this.parameters[i].startsWith("EV")) {
				retval = this.testpattern[i].equals(Board.T);
			}
			i++;
		}
		return retval;
	}

	public double get(String question) {
		return this.t.get(question);
	}
}
