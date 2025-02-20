package changeManager;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class WordWrapChangeManager {
	int numberOfDo;
	int numberOfUndo;
	
	public WordWrapChangeManager(boolean initialToggle) {
		numberOfDo = (initialToggle) ? 1 : 0;
	}
	
	public boolean getCurrentToggle() {
		return numberOfDo % 2 == 1;
	}
//	
//	public int getNumberOfChanges() {
//		return toggles.size() - 1;
//	}
//	
//	public boolean hasNextString() {
//		return currentPointer < getNumberOfChanges();
//	}
//	
//	public boolean hasPrevoiusString() {
//		return currentPointer > 0;
//	}
//
//	public void saveToggleChange(boolean updatedToggle) {
//		toggles.add(updatedToggle);
//		currentPointer++;
//	}
//	
//	public boolean getToggleAtIndex(Integer index) {
//		boolean result = false;
//		try {
//			result = toggles.get(index);
//		} catch (IndexOutOfBoundsException e) {
//			e.printStackTrace();
//		}
//		return result;
//	}
//	
//	public boolean getPreviousToggle() {
//		currentPointer = (currentPointer <= 0) ? 0 : currentPointer - 1;
//		return toggles.get(currentPointer);
//	}
//	
//	public boolean getNextString() {
//		int max = getNumberOfChanges();
//		currentPointer = (currentPointer >= max) ? max : currentPointer + 1;
//		return toggles.get(currentPointer);
//	}
//	
//	public void deleteFollowingChangesAfterCurrent() {
//		Iterator<Boolean> iterator = toggles.listIterator(currentPointer+1);
//		while (iterator.hasNext()) {
//			iterator.next();
//			iterator.remove();	
//		}
//	}
//	
//	public void resetWithNewString(boolean newToggle) {
//		currentPointer = 0;
//		toggles.clear();
//		toggles.add(newToggle);
//		System.out.println(toggles.size());
//	}
//	
}
