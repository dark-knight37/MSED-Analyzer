package applications.koon;

import java.util.ArrayList;
import java.util.List;

public class ArbiterTer {
	
	private Triplet t;
	
	private String[] testpattern;
	
	private String[] parameters;
	
	public ArbiterTer(String[] testpattern, String[] par, double errorRate) {
		this.parameters = par;
		this.testpattern = testpattern;
		this.t = new Triplet();
		if (!this.sensorDown()) {
			t.set(Board.T,errorRate);
			t.set(Board.F,1-errorRate);
		} else {
			t.set(Board.T,0);
			t.set(Board.F,1);
		}
	}

	private boolean sensorDown() {
		boolean retval = false;
		int i = 0;
		while ((retval == false) && (i < this.parameters.length)) {
			if (this.parameters[i].startsWith("SEN")) {
				retval = testpattern[i].equals(Board.F);
			}
			i++;
		}
		return retval;
	}

	public double get(String question) {
		return this.t.get(question);
	}
}
