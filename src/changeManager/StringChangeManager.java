package changeManager;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class StringChangeManager {
	private int currentKey;
	private List<String> changedStringVersions = new LinkedList<String>();

	
	public StringChangeManager(String initialString) {
		this.currentKey = 0;
		this.changedStringVersions.add(initialString);
	}
	
	public String getCurrentString() {
		return changedStringVersions.get(currentKey);
	}
	
	public int getNumberOfChanges() {
		return changedStringVersions.size() - 1;
	}
	
	public boolean hasNextString() {
		return currentKey < getNumberOfChanges();
	}
	
	public boolean hasPrevoiusString() {
		return currentKey > 0;
	}

	public void saveStringChange(String updatedString) {
		changedStringVersions.add(updatedString);
		currentKey++;
	}
	
	public String getStringAtIndex(Integer index) {
		int size = this.getNumberOfChanges();
		String result = (index > size || index < 0) ? "" : changedStringVersions.get(index);
		
		return result;
	}
	
	public String getPreviousString() {
		currentKey = (currentKey <= 0) ? 0 : currentKey - 1;
		return changedStringVersions.get(currentKey);
	}
	
	public String getNextString() {
		int max = getNumberOfChanges();
		currentKey = (currentKey >= max) ? max : currentKey + 1;
		return changedStringVersions.get(currentKey);
	}
	
	public void deleteFollowingChangesAfterCurrent() {
		Iterator<String> iterator = changedStringVersions.listIterator(currentKey+1);
		while (iterator.hasNext()) {
			iterator.next();
			iterator.remove();	
		}
	}
	
	public void resetWithNewString(String newString) {
		currentKey = 0;
		changedStringVersions.clear();
		changedStringVersions.add(newString);
		System.out.println(changedStringVersions.size());
	}
}
