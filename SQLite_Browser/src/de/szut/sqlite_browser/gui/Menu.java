package de.szut.sqlite_browser.gui;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import de.szut.sqlite_browser.model.Model;

/**
 * JMenubar offering options
 * @author Steffen Wißmann
 *
 */
public class Menu extends JMenuBar {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6766567547255409808L;
	private static final String CHOOSER_TITLE = "Please choose a valid db3 file";
	private static final String DB_FOLDER = "db";
	private static final FileFilter DB_FILTER = new FileNameExtensionFilter("DB3 File", "db3");
	
	public Menu(Model model) {
		
		JMenu fileMenu = new JMenu("File");
		add(fileMenu);
		
		JMenu dataBaseMenu = new JMenu("Database");
		add(dataBaseMenu);
		
		JMenuItem disconnectMenuItem = new JMenuItem("Disconnect");
		dataBaseMenu.add(disconnectMenuItem);
		disconnectMenuItem.addActionListener(e -> {
			model.closeConnection();
		});
		
		JMenuItem openDataBaseMenuItem = new JMenuItem("Open Database");
		fileMenu.add(openDataBaseMenuItem);
		openDataBaseMenuItem.addActionListener(e -> {
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setDialogTitle(CHOOSER_TITLE);
			fileChooser.setCurrentDirectory(new File(DB_FOLDER));
			fileChooser.setFileFilter(DB_FILTER);
			int option = fileChooser.showOpenDialog(null);
			if (option == JFileChooser.APPROVE_OPTION) {
				model.openConnection(fileChooser.getSelectedFile().getAbsolutePath());
			}
				
		});	
	}
}
