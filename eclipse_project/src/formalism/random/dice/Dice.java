package formalism.random.dice;


/**
 * @author Stefano Marrone
 */
public class Dice extends ZeroDice {
	
	public Dice(int faces) {
		super(faces);
	}

	public int roll() {		
		return super.roll() + 1;
	}
}