package fgcs.applications;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.List;

import applications.koon.ExperimentFactory;
import applications.koon.SimpleVoter;
import bn.analysis.JavaBayesSolver;
import bn.analysis.distribution.Distribution;
import bn.model.BayesianNetwork;
import formalism.analysis.DiscreteDistribution;
import utils.Utils;

public class SIL_Designer {
	
	public static void main(String[] args) {
		String confName = args[0];
		String csvName = args[1];
		String buffer = Utils.smallHeader();
		JavaBayesSolver jbds = new JavaBayesSolver();
		List<BayesianNetwork> models;
		try {
			models = ExperimentFactory.generate(confName);
			for (BayesianNetwork kn: models) {
				long time = System.nanoTime();
				jbds.analyse(kn);
				float ttime = Utils.nano2milli(System.nanoTime() - time);
				String name = kn.getName();
				for (int i=0; i<kn.getMeasureNumber(); i++) {
					Distribution d = (Distribution) kn.getMeasure(i);
					DiscreteDistribution dd = (DiscreteDistribution) d.getResults();
					SimpleVoter voter = (SimpleVoter) kn;
					String oracle = voter.getOracle(d.getName());
					buffer += name + ";" + voter.getK() + ";" + voter.getN() + ";";
					buffer += d.getName() + ";";
					//buffer += dd.toCSV();
					buffer += dd.getHazardProb(oracle) + ";"; 
					buffer += dd.getUnavail() + ";\n";
					buffer += ttime + ";";
					buffer += kn.getGenerationTime() + ";\n";
				}
			}
		    BufferedWriter writer = new BufferedWriter(new FileWriter(csvName));
		    writer.write(buffer);		     
		    writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}