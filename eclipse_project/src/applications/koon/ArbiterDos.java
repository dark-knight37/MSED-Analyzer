package applications.koon;

import java.util.ArrayList;
import java.util.List;

public class ArbiterDos {
	
	private Triplet t;
	
	private String[] testpattern;
	
	private String[] parameters;
	
	public ArbiterDos(String[] testpattern, String[] par, double errorRate) {
		this.parameters = par;
		this.testpattern = testpattern;
		this.t = new Triplet();
		if (!this.sensorDown()) {
			int counter = this.howManyFeedbacks();
			double temp = Math.pow(errorRate,counter);
			t.set(Board.T,temp);
			t.set(Board.F,1-temp);
		} else {
			t.set(Board.T,0);
			t.set(Board.F,1);
		}
	}
	
	private int howManyFeedbacks() {
		int retval = 0;
		for (int i=0; i<this.parameters.length; i++) {
			if (this.parameters[i].startsWith(Board.pfxDfs)) {
				if (this.testpattern[i].equals(Board.F)) {
					retval++;
				}
			}
		}
		return retval;
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
