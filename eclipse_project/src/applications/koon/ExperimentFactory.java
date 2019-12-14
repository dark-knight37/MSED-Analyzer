package applications.koon;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import bn.model.BayesianNetwork;
import utils.IniReader;
import utils.Utils;

public class ExperimentFactory {
	
	public static List<BayesianNetwork> generate(String inifilename) throws Exception {
		List<BayesianNetwork> retval = new ArrayList<BayesianNetwork>();
		IniReader reader = new IniReader(inifilename);
		Board.init(reader);
		int i = 0;
		while (reader.test("conf_" + i)) {
			String temp = reader.get("conf_" + i);
			BayesianNetwork experiment = ExperimentFactory.generateSingle(temp);
			retval.add(experiment);
			i++;
		}
		return retval;
	}

	private static SimpleVoter generateSingle(String temp) {
		StringTokenizer tokenizer = new StringTokenizer(temp,",");
		int k = Integer.valueOf(tokenizer.nextToken());
		int n = Integer.valueOf(tokenizer.nextToken());
		List<String> names = new ArrayList<String>();
		for (int i=0; i<n; i++) {
			names.add(tokenizer.nextToken());
		}
		SimpleVoter model = new SimpleVoter(k,n,names);
		model.build();
		String[] patterns = Utils.generationOfPatterns(Board.getExternals()); 
		model.setObservation(patterns);
		return model;
	}
}