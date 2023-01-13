package logic;

import java.nio.file.Path;

import frames.Main;

public class ImplementationExample {
	private String id;
	private String name;
	private String implementationId;
	private String description;
	private String programmingLang;
	private String paradigm;
	private Path sourcePath;
	
	public ImplementationExample() {
		super();
	}
	
	
	public ImplementationExample(String name, String implementationId, String description, String programmingLang,
			String paradigm, Path sourcePath) {
		super();
		this.name = name;
		this.implementationId = implementationId;
		this.description = description;
		this.programmingLang = programmingLang;
		this.paradigm = paradigm;
		this.sourcePath = sourcePath;
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public static String getNextId() {
		Integer next = Main.indexes.get("exampleID") + 1;
		return "EXP" + String.format("%05d", next);
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public AdaptivityModelImplementation getImplementation() {
		for (AdaptivityModelImplementation implementation : Main.implementations) {
			if (implementation.getId().equals(this.implementationId)) {
				return implementation;
			}
		}
		return null;
	}
	
	public void setImplementationId(String implementationId) {
		this.implementationId = implementationId;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getProgrammingLang() {
		return programmingLang;
	}
	public void setProgrammingLang(String programmingLang) {
		this.programmingLang = programmingLang;
	}
	public String getParadigm() {
		return paradigm;
	}
	public void setParadigm(String paradigm) {
		this.paradigm = paradigm;
	}
	public Path getSourcePath() {
		return sourcePath;
	}
	public void setSourcePath(Path sourcePath) {
		this.sourcePath = sourcePath;
	}
	
	public String toString() {
		String example2String="";
		example2String="id "+this.id+"\n"+
					 "name "+this.name+"\n"+
				     "implementationId "+this.implementationId+"\n"+				
				     "description "+this.description+"\n"+
				     "programmingLang "+this.programmingLang+"\n"+
				     "paradigm "+this.paradigm+"\n"+
				     "sourcePath "+this.sourcePath;
		return example2String;
	}
}
