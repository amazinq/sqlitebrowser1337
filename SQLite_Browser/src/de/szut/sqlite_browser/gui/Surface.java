package de.szut.sqlite_browser.gui;

import java.util.ArrayList;

import de.szut.sqlite_browser.model.Model;
/**
 * Surface interface. All GUIs used for this application have to implement this interface
 * which results in a replacable GUI
 * @author Steffen Wiﬂmann
 *
 */
public interface Surface {
	
	/**
	 * updates the list, which displays a database table
	 * @param data array which consists of the data inside the database table
	 * @param columnNames array which consists of all columnnames
	 */
	public void updateDataList(Object[][] data, String[] columnNames);

	/**
	 * Updates the tree which displays the database structure
	 * @param tableNames all tablenames included in the database
	 */
	public void updateTree(ArrayList<String> tableNames);
	
	/**
	 * Sets the model which basically has to be known by the GUI
	 * @param model
	 */
	public void setModel(Model model);
	
	/**
	 * notifys the GUI whether a connection is available or not
	 * @param connectionEnabled true if the connection is available and false if not
	 */
	public void setConnectionEnabled(boolean connectionEnabled);
	
	/**
	 * Clears the tree which displays the database structure
	 * e.g. after the connection has been closed
	 */
	public void clearTree();
	
	/**
	 * notifys the GUI that an error has been occured, which has to be displayed
	 * by the GUI.
	 * @param errorMessage message which will be shown to the user 
	 */
	public void showErrorMessage(String errorMessage);
	
}
