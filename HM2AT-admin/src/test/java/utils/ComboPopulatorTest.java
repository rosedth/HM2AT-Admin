package utils;

import static org.junit.jupiter.api.Assertions.assertEquals;

import javax.swing.JComboBox;

import org.junit.jupiter.api.Test;

class ComboPopulatorTest {

	@Test
	void populateCodeTypeTest() {
		int expected=3;
		JComboBox<String> combo=new JComboBox<String>();
		
		ComboPopulator.populateCodeTypeFile(combo);
		int actual=combo.getItemCount();
		
		assertEquals(expected, actual,"The method should populate comboBox with CodeType options");
	}

	
	@Test
	void populateDependencyManagerTest() {
		int expected=7;
		JComboBox<String> combo=new JComboBox<String>();
		
		ComboPopulator.populateDependencyManager(combo);
		int actual=combo.getItemCount();
		
		assertEquals(expected, actual,"The method should populate comboBox with CodeType options");
	}
	
	@Test
	void populateModelApproachTest() {
		int expected=7;
		JComboBox<String> combo=new JComboBox<String>();
		
		ComboPopulator.populateModelApproach(combo);
		int actual=combo.getItemCount();
		
		assertEquals(expected, actual,"The method should populate comboBox with CodeType options");
	}

	@Test
	void populateModelingLanguageTest(){
		int expected=4;
		JComboBox<String> combo=new JComboBox<String>();
		
		ComboPopulator.populateModelingLanguage(combo);
		int actual=combo.getItemCount();
		
		assertEquals(expected, actual,"The method should populate comboBox with CodeType options");
	}

	@Test
	void populateProgrammingLanguageTest(){
		int expected=8;
		JComboBox<String> combo=new JComboBox<String>();
		
		ComboPopulator.populateProgrammingLanguage(combo);
		int actual=combo.getItemCount();
		
		assertEquals(expected, actual,"The method should populate comboBox with CodeType options");
	}

	@Test
	void populateProgrammingParadigmTest(){
		int expected=6;
		JComboBox<String> combo=new JComboBox<String>();
		
		ComboPopulator.populateProgrammingParadigm(combo);
		int actual=combo.getItemCount();
		
		assertEquals(expected, actual,"The method should populate comboBox with CodeType options");
	}
	
	@Test
	void populateRepositoryTypeTest(){
		int expected=2;
		JComboBox<String> combo=new JComboBox<String>();
		
		ComboPopulator.populateRepositoryType(combo);
		int actual=combo.getItemCount();
		
		assertEquals(expected, actual,"The method should populate comboBox with CodeType options");
	}
	
}
