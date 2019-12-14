package bn.analysis.distribution;

import java.util.Hashtable;
import java.util.Stack;

import bn.analysis.distribution.analysis.DepthFirstAdapter;
import bn.analysis.distribution.node.ADefDecimal;
import bn.analysis.distribution.node.ADefExponent;
import bn.analysis.distribution.node.ADefFloat;
import bn.analysis.distribution.node.ADefMantisse;
import bn.analysis.distribution.node.AIdVariable;
import bn.analysis.distribution.node.APayloadStructure;
import bn.analysis.distribution.node.ASciFloat;

public class DistributionResultAnalyser extends DepthFirstAdapter {
	
	protected Hashtable<String,Double> results;
	
	private Stack<String> stack;
	
	public DistributionResultAnalyser() {
		super();
		this.stack = new Stack<String>();
		this.results = new Hashtable<String,Double>();
	}

	public Hashtable<String,Double> getResult() {
		return this.results;
	}
	
    public void inAPayloadStructure(APayloadStructure node) {
        this.stack.clear();
    }

    public void outAPayloadStructure(APayloadStructure node) {
        String var = this.stack.pop();
        Double value = Double.valueOf(this.stack.pop());
        this.results.put(var,value);
    }

    public void outADefFloat(ADefFloat node) {
        Double m = Double.valueOf(this.stack.pop());
        Double d = Double.valueOf(this.stack.pop());
        Double res = m + d;
        stack.push(res.toString());
    }

    public void outASciFloat(ASciFloat node) {
        Double e = Double.valueOf(this.stack.pop());
        Double d = Double.valueOf(this.stack.pop());
        Double m = Double.valueOf(this.stack.pop());
        Double res = (m + d) / Math.pow(10,e);
        stack.push(res.toString());
    }

    public void inADefMantisse(ADefMantisse node) {
        String temp = node.getNumber().getText();
        this.stack.push(temp);
    }

    public void inADefExponent(ADefExponent node) {
        String temp = node.getNumexp().getText();
        int eindex = temp.indexOf("E");
        Double d = Double.valueOf("0." + temp.substring(0,eindex));
        Double e = Double.valueOf(temp.substring(eindex+2,temp.length())); 
        this.stack.push(d.toString());
        this.stack.push(e.toString());
    }

    public void inADefDecimal(ADefDecimal node) {
        String temp = node.getNumber().getText();
        Double d = Double.valueOf("0." + temp);
        this.stack.push(d.toString());
    }

    public void inAIdVariable(AIdVariable node) {
        String temp = node.getIdentifier().getText();
        this.stack.push(temp);
    }
}
