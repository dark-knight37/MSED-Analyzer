package formalism.random;

/**
 * This interface represents the behavior of a generator of events.
 * @author Stefano Marrone
 * @date 19.12.2015
 */
public interface BareGenerator {
	
	/**
	 * To restart the random engine if needed
	 */
	public void reset();
}
