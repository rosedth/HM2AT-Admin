package frames;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;

import javax.swing.JPanel;

import org.junit.jupiter.api.Test;

class SettingsPanelTest {
	private Main frame = new Main();
	private SettingsPanel settings=(SettingsPanel) frame.getSettingsPanel();
	
	@Test
	void clearPanelTest() {
		settings.clearPanel();
		assert settings.getBtnSave().isEnabled()==false; 
	}
	
	@Test
	void createRepositoryTest() {
		settings.createRepository("src\\test\\java\\frames\\repository");
		File newRoot= new File("src\\test\\java\\frames\\repository");
		File newModelsDir= new File("src\\test\\java\\frames\\repository\\models");
		File newImpDir= new File("src\\test\\java\\frames\\repository\\implementations");
		File newExDir= new File("src\\test\\java\\frames\\repository\\examples");
		File newDepDir= new File("src\\test\\java\\frames\\repository\\dependencies");
		assert newRoot.exists();
		assert newModelsDir.exists();
		assert newImpDir.exists();
		assert newExDir.exists();
		assert newDepDir.exists();
		assert Main.repository.toString().equalsIgnoreCase(newRoot.toPath().toString());
	}
	
	void loadRepositoryTest() {
		
	}
	
	@Test
	void verifyRepositoryTest() {
		settings.verifyRepository();
		if(Main.repositoryExists()) {
		assert Main.getRepository()==settings.getRepository();
		assert Main.getRepository().toString()==settings.getTxtRepositoryPath().getText();
		}else {
			assert settings.getRepository()==null;
			assert settings.getTxtRepositoryPath().getText().isBlank();
		}
	}
}
