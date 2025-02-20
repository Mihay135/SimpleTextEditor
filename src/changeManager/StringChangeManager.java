package changeManager;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class StringChangeManager {
	private int currentPointer;
	private List<String> changedStringVersions = new LinkedList<String>();

	
	public StringChangeManager(String initialString) {
		this.currentPointer = 0;
		this.changedStringVersions.add(initialString);
	}
	
	public String getCurrentString() {
		return changedStringVersions.get(currentPointer);
	}
	
	public int getNumberOfChanges() {
		return changedStringVersions.size() - 1;
	}
	
	public boolean hasNextString() {
		return currentPointer < getNumberOfChanges();
	}
	
	public boolean hasPrevoiusString() {
		return currentPointer > 0;
	}

	public void saveStringChange(String updatedString) {
		changedStringVersions.add(updatedString);
		currentPointer++;
	}
	
	public String getStringAtIndex(Integer index) {
		int size = this.getNumberOfChanges();
		String result = (index > size || index < 0) ? "" : changedStringVersions.get(index);
		
		return result;
	}
	
	public String getPreviousString() {
		currentPointer = (currentPointer <= 0) ? 0 : currentPointer - 1;
		return changedStringVersions.get(currentPointer);
	}
	
	public String getNextString() {
		int max = getNumberOfChanges();
		currentPointer = (currentPointer >= max) ? max : currentPointer + 1;
		return changedStringVersions.get(currentPointer);
	}
	
	public void deleteFollowingChangesAfterCurrent() {
		Iterator<String> iterator = changedStringVersions.listIterator(currentPointer+1);
		while (iterator.hasNext()) {
			iterator.next();
			iterator.remove();	
		}
	}
	
	public void resetWithNewString(String newString) {
		currentPointer = 0;
		changedStringVersions.clear();
		changedStringVersions.add(newString);
		System.out.println(changedStringVersions.size());
	}
}
