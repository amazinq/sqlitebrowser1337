package de.szut.sqlite_browser.model;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;

import de.szut.sqlite_browser.connection.Connector;
import de.szut.sqlite_browser.gui.Surface;

public class Model {

	private Connector connector;
	private Surface surface;
	private ArrayList<String> tableNames;

	public Model(Surface surface) {
		connector = new Connector();
		this.surface = surface;
		tableNames = new ArrayList<String>();
	}

	public void openConnection(String path) {
			try {
				connector.connect(path);
				ResultSet tables = connector.getTables();
				while(tables.next()) {
					tableNames.add(tables.getString(3));
				}
				surface.updateTree(tableNames);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	public void executeQuery(String query) {
		Object[][] data;
		String[] columnNames;
		ResultSet queryResult;
		ResultSetMetaData queryResultMetaData;
		try {
			queryResult = connector.executeQuery(query);
			queryResultMetaData = queryResult.getMetaData();
			columnNames = new String[queryResultMetaData.getColumnCount() -1];
			for(int i = 1; i < queryResultMetaData.getColumnCount(); i++) {
				columnNames[i-1] = queryResultMetaData.getColumnName(i);
			}
			surface.updateDataList(null, columnNames);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
