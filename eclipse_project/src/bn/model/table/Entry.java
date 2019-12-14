package bn.model.table;

import java.util.Vector;

import bn.model.Variable;
import formalism.utils.Sorter;

public class Entry {

	protected Vector<EntryPoint> entryset;
	
	public Entry() {
		this.entryset = new Vector<EntryPoint>();
	}
	
	@SuppressWarnings("unchecked")
	public Entry(Entry e) {
		this.entryset = (Vector<EntryPoint>) e.entryset.clone();
	}

	public EntryPoint get(int index) {
		return this.entryset.elementAt(index);
	}
	
	public void sort() {
		Vector<String> names = this.getParents();
		names.sort(null);
		Vector<EntryPoint> newEntrySet = new Vector<EntryPoint>();
		for (int index = 0; index < names.size(); index++) {
			String sp = names.elementAt(index);
			String sv = this.get(sp);
			EntryPoint temp = new EntryPoint(sp,sv);
			newEntrySet.add(temp);
		}
		this.set(newEntrySet);
	}
	
	public void set(Vector<EntryPoint> eset) {
		this.entryset = eset;
	}

	public Vector<String> getParents() {
		Vector<String> retval = new Vector<String>();
		for (EntryPoint ep: this.entryset) {
			retval.add(ep.getVarstate());
		}
		return retval;
	}
	
	public String get(String ParentName) {
		int index = 0;
		boolean found = false;
		String value = null;
		while ((index < this.size()) && (!found)) {
			EntryPoint temp = this.get(index);
			value = temp.getVarname();
			found = (value.equals(ParentName));
			index++;
		}
		return (found) ? value : null;
	}

	public boolean match(Entry r) {
		boolean retval = false;
		if (r.size() == this.size()) {
			int index = 0;
			retval = true;
			while ((retval) && (index < this.size())) {
				EntryPoint rep = r.get(index);
				retval = (this.get(index).equals(rep));
				index++;
			}
		}
		return retval;
	}

	public boolean contains(EntryPoint ep) {
		boolean retval = false;
		int index = 0;
		while ((!retval) && (index<this.size())) {
			retval = (this.get(index).equals(ep));
			index++;
		}
		return retval;
	}

	
	public int size() {
		return this.entryset.size();
	}

	public void add(EntryPoint ep) {
		this.entryset.add(ep);
	}
	
	public Object clone() {
		return new Entry(this);
	}
	
	public String stringvalue() {
		String retval = "";
		int nps = this.entryset.size();
		String[] pss = new String[nps];
		int j = 0;
		for (EntryPoint e: this.entryset) {
			pss[j++] += e.stringvalue(); 
		}
		pss = Sorter.sort(pss);
		for (int i=0; i<nps; i++) {
			retval += pss[i] + "/"; 
		}
		return retval.substring(0,retval.length()-1);
	}

}