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
	private boolean connectionEnabled;

	public Model(Surface surface) {
		connector = new Connector();
		this.surface = surface;
		tableNames = new ArrayList<String>();
		connectionEnabled = false;
	}

	public void openConnection(String path) {
			try {
				connector.connect(path);
				ResultSet tables = connector.getTables();
				while(tables.next()) {
					tableNames.add(tables.getString(3));
				}
				connectionEnabled = true;
				surface.updateTree(tableNames);
				surface.setConnectionEnabled(connectionEnabled);
			} catch (SQLException e) {
				surface.setConnectionEnabled(connectionEnabled);
				surface.showErrorMessage("Error while connecting to Database!");
			}
	}
	
	public void executeQuery(String query, boolean limitEnabled, String lowerBound, String upperBound) {
		Object[][] data;
		String[] columnNames;
		ResultSet queryResult;
		ResultSet numberOfRows;
		ResultSetMetaData queryResultMetaData;
		String tableName = "";
		String limitString;
		query = query.toLowerCase();
		
		if(connectionEnabled) {
			if(!limitEnabled) {
				lowerBound = null;
				upperBound = null;
			} else {
				if(!isAValidNumber(lowerBound, upperBound)) {
					surface.showErrorMessage("Limit bounds are invalid! Both have to be a number greater than 0 and upper bound must be greater than lower bound!");
					return;
				}
			}
		} else {
			surface.showErrorMessage("No connection enabled!");
			return;
		}
		
		if(lowerBound == null || upperBound == null ||query.contains("limit")) {
			limitString = "";
		} else {
//			ÜBERLEGEN WEGEN LIMIT!!!!
			limitString = " limit " + lowerBound +" , " + (String.valueOf((Integer.parseInt(upperBound) - Integer.parseInt(lowerBound))));
		}
		
		
		try {
			
			queryResult = connector.executeQuery(query + limitString);
			String[] queryArray = query.split(" ");
			
			int startOffset = 0;
			int endOffset = 0;
			while(!queryArray[startOffset].contains("from")) {
				startOffset++;
				endOffset++;
			}
			if(queryArray[startOffset +1].startsWith("'") || queryArray[startOffset +1].startsWith(String.valueOf('"'))) {
				while(!queryArray[endOffset +1].endsWith("'") && !queryArray[endOffset +1].endsWith(String.valueOf('"'))) {
					endOffset++;
				}
			}
			for(int iterator = startOffset; iterator <= endOffset; iterator++) {
				tableName = tableName + " " + queryArray[iterator +1];
				
			}
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
			queryResult.close();
			numberOfRows.close();
			surface.updateDataList(data, columnNames);
		} catch (SQLException e) {
			surface.showErrorMessage("Query could not be executed! Might be caused by a typing error.");
		}
	}
	
	public void closeConnection() {
		if(connectionEnabled) {
			try {
				connector.closeConnection();
				connectionEnabled = false;
				surface.updateDataList(null, null);
				surface.setConnectionEnabled(false);
				surface.clearTree();
			} catch (SQLException e) {
				surface.showErrorMessage("Error while closing the connection!");
			}
		} else {
			surface.showErrorMessage("No connection enabled!");
		}
	}
	
	private boolean isAValidNumber(String lowerBound, String upperBound) {
		try {
			int tempLowerBound = Integer.parseInt(lowerBound);
			int tempUpperBound = Integer.parseInt(upperBound);
			if (tempLowerBound < 0 || tempUpperBound < 0) {
				return false;
			}
			if (tempUpperBound < tempLowerBound) {
				return false;
			}
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}
}
