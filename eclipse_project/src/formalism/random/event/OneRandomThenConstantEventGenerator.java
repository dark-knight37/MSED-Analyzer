package formalism.random.event;

import java.util.Random;

public class OneRandomThenConstantEventGenerator implements EventGenerator {
	
	protected boolean response;
	
	public OneRandomThenConstantEventGenerator() {
		Random engine = new Random();
		this.response = engine.nextBoolean(); 
	}
	
	public boolean toss() {
		return this.response;
	}
	
	public void reset() {}
}
