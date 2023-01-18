package logic;

import java.io.File;
import java.nio.file.Path;

public class Repository {

	private String type;
	private Path path;
	private File config;
	private Path scriptDir;
	
	public Repository() {};
	public Repository(String type, Path path, File config, Path scriptDir) {
		this.type = type;
		this.path = path;
		this.config = config;
		this.scriptDir=scriptDir;
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
	public Path getScriptDir() {
		return scriptDir;
	}
	public void setScriptDir(Path scriptDir) {
		this.scriptDir = scriptDir;
	}
	
	public static void verifyStructure(Repository repo) {

		
	}
	public static void updateIndexes(Repository repo) {

		
	}
	
	
	
}
