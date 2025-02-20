package textEditor;

import java.util.HashMap;
import java.util.Map;

public class CustomStringChangeManager {
	private int nextEmptyKey;
	private int currentKey;
	private Map<Integer,String> changedStringVersions = new HashMap<>();
	
	public CustomStringChangeManager(String initialString) {
		this.nextEmptyKey = 0;
		this.currentKey = 0;
		this.changedStringVersions.put(nextEmptyKey++, initialString);
	}

	public void saveStringChange(String updatedString) {
		changedStringVersions.put(nextEmptyKey++, updatedString);
		currentKey++;
	}
	
	public String getPreviousString() {
		currentKey = (currentKey == 0) ? 0 : currentKey--;
		return changedStringVersions.getOrDefault(currentKey, "");
	}
	
	public String getNextString() {
		currentKey = (currentKey == (nextEmptyKey - 1)) ? currentKey : currentKey++;
		return changedStringVersions.getOrDefault(currentKey, "");
	}
}
