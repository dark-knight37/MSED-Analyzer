package bn.analysis;

import java.io.File;
import java.io.PrintWriter;

import external.javabayes.InferenceGraphs.InferenceGraph;
import formalism.utils.LabelGenerator;

public class ModelFactory {

	public static InferenceGraph create(String fill) throws Exception {
		String tempFileName = LabelGenerator.get("tmp");
		File temp = File.createTempFile(tempFileName,".tmp"); 
		PrintWriter out = new PrintWriter(temp);
		out.println(fill);
		out.flush();
 	   	InferenceGraph mdl = new InferenceGraph(temp.getAbsolutePath());
 	   	temp.deleteOnExit();
		return mdl;
	}
}