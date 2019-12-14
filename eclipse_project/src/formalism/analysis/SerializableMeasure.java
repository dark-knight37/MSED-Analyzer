package formalism.analysis;

import formalism.features.Measurable;

public abstract class SerializableMeasure extends Measure {

	protected String fileName;

	public SerializableMeasure(String name, Measurable m, String fileName){
		super(name,m);
		this.fileName = fileName;
	}

	public String getFileName(){
		return fileName;
	}

	public void setFileName(String fileName){
		this.fileName=fileName;
	}
}