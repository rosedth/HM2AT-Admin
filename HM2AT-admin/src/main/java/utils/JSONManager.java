package utils;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import org.python.util.PythonInterpreter;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import frames.DependencyPanel;
import frames.Main;
import logic.AdaptivityModel;
import logic.AdaptivityModelImplementation;
import logic.ImplementationDependency;
import logic.ImplementationExample;
import logic.Repository;

public class JSONManager {
	
	public static Repository loadRepositoryConfig(String repoPath) {
		ObjectMapper mapper = new ObjectMapper();
		Repository repo = new Repository();

		try {
			repo = mapper.readValue(new File(repoPath), Repository.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return repo;
	}

	public static void saveRepositoryConfig(String path, Repository repo) {
		ObjectMapper mapper = new ObjectMapper();
		ObjectWriter writer = mapper.writer(new DefaultPrettyPrinter());
		try {
			writer.writeValue(new File(path), repo);
			// mapper.writeValue(new File(path), repo);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static List<AdaptivityModel> readModels() {
		List<AdaptivityModel> modelList = new ArrayList<AdaptivityModel>();
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
		File models_File = new File(Main.repository + "\\models\\models.json");
		if (models_File.exists()) {
			try {
				modelList = objectMapper.readValue(models_File, new TypeReference<List<AdaptivityModel>>() {
				});
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return modelList;
	}

	public static List<AdaptivityModelImplementation> readImplementations() {
		List<AdaptivityModelImplementation> implementationList = new ArrayList<AdaptivityModelImplementation>();
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
		File implementations_File = new File(Main.repository + "\\implementations\\implementations.json");
		if (implementations_File.exists()) {
			try {
				implementationList = objectMapper.readValue(implementations_File, new TypeReference<List<AdaptivityModelImplementation>>() {
				});
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return implementationList;
	}

	public static List<ImplementationDependency> readDependencies() {
		List<ImplementationDependency> dependencyList = new ArrayList<ImplementationDependency>();
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
		File dependencies_File = new File(Main.repository + "\\dependencies\\dependencies.json");
		try {
			dependencyList = objectMapper.readValue(dependencies_File, new TypeReference<List<ImplementationDependency>>() {
			});
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return dependencyList;
	}
	
	public static List<ImplementationExample> readExamples() {
		List<ImplementationExample> dependencyList = new ArrayList<ImplementationExample>();
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
		File dependencies_File = new File(Main.repository + "\\examples\\examples.json");
		try {
			dependencyList = objectMapper.readValue(dependencies_File, new TypeReference<List<ImplementationExample>>() {
			});
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return dependencyList;
	}
	
	public static boolean saveModel(String path, AdaptivityModel model) {
		boolean result;
		// From Java Object to txt (temporary file)
		try {
			Files.writeString(Paths.get(path + "\\temp-model.txt"), model.toString(), StandardCharsets.UTF_8);
		} catch (IOException ex) {
			System.out.print("Invalid Path");
		}
		
		// Executing Python scripts to update Json file
		runSaveModPython(path + "\\temp-model.txt", path + "\\models.json", path);
		
		// Update implementationID in configs
		Main.indexes.replace("modelID", Main.indexes.get("modelID") + 1);

		return true;
	}

	public static boolean saveImplementation(String path, AdaptivityModelImplementation implementation) {
		boolean result;
		// From Java Object to txt (temporary file)
		try {
			Files.writeString(Paths.get(path + "\\temp-implementation.txt"), implementation.toString(),
					StandardCharsets.UTF_8);
		} catch (IOException ex) {
			System.out.print("Invalid Path");
		}

		// Executing Python scripts to update Json file
		runSaveImpPython(path + "\\temp-implementation.txt", path + "\\implementations.json", path);

		// Update implementationID in configs
		Main.indexes.replace("implementationID", Main.indexes.get("implementationID") + 1);
		
		return true;
	}

	
	public static boolean saveDependency2(String path, ImplementationDependency dependency) {
		boolean result;
		// From Java Object to txt (temporary file)
		try {
			Files.writeString(Paths.get(path + "\\temp-dependency.txt"), dependency.toString(),
					StandardOpenOption.APPEND);
		} catch (IOException ex) {
			System.out.print("Invalid Path");
		}

		// Executing Python scripts to update Json file
		String sourceDep="D:\\Academics\\research-workspace\\HM2AT\\repository-root\\dependencies\\temp-dependency.txt";
		String outputDep="D:\\Academics\\research-workspace\\HM2AT\\repository-root\\dependencies\\dependencies.json";
		String repositoryDep="D:\\Academics\\research-workspace\\HM2AT\\repository-root\\dependencies";
		runSaveDepPython(sourceDep, outputDep, repositoryDep);
		//runSaveDepPython(path + "\\temp-dependency.txt", path + "\\dependencies.json", path);

		// Update implementationID in configs
		Main.indexes.replace("dependencyID", Main.indexes.get("dependencyID") + 1);
		
		return true;

	}
	
	public static boolean saveDependency(String path, ImplementationDependency dependency) {
		boolean result;
		// From Java Object to txt (temporary file)
		try {
			Files.writeString(Paths.get(path + "\\temp-dependency.txt"), dependency.toString(),
					StandardCharsets.UTF_8);
		} catch (IOException ex) {
			System.out.print("Invalid Path");
		}

		// Executing Python scripts to update Json file
		runSaveDepPython(path + "\\temp-dependency.txt", path + "\\dependencies.json", path);

		// Update implementationID in configs
		Main.indexes.replace("dependencyID", Main.indexes.get("dependencyID") + 1);
		
		return true;
	}
	
	public static boolean saveExample(String path, ImplementationExample example) {
		boolean result;
		// From Java Object to txt (temporary file)
		try {
			Files.writeString(Paths.get(path + "\\temp-example.txt"), example.toString(),
					StandardCharsets.UTF_8);
		} catch (IOException ex) {
			System.out.print("Invalid Path");
		}

		// Executing Python scripts to update Json file
		runSaveExaPython(path + "\\temp-example.txt", path + "\\examples.json", path);

		// Update implementationID in configs
		Main.indexes.replace("exampleID", Main.indexes.get("exampleID") + 1);
		
		return true;
	}
	
	public static void runSaveImpPython(String source, String output, String repositoryPath) {
		ProcessBuilder builder = new ProcessBuilder("python",
				System.getProperty("user.dir") + "\\scripts\\savingImpToJSON.py", source, output, repositoryPath);
		builder.redirectErrorStream(true);
		try {
			Process process = builder.start();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void runSaveDepPython(String source, String output, String repositoryPath) {
		ProcessBuilder builder = new ProcessBuilder("python",
				System.getProperty("user.dir") + "\\scripts\\savingDepToJSON2.py", source, output, repositoryPath);
		builder.redirectErrorStream(true);
		try {
			Process process = builder.start();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void runSaveModPython(String sourceMod, String outputMod, String repositoryPathMod) {
		ProcessBuilder builder = new ProcessBuilder("python",
				System.getProperty("user.dir") + "\\scripts\\savingModelToJSON.py", sourceMod, outputMod,
				repositoryPathMod);
		builder.redirectErrorStream(true);
		try {
			Process process = builder.start();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void runSaveExaPython(String sourceEx, String outputEx, String repositoryPathEx) {
		ProcessBuilder builder = new ProcessBuilder("python",
				System.getProperty("user.dir") + "\\scripts\\savingExaToJSON.py", sourceEx, outputEx,
				repositoryPathEx);
		builder.redirectErrorStream(true);
		try {
			Process process = builder.start();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}




}
