package formalism.random.event;

import formalism.random.BareGenerator;

/**
 * This interface represents the behavior of a generator of events.
 * @author Stefano Marrone
 * @date 19.12.2015
 */
public interface EventGenerator extends BareGenerator {
	
	/**
	 * It simulates an arrival event generator. The mechanism can be of different nature: 
	 * stochastic, deterministic, constant, etc..
	 * @return true if the event is generated, false otherwise
	 */
	public boolean toss();
}
