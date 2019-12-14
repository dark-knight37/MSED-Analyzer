package utils;

import java.util.List;

public class Utils {

	public static String genName(List<String> values, int tag1) {
		String retval = "";
		for (int i=0; i<values.size(); i++) {
			retval += values.get(i) + "-";
		}
		return retval.substring(0,retval.length()-1);
	}

	public static float nano2milli(long nanotime) {
		float retval = (float) nanotime / 10000;
		return retval/100;
	}
	
	public static String header() {
		return "configuration;K;N;test;unknown;true;false;hazard_probability;unavail\n";
	}

	public static String getOracle(int k, String name) {
		String retval = "U";
		int trueCounter = 0;
		int falseCounter = 0;
		for (int i = 0; i < name.length(); i++) {
		    if (name.charAt(i) == 'T') {
		        trueCounter++;
		    } else if (name.charAt(i) == 'F') {
		    	falseCounter++;
		    }
		}
		if (falseCounter >= k) {
			retval = "F";
		} else if (trueCounter >= k) {
			retval = "T";
		}
		return retval;
	}

	public static String[] generationOfPatterns(List<String> externals) {
		String retval[] = null;		
		int size = externals.size();
		retval = new String[size+1];		
		retval[0] = "T" + Utils.generatePattern("F","T",size,-1);
		if (size > 0) {
			for (int i = 0; i < size; i++) {
				retval[i+1] = "T" + Utils.generatePattern("F","T",size,i);
			}
		}
		return retval;
	}
	
	public static String generatePattern(String base, String variation, int dim, int position) {
		String retval = "";
		for (int i = 0; i < dim; i++) {
			retval += (i == position) ? variation : base; 
		}
		return retval;		
	}

	public static String smallHeader() {
		return "configuration;K;N;test;hazard_probability;unavail;buildtime(msec);executiontime(msec);\n";
	}
}