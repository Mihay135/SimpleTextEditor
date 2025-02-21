package textEditor;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class EditorDocumentListener implements DocumentListener{
	
	private String editorTitle;
	private SimpleTextEditor editor;
	
	public EditorDocumentListener(SimpleTextEditor editor) {
		this.editor = editor;
		this.editorTitle = editor.getTitle();
	}
	
	@Override
	public void removeUpdate(DocumentEvent e) {
		editor.setTitle(editorTitle + "*");
		
	}
	
	@Override
	public void insertUpdate(DocumentEvent e) {
		editor.setTitle(editorTitle + "*");
		
	}
	
	@Override
	public void changedUpdate(DocumentEvent e) {
		editor.setTitle(editorTitle + "*");
	}

}
