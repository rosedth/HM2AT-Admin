package logic;

import java.nio.file.Path;

import frames.Main;

public class AdaptivityModelImplementation {
	private String id;
	private String name;
	private String modelId;
	private String programmingLang;
	private String paradigm;
	private String type;
	private Path sourcePath;
	private Path script;

	public AdaptivityModelImplementation() {
		super();
	}

	public AdaptivityModelImplementation(String modelId, String name, String programmingLang, String paradigm,
			String type, Path sourcePath, Path script) {
		super();
		this.name = name;
		this.modelId = modelId;
		this.programmingLang = programmingLang;
		this.paradigm = paradigm;
		this.type = type;
		this.sourcePath = sourcePath;
		this.script = script;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public static String getNextId() {
		Integer next = Main.indexes.get("implementationID") + 1;
		return "IMP" + String.format("%05d", next);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public AdaptivityModel getModel() {
	    for (AdaptivityModel model : Main.models) {
	        if (model.getId().equals(this.modelId)) {
	            return model;
	        }
	    }
		return null;
	}

	public void setModelId(String modelId) {
		this.modelId = modelId;
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Path getSourcePath() {
		return sourcePath;
	}

	public void setSourcePath(Path sourcePath) {
		this.sourcePath = sourcePath;
	}

	public Path getScript() {
		return script;
	}

	public void setScript(Path script) {
		this.script = script;
	}

	@Override
	public String toString() {
		String imp2String = "";
		imp2String = "id " + this.id + "\n" + "name " + this.name + "\n" + "modelId " + this.modelId + "\n"
				+ "programmingLang " + this.programmingLang + "\n" + "paradigm " + this.paradigm + "\n" + "type "
				+ this.type + "\n" + "sourcePath " + this.sourcePath + "\n" + "script " + this.script;
		return imp2String;
	}

}
