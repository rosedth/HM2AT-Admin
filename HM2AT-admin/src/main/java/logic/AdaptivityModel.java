package logic;

import java.nio.file.Path;
import java.util.concurrent.atomic.AtomicInteger;

import frames.Main;

public class AdaptivityModel {
	private String id;
	private String name;
	private String description;
	private String approach;
	private String language;
	private Path sourcePath;

	
	public static String getNextId() {
	  Integer next=Main.indexes.get("modelID")+1;
	  return "MOD"+String.format("%05d",next);
	}
	
	public AdaptivityModel() {
		super();
	}
	
	public AdaptivityModel(String id,String name, String description, String approach, String language, Path sourcePath) {
		super();
		this.id=id;
		this.name = name;
		this.description = description;
		this.approach = approach;
		this.language = language;
		this.sourcePath = sourcePath;
	}
	
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
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id=id;
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
		model2String="id "+this.id+"\n"+
					 "name "+this.name+"\n"+
				     "description "+this.description+"\n"+
				     "approach "+this.approach+"\n"+
				     "language "+this.language+"\n"+
				     "sourcePath "+this.sourcePath;
		return model2String;
	}
}
