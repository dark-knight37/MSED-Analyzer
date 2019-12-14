package applications.koon;

import java.util.Hashtable;
import java.util.Stack;

import bn.model.BayesianNetwork;
import bn.model.Variable;
import bn.model.table.CPD;
import bn.model.table.CPDFactory;
import formalism.utils.Sorter;

public class VariableFactory {
	
	public static Variable binFactory(String name, BayesianNetwork bn) {
		return VariableFactory.factory(name,bn,2);
	}
	
	private static Variable factory(String name, BayesianNetwork bn, int index) {
		Variable retval = new Variable(name);
		for (int i=0; i<index; i++) {
			retval.add(Board.values[i]);
		}
		bn.add(retval);
		return retval;
	}

	public static Variable terFactory(String name, BayesianNetwork bn) {
		return VariableFactory.factory(name,bn,3);
	}

	public static void setVoterCPT(Variable v, BayesianNetwork bn, int k) {
  		CPD temp = CPDFactory.createCPD(v,bn);
  		int unitNum = bn.getParents(v).size();
  		String[] par = new String[unitNum];
  		for (int i=0; i<unitNum; i++) {
  			par[i] = bn.getParents(v).elementAt(i).getName();
  		}
  		par = Sorter.sort(par);
  		int thespace = bn.getParentSpaceSize(v);
  		for (int i=0; i<thespace; i++) {
  			String[] vv = VariableFactory.decode(i,unitNum);
  			double[] weigths = VariableFactory.voter(vv,k);
  	  		temp.setProbability(Board.F,weigths[0],par,vv);
  	  		temp.setProbability(Board.T,weigths[1],par,vv);
  	  		temp.setProbability(Board.U,weigths[2],par,vv);
  		}
  		v.setDistribution(temp);
	}

	public static void setIdentityCPT(Variable v, BayesianNetwork bn) {
  		CPD temp = CPDFactory.createCPD(v,bn);
 		int parentnumber = bn.getParents(v).size();
  		String[] par = new String[parentnumber];
  		for (int i=0; i<parentnumber; i++) {
  			par[i] = bn.getParents(v).elementAt(i).getName();
  		}
  		par = Sorter.sort(par);
  		String[] vf = {Board.F};
  		temp.setProbability(Board.F,1,par,vf);
  		temp.setProbability(Board.T,0,par,vf);
  		String[] vt = {Board.T};
  		temp.setProbability(Board.F,0,par,vt);
  		temp.setProbability(Board.T,1,par,vt);
  		v.setDistribution(temp);
	}

	
	private static String[] decode(int i, int len) {
		String[] retval = new String[len];
		int vlen = Board.values.length;
		for (int k=0; k<len; k++) {
			retval[k] = Board.values[0];
		}
		Stack<Integer> stack = new Stack<Integer>();
		while (i != 0) {
			int d = i % vlen;
			stack.push(d);
			i /= vlen;
		}
		int ssize = stack.size();
		i = len-1;
		for (int index = (len - ssize); index < len; index++) {
	    	retval[index] = Board.values[stack.pop()];
		}
	    return retval;
	}

	private static double[] voter(String[] vv, int k) {
		Hashtable<String,Integer> votes = new Hashtable<String,Integer>();
		votes.put(Board.F,0);
		votes.put(Board.T,0);
		votes.put(Board.U,0);
		for (int j=0; j< vv.length; j++) {
			int curr = votes.get(vv[j]);
			votes.put(vv[j],curr+1);
		}
		double[] retval = {0,0,0};
		if (votes.get(Board.F) >= k) {
			retval[0] = 1;
		} else if (votes.get(Board.T) >= k) {
			retval[1] = 1;
		} else {
			retval[2] = 1;
		}
		return retval;
	}

	public static void setUnitCPT(Variable v, BayesianNetwork bn, double fp, double fn, Dependency dependency) {
		CPD temp = CPDFactory.createCPD(v,bn);
		int parentnumber = bn.getParents(v).size();
		String[] par = new String[parentnumber];
		for (int i=0; i<parentnumber; i++) {
			par[i] = bn.getParents(v).elementAt(i).getName();
		}
		par = Sorter.sort(par);
		int runs = (int) Math.pow(2,parentnumber);
		String[] testpattern = new String[parentnumber]; 
		for (int i=0; i<runs; i++) {
			String code = Integer.toBinaryString(i);
			for (int j=0; j<parentnumber; j++) {
				String cod = Board.F;
				if (j<code.length()) {
					String testChar = String.valueOf(code.charAt(code.length()-j-1));
					if (testChar.equals("1")) {
						cod = Board.T; 
					}
				}
				testpattern[parentnumber-j-1] = cod;
			}
			Arbiter a = new Arbiter(testpattern,par,dependency);
			double df = a.get(Board.F);
			double dt = a.get(Board.T);
			double du = a.get(Board.U);  						
			temp.setProbability(Board.F,df,par,testpattern);
			temp.setProbability(Board.T,dt,par,testpattern);
			temp.setProbability(Board.U,du,par,testpattern);
		} 
		v.setDistribution(temp);
	}

