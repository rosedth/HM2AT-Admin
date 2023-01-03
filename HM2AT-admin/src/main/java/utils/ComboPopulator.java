package utils;

import java.lang.StackWalker.Option;
import java.util.ArrayList;

import javax.swing.JComboBox;

public class ComboPopulator {

	public static void populateModelingLanguage(JComboBox<String> combo) {
		ArrayList<String> options = new ArrayList<String>();
		options.add("---");
		options.add("UML");
		options.add("Other 1");
		options.add("Other 2");
		
		for (String value : options) {
			combo.addItem(value);
		}
	}
	
	public static void populateProgrammingLanguage(JComboBox<String> combo) {
		ArrayList<String> options = new ArrayList<String>();
		options.add("---");
		options.add("Java");
		options.add("C");
		options.add("C++");
		options.add("C#");
		options.add("Ruby");
		options.add("PHP");
		options.add("Python");

		for (String value : options) {
			combo.addItem(value);
		}
	}
	

	public static void  populateProgrammingParadigm(JComboBox<String> combo) {
		ArrayList<String> options = new ArrayList<String>();
		options.add("---");
		options.add("Object-oriented");
		options.add("Service-oriented");
		options.add("Aspect-oriented");
		options.add("Declarative");
		options.add("Other");

		for (String value : options) {
			combo.addItem(value);
		}
		
	}

	public static void  populateCodeTypeFile(JComboBox<String> combo) {
		ArrayList<String> options = new ArrayList<String>();
		options.add("---");
		options.add("Source");
		options.add("Library");
		
		for (String value : options) {
			combo.addItem(value);
		}	
	}	
	
	public static void  populateDependencyManager(JComboBox<String> combo) {
		ArrayList<String> options = new ArrayList<String>();
		options.add("---");
		options.add("Maven Central (Java)");
		options.add("NuGet (.NET)");
		options.add("Packagist (PHP)");
		options.add("PyPI (Python)");
		options.add("RubyGems (Ruby)");
		options.add("Other");
		
		for (String value : options) {
			combo.addItem(value);
		}	
	}	

	public static void  populateModelApproach(JComboBox<String> combo){
		ArrayList<String> options = new ArrayList<String>();
		options.add("---");
		options.add("Holistic");
		options.add("Organic Computing");
		options.add("Autonomic Computing");
		options.add("Control Theory");
		options.add("CAS");
		options.add("Other");
		
		for (String value : options) {
			combo.addItem(value);
		}	
	}
	
	public static void  populateRepositoryType(JComboBox<String> combo){
		ArrayList<String> options = new ArrayList<String>();
		options.add("Local");
		options.add("External");
		
		for (String value : options) {
			combo.addItem(value);
		}	
	}
	
}
