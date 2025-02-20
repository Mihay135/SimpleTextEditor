package textEditorTests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import textEditor.CustomStringChangeManager;

class CustomStringChangeManagerTest {

	@Test
	void testStringChangeManagerInitialStringIsCorrect() {
		String initialString = "initial";
		CustomStringChangeManager stringManager = new CustomStringChangeManager(initialString);
		
		assertEquals("initial", stringManager.getCurrentString());
	}
	
	@Test
	void testStringChangeManagerSaveStringMovesCurrentStringPointer() {
		String initialString = "";
		CustomStringChangeManager stringManager = new CustomStringChangeManager(initialString);
		
		String updatedString = initialString + "Updated";
		stringManager.saveStringChange(updatedString);
		
		assertEquals("Updated", stringManager.getCurrentString());
	}
	
	@Test
	void testStringChangeManagerPrevoiusStringChange() {
		String initialString = "";
		CustomStringChangeManager stringManager = new CustomStringChangeManager(initialString);
		
		String updatedString = initialString + "Updated";
		stringManager.saveStringChange(updatedString);
		
		assertEquals("", stringManager.getPreviousString());
	}
	
	@Test
	void testStringChangeManagerPrevoiusStringIsTheSameWhenStartOfChangesIsReached() {
		String initialString = "";
		CustomStringChangeManager stringManager = new CustomStringChangeManager(initialString);
		
		assertEquals("", stringManager.getPreviousString());
	}

	@Test
	void testStringChangeManagerNextStringIsTheSameWhenEndIsReached() {
		String initialString = "";
		CustomStringChangeManager stringManager = new CustomStringChangeManager(initialString);
		
		String updatedString = initialString + "Updated";
		stringManager.saveStringChange(updatedString);
		
		assertEquals("Updated", stringManager.getNextString());
	}
	
}
