package de.szut.sqlite_browser.gui;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import de.szut.sqlite_browser.model.Model;


public class Menu extends JMenuBar {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6766567547255409808L;
	private static final String CHOOSER_TITLE = "Please choose a valid db3 file";
	private static final String AI_FOLDER = "db";
	private static final FileFilter AI_FILTER = new FileNameExtensionFilter("DB3 File", "db3");
	
	public Menu(Model model) {
		
		JMenu fileMenu = new JMenu("File");
		add(fileMenu);
		
		JMenuItem openDataBaseMenuItem = new JMenuItem("Open Database");
		fileMenu.add(openDataBaseMenuItem);
		openDataBaseMenuItem.addActionListener(e -> {
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setDialogTitle(CHOOSER_TITLE);
			fileChooser.setCurrentDirectory(new File(AI_FOLDER));
			fileChooser.setFileFilter(AI_FILTER);
			int option = fileChooser.showOpenDialog(null);
			if (option == JFileChooser.APPROVE_OPTION) {
				model.openConnection(fileChooser.getSelectedFile().getAbsolutePath());
			}
				
		});
		
		
	}
}
