package utils;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import org.python.util.PythonInterpreter;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import frames.Main;
import logic.AdaptivityModel;
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
			//mapper.writeValue(new File(path), repo);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public static List<AdaptivityModel> readModels() {
		List<AdaptivityModel> modelList = new ArrayList<AdaptivityModel>();
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
		File models_File=new File(Main.repository+"\\models\\models.json");
		if (models_File.exists()){
			try {
				modelList = objectMapper.readValue(models_File,new  TypeReference<List<AdaptivityModel>>(){});
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
		}
		return modelList;
	}	
	
	public static boolean saveModel(String path, AdaptivityModel model) {
		boolean result;
		// From Java Object to txt (temporary file)
        try {
            Files.writeString(Paths.get(path+"\\temp-model.txt"),model.toString(),
                              StandardCharsets.UTF_8);
        }
        catch (IOException ex) {
            System.out.print("Invalid Path");
        }
           
		ObjectMapper mapper = new ObjectMapper();
		ObjectWriter writer = mapper.writer(new DefaultPrettyPrinter());
		
        try {
            // Java objects to JSON string - pretty-print
            String jsonInString = mapper.writeValueAsString(model);
            System.out.println(jsonInString);
            jsonInString="'"+jsonInString+"'";
            appendJSON(path+"\\temp-model.txt",path+"\\models.json");
            Main.indexes.replace("modelID", Main.indexes.get("modelID")+1);
            result=true;

        } catch (IOException e) {
            e.printStackTrace();
            result=false;
        }
		return result;
	}
	
	
	public static void appendJSON(String path, String jsonStringModel) {
		// using Jython to execute python script
		
		String[] arguments = { "savingModelToJSON.py", path,jsonStringModel};
		PythonInterpreter.initialize(System.getProperties(), System.getProperties(), arguments);
		try (PythonInterpreter interpreter = new PythonInterpreter()) {
			StringWriter out = new StringWriter();
			interpreter.setOut(out);
			interpreter.execfile(System.getProperty("user.dir") + "\\scripts\\savingModelToJSON.py");
			String outputStr = out.toString();
			System.out.println(outputStr);
		}
		// Update ModelID in configs
		
		JOptionPane.showMessageDialog(null, "Settings saved!");
		
	}
}
