package formalism.utils;

import java.util.Vector;

public class Sorter {
	
	public static String[] sort(String[] i) {
		String[] retval = new String[i.length];
		Vector<String> temp = new Vector<String>();
		for (int j=0; j<i.length; j++) {
			temp.add(i[j]);
		}
		temp.sort(null);
		for (int j=0; j<i.length; j++) {
			retval[j] = temp.get(j);
		}
		return retval;
	}
}