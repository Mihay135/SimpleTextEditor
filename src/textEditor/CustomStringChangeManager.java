package textEditor;

import java.util.HashMap;
import java.util.Map;

public class CustomStringChangeManager {
	private int nextEmptyKey;
	private int currentKey;
	private Map<Integer,String> changedStringVersions = new HashMap<>();
	private int numberOfChanges;
	
	public CustomStringChangeManager(String initialString) {
		this.numberOfChanges = 0;
		this.nextEmptyKey = 0;
		this.currentKey = 0;
		this.changedStringVersions.put(nextEmptyKey++, initialString);
	}
	
	public String getCurrentString() {
		return changedStringVersions.getOrDefault(currentKey, "");
	}
	
	public int getNumberOfChanges() {
		return this.numberOfChanges;
	}
	
	public boolean hasNextString() {
		return currentKey < (nextEmptyKey - 1);
	}
	
	public boolean hasPrevoiusString() {
		return currentKey > 0;
	}

	public void saveStringChange(String updatedString) {
		changedStringVersions.put(nextEmptyKey++, updatedString);
		currentKey++;
		numberOfChanges++;
	}
	
	public String getStringAtIndex(Integer index) {
		return changedStringVersions.getOrDefault(index, "");
	}
	
	public String getPreviousString() {
		currentKey = (currentKey <= 0) ? 0 : currentKey - 1;
		return changedStringVersions.getOrDefault(currentKey, "");
	}
	
	public String getNextString() {
		currentKey = (currentKey >= (nextEmptyKey - 1)) ? nextEmptyKey - 1 : currentKey + 1;
		return changedStringVersions.getOrDefault(currentKey, "");
	}
	
	public void deleteFollowingChangesFromCurrent() {
		this.nextEmptyKey = currentKey + 1;
	}
	
	public void resetWithNewString(String newString) {
		changedStringVersions.clear();
		this.numberOfChanges = 0;
		this.nextEmptyKey = 0;
		this.currentKey = 0;
		this.changedStringVersions.put(nextEmptyKey++, newString);
	}
}
