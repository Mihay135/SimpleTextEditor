package changeManager;

import javax.swing.JTextArea;

public class ActionManager {
	private StringChangeManager stringChangeManager;
	private JTextArea textArea;
	
	public ActionManager(JTextArea textArea , StringChangeManager stringChangeManager) {
		this.textArea = textArea;
		this.stringChangeManager = stringChangeManager;
	}
	
	public void textChanged(String newString) {
		stringChangeManager.saveStringChange(newString);
	}
	
	public void undo() {
		String prev = stringChangeManager.getPreviousString();
		textArea.setText(prev);
	}
	
	public void undoAll() {
		
	}
	
	public void redo() {
		String next = stringChangeManager.getNextString();
		textArea.setText(next);
	}
	
	public void redoAll() {
		
	}
}
