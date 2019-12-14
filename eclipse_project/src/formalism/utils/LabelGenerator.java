package formalism.utils;

public class LabelGenerator {
	
	private static int counter = 0;
	
	public static String get() {
		return LabelGenerator.get("label");
	}

	public static String get(String s) {
		return s + (counter++);
	}
}