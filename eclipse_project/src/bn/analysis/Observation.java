package bn.analysis;

public class Observation {

	protected String variable;
	
	protected String value;

	public Observation(String variable, String value) {
		super();
		this.variable = variable;
		this.value = value;
	}

	public String getVariable() {
		return variable;
	}

	public void setVariable(String variable) {
		this.variable = variable;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
