package textEditor;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.stream.IntStream;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.filechooser.FileNameExtensionFilter;

import changeManager.ActionManager;
import changeManager.StringChangeManager;

public class SimpleTextEditor extends JFrame implements ActionListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextArea textArea;
	private JFileChooser fileChooser;
	private Font currentFont;
	private boolean toggleWordWrap = false;
	private Color defaultMenuBgColor = SystemColor.menu;
	private ActionManager actionManager;
	private boolean newFileSaved = false;
	private File openedFile;
	
	public SimpleTextEditor() {
		setTitle("Simple Text Editor");
		setSize(800,600);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JMenuBar menuBar = new JMenuBar();
		
	//File menu option
		JMenu fileMenu = new JMenu("File");
		
	//File manipulation menu item choices and keyboard shortcuts
		
		//Associate shortcut to "new file" menu
		JMenuItem newMenuItem = new JMenuItem("New");
		KeyStroke ctrN = KeyStroke.getKeyStroke(KeyEvent.VK_N, KeyEvent.CTRL_DOWN_MASK);
		newMenuItem.setAccelerator(ctrN);
		
		//Associate shortcut to "open file" option on menu
		JMenuItem openMenuItem = new JMenuItem("Open");
		KeyStroke ctrO = KeyStroke.getKeyStroke(KeyEvent.VK_O, KeyEvent.CTRL_DOWN_MASK);
		openMenuItem.setAccelerator(ctrO);
		
		//Associate Shortcut to "save" option on menu
		JMenuItem saveMenuItem = new JMenuItem("Save");
		KeyStroke ctrS = KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_DOWN_MASK);
		saveMenuItem.setAccelerator(ctrS);
		
		//Associate Shortcut to "exit" option on menu
		JMenuItem exitMenuItem = new JMenuItem("Exit");
		KeyStroke f4 = KeyStroke.getKeyStroke(KeyEvent.VK_F4, 0);
		exitMenuItem.setAccelerator(f4);
		
		newMenuItem.addActionListener(this);
		openMenuItem.addActionListener(this);
		saveMenuItem.addActionListener(this);
		exitMenuItem.addActionListener(this);
		
		fileMenu.add(newMenuItem);
		fileMenu.add(openMenuItem);
		fileMenu.add(saveMenuItem);
		fileMenu.addSeparator();
		fileMenu.add(exitMenuItem);
		
	//Format Menu
		JMenu formatMenu = new JMenu("Format");
		JMenuItem fontMenuItem = new JMenuItem("Font...");
		JMenuItem textColorMenuItem = new JMenuItem("Text Color");
		JMenuItem bgColorMenuItem = new JMenuItem("Background Color");
		JMenuItem wordWrapMenuItem = new JMenuItem("Word Wrap");
		
		wordWrapMenuItem.addActionListener(new ActionListener() {
			
		//When word wrap is active the bg should be a different color so it is noticeable
			@Override
			public void actionPerformed(ActionEvent e) {
				toggleWordWrap = !toggleWordWrap;
				textArea.setLineWrap(toggleWordWrap);
				if(toggleWordWrap)
					wordWrapMenuItem.setBackground(Color.GRAY);
				else {
					wordWrapMenuItem.setBackground(defaultMenuBgColor);
				}
			}
		});
		
		fontMenuItem.addActionListener(this);
		textColorMenuItem.addActionListener(this);
		bgColorMenuItem.addActionListener(this);
		
		//Key shortcuts for the format menu
		KeyStroke altW = KeyStroke.getKeyStroke(KeyEvent.VK_W, KeyEvent.ALT_DOWN_MASK);
		wordWrapMenuItem.setAccelerator(altW);
		KeyStroke altC = KeyStroke.getKeyStroke(KeyEvent.VK_C, KeyEvent.ALT_DOWN_MASK);
		textColorMenuItem.setAccelerator(altC);
		KeyStroke altF = KeyStroke.getKeyStroke(KeyEvent.VK_F, KeyEvent.ALT_DOWN_MASK);
		fontMenuItem.setAccelerator(altF);
		KeyStroke altB = KeyStroke.getKeyStroke(KeyEvent.VK_B, KeyEvent.ALT_DOWN_MASK);
		bgColorMenuItem.setAccelerator(altB);
		
		formatMenu.add(fontMenuItem);
		formatMenu.add(textColorMenuItem);
		formatMenu.add(bgColorMenuItem);
		formatMenu.add(wordWrapMenuItem);
		
		

	// Edit Menu for things like "Undo" or "Redo"
	// Undo/Redo All should undo/redo all actions that are saved up
		JMenu editMenu = new JMenu("Edit");
		JMenuItem undo = new JMenuItem("Undo");
		JMenuItem undoAll = new JMenuItem("Undo All");
		JMenuItem redo = new JMenuItem("Redo");
		JMenuItem redoAll = new JMenuItem("Redo All");
		
		undo.addActionListener(this);
		redo.addActionListener(this);
		
		editMenu.add(undo);
		editMenu.add(redo);
		
	//Menus Added to MenuBar
		menuBar.add(fileMenu);
		menuBar.add(formatMenu);
		menuBar.add(editMenu);
		
		setJMenuBar(menuBar);
		
		//Initializing Editor Area and some utility variables
		textArea = new JTextArea();
		currentFont = textArea.getFont();
		
		fileChooser = new JFileChooser();
		fileChooser.setFileFilter(new FileNameExtensionFilter("Text File", "txt"));
		
		actionManager = new ActionManager(textArea, new StringChangeManager(textArea.getText()));
		
		//Adding area to scrollPane to make it visible
		JScrollPane scrollPane = new JScrollPane(textArea);
		add(scrollPane);
	}
	
	
	
	//MAIN PROCESS
	public static void main(String[] args) {
	SimpleTextEditor editor = new SimpleTextEditor();
	editor.setVisible(true);
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();
		switch (command) {
		case "New":
			newFile();
			break;
		case "Open":
			openFile();
			break;
		case "Save":
			saveFile();
			break;
		case "Font...":
			fontChange();
			break;
		case "Text Color":
			fontColor();
			break;
		case "Background Color":
			bgColor();
			break;
		case "Undo":
			undo();
			break;
		case "Undo All":
			undoAll();
			break;
		case "Redo":
			redo();
			break;
		case "Redo All":
			redoAll();
			break;
		case "Exit":
			System.exit(0);
			break;
		default:
			break;
		}
		
	}
	
	private void redoAll() {
		// TODO Auto-generated method stub
		
	}



	private void redo() {
		actionManager.redo();
		
	}



	private void undoAll() {
		// TODO Auto-generated method stub
		
	}



	private void undo() {
		actionManager.undo();
		
	}



	private void fontColor() {
		Color initialcolor = textArea.getForeground();
 
    // color chooser Dialog Box
		Color color = JColorChooser.showDialog(this,
                "Select a color", initialcolor);
		textArea.setForeground(color);
	}
	
	private void bgColor() {
		Color initialcolor = textArea.getBackground();
 
    // color chooser Dialog Box
		Color color = JColorChooser.showDialog(this,
                "Select a color", initialcolor);
		textArea.setBackground(color);
	}
	
	private void fontChange() {
		JFrame frame;
		JPanel topPanel;
		JLabel lblTitle;
		final JComboBox<String> comboBox;
		JPanel panel2;
		JRadioButton radBtn1;
		JRadioButton radBtn2;
		JLabel lblSize;
		
		frame = new JFrame("Font Type");
		
		
		//Font Family Panel
		topPanel = new JPanel();
		topPanel.setLayout(new BoxLayout(topPanel,BoxLayout.LINE_AXIS));
		
		
		lblTitle = new JLabel("Select desired Font Family and click OK");
		lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		//get the list of all available font families and auto select the current used font family
		String[] availableFonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
		int currentFontFamily = Arrays.asList(availableFonts).indexOf(currentFont.getFamily());
		comboBox = new JComboBox<String>(availableFonts);
		comboBox.setMaximumSize(comboBox.getPreferredSize());
		comboBox.setAlignmentX(Component.CENTER_ALIGNMENT);
		comboBox.setSelectedIndex(currentFontFamily);
		
		topPanel.add(lblTitle);
		topPanel.add(Box.createHorizontalGlue());
		topPanel.add(comboBox);
		
		
		//Size And Type Font Panel
		panel2 = new JPanel();
		panel2.setLayout(new BoxLayout(panel2, BoxLayout.X_AXIS));

		lblSize = new JLabel("Font Size");
		lblSize.setAlignmentX(Component.LEFT_ALIGNMENT);
		String[] availableSizes = IntStream.rangeClosed(8, 72)
				.sorted()
				.mapToObj(String::valueOf)
				.toArray(String[]::new);
		
		
		final JComboBox<String> comboBox2 = new JComboBox<String>(availableSizes);
		comboBox2.setMaximumSize(new Dimension(50, 25));;
		comboBox2.setAlignmentX(Component.LEFT_ALIGNMENT);
		comboBox2.setSelectedIndex(currentFont.getSize()-8);
		
		radBtn1 = new JRadioButton("BOLD");
		radBtn2 = new JRadioButton("ITALIC");
		
		
		
		panel2.add(lblSize);
		panel2.add(comboBox2);
		panel2.add(radBtn1);
		panel2.add(radBtn2);
		
		//Confirm Button to edit the font
		JButton btn = new JButton("OK");
		btn.setAlignmentX(Component.CENTER_ALIGNMENT);
		btn.addActionListener(new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent e) {
				int fontStyle;
				if (radBtn2.isSelected() && !radBtn1.isSelected())
				    fontStyle = Font.ITALIC;
				  else if (radBtn1.isSelected() && !radBtn2.isSelected())
				    fontStyle = Font.BOLD;
				  else if ( radBtn1.isSelected() && radBtn2.isSelected() )
				    fontStyle = Font.ITALIC | Font.BOLD;
				  else 
				    fontStyle = Font.PLAIN;
				
				String newFontFamilyName = (String)comboBox.getSelectedItem();
				String newFontSize = (String)comboBox2.getSelectedItem();
				
				currentFont = new Font(newFontFamilyName, fontStyle, Integer.valueOf(newFontSize));
				textArea.setFont(currentFont);
				frame.dispose();
			}
		});
		
		panel2.add(Box.createHorizontalGlue());
		panel2.add(btn);
		
		frame.add(topPanel, BorderLayout.NORTH);
		frame.add(panel2, BorderLayout.CENTER);
		frame.setVisible(true);
		frame.setSize(480,110);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}

	private void newFile() {
		textArea.setText("");
		newFileSaved = false;
	}
	
	private void openFile() {
		fileChooser.setDialogTitle("Open File");
		fileChooser.setApproveButtonText("Open");
		int returnValue = fileChooser.showOpenDialog(this);
		
		if (returnValue == JFileChooser.APPROVE_OPTION) {
			File file = fileChooser.getSelectedFile();
			try(BufferedReader reader = new BufferedReader(new FileReader(file))) {
				textArea.read(reader, null);
				newFileSaved = true;
				openedFile = file;
				reader.close();
			} catch (IOException e) {
				JOptionPane.showMessageDialog(this, "Error opening file " + openedFile.getName());
			}
		}
	}
	
	private void saveFile() {
		if(newFileSaved == false) {
			fileChooser.setDialogTitle("Save File");
			fileChooser.setApproveButtonText("Save");
			int returnValue = fileChooser.showOpenDialog(this);
			if (returnValue == JFileChooser.APPROVE_OPTION) {
				openedFile = fileChooser.getSelectedFile();
			}
			newFileSaved = true;
		}
		
		String stringToSave = textArea.getText();
		actionManager.textChanged(stringToSave);
		FileWriter writer;
		
		try {
			writer = new FileWriter(openedFile,false);
			writer.write(stringToSave);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(this, "Error saving file: "+openedFile.getName());
		}
			
	}
}
