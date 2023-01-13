package logic;

import java.nio.file.Path;

import frames.Main;

public class ImplementationDependency {

	private String id;
	private String name;
	private String implementationId;
	private String manager;
	private String spec;
	private Path sourcePath;

	public ImplementationDependency() {
		super();
	}

	public ImplementationDependency(String name, String implementationId, String manager, String spec,
			Path sourcePath) {
		super();
		this.name = name;
		this.implementationId = implementationId;
		this.manager = manager;
		this.spec = spec;
		this.sourcePath = sourcePath;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public static String getNextId() {
		Integer next = Main.indexes.get("dependencyID") + 1;
		return "DEP" + String.format("%05d", next);
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

	public String getManager() {
		return manager;
	}

	public void setManager(String manager) {
		this.manager = manager;
	}

	public String getSpec() {
		return spec;
	}

	public void setSpec(String spec) {
		this.spec = spec;
	}

	public Path getSourcePath() {
		return sourcePath;
	}

	public void setSourcePath(Path sourcePath) {
		this.sourcePath = sourcePath;
	}

	@Override
	public String toString() {

		String dep2String = "";
		dep2String = "id " + this.id + "\n" + "name " + this.name + "\n" + "implementationId " + this.implementationId
				+ "\n" + "manager " + this.manager + "\n" + "spec " + this.spec + "\n" + "sourcePath "
				+ this.sourcePath;
		return dep2String;
	}
}
