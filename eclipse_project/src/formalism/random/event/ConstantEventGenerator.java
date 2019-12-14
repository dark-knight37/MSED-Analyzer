package formalism.random.event;

public class ConstantEventGenerator extends OneRandomThenConstantEventGenerator {

	public ConstantEventGenerator(boolean value) {
		this.response = value;
	}
}
