package bn.analysis.distribution;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PushbackReader;
import java.io.StringReader;
import java.util.Vector;

import bn.analysis.BNMeasure;
import bn.analysis.distribution.lexer.Lexer;
import bn.analysis.distribution.lexer.LexerException;
import bn.analysis.distribution.node.Start;
import bn.analysis.distribution.parser.Parser;
import bn.analysis.distribution.parser.ParserException;
import bn.model.Variable;
import external.javabayes.InferenceGraphs.InferenceGraph;
import formalism.analysis.DiscreteDistribution;
import formalism.features.Measurable;

public class Distribution extends BNMeasure {

	public Distribution(String name, Measurable m) {
		super(name, m);
	}

	public Distribution(String name) {
		super(name);
	}

	@Override
	protected Vector<String> commit(InferenceGraph graph) throws Exception {
		Vector<String> retval = new Vector<String>();
		int size = this.size();
		for (int i=0; i<size; i++) {
			Variable v = (Variable) this.measurableArray.get(i);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			PrintStream ps = new PrintStream(baos);
			graph.print_marginal(ps,v.getName(),false,false);
			retval.add(baos.toString());
		}
		graph.reset_marginal();
		return retval;
	}

	@Override
	protected DiscreteDistribution infere(String resultString) {
		DiscreteDistribution retval = null;
		PushbackReader pbr = new PushbackReader(new StringReader(resultString), 1024);
		Lexer lexer = new Lexer (pbr); 
		Parser parser = new Parser(lexer); 
		DistributionResultAnalyser dra = null;
		try {
			Start ast = parser.parse();
			dra = new DistributionResultAnalyser();
			ast.apply(dra);
			retval = new DiscreteDistribution(dra.getResult());
		} catch (ParserException | LexerException | IOException e) {
			e.printStackTrace();
		}
		return retval;
	}
	
	protected String prefilter(String input) {
		int startIdx = input.indexOf("table");
		int endIdx= input.lastIndexOf("}");
		String retval = input.substring(startIdx,endIdx);  
		retval = retval.replaceAll("\t","");
		retval = retval.replaceAll("\n","");
		retval = retval.replace("p(","p( ");
		retval = retval.trim();
		return retval; 
	}

}