package formalism.random.event;

import formalism.random.RandomGenerator;

public abstract class RandomEventGenerator extends RandomGenerator implements EventGenerator {

	public abstract boolean toss();
	
}