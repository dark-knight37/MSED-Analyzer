package tvbn;

import java.util.Vector;

import bn.analysis.JavaBayesSolver;
import bn.analysis.Observation;
import bn.analysis.distribution.Distribution;
import bn.model.BayesianNetwork;
import bn.model.Variable;
import dbn.analysis.TimedBNMeasure;
import dbn.analysis.TimedDiscreteDistribution;
import formalism.analysis.DiscreteDistribution;
import formalism.features.Analyzable;

public class JavaBayesTVBNSolver extends JavaBayesSolver {
	
	private double timeslice;
	
	private final double eps = 0.001; 
	
	public JavaBayesTVBNSolver(double slice) {
		this.timeslice = slice;
	}
	
	@Override
	public void analyse(Analyzable a) throws Exception {
		TimeVaryingDynamicBayesianNetwork sbn = (TimeVaryingDynamicBayesianNetwork) a;
		int ms = sbn.getMeasureNumber();
		BayesianNetwork aModel = (BayesianNetwork) sbn.clone();
		JavaBayesSolver jbs = new JavaBayesSolver();

		for (int i = 0; i < ms; i++) {
			aModel.clearMeasures();
			TimedBNMeasure qry = (TimedBNMeasure) sbn.getMeasure(i);
			TimedDiscreteDistribution retdistro = new TimedDiscreteDistribution();
			sbn.getMeasure(i).addResults(retdistro);
			String varname = ((Variable) qry.getMeasurable()).getName();
			Variable newvar = aModel.getVariableByName(varname);
			String measureName = qry.getName();
			
			double cur = 0;
			double end = qry.getStopTime();
			
			while (cur < end) {
				double next = cur + this.timeslice;
				double avg = cur + this.timeslice / 2;
				Vector<Observation> localObs = qry.getBetween(cur,next-this.eps);
				Distribution d = new Distribution(measureName,newvar);
				// TODO - debug vedere anche le altre variabili
				d.add(localObs);
				aModel.add(d);
				jbs.analyse(aModel);
	  			DiscreteDistribution sd = (DiscreteDistribution) d.getResults();
	  			retdistro.add(avg,sd);
	  			sbn.updateMode(cur,avg,next);
				cur = next;
			}
		}
	}
}