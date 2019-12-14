package applications.koon;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

import utils.IniReader;

public class Board {
	
	// Static final attributes
	public static final String F = "F";

	public static final String T = "T";

	public static final String U = "U";

	public static final String[] values = {Board.F, Board.T, Board.U};	

	public static final String pfxSes = "SEN";
	
	public static final String pfxEvs = "EV";

	public static final String pfxUns = "DET";
	
	public static final String pfxExt = "XTR";

	public static final String pfxDfs = "DFF";
	
	public static final String pfxFss = "FSS";

	public static final String pfxRls = "RLS";
	
	// static structures
	private static Hashtable<String,Dependency> failureRates;

	private static Hashtable<String,Double> externals;
	
	private static Hashtable<String,Feedback> feedback;

	private static Hashtable<String,String> externalsMapping;
	
	public static double updateFactor;

	// Methods
	public static void init(IniReader reader) throws Exception {
		Board.create();
		Board.basics(reader);
		Board.externals(reader);
		Board.feedback(reader);
	}
	
	private static void create() {
		Board.failureRates = new Hashtable<String,Dependency>();
		Board.externals = new Hashtable<String,Double>();
		Board.feedback = new Hashtable<String,Feedback>();		
	}

	private static void feedback(IniReader reader) {
		Board.updateFactor = 1;
		if (reader.test("updt")) {
			Board.updateFactor = Double.valueOf(reader.get("updt"));
		}
		int i = 0;
		while (reader.test("feed_" + i)) {
			String temp = reader.get("feed_" + i);
			StringTokenizer st = new StringTokenizer(temp,",");
			String name = st.nextToken();
			String external = st.nextToken();
			Double error = Double.valueOf(st.nextToken());
			Feedback f = new Feedback(external,error);
			Board.feedback.put(name,f);
			i++;
		}
	}

	private static void basics(IniReader reader) throws Exception {
		int i = 0;
		while (reader.test("rate_" + i)) {
			String temp = reader.get("rate_" + i);
			int index = temp.indexOf(',');
			String name = temp.substring(0,index);
			String dep = temp.substring(index+1,temp.length());
			Dependency d = new Dependency(dep);
			Board.failureRates.put(name,d);
			i++;
		}
	}

	private static void externals(IniReader reader) {
		int i = 0;
		while (reader.test("ext_" + i)) {
			String temp = reader.get("ext_" + i);
			int index = temp.indexOf(',');
			String name = temp.substring(0,index);
			double rate = Double.valueOf(temp.substring(index+1,temp.length()));
			Board.externals.put(name,rate);
			i++;
		}
	}

	public static List<Double> getRates(List<String> names) {
		List<Double> retval = new ArrayList<Double>();
		for (int i=0; i<names.size(); i++) {
			String name = names.get(i);
			double value = 0;
			boolean isBasic = Board.isItBasic(name);
			boolean isFeedback = Board.isItFeedback(name);
			if (isBasic) {
				Dependency temp = Board.failureRates.get(name);
				value = temp.getRate(null);				
			} else if (isFeedback) {
				Feedback f = Board.feedback.get(name); 
				value = f.getErrorRate();
			}
			retval.add(value);
		}
		return retval;
	}

	private static boolean isItBasic(String name) {
		boolean retval = Board.failureRates.containsKey(name);
		return retval;
	}

	private static boolean isItFeedback(String name) {
		boolean retval = Board.feedback.containsKey(name);
		return retval;
	}

	public static double getExternalRate(String name) {
		return Board.externals.get(name);
	}

	public static List<String> getExternals() {
		List<String> retval = new ArrayList<String>();
		Enumeration<String> temp = Board.externals.keys();
		while (temp.hasMoreElements()) {
			retval.add(temp.nextElement());
		}
		return retval;
	}

	public static Dependency getDependency(String sensorname) {
		return Board.failureRates.get(sensorname);
	}

	public static Feedback getFeedback(String sensorname) {
		return Board.feedback.get(sensorname);
	}

	public static int getExternalSize() {
		Set<String> set = Board.externals.keySet();  
		return set.size();
	}

	public static String getMapping(String depName) {
		String retval = Board.externalsMapping.get(depName);
		return retval;
	}

	public static void resetMapping() {
		if (Board.externalsMapping == null) {
			Board.externalsMapping = new Hashtable<String,String>();
		}
		Board.externalsMapping.clear();		
	}
	
	public static String reverseMapping(String varname) {
		String retval = null;
		Enumeration<String> keys = Board.externalsMapping.keys();
		while ((keys.hasMoreElements()) && (retval == null)) {
			String key = keys.nextElement();
			String value = Board.getMapping(key);
			if (value.equals(varname)) {
				retval = key;
			}
		}
		return retval;
	}

	public static void addExternalMapping(String external, String varname) {
		Board.externalsMapping.put(external,varname);		
	}

	public static String getDistributionName(String pattern) {
		String retval = "Event(" + Board.T +")-";
		int len = pattern.length();
  		for (int i=1; i<len; i++) {
  			String tag = Board.reverseMapping(Board.pfxExt + (i-1));
  			String val = String.valueOf(pattern.charAt(i));
  			retval += tag + "(" + val + ")-";
  		}
		return retval.substring(0,retval.length()-1);
	}

	public static List<String> getFeedbackVariableFromExternal(String externalName) {
		List<String> retval = new ArrayList<String>();
		Enumeration<String> keys = Board.feedback.keys();
		while (keys.hasMoreElements()) {
			String key = keys.nextElement();
			String effectiveExternal = Board.feedback.get(key).getExternal();
			if (externalName.equals(effectiveExternal)) {
				Hashtable<String,String> ciccio = Board.externalsMapping;				
				String varname = Board.getMapping(key);
				retval.add(varname);
			}
		}
		return retval;
	}

	public static List<String> getFeedbacks() {
		List<String> retval = new ArrayList<String>();
		Enumeration<String> temp = Board.feedback.keys();
		while (temp.hasMoreElements()) {
			retval.add(temp.nextElement());
		}
		return retval;
	}
}