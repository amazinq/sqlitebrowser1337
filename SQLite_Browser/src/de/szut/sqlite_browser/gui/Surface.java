package de.szut.sqlite_browser.gui;

import java.util.ArrayList;

import de.szut.sqlite_browser.model.Model;

public interface Surface {
	
	public void updateDataList(Object[][] data, String[] columnNames);

	public void updateTree(ArrayList<String> tableNames);
	
	public void setModel(Model model);
	
}
