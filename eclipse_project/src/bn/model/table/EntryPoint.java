package bn.model.table;

public class EntryPoint {
	protected String varname;
	
	protected String varstate;

	public EntryPoint(String varname, String varstate) {
		super();
		this.varname = varname;
		this.varstate = varstate;
	}

	public String getVarname() {
		return varname;
	}

	public void setVarname(String varname) {
		this.varname = varname;
	}

	public String getVarstate() {
		return varstate;
	}

	public void setVarstate(String varstate) {
		this.varstate = varstate;
	}
	
	public boolean equals(EntryPoint ep) {
		return (ep.varname.equals(this.varname)) && (ep.varstate.equals(this.varstate));
	}
	
	public Object clone() {
		return new EntryPoint(this.varname,this.varstate);
	}
	
	public String stringvalue() {
		return this.varname + "=" + this.varstate;
	}

}