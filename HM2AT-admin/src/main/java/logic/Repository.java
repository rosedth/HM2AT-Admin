package logic;

import java.io.File;
import java.nio.file.Path;

public class Repository {

	private String type;
	private Path path;
	private File config;
	
	public Repository() {};
	public Repository(String type, Path path, File config) {
		this.type = type;
		this.path = path;
		this.config = config;
	}
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Path getPath() {
		return path;
	}
	public void setPath(Path path) {
		this.path = path;
	}
	public File getConfig() {
		return config;
	}
	public void setConfig(File config) {
		this.config = config;
	}
	public static void verifyStructure(Repository repo) {

		
	}
	public static void updateIndexes(Repository repo) {

		
	}
	
	
	
}