	public static void setUnitCPT(Variable v, BayesianNetwork bn, double err) {
		CPD temp = CPDFactory.createCPD(v,bn);
		int parentnumber = bn.getParents(v).size();
		String[] par = new String[parentnumber];
		for (int i=0; i<parentnumber; i++) {
			par[i] = bn.getParents(v).elementAt(i).getName();
		}
		par = Sorter.sort(par);
		int runs = (int) Math.pow(2,parentnumber);
		String[] testpattern = new String[parentnumber]; 
		for (int i=0; i<runs; i++) {
			String code = Integer.toBinaryString(i);
			for (int j=0; j<parentnumber; j++) {
				String cod = Board.F;
				if (j<code.length()) {
					String testChar = String.valueOf(code.charAt(code.length()-j-1));
					if (testChar.equals("1")) {
						cod = Board.T; 
					}
				}
				testpattern[parentnumber-j-1] = cod;
			}
			ArbiterTer a = new ArbiterTer(testpattern,par,err);
			double df = a.get(Board.F);
			double dt = a.get(Board.T);
			temp.setProbability(Board.F,df,par,testpattern);
			temp.setProbability(Board.T,dt,par,testpattern);
		} 
		v.setDistribution(temp);
	}

	public static void setCauseCPT(Variable v, BayesianNetwork bn, double pTrue) {
		VariableFactory.setLeafCPT(v,bn,pTrue);
	}	

	public static void setEventCPT(Variable v, BayesianNetwork bn, double pTrue) {
		VariableFactory.setLeafCPT(v,bn,pTrue);
	}

	public static void setSensorCPT(Variable v, BayesianNetwork bn, double pTrue) {
		VariableFactory.setLeafCPT(v,bn,pTrue);
	}
	
	public static void setExternalCPT(Variable v, BayesianNetwork bn, double pTrue) {
		VariableFactory.setLeafCPT(v,bn,pTrue);
	}

	private static void setLeafCPT(Variable v, BayesianNetwork bn, double pTrue) {
 		CPD temp = CPDFactory.createCPD(v,bn);
  		temp.setProbability(Board.F,1-pTrue,null);
  		temp.setProbability(Board.T,pTrue,null);
  		v.setDistribution(temp);
	}

	public static void setRelCPT(Variable v, BayesianNetwork bn, double rep) {
 		CPD temp = CPDFactory.createCPD(v,bn);
 		rep = normalizeReputation(rep);
  		String[] par = new String[1];
  		for (int i=0; i<1; i++) {
  			par[i] = bn.getParents(v).elementAt(i).getName();
  		}
  		par = Sorter.sort(par);
  		String[] v0 = {Board.F};
  		temp.setProbability(Board.F,(rep <= 0.7) ? 0 : ((rep >= 0.97) ? 1 : rep),par,v0);
  		temp.setProbability(Board.T,(rep <= 0.7) ? 0 : ((rep >= 0.97) ? 0 : (1 -rep)),par,v0);
  		temp.setProbability(Board.U,(rep <= 0.7) ? 1 : 0,par,v0);
  		String[] v1 = {Board.T};
  		temp.setProbability(Board.F,(rep <= 0.7) ? 0 : ((rep >= 0.97) ? 0 : (1 -rep)),par,v1);
  		temp.setProbability(Board.T,(rep <= 0.7) ? 0 : ((rep >= 0.97) ? 1 : rep),par,v1);
  		temp.setProbability(Board.U,(rep <= 0.7) ? 1 : 0,par,v1);
  		String[] v2 = {Board.U};
  		temp.setProbability(Board.F,0,par,v2);
  		temp.setProbability(Board.T,0,par,v2);
  		temp.setProbability(Board.U,1,par,v2);
 		v.setDistribution(temp);
	}

	private static double normalizeReputation(double rep) {
		return Math.max(Math.min(rep,0.97),0.7);
	}

	public static void setRepCPT(Variable v, BayesianNetwork bn) {
		CPD temp = CPDFactory.createCPD(v,bn);
		int parentnumber = bn.getParents(v).size();
		String[] par = new String[parentnumber];
		for (int i=0; i<parentnumber; i++) {
			par[i] = bn.getParents(v).elementAt(i).getName();
		}
		par = Sorter.sort(par);
		int runs = (int) Math.pow(2,parentnumber);
		String[] testpattern = new String[parentnumber]; 
		for (int i=0; i<runs; i++) {
			String code = Integer.toBinaryString(i);
			for (int j=0; j<parentnumber; j++) {
				String cod = Board.F;
				if (j<code.length()) {
					String testChar = String.valueOf(code.charAt(code.length()-j-1));
					if (testChar.equals("1")) {
						cod = Board.T; 
					}
				}
				testpattern[parentnumber-j-1] = cod;
			}
			ArbiterDos a = new ArbiterDos(testpattern,par,Board.updateFactor);
			double df = a.get(Board.F);
			double dt = a.get(Board.T);
			temp.setProbability(Board.F,df,par,testpattern);
			temp.setProbability(Board.T,dt,par,testpattern);
		} 
		v.setDistribution(temp);
	}
}