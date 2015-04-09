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
				surface.setConnectionEnabled(true);
			} catch (SQLException e) {
				surface.setConnectionEnabled(false);
				e.printStackTrace();
			}
	}
	
	public void executeQuery(String query) {
		Object[][] data;
		String[] columnNames;
		ResultSet queryResult;
		ResultSet numberOfRows;
		ResultSetMetaData queryResultMetaData;
		String tableName;
		try {
			query = query.toLowerCase();
			queryResult = connector.executeQuery(query);
			String[] queryArray = query.split(" ");
			
			int iterator = 0;
			while(!queryArray[iterator].contains("from")) {
				iterator++;
			}
			
			tableName = queryArray[iterator +1];
			numberOfRows = connector.executeQuery("Select count(*) from " + tableName);
			queryResultMetaData = queryResult.getMetaData();
			numberOfRows.next();
			columnNames = new String[queryResultMetaData.getColumnCount()];
			for(int i = 1; i <= queryResultMetaData.getColumnCount(); i++) {
				columnNames[i-1] = queryResultMetaData.getColumnName(i);
			}
			data = new Object[numberOfRows.getInt(1)][queryResultMetaData.getColumnCount()];
			int counter = 0;
			while(queryResult.next()) {
				for(int i = 0; i < queryResultMetaData.getColumnCount(); i++) {
					data[counter][i] = queryResult.getString(i+1);
				}
				counter++;
			}
			surface.updateDataList(data, columnNames);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void closeConnection() {
		try {
			connector.closeConnection();
			surface.updateDataList(null, null);
			surface.setConnectionEnabled(false);
			surface.clearTree();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
