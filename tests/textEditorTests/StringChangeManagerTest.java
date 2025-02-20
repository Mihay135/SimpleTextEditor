package textEditorTests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import changeManager.StringChangeManager;

class StringChangeManagerTest {

	@Test
	void testStringChangeManagerInitialStringIsCorrect() {
		String initialString = "initial";
		StringChangeManager stringManager = new StringChangeManager(initialString);
		
		assertEquals("initial", stringManager.getCurrentString());
	}
	
	@Test
	void testStringChangeManagerSaveStringMovesCurrentStringPointer() {
		String initialString = "";
		StringChangeManager stringManager = new StringChangeManager(initialString);
		
		String updatedString = initialString + "Updated";
		stringManager.saveStringChange(updatedString);
		
		assertEquals("Updated", stringManager.getCurrentString());
	}
	
	@Test
	void testStringChangeManagerPrevoiusStringChange() {
		String initialString = "";
		StringChangeManager stringManager = new StringChangeManager(initialString);
		
		String updatedString = initialString + "Updated";
		stringManager.saveStringChange(updatedString);
		
		assertEquals("", stringManager.getPreviousString());
	}
	
	@Test
	void testStringChangeManagerPrevoiusStringIsTheSameWhenStartOfChangesIsReached() {
		String initialString = "";
		StringChangeManager stringManager = new StringChangeManager(initialString);
		
		assertEquals("", stringManager.getPreviousString());
	}

	@Test
	void testStringChangeManagerNextStringIsTheSameWhenEndIsReached() {
		String initialString = "";
		StringChangeManager stringManager = new StringChangeManager(initialString);
		
		String updatedString = initialString + "Updated";
		stringManager.saveStringChange(updatedString);
		
		assertEquals("Updated", stringManager.getNextString());
	}
	
	@Test
	void testHasNextStringOnEmptyStrings() {
		String initialString = "";
		StringChangeManager stringManager = new StringChangeManager(initialString);
		
		assertFalse(stringManager.hasNextString());
	}
	
	@Test
	void testHasNextStringReturnsTrueWhenThereIsAnotherStringNext() {
		String initialString = "";
		StringChangeManager stringManager = new StringChangeManager(initialString);
		
		String updatedString = initialString + "Updated";
		stringManager.saveStringChange(updatedString);
		
		//Moving the current pointer back by 1 (currentKey--)
		stringManager.getPreviousString();
		
		assertTrue(stringManager.hasNextString());
	}
	
	@Test
	void testGetStringAtValidIndex() {
		String initialString = "";
		StringChangeManager stringManager = new StringChangeManager(initialString);
		String updatedString = initialString + "Updated";
		stringManager.saveStringChange(updatedString);
		
		assertEquals("", stringManager.getStringAtIndex(0));
	}
	
	@Test
	void testGetStringAtNotValidIndexReturnsEmptyString() {
		String initialString = "";
		StringChangeManager stringManager = new StringChangeManager(initialString);
		String updatedString = initialString + "Updated";
		stringManager.saveStringChange(updatedString);
		
		assertEquals("", stringManager.getStringAtIndex(-1));
	}
	
	@Test
	void testGetStringAtValidIndexDoesNotMoveTheCurrentKey() {
		String initialString = "";
		StringChangeManager stringManager = new StringChangeManager(initialString);
		String updatedString = initialString + "Updated";
		stringManager.saveStringChange(updatedString);
		stringManager.getStringAtIndex(0);
		
		assertEquals("Updated", stringManager.getCurrentString());
	}
	
	@Test
	void testGetNumberOfChangesIsUpdatedCorrectly() {
		String initialString = "";
		StringChangeManager stringManager = new StringChangeManager(initialString);
		
		String updatedString1 = initialString + "Updated";
		stringManager.saveStringChange(updatedString1);
		String updatedString2 = updatedString1 + ", Updated2";
		stringManager.saveStringChange(updatedString2);
		
		assertEquals(2, stringManager.getNumberOfChanges());
	}
	
	@Test
	void testDeleteFromCurrentCorrectlyUpdatesPointers() {
		String initialString = "";
		StringChangeManager stringManager = new StringChangeManager(initialString);
		
		String updatedString1 = initialString + "Updated";
		stringManager.saveStringChange(updatedString1);
		String updatedString2 = updatedString1 + ", Updated2";
		stringManager.saveStringChange(updatedString2);
		
		//move pointer currentKey - 1
		stringManager.getPreviousString();
		stringManager.deleteFollowingChangesAfterCurrent();
		
		assertEquals("Updated", stringManager.getCurrentString());
		assertFalse(stringManager.hasNextString());
	}
	
	@Test
	void testDeleteAfterCurrentCorrectlyUpdatesPointersWithInitialValues() {
		String initialString = "First";
		StringChangeManager stringManager = new StringChangeManager(initialString);
		
		String updatedString1 = initialString + ", Updated1";
		stringManager.saveStringChange(updatedString1);
		String updatedString2 = updatedString1 + ", Updated2";
		stringManager.saveStringChange(updatedString2);
		
		stringManager.resetWithNewString("new");
		
		assertEquals("new", stringManager.getCurrentString());
		System.out.println(stringManager.getNumberOfChanges());
		assertFalse(stringManager.hasNextString());
	}
	
	@Test
	void testResetWithNewValueEmptiesPreviousMemory() {
		String initialString = "First";
		StringChangeManager stringManager = new StringChangeManager(initialString);
		
		//move pointer currentKey - 1
		stringManager.getPreviousString();
		stringManager.deleteFollowingChangesAfterCurrent();
		
		assertEquals("First", stringManager.getCurrentString());
		assertFalse(stringManager.hasNextString());
	}
}
