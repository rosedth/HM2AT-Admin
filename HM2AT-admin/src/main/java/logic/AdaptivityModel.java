package logic;

import java.nio.file.Path;

public class AdaptivityModel {
	private String name;
	private String description;
	private String approach;
	private String language;
	private Path sourcePath;
	
	
	public AdaptivityModel(String name, String description, String approach, String language, Path sourcePath) {
		super();
		this.name = name;
		this.description = description;
		this.approach = approach;
		this.language = language;
		this.sourcePath = sourcePath;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getApproach() {
		return approach;
	}
	public void setApproach(String approach) {
		this.approach = approach;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public Path getSourcePath() {
		return sourcePath;
	}
	public void setSourcePath(Path sourcePath) {
		this.sourcePath = sourcePath;
	}
	
	public boolean verifyModel() {
		// Verify if the model is an instance of the Holistic Meta-Model for Adaptivity
		// Load the holistic meta-model specification
		// Load the model specification
		// compare UML models to verify compliance from model to meta-model
		// return result
		return true;
	}
	
	public String toString() {
		String model2String="";
		model2String="name "+this.name+"\n"+
				     "description "+this.description+"\n"+
				     "approach "+this.approach+"\n"+
				     "language "+this.language+"\n"+
				     "sourcePath "+this.sourcePath;
		return model2String;
	}
}
